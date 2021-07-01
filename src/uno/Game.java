package uno;

import uno.Cards.CardColor;
import uno.Cards.CardType;
import uno.Help.Help;
import uno.Help.HelpText_Inputs;
import uno.Help.HelpText_Rules;
import uno.Player.Player;

import java.io.PrintStream;
import java.util.Scanner;

public class Game {

    public static final int NUMBER_OF_CARDS_DEALT = 7;
    private final Scanner input;
    private final PrintStream output;
    private final Pile drawPile = new Pile();
    private final Pile discardPile = new Pile();
    private boolean exit = false;
    private String cardInput;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player[] players = new Player[4];
    private String direction;
    private int helpNeeded;
    private String playerName;
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
        setDirection("clockwise");

        startingPlayer = nextPlayer(startingPlayer, getDirection());
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

        System.out.println("");

        for (int i = 1; i <= 4; i++) {
            Scanner input = new Scanner(System.in);
            System.out.println("Spieler Nr. " + i + ", bitte gib deinen Namen ein:");
            playerName = input.next();
            if (i == 1) {
                player1 = new Player(playerName);
                players[0] = player1;
                System.out.println("____________________");
            }
            if (i == 2) {
                if (player1.getName().equals(playerName)) {
                    player2 = new Player(playerName + "bert");
                    System.out.println("Dieser Name existiert bereits. Dein Name wurde angepasst.");
                    System.out.println("Du heißt jetzt " + player2.getName());
                } else {
                    player2 = new Player(playerName);
                }
                players[1] = player2;
                System.out.println("____________________");
            }
            if (i == 3) {
                if (player1.getName().equals(playerName) || player2.getName().equals(playerName)) {
                    player3 = new Player(playerName + "chen");
                    System.out.println("Dieser Name existiert bereits. Dein Name wurde angepasst.");
                    System.out.println("Du heißt jetzt " + player3.getName());
                } else {
                    player3 = new Player(playerName);
                }
                players[2] = player3;
                System.out.println("____________________");
            }
            if (i == 4) {
                if (player1.getName().equals(playerName) || player2.getName().equals(playerName) || player3.getName().equals(playerName)) {
                    player4 = new Player(playerName + "maus");
                    System.out.println("Dieser Name existiert bereits. Dein Name wurde angepasst.");
                    System.out.println("Du heißt jetzt " + player4.getName());
                } else {
                    player4 = new Player(playerName);
                }
                players[3] = player4;
                System.out.println("____________________");
            }
        }

        System.out.println("");
        System.out.println("Willkommen " + player1.getName() + ", " + player2.getName() + ", " + player3.getName() + " und " + player4.getName() + "!");
        System.out.println("Das Spiel kann nun beginnen! Viel Spass!");
        System.out.println("");
        System.out.println("* * * * * * * * * * *");
        System.out.println("");

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
        System.out.println("Benötigst du Hilfe?");
        System.out.println("Für SPIELREGELN drücke 1");
        System.out.println("Für EINGABEMÖGLICHKEITEN drücke 2");
        System.out.println("Benötigst du keine Hilfe, drücke 3");
        do {
            helpNeeded = input.nextInt();
            if (helpNeeded < 1 || helpNeeded > 3) {
                output.println("Dies ist keine gültige Eingabe!");
            } else {
                break;
            }
        } while (true);
    }//Help Input


    private void updateHelp() {
        switch (helpNeeded) {
            case 1:
                help = new HelpText_Rules();
                break;
            case 2:
                help = new HelpText_Inputs();
                break;
            case 3:
                System.out.println("Spiel wird fortgesetzt");
                break;
            default:
                break;
        }
    }//update Help


    // checks which card is played and returns
    private Player checkPlayedCard(Player currentPlayer) {
//        Card discardPileTopCard = discardPile.lookAtTopCard();

        // error: if P1 plays +2 after having drawn card, and P2 can't play, +2 goes to next next player P3

        if (cardInput.contains("+4")) {
            System.out.println("Hi " + currentPlayer + "! Du hast W+4 gespielt. Du darfst dir eine Farbe aussuchen.");

            currentPlayer = nextPlayer(currentPlayer, getDirection());
            currentPlayer.getPlusTwoCards(drawPile);
            currentPlayer.getPlusTwoCards(drawPile);
            System.out.println("_________________________________");
            System.out.println("Hi " + currentPlayer + "! Du musst vier Karten ziehen. Der nächste Spieler ist an der Reihe");
            return currentPlayer;
        }

        if (cardInput.contains("+2")) {
            currentPlayer = nextPlayer(currentPlayer, getDirection());
            currentPlayer.getPlusTwoCards(drawPile);
            System.out.println("_________________________________");
            System.out.println("Hi " + currentPlayer + "! Du musst zwei Karten ziehen. Der nächste Spieler ist an der Reihe");
            return currentPlayer;
        }

        if (cardInput.contains("<->")) {
            changeDirection(direction);
            System.out.println("Hi " + nextPlayer(currentPlayer, direction) + ", the direction was changed, it's your turn now!");
            return currentPlayer;
        }

        if (cardInput.contains("<S>")) {
            System.out.println("Sorry " + nextPlayer(currentPlayer, direction) + ", you're being skipped.");
            Player nextPlayer = nextPlayer(currentPlayer, direction);
            System.out.println("Hi " + nextPlayer(nextPlayer, direction) + ", it's your turn");
            return nextPlayer;
        }

        return currentPlayer;
    }

    //Spieler Input
    private void inputCard(Player currentPlayer) {
        Scanner input = new Scanner(System.in);
        do {

            showHandAndTable(currentPlayer);
            output.println("Play Card");
            cardInput = input.next();

            if (cardInput.equals("help")) {
                inputHelp();
                updateHelp();

            } else if (cardInput.equals("ziehen")) {
                currentPlayer.drawCard(drawPile, discardPile);
                currentPlayer = nextPlayer(currentPlayer, getDirection());

            } else if (currentPlayer.playCard(discardPile, drawPile, cardInput) == true) {

                if (currentPlayer.handIsEmpty()) {
                    System.out.println("Deine Hand ist leer! Gratulation! " + currentPlayer + " hat das Spiel gewonnen!");
                    System.exit(0);

                } else {
                    // checks which card is being played
                    currentPlayer = checkPlayedCard(currentPlayer);

                    // next player's turn
                    currentPlayer = nextPlayer(currentPlayer, getDirection());
                }
            }

        } while (true);
    }//inputCard

    private String getDirection() {
        return direction;
    }

    //    private void playTurn() {
//        System.out.println("current player: ");
//    }//playTurn
    private void setDirection(String direction) {
        this.direction = direction;
    }

    private void changeDirection(String direction) {
        if (direction.equals("clockwise"))
            setDirection("counterclockwise");
        else
            setDirection("clockwise");
    }

    private Player nextPlayer(Player currentPlayer, String direction) {

        if (direction.equals("clockwise"))
            return clockwise(currentPlayer);
        else
            return counterClockwise(currentPlayer);

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
        System.out.println("Spielrichtung: " + getDirection());
        System.out.println("Deine Hand: " + player.getHand());
    }//showHandAndTable


    public int countAllCards() {
        return discardPile.getSize() + drawPile.getSize() + player1.getHand().getHandSize() + player2.getHand().getHandSize()
                + player3.getHand().getHandSize() + +player4.getHand().getHandSize();
    }//countAllCards

    // test comment caro 4
    // test comment 5

}
