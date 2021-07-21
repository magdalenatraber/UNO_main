package uno.Database;

import uno.Game;
import uno.Player.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class DemoApp {
    private static final String CREATETABLE = "CREATE TABLE Sessions (Player varchar(100) NOT NULL, Session int NOT NULL, Round int NOT NULL, Score int NOT NULL, CONSTRAINT PK_Sessions PRIMARY KEY (Player, Session, Round));";
    private static final String INSERT_TEMPLATE = "INSERT INTO Sessions (Player, Session, Round, Score) VALUES ('%1s', %2d, %3d, %4d);";
    private static final String SELECT_BYPLAYERANDSESSION = "SELECT Player, SUM(Score) AS Score FROM Sessions WHERE Player = '%1s' AND Session = %2d;";
    private static final String SELECT_ACTUALPOINTS = "SELECT Player, Score FROM Sessions WHERE Player = '%1s' AND Session = %2d;";
    public static String requestedPoints;
    public static String databaseRoundWinner;
    public static SqliteClient client;
    public static int pointsCheck;

    // Client wird erstellt
    static {
        try {
            client = new SqliteClient("demodatabase.sqlite");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public DemoApp() throws SQLException {
    }

    // Ermittelt den Gewinner der aktuellen Runde
    public static String findWinnerName() {
        String winner = "";
        for (int i = 0; i < Game.players.length; i++) {
            if (Game.players[i].getHand().getHandPoints() == 0) {
                winner = Game.players[i].getName();
            }
        }
        return winner;
    }// findWinnerName

    //Ermittelt die Punkte auf der Hand jedes Spielers am Rundenende
    public static int pointsForWinner() {
        int points = 0;
        for (Player p : Game.players) {
            points += p.getHand().getHandPoints();
        }
        return points;
    }// pointsForWinner

    // Gibt den Rundengewinner zurück
    public static String getDatabaseRoundWinner() {
        return databaseRoundWinner;
    } //getDatabaseRoundWinner

    // * * * ANFORDERUNGEN PUNKT 51 * * *
    // Gibt die aktuellen Punkte aller Spieler während der laufenden Runde aus
    public static String requestedPointsAll() {
        try {
            for (Player p : Game.players) {
                ArrayList<HashMap<String, String>> results = client.executeQuery(String.format(SELECT_BYPLAYERANDSESSION, p.getName(), 1));
                for (HashMap<String, String> map : results) {
                    requestedPoints = ("Dein aktueller Punktestand beläuft sich auf " + map.get("Score") + " Punkte!");
                }
                System.out.println("Spieler " + p.getName() + ": " + requestedPoints);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }// requestedPointsAll

    //Startet und initialisiert die Database am Anfang des Spiels
    public static void startDatabase() {
        try {
            if (client.tableExists("Sessions")) {
                client.executeStatement("DROP TABLE Sessions;");
            }
            client.executeStatement(CREATETABLE);

        } catch (SQLException ex) {
            System.out.println("Ups! Something went wrong: " + ex.getMessage());
        }
    } //startDatabase

    // * * * ANFORDERUNGEN PUNKT 43 * * *
    // * * * ANFORDERUNGEN PUNKT 50 * * *
    // updatet die Database nach jeder gespielten Runde
    public static void updateDatabase() {
        try {
            for (int i = 0; i < Game.players.length; i++) {
                int round = Game.getRound();

                // Ermittelt den Gewinner und verteilt die Punkte dementsprechend

                // Verlierer bekommen je 0 Punkte für die Runde gutgeschrieben
                if (Game.players[i].getHand().getHandPoints() != 0) {
                    client.executeStatement(String.format(INSERT_TEMPLATE, Game.players[i].getName(), 1, round, 0));

                    // Gewinner bekommt die Punkte aller Handkarten der Verlierer gutgeschrieben
                } else {
                    int points = pointsForWinner();
                    client.executeStatement(String.format(INSERT_TEMPLATE, Game.players[i].getName(), 1, round, points));
                }
            }

            String name = findWinnerName();

            // Endpunktestand der Runde wird für den Gewinner ausgelesen
            ArrayList<HashMap<String, String>> results = client.executeQuery(String.format(SELECT_BYPLAYERANDSESSION, name, 1));
            for (HashMap<String, String> map : results) {
                databaseRoundWinner = (map.get("Player") + ", du hast nun insgesamt " + map.get("Score") + " Punkte! Mach weiter so, dann kannst du gewinnen!");
                pointsCheck = Integer.parseInt(map.get("Score"));
            }

        } catch (SQLException ex) {
            System.out.println("Update - Ups! Something went wrong: " + ex.getMessage());
        }
    }//updateDatabase


}
