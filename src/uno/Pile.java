package uno;

import uno.Cards.Card;
import uno.Cards.CardColor;
import uno.Cards.CardType;

import java.util.ArrayList;

public class Pile {
    public ArrayList<Card> cards = new ArrayList<>();
    public Pile(ArrayList<Card> cards) {
        this.cards = cards;
    }

    // creates deck out of 108 single cards
    public ArrayList<Card> generateDeck(CardColor[] colors, CardType[] cardTypes) {

        ArrayList<Card> cards = new ArrayList<>();
        Card card;
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < cardTypes.length; j++) {

                if (colors[i].equals(CardColor.BLACK) && cardTypes[j].equals(CardType.WILD)) {
                    card = generateCard(colors[i], cardTypes[j], cardTypes[j].getNumInDeck());
                    cards.add(card);
                } else if (colors[i].equals(CardColor.BLACK) && cardTypes[j].equals(CardType.WILD_DRAW_4)) {
                    card = generateCard(colors[i], cardTypes[j], cardTypes[j].getNumInDeck());
                    cards.add(card);
                } else if (!colors[i].equals(CardColor.BLACK) && (!cardTypes[j].equals(CardType.WILD) && !cardTypes[j].equals(CardType.WILD_DRAW_4))) {
                    card = generateCard(colors[i], cardTypes[j], cardTypes[j].getNumInDeck());
                    cards.add(card);
                }
            }
        }
        return cards;
    }

    // creates single card
    public Card generateCard(CardColor cardColor, CardType cardType, int numInDeck) {
        Card card = new Card(cardColor, cardType);
        for (int i = 0; i < numInDeck; i++) {
            card = new Card(cardColor, cardType);
            cards.add(card);
        }
        return card;
    }


    @Override
    public String toString() {
        return "cards=" + cards.toString();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }


}
