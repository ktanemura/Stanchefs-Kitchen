package com.stanchefskitchen.presentation.signup;

import com.stanchefskitchen.data.models.Account;
import com.stanchefskitchen.data.models.AccountType;
import com.stanchefskitchen.data.providers.AccountProvider;
import com.stanchefskitchen.presentation.NavController;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Tyler Wong
 */
public class SignupController {
    
    private UIInput firstInput;
    private UIInput lastInput;
    private UIInput usernameInput;
    private UIInput passwordInput;
    private UIInput confirmPasswordInput;
    private UIInput emailInput;
   
    private String email;
        
    private static final String EMAIL_AT_SIGN = "@";
    private static final String WHITE_SPACE = "\\s+";
    private static final String FIRST_EMPTY_ERROR = "First name can't be empty";
    private static final String LAST_EMPTY_ERROR = "Last name can't be empty";
    private static final String USERNAME_EMPTY_ERROR = "Username can't be empty";
    private static final String PASSWORD_EMPTY_ERROR = "Password can't be empty";
    private static final String PASSWORD_MATCH_ERROR = "Password don't match";
    private static final String EMAIL_EMPTY_ERROR = "E-mail can't be empty";
    private static final String EMAIL_INVALID_ERROR = "Invalid E-mail";
    private static final String FAILED_SIGNUP = "Could not signup";

    public void setUsernameInput(UIInput username) {
        this.usernameInput = username;
    }

    public void setPasswordInput(UIInput password) {
        this.passwordInput = password;
    }
    
    public void setConfirmPasswordInput(UIInput password) {
        this.confirmPasswordInput = password;
    }

    public void setFirstInput(UIInput firstName) {
        this.firstInput = firstName;
    }

    public void setLastInput(UIInput lastName) {
        this.lastInput = lastName;
    }

    public void setEmailInput(UIInput email) {
        this.emailInput = email;
    }
    
    public UIInput getFirstInput() {
        return firstInput;
    }

    public UIInput getLastInput() {
        return lastInput;
    }

    public UIInput getUsernameInput() {
        return usernameInput;
    }

    public UIInput getPasswordInput() {
        return passwordInput;
    }

    public UIInput getConfirmPasswordInput() {
        return confirmPasswordInput;
    }

    public UIInput getEmailInput() {
        return emailInput;
    }
    
    public void validateInputs(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        
        String firstName = firstInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        String lastName = lastInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        String username = usernameInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        String password = passwordInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        String confirmPassword = confirmPasswordInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        email = (String) value;
        
        if (firstName.isEmpty()) {
            FacesMessage msg = new FacesMessage(FIRST_EMPTY_ERROR, FAILED_SIGNUP);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (lastName.isEmpty()) {
            FacesMessage msg = new FacesMessage(LAST_EMPTY_ERROR, FAILED_SIGNUP);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (username.isEmpty()) {
            FacesMessage msg = new FacesMessage(USERNAME_EMPTY_ERROR, FAILED_SIGNUP);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (password.isEmpty()) {
            FacesMessage msg = new FacesMessage(PASSWORD_EMPTY_ERROR, FAILED_SIGNUP);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (!password.equals(confirmPassword)) {
            FacesMessage msg = new FacesMessage(PASSWORD_MATCH_ERROR, FAILED_SIGNUP);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (email.isEmpty()) {
            FacesMessage msg = new FacesMessage(EMAIL_EMPTY_ERROR, FAILED_SIGNUP);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (email.split(EMAIL_AT_SIGN).length != 2) {
            FacesMessage msg = new FacesMessage(EMAIL_INVALID_ERROR, FAILED_SIGNUP);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

    public String createCustomer() {
        String firstName = firstInput.getLocalValue().toString();
        String lastName = lastInput.getLocalValue().toString();
        String username = usernameInput.getLocalValue().toString();
        String password = passwordInput.getLocalValue().toString();
        
        Account account = new Account(username, password, firstName, lastName, 
                AccountType.customer);
        int accountId = AccountProvider.addAccount(account);
        
        if (accountId > 0) {
            return NavController.LOGIN;
        }
        else {
            return NavController.SIGNUP;
        }
    }
}
