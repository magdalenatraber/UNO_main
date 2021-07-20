package uno.Cards;

// * * * ANFORDERUNGEN PUNKT 44 * * *
public enum CardType {
    SKIP(2, "<S>", 20),
    REVERSE(2, "<->", 20),
    DRAW2(2, "+2", 20),
    WILD(4, "W", 50),
    WILD_DRAW_4(4, "+4", 50),
    DIGIT_ZERO(1, "0", 0),
    DIGIT_ONE(2, "1", 1),
    DIGIT_TWO(2, "2", 2),
    DIGIT_THREE(2, "3", 3),
    DIGIT_FOUR(2, "4", 4),
    DIGIT_FIVE(2, "5", 5),
    DIGIT_SIX(2, "6", 6),
    DIGIT_SEVEN(2, "7", 7),
    DIGIT_EIGHT(2, "8", 8),
    DIGIT_NINE(2, "9", 9);


    public static final CardType[] cardType = CardType.values();
    public final int numInDeck;
    private final String caption;
    public final int value;

    CardType(final int numInDeck, final String caption, final int value) {
        this.numInDeck = numInDeck;
        this.caption = caption;
        this.value = value;
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

    public int getValue() {
        return value;
    }
}