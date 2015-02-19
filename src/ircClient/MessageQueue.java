package ircClient;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
    /**
     * This class is a container that contains the queues for the messages going
     * out to be sent to the server ("out" queue) as well as the queue for
     * messages coming to the client ("in" queue, to be implemented)
     * 
     * This class is threadsafe. 
     * 
     */

    private final BlockingQueue<OutputMessage> out;

    private final BlockingQueue<RawServerMessage> in;

    /**
     * creates a new message queue
     */
    public MessageQueue() {
        out = new LinkedBlockingQueue<OutputMessage>();
        in = new LinkedBlockingQueue<RawServerMessage>();
    }

    /**
     * adds messageToAdd to the "out" queue
     * 
     * @param messageToAdd
     *            the OutputMessage to add to the "out" queue
     */
    public void addToOutputQueue(OutputMessage messageToAdd) {
        try {
            out.put(messageToAdd);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 
     * @return the outputMessage from the head of the "out" queue. If there are
     *         no messages in the "out" queue, this method blocks until there is
     *         a message to be removed.
     */
    public OutputMessage popFromOutputQueue() {
        OutputMessage message;
        try {
            message = out.take();
            return message;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(
                    "Interrupted while trying to take from OutputQueue");
        }

    }

    /**
     * adds messageToAdd to the "in" queue
     * 
     * @param messageToAdd
     *            the RawServerMessage to add to the "in" queue
     * 
     */
    public void addToInputQueue(RawServerMessage messageToAdd) {

    }

    /**
     * 
     * @return the serverMessage from the head of the "in" queue. If there are
     *         no messages in the "in" queue, this method blocks until there is
     *         a message until to be removed.
     */
    public RawServerMessage popFromInputQueue() {
        RawServerMessage message;
        try {
            message = in.take();
            return message;
            
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(
                    "Interrupted while trying to take from InputQueue");
        }
               
        
    }

}
