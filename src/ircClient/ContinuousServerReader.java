package ircClient;

import java.io.BufferedReader;
import java.io.IOException;

import messageClasses.RawServerMessage;

/**
 * This class creates a new thread that constantly waits for a non-null input
 * from the server and then hands it off to the manager for processing.
 * 
 * This class is not meant to be used by any class other than the Communicator.
 * 
 * @author gmgilmore
 *
 **/
class ContinuousServerReader {

    private final Thread thread;

    /**
     * Creates a new ContinuousServerReader.
     * 
     * @param reader
     *            the BufferedReader that is monitoring the input stream coming
     *            from our socket that is connected to the server
     * @param manager
     *            the manager instance that we are going to hand this input off
     * 
     */
    ContinuousServerReader(BufferedReader reader, Manager manager) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                String inputLine;
                try {
                    inputLine = reader.readLine();
                    while (true) {
                        if (inputLine != null) {
                            System.out.println("inputLine: " + inputLine);
                            RawServerMessage message = new RawServerMessage(
                                    inputLine.trim());
                            manager.queueUpServerInput(message);
                        }
                        inputLine = reader.readLine();
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        this.thread = thread;
    }

    /**
     * Calling this method causes this ContinuousServerReader to continuously
     * read server input and send it to the manager. Call this first.
     */
    void startReading() {
        this.thread.start();
    }

}
