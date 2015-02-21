package ircClient;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is an interface I made in order to easily populate the "commandMapping"
 * dictionary in the serverHandler class. Methods that are intended to be
 * responses to IRC server commands should be tagged with this annotation.
 * 
 * This interface is not intended for use in anywhere but the serverHandler
 * class.
 * 
 * @author gmgilmore
 *
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface CommandResponseMethod {

    /**
     * Value is the IRC command that should this function should fire in
     * response to.
     * 
     * @return the IRC command that this function should respond to
     */
    String value();

}
