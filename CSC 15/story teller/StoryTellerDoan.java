/* 
Timothy Doan
October 12, 2021
CSC 15
This program takes user input by asking a few questions to generate a story.
100/100 becuase i have followed all guidelines provided by rubric and assignment desciption.
*/


import java.util.Scanner;

public class StoryTellerDoan{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        //This is declaring the scanner and will take user input.
        System.out.print("How many stories are you making: ");
        int number = scan.nextInt();
        for(int i=0; i<number;i++){
            System.out.println("Answer a few questions and I will make up a story for you.");
            System.out.println();
            story(scan);
            System.out.println();
        }
        //This for loop determines how many stories will be printed.
    }
    //This is the main mathod that declares the scanner and the for loop that allows the code to be run a certain amount of times.
    public static void story(Scanner scan){
        scan.nextLine();
        System.out.print("What day of the week was it: ");
        String day = scan.nextLine();
        System.out.print("What is the main characters first name: ");
        String name = scan.next();
        String letter = name.substring(0,1).toUpperCase();
        String newName = name.toUpperCase();
        System.out.print("What store is the character travelling too (Walmart, Target  etc): ");
        String place = scan.next();
        scan.nextLine();
        System.out.print("Who stops the main character from getting to their destination(cop, stranger etc): ");
        String character = scan.nextLine();
        System.out.print("What did the character give the main character: ");
        String item = scan.nextLine();
        System.out.print("How many did they give to the main character: ");
        int amount = scan.nextInt();
        scan.nextLine();
        System.out.print("Was the main character happy or sad about this: ");
        String emotion = scan.next();
        scan.nextLine();
        System.out.print("Who did the main character show this gift too: ");
        String person = scan.nextLine();
        System.out.print("What did the main character buy from the store: ");
        String purchases = scan.nextLine();
        System.out.print("What was the exact price of the purchase so you know the rounded up amount of cash you need to to give to te cashier: ");
        double price = scan.nextDouble();
        double newPrice = Math.ceil(price);
        scan.nextLine();
        System.out.print("What does the main character do when they get home: ");
        String home = scan.nextLine();
        System.out.println();
        //This section asks the user a variety of questions and stores them into variables.
        System.out.println("It was " + day + " when " + name + " whose name starts with " + letter + " decided to go to " + place + ".");
        System.out.println("On their way there, they are stopped by a " + character + ".");
        System.out.println("The " + character + " gives " + name + " " + amount + " " + item + ".");
        System.out.println(name + " is so " + emotion + " that they show this gift to " + person + ".");
        System.out.println(name + " goes on to buy " + purchases + " from " + place + ".");
        System.out.println("It ended up costing $" + price + " so " + name + " gives the cashier $" + newPrice);
        System.out.println(name + " goes home to " + home + ".");
        System.out.println("On his way home, he sees " + newName + " painted on the wall. He is suprised by this incident but doesn't think much of it. The End." );
        //This section takes the variables and uses them to generate a story.
    }
}