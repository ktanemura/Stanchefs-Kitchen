package com.stanchefskitchen.presentation.customer;

import com.stanchefskitchen.data.models.CreditCard;
import com.stanchefskitchen.data.providers.CreditCardProvider;
import com.stanchefskitchen.presentation.login.Session;
import java.util.List;

/**
 *
 * @author Tyler Wong
 */
public class CreditCardController {
    private Session userSession;
    public List<CreditCard> creditCards = CreditCardProvider.getCardsByAccountId(userSession.getAccount().id);
    
    public List<CreditCard> getCreditCards() {
        return creditCards;
    }
    
    public String deleteCard(CreditCard card) {
        CreditCardProvider.removeCardFromAccount(card.getNumber());
        return null;
    }
}
