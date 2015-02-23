package messageClasses;

import java.util.HashMap;
import java.util.Map;

import sun.applet.Main;

public enum OutputMessageType {
    NICK, USER, JOIN, PONG, PRIVMSG, PART, RAW, INVALID;

    private static Map<String, OutputMessageType> nameTypeMapping = new HashMap<String, OutputMessageType>() {
        {
            put("NICK", NICK);
            put("USER", USER);
            put("JOIN", JOIN);
            put("PONG", PONG);
            put("PRIVMSG", PRIVMSG);
            put("PART", PART);
        }
    };

    public static OutputMessageType parseOutputMessageType(String commandToParse) {
        if (nameTypeMapping.containsKey(commandToParse)){
            return nameTypeMapping.get(commandToParse);
        }
        else{
            return INVALID;
        }
    }

    
}
