package com.stanchefskitchen.presentation.login;

import com.stanchefskitchen.data.models.Account;
import com.stanchefskitchen.data.providers.AccountProvider;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Brittany Berlanga
 */
public class Session implements Serializable {

    private static final String FAILED_LOGIN = "Login validation failed";
    private static final String INVALID_LOGIN = "Invalid login";
    private static final String PASSWORD_CHANGE_FAIL = "Password change failed";
    private static final String OLD_PASSWORD_FAIL = "Incorrect old password";
    private static final String NEW_PASSWORD_FAIL = "New passwords do not "
            + "match";
    private static final String NEW_PASSWORD_LEN_FAIL = "New passwords must "
            + "contain at least one character";
    private static final String PASSWORD_CHANGE_SUCCESS = "Password change "
            + "was successful";
    private static final String FAILED_CONNECTION = "Failed to connect to server";

    private UIInput usernameUI;
    private UIInput oldPasswordInput;
    private UIInput newPasswordInput;
    private String username;
    private String password;
    private Account account;

    /**
     * Creates a new instance of LoginBean
     */
    public Session() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsernameUI(UIInput usernameUI) {
        this.usernameUI = usernameUI;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setOldPasswordInput(UIInput oldPasswordInput) {
        this.oldPasswordInput = oldPasswordInput;
    }

    public void setNewPasswordInput(UIInput newPasswordInput) {
        this.newPasswordInput = newPasswordInput;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UIInput getUsernameUI() {
        return usernameUI;
    }

    public Account getAccount() {
        if (account == null && username != null && password != null) {
             account = AccountProvider.login(username, password);
        }
        return account;
    }

    public UIInput getOldPasswordInput() {
        return oldPasswordInput;
    }

    public UIInput getNewPasswordInput() {
        return newPasswordInput;
    }

    public void logout() {
        account = null;
        username = null;
        password = null;
    }

    public void validateLogin(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        String password = (String) value;
        String username = usernameUI.getLocalValue().toString();
        
        try {
            account = AccountProvider.login(username, password);
        }
        catch (NullPointerException e) {
            FacesMessage msg = new FacesMessage(FAILED_CONNECTION, INVALID_LOGIN);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (account == null) {
            FacesMessage msg = new FacesMessage(FAILED_LOGIN, INVALID_LOGIN);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        else {
            this.username = username;
            this.password = password;
        }
    }

    public void validatePasswordChange(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        String oldPassword = oldPasswordInput.getLocalValue().toString().trim();
        String newPassword = newPasswordInput.getLocalValue().toString().trim();
        String reenterNewPassword = ((String) value).trim();
        FacesMessage msg;

        if (!oldPassword.equals(account.password)) {
            msg = new FacesMessage(OLD_PASSWORD_FAIL, PASSWORD_CHANGE_FAIL);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        }
        else if (!newPassword.equals(reenterNewPassword)) {
            msg = new FacesMessage(NEW_PASSWORD_FAIL, PASSWORD_CHANGE_FAIL);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        }
        else if (newPassword.length() <= 0) {
            msg = new FacesMessage(NEW_PASSWORD_LEN_FAIL, PASSWORD_CHANGE_FAIL);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        }
        else if (AccountProvider.changePassword(account, newPassword)) {
            account = AccountProvider.login(account.username, newPassword);
            this.username = account.username;
            this.password = newPassword;
            msg = new FacesMessage(PASSWORD_CHANGE_SUCCESS,
                    PASSWORD_CHANGE_SUCCESS);
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        else {
            msg = new FacesMessage(PASSWORD_CHANGE_FAIL,
                    PASSWORD_CHANGE_FAIL);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        }

        throw new ValidatorException(msg);
    }
}
