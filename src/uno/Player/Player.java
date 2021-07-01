package uno.Player;

import uno.Cards.Card;
import uno.Pile;

public abstract class Player {
    public String name;
    public Hand hand;
    public int point;

    public Player(String name) {
        this.name = name;

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

    public abstract void drawCardInHand(final Pile drawPile);

    public abstract String drawCard(Pile drawPile, Pile discardPile, String pickedColor);

    public abstract void removeCardFromHand();

    public abstract boolean playCard(Pile discardPile, Pile drawPile, String playCard, String pickedColor);

    public abstract void getPenaltyCard(Pile drawPile);

    public abstract void getPlusTwoCards(Pile drawPile);

    public abstract boolean playsMatchingCard(Pile discardPile, Card card, String pickedColor);

    public abstract boolean handIsEmpty();

    @Override
    public String toString() {
        return name;
    }
}
