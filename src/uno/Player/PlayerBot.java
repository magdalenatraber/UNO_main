package uno.Player;

import uno.Cards.Card;
import uno.Game;
import uno.Pile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class PlayerBot extends Player {

    public PlayerBot(String name) {
        super(name);
        this.hand = new Hand();
        this.point = 0;
    }

    @Override
    public void setPoint(int point) {
        this.point = point;
    }

    // Karten werden in die Hand gezogen
    @Override
    public void drawCardInHand(final Pile drawPile) {
        final var card = drawPile.pop();
        hand.add(card);
        if (Game.drawPile.isEmpty())
            Game.renewDrawPile();
    }//drawCardInHand

    //Bot reagiert auf gezogene Karte
    @Override
    public String drawCard(Pile drawPile, Pile discardPile, String pickedColor) {
        Card drawnCard = drawPile.lookAtTopCard();
        System.out.println("Gezogene Karte:" + drawnCard);

        System.out.println("Möchtest du diese Karte spielen? j/n");

        if (playsMatchingCard(discardPile, drawnCard, pickedColor)) {
            discardPile.push(Game.drawPile.pop());
            if (Game.drawPile.isEmpty()) {
                Game.renewDrawPile();
            }
            System.out.println("Ich spiele " + drawnCard);
            return drawnCard.toString();
        } else if (!playsMatchingCard(discardPile, drawnCard, pickedColor)) {
            drawCardInHand(Game.drawPile);
            System.out.println("Ich kann nicht spielen.");
            return null;
        }
        return null;
    } //drawCard

    @Override
    public void removeCardFromHand() {
    }

    //Bot spielt eine Karte
    @Override
    public boolean playCard(Pile discardPile, Pile drawPile, String playCard, String pickedColor) {
        for (Card card : hand.cardsInHand) {
            if (playCard.contains(card.toString())) {
                // Spielregel Methoden
                if (playsMatchingCard(discardPile, card, pickedColor)) {
                    hand.remove(card);
                    discardPile.push(card);
                    return true;
                // wenn falsche Karte gespielt
                } else {
                    getPenaltyCard(drawPile);
                    return true;
                }
            }
        }
        System.out.println("Dies ist keine gültige Karte!");
        return false;
    }// playCard

    // wenn falsche Karte gespielt wird
    @Override
    public void getPenaltyCard(Pile drawPile) {
        drawCardInHand(drawPile);
        System.out.println("Du hast eine falsche Karte gespielt, du bekommst 1 Strafkarte");
        System.out.println("Der nächste Spieler ist an der Reihe!");
    }// getPenaltyCard

    // ziehe 2 Strafkarten
    @Override
    public void getPlusTwoCards(Pile drawPile) {
        drawCardInHand(drawPile);
        drawCardInHand(drawPile);
    }// getPlusTwoCards

    // prüft, ob die gespielte Karte gültig ist
    @Override
    public boolean playsMatchingCard(Pile discardPile, Card card, String pickedColor) {
        Card topCard = discardPile.lookAtTopCard();
        if (topCard.getColor() == card.getColor() || topCard.getType() == card.getType()) {
            return true;
        } else if (topCard.getColor().getCaption().equals("W") && card.getColor().getCaption().equals(pickedColor)) {
            return true;
        } else if (card.getColor().getCaption().equals("W")) {
            return true;
        } else {
            return false;
        }
    }// playsMatchingCard

    // zeigt, ob die Hand leer ist oder nicht
    @Override
    public boolean handIsEmpty() {
        if (hand.getHandSize() == 0) {
            return true;
        } else {
            return false;
        }
    }// handIsEmpty

    // zählt die Karten in der Hand
    public int countCardsInHand() {
        return hand.getHandSize();
    }//countCardsInHand

    // der Bot zieht eine Karte
    public String inputData(Pile discardPile, String pickedColor) {
        String input = "ziehen";
        for (Card card : hand.cardsInHand) {
            if (playsMatchingCard(discardPile, card, pickedColor)) {
                input = card.toString();
                break;
            }
        }
        return input;
    }// inputData

    // prüft, ob Uno gesagt wurde
    public boolean didYouSayUno(String cardInput) {
        if (cardInput.contains("uno")) {
            return true;
        } else {
            return false;
        }
    }//didYouSayUno

    // Bot sagt Uno
    public String sayUno(String cardInput) {
        return "uno";
    }// sayUno

    // Bot sucht sich eine Farbe aus
    public String pickColor() {
        String colorInput = generateRandomColor();
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
    }// pickColor

    // wählt eine Farbe per Zufall aus
    public static String generateRandomColor() {
        int len = 1;
        String chars = "BRGY";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }//generateRandomColor

    //Entscheidet, ob der Bot den Spieler bei einer +4 Karte herausfordert
    public boolean challenge(boolean rightOrWrong) {
        System.out.println("Es wurde eine +4 gespielt. Möchtest du den Spieler herausfordern?");
        if (rightOrWrong) {
            Card card = Game.discardPile.pop();
            System.out.println("Karten in der Hand des Vorgängers: " + Game.showCards + "Karte am Tisch: " + Game.discardPile.lookAtTopCard());
            Game.discardPile.push(card);
            System.out.println("Du hattest recht. Dein Vorgänger muss die vier Karten ziehen.");
            return true;
        } else {
            getPlusTwoCards(Game.drawPile);
            getPlusTwoCards(Game.drawPile);
            System.out.println("_________________________________");
            System.out.println("Hi " + name + "! Du willst den Vorgänger nicht herausfordern. Du musst vier Karten ziehen. Der nächste Spieler ist an der Reihe");
            return false;
        }
    }// challenge

    // zeigt dem Bot, ob die vor ihm gespielte +4 Karte gerechtfertigt war oder nicht, um gegebenenfalls herausfordern zu können
    public boolean compareHandWithPile() {
        Card fcard = Game.discardPile.pop();
        Card topCard = Game.discardPile.lookAtTopCard();
        Game.discardPile.push(fcard);
        Game.showCards = (ArrayList<Card>) hand.cardsInHand.clone();
        if (topCard.getColor().getCaption().equals("W"))
            return false;
        for (Card card : hand.cardsInHand) {
            if (card.getColor().getCaption().equals(topCard.getColor().getCaption())) {
                return true;
            }
        }
        return false;
    }// compareHandWithPile


    @Override
    public String toString() {
        return name;
    }

}
