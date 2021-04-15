package uno;

import uno.Cards.Card;

import java.util.ArrayList;

public class Hand {
    ArrayList<Card> cardsInHand = new ArrayList<>();


    @Override
    public String toString() {
        return "cardsInHand=" + cardsInHand;
    }
}

// hand hat nur mehr eine Karte drinnen
