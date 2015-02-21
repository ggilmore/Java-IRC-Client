package ircClient;

import java.util.Arrays;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.experimental.theories.Theories;

import messageClasses.OutputMessage;
import messageClasses.OutputMessageType;
import messageClasses.ServerResponse;

/**
 * This class parses the input from the server and also stores functions that
 * are called in response to the command that the server has given us.
 * 
 * @author gmgilmore
 *
 */
public class ServerHandler {

    // TODO: is there a better way to do this than typing all these strings and
    // adding them manually to commandMapping? SOLVED
    /**
     * Dictionary that maps all commands from the server responses to functions
     * that are called and respond appropriately to their respective commands.
     */
    private final static Map<String, ServerHandlerHelper> commandMapping = new HashMap<String, ServerHandlerHelper>() {
        {

            /**
             * 1) look through all the methods in ServerHandler
             * 
             * 2) If a method has the command response annotation
             * 
             * 3) Grab the value associate with the annotation and use it as the
             * key, and put the function tagged with the annotation as the value
             */
            ServerHandler handler = new ServerHandler();
            for (Method method : handler.getClass().getMethods()) {
                System.out.println(method.toGenericString());
                if (method.isAnnotationPresent(CommandResponseMethod.class)) {
                    CommandResponseMethod annotation = method
                            .getAnnotation(CommandResponseMethod.class);
                    String command = annotation.value();
                    put(command, (x, m) -> {
                        try {
                            method.invoke(handler, x, m);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    });
                }
            }

        }
    };

    /**
     * Creates a new ServerHandler
     */
    public ServerHandler() {

    }

    /**
     * Parses the server input and runs a function that is an appropriate
     * response to that input
     * 
     * @param responseToParse
     *            the server input string to parse
     * @param manager
     *            the manager that is controlling this server handler
     */
    public static void parseServerResponse(String responseToParse,
            Manager manager) {

        ServerResponse response = ServerParser
                .parseServerResponse(responseToParse);

        if (commandMapping.containsKey(response.getCommand())) {
            commandMapping.get(response.getCommand()).run(response, manager);
        }

    }

    /**
     * 
     * @param response
     * @param manager
     */
    @CommandResponseMethod("PING")
    public static void pong(ServerResponse response, Manager manager) {

        Map<String, String> arguments = new HashMap<String, String>() {
            {
                put("RESPONSEID", response.getTrail());
            }
        };
        OutputMessage msg = new OutputMessage(OutputMessageType.PONG, arguments);

        manager.sendToOutputQueue(msg);

    }

    @CommandResponseMethod("004")
    public static void joinReddit(ServerResponse response, Manager manager) {
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
