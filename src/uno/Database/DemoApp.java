package uno.Database;

import uno.Game;
import uno.Player.Player;
import uno.Player.PlayerHuman;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class DemoApp {
    private static final String CREATETABLE = "CREATE TABLE Sessions (Player varchar(100) NOT NULL, Session int NOT NULL, Round int NOT NULL, Score int NOT NULL, CONSTRAINT PK_Sessions PRIMARY KEY (Player, Session, Round));";
    private static final String INSERT_TEMPLATE = "INSERT INTO Sessions (Player, Session, Round, Score) VALUES ('%1s', %2d, %3d, %4d);";
    private static final String SELECT_BYPLAYERANDSESSION = "SELECT Player, SUM(Score) AS Score FROM Sessions WHERE Player = '%1s' AND Session = %2d;";

    public static String findWinnerName() {
        String winner = "";
        for (int i = 0; i < Game.players.length - 1; i++) {
            if (Game.players[i].getHand().getHandPoints() == 0) {
                winner = Game.players[i].getName();
            }
        }
        return winner;
    }

    public static int pointsForWinner() {
        int points = 0;
        for (Player p : Game.players) {
            points += p.getHand().getHandPoints();
        }
        return points;
    }


    public static void startDatabase() {
        try {
            SqliteClient client = new SqliteClient("demodatabase.sqlite");
            if (client.tableExists("Sessions")) {
                client.executeStatement("DROP TABLE Sessions;");
            }

            client.executeStatement(CREATETABLE);

            int points = pointsForWinner();
            String name = findWinnerName();

            client.executeStatement(String.format(INSERT_TEMPLATE, name, 1, 1, points));
            client.executeStatement(String.format(INSERT_TEMPLATE, "Hans", 1, 1, 0));
            client.executeStatement(String.format(INSERT_TEMPLATE, "Franz", 1, 2, 20));
            client.executeStatement(String.format(INSERT_TEMPLATE, "Hans", 1, 2, 100));

            ArrayList<HashMap<String, String>> results = client.executeQuery(String.format(SELECT_BYPLAYERANDSESSION, name, 1));

            for (HashMap<String, String> map : results) {
                System.out.println(map.get("Player") + " hat in dieser Runde " + map.get("Score") + " Punkte gewonnen!");
            }
        } catch (SQLException ex) {
            System.out.println("Ups! Something went wrong: " + ex.getMessage());
        }
    }


}
