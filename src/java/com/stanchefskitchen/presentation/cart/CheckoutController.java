package com.stanchefskitchen.presentation.cart;

import com.stanchefskitchen.presentation.NavController;

/**
 *
 * @author Brittany Berlanga
 */
public class CheckoutController {
    private ShoppingCartController shoppingCart;
    private boolean pickUp;
    private boolean payAtStore;
    private boolean useExistingCard;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String cardNum;
    private String crcCode;
    private String cardExp;
    private String email;
    private String phoneNumber;
    
    /**
     * Creates a new instance of CheckoutController
     */
    public CheckoutController() {
        this.pickUp = true;
        this.payAtStore = true;
        this.useExistingCard = true;
    }

    public ShoppingCartController getShoppingCart() {
        return shoppingCart;
    }
    
    public boolean finishOrderDisabled() {
        return false;
    }
    
    public String finishOrder() {
        shoppingCart.clearCart();
        return NavController.CUS_HOME;
    }
    
    public String payAtStoreDeliveryPerson() {
        return pickUp ? "Pay At Store" : "Pay Delivery Person";
    }

    public void setShoppingCart(ShoppingCartController shoppingCart) {
        this.shoppingCart = shoppingCart;
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
    
}
