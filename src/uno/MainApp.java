package uno;

import uno.Cards.Card;
import uno.Cards.CardColor;
import uno.Cards.CardType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Game game = new Game(input, System.out);
        game.Run();
        input.close();
        System.out.println("Program ends");
        System.out.println();



    } // main



} // class
