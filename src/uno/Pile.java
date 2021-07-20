package uno;

import uno.Cards.Card;
import uno.Cards.CardColor;
import uno.Cards.CardType;

import java.util.Collections;
import java.util.Stack;

public class Pile {
    public Stack<Card> cards;

    public Pile() {
        this.cards = new Stack<>();
    }

    // Erstellt das Kartendeck mit 108 Karten
    public void generateDeck(CardColor[] colors, CardType[] cardTypes) {

        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < cardTypes.length; j++) {

                if (colors[i].equals(CardColor.BLACK) && cardTypes[j].equals(CardType.WILD)) {
                    generateCard(colors[i], cardTypes[j], cardTypes[j].getNumInDeck());

                } else if (colors[i].equals(CardColor.BLACK) && cardTypes[j].equals(CardType.WILD_DRAW_4)) {
                    generateCard(colors[i], cardTypes[j], cardTypes[j].getNumInDeck());

                } else if (!colors[i].equals(CardColor.BLACK) && (!cardTypes[j].equals(CardType.WILD) && !cardTypes[j].equals(CardType.WILD_DRAW_4))) {
                    generateCard(colors[i], cardTypes[j], cardTypes[j].getNumInDeck());
                }
            }
        }
    }// generateDeck

    // Erzeugt eine einzelne Karte
    public Card generateCard(CardColor cardColor, CardType cardType, int numInDeck) {
        Card card = new Card(cardColor, cardType);
        for (int i = 0; i < numInDeck; i++) {
            card = new Card(cardColor, cardType);
            cards.add(card);
        }
        return card;
    }// generateCard

    // Karten werden gemischt
    public void shuffle() {
        Collections.shuffle(cards);
    }// shuffle

    // zeigt, ob noch Karten vorhanden sind oder nicht
    public boolean isEmpty() {
        if (cards.isEmpty())
            return true;
        else
            return false;
    }// isEmpty

    public Card pop() {
        if (cards.empty()) {
            throw new java.util.EmptyStackException();
        }
        return cards.pop();
    }// pop

    public void push(final Card card) {
        cards.push(card);
    }// push

    public Card lookAtTopCard() {
        return cards.peek();
    }// lookAtTopCard

    // Getter & Setter

    public Stack<Card> getCards() {
        return cards;
    }

    public int getSize() {
        return cards.size();
    }

    @Override
    public String toString() {
        return "cards:" + cards.toString();
    }


}
