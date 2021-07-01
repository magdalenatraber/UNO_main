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

    // creates deck out of 108 single cards
    public void generateDeck(CardColor[] colors, CardType[] cardTypes) {

        Card card;
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < cardTypes.length; j++) {

                if (colors[i].equals(CardColor.BLACK) && cardTypes[j].equals(CardType.WILD)) {
                    card = generateCard(colors[i], cardTypes[j], cardTypes[j].getNumInDeck());
                    //   cards.add(card);
                } else if (colors[i].equals(CardColor.BLACK) && cardTypes[j].equals(CardType.WILD_DRAW_4)) {
                    card = generateCard(colors[i], cardTypes[j], cardTypes[j].getNumInDeck());
                    //  cards.add(card);
                } else if (!colors[i].equals(CardColor.BLACK) && (!cardTypes[j].equals(CardType.WILD) && !cardTypes[j].equals(CardType.WILD_DRAW_4))) {
                    card = generateCard(colors[i], cardTypes[j], cardTypes[j].getNumInDeck());
                    // cards.add(card);
                }
            }
        }
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

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public boolean isEmpty(){
        if(cards.isEmpty())
            return true;
        return false;

    }



    public int getSize() {
        return cards.size();
    }

    public Card pop() {
        if (cards.empty()) {
            throw new IllegalStateException("Pile is empty, cannot draw");
        }

        return cards.pop();
    }

    public void push(final Card card) {
        cards.push(card);
    }


    public Card lookAtTopCard() {
        return cards.peek();
    }

}
