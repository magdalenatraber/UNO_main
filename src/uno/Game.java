package uno;

import uno.Cards.Card;
import uno.Cards.CardColor;
import uno.Cards.CardType;

import java.util.ArrayList;
import java.util.Collections;

public class Game {

//    private final Scanner input;
//    private final PrintStream output;
//    private boolean exit = false;

ArrayList<Card> cards = new ArrayList<>(); //Array mit Kartendeck
    final Pile cardDeck = new Pile(cards);

    public Game (){
        initPlayer();
        cardDeck.generateDeck(CardColor.colors,CardType.cardType);
        Collections.shuffle(cards);
        System.out.println(cards);

    }

    public void initPlayer() {
        Player player1 = new Player("Caro");
        Player player2 = new Player("Meggie");
        Player player3 = new Player("Steff");
        Player player4 = new Player("Kunibert");
        System.out.println("players: "+ player1 + " , " + player2 + " , " + player3 + " , " + player4);

    }

//    public void Run() {
//        initialize();
//        printState();
//        while(!exit) {
//            readUserInput();
//            updateState();
//            printState();
//        }
//    }



}
