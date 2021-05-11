package uno;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Game game = new Game(input, System.out);
        game.run();
        input.close();
        System.out.println("Program ends");
        System.out.println();



    } // main



} // class
