package uno.Player;

import uno.Cards.Card;
import uno.Game;
import uno.Pile;

public abstract class Player {
    public String name;
    public Hand hand;
    public int point;

    // Konstruktor
    public Player(String name) {
        this.name = name;
    }

    // Getter & Setter & abstrakte Methoden

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public int getPoint() {
        return point;
    }

    public abstract void setPoint(int point);

    public abstract void drawCardInHand();

    public abstract String drawCard(String pickedColor);


    public abstract void removeCardFromHand();

    public abstract boolean playCard(String playCard, String pickedColor);

    public abstract void getPenaltyCard();

    public abstract void getPlusTwoCards();

    public abstract boolean playsMatchingCard(Card card, String pickedColor);

    public abstract boolean handIsEmpty();

    public abstract int countCardsInHand();

    public abstract String inputData(String pickedColor);

    public abstract boolean didYouSayUno(String cardInput);

    public abstract String sayUno(String cardInput);

    public abstract String pickColor();

    public abstract boolean challenge(boolean rightOrWrong);

    public abstract boolean compareHandWithPile();
    @Override
    public String toString() {
        return name;
    }



}
