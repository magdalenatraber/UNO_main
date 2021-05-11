package uno;

import uno.Cards.Card;
import uno.Cards.CardColor;
import uno.Cards.CardType;
import uno.Help.Help;
import uno.Help.HelpText;
import uno.Player.Player;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class Game {

    public static final int NUMBER_OF_CARDS_DEALT = 7;
    private final Scanner input;
    private final PrintStream output;
    private final Pile drawPile = new Pile();
    private final Pile discardPile = new Pile();
    private boolean exit = false;
    private String card;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player[] players = new Player[4];

    private int helpNeeded;
    private Help help;


    //Konstruktor
    public Game(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
    }// Konstruktor


    // game loop
    public void run() {
        // Spielvorbereitung
        initPlayer();
        initDrawPile();
        initDiscardPile();


        Player startingPlayer = choosePlayer(); // Meggie // Steff // Caro // Kuni


        // neue Runde
        startingPlayer = clockwise(startingPlayer);
        Player currentPlayer = startingPlayer;
        System.out.println("Spieler " + currentPlayer + " beginnt. Spieler " + counterClockwise(currentPlayer) + " gibt die Karten.");
        dealCards();

        System.out.println(player1 + " " + player1.getHand());
        System.out.println(player2 + " " + player2.getHand());
        System.out.println(player3 + " " + player3.getHand());
        System.out.println(player4 + " " + player4.getHand());



        inputCard(currentPlayer);

        while (!exit) {
        }
    }//Game Loop


    //Spieler werden erstellt
    public void initPlayer() {

        player1 = new Player("Caro");
        player2 = new Player("Meggie");
        player3 = new Player("Steff");
        player4 = new Player("Kunibert");

        players[0] = player1;
        players[1] = player2;
        players[2] = player3;
        players[3] = player4;

    }//initPlayer

    //Ziehstapel wird erstellt und gemischt
    public void initDrawPile() {
        drawPile.generateDeck(CardColor.colors, CardType.cardType);
        drawPile.shuffle();
    }//initDrawPile


    //Ablagestapel wird erstellt - oberste Karte wird vom Ziehstapel genommen und auf Ablagestapel gelegt
    private void initDiscardPile() {
        final var initialCard = drawPile.pop();
        discardPile.push(initialCard);
        if (initialCard.getType().getCaption().equals("+4")) {
            initDiscardPile();
        }
    }//initDiscardPile


    private Player choosePlayer() {
        int index = (int) (Math.random() * players.length);
        return players[index];
    }//choosePlayer

    // Karten werden ausgeteilt - 7 Stück pro Spieler
    private void dealCards() {
        for (int i = 0; i < NUMBER_OF_CARDS_DEALT; i++) {
            for (Player player : players) {
                player.drawCardInHand(drawPile);
            }
        }
    }//dealCards


    //Help Input
    private void inputHelp() {
        Scanner input = new Scanner(System.in);
        System.out.println("Benötigst du Hilfe? Für JA drücke 1, für NEIN drücke 2");
        do {
            helpNeeded = input.nextInt();
            if (helpNeeded < 1 || helpNeeded > 2) {
                output.println("Dies ist keine gültige Eingabe!");
            } else {
                break;
            }
        } while (true);
    }//Help Input


    private void updateHelp() {
        switch (helpNeeded) {
            case 1:
                help = new HelpText();
                break;
            case 2:
                System.out.println("Spiel wird fortgesetzt");
                break;
            default:
                break;
        }
    }//update Help


    private boolean checkTopCard(Player currentPlayer) {
        Card discardPileTopCard = discardPile.lookAtTopCard();

        if (discardPileTopCard.getType().getCaption().equals("+2")){
            currentPlayer.getPlusTwoCards(drawPile);
            System.out.println("_________________________________");
            System.out.println("Hi " + currentPlayer + "! Du musst zwei Karten ziehen. Der nächste Spieler ist an der Reihe");
        return true;
        }
        return false;
    }

    //Spieler Input
    private void inputCard(Player currentPlayer) {
        Scanner input = new Scanner(System.in);
        do {

            showHandAndTable(currentPlayer);
            output.println("Play Card");
            card = input.next();

            if (card.equals("help")) {
                inputHelp();
                updateHelp();

            } else if (card.equals("ziehen")) {
                currentPlayer.drawCard(drawPile, discardPile);
                currentPlayer = nextPlayer(currentPlayer);

            } else if (currentPlayer.playCard(discardPile, drawPile, card) == true) {

                if (currentPlayer.handIsEmpty()) {
                    System.out.println("Deine Hand ist leer! Gratulation! " + currentPlayer + " hat das Spiel gewonnen!");
                    System.exit(0);

                } else {
                    currentPlayer = nextPlayer(currentPlayer);

                    if (checkTopCard(currentPlayer)) {
                        currentPlayer = nextPlayer(currentPlayer);
                    }
                }
            }

        } while (true);
    }//inputCard


//    private void playTurn() {
//        System.out.println("current player: ");
//    }//playTurn


    private Player nextPlayer(Player currentPlayer) {
        return clockwise(currentPlayer);
    }//nextPlayer


    private Player clockwise(Player currentPlayer) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == currentPlayer) {
                if (i == 3) {
                    currentPlayer = players[0];
                    return currentPlayer;
                } else
                    currentPlayer = players[i + 1];
                return currentPlayer;
            }
        }
        return currentPlayer;
    }//clockwise


    private Player counterClockwise(Player currentPlayer) {
       for (int i = 0; i < players.length; i++) {
            if (players[i] == currentPlayer) {
                if (i == 0) {
                    currentPlayer = players[3];
                    return currentPlayer;
                } else
                    currentPlayer = players[i - 1];
                return currentPlayer;
            }
        }
        return currentPlayer;
    }//counterClockwise


    private void showHandAndTable(Player player) {
        System.out.println("---------------------------");
        System.out.println("card amount check:" + countAllCards());
        System.out.println("card on table: " + discardPile.lookAtTopCard());
        System.out.println("Aktueller Spieler: " + player);
        System.out.println("Deine Hand: " + player.getHand());
    }//showHandAndTable


    public int countAllCards() {
        return discardPile.getSize() + drawPile.getSize() + player1.getHand().getHandSize() + player2.getHand().getHandSize()
                + player3.getHand().getHandSize() + +player4.getHand().getHandSize();
    }//countAllCards

    // test comment caro 4
    // test comment 5

}
