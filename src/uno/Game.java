package uno;

import uno.Cards.Card;
import uno.Cards.CardColor;
import uno.Cards.CardType;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class Game {

    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;
    private String card;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    Stack<Card> cards = new Stack<>(); //Array mit Kartendeck
    final Pile cardDeck = new Pile(cards);

    public Game (Scanner input, PrintStream output){
        this.input = input;
        this.output = output;
    }

    // game loop
    public void Run() {
        initPlayer();
        initDrawPile();


        while(!exit){
//            readUserInput();

        }
    }

    public void initPlayer() {
        player1 = new Player("Caro");
        player2 = new Player("Meggie");
        player3 = new Player("Steff");
        player4 = new Player("Kunibert");
        System.out.println("players: "+ player1 + " , " + player2 + " , " + player3 + " , " + player4);

    }

    public void initDrawPile(){
        cardDeck.generateDeck(CardColor.colors,CardType.cardType);
        Collections.shuffle(cards);
        System.out.println(cards);

    }

    private void readUserInput(){
        inputCard();
    }

    private void inputCard(){

        do {
            output.println("Play Card");
            card = input.next();

        } while (true);

    }



}
