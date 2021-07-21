package uno.Player;

import uno.Cards.Card;
import uno.Game;

import java.util.ArrayList;
import java.util.Scanner;

public class PlayerHuman extends Player {

    //Konstruktor
    public PlayerHuman(String name) {
        super(name);
        this.hand = new Hand();
        this.point = 0;
    }// PlayerHuman

    // Getter & Setter

    public String getName() {
        return name;
    }// getName

    public Hand getHand() {
        return hand;
    }// getHand

    public int getPoint() {
        return point;
    }// getPoint

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

    // Karte wird gezogen und angesehen
    @Override
    public String drawCard(String pickedColor) {
        String playOrNot;
        Card drawnCard = Game.drawPile.lookAtTopCard();
        System.out.println("Gezogene Karte: " + drawnCard);
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("Möchtest du diese Karte spielen? j/n");
            playOrNot = input.nextLine();
            if (playOrNot.contains("j")) {
                if (playsMatchingCard(drawnCard, pickedColor)) {
                    Game.discardPile.push(Game.drawPile.pop());
                    if(Game.drawPile.isEmpty()){
                        Game.renewDrawPile();
                    }
                    return drawnCard + playOrNot;
                }
                else {
                    drawCardInHand();
                    getPenaltyCard();
                    return null;
                }
            } else if (playOrNot.equals("n")) {
                drawCardInHand();
                return null;
            } else
                System.out.println("Diese Eingabe ist nicht gültig");
        } while (true);
    } //drawCard

    @Override
    public void removeCardFromHand() {
    }// removeCardFromHand

    // Karte wird gespielt
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
                } else {
                    getPenaltyCard();
                    return true;
                }
            }
        }
        System.out.println("Dies ist keine gültige Karte!");
        return false;
    }// playCard

    // * * * ANFORDERUNGEN PUNKT 15 * * *
    // * * * ANFORDERUNGEN PUNKT 39 * * *
    // Strafkarte wird gezogen
    @Override
    public void getPenaltyCard() {
        drawCardInHand();
        System.out.println("Hallo " + name + "! Du hast eine falsche Karte gespielt, du bekommst 1 Strafkarte");
        System.out.println("Der nächste Spieler ist an der Reihe!");
    }// getPenaltyCard

    // zwei Strafkarten werden gezogen
    @Override
    public void getPlusTwoCards() {
        drawCardInHand();
        drawCardInHand();
    }// getPlusTwoCards

    // * * * ANFORDERUNGEN PUNKT 12 * * *
    // * * * ANFORDERUNGEN PUNKT 21 * * *
    // * * * ANFORDERUNGEN PUNKT 24 * * *
    // * * * ANFORDERUNGEN PUNKT 27 * * *
    // * * * ANFORDERUNGEN PUNKT 30 * * *
    // ist die vom Spieler ausgewählte Karte spielbar?
    @Override
    public boolean playsMatchingCard(Card card, String pickedColor) {
        Card topCard = Game.discardPile.lookAtTopCard();
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
    }// playsMatchingCard

    // zeigt an, ob die Hand leer ist oder nicht
    @Override

    public boolean handIsEmpty() {
        if (hand.getHandSize() == 0) {
            return true;
        } else {
            return false;
        }
    }// handIsEmpty

    // zählt die aktuellen Karten einer Hand
    public int countCardsInHand() {
        return hand.getHandSize();
    }// countCardsInHand

    // * * * ANFORDERUNGEN PUNKT 48 * * *
    // ermöglicht den Spieler-Input
    public String inputData(String pickedColor) {
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }// inputData

    // prüft, ob Uno gesagt wurde
    public boolean didYouSayUno(String cardInput) {
        if (cardInput.contains("uno")) {
            return true;
        } else {
            return false;
        }
    }// didYouSayUno

    // * * * ANFORDERUNGEN PUNKT 17 * * *
    // ermöglicht die Eingabe von Uno
    public String sayUno(String cardInput) {
        return cardInput;
    }// sayUno

    // Spieler sucht sich eine Farbe aus
    public String pickColor() {
        Scanner inputColor = new Scanner(System.in);
        String colorInput = null;
        boolean pickedColor = false;
        while (pickedColor == false) {
            colorInput = inputColor.next();
            if (colorInput.equals("Y")) {
                System.out.println(name + " hat die Farbe " + colorInput + " gewählt");
                pickedColor = true;
            } else if (colorInput.equals("G")) {
                System.out.println(name + " hat die Farbe " + colorInput + " gewählt");
                pickedColor = true;
            } else if (colorInput.equals("B")) {
                System.out.println(name + " hat die Farbe " + colorInput + " gewählt");
                pickedColor = true;
            } else if (colorInput.equals("R")) {
                System.out.println(name + " hat die Farbe " + colorInput + " gewählt");
                pickedColor = true;
            } else {
                System.out.println("Diese Eingabe ist nicht gültig");
                continue;
            }
        }
        return colorInput;
    }// pickColor

    // * * * ANFORDERUNGEN PUNKT 33 * * *
    // * * * ANFORDERUNGEN PUNKT 35 * * *
    // * * * ANFORDERUNGEN PUNKT 40 * * *
    // * * * ANFORDERUNGEN PUNKT 42 * * *
    // Herausfordern bei +4
    public boolean challenge(boolean rightOrWrong) {
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("Hallo " + name + "! Es wurde eine +4 gespielt. Möchtest du den Vorgänger herausfordern? j/n");
            String yesOrNo = input.next();
            if (yesOrNo.equals("j")) {
                System.out.println(name +" fordert den Vorgänger heraus.");
                Card card = Game.discardPile.pop();
                System.out.println("Karten in der Hand des Vorgängers: " + Game.showCards + " | Karte am Tisch: " + Game.discardPile.lookAtTopCard());
                Game.discardPile.push(card);
                if (!rightOrWrong) {
                    System.out.println("Du hattest unrecht. Du musst sechs Karten ziehen.");
                    getPlusTwoCards();
                    getPlusTwoCards();
                    getPlusTwoCards();
                    return false;
                } else {
                    System.out.println("Du hattest recht. Dein Vorgänger muss die vier Karten ziehen.");
                    return true;
                }
            } else if (yesOrNo.equals("n")) {
                getPlusTwoCards();
                getPlusTwoCards();
                System.out.println("_________________________________");
                System.out.println("Hi " + name + "! Du willst den Vorgänger nicht herausfordern. Du musst vier Karten ziehen. Der nächste Spieler ist an der Reihe.");
                return false;
            } else
                System.out.println("Bitte Eingabe wiederholen.");
        } while (true);
    }// challenge

    // * * * ANFORDERUNGEN PUNKT 41 * * *
    // Zeigt, ob der Herausforderer bei der +4 Karte Recht hatte oder nicht
    public boolean compareHandWithPile() {
        Card fcard = Game.discardPile.pop();
        Card topCard = Game.discardPile.lookAtTopCard();
        Game.discardPile.push(fcard);
        Game.showCards = (ArrayList<Card>) hand.cardsInHand.clone();
        if(topCard.getColor().getCaption().equals("W"))
            return false;
        for (Card card: hand.cardsInHand) {
            if(card.getColor().getCaption().equals(topCard.getColor().getCaption()) ){
                return true;
            }
        }
        return false;
        }// compareHandWithPile


    @Override
    public String toString() {
        return name;
    }// toString

}


