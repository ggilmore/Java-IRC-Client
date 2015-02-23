package ircClient;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import messageClasses.OutputMessage;
import messageClasses.OutputMessageType;

public class UserParser {

    public static OutputMessage parseUserInput(String userInput) {

        StringTokenizer st = new StringTokenizer(userInput);
        if (!userInput.startsWith("/")) {
            ;
            Map<String, String> args = new HashMap<String, String>() {
                {
                    put("TARGET", "#reddit");
                    put("CONTENTS", userInput);
                }

            };
            return new OutputMessage(OutputMessageType.PRIVMSG, args);
        }

        else {
            int numberOfTokens = st.countTokens();

            switch (numberOfTokens) {
            case 3: {
                String command = st.nextToken().substring(1);
                String parameter = st.nextToken();
                String message = st.nextToken();
                Map<String, String> args = new HashMap<String, String>();
                args.put("CONTENTS", command + " " + parameter + " " + message);
                return new OutputMessage(OutputMessageType.RAW, args);
            }

            case 2: {
                String command = st.nextToken().substring(1);
                String message = st.nextToken();
                Map<String, String> args = new HashMap<String, String>();
                args.put("CONTENTS", command + " " + message);
                return new OutputMessage(OutputMessageType.RAW, args);
            }
            
            default:{
                String command = st.nextToken();
                String message = st.nextToken();
                Map<String, String> args = new HashMap<String, String>();
                args.put("CONTENTS", "");
                return new OutputMessage(OutputMessageType.RAW, args);
            }

            }
        }
    }
    
    
    
}
