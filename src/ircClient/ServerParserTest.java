package ircClient;

import static org.junit.Assert.*;
import messageClasses.ServerResponse;

import org.junit.Test;

public class ServerParserTest {

    /**
     * testing Strategy:
     * 
     * test response with prefix, command, arguments, and trail test response
     * with prefix, command, and trail
     * with prefix and command
     * with command and trail
     * with command and args
     * with command only 
     */

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void testStringWithPrefixCommandArgsAndTrail() {
        String testString = ":irc.localhost.localdomain 433 Caleb :Nickname is already in use.";
        ServerResponse response = ServerParser.parseServerResponse(testString);
        assertEquals(response.getCommand(), "433");
        assertEquals(response.getPrefix(), "irc.localhost.localdomain");
        assertEquals(response.getTrail(), "Nickname is already in use.");
        for (String argument : response.getArguments()) {
            assertEquals(argument, "Caleb");
        }
    }

    @Test
    public void testStringWithPrefixCommandAndTrail() {
        String testString = ":irc.localhost.localdomain 433 :Nickname is already in use.";
        ServerResponse response = ServerParser.parseServerResponse(testString);
        assertEquals(response.getCommand(), "433");
        assertEquals(response.getPrefix(), "irc.localhost.localdomain");
        assertEquals(response.getTrail(), "Nickname is already in use.");
        assert (response.getArguments().isEmpty());
    }

    @Test
    public void testStringWithOnlyPrefixAndCommand() {
        String testString = ":irc.localhost.localdomain 433";
        ServerResponse response = ServerParser.parseServerResponse(testString);
        assertEquals(response.getCommand(), "433");
        assertEquals(response.getPrefix(), "irc.localhost.localdomain");
        assert response.getTrail().isEmpty();
        assert (response.getArguments().isEmpty());

    }
    
    @Test
    public void testStringWithOnlyCommandAndTrail() {
        String testString = "433 :Nickname is already in use.";
        ServerResponse response = ServerParser.parseServerResponse(testString);
        assertEquals(response.getCommand(), "433");
        assert response.getPrefix().isEmpty();
        assertEquals(response.getTrail(), "Nickname is already in use.");
        assert (response.getArguments().isEmpty());

    }
    
    @Test
    public void testStringWithOnlyCommandAndArgs() {
        String testString = "433 Caleb";
        ServerResponse response = ServerParser.parseServerResponse(testString);
        assertEquals(response.getCommand(), "433");
        assert response.getPrefix().isEmpty();
        assert response.getTrail().isEmpty();
        assert response.getArguments().size() == 1;
        assert response.getArguments().contains("Caleb");

    }
    
    @Test
    public void testStringWithOnlyCommand() {
        String testString = "433";
        ServerResponse response = ServerParser.parseServerResponse(testString);
        assertEquals(response.getCommand(), "433");
        assert response.getPrefix().isEmpty();
        assert response.getTrail().isEmpty();
        assert response.getArguments().isEmpty();
        
    }
    
    @Test
    public void testStringPing() {
        String testString = "PING :c?Sj?BQj|p";
        ServerResponse response = ServerParser.parseServerResponse(testString);
        assertEquals(response.getCommand(), "PING");
        assert response.getPrefix().isEmpty();
        assertEquals(response.getTrail(), "c?Sj?BQj|p");
        assert response.getArguments().isEmpty();
        
    }
    
    

}
