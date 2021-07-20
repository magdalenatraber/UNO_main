package uno;

import uno.Cards.Card;
import uno.Cards.CardColor;
import uno.Cards.CardType;
import uno.Database.DemoApp;
import uno.Help.Help;
import uno.Help.HelpText_Inputs;
import uno.Help.HelpText_Punishments;
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
    //private boolean exit = false;
    public boolean gameEnded;
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

    // * * * ANFORDERUNGEN PUNKT 16 * * *
    // Ziehstapel wird neu erstellt
    public static void renewDrawPile() {
        System.out.println("Ziehstapel wird neu gemischt.");
        Card lastCard = discardPile.pop();
        while (discardPile.getSize() > 1) {
            Card card = discardPile.pop();
            drawPile.push(card);
        }
        drawPile.shuffle();
        discardPile.push(lastCard);
        drawPileCounter++;
        System.out.println("Karten wurden neu gemischt");
    }//renewDrawPile

    // neu erstellter Ziehstapel wird gemischt
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
    } //shuffleNewDrawPile

    // Spielrichtung wird zurückgegeben
    private String getDirection() {
        return direction;
    }// getDirection

    // Rundenanzahl wird zurückgegeben
    public static int getRound() {
        return round;
    }// getRound

    // gibt die Spielrichtung vor
    private void setDirection(String direction) {
        this.direction = direction;
    }// setDirection

    // GAME LOOP - run
    public void run() {
        // Spielvorbereitung // Passiert nur 1x
        initPlayer();
        // * * * ANFORDERUNGEN PUNKT 7 * * *
        startingPlayer = choosePlayer(); // Bestimmt einen Startspieler per Zufall
        DemoApp.startDatabase();
        initDrawPile();
        newRound();
    }//Game Loop

    // Eine neue Runde wird gestartet
    public void newRound() {
        // passiert für jede Runde
        setDirection("im UZS");
        round++;
        System.out.println("Runde: " + round);

        // * * * ANFORDERUNGEN PUNKT 9 * * *
        // Startspieler und Geber verschieben sich um eine Position
        if (round > 1) {
            startingPlayer = nextPlayer(startingPlayer, getDirection());
            shuffleNewDrawPile();
        }

        // neuer Ablagestapel wird erstellt
        initDiscardPile();
        System.out.println("Spieler " + startingPlayer + " gibt die Karten." + " Spieler " + nextPlayer(startingPlayer, getDirection()) + " beginnt.");
        currentPlayer = startingPlayer;

        // Karten werden ausgeteilt
        dealCards();

        // * * * ANFORDERUNGEN PUNKT 7 * * *
        // Es wird geprüft, ob die erste Karte eine Action-Karte ist
        checkActionCard();

        // Eingabe Spieler
        inputCard(currentPlayer);

    }// newRound

    // * * * ANFORDERUNGEN PUNKT 2 * * *
    //Spieler werden erstellt - passiert einmal zu Beginn des Spieles
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
        System.out.println("");
        System.out.println("Ihr erreicht das Hilfemenü jederzeit mit der Eingabe help");
        System.out.println("");
        System.out.println("Das Spiel kann nun beginnen! Viel Spass!");
        System.out.println("");
        System.out.println("* * * * * * * * * * *");
        System.out.println("");
    }//initPlayer

    // * * * ANFORDERUNGEN PUNKT 1 * * *
    // * * * ANFORDERUNGEN PUNKT 3 * * *
    // * * * ANFORDERUNGEN PUNKT 5 * * *
    //Ziehstapel wird erstellt und gemischt - passiert einmal am Anfang des Spiels
    public void initDrawPile() {
        drawPile.generateDeck(CardColor.colors, CardType.cardType);
        drawPile.shuffle();
    }//initDrawPile

    // * * * ANFORDERUNGEN PUNKT 6 * * *
    // * * * ANFORDERUNGEN PUNKT 37 * * *
    //Ablagestapel wird erstellt - oberste Karte wird vom Ziehstapel genommen und auf Ablagestapel gelegt
    private void initDiscardPile() {
        final var initialCard = drawPile.pop();
        discardPile.push(initialCard);
        if (initialCard.getType().getCaption().equals("+4")) {
            initDiscardPile();
        }
    }//initDiscardPile

    // Erster Spieler des Spiels wird per Zufall gewählt
    private Player choosePlayer() {
        int index = (int) (Math.random() * players.length);
        return players[index];
    }//choosePlayer

    // * * * ANFORDERUNGEN PUNKT 4 * * *
    // Karten werden ausgeteilt - 7 Stück pro Spieler - zu Beginn jeder Runde
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

    // prüft, ob die aktuelle Karte eine Action-Karte ist
    public void checkActionCard() {

        // überprüft die erste Karte
        Card firstCard = discardPile.lookAtTopCard();
        cardInput = firstCard.getType().getCaption();

        currentPlayer = checkPlayedCard(currentPlayer);

        // * * * ANFORDERUNGEN PUNKT 25 * * *
        if (!cardInput.equals("<->")) {
            currentPlayer = nextPlayer(currentPlayer, getDirection());
        }

        // * * * ANFORDERUNGEN PUNKT 31 * * *
        if (cardInput.equals("W")) {
            pickedColor = currentPlayer.pickColor();
            System.out.println(currentPlayer + " hat " + pickedColor + " ausgesucht.");
        }
    }//checkActionCard

    //SPIELER INPUT - inputCard
    private void inputCard(Player currentPlayer) {

        do {
            // * * * ANFORDERUNGEN PUNKT 12 * * *
            showHandAndTable(currentPlayer);
            output.println(currentPlayer + ", du bist dran. Was möchtest du machen?");
            cardInput = currentPlayer.inputData(discardPile, pickedColor);

            // diverse Eingaben

            // * * * ANFORDERUNGEN PUNKT 49 * * *
            if (cardInput.equals("help")) {
                inputHelp();
                updateHelp();

            } else if (cardInput.equals("Punktestand")) {
                System.out.println(DemoApp.requestedPointsAll());

            // * * * ANFORDERUNGEN PUNKT 17 * * *
            // * * * ANFORDERUNGEN PUNKT 38 * * *
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

                // * * * ANFORDERUNGEN PUNKT 13 * * *
                // Spieler spielt Karte
            } else if (currentPlayer.playCard(discardPile, drawPile, cardInput, pickedColor)) {

                System.out.println(currentPlayer + " spielt " + cardInput);

                // * * * ANFORDERUNGEN PUNKT 19 * * *
                // * * * ANFORDERUNGEN PUNKT 45 * * *
                // Spieler hat keine Karten mehr auf der Hand - Runde gewonnen
                if (currentPlayer.handIsEmpty()) {
                    System.out.println("");
                    System.out.println("* * * " + currentPlayer + " hat keine Karten mehr auf der Hand! " + currentPlayer + " hat die Runde gewonnen! Gratulation! * * *");
                    System.out.println("");
                    System.out.println("Punkte der Karten auf der Hand:");
                    inputPoints();

                    System.out.println("");
                    System.out.println(DemoApp.getDatabaseRoundWinner());
                    System.out.println("");
                    System.out.println("Ende der Runde " + round);
                    System.out.println("");

                    // * * * ANFORDERUNGEN PUNKT 46 * * *
                    // Bei einem Punktestand über 500 gewinnt der aktuelle Rundengewinner das Spiel
                    if (checkPoints() < 500) {
                        startNewRound();
                    // * * * ANFORDERUNGEN PUNKT 47 * * *
                    } else {
                        System.out.println("Gratuliere, du hast damit das Spiel gewonnen!");
                        gameEnded = true;
                    }

                } else {
                    // * * * ANFORDERUNGEN PUNKT 18 * * *
                    // Uno
                    if (currentPlayer.countCardsInHand() == 1) {
                        cardInput = currentPlayer.sayUno(cardInput);
                        if (currentPlayer.didYouSayUno(cardInput)) {
                            System.out.println(currentPlayer + " sagt Uno");
                        } else {
                            System.out.println(currentPlayer + " hat vergessen UNO zu sagen." + currentPlayer + " muss zwei Karten heben");
                            currentPlayer.getPlusTwoCards(drawPile);
                        }
                    }
                    // Prüft die Karte, die der Spieler spielt
                    currentPlayer = checkPlayedCard(currentPlayer);

                    // Nächster Spieler ist an der Reihe
                    currentPlayer = nextPlayer(currentPlayer, getDirection());
                }
            }
        } while (!gameEnded);
    }//inputCard

    // in eine Zahl umgewandelter Endpunktestand aus der Datenbank
    public int checkPoints() {
        return DemoApp.pointsCheck;
    }// checkPoints

    // Abfrage, ob eine neue Runde gestartet werden soll
    public void startNewRound() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("neue Runde? j/n");
        String yesOrNo = scanner.next();

        if (yesOrNo.equals("j")) {
            newRound();
        } else if (yesOrNo.equals("n")) {
            System.out.println("Du magst keine weitere Runde spielen. Das Spiel wird nun beendet! Bis zum nächsten Mal!");
            System.exit(0);
        } else {
            System.out.println("Dies ist keine gültige Eingabe!");
        }
    }//startNewRound

    // Zeigt den Punktestand der Spieler am Ende einer Runde und startet das Update der Datenbank
    public void inputPoints() {
        for (Player p : players) {
            int points = p.getHand().getHandPoints();
            p.setPoint(points);
            System.out.println(p.getName() + ": " + points);
        }
        // Datenbank wird aktualisiert
        DemoApp.updateDatabase();
    }// inputPoints


    // * * * ANFORDERUNGEN PUNKT 10 * * *
    // * * * ANFORDERUNGEN PUNKT 11 * * *
    // Prüft welche Karte gespielt wurde und reagiert dementsprechend
    private Player checkPlayedCard(Player currentPlayer) {

        // * * * ANFORDERUNGEN PUNKT 32 * * *
        // * * * ANFORDERUNGEN PUNKT 34 * * *
        // * * * ANFORDERUNGEN PUNKT 35 * * *
        // W+4 wurde gespielt
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

        // * * * ANFORDERUNGEN PUNKT 29 * * *
        // WW wurde gespielt
        if (cardInput.contains("WW")) {
            System.out.println("Hi " + currentPlayer + "! Du hast WW gespielt. Du darfst dir eine Farbe aussuchen.");

            pickedColor = currentPlayer.pickColor();
            System.out.println("Hi " + nextPlayer(currentPlayer, getDirection()) + "! Du musst die Farbe " + pickedColor + " spielen");
            return currentPlayer;
        }

        // * * * ANFORDERUNGEN PUNKT 20 * * *
        // * * * ANFORDERUNGEN PUNKT 22 * * *
        // +2 wurde gespielt
        if (cardInput.contains("+2")) {
            currentPlayer = nextPlayer(currentPlayer, getDirection());
            currentPlayer.getPlusTwoCards(drawPile);
            System.out.println("_________________________________");
            System.out.println("Hi " + currentPlayer + "! Du musst zwei Karten ziehen. Der nächste Spieler ist an der Reihe.");
            return currentPlayer;
        }

        // * * * ANFORDERUNGEN PUNKT 23 * * *
        // Richtungswechsel wurde gespielt
        if (cardInput.contains("<->")) {
            changeDirection(direction);
            System.out.println("Hi " + nextPlayer(currentPlayer, direction) + ", Die Richtung wurde geändert. Du bist jetzt an der Reihe!");
            return currentPlayer;
        }

        // * * * ANFORDERUNGEN PUNKT 26 * * *
        // * * * ANFORDERUNGEN PUNKT 28 * * *
        // Aussetzen wurde gespielt
        if (cardInput.contains("<S>")) {
            System.out.println("Sorry " + nextPlayer(currentPlayer, direction) + ", du musst aussetzen!");
            Player nextPlayerHuman = nextPlayer(currentPlayer, direction);
            System.out.println("Hi " + nextPlayer(nextPlayerHuman, direction) + ", du bist dran!");
            return nextPlayerHuman;
        }
        return currentPlayer;
    }//checkPlayedCard

    // wechselt die Richtung
    private void changeDirection(String direction) {
        if (direction.equals("im UZS"))
            setDirection("gegen den UZS");
        else
            setDirection("im UZS");
    }//changeDirection

    // Richtung: im Uhrzeigersinn
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

    // Richtung: gegen den Uhrzeigersinn
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

    // Wechselt zum nächsten Spieler
    private Player nextPlayer(Player currentPlayer, String direction) {

        if (direction.equals("im UZS"))
            return clockwise(currentPlayer);
        else
            return counterClockwise(currentPlayer);
    }//nextPlayer

    //Zeigt das Hilfe-Menü
    private void inputHelp() {
        Scanner input = new Scanner(System.in);
        System.out.println("Benötigst du Hilfe?");
        System.out.println("Für SPIELREGELN drücke 1");
        System.out.println("Für EINGABEMÖGLICHKEITEN drücke 2");
        System.out.println("Für STRAFEN drücke 3");
        System.out.println("Benötigst du keine Hilfe, drücke 4");
        do {
            helpNeeded = input.nextInt();
            if (helpNeeded < 1 || helpNeeded > 4) {
                output.println("Dies ist keine gültige Eingabe!");
            } else {
                break;
            }
        } while (true);
    }//Help Input

    // Entscheidet die Art der Hilfe
    private void updateHelp() {
        switch (helpNeeded) {
            case 1:
                help = new HelpText_Rules();
                break;
            case 2:
                help = new HelpText_Inputs();
                break;
            case 3:
                help = new HelpText_Punishments();
                break;
            case 4:
                System.out.println("Spiel wird fortgesetzt");
                break;
            default:
                break;
        }
    }//update Help

    //Zeigt alle relevanten Spieldaten
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
        else System.out.println();
        System.out.println("Deine Hand:" + player.getHand());
    }//showHandAndTable

    //Zählt alle Karten im Spiel
    public int countAllCards() {
        return discardPile.getSize() + drawPile.getSize() + player1.getHand().getHandSize() + player2.getHand().getHandSize()
                + player3.getHand().getHandSize() + player4.getHand().getHandSize();
    }//countAllCards

}
