package uno.Cards;

public enum CardColor {
    BLUE("B"),
    GREEN("G"),
    YELLOW("Y"),
    RED("R"),
    BLACK("W");

    public static final CardColor[] colors = CardColor.values();
    private final String caption;

    CardColor(final String caption) {
        this.caption = caption;
    }

    public static CardColor getColor(int i) {
        return CardColor.colors[i];
    }

    public String getCaption() {
        return caption;
    }


} // CardColor
