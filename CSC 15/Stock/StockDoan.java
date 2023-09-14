/*
Timothy Doan
10/4/2021
This program calculates the profits from buying and selling stocks by taking user input and calculating it.
100/100 I have included all requirments from both the rubruc and the assignment details.
*/
import java.util.Scanner;
//This is a import that lets us use scanner in our program.
public class StockDoan {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        description();
        run(scan);
    }
    //This is the main method that runs all the other methods.
    public static void run(Scanner scan){
        System.out.print("Enter the number of customers using this app: ");
        int time = scan.nextInt();
        for(int i =0; i<time;i++){
            scan.nextLine();
            System.out.print("Enter the name of stock: ");
            String stock = scan.nextLine();
            System.out.print("Enter number of stocks purchased: ");
            double count = scan.nextDouble();
            System.out.print("Enter the purchase price per stock: ");
            double price = scan.nextDouble();
            System.out.print("Enter the current price of stock: ");
            double current = scan.nextDouble();
            System.out.print("Enter the commission rate: ");
            double rate = scan.nextDouble();
            //This section of the method takes the user input and stores it into a variable.
            stars();
            System.out.println();
            System.out.println("Here is the information about your transaction");
            System.out.println("Stock: " + stock);
            System.out.println("Number of the stocks bought: " + count); 
            System.out.println("Purchase price per stock: " + price);
            double amount = getTotal(price, count);
            double commission = getCommission(amount, rate);
            System.out.println("Total commission when buying the stock: " + commission);
            System.out.println("Selling price of the stock: " + current);
            double sell = getTotal(current,count);
            double sellcommission = getCommission(sell, rate);
            System.out.println("Total commission paid when selling the stock: " + sellcommission);
            double totalProfit = profit(sell, amount, commission, sellcommission);
            System.out.println("The profit you made buying and selling this stock: " + totalProfit);
            stars();
            System.out.println();
            //This section of the method takes those variables and calculates them.
        }
        System.out.println("Come back soon!");  
    }  
    //This whole method runs the other sub methods.
    public static void description(){
        System.out.println("Welcome to stock calculator");
        System.out.println("This app calculates the amount of profit that you can make when buying and selling some stocks");
        System.out.println("when you buy or sell stock you need to pay commission");
        System.out.println("Answer a few questions then you will see the profit you made");
        stars();
        System.out.println();
    }
    //This method prints out the description of what the code is supposed to do.
    public static void stars(){
        for(int i = 0; i<=100;i++){
            System.out.print("*");
        }
    }
    //This method prints out the stars the divides the sections in the code.
    public static double getTotal(double price, double count){
        double amount = price * count;
        return amount;
    }
    //This method finds the total price from buying the stocks.
    public static double getCommission(double price, double rate){
        double commission = price * (rate/100);
        return commission;
    }
    //This method calculates the commssion from the total maount spent.
    public static double profit(double purchaseAmount, double sellAmount, double buyCommission, double sellCommission){
        double profit = purchaseAmount - sellAmount - buyCommission - sellCommission;
        return profit;
    }
    //This method calculates the total profit made.
  
}
