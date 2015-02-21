package messageClasses;

import static org.junit.Assert.*;

import org.junit.Test;

public class RawServerMessageTest {
    /**
     * Testing strategy:
     * Constructor:
     * - valid input
     * - null input 
     * 
     * getMessage
     * -valid input 
     * 
     */

    
    
    @Test (expected = AssertionError.class)
    public void testAssertionsEnabled(){
        assert false;
    }
    
    @Test
    public void testContructorValidInput(){
        String testString = "rawMessageToTest";
        RawServerMessage message = new RawServerMessage(testString);
        
    }

    @Test (expected = AssertionError.class)
    public void testContructorNullInput(){
        String testString = null;
        RawServerMessage message = new RawServerMessage(testString);        
    }
    
    @Test 
    public void testGetMessage(){
        String testString = "rawMessageToTest";
        RawServerMessage message = new RawServerMessage(testString);
        assertEquals("rawMessageToTest", message.getMessage());
    }
    
    @Test
    public void testToString(){
        String testString = "rawMessageToTest";
        RawServerMessage message = new RawServerMessage(testString);
        assertNotNull(message.toString());
    }
}
