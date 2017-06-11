package com.stanchefskitchen.presentation.customer;

import com.stanchefskitchen.data.models.CreditCard;
import com.stanchefskitchen.data.providers.CreditCardProvider;
import com.stanchefskitchen.presentation.NavController;
import com.stanchefskitchen.presentation.login.Session;
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
public class AddCreditCardController {

    private UIInput cardNumInput;
    private UIInput expInput;
    private UIInput addressInput;
    private String crc;
    private Session userSession;
    
    private static final String NUMBER_REGEX = "[0-9]+";
    private static final String WHITE_SPACE = "\\s+";
    private static final String CARDNUM_EMPTY_ERROR = "Card number can't be empty";
    private static final String CARDNUM_INVALID_ERROR = "Card number must be only numbers";
    private static final String DATE_FORMAT_ERROR = "Incorrect date format (MM/dd/YYYY)";
    private static final String CRC_EMPTY_ERROR = "Crc can't be empty";
    private static final String CRC_INVALID_ERROR = "Crc must only be numbers";
    private static final String ADDRESS_EMPTY_ERROR = "Address can't be empty";
    private static final String FAILED_ADD_CARD = "Could not add new credit card";
    
    public UIInput getCardNumInput() {
        return cardNumInput;
    }

    public void setCardNumInput(UIInput cardNumInput) {
        this.cardNumInput = cardNumInput;
    }

    public UIInput getExpInput() {
        return expInput;
    }

    public void setExpInput(UIInput expInput) {
        this.expInput = expInput;
    }

    public UIInput getAddressInput() {
        return addressInput;
    }

    public void setAddressInput(UIInput addressInput) {
        this.addressInput = addressInput;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public Session getUserSession() {
        return userSession;
    }

    public void setUserSession(Session userSession) {
        this.userSession = userSession;
    }
    
    public void validateInputs(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        
        String cardNum = cardNumInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        String expiration = expInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        String address = addressInput.getLocalValue().toString().replaceAll(WHITE_SPACE,"");
        crc = (String) value;
        
        if (cardNum.isEmpty()) {
            FacesMessage msg = new FacesMessage(CARDNUM_EMPTY_ERROR, FAILED_ADD_CARD);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (!cardNum.matches(NUMBER_REGEX)) {
            FacesMessage msg = new FacesMessage(CARDNUM_INVALID_ERROR, FAILED_ADD_CARD);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (expiration.split("/").length != 3) {
            FacesMessage msg = new FacesMessage(DATE_FORMAT_ERROR, FAILED_ADD_CARD);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (address.isEmpty()) {
            FacesMessage msg = new FacesMessage(ADDRESS_EMPTY_ERROR, FAILED_ADD_CARD);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (crc.isEmpty()) {
            FacesMessage msg = new FacesMessage(CRC_EMPTY_ERROR, FAILED_ADD_CARD);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        if (!crc.matches(NUMBER_REGEX)) {
            FacesMessage msg = new FacesMessage(CRC_INVALID_ERROR, FAILED_ADD_CARD);
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
    
    public String addCard() {
        String cardNum = cardNumInput.getLocalValue().toString();
        String expiration = expInput.getLocalValue().toString();
        String address = addressInput.getLocalValue().toString();
        
        int accountId = userSession.getAccount().id;
        
        CreditCard card = new CreditCard(cardNum, accountId, Integer.valueOf(crc), 
                address, parseDate(expiration));
        boolean success = CreditCardProvider.addCardToAccount(accountId, card);
        
        if (success) {
            return NavController.CREDIT_CARD;
        }
        else {
            return null;
        }
    }
}
