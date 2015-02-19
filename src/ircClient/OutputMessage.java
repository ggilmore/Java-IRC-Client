package ircClient;

import java.util.List;
import java.util.Map;

public class OutputMessage {
    /**
     * this class represents messages that are going to be sent to the irc
     * server that we are connected to
     */

    private final OutputMessageType type;

    private final static String NICK = "NICK";

    private final static String USER = "USER";

    private final static String USER_MODE_DEFAULT = "0";

    private final static String ASTERISK = "*";

    private final static String JOIN = "JOIN";

    private final static String CHANNEL = "CHANNEL";

    private final static String POUND_SYMBOL = "#";

    private final static String COLON = ":";

    private final static String FULLNAME = "FULLNAME";
    
    private final static String RESPONSE_ID = "RESPONSEID";
    
    private final static String PONG = "PONG";

    private final String contents;

    /**
     * Creates a new outputmessage instances with type "type" and arguments
     * "arguments"
     * 
     * @param type
     *            the type of output message this is
     * @param arguments
     *            If type == OutputMessageType.NICK, arguments must be a map
     *            with two key value pairs. One of the keys must be "USER" with
     *            it's value being the username associated with the request. The
     *            other value must be "NICK", which is the nickname associated
     *            with the request.
     * 
     *            If type == OutputMessageType.JOIN, arguments must be a map
     *            with one key value pair. The key must be "CHANNEL", with the
     *            value being the channel that the user wants to join.
     * 
     * 
     * 
     */
    public OutputMessage(OutputMessageType type, Map<String, String> arguments) {
        this.type = type;

        switch (type) {
        case USER: {
            assert arguments.size() == 2;
            String username = arguments.get(USER);
            String fullName = arguments.get(FULLNAME);
            this.contents = USER + " " + username + " " + USER_MODE_DEFAULT
                    + " " + ASTERISK + " " + COLON + fullName;
//            this.contents = USER + " " + username + " " + "irc.snoonet.org" + " irc.snoonet.org" + " :Geoffrey Gilmore";
        }
            break;

        case NICK: {
            assert arguments.size() == 1;
            String nickname = arguments.get(NICK);
            this.contents = NICK + " " + nickname;
        }
            break;
        case JOIN: {
            assert arguments.size() == 1;
            String channel = arguments.get(CHANNEL);
            this.contents = JOIN + " " + POUND_SYMBOL + " " + channel;
        }
            break;
            
        case PONG: {
            assert arguments.size() == 1;
            String responseID = arguments.get(RESPONSE_ID);
            this.contents = PONG + " " + COLON + responseID;
            break;
        }

        default:
            throw new RuntimeException(
                    "uh -oh, shouldn't get here. Forgot to handle an output type for some reason");
        }

    }

    /**
     * 
     * @return the contents of this output message
     */
    public String getContents() {
        return this.contents;
    }

    /**
     * a helpful string representation of this instance, usefull for debugging
     */
    @Override
    public String toString() {
        return "OutMsg Cntns: " + this.contents;
    }
}
