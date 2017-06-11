package com.stanchefskitchen.presentation.cart;

import com.stanchefskitchen.data.models.CreditCard;
import com.stanchefskitchen.data.providers.CreditCardProvider;
import com.stanchefskitchen.presentation.NavController;
import com.stanchefskitchen.presentation.login.Session;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Brittany Berlanga
 */
public class CheckoutController {

    private static final String NUMBER_REGEX = "[0-9]+";
    private static final String EMAIL_AT_SIGN = "@";
    private static final String WHITE_SPACE = "\\s+";
    private static final String PHONE_EMPTY_ERROR = "Phone number can't be empty";
    private static final String PHONE_INVALID_ERROR = "Invalid phone number";
    private static final String EMAIL_EMPTY_ERROR = "E-mail can't be empty";
    private static final String EMAIL_INVALID_ERROR = "Invalid E-mail";
    private static final String ADDRESS_EMPTY_ERROR = "Address can't be empty";
    private static final String CITY_EMPTY_ERROR = "City can't be empty";
    private static final String STATE_EMPTY_ERROR = "State can't be empty";
    private static final String ZIP_EMPTY_ERROR = "Zip can't be empty";
    private static final String ZIP_INVALID_ERROR = "Zip must be only numbers";
    private static final String CARDNUM_EMPTY_ERROR = "Card number can't be empty";
    private static final String CARDNUM_INVALID_ERROR = "Card number must be only numbers";
    private static final String DATE_FORMAT_ERROR = "Incorrect date format (MM/YYYY)";
    private static final String CRC_EMPTY_ERROR = "Crc can't be empty";
    private static final String CRC_INVALID_ERROR = "Crc must only be numbers";
    private static final String FAILED_SIGNUP = "Could not signup";

    private ShoppingCartController shoppingCart;
    private Session userSession;
    private boolean pickUp;
    private boolean payAtStore;
    private boolean useExistingCard;
    private boolean completeForm;
    private String address = "";
    private String city = "";
    private String state = "";
    private String zipcode = "";
    private String cardNum = "";
    private String crcCode = "";
    private String cardExp = "";
    private String cardAddress = "";
    private String cardCity = "";
    private String cardState = "";
    private String cardZipcode = "";
    private String email = "";
    private String phoneNumber = "";

    private UIInput emailInput;
    private UIInput phoneNumberInput;
    private UIInput addressInput;
    private UIInput cityInput;
    private UIInput stateInput;
    private UIInput zipInput;
    private UIInput cardNumInput;
    private UIInput expInput;
    private UIInput crcInput;
    private UIInput cardAddressInput;
    private UIInput cardCityInput;
    private UIInput cardStateInput;
    private UIInput cardZipInput;
    private String selectedCreditCard;
    private List<CreditCard> creditCards;

    /**
     * Creates a new instance of CheckoutController
     */
    public CheckoutController() {
        this.pickUp = true;
        this.payAtStore = true;
        this.useExistingCard = true;
        this.creditCards = new ArrayList();
    }

    public ShoppingCartController getShoppingCart() {
        return shoppingCart;
    }

    public List<CreditCard> getCreditCards() {
        if (userSession.getAccount() != null && creditCards.isEmpty()) {
            creditCards = CreditCardProvider.getCardsByAccountId(userSession
                    .getAccount().id);
        }
        return creditCards;
    }

    public boolean finishDisabled() {
        if (pickUp) {
            if (payAtStore) {
                return email.isEmpty() || phoneNumber.isEmpty();
            }
            else if (useExistingCard) {
                return creditCards.isEmpty();
            }
            else {
                return cardNum.isEmpty() || crcCode.isEmpty()
                        || cardExp.isEmpty() || cardAddress.isEmpty()
                        || cardCity.isEmpty() || cardState.isEmpty()
                        || cardZipcode.isEmpty();
            }
        }
        else {
            if (address.isEmpty() || city.isEmpty() || state.isEmpty()
                    || zipcode.isEmpty()) {
                return true;
            }
            if (payAtStore) {
                return email.isEmpty() || phoneNumber.isEmpty();
            }
            else if (useExistingCard) {
                return creditCards.isEmpty();
            }
            else {
                return cardNum.isEmpty() || crcCode.isEmpty()
                        || cardExp.isEmpty() || cardAddress.isEmpty()
                        || cardCity.isEmpty() || cardState.isEmpty()
                        || cardZipcode.isEmpty();
            }
        }
    }

    public String finishOrder() {
        if (completeForm) {
            // add a new card if needed
            if (!payAtStore && !useExistingCard && userSession.getAccount() != null) {
                CreditCardProvider.addCardToAccount(userSession.getAccount().id,
                        new CreditCard(cardNum, userSession.getAccount().id,
                                Integer.valueOf(crcCode), cardAddress,
                                parseDate(cardExp)));
            }
            // create order and bill
            shoppingCart.clearCart();
            return NavController.CUS_HOME;
        }
        return "";
    }

    private Date parseDate(String dateString) {
        String[] dateFields = dateString.split("/");
        int month = Integer.valueOf(dateFields[0]);
        int year = Integer.valueOf(dateFields[1]);
        return Date.valueOf(year + "-" + month + "-" + 1);
    }

    public String payAtStoreDeliveryPerson() {
        return pickUp ? "Pay At Store" : "Pay Delivery Person";
    }

    public void setShoppingCart(ShoppingCartController shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void validateInputs(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        email = emailInput.getLocalValue().toString().replaceAll(WHITE_SPACE, "");
        phoneNumber = ((String) value).replaceAll(WHITE_SPACE, "");
        address = addressInput.getLocalValue().toString().replaceAll(WHITE_SPACE, "");
        city = cityInput.getLocalValue().toString().replaceAll(WHITE_SPACE, "");
        state = stateInput.getLocalValue().toString().replaceAll(WHITE_SPACE, "");
        zipcode = zipInput.getLocalValue().toString().replaceAll(WHITE_SPACE, "");
        cardNum = cardNumInput.getLocalValue().toString().replaceAll(WHITE_SPACE, "");
        crcCode = crcInput.getLocalValue().toString().replaceAll(WHITE_SPACE, "");
        cardExp = expInput.getLocalValue().toString().replaceAll(WHITE_SPACE, "");
        cardAddress = cardAddressInput.getLocalValue().toString().replaceAll(WHITE_SPACE, "");
        cardCity = cardCityInput.getLocalValue().toString().replaceAll(WHITE_SPACE, "");
        cardState = cardStateInput.getLocalValue().toString().replaceAll(WHITE_SPACE, "");
        cardZipcode = cardZipInput.getLocalValue().toString().replaceAll(WHITE_SPACE, "");

        if (!finishDisabled()) {

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

            if (phoneNumber.isEmpty()) {
                FacesMessage msg = new FacesMessage(PHONE_EMPTY_ERROR, FAILED_SIGNUP);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }

            if (!phoneNumber.matches(NUMBER_REGEX)) {
                FacesMessage msg = new FacesMessage(PHONE_INVALID_ERROR, FAILED_SIGNUP);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }

            if (!pickUp) {
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

                if (zipcode.isEmpty()) {
                    FacesMessage msg = new FacesMessage(ZIP_EMPTY_ERROR, FAILED_SIGNUP);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }

                if (!zipcode.matches(NUMBER_REGEX)) {
                    FacesMessage msg = new FacesMessage(ZIP_INVALID_ERROR, FAILED_SIGNUP);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }
            }

            if (!payAtStore && !useExistingCard) {
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

                if (cardExp.split("/").length != 2) {
                    FacesMessage msg = new FacesMessage(DATE_FORMAT_ERROR, FAILED_SIGNUP);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }

                try {
                    parseDate(cardExp);
                }
                catch (Exception e) {
                    FacesMessage msg = new FacesMessage(DATE_FORMAT_ERROR, FAILED_SIGNUP);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }

                if (crcCode.isEmpty()) {
                    FacesMessage msg = new FacesMessage(CRC_EMPTY_ERROR, FAILED_SIGNUP);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }

                if (!crcCode.matches(NUMBER_REGEX)) {
                    FacesMessage msg = new FacesMessage(CRC_INVALID_ERROR, FAILED_SIGNUP);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }

                if (cardAddress.isEmpty()) {
                    FacesMessage msg = new FacesMessage(ADDRESS_EMPTY_ERROR, FAILED_SIGNUP);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }

                if (cardCity.isEmpty()) {
                    FacesMessage msg = new FacesMessage(CITY_EMPTY_ERROR, FAILED_SIGNUP);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }

                if (cardState.isEmpty()) {
                    FacesMessage msg = new FacesMessage(STATE_EMPTY_ERROR, FAILED_SIGNUP);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }

                if (cardZipcode.isEmpty()) {
                    FacesMessage msg = new FacesMessage(ZIP_EMPTY_ERROR, FAILED_SIGNUP);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }

                if (!cardZipcode.matches(NUMBER_REGEX)) {
                    FacesMessage msg = new FacesMessage(ZIP_INVALID_ERROR, FAILED_SIGNUP);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }
            }
            completeForm = true;
        }
    }

    public boolean isPickUp() {
        return pickUp;
    }

    public void setPickUp(boolean pickUp) {
        this.pickUp = pickUp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public boolean isPayAtStore() {
        return payAtStore;
    }

    public void setPayAtStore(boolean payAtStore) {
        this.payAtStore = payAtStore;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(String crcCode) {
        this.crcCode = crcCode;
    }

    public String getCardExp() {
        return cardExp;
    }

    public void setCardExp(String cardExp) {
        this.cardExp = cardExp;
    }

    public boolean isUseExistingCard() {
        return useExistingCard;
    }

    public void setUseExistingCard(boolean useExistingCard) {
        this.useExistingCard = useExistingCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCardAddress() {
        return cardAddress;
    }

    public void setCardAddress(String cardAddress) {
        this.cardAddress = cardAddress;
    }

    public String getCardCity() {
        return cardCity;
    }

    public void setCardCity(String cardCity) {
        this.cardCity = cardCity;
    }

    public String getCardState() {
        return cardState;
    }

    public void setCardState(String cardState) {
        this.cardState = cardState;
    }

    public String getCardZipcode() {
        return cardZipcode;
    }

    public void setCardZipcode(String cardZipcode) {
        this.cardZipcode = cardZipcode;
    }

    public UIInput getEmailInput() {
        return emailInput;
    }

    public void setEmailInput(UIInput emailInput) {
        this.emailInput = emailInput;
    }

    public UIInput getPhoneNumberInput() {
        return phoneNumberInput;
    }

    public void setPhoneNumberInput(UIInput phoneNumberInput) {
        this.phoneNumberInput = phoneNumberInput;
    }

    public UIInput getAddressInput() {
        return addressInput;
    }

    public void setAddressInput(UIInput addressInput) {
        this.addressInput = addressInput;
    }

    public UIInput getCityInput() {
        return cityInput;
    }

    public void setCityInput(UIInput cityInput) {
        this.cityInput = cityInput;
    }

    public UIInput getStateInput() {
        return stateInput;
    }

    public void setStateInput(UIInput stateInput) {
        this.stateInput = stateInput;
    }

    public UIInput getZipInput() {
        return zipInput;
    }

    public void setZipInput(UIInput zipInput) {
        this.zipInput = zipInput;
    }

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

    public UIInput getCrcInput() {
        return crcInput;
    }

    public void setCrcInput(UIInput crcInput) {
        this.crcInput = crcInput;
    }

    public UIInput getCardAddressInput() {
        return cardAddressInput;
    }

    public void setCardAddressInput(UIInput cardAddressInput) {
        this.cardAddressInput = cardAddressInput;
    }

    public UIInput getCardCityInput() {
        return cardCityInput;
    }

    public void setCardCityInput(UIInput cardCityInput) {
        this.cardCityInput = cardCityInput;
    }

    public UIInput getCardStateInput() {
        return cardStateInput;
    }

    public void setCardStateInput(UIInput cardStateInput) {
        this.cardStateInput = cardStateInput;
    }

    public UIInput getCardZipInput() {
        return cardZipInput;
    }

    public void setCardZipInput(UIInput cardZipInput) {
        this.cardZipInput = cardZipInput;
    }

    public Session getUserSession() {
        return userSession;
    }

    public void setUserSession(Session userSession) {
        this.userSession = userSession;
    }

    public String getSelectedCreditCard() {
        return selectedCreditCard;
    }

    public void setSelectedCreditCard(String selectedCreditCard) {
        this.selectedCreditCard = selectedCreditCard;
    }

}
