package ircClient;

public class RawServerMessage {

    private final String message;

    public RawServerMessage(String rawMessage) {
        this.message = rawMessage;
    }
    
    public String getMessage(){
        return this.message;
    }

}
