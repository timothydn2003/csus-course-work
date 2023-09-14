/*Timothy Doan
February 6, 2022
CSC 20
This program produces random numbers and checks to see if any of them are matching. If there are matching numbers then an amount is added to the total;
100/100 because i have followed all the rules on assignment description and rubric.
*/
import java.util.Random;
import java.util.Scanner;

public class MatchingDoan{
    public static void main(String[] args){
        String answer = "";
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        while(!answer.equalsIgnoreCase("q")){//This while loop is to determine how many times the program is run.
            description();
            System.out.print("What is your name: ");
            String name = scan.next();
            System.out.println("Hello " + name + " lets start playing\n");
            play(rand,scan);
            System.out.print("Hit p/P to let another person play or hit q/Q to quit the program: ");
            answer = scan.next();
            System.out.println();
        }
        System.out.println("Goodbye! Come back and play again soon!");
    }
    //This is the main method that calls the rest of the methods.
    public static void description(){
        for(int i = 0;i<50;i++){
            System.out.print("*");
        }
        System.out.println();
        System.out.println("*Welcome to the matching game. I will generate three random numbers for you.*\n*If two of the numbers match you win 100, if you get three matching numbers you win 300 dollars*");
        for(int i = 0;i<50;i++){
            System.out.print("*");
        }
        System.out.println();
    }
    //This method prints out the description of the program.
    public static void play(Random rand, Scanner scan){
        int total = 0;
        String answer = "";
        int num1 = 0, num2 = 0, num3 = 0;
        while(!answer.equalsIgnoreCase("q")){//This while loop is to determine how many times this method is run.
            num1 = getRand(rand);//Calling this method 3 times is to produce 3 random numbers
            num2 = getRand(rand);
            num3 = getRand(rand);
            System.out.println("You got: " + num1 + " " + num2 + " "+ num3);
            int match = match(num1,num2,num3);//Calling this method is to check how many of these numbers are matching.
            if(match == 2){
                total += 100;
                System.out.println("You got two matches, you win $100");
            }else if(match ==3){
                total += 300;
                System.out.println("You got 3 matches, you win $300");
            }else{
                System.out.println("Sorry no match");
            }
            System.out.println();
            System.out.print("Hit p/P to continue, or q/Q to quit: ");
            answer = scan.next();

        }
        System.out.println("Total amount you won: $" + total);
        System.out.println();
    }
    //This method is the play method and it checks how many matches there are to add money to the total.
    public static int getRand(Random rand){
        int number = rand.nextInt(9)+1;
        return number;
    }
    //This method produces the random numbers.
    public static int match(int num1, int num2, int num3){
        int matches = 0;
        if(num1==num2&&num1==num3){
            matches = 3;
            return matches;
        }else if(num1==num2&&num1!=num3){
            matches = 2;
            return matches;
        }else if(num2==num3&&num2!=num1){
            matches =2;
            return matches;
        }else if(num3==num1&&num3!=num2){
            matches = 2;
            return matches;
        }else{
            matches = 0;
            return matches;
        }
    }
    //This method checks to see how many matches are in the 3 random numbers that are produced.
}
