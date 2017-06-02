package com.stanchefskitchen.presentation.signup;

import com.stanchefskitchen.data.models.Account;
import com.stanchefskitchen.data.models.AccountType;
import com.stanchefskitchen.data.providers.AccountProvider;
import com.stanchefskitchen.presentation.NavController;
import java.sql.Date;
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
    private UIInput addressInput;
    private UIInput cityInput;
    private UIInput stateInput;
    private UIInput zipInput;
    private UIInput cardNumInput;
    private UIInput expInput;
    private UIInput crcInput;
    
    private String crc;
    
    private final AccountType type = AccountType.customer;
    
    private static final String NUMBER_REGEX = "[0-9]+";
    private static final String EMAIL_AT_SIGN = "@";
    private static final String WHITE_SPACE = "\\s+";
    private static final String FIRST_EMPTY_ERROR = "First name can't be empty";
    private static final String LAST_EMPTY_ERROR = "Last name can't be empty";
    private static final String USERNAME_EMPTY_ERROR = "Username can't be empty";
    private static final String PASSWORD_EMPTY_ERROR = "Password can't be empty";
    private static final String PASSWORD_MATCH_ERROR = "Password don't match";
    private static final String EMAIL_EMPTY_ERROR = "E-mail can't be empty";
    private static final String EMAIL_INVALID_ERROR = "Invalid E-mail";
    private static final String ADDRESS_EMPTY_ERROR = "Address can't be empty";
    private static final String CITY_EMPTY_ERROR = "City can't be empty";
    private static final String STATE_EMPTY_ERROR = "State can't be empty";
    private static final String ZIP_EMPTY_ERROR = "Zip can't be empty";
    private static final String ZIP_INVALID_ERROR = "Zip must be only numbers";
    private static final String CARDNUM_EMPTY_ERROR = "Card number can't be empty";
    private static final String CARDNUM_INVALID_ERROR = "Card number must be only numbers";
    private static final String DATE_FORMAT_ERROR = "Incorrect date format (MM/dd/YYYY)";
    private static final String CRC_EMPTY_ERROR = "Crc can't be empty";
    private static final String CRC_INVALID_ERROR = "Crc must only be numbers";
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

    public void setAddressInput(UIInput address) {
        this.addressInput = address;
    }

    public void setCityInput(UIInput city) {
        this.cityInput = city;
    }

    public void setStateInput(UIInput state) {
        this.stateInput = state;
    }

    public void setZipInput(UIInput zip) {
        this.zipInput = zip;
    }

    public void setCardNumInput(UIInput cardNumber) {
        this.cardNumInput = cardNumber;
    }

    public void setExpInput(UIInput expiration) {
        this.expInput = expiration;
    }
    
    public void setCrcInput(UIInput crc) {
        this.crcInput = crc;
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

    public UIInput getAddressInput() {
        return addressInput;
    }

    public UIInput getCityInput() {
        return cityInput;
    }

    public UIInput getStateInput() {
        return stateInput;
    }

    public UIInput getZipInput() {
        return zipInput;
    }

    public UIInput getCardNumInput() {
        return cardNumInput;
    }

    public UIInput getExpInput() {
        return expInput;
    }

    public UIInput getCrcInput() {
        return crcInput;
    }
    
    public void validateInputs(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        
        String firstName = firstInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        String lastName = lastInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        String username = usernameInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        String password = passwordInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        String confirmPassword = confirmPasswordInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        String email = emailInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        String address = addressInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        String city = cityInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        String state = stateInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        String zip = zipInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        String cardNum = cardNumInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        String expiration = expInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        crc = (String) value;
        
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
        
        if (address.isEmpty()) {
            FacesMessage msg = new FacesMessage(ADDRESS_EMPTY_ERROR, FAILED_SIGNUP);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (city.isEmpty()) {
            FacesMessage msg = new FacesMessage(CITY_EMPTY_ERROR, FAILED_SIGNUP);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (state.isEmpty()) {
            FacesMessage msg = new FacesMessage(STATE_EMPTY_ERROR, FAILED_SIGNUP);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (zip.isEmpty()) {
            FacesMessage msg = new FacesMessage(ZIP_EMPTY_ERROR, FAILED_SIGNUP);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (!zip.matches(NUMBER_REGEX)) {
            FacesMessage msg = new FacesMessage(ZIP_INVALID_ERROR, FAILED_SIGNUP);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (cardNum.isEmpty()) {
            FacesMessage msg = new FacesMessage(CARDNUM_EMPTY_ERROR, FAILED_SIGNUP);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (!cardNum.matches(NUMBER_REGEX)) {
            FacesMessage msg = new FacesMessage(CARDNUM_INVALID_ERROR, FAILED_SIGNUP);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (expiration.split("/").length != 3) {
            FacesMessage msg = new FacesMessage(DATE_FORMAT_ERROR, FAILED_SIGNUP);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (crc.isEmpty()) {
            FacesMessage msg = new FacesMessage(CRC_EMPTY_ERROR, FAILED_SIGNUP);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (!crc.matches(NUMBER_REGEX)) {
            FacesMessage msg = new FacesMessage(CRC_INVALID_ERROR, FAILED_SIGNUP);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

    private Date parseDate(String dateString) {
        String[] dateFields = dateString.split("/");
        int month = Integer.valueOf(dateFields[0]);
        int day = Integer.valueOf(dateFields[1]);
        int year = Integer.valueOf(dateFields[2]);
        return Date.valueOf(year + "-" + month + "-" + day);
    }

    public String createCustomer() {
        String firstName = firstInput.getLocalValue().toString();
        String lastName = lastInput.getLocalValue().toString();
        String username = usernameInput.getLocalValue().toString();
        String password = passwordInput.getLocalValue().toString();
        String email = emailInput.getLocalValue().toString();
        String address = addressInput.getLocalValue().toString();
        String city = cityInput.getLocalValue().toString();
        String state = stateInput.getLocalValue().toString();
        String zip = zipInput.getLocalValue().toString();
        String cardNum = cardNumInput.getLocalValue().toString();
        String expiration = expInput.getLocalValue().toString();
        
        Account account = new Account(username, password, firstName, lastName, 
                AccountType.customer);
        if (AccountProvider.addAccount(account) > 0) {
            return NavController.LOGIN;
        }
        else {
            return NavController.SIGNUP;
        }
    }
}
