package uno.Player;

import uno.Cards.Card;

import java.util.ArrayList;

public class Hand{

    ArrayList<Card> cardsInHand = new ArrayList<>();

    public void add(final Card card) {
        cardsInHand.add(card);
    }
    public void remove(final Card card) {
        cardsInHand.remove(card);
    }
    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public int getHandSize(){
        return cardsInHand.size();
    }




    @Override
    public String toString() {
        return " " + cardsInHand;
    }


}

// hand hat nur mehr eine Karte drinnen
