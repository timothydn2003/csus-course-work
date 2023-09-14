/*
Timothy Doan 
CSC 15
October 28, 2021
This program takes user input to decrypt and encrypt messages.
100/100 because I have followed all directions and requirments.
*/
import java.util.Scanner;

public class CeaserCipherDoan {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        run(scan);
    }
    //Main method that decalres scanner and the method run.
    public static void run(Scanner scan){
        System.out.print("How many times do you want to use this program: ");
        int count = scan.nextInt();
        for(int i = 0; i < count; i ++){
            menu();
            System.out.print("Enter your choice ----> ");
            String choice = scan.next();
            char letter = choice.charAt(0);
            if(letter == 'e'){ //condition if user selects option e
                scan.nextLine();
                System.out.print("Your message: ");
                String message = scan.nextLine();
                System.out.print("Encoding Key: ");
                int key = scan.nextInt();
                String encrypted = encrypt(message, key);
                System.out.println("The encrypted message is:\n" + encrypted);
            }else if(letter == 'd'){//condition if user selects option d
                scan.nextLine();
                System.out.print("Your message: ");
                String message = scan.nextLine();
                System.out.print("Encoding Key: ");
                int key = scan.nextInt();
                String decrypted = decrypt(message, key);
                System.out.println("The decrypted message is:\n" + decrypted);
            }else{
                System.out.print("NOT A VALID INPUT!");
            } 
        }
    }
    //This method uses a for loop to determine how many times the program will be run and it takes user input and passes them as parameters for the other methods.
    public static void menu(){
        for(int i = 0; i<10;i++){
            System.out.print("*");
        }
        System.out.println();
        System.out.println("Enter e to encrypt your message");
        System.out.println("Enter d to decrypt your message");
        for(int i = 0; i<10;i++){
            System.out.print("*");
        }
        System.out.println();
    }
    //Prints out menu that gives user information about program.
    public static String encrypt(String message, int key){
        message = message.toUpperCase();//takes the message and converts it to all uppercase.
        String encrypted = "";
        for(int i = 0; i < message.length(); i++){
            char letter = message.charAt(i);
            if(letter >= 'A' && letter <= 'Z'){
                letter += key;
            }if(letter > 'Z'){
                letter -=26;
            }else if(letter <'A'){
                letter += 26;
            }if(letter == ' '){ //this if statement is for if there is a space. It will be substituted by a colon.
                letter = ':';
            }
            encrypted += letter;
       }
       return encrypted;
    }
    //This method uses parameters that get passed into a for loop which will encrypt the users message.
    public static String decrypt(String message, int key){
        message = message.toUpperCase(); //takes the message and converts it to all uppercase.
        String decrypted = "";
        for(int i = 0; i<message.length(); i++){
            char letter = message.charAt(i);
            if(letter ==':'){ //this if statement is for if there is a colon. It will be replaced by a space.
                letter = ' ';
            }else if(letter >= 'A' && letter <= 'Z'){
                letter -= key;
            if(letter < 'A'){
                int difference = 'A' - letter;
                letter = (char)('Z' - difference +1);
            }else if(letter > 'Z'){
                int difference = 'Z' - letter;
                letter = (char)('A' + difference + 1);
            }
        }
            decrypted = decrypted + letter;
        }
        return decrypted;
    }
    //this method uses a for loop to decrypt the users message
}


