package messageClasses;

import static org.junit.Assert.*;

/**
 * This class simply packages up the raw input that the communicator gets back
 * from the server, and sends to the manager for processing.
 * 
 * This class is threadsafe because its fields are immutable. this.message is
 * final and stores a string, which is immutable.
 * 
 * @author gmgilmore
 *
 */
public class RawServerMessage {

    private final String message; // not null

    /**
     * Creates a new RawServerMessage instances with "message" equal to
     * "rawMessage"
     * 
     * @param rawMessage
     *            the raw message from the server that we want to store
     */
    public RawServerMessage(String rawMessage) {
        this.message = rawMessage;
        checkRep();
    }

    /**
     * asserts the rep invariants that are stated above
     */
    private void checkRep() {
        assertNotNull(this.message);
    }

    /**
     * 
     * @return the sever message stored in this RawSeverMessage instance
     */
    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "RawServerMessage instance Cntns: " + this.getMessage();
    }

}
