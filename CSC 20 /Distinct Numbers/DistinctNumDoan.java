/*Timothy Doan
January 30, 2022
CSC 20
This program prompts the user to input integers and removes the repeated inputtted numbers.I then returns this list of numbers.
100/100 because I have followed all guidelines on rubric and assignment description.
*/
import java.util.Scanner;

public class DistinctNumDoan {
    public static void main(String[] args){
        int[] newNum = getInput();
        display(newNum);
    }
    //This is the main method which first calls the getInput method that gets the user input and puts it into an array. Then is calls the display method to display the results.
    public static int[] getInput(){
        System.out.println("Welcome to distinct numbers\nI will remove repeated numbers and display the numbers you just entered");
        int[] nums = new int[10];
        Scanner scan = new Scanner(System.in);
        for(int i = 0; i < nums.length;i++){
            System.out.print("Enter a number: ");
            int x = scan.nextInt();
            boolean repeat = found(nums,x);
            if(repeat == false){
                nums[i] = x;
            }else if(repeat == true){
                i = i - 1;
            }
        }
        return nums;
    }
    //This method asks the user to input numbers and then adds them to an array.
    public static Boolean found(int[] nums, int x){
        for(int i = 0; i < nums.length;i++){
            if(x == nums[i]){
                return true;
            }
        }
        return false;
    }
    //This method makes sure that the integers that are being inputted are not repeated integers.
    public static void display(int[] newNum){
        System.out.println("I filtered out all the repeated numbers you entered and kept only one copy of each number");
        System.out.println("Here is the list of your numbers");
        System.out.print("{");
        for(int i = 0; i < newNum.length-1; i++){
            System.out.print(newNum[i]+",");
        }
        System.out.println(newNum[newNum.length-1]+"}");
    }
    //This method displays the results in an array format.
}
