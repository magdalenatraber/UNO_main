package uno.Player;

import uno.Cards.Card;
import uno.Game;
import uno.Pile;

import java.util.Scanner;

public class PlayerHuman extends Player {

    public PlayerHuman(String name) {
        super(name);
        this.hand = new Hand();
        this.point = 0;
    }

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
    public void drawCardInHand(final Pile drawPile) {
        final var card = drawPile.pop();
        hand.add(card);
    }//drawCardInHand

    // Karte wird gezogen und angesehen
    @Override
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

    @Override
    public void removeCardFromHand() {
    }// removeCardFromHand

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
    }// playCard

    @Override
    public void getPenaltyCard(Pile drawPile) {
        drawCardInHand(drawPile);
        System.out.println("Du hast eine falsche Karte gespielt, du bekommst 1 Strafkarte");
        System.out.println("Der nächste Spieler ist an der Reihe!");
    }// getPenaltyCard

    @Override
    public void getPlusTwoCards(Pile drawPile) {
        drawCardInHand(drawPile);
        drawCardInHand(drawPile);
    }// getPlusTwoCards

    // ist die vom Spieler ausgewählte Karte spielbar?
    @Override
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
    }// playsMatchingCard

    @Override
    public boolean handIsEmpty() {
        if (hand.getHandSize() == 0) {
            return true;
        } else {
            return false;
        }
    }// handIsEmpty

    public int countCardsInHand() {
        return hand.getHandSize();
    }// countCardsInHand

    public String inputData(Pile discardPile, String pickedColor) {
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }// inputData

    public boolean didYouSayUno(String cardInput) {
        if (cardInput.contains("uno")) {
            System.out.println("Hat Uno gesagt");
            return true;
        } else {
            return false;
        }
    }// didYouSayUno

    public String sayUno(String cardInput) {
        return cardInput;
    }// sayUno

    public String pickColor() {
        Scanner inputColor = new Scanner(System.in);
        String colorInput = null;
        boolean pickedColor = false;
        while (pickedColor == false) {
            colorInput = inputColor.next();
            if (colorInput.equals("Y")) {
                System.out.println(name + "hat die Farbe " + colorInput + " gewählt");
                pickedColor = true;
            } else if (colorInput.equals("G")) {
                System.out.println(name + "hat die Farbe " + colorInput + " gewählt");
                pickedColor = true;
            } else if (colorInput.equals("B")) {
                System.out.println(name + "hat die Farbe " + colorInput + " gewählt");
                pickedColor = true;
            } else if (colorInput.equals("R")) {
                System.out.println(name + "hat die Farbe " + colorInput + " gewählt");
                pickedColor = true;
            } else {
                System.out.println("Diese Eingabe ist nicht gültig");
                continue;
            }
        }
        return colorInput;
    }// pickColor

// Herausfordern bei +4
  public boolean challenge(boolean rightOrWrong){
        Scanner input = new Scanner(System.in);
String yesOrNo = input.next();
        while(true) {
            System.out.println("Es wurde eine +4 gespielt. Möchtest du den Spieler herausfordern? j/n");
            if(yesOrNo.equals("j")) {
                System.out.println("Du forderst den Vorgänger heraus");
                if (!rightOrWrong) {
                    System.out.println("Du hattest unrecht.Du musst sechs Karten ziehen");
                    getPlusTwoCards(Game.drawPile);
                    getPlusTwoCards(Game.drawPile);
                    getPlusTwoCards(Game.drawPile);
                    return false;
                } else{
                        System.out.println("Du hattest recht. Dein Vorgänger muss die vier Karten ziehen");
                        return true;
                    }
            }else if (yesOrNo.equals("n")){
                getPlusTwoCards(Game.drawPile);
                getPlusTwoCards(Game.drawPile);
                System.out.println("_________________________________");
                System.out.println("Hi " + name + "! Du willst den Vorgänger nicht herausfordern. Du musst vier Karten ziehen. Der nächste Spieler ist an der Reihe");
                return false;
            }
            else
                System.out.println("Bitte Eingabe wiederholen");
        }
    }
public boolean compareHandWithPile(){
        Card topCard = Game.discardPile.lookAtTopCard();
    for (Card card: hand.cardsInHand) {
        if(card.getColor().getCaption().equals(topCard.getColor().getCaption()) ){
            return true;
        }
    }
    return false;
}




    @Override
    public String toString() {
        return name;
    }// toString

}


