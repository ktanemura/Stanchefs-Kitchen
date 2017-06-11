package com.stanchefskitchen.presentation.customer;

import com.stanchefskitchen.data.models.CreditCard;
import com.stanchefskitchen.data.providers.CreditCardProvider;
import com.stanchefskitchen.presentation.NavController;
import com.stanchefskitchen.presentation.login.Session;
import java.util.List;

/**
 *
 * @author Tyler Wong
 */
public class CreditCardController {
    private Session userSession;
    public List<CreditCard> creditCards;
    
    public List<CreditCard> getCreditCards() {
        if (creditCards == null) {
            creditCards = CreditCardProvider.getCardsByAccountId(userSession.getAccount().id);
        }
        return creditCards;
    }
    
    public String deleteCard(CreditCard card) {
        CreditCardProvider.removeCardFromAccount(card.getNumber());
        return null;
    }
    
    public Session getUserSession() {
        return userSession;
    }

    public void setUserSession(Session userSession) {
        this.userSession = userSession;
    }
    
    public String addCard() {
        return NavController.ADD_CARD;
    }
}
