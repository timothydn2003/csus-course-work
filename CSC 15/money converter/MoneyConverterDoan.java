
public class MoneyConverterDoan {
    public static void main(String[] args){
        description();
        converter();
    }
    public static void description(){
        System.out.println("Welcome to the Coin Converter");
        System.out.println("Tell many the number of pennies yopu have,");
        System.out.println("I will tell you the number of dollar bills, quarters, dimes, nickels, and pennies");


    }
    public static void converter(){
        int pennies = 6789;
        int dollars = pennies / 100;
        int penniesLeft = pennies % 100;
        int quarters = penniesLeft / 25;
        penniesLeft = penniesLeft % 25;
        int dimes = penniesLeft / 10;
        penniesLeft = penniesLeft % 10;
        int nickels = penniesLeft / 5;
        penniesLeft = penniesLeft % 5;
        int penny = penniesLeft;
        System.out.println(pennies + " pennies are equal to:");
        System.out.println(dollars + " dollar (s)");
        System.out.println(quarters + " quarter (s)" );
        System.out.println(dimes + " dime (s)");
        System.out.println(nickels + " nickel (s)");
        System.out.println(penny + " penny (s)");



    }
    
}
