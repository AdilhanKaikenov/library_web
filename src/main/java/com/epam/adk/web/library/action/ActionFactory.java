package com.epam.adk.web.library.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * ActionFactory class created on 23.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class ActionFactory {

    private static final Logger log = LoggerFactory.getLogger(ActionFactory.class);
    private static final ActionFactory instance = new ActionFactory();

    private static Map<String, Action> actions;

    private ActionFactory() {
        log.debug("Entering constructor ActionFactory class");
        actions = new HashMap<>();
        actions.put("POST/registration", new RegistrationAction());
        actions.put("GET/registration", new ShowPageAction("registration"));
        actions.put("POST/authorization", new AuthorizationAction());
        actions.put("GET/authorization", new AuthorizationAction());
        actions.put("GET/set-locale", new SelectLocaleAction());
        actions.put("GET/welcome", new ShowWelcomeAction());
        actions.put("GET/success-registration", new ShowPageAction("success-registration"));
        actions.put("GET/authorization-error", new ShowPageAction("authorization-error"));
        actions.put("GET/logout", new LogoutAction());
        actions.put("GET/book-about", new BookAboutAction());
        log.debug("Action Factory class, actions SIZE {}", actions.size());
    }

    public static ActionFactory getInstance(){
        return instance;
    }

    /**
     * The method which returns Action.
     *
     * @param actionName string key to determine the object is an Action.
     * @return Action.
     */
    public Action getAction(String actionName){
        return actions.get(actionName);
    }
}
