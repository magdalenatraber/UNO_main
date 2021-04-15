package uno;

public class Player {
    private String name;
    private Hand hand = new Hand();
    private int point;

    public Player(String name) {
        this.name = name;
        this.hand = hand;
        this.point = 0;
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }


    @Override
    public String toString() {
        return name;
    }
}

//karte heben
//karte spielen
//karten zeigen
