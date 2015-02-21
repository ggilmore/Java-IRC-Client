package ircClient;

import messageClasses.RawServerMessage;

/**
 * This class creates a new thread that continuously pulls RawServerMessages
 * from the inputQueue and sends them to the server handler to be dealt with
 * there.
 * 
 * This class is not meant to be used by any class other than the manager.
 * 
 */
class ContinuousQueueInputProcessor {

    private final Thread thread;

    /**
     * Creates a new ContinuousQueueInputProcessor
     * 
     * @param handler
     *            the handler that the RawServerMessages are sent to
     * @param queue
     *            the queue that the RawServerMessages are being pulled from
     * @param manager
     *            the manager that is controlling this
     *            continuousQueueInputProcessor instance
     */
    ContinuousQueueInputProcessor(ServerHandler handler, MessageQueue queue,
            Manager manager) {
        // TODO Auto-generated constructor stub
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
        this.thread = thread;

    }

    /**
     * Calling this function will cause this ContinuousQueueInputProcessor to
     * start pulling messages off of the InputQueue. Call this before anything
     * else.
     */
    void processInput() {
        this.thread.start();
    }

}
