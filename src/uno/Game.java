package uno;

import uno.Cards.Card;
import uno.Cards.CardColor;
import uno.Cards.CardType;

import java.io.PrintStream;
import java.util.*;

public class Game {

    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;
    private String card;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    public static final int NUMBER_OF_CARDS_DEALT = 7;

    private final Pile drawPile = new Pile();
    private final Pile discardPile = new Pile();

    private Player[] players = new Player[4];


    public Game (Scanner input, PrintStream output){
        this.input = input;
        this.output = output;
    }

    // game loop
    public void Run() {
        initPlayer();
        initDrawPile();
        System.out.println("#cards in draw Pile " + drawPile.getSize());
        System.out.println("drawPile" + drawPile);
//        System.out.println("#cards in draw Pile" + discardPile.getSize());
//        initDiscardPile();
//
//        dealCards();
//        System.out.println("list of players:" + Arrays.toString(players));
//
//        System.out.println(discardPile.getSize());
//
//        System.out.println("player1: " + player1 + " " + player1.getHand());
//        System.out.println("player2: " + player2 + " " + player2.getHand());
//        System.out.println("player3: " + player3 + " " + player3.getHand());
//        System.out.println("player4: " + player4 + " " + player4.getHand());

        System.out.println("#cards in draw Pile " + drawPile.getSize());


        System.out.println("---------------------------");

        while (!exit) {
            readUserInput();


        }
    }


    public void initPlayer() {

        player1 = new Player("Caro");
        player2 = new Player("Meggie");
        player3 = new Player("Steff");
        player4 = new Player("Kunibert");

        players[0] = player1;
        players[1] = player2;
        players[2] = player3;
        players[3] = player4;
        System.out.println("players: " + player1 + " , " + player2 + " , " + player3 + " , " + player4);
        System.out.println(Arrays.toString(players));

    }

    private void initDiscardPile() {
        //Rule XY: Take one card from draw pile and put on discard pile
        final var initialCard = drawPile.pop();
        discardPile.push(initialCard);
    }

    //Ziehstapel wird erstellt und gemischt
    public void initDrawPile() {
        drawPile.generateDeck(CardColor.colors, CardType.cardType);
        drawPile.shuffle();
    }

    private void dealCards() {

        for (int i = 0; i < NUMBER_OF_CARDS_DEALT; i++) {
            for (Player player : players) {
                player.drawCard(drawPile);

            }
        }
    }

    private void playTurn(){
        System.out.println("current player: ");

    }

    private void readUserInput() {
        inputCard();
    }


    private Player choosePlayer() {
        int index = (int) (Math.random() * players.length);
        return players[index];

    }


}
