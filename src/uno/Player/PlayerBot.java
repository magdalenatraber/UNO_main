package uno.Player;

import uno.Cards.Card;
import uno.Game;
import uno.Pile;

import java.util.Random;
import java.util.Scanner;

public class PlayerBot extends Player {

    public PlayerBot(String name) {
        super(name);
        this.hand = new Hand();
        this.point = 0;
    }

    public static String generateRandomName(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                + "lmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    // Karten werden in die Hand gezogen
    @Override
    public void drawCardInHand(final Pile drawPile) {

        final var card = drawPile.pop();
        hand.add(card);
    }//drawCardInHand

    @Override
    public String drawCard(Pile drawPile, Pile discardPile, String pickedColor) {
        String playOrNot;
        final var card = drawPile.pop();
        Card drawnCard = drawPile.lookAtTopCard();
        System.out.println("Gezogene Karte:" + drawnCard);
        Scanner input = new Scanner(System.in);

        System.out.println("Möchtest du diese Karte spielen? j/n");
        // playOrNot = input.next();
        //if (playOrNot.equals("j")) {

        if (playsMatchingCard(discardPile, drawnCard, pickedColor)) {
            discardPile.push(drawnCard);
            System.out.println("Ich spiele " + drawnCard);
            return drawnCard.toString();
        } else if (!playsMatchingCard(discardPile, drawnCard, pickedColor)) {
            hand.add(drawnCard);
            System.out.println("Ich kann nicht spielen.");
            return null;
        }

        return null;

    } //drawCard

    @Override
    public void removeCardFromHand() {
    }

    @Override
    public boolean playCard(Pile discardPile, Pile drawPile, String playCard, String pickedColor) {

        for (Card card : hand.cardsInHand) {

            if (playCard.contains(card.toString())) {

                // Spielregel Methoden
                if (playsMatchingCard(discardPile, card, pickedColor)) {
                    hand.remove(card);
                    discardPile.push(card);
                    return true;
                } else {
                    getPenaltyCard(drawPile);
                    return true;
                }
            }
        }

        System.out.println("Dies ist keine gültige Karte!");
        return false;

    }

    @Override
    public void getPenaltyCard(Pile drawPile) {
        drawCardInHand(drawPile);
        System.out.println("Du hast eine falsche Karte gespielt, du bekommst 1 Strafkarte");
        System.out.println("Der nächste Spieler ist an der Reihe!");
    }

    @Override
    public void getPlusTwoCards(Pile drawPile) {
        drawCardInHand(drawPile);
        drawCardInHand(drawPile);
    }

    @Override
    public boolean playsMatchingCard(Pile discardPile, Card card, String pickedColor) {
        Card topCard = discardPile.lookAtTopCard();
        if (topCard.getColor() == card.getColor() || topCard.getType() == card.getType()) {
            return true;
        } else if (topCard.getColor().getCaption().equals("W") && card.getColor().getCaption().equals(pickedColor)) {
            System.out.println("W* wurde gespielt. Du musst färben.");
            return true;

        } else if (card.getColor().getCaption().equals("W")) {
            System.out.println("W* wurde gespielt. Du kannst spielen was du willst.");
            return true;
        } else {

            return false;
        }
    }

    @Override
    public boolean handIsEmpty() {
        if (hand.getHandSize() == 0) {
            return true;

        } else {
            return false;
        }
    }

    public int countCardsInHand() {
        return hand.getHandSize();
    }

    public String inputData(Pile discardPile, String pickedColor) {
        String input = "ziehen";
        for (Card card : hand.cardsInHand) {
            if (playsMatchingCard(discardPile, card, pickedColor)) {
                input = card.toString();
                break;
            }
        }

        return input;
    }

    public boolean didYouSayUno(String cardInput) {
        if (cardInput.contains("uno")) {
            return true;
        } else {
            return false;
        }
    }

    public String sayUno(String cardInput){
        return "piep";
    }

    public String pickColor() {
        String colorInput = null;
        for (Card card : hand.cardsInHand) {
            if (!card.getColor().getCaption().equals("W"))
                colorInput = card.getColor().getCaption();
        }

        if (colorInput.equals("Y")) {
            System.out.println("Du hast die Farbe " + colorInput + " gewählt");

        } else if (colorInput.equals("G")) {
            System.out.println("Du hast die Farbe " + colorInput + " gewählt");

        } else if (colorInput.equals("B")) {
            System.out.println("Du hast die Farbe " + colorInput + " gewählt");

        } else if (colorInput.equals("R")) {
            System.out.println("Du hast die Farbe " + colorInput + " gewählt");

        }
        return colorInput;
    }


    @Override
    public String toString() {
        return name;
    }

}
