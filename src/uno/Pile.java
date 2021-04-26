package uno;

import uno.Cards.Card;
import uno.Cards.CardColor;
import uno.Cards.CardType;

import java.util.ArrayList;
import java.util.Stack;

public class Pile {
    public Stack<Card> cards = new Stack<>();
    public Pile(Stack<Card> cards) {
        this.cards = cards;
    }

    // creates deck out of 108 single cards
    public Stack<Card> generateDeck(CardColor[] colors, CardType[] cardTypes) {

        Stack<Card> cards = new Stack<>();
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

    public Stack<Card> getCards() {
        return cards;
    }


}
