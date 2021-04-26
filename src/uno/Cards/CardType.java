package uno.Cards;

public enum CardType {
    SKIP(2, "<S>"),
    REVERSE(2, "<->"),
    DRAW2(2, "+2"),
    WILD(4, "W"),
    WILD_DRAW_4(4, "+4"),
    DIGIT_ZERO(1, "0"),
    DIGIT_ONE(2, "1"),
    DIGIT_TWO(2, "2"),
    DIGIT_THREE(2, "3"),
    DIGIT_FOUR(2, "4"),
    DIGIT_FIVE(2, "5"),
    DIGIT_SIX(2, "6"),
    DIGIT_SEVEN(2, "7"),
    DIGIT_EIGHT(2, "8"),
    DIGIT_NINE(2, "9");


    public static final CardType[] cardType = CardType.values();
    public final int numInDeck;
    private final String caption;

    CardType(final int numInDeck, final String caption) {
        this.numInDeck = numInDeck;
        this.caption = caption;
    }

    public static CardType getCardType(int i) {
        return CardType.cardType[i];
    }

    public int getNumInDeck()
    {
        return numInDeck;
    }

    public String getCaption() {
        return caption;
    }

}