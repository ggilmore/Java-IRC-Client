package ircClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

import messageClasses.RawServerMessage;

class Communicator {
    /**
     * This class is responsible for interacting directly with the IRC server.
     * It continually reads input from the IRC server and forwards that
     * information to the Manager. The Manager continually sends the
     * communicator OutputMessages that the communicator directly sends to the
     * IRC server.
     * 
     * This class is not threadsafe.
     * 
     * This class is not meant to be used by any class other than the Manager.
     */

    /**
     * The socket used to communicate with the server.
     */
    private final Socket socket;

    /**
     * The manager that is controlling this communicator.
     */
    private final Manager manager;

    /**
     * The reader for the socket's input stream.
     */
    private final BufferedReader reader;

    /**
     * The writer for the socket's output stream.
     */
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
    Communicator(String serverAddress, int portToConnectTo, Manager manager)
            throws UnknownHostException, IOException {

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
            throw new RuntimeException("unable to create socket input reader");
        }

        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            this.writer = writer;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("unable to create socket writer");
        }

    }

    /**
     * Starts the communication between the server and this communicator
     * instance. Call this before anything else.
     */
    void start() {
        this.readServerInput(this.reader, this.manager);
        System.out.println("still not working");
    }

    /**
     * Sends "stringToSend" to the IRC server that we are currently connected
     * to.
     * 
     * @param stringToSend
     *            the message that we want to send
     */
    void sendToServer(String stringToSend) {
        System.out.println("sent: " + stringToSend);
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
    void readServerInput(BufferedReader reader, Manager manager) {
        ContinuousServerReader serverReader = new ContinuousServerReader(
                reader, manager);
        serverReader.startReading();
    }
}
