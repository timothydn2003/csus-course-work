/*Timothy Doan
September 27,2021
This program converts Centigrade to Fahrenheit.
100/100 because I have met all requirments on rubric as well as assignment description.*/

public class TempConversionDoan {
    public static final double FAR = 9.0/5;
    //This is the main method and it will run all the other methods to print the output
   public static void main(String[] args){
       description();
       farToCenti();

   }
   //this method will print out the stars using a for loop and also print out the Welcome statements
   public static void description(){
       for(int i = 0; i <2; i++){
           for(int j = 1; j < 100; j++){
               System.out.print("*");
           }
           System.out.println();
       }
       System.out.println("Welcome to the Temp converter app");
       System.out.println("This app provides the temperatures in both centigrade and Fahrenheit");
       for(int i = 0; i < 2 ; i++){
           for(int j = 1; j<100; j++){
               System.out.print("*");
           }
           System.out.println();
       }
       System.out.println();
    }
    //This method will print out the conversion of Centigrade to Fahrenheit
   public static void farToCenti(){
       System.out.println("Centigrade\tFahrenheit");
       for (int i = 0; i<= 45; i++){
           double fahrenheit = FAR * i + 32;
           System.out.println(i+"\t\t"+ (int)fahrenheit);
       }
   }
}
