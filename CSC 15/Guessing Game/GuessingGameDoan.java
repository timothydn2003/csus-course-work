/*
Timothy Doan
CSC 15
November 1,2021
This program is guessing game that asks the user to input numbers until it mathces the random number produced by the computer
100/100 because i have followed all guidlines and requirments on rubric.
*/
import java.util.Random;
import java.util.Scanner;

public class GuessingGameDoan {
    public static final int MIN = 500;
    public static final int MAX = 600;
//These are class constants later used in the program
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        intro();
        run(scan);
    }
    //This is the main method that declares the scanner and runs the other methods.
    public static void intro(){
        System.out.println("Hi, lets play a game!");
        System.out.println("This game is a guessing game.");
        System.out.println("You will guess a number between 500 and 600 \nuntil you get the one I am thinking of.");
        System.out.println("After each guess, if it is wrong, \nI will tell you if the number is higher or lower than the one you guessed.");
        System.out.println();
    }
    //This method prints out the explanation of the game.
    public static void run(Scanner scan){
        String answer = "yes";
        while(answer.equals("yes")){
            Random rand = new Random();
            int random = rand.nextInt(100) + 500;
            int num = playGame(scan, random);
            System.out.println("You guessed correct in " + num + " guesses" );
            System.out.println();
            System.out.print("Do you want to play again, yes or no: ");
            answer = scan.next();
            System.out.println();
        }
        //This while loop is for if the user wants to play, it runs the method playGame within it.
        System.out.println("Thanks for playing!");

    }
    //This method asks the user if they want to play or not.
    public static int playGame(Scanner scan, int random){
        int count = 0;
        System.out.println("Enter a number between 500 and 600");
        System.out.print("Enter your guess --> ");
        int guess = scan.nextInt();
        while(guess != random){
            if(guess > random){
                System.out.println("Enter a lower number");
                System.out.print("Enter your guess --> ");
                guess = scan.nextInt();
            }else if(guess < random){
                System.out.println("Enter a higher number.");
                System.out.print("Enter your guess --> ");
                guess = scan.nextInt();
            }
            count++;
        }
        //This while checks if the number is lower or higher and prompts the user. It also checks how many guesses were made.
        return count;

    }
    //This method asks the user to input numbers until it reaches the number that was generated
}
