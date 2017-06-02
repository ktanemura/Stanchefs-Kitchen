/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanchefskitchen.presentation.admin;

import com.stanchefskitchen.data.models.AccountType;
import com.stanchefskitchen.data.models.Account;
import com.stanchefskitchen.data.providers.AccountProvider;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Ryan
 */
public class CreateEmployeeController {
    private static final String E_PASSWORD = "Passwords do not match";
    private static final String E_USERNAME = "Username is taken";
    private static final String CREATE_FAIL = "Employee creation failed";
    private static final String CREATE_SUCCESS = "Employee creation was "
            + "successful";
    
    public void validateNewEmployee(FacesContext context, UIComponent component, 
            Object value) throws ValidatorException {
        String firstName = emplFirstNameInput.getLocalValue().toString().trim();
        String lastName = emplLastNameInput.getLocalValue().toString().trim();
        String username = emplUsernameInput.getLocalValue().toString().trim();
        String password = emplPasswordInput.getLocalValue().toString().trim();
        String confirmPassword = ((String) value).trim();
        
        FacesMessage msg;
        if (!password.equals(confirmPassword)) {
            msg = new FacesMessage(E_PASSWORD, CREATE_FAIL);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        }
        else if (AccountProvider.usernameExists(username)) {
            msg = new FacesMessage(E_USERNAME, CREATE_FAIL);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        }
        else {
            Account account = new Account(username, password, firstName, 
                        lastName, AccountType.employee);
                if (AccountProvider.addAccount(account) != -1) {
                    msg = new FacesMessage(CREATE_SUCCESS, CREATE_SUCCESS);
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);
                }
                else {
                    msg = new FacesMessage(CREATE_FAIL, CREATE_FAIL);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                }
        }
        
        throw new ValidatorException(msg);
    }
    
    private UIInput emplFirstNameInput;
    private UIInput emplLastNameInput;
    private UIInput emplUsernameInput;
    private UIInput emplPasswordInput;

    public UIInput getEmplFirstNameInput() {
        return emplFirstNameInput;
    }

    public void setEmplFirstNameInput(UIInput emplFirstNameInput) {
        this.emplFirstNameInput = emplFirstNameInput;
    }

    public UIInput getEmplLastNameInput() {
        return emplLastNameInput;
    }

    public void setEmplLastNameInput(UIInput emplLastNameInput) {
        this.emplLastNameInput = emplLastNameInput;
    }

    public UIInput getEmplUsernameInput() {
        return emplUsernameInput;
    }

    public void setEmplUsernameInput(UIInput emplUsernameInput) {
        this.emplUsernameInput = emplUsernameInput;
    }

    public UIInput getEmplPasswordInput() {
        return emplPasswordInput;
    }

    public void setEmplPasswordInput(UIInput emplPasswordInput) {
        this.emplPasswordInput = emplPasswordInput;
    }
}
