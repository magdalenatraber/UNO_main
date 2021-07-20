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

    public abstract void drawCardInHand(final Pile drawPile);

    public abstract String drawCard(Pile drawPile, Pile discardPile, String pickedColor);


    public abstract void removeCardFromHand();

    public abstract boolean playCard(Pile discardPile, Pile drawPile, String playCard, String pickedColor);

    public abstract void getPenaltyCard(Pile drawPile);

    public abstract void getPlusTwoCards(Pile drawPile);

    public abstract boolean playsMatchingCard(Pile discardPile, Card card, String pickedColor);

    public abstract boolean handIsEmpty();

    public abstract int countCardsInHand();

    public abstract String inputData(Pile discardPile, String pickedColor);

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
