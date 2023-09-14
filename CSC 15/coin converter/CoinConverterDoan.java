/* 
Timothy Doan
September 20,2021
This program breaks down an amount of pennies into dollars, quarters, dimes, nickels, and pennies.
100/100 because I have met and completed all requirements in rubric and assignment description.
*/
public class CoinConverterDoan {
    public static void main(String[] args){
        //this method will run the other methods to print out the code
        description();
        convert();
    }
    public static void description(){
        //This method will be the intoduction to the code
        System.out.println("Welcome to the Coin Converter");
        System.out.println("Tell many the number of pennies you have,");
        System.out.println("I will tell you the number of dollar bills, quarters, dimes, nickels, and pennies");


    }
    public static void convert(){
        //this section will contain the variables and their conversions from pennies
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
        //this section prints out the variables after they are converted
        System.out.println(pennies + " pennies are equal to:");
        System.out.println(dollars + " dollar (s)");
        System.out.println(quarters + " quarter (s)" );
        System.out.println(dimes + " dime (s)");
        System.out.println(nickels + " nickel (s)");
        System.out.println(penny + " penny (s)");
    }
}
