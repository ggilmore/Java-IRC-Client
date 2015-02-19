package ircClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This class parses the input from the server and then makes decisions about
 * what to do from there.
 * 
 * @author gmgilmore
 *
 */
public class ServerHandler {

    /**
     * Dictionary that maps all commands from the server responses to functions
     * that are called and respond appropriately to their respective commands.
     */
    private final static Map<String, ServerHandlerHelper> commandMapping = new HashMap<String, ServerHandlerHelper>() {
        {
            put("PING", (x, m) -> {
                pong(x, m);
            });

            put("004", (x, m) -> {
                joinReddit(x, m);
            });

        }
    };

    /**
     * 
     * @param responseToParse
     * @param manager
     */
    public static void parseServerResponse(String responseToParse,
            Manager manager) {

        ServerResponse response = ServerParser
                .parseServerResponse(responseToParse);

        if (commandMapping.containsKey(response.getCommand())) {
            commandMapping.get(response.getCommand()).run(response, manager);
        }

    }

    private static void pong(ServerResponse response, Manager manager) {

        Map<String, String> arguments = new HashMap<String, String>() {
            {
                put("RESPONSEID", response.getTrail());
            }
        };
        OutputMessage msg = new OutputMessage(OutputMessageType.PONG, arguments);

        manager.sendToOutputQueue(msg);

    }

    private static void joinReddit(ServerResponse response, Manager manager) {
        Map<String, String> arguments2 = new HashMap<String, String>() {
            {
                put("CHANNEL", "reddit");
            }
        };
        OutputMessage msg2 = new OutputMessage(OutputMessageType.JOIN,
                arguments2);
        manager.sendToOutputQueue(msg2);
    }

}
