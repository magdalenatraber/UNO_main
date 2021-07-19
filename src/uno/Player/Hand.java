package uno.Player;

import uno.Cards.Card;
import uno.Cards.CardComparator;

import java.util.ArrayList;
import java.util.Collections;

public class Hand{

    ArrayList<Card> cardsInHand = new ArrayList<>();

    public void add(final Card card) {

        cardsInHand.add(card);
        Collections.sort(cardsInHand,new CardComparator());
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

    public int getHandPoints() {
        int points = 0;
        for (Card card : cardsInHand) {
            points += card.getType().getValue();
        }
        return points;
    }

    @Override
    public String toString() {
        return " " + cardsInHand;
    }


}

// hand hat nur mehr eine Karte drinnen
