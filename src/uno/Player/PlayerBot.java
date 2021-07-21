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
    public void drawCardInHand() {
        final var card = Game.drawPile.pop();
        hand.add(card);
        if (Game.drawPile.isEmpty())
            Game.renewDrawPile();
    }//drawCardInHand

    //Bot reagiert auf gezogene Karte
    @Override
    public String drawCard(String pickedColor) {
        Card drawnCard = Game.drawPile.lookAtTopCard();
        System.out.println("Gezogene Karte:" + drawnCard);

        System.out.println("Möchtest du diese Karte spielen? j/n");

        if (playsMatchingCard(drawnCard, pickedColor)) {
            Game.discardPile.push(Game.drawPile.pop());
            if (Game.drawPile.isEmpty()) {
                Game.renewDrawPile();
            }
            System.out.println("Ich spiele " + drawnCard);
            return drawnCard.toString();
        } else if (!playsMatchingCard(drawnCard, pickedColor)) {
            drawCardInHand();
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
    public boolean playCard(String playCard, String pickedColor) {
        for (Card card : hand.cardsInHand) {
            if (playCard.contains(card.toString())) {
                // Spielregel Methoden
                if (playsMatchingCard(card, pickedColor)) {
                    hand.remove(card);
                    Game.discardPile.push(card);
                    System.out.println(name + " spielt " + card);
                    return true;
                // wenn falsche Karte gespielt
                } else {
                    getPenaltyCard();
                    return true;
                }
            }
        }
        System.out.println("Dies ist keine gültige Karte!");
        return false;
    }// playCard

    // * * * ANFORDERUNGEN PUNKT 39 * * *
    // wenn falsche Karte gespielt wird
    @Override
    public void getPenaltyCard() {
        drawCardInHand();
        System.out.println("Du hast eine falsche Karte gespielt, du bekommst 1 Strafkarte");
        System.out.println("Der nächste Spieler ist an der Reihe!");
    }// getPenaltyCard

    // ziehe 2 Strafkarten
    @Override
    public void getPlusTwoCards() {
        drawCardInHand();
        drawCardInHand();
    }// getPlusTwoCards

    // * * * ANFORDERUNGEN PUNKT 12 * * *
    // prüft, ob die gespielte Karte gültig ist
    @Override
    public boolean playsMatchingCard(Card card, String pickedColor) {
        Card topCard = Game.discardPile.lookAtTopCard();
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
    public String inputData(String pickedColor) {
        String input = "ziehen";
        for (Card card : hand.cardsInHand) {
            if (playsMatchingCard(card, pickedColor)) {
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

    // * * * ANFORDERUNGEN PUNKT 33 * * *
    // * * * ANFORDERUNGEN PUNKT 35 * * *
    // * * * ANFORDERUNGEN PUNKT 40 * * *
    // * * * ANFORDERUNGEN PUNKT 42 * * *
    //Entscheidet, ob der Bot den Spieler bei einer +4 Karte herausfordert
    public boolean challenge(boolean rightOrWrong) {
        System.out.println("Hallo " + name + "! Es wurde eine +4 gespielt. Möchtest du den Vorgänger herausfordern?");
        if (rightOrWrong) {
            System.out.println(name+ "fordert den Vorgänger heraus.");
            Card card = Game.discardPile.pop();
            System.out.println("Karten in der Hand des Vorgängers: " + Game.showCards + " | Karte am Tisch: " + Game.discardPile.lookAtTopCard());
            Game.discardPile.push(card);
            System.out.println("Du hattest recht. Dein Vorgänger muss die vier Karten ziehen.");
            return true;
        } else {
            getPlusTwoCards();
            getPlusTwoCards();
            System.out.println("_________________________________");
            System.out.println("Hi " + name + "! Du willst den Vorgänger nicht herausfordern. Du musst vier Karten ziehen. Der nächste Spieler ist an der Reihe");
            return false;
        }
    }// challenge

    // * * * ANFORDERUNGEN PUNKT 41 * * *
    // zeigt dem Bot, ob die vor ihm gespielte +4 Karte gerechtfertigt war oder nicht
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
