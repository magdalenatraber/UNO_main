package uno;

import uno.Cards.Card;

import java.util.ArrayList;

public class Hand {
    ArrayList<Card> cardsInHand = new ArrayList<>();

    public void add(final Card card) {
        cardsInHand.add(card);
    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public int getHandSize(){
        return cardsInHand.size();
    }

    @Override
    public String toString() {
        return "cardsInHand:" + cardsInHand;
    }
}

// hand hat nur mehr eine Karte drinnen
