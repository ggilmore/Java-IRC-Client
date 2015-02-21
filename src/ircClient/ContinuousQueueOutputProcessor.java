package ircClient;

import messageClasses.OutputMessage;

/**
 * This class creates a new thread that continuously pulls output messages from
 * the output queue and sends them to the communicator.
 * 
 * 
 * This class is not meant for use by any other class other the manager.
 * 
 * @author gmgilmore
 *
 * 
 */
class ContinuousQueueOutputProcessor {

    private final Thread thread;

    /**
     * Create a new ContinuousQueueOutputProcessor instance
     * 
     * @param queue
     *            the queue to pull the output messages from
     * @param communicator
     *            the communicator to send the message text to
     */
    ContinuousQueueOutputProcessor(MessageQueue queue, Communicator communicator) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    OutputMessage message = queue.popFromOutputQueue();

                    communicator.sendToServer(message.getContents());

                }

            }
        });
        this.thread = thread;
    }

    /**
     * Calling this function will cause this ContinuousQueueOutputProcessor to
     * start pulling messages off of the OutputQueue. Call this before anything
     * else.
     */
    void processOutput() {
        this.thread.start();
    }

}
