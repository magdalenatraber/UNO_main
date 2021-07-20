package uno.Player;

import uno.Cards.Card;
import uno.Cards.CardComparator;

import java.util.ArrayList;
import java.util.Collections;

public class Hand{

    ArrayList<Card> cardsInHand = new ArrayList<>();

    // fügt eine neue Karte zur Hand hinzu
    public void add(final Card card) {
        cardsInHand.add(card);
        Collections.sort(cardsInHand,new CardComparator());
    }//add

    // entfernt eine Karte aus der Hand
    public void remove(final Card card) {
        cardsInHand.remove(card);
    }// remove

    // zeigt die aktuellen Karten einer Hand an
    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }// getCardsInHand

    // zeigt die aktuelle Anzahl der Karten einer Hand an
    public int getHandSize(){
        return cardsInHand.size();
    }// getHandSize

    // zeigt die aktuellen Punkte der aktuellen Karten einer Hand an
    public int getHandPoints() {
        int points = 0;
        for (Card card : cardsInHand) {
            points += card.getType().getValue();
        }
        return points;
    }// getHandPoints

    @Override
    public String toString() {
        return " " + cardsInHand;
    }


}

