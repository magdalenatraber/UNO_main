package uno;

import uno.Cards.Card;
import uno.Cards.CardColor;
import uno.Cards.CardType;
import uno.Database.DemoApp;
import uno.Help.Help;
import uno.Help.HelpText_Inputs;
import uno.Help.HelpText_Rules;
import uno.Player.Player;
import uno.Player.PlayerBot;
import uno.Player.PlayerHuman;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Scanner;

public class Game {

    public static final int NUMBER_OF_CARDS_DEALT = 7;
    public static Pile drawPile = new Pile();
    public static Pile discardPile = new Pile();
    public static Player player1;
    public static Player player2;
    public static Player player3;
    public static Player player4;
    public static Player[] players = new Player[4];
    public static Player currentPlayer;
    private final Scanner input;
    private final PrintStream output;
    public Player startingPlayer;
    private boolean exit = false;
    private String cardInput;
    private String pickedColor;
    private String direction;
    private int helpNeeded;
    private String playerName;
    private int nrBots;
    private Help help;
    public static int round = 0;
    public static ArrayList<Card> showCards = new ArrayList<>();
    private static int drawPileCounter = 0;

    //Konstruktor
    public Game(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
    }// Konstruktor

    public static void renewDrawPile() {
        System.out.println("Ablagestapel wird neu gemischt.");
        Card lastCard = discardPile.pop();
        while (discardPile.getSize() > 1) {
            Card card = discardPile.pop();
            drawPile.push(card);
        }
        drawPile.shuffle();
        discardPile.push(lastCard);
        drawPileCounter++;
        System.out.println("Karten wurden neu gemischt");
    }

    public void shuffleNewDrawPile() throws EmptyStackException {
        for (Player player : players) {
            while (!player.hand.getCardsInHand().isEmpty()) {
                Card card = player.hand.getCardsInHand().remove(0);
                drawPile.push(card);
            }
        }
        while (discardPile.getSize() != 0) {
            Card card = discardPile.pop();
            drawPile.push(card);
        }
        drawPile.shuffle();

    }

    private String getDirection() {
        return direction;
    }

    public static int getRound() {
        return round;
    }

    //    private void playTurn() {
//        System.out.println("current player: ");
//    }//playTurn
    private void setDirection(String direction) {
        this.direction = direction;
    }

    // game loop
    public void run() {

        // Spielvorbereitung // Passiert nur 1x

        initPlayer();
        startingPlayer = choosePlayer(); // randomly chooses player to start
        // currentPlayer = startingPlayer;
        DemoApp.startDatabase();
        initDrawPile();
        //initDiscardPile();

        while (!exit) {
            newRound();
        }
    }//Game Loop

    public void newRound() {
        // passiert für jede Runde
        setDirection("clockwise");
        round++;
        System.out.println("Runde: " + round);
        if (round > 1) {
            startingPlayer = nextPlayer(startingPlayer, getDirection());
            shuffleNewDrawPile();
        }

        initDiscardPile();
        System.out.println("Spieler " + startingPlayer + " gibt die Karten." + " Spieler " + nextPlayer(startingPlayer, getDirection()) + " beginnt.");
        currentPlayer = startingPlayer;
        //System.out.println("Aktueller Spieler " + currentPlayer.name);

        // distribute cards
        dealCards();

        // check first card
        checkActionCard();

        // player writes what he plays
        inputCard(currentPlayer);
    }

    //Spielvorbereitung: Passiert nur 1x
    //Spieler werden erstellt
    public void initPlayer() {

        System.out.println("");

        Scanner botOrNot = new Scanner(System.in);
        System.out.println("Es können 4 Spieler mitspielen.");
        System.out.println("Wieviele Bots werden benötigt?");
        nrBots = botOrNot.nextInt();

        if (nrBots > 4) {
            System.out.println("Ungültige Eingabe! Die Eingabe wurde auf 4 verringert!");
            nrBots = 4;
        }

        System.out.println("Es werden " + nrBots + " Bots erstellt.");
        System.out.println("____________________");

        for (int i = 1; i <= 4; i++) {

            if (i == 1) {
                if (i <= nrBots) {
                    playerName = "Dalek";
                    player1 = new PlayerBot(playerName);
                    players[0] = player1;
                    System.out.println("Bot " + playerName + " erstellt!");
                } else {
                    Scanner input = new Scanner(System.in);
                    System.out.println("____________________");
                    System.out.println("Spieler Nr. " + i + ", bitte gib deinen Namen ein:");
                    playerName = input.next();
                    player1 = new PlayerHuman(playerName);
                    players[0] = player1;
                    System.out.println("____________________");
                }
            }

            if (i == 2) {
                if (i <= nrBots) {
                    playerName = "Terminator";
                    player2 = new PlayerBot(playerName);
                    players[1] = player2;
                    System.out.println("Bot " + playerName + " erstellt!");
                } else {
                    Scanner input = new Scanner(System.in);
                    System.out.println("____________________");
                    System.out.println("Spieler Nr. " + i + ", bitte gib deinen Namen ein:");
                    playerName = input.next();
                    if (player1.getName().equals(playerName)) {
                        player2 = new PlayerHuman(playerName + "bert");
                        playerName = player2.getName();
                        System.out.println("Dieser Name existiert bereits. Dein Name wurde angepasst.");
                        System.out.println("Du heißt jetzt " + player2.getName());
                        players[1] = player2;
                        System.out.println("____________________");
                    } else {
                        player2 = new PlayerHuman(playerName);
                        players[1] = player2;
                        System.out.println("____________________");
                    }
                }
            }

            if (i == 3) {
                if (i <= nrBots) {
                    playerName = "Bumblebee";
                    player3 = new PlayerBot(playerName);
                    players[2] = player3;
                    System.out.println("Bot " + playerName + " erstellt!");
                } else {
                    Scanner input = new Scanner(System.in);
                    System.out.println("____________________");
                    System.out.println("Spieler Nr. " + i + ", bitte gib deinen Namen ein:");
                    playerName = input.next();
                    if (player1.getName().equals(playerName) || player2.getName().equals(playerName)) {
                        player3 = new PlayerHuman(playerName + "chen");
                        playerName = player3.getName();
                        System.out.println("Dieser Name existiert bereits. Dein Name wurde angepasst.");
                        System.out.println("Du heißt jetzt " + player3.getName());
                        players[2] = player3;
                        System.out.println("____________________");
                    } else {
                        player3 = new PlayerHuman(playerName);
                        players[2] = player3;
                        System.out.println("____________________");
                    }
                }
            }

            if (i == 4) {
                if (i <= nrBots) {
                    playerName = "R2D2";
                    player4 = new PlayerBot(playerName);
                    players[3] = player4;
                    System.out.println("Bot " + playerName + " erstellt!");
                } else {
                    Scanner input = new Scanner(System.in);
                    System.out.println("____________________");
                    System.out.println("Spieler Nr. " + i + ", bitte gib deinen Namen ein:");
                    playerName = input.next();
                    if (player1.getName().equals(playerName) || player2.getName().equals(playerName) || player3.getName().equals(playerName)) {
                        player4 = new PlayerHuman(playerName + "maus");
                        playerName = player4.getName();
                        System.out.println("Dieser Name existiert bereits. Dein Name wurde angepasst.");
                        System.out.println("Du heißt jetzt " + player4.getName());
                        players[3] = player4;
                        System.out.println("____________________");
                    } else {
                        player4 = new PlayerHuman(playerName);
                        players[3] = player4;
                        System.out.println("____________________");
                    }
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

    //für jede Runde
    // Karten werden ausgeteilt - 7 Stück pro Spieler
    private void dealCards() {
        for (int i = 0; i < NUMBER_OF_CARDS_DEALT; i++) {
            for (Player player : players) {
                player.drawCardInHand(drawPile);
            }
        }
        System.out.println(player1 + " " + player1.getHand());
        System.out.println(player2 + " " + player2.getHand());
        System.out.println(player3 + " " + player3.getHand());
        System.out.println(player4 + " " + player4.getHand());

    }//dealCards

    public void checkActionCard() {

        // check first card
        Card firstCard = discardPile.lookAtTopCard();
        cardInput = firstCard.getType().getCaption();

        currentPlayer = checkPlayedCard(currentPlayer);

        if (!cardInput.equals("<->")) {
            currentPlayer = nextPlayer(currentPlayer, getDirection());
        }

        if (cardInput.equals("W")) {
            pickedColor = currentPlayer.pickColor();
            System.out.println(currentPlayer + " hat " + pickedColor + " ausgesucht.");
        }
    }

    //Spieler Input
    private void inputCard(Player currentPlayer) {
        Scanner input = new Scanner(System.in);

        do {
            showHandAndTable(currentPlayer);
            output.println(currentPlayer + ", du bist dran. Was möchtest du machen?");
            cardInput = currentPlayer.inputData(discardPile, pickedColor);
            if (cardInput.equals("help")) {
                inputHelp();
                updateHelp();

            } else if (cardInput.equals("Punktestand")) {
                System.out.println(DemoApp.getRequestedPoints());

            } else if (cardInput.equals("Punktestand gesamt")) {
                System.out.println(DemoApp.requestedPointsAll());

            } else if (cardInput.equals("ziehen")) {
                if ((cardInput = currentPlayer.drawCard(drawPile, discardPile, pickedColor)) != null) {
                    currentPlayer = checkPlayedCard(currentPlayer);
                    if (currentPlayer.countCardsInHand() == 1) {
                        cardInput = currentPlayer.sayUno(cardInput);
                        if (currentPlayer.didYouSayUno(cardInput)) {
                            System.out.println(currentPlayer + " sagt Uno");
                        } else {
                            System.out.println(currentPlayer + " hat vergessen UNO zu sagen." + currentPlayer + " muss zwei Karten heben");
                            currentPlayer.getPlusTwoCards(drawPile);
                        }
                    }
                }
                currentPlayer = nextPlayer(currentPlayer, getDirection());


            } else if (currentPlayer.playCard(discardPile, drawPile, cardInput, pickedColor)) {

                System.out.println(currentPlayer + " spielt " + cardInput);

                if (currentPlayer.handIsEmpty()) {
                    System.out.println(currentPlayer + " hat keine Karten mehr auf der Hand! " + currentPlayer + " hat die Runde gewonnen! Gratulation");

                    for (Player p : players) {
                        int points = p.getHand().getHandPoints();
                        p.setPoint(points);
                        System.out.println(p.getName() + ": " + points);
                    }

                    DemoApp.updateDatabase();
                    System.out.println(DemoApp.getDatabaseRundensieger());

                    if (DemoApp.pointsCheck >= 500) {
                        System.out.println("Gratuliere, du hast damit das Spiel gewonnen!");
                        exit = true;
                    }

                    // System.out.println("Ablagestapel wurde neu gemischt: " + drawPileCounter);
                    System.out.println("Ende der Runde " + round);
                    Scanner scanner = new Scanner(System.in);
                    do {
                        System.out.println("neue Runde? j/n");
                        String yesOrNo = scanner.next();
                        if (yesOrNo.equals("j")) newRound();
                        else if (yesOrNo.equals("n")) System.exit(0);
                        else System.out.println("Dies ist keine gültige Eingabe!");
                    } while (true);

                } else {

                    if (currentPlayer.countCardsInHand() == 1) {
                        cardInput = currentPlayer.sayUno(cardInput);
                        if (currentPlayer.didYouSayUno(cardInput)) {
                            System.out.println(currentPlayer + " sagt Uno");
                        } else {
                            System.out.println(currentPlayer + " hat vergessen UNO zu sagen." + currentPlayer + " muss zwei Karten heben");
                            currentPlayer.getPlusTwoCards(drawPile);
                        }
                    }
                    // checks which card is being played
                    currentPlayer = checkPlayedCard(currentPlayer);

                    // neuer Stapel


                    // next player's turn
                    currentPlayer = nextPlayer(currentPlayer, getDirection());


                }
            }

        } while (true);
    }//inputCard

    //passiert mehrmals pro Runde
    // checks which card is played and returns
    private Player checkPlayedCard(Player currentPlayer) {
//        Card discardPileTopCard = discardPile.lookAtTopCard();

        // error: if P1 plays +2 after having drawn card, and P2 can't play, +2 goes to next next player P3

        if (cardInput.contains("W+4")) {
            System.out.println("Hi " + currentPlayer + "! Du hast W+4 gespielt. Du darfst dir eine Farbe aussuchen.");
            pickedColor = currentPlayer.pickColor();
            boolean rightOrWrong = currentPlayer.compareHandWithPile();
            if (nextPlayer(currentPlayer, getDirection()).challenge(rightOrWrong)) {
                currentPlayer.getPlusTwoCards(drawPile);
                currentPlayer.getPlusTwoCards(drawPile);
            }
            currentPlayer = nextPlayer(currentPlayer, getDirection());
            System.out.println("Hi " + nextPlayer(currentPlayer, getDirection()) + "! Du musst die Farbe " + pickedColor + " spielen");
            Game.showCards.removeAll(Game.showCards);
            return currentPlayer;
        }

        if (cardInput.contains("WW")) {
            System.out.println("Hi " + currentPlayer + "! Du hast WW gespielt. Du darfst dir eine Farbe aussuchen.");

            pickedColor = currentPlayer.pickColor();
            System.out.println("Hi " + nextPlayer(currentPlayer, getDirection()) + "! Du musst die Farbe " + pickedColor + " spielen");
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

    private void changeDirection(String direction) {
        if (direction.equals("clockwise"))
            setDirection("counterclockwise");
        else
            setDirection("clockwise");
    }

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

    private Player nextPlayer(Player currentPlayer, String direction) {

        if (direction.equals("clockwise"))
            return clockwise(currentPlayer);
        else
            return counterClockwise(currentPlayer);

    }//nextPlayer

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

    //END OF ROUND
    private void showHandAndTable(Player player) {
        System.out.println("---------------------------");
        System.out.print("Anzahl Karten: " + countAllCards());
        System.out.print(" | Ziehstapel: " + drawPile.getSize());
        System.out.println(" | Ablagestapel: " + discardPile.getSize());
        System.out.print("Aktueller Spieler: " + player);
        System.out.println(" | Spielrichtung: " + getDirection());
        System.out.print("Karte auf dem Tisch: " + discardPile.lookAtTopCard());
        if (discardPile.lookAtTopCard().getColor().getCaption().equals("W"))
            System.out.println(" | Gewählte Farbe: " + pickedColor);
        System.out.println();
        System.out.println("Deine Hand:" + player.getHand());
    }//showHandAndTable

    public int countAllCards() {
        return discardPile.getSize() + drawPile.getSize() + player1.getHand().getHandSize() + player2.getHand().getHandSize()
                + player3.getHand().getHandSize() + player4.getHand().getHandSize();
    }//countAllCards

}
