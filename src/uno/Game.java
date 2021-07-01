package uno;

import uno.Cards.Card;
import uno.Cards.CardColor;
import uno.Cards.CardType;
import uno.Help.Help;
import uno.Help.HelpText_Inputs;
import uno.Help.HelpText_Rules;
import uno.Player.Player;
import uno.Player.PlayerHuman;

import java.io.PrintStream;
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

        Player startingPlayerHuman = choosePlayer(); // Meggie // Steff // Caro // Kuni
        //   startingPlayer = nextPlayer(startingPlayer, getDirection());
        Player currentPlayerHuman = startingPlayerHuman;

        System.out.println("Spieler " + currentPlayerHuman + " gibt die Karten." + " Spieler " + nextPlayer(currentPlayerHuman, getDirection()) + " beginnt.");
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
        currentPlayerHuman = checkPlayedCard(currentPlayerHuman);
        if (!cardInput.equals("<->")) {
            currentPlayerHuman = nextPlayer(currentPlayerHuman, getDirection());
        }

        inputCard(currentPlayerHuman);
        // wegen bot
//        currentPlayer.playCard()

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

    // doesnt work yet
    public void newDiscardPile() {
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
    private void inputCard(Player currentPlayerHuman) {
        Scanner input = new Scanner(System.in);
        do {

            showHandAndTable(currentPlayerHuman);
            if (drawPile.isEmpty()) {
                newDiscardPile();
            }

            output.println("Play Card");
            cardInput = input.next();

            if (cardInput.equals("help")) {
                inputHelp();
                updateHelp();

            } else if (cardInput.equals("ziehen")) {
                if ((cardInput = currentPlayerHuman.drawCard(drawPile, discardPile, colorInput)) != null) {
                    currentPlayerHuman = checkPlayedCard(currentPlayerHuman);
                }

                currentPlayerHuman = nextPlayer(currentPlayerHuman, getDirection());

            } else if (currentPlayerHuman.playCard(discardPile, drawPile, cardInput, colorInput) == true) {

                if (currentPlayerHuman.handIsEmpty()) {
                    System.out.println("Deine Hand ist leer! Gratulation! " + currentPlayerHuman + " hat das Spiel gewonnen!");
                    System.exit(0);

                } else {
                    // checks which card is being played
                    currentPlayerHuman = checkPlayedCard(currentPlayerHuman);

                    // next player's turn
                    currentPlayerHuman = nextPlayer(currentPlayerHuman, getDirection());
                }
            }

        } while (true);
    }//inputCard


    // checks which card is played and returns
    private Player checkPlayedCard(Player currentPlayerHuman) {
//        Card discardPileTopCard = discardPile.lookAtTopCard();

        // error: if P1 plays +2 after having drawn card, and P2 can't play, +2 goes to next next player P3

        if (cardInput.equals("W+4")) {
            System.out.println("Hi " + currentPlayerHuman + "! Du hast W+4 gespielt. Du darfst dir eine Farbe aussuchen.");
            String pickedColor = pickColor();
            currentPlayerHuman = nextPlayer(currentPlayerHuman, getDirection());
            currentPlayerHuman.getPlusTwoCards(drawPile);
            currentPlayerHuman.getPlusTwoCards(drawPile);
            System.out.println("_________________________________");
            System.out.println("Hi " + currentPlayerHuman + "! Du musst vier Karten ziehen. Der nächste Spieler ist an der Reihe");
            System.out.println("Hi " + nextPlayer(currentPlayerHuman, getDirection()) + " Du musst die Farbe " + pickedColor + " spielen");
            return currentPlayerHuman;
        }

        if (cardInput.equals("WW")) {
            System.out.println("Hi " + currentPlayerHuman + "! Du hast WW gespielt. Du darfst dir eine Farbe aussuchen.");

            String pickedColor = pickColor();
            // switch to next player
            currentPlayerHuman = nextPlayer(currentPlayerHuman, getDirection());
            System.out.println("Hi " + currentPlayerHuman + "! Du musst die Farbe " + pickedColor + " spielen");
            return currentPlayerHuman;
        }

        if (cardInput.contains("+2")) {
            currentPlayerHuman = nextPlayer(currentPlayerHuman, getDirection());
            currentPlayerHuman.getPlusTwoCards(drawPile);
            System.out.println("_________________________________");
            System.out.println("Hi " + currentPlayerHuman + "! Du musst zwei Karten ziehen. Der nächste Spieler ist an der Reihe.");
            return currentPlayerHuman;
        }

        if (cardInput.contains("<->")) {
            changeDirection(direction);
            System.out.println("Hi " + nextPlayer(currentPlayerHuman, direction) + ", Die Richtung wurde geändert. Du bist jetzt an der Reihe!");
            return currentPlayerHuman;
        }

        if (cardInput.contains("<S>")) {
            System.out.println("Sorry " + nextPlayer(currentPlayerHuman, direction) + ", du musst aussetzen!");
            Player nextPlayerHuman = nextPlayer(currentPlayerHuman, direction);
            System.out.println("Hi " + nextPlayer(nextPlayerHuman, direction) + ", du bist dran!");
            return nextPlayerHuman;
        }

        return currentPlayerHuman;
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

    private Player nextPlayer(Player currentPlayerHuman, String direction) {

        if (direction.equals("clockwise"))
            return clockwise(currentPlayerHuman);
        else
            return counterClockwise(currentPlayerHuman);

    }//nextPlayer

    private Player clockwise(Player currentPlayerHuman) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == currentPlayerHuman) {
                if (i == 3) {
                    currentPlayerHuman = players[0];
                    return currentPlayerHuman;
                } else
                    currentPlayerHuman = players[i + 1];
                return currentPlayerHuman;
            }
        }
        return currentPlayerHuman;
    }//clockwise

    private Player counterClockwise(Player currentPlayerHuman) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == currentPlayerHuman) {
                if (i == 0) {
                    currentPlayerHuman = players[3];
                    return currentPlayerHuman;
                } else
                    currentPlayerHuman = players[i - 1];
                return currentPlayerHuman;
            }
        }
        return currentPlayerHuman;
    }//counterClockwise

    private void showHandAndTable(Player playerHuman) {
        System.out.println("---------------------------");
        System.out.println("card amount check:" + countAllCards());
        System.out.println("card on table: " + discardPile.lookAtTopCard());
        System.out.println("Aktueller Spieler: " + playerHuman);
        System.out.println("Spielrichtung: " + getDirection());
        System.out.println("Deine Hand: " + playerHuman.getHand());
        System.out.println("DrawPile: " + drawPile.getSize());
        System.out.println("DiscardPile: " + discardPile.getSize());
    }//showHandAndTable



    public int countAllCards() {
        return discardPile.getSize() + drawPile.getSize() + player1.getHand().getHandSize() + player2.getHand().getHandSize()
                + player3.getHand().getHandSize() + +player4.getHand().getHandSize();
    }//countAllCards

    // test comment caro 4
    // test comment 5

}
