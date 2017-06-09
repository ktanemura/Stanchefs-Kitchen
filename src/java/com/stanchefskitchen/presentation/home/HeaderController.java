package com.stanchefskitchen.presentation.home;

import com.stanchefskitchen.data.models.Account;
import com.stanchefskitchen.presentation.login.Session;
import java.io.Serializable;

/**
 *
 * @author Brittany Berlanga
 */
public class HeaderController implements Serializable {
    private static final String LOGGED_IN_INFO = "Logged in as: %s %s";
    private Session userSession;
    private String loggedInInfo;
    
    public HeaderController() {}
    
    public void setUserSession(Session userSession) {
        this.userSession = userSession;
    }
    
    public Session getUserSession() {
        return userSession;
    }
    
    public String getLoggedInInfo() {
        return "hello, monica";
//        Account account = userSession.getAccount();
//        this.loggedInInfo = String.format(LOGGED_IN_INFO, account.firstName, 
//                account.lastName);
//        return loggedInInfo;
    }
}
