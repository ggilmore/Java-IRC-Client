package ircClient;

import java.util.Arrays;
import java.util.List;

public class ServerParser {

    /**
     * 
     * @param serverInput
     *            the raw server response string that you would like to parse.
     *            The string follows this format:
     * 
     *            :(prefix) (command) (params) :(trailing)
     * 
     *            The prefix, params, and trailing sections are all optional,
     *            but the command is not.
     *            
     *            //TODO:expand this 
     * 
     * 
     * 
     * 
     * @return A server response with all the prefix, command, arguments, and
     *         trail information nicely parsed out of the serverInput string
     */
    public static ServerResponse parseServerResponse(String serverInput) {

        String prefix = "";
        boolean hasPrefix = serverInput.startsWith(":");
        int prefixEndLocation = 0;
        if (hasPrefix) {
            int indexOfFirstSpace = serverInput.indexOf(" ");
            prefix = serverInput.substring(1, indexOfFirstSpace);
            prefixEndLocation = serverInput.indexOf(" ") + 1;
        }

        String trail = "";
        int trailStartLocation = serverInput.indexOf(" :");
        boolean hasTrail = trailStartLocation >= 0;
        if (hasTrail) {
            trail = serverInput.substring(trailStartLocation + 2);
        } else {
            trailStartLocation = serverInput.length();
        }

        String command;
        List<String> commandPlusArgs = Arrays.asList(serverInput.substring(
                prefixEndLocation, trailStartLocation).split(" "));
        command = commandPlusArgs.get(0);

        return new ServerResponse(prefix, command, commandPlusArgs.subList(1,
                commandPlusArgs.size()), trail);

    }

}
