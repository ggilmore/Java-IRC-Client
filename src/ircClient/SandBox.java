package ircClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SandBox {

    /**
     * Junk working class that I just use to make sure that I understand the
     * syntax of what is going on here.
     */

    private final static String SERVER_STRING = "";

    public static void serve() {
        Socket socket;
        try {
            socket = new Socket("irc.snoonet.org", 6667);
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            Map<String, String> arguments = new HashMap<String, String>();
            arguments.put("NICK", "cbk486");
            OutputMessage msg = new OutputMessage(OutputMessageType.NICK,
                    arguments);
            Map<String, String> arguments2 = new HashMap<String, String>();
            arguments2.put("USER", "gmgilmore");
            arguments2.put("FULLNAME", "Geoffrey Gilmore");
            OutputMessage msg2 = new OutputMessage(OutputMessageType.USER,
                    arguments2);

            MessageQueue queue = new MessageQueue();
            queue.addToOutputQueue(msg2);
            queue.addToOutputQueue(msg);
            Thread sendToServerThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    PrintWriter writer = new PrintWriter(
                            new OutputStreamWriter(outputStream));
                    while (true) {

                        OutputMessage msg = queue.popFromOutputQueue();

                        writer.println(msg.getContents());

                        writer.flush();
                        System.out.println(msg.getContents());

                    }

                }
            });

            sendToServerThread.start();
            String inputLine = reader.readLine();

            while (true) {
                if (inputLine != null) {
                    System.out.println(inputLine);
                    ServerHandler.parseServerResponse(inputLine, queue);
                }
                inputLine = reader.readLine();

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        serve();

    }
}
