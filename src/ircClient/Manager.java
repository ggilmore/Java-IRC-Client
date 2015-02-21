package ircClient;

import java.io.IOException;
import java.net.UnknownHostException;

import messageClasses.OutputMessage;
import messageClasses.RawServerMessage;
import static org.junit.Assert.*;

/**
 *
 * This is the "master class" that coordinates communication between the
 * communicator, queue, and server handler. Any member of this class should this
 * class' methods in order to communicate amongst themselves (pass information
 * around the program).
 * 
 * 
 * @author gmgilmore
 *
 */
public class Manager {

    /**
     * The communicator that talks directly with the server
     */
    private Communicator communicator;

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
     * The address of the server that we are going to connect to
     */
    private final String serverAddress;

    /**
     * The port that we are using to connect to the IRC server.
     */
    private final int portToConnectTo;

    /**
     * Creates a new manager instance.
     * 
     * @param serverAddress
     *            The address of the server that we are going to connect to. Can
     *            be a host name or an IP address.
     * @param portToConnectTo
     *            The port that we are using to connect to the IRC server.
     */
    public Manager(String serverAddress, int portToConnectTo)
            throws UnknownHostException, IOException {
        this.queue = new MessageQueue();
        this.handler = new ServerHandler();
        this.serverAddress = serverAddress;
        this.portToConnectTo = portToConnectTo;

        checkRep();

    }

    /**
     * Starts the client-server communicator and queue processing. Call this
     * before anything else.
     * 
     * @throws IOException
     * @throws UnknownHostException
     *             if the given host wasn't valid or we can't connect to it
     */
    public void start() throws UnknownHostException, IOException {

        this.communicator = new Communicator(this.serverAddress,
                this.portToConnectTo, this);

        this.communicator.start();
        // start processing the input queue
        this.processServerInput(handler, this.queue, this);
        // start processing the output queue
        this.processOutputQueue(this.queue, this.communicator);

    }

    private void checkRep() {
        assertNotNull(this.queue);
        assertNotNull(this.handler);
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
        ContinuousQueueOutputProcessor processor = new ContinuousQueueOutputProcessor(
                queue, communicator);
        processor.processOutput();
    }

    /**
     * queue's up this message and prepares it to be sent to the server
     * 
     * @param rawMessage
     *            the message to be sent to the server
     */
    public void queueUpServerInput(RawServerMessage rawMessage) {
        assert rawMessage != null;
        this.queue.addToInputQueue(rawMessage);
        System.out.println("input message stored successfully");
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
    private void processServerInput(ServerHandler handler, MessageQueue queue,
            Manager manager) {
        ContinuousQueueInputProcessor processor = new ContinuousQueueInputProcessor(
                handler, queue, manager);
        processor.processInput();
    }

    /**
     * Prepares "messageToSend" so that it can be sent to the IRC server
     * 
     * @param messageToSend
     *            the message to send
     */
    public void sendToOutputQueue(OutputMessage messageToSend) {
        this.queue.addToOutputQueue(messageToSend);
    }

}
