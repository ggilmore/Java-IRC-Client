package messageClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerResponse {

    private final String prefix;

    private final String command;

    private final String trail;

    private final List<String> arguments;

    /**
     * 
     * @param prefix
     * @param command
     * @param arguments
     * @param trail
     */
    public ServerResponse(String prefix, String command,
            List<String> arguments, String trail) {
        this.prefix = prefix;
        this.command = command;
        List<String> argumentsCopy = new ArrayList<String>();
        argumentsCopy.addAll(arguments);
        argumentsCopy = Collections.unmodifiableList(argumentsCopy);
        this.arguments = argumentsCopy;
        this.trail = trail;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getCommand() {
        return this.command;
    }

    public String getTrail() {
        return this.trail;
    }

    public List<String> getArguments() {
        return this.arguments;
    }

    @Override
    public String toString() {
        return this.prefix + " " + this.command + " " + this.getTrail() + " "
                + this.getArguments();
    }

}
