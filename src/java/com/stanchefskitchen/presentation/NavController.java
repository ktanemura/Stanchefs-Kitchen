package com.stanchefskitchen.presentation;

import com.stanchefskitchen.presentation.login.Session;
import java.io.Serializable;

/**
 *
 * @author Brittany Berlanga
 */
public class NavController implements Serializable {
    public static final String LOGIN = "login";
    public static final String SIGNUP = "signup";
    public static final String ADMIN_HOME = "admin_home";
    public static final String EMPL_HOME = "empl_home";
    public static final String CUS_HOME = "cus_home";
    public static final String CREATE_ACCOUNT = "create_account";
    
    private Session userSession;
    
    /**
     * Creates a new instance of NavController
     */
    public NavController() {
    }
    
    public Session getUserSession()
    {
        return userSession;
    }

    public void setUserSession(Session userSession)
    {
        this.userSession = userSession;
    }
       
    public String goToLogin() {
        return LOGIN;
    }
    
    public String goToCreateAccount() {
        return SIGNUP;
    }
    
    public String goToAccountHome() {
        switch (userSession.getAccount().type) {
            case administrator:
                return ADMIN_HOME;
            case employee:
                return EMPL_HOME;
            case customer:
                return CUS_HOME;
            default:
                return LOGIN;
        }
    }
    
    public String logout() {
        userSession.logout();
        return LOGIN;
    }
}
