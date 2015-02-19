package ircClient;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 *
 * This is the "master class" that coordinates communication between the
 * communicator, queue, and server handler. Any member of this class must use
 * this class' methods in order to communicate amongst themselves.
 * 
 * 
 * @author gmgilmore
 *
 */
public class Manager {

    /**
     * The communicator that talks directly with the server
     */
    private final Communicator communicator;

    /**
     * Threadsafe queue that holds all the incoming and outgoing messages that
     * we use for processing.
     */
    private final MessageQueue queue;

    /**
     * Handler instance that process all the input from the server and then
     * decides what to do from there.
     */
    private final ServerHandler handler;

    /**
     * 
     * @param serverAddress
     * @param portToConnectTo
     * @throws UnknownHostException
     * @throws IOException
     */
    public Manager(String serverAddress, int portToConnectTo)
            throws UnknownHostException, IOException {
        this.communicator = new Communicator(serverAddress, portToConnectTo,
                this);
        this.queue = new MessageQueue();
        this.handler = new ServerHandler();

        // start processing the output queue
        this.processOutputQueue(this.queue, this.communicator);

        // start processing the input queue
        this.processServerInput(handler, this);
    }

    /**
     * Creates a new thread that continuously pulls output messages from the
     * output queue and sends them to the communicator.
     * 
     * @param queue
     *            the queue to pull the output messages from
     * @param communicator
     *            the communicator to send the message text to
     */
    private void processOutputQueue(MessageQueue queue,
            Communicator communicator) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    OutputMessage message = queue.popFromOutputQueue();
                    communicator.sendToServer(message.getContents());
                }

            }
        });
        thread.start();
    }

    /**
     * queue's up this message and prepares it to be sent to the server
     * 
     * @param rawMessage
     *            the message to be sent to the server
     */
    public void queueUpServerInput(RawServerMessage rawMessage) {
        this.queue.addToInputQueue(rawMessage);
    }

    /**
     * Creates a new thread that continuously pulls RawServerMessage from the
     * inputQueue and sends them to the server handler to be dealt with there
     * 
     * @param handler
     *            the handler that the RawServerMessages are sent to
     * @param manager
     *            the manager that stores the inputQueue that you want to pull
     *            from
     */
    private void processServerInput(ServerHandler handler, Manager manager) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    RawServerMessage rawMessage = queue.popFromInputQueue();
                    handler.parseServerResponse(rawMessage.getMessage(),
                            manager);
                }

            }
        });
        thread.start();
    }

    /**
     * Prepares "messageToSend" so that it can be sent to the irc server
     * 
     * @param messageToSend
     *            the message to send
     */
    public void sendToOutputQueue(OutputMessage messageToSend) {
        this.queue.addToOutputQueue(messageToSend);
    }

}
