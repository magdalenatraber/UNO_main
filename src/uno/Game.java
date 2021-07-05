package uno;

import uno.Cards.Card;
import uno.Cards.CardColor;
import uno.Cards.CardType;
import uno.Help.Help;
import uno.Help.HelpText_Inputs;
import uno.Help.HelpText_Rules;
import uno.Player.Player;
import uno.Player.PlayerBot;
import uno.Player.PlayerHuman;

import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class Game {

    public static final int NUMBER_OF_CARDS_DEALT = 7;
    private final Scanner input;
    private final PrintStream output;
    private Pile drawPile = new Pile();
    private Pile discardPile = new Pile();
    private boolean exit = false;
    private String cardInput;
    private String colorInput;

    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player[] players = new Player[4];

    private Player currentPlayer;
    private String direction;
    private int helpNeeded;
    private String playerName;
    private int nrBots;
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
        setDirection("clockwise");

        Player startingPlayer = choosePlayer(); // Meggie // Steff // Caro // Kuni
        //   startingPlayer = nextPlayer(startingPlayer, getDirection());
        Player currentPlayer = startingPlayer;

        System.out.println("Spieler " + currentPlayer + " gibt die Karten." + " Spieler " + nextPlayer(currentPlayer, getDirection()) + " beginnt.");
        initDrawPile();
        initDiscardPile();
        dealCards();

        // distribute cards
        System.out.println(player1 + " " + player1.getHand());
        System.out.println(player2 + " " + player2.getHand());
        System.out.println(player3 + " " + player3.getHand());
        System.out.println(player4 + " " + player4.getHand());

        // check first card
        Card firstCard = discardPile.lookAtTopCard();
        cardInput = firstCard.getType().getCaption();
        currentPlayer = checkPlayedCard(currentPlayer);
        if (!cardInput.equals("<->")) {
            currentPlayer = nextPlayer(currentPlayer, getDirection());
        }

        inputCard(currentPlayer);
        // wegen bot
//        currentPlayer.playCard()

        while (!exit) {
        }
    }//Game Loop

    //Spieler werden erstellt
    public void initPlayer() {

        System.out.println("");

        Scanner botOrNot = new Scanner(System.in);
        System.out.println("Es können 4 Spieler mitspielen.");
        System.out.println("Wieviele Bots werden benötigt?");
        nrBots = input.nextInt();
        System.out.println("Es werden " + nrBots + " Bots erstellt.");

        for (int i = 1; i <= 4-nrBots; i++) {
            Scanner input = new Scanner(System.in);
            System.out.println("Spieler Nr. " + i + ", bitte gib deinen Namen ein:");
            playerName = input.next();
            if (i == 1) {
                player1 = new PlayerHuman(playerName);
                players[0] = player1;
                System.out.println("____________________");
            }
            if (i == 2) {
                if (player1.getName().equals(playerName)) {
                    player2 = new PlayerHuman(playerName + "bert");
                    System.out.println("Dieser Name existiert bereits. Dein Name wurde angepasst.");
                    System.out.println("Du heißt jetzt " + player2.getName());
                } else {
                    player2 = new PlayerHuman(playerName);
                }
                players[1] = player2;
                System.out.println("____________________");
            }
            if (i == 3) {
                if (player1.getName().equals(playerName) || player2.getName().equals(playerName)) {
                    player3 = new PlayerHuman(playerName + "chen");
                    System.out.println("Dieser Name existiert bereits. Dein Name wurde angepasst.");
                    System.out.println("Du heißt jetzt " + player3.getName());
                } else {
                    player3 = new PlayerHuman(playerName);
                }
                players[2] = player3;
                System.out.println("____________________");
            }
            if (i == 4) {
                if (player1.getName().equals(playerName) || player2.getName().equals(playerName) || player3.getName().equals(playerName)) {
                    player4 = new PlayerHuman(playerName + "maus");
                    System.out.println("Dieser Name existiert bereits. Dein Name wurde angepasst.");
                    System.out.println("Du heißt jetzt " + player4.getName());
                } else {
                    player4 = new PlayerHuman(playerName);
                }
                players[3] = player4;
                System.out.println("____________________");
            }

            for (Player p : players){
                if (p.getName() == null){
                    String name = generateRandomName(7);
                    p = new PlayerBot(name);
                }
            }
        }

        System.out.println("");
        System.out.println("Willkommen " + player1.getName() + ", " + player2.getName() + ", " + player3.getName() + " und " + player4.getName() + "!");
        System.out.println("Das Spiel kann nun beginnen! Viel Spass!");
        System.out.println("");
        System.out.println("* * * * * * * * * * *");
        System.out.println("");

    }//initPlayer

    public static String generateRandomName(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                +"lmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    //Ziehstapel wird erstellt und gemischt
    public void initDrawPile() {
        drawPile.generateDeck(CardColor.colors, CardType.cardType);
        drawPile.shuffle();
    }//initDrawPile

    // doesnt work yet
    public void newDrawPile() {
        Pile newDiscardPile = new Pile();
        Card lastCard = discardPile.pop();
        newDiscardPile.push(lastCard);
        drawPile = discardPile;
        drawPile.shuffle();
        discardPile = newDiscardPile;
    }

    //Ablagestapel wird erstellt - oberste Karte wird vom Ziehstapel genommen und auf Ablagestapel gelegt
    private void initDiscardPile() {
        final var initialCard = drawPile.pop();
        discardPile.push(initialCard);
        if (initialCard.getType().getCaption().equals("+4")) {
            initDiscardPile();
        }//initDiscardPile

    }

    private void checkFirstCard(Card initialCard) {


    }

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


    //Spieler Input
    private void inputCard(Player currentPlayer) {
        Scanner input = new Scanner(System.in);
        do {

            showHandAndTable(currentPlayer);

            output.println("Play Card");

            cardInput = currentPlayer.inputData();

            if (cardInput.equals("help")) {
                inputHelp();
                updateHelp();

            } else if (cardInput.equals("ziehen")) {
                if ((cardInput = currentPlayer.drawCard(drawPile, discardPile, colorInput)) != null) {
                    currentPlayer = checkPlayedCard(currentPlayer);
                }

                currentPlayer = nextPlayer(currentPlayer, getDirection());

            } else if (currentPlayer.playCard(discardPile, drawPile, cardInput, colorInput) == true) {

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



    // checks which card is played and returns
    private Player checkPlayedCard(Player currentPlayer) {
//        Card discardPileTopCard = discardPile.lookAtTopCard();

        // error: if P1 plays +2 after having drawn card, and P2 can't play, +2 goes to next next player P3

        if (cardInput.equals("W+4")) {
            System.out.println("Hi " + currentPlayer + "! Du hast W+4 gespielt. Du darfst dir eine Farbe aussuchen.");
            String pickedColor = pickColor();
            currentPlayer = nextPlayer(currentPlayer, getDirection());
            currentPlayer.getPlusTwoCards(drawPile);
            currentPlayer.getPlusTwoCards(drawPile);
            System.out.println("_________________________________");
            System.out.println("Hi " + currentPlayer + "! Du musst vier Karten ziehen. Der nächste Spieler ist an der Reihe");
            System.out.println("Hi " + nextPlayer(currentPlayer, getDirection()) + " Du musst die Farbe " + pickedColor + " spielen");
            return currentPlayer;
        }

        if (cardInput.equals("WW")) {
            System.out.println("Hi " + currentPlayer + "! Du hast WW gespielt. Du darfst dir eine Farbe aussuchen.");

            String pickedColor = pickColor();
            // switch to next player
            currentPlayer = nextPlayer(currentPlayer, getDirection());
            System.out.println("Hi " + currentPlayer + "! Du musst die Farbe " + pickedColor + " spielen");
            return currentPlayer;
        }

        if (cardInput.contains("+2")) {
            currentPlayer = nextPlayer(currentPlayer, getDirection());
            currentPlayer.getPlusTwoCards(drawPile);
            System.out.println("_________________________________");
            System.out.println("Hi " + currentPlayer + "! Du musst zwei Karten ziehen. Der nächste Spieler ist an der Reihe.");
            return currentPlayer;
        }

        if (cardInput.contains("<->")) {
            changeDirection(direction);
            System.out.println("Hi " + nextPlayer(currentPlayer, direction) + ", Die Richtung wurde geändert. Du bist jetzt an der Reihe!");
            return currentPlayer;
        }

        if (cardInput.contains("<S>")) {
            System.out.println("Sorry " + nextPlayer(currentPlayer, direction) + ", du musst aussetzen!");
            Player nextPlayerHuman = nextPlayer(currentPlayer, direction);
            System.out.println("Hi " + nextPlayer(nextPlayerHuman, direction) + ", du bist dran!");
            return nextPlayerHuman;
        }

        return currentPlayer;
    }

    private String pickColor() {

        Scanner inputColor = new Scanner(System.in);
        boolean pickedColor = false;
        while (pickedColor == false) {
            colorInput = inputColor.next();


            if (colorInput.equals("Y")) {
                System.out.println("Du hast die Farbe " + colorInput + " gewählt");
                pickedColor = true;
            } else if (colorInput.equals("G")) {
                System.out.println("Du hast die Farbe " + colorInput + " gewählt");
                pickedColor = true;
            } else if (colorInput.equals("B")) {
                System.out.println("Du hast die Farbe " + colorInput + " gewählt");
                pickedColor = true;
            } else if (colorInput.equals("R")) {
                System.out.println("Du hast die Farbe " + colorInput + " gewählt");
                pickedColor = true;
            } else {
                System.out.println("Diese Eingabe ist nicht gültig");
                continue;
            }
        }
        return colorInput;
    }

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

    private Player setNextPlayer() {
        return nextPlayer(currentPlayer, getDirection());
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
        System.out.println("DrawPile: " + drawPile.getSize());
        System.out.println("DiscardPile: " + discardPile.getSize());
    }//showHandAndTable



    public int countAllCards() {
        return discardPile.getSize() + drawPile.getSize() + player1.getHand().getHandSize() + player2.getHand().getHandSize()
                + player3.getHand().getHandSize() + player4.getHand().getHandSize();
    }//countAllCards

    // test comment caro 4
    // test comment 5

}
