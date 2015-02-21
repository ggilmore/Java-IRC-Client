package ircClient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import messageClasses.OutputMessage;
import messageClasses.OutputMessageType;

public class Sandbox2 {

    public static void serve() {
        try {
            System.out.println("help");
            Manager manager = new Manager("142.4.213.142", 6667);
            System.out.println("we got there");
            Map<String, String> arguments = new HashMap<String, String>();
            arguments.put("NICK", "cbk486");
            OutputMessage msg = new OutputMessage(OutputMessageType.NICK,
                    arguments);
            Map<String, String> arguments2 = new HashMap<String, String>();
            arguments2.put("USER", "gmgilmore");
            arguments2.put("FULLNAME", "Geoffrey Gilmore");
            OutputMessage msg2 = new OutputMessage(OutputMessageType.USER,
                    arguments2);
           
            

            manager.start();
            System.out.println("bla");
            
            manager.sendToOutputQueue(msg2);
            
            manager.sendToOutputQueue(msg);
            
            
            
            

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("bad address");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("IOException");
        }
        
        
    }
    
    public static void main(String[] args) {
        serve();
    }
}
