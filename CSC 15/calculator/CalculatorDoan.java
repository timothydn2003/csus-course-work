/*
Timothy Doan
October 18,2021
CSC 15
This program takes user input and acts as a calculator to return the correct total of function.
100/100 because I have followed all guidelines on rubric and assignment description.
*/
import java.util.Scanner;

public class CalculatorDoan {
    public static void main(String[] args){
        run();
    }
    //This is the main method that runs the method run.
    public static void run(){
        Scanner scan = new Scanner(System.in);
        System.out.print("How many times do you want to use this software: ");
        int count = scan.nextInt();
        for(int i = 0; i < count; i++){
            menu();
            System.out.print("Enter the operation: ");
            String operation = scan.next();
            System.out.print("Enter number between 0-9: ");
            int x = scan.nextInt();
            System.out.print("Enter number between 0-9: ");
            int y = scan.nextInt();
            int calculate = calculate(x,y,operation);
            String conversion = convertOperation(operation);
            String operand1 = convertOperand(x); 
            String operand2 = convertOperand(y);
            System.out.println();
            System.out.println(operand1 + " " + conversion + " " + operand2 + " equals " + calculate);
        }
    }
    //This is the method that determines how many times the questions will be asked and it takes the userinput and stroes them into variables for the parameters.
    public static void menu(){
        for (int i = 0; i < 20; i++){
            System.out.print("*");
        }
        System.out.println();
        System.out.println("To do addition enter +");
        System.out.println("To do multiplication enter *");
        System.out.println("To do subtraction enter -");
        System.out.println("To do exponent enter ^");
        System.out.println("To do division enter /");
        System.out.println("To do modulus enter %");
        for (int i = 0; i < 20; i++){
            System.out.print("*");
        }
        System.out.println();
    }
    //This method prints out the menu and gives the user a brief description of the software.
    public static int calculate(int x, int y, String operation){
        switch(operation){
            case "+": 
                return x = x+y;
            case "*":
                return x = x*y;
            case "-":
                return x = x-y;
            case "^":
                return x = (int)Math.pow(x,y);
            case "/":
                return x = x/y;
            case "%":
                return x = x%y;
            default:
                return 0;
        }
    }
    //This method uses a switch case and it takes the parameters from the run method to calculate the total.
    public static String convertOperand(int x){
        if(x==0){
            return "zero";
        }if(x==1){
            return "one";
        }if (x==2){
            return "two";
        }if(x==3){
            return "three";
        }if(x==4){
            return "four";
        }if(x==5){
            return "five";
        }if(x==6){
            return "six";
        }if(x==7){
            return "seven";
        }if(x==8){
            return "eight";
        }if(x==9){
            return "nine";
        }else{
          return "NUMBER NOT IN BOUNDS";
        }
    }
    //This method takes parameters from the run method and returns the integers from the user in word form.
    public static String convertOperation(String operation){
        if(operation.equals("+")){
            return "add";
        }else if(operation.equals("*")){
            return "multiply";
        }else if(operation.equals("-")){
            return "subtract";
        }else if(operation.equals("^")){
            return "to the power";
        }else if(operation.equals("/")){
            return "divide";
        }else if(operation.equals("%")){
            return "modulus";
        }else{
            return "not an operation";
        }

    }
    //This method takes parameters from the run method and takes the operation string to return it as word form.
}
  