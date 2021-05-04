package uno;

import uno.Cards.Card;

import java.util.ArrayList;

public class Player {
    private String name;
    private Hand hand = new Hand();
    private int point;

    public Player(String name) {
        this.name = name;
        this.point = 0;
    }


    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public int getPoint() {
        return point;
    }

    public void drawCard(final Pile drawPile) {
        final var card = drawPile.pop();
        hand.add(card);
    }

    public void removeCardFromHand(){
    }


    public boolean playCard(Pile discardpile, String playcard) {

        for (Card card : hand.cardsInHand) {
            if (card.toString().equals(playcard)) {
                hand.remove(card);
                discardpile.push(card);
                return true;
            }
        }
        System.out.println("Dies ist keine gültige Karte!");
        return false;
    }
    public boolean handIsEmpty(){
        if(hand.getHandSize() == 0)
            return true;
        else
            return false;
    }
    @Override
    public String toString() {
        return name;
    }
}

//karte heben
//karte spielen
//karten zeigen
