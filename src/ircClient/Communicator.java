package ircClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

public class Communicator {
    /**
     * This class is responsible for interacting directly with the IRC server.
     * It continually reads input from the IRC server and forwards that
     * information to the Manager. The Manager continually sends the
     * communicator OutputMessages that the communicator directly sends to the
     * IRC server.
     */

    private final Socket socket; 

    private final Manager manager;

    private final BufferedReader reader;

    private final PrintWriter writer;

    /**
     * Creates a new communicator
     * 
     * @param serverAddress
     *            the address of the irc server to connect to. A hostname or IP
     *            address (in the form of a string), are acceptable.
     * @param portToConnectTo
     *            the port connect to, most commonly 6667
     * @param manager
     *            the Manager that is controlling this communicator
     * @throws IOException
     *             if
     * @throws UnknownHostException
     */
    public Communicator(String serverAddress, int portToConnectTo,
            Manager manager) throws UnknownHostException, IOException {

        // I gave the constructor the ability throw an exception that must be
        // handled because not being able to connect to the specified server is
        // an occurrence that is not at all uncommon.
        this.socket = new Socket(serverAddress, portToConnectTo);

        this.manager = manager;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    this.socket.getInputStream()));
            this.reader = reader;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("unable to create input reader");
        }

        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            this.writer = writer;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("unable to create socket writer");
        }

        this.readServerInput(this.reader, this.manager);
    }

    /**
     * Sends "stringToSend" to the IRC server that we are currently connected
     * to.
     * 
     * @param stringToSend
     *            the message that we want to send
     */
    public void sendToServer(String stringToSend) {
        this.writer.println(stringToSend);
        this.writer.flush();
    }

    /**
     * Creates a new thread that constantly waits for a non-null input from the
     * server and then hands it off to the manager for processing.
     * 
     * @param reader
     *            the BufferedReader that is monitoring the input stream coming
     *            from our socket that is connected to the server
     * @param manager
     *            the manager instance that we are going to hand this input off
     *            to
     */
    private void readServerInput(BufferedReader reader, Manager manager) {
        Thread thread = new Thread() {
            public void run() {
                String inputLine;
                try {
                    inputLine = reader.readLine();
                    while (true) {
                        if (inputLine != null) {
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
        };
        thread.run();
    }
}
