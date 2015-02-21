package ircClient;

import java.util.Queue;

import messageClasses.ServerResponse;

interface ServerHandlerHelper {

    /**
     * This is just a runnable interface so that we can write ServerHandler's
     * commandMapping dictionary is a nice, clean way. This allows us to use
     * Java's lambda syntax.
     * 
     * run can store any function that we stick inside of it upon the creation
     * the of lambda expression.
     * 
     * @param response
     *            is the ServerResposne that we are going to feed to the
     *            function stored inside of run
     * @param manager
     *            the manager that is controlling this server handler
     */
    public void run(ServerResponse response, Manager manager);
}
