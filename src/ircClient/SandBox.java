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

    private final static String SERVER_STRING = "";

    public static void serve() {
        Socket socket;
        try {
            socket = new Socket("irc.freenode.net", 6667);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            Map<String, String> arguments = new HashMap<String, String>();
            arguments.put("NICK", "cbk486");
            OutputMessage msg = new OutputMessage(OutputMessageType.NICK,
                    arguments);
            Map<String, String> arguments2 = new HashMap<String, String>();
            arguments2.put("USER", "gmgilore");
            arguments2.put("FULLNAME", "Geoffrey Gilmore");
            OutputMessage msg2 = new OutputMessage(OutputMessageType.USER,
                    arguments2);

            MessageQueue queue = new MessageQueue();
            queue.addToOutputQueue(msg);
            queue.addToOutputQueue(msg2);
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                       
                        OutputMessage msg = queue.popFromOutputQueue();
                        try {
                            OutputStream stream = socket.getOutputStream();
                            PrintWriter writer = new PrintWriter(
                                    new OutputStreamWriter(stream));
                            writer.print(msg.getContents());

                            writer.flush();
                            System.out.println(msg.getContents());
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                }
            });

            thread.start();
            String inputLine = reader.readLine();
            int i = 0;
            while (inputLine != null) {
//                System.out.println(i);
                i++;
                if (i < 10) {
                    System.out.println(inputLine);
                }
                reader.readLine();
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
