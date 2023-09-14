/*
Timothy Doan
11/16/21
CSC 15
This program takes user input and determines how many times the ant will slip off the building.
100/100 because I have followed all direction on assignment description and rubric.
*/
import java.util.Random;
import java.util.Scanner;

public class AntsDoan{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        run(rand,scan);
    }
    //This is the main method and it declares the scanner and the random object. It also runs the run method.
    public static int getHeight(Scanner scan){
        int height = 0;
        while(height<=0){
            System.out.print("What is the height of the building: ");
            while(!scan.hasNextInt()){
                scan.next();
                System.out.print("What is the height of the building: ");
            }
            height = scan.nextInt();
        }
        return height;
    }
    //This method asks the user for the height of the building and makes sure the user inputs an integer and a number breater than 0.
    public static int move(int height, Random rand){
        int distance = 0;
        int fall = 0;
        do{
            int n1 = rand.nextInt(1-0+1)+0;
            if(n1 == 1)
                distance++;
            else{
                fall++;
                distance = 0;
            }
        }while(distance != height);
            return Math.abs(fall);
    }
    //This method creates a random number and also determines how many times the ant slips.
    public static void run(Random rand, Scanner scan){
        boolean repeat = true;
        while(repeat){
            int height = getHeight(scan);
            int slip = move(height, rand);
            System.out.println("To climb up the building of height " + height + " the ant slipped " + slip + " times.");
            System.out.print("Another run: ");
            String answer = scan.next();
            if(answer.equalsIgnoreCase("no"))
                repeat = false;
        }
    }
    //This method runs all the other methods and prints the results. It also asks the user if they want to run the program again.
}