package uno.Cards;

public enum CardType {
    SKIP(2, "<S>"),
    REVERSE(2, "<->"),
    DRAW2(2, "+2"),
    WILD(4, "W"),
    WILD_DRAW_4(4, "W+4"),
    DIGIT_ZERO(1, "ZERO"),
    DIGIT_ONE(2, "ONE"),
    DIGIT_TWO(2, "TWO"),
    DIGIT_THREE(2, "THREE"),
    DIGIT_FOUR(2, "FOUR"),
    DIGIT_FIVE(2, "FIVE"),
    DIGIT_SIX(2, "SIX"),
    DIGIT_SEVEN(2, "SEVEN"),
    DIGIT_EIGHT(2, "EIGHT"),
    DIGIT_NINE(2, "NINE");


    public static final CardType[] cardType = CardType.values();
    private final int numInDeck;
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