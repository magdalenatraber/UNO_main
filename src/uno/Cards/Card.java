package uno.Cards;

public class Card {

    private CardColor color;
    private CardType type;

    public Card(CardColor color, CardType type){
        this.color = color;
        this.type = type;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color.getCaption() + type.getCaption();
    }
}
