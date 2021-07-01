package uno.Player;

import uno.Cards.Card;
import uno.Pile;

import java.util.Scanner;

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

    // Karten werden in die Hand gezogen
    public void drawCardInHand(final Pile drawPile) {
        final var card = drawPile.pop();
        hand.add(card);
    }//drawCardInHand

    // Karte wird gezogen und angesehen
    public String drawCard(Pile drawPile, Pile discardPile, String pickedColor) {
        String playOrNot;
        final var card = drawPile.pop();
        Card drawnCard = drawPile.lookAtTopCard();
        System.out.println("Gezogene Karte:" + drawnCard);
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("Möchtest du diese Karte spielen? j/n");
            playOrNot = input.next();
            if (playOrNot.equals("j")) {


                if (playsMatchingCard(discardPile, drawnCard, pickedColor)) {
                    discardPile.push(drawnCard);
                    return drawnCard.toString();
                }

                if (!playsMatchingCard(discardPile, drawnCard, pickedColor)) {
                    hand.add(drawnCard);
                    getPenaltyCard(drawPile);
                    return null;
                }

            } else if (playOrNot.equals("n")) {

                hand.add(drawnCard);
                return null;
            } else
                System.out.println("Diese Eingabe ist nicht gültig");
        } while (true);
    } //drawCard


    public void removeCardFromHand() {
    }


    public boolean playCard(Pile discardPile, Pile drawPile, String playCard, String pickedColor) {

        for (Card card : hand.cardsInHand) {

            if (card.toString().equals(playCard)) {

                // Spielregel Methoden
                if (playsMatchingCard(discardPile, card, pickedColor)) {
                    hand.remove(card);
                    discardPile.push(card);
                    return true;
                }
                else {
                    getPenaltyCard(drawPile);
                    return true;
                }
                
            }
        }

        System.out.println("Dies ist keine gültige Karte!");
        return false;

    }

    public void getPenaltyCard(Pile drawPile) {
        drawCardInHand(drawPile);
        System.out.println("Du hast eine falsche Karte gespielt, du bekommst 1 Strafkarte");
        System.out.println("Der nächste Spieler ist an der Reihe!");
    }

    public void getPlusTwoCards(Pile drawPile) {
        drawCardInHand(drawPile);
        drawCardInHand(drawPile);
    }

    // ist die vom Spieler ausgewählte Karte spielbar?
    public boolean playsMatchingCard(Pile discardPile, Card card, String pickedColor) {
        Card topCard = discardPile.lookAtTopCard();
        if (topCard.getColor() == card.getColor() || topCard.getType() == card.getType()) {
            return true;
        } else if (card.getColor().getCaption().equals("W")) {
            return true;
        } else if (topCard.getColor().getCaption().equals("W") && card.getColor().getCaption().equals(pickedColor)) {
            return true;
        } else {
            System.out.println("Karte kann nicht gespielt werden!");
            return false;
        }
    }



    public boolean handIsEmpty() {
        if (hand.getHandSize() == 0)
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
