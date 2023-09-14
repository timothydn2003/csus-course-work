/*Timothy Doan
May 3, 2022
CSC 20
This program uses recursion in many different methods
100/100 because I have followed all guidelines and rules on rubric*/
import java.util.*;

public class RecursionDoan{
    public static void main(String[] args){
        int[] nums = {1,2,2,1};
        System.out.println("Testing palindrome method");
        System.out.println(Arrays.toString(nums) + " is a palindrome? " + palindrome(nums, 0));
        //palindrome
        stars();

        System.out.println("Testing sum of digits method");
        System.out.println("The sum of digits in 3456 is " +sum(3456));
        //sum method

        stars();
        String[] s = {"Hello", "Bye", "Said", "song", "Building"};
        System.out.println("Testing the longest string method");
        System.out.println("The longest string is the array " + Arrays.toString(s) + " is " + longest(s, 1, s[0]));  
        //longest string method

        stars();
        String s1 = "hello";
        String s2 = "helloo";
        String s3 = "tomorrow";
        String s4 = "tomorrow";
        System.out.println("Testing equals method");
        System.out.println("Are " + s1 + " and " + s2 + " the same? "+equals(s1,s2,0));
        System.out.println("Are " + s3 + " and " + s4 + " the same? "+equals(s3,s4,0));
        //checks if 2 string are equal method
        stars();

        LinkedList<Integer> numbers = new LinkedList<Integer>();
        numbers.add(5);
        numbers.add(7);
        numbers.add(8);
        numbers.add(12);
        System.out.println("Testign sum of integers in linked list");
        System.out.println("The sum of numbers in the linked list [5,7,8,12] is " + listSum(numbers,0));
        //Linked list sum method

    }
    public static boolean palindrome(int[] nums, int index) {
        if(index==nums.length/2){
            return true;
        }if(nums[index]!=nums[nums.length-1-index]){
            return false;
        }
        return true && palindrome(nums,index+1);
    }
    //Checks if array is a palindrome
    public static int sum(int n){
        if(n==0){
            return n;
        }else{
            return (n%10)+sum(n/10);
        }
    }
    //adds all digits of an integer together
    public static String longest(String[] s, int index, String min){
        if(index==s.length){
            return min;
        }
        if(s[index].length()>min.length()){
            min = s[index];
        }
        return longest(s,index+1,min);
    }
    //finds longest string in array

    public static boolean equals(String s1, String s2, int index){
        if(s1.length() != s2.length()){
            return false;
        }if(index==s1.length()){
            return true;
        }if(s1.charAt(index)!=s2.charAt(index)){
            return false;
        }
        return true && equals(s1,s2,index+1);
    }
    //checks if 2 strings are equal
    public static int listSum(LinkedList<Integer> numbers, int index){
        if(index>numbers.size()-1){
            return 0;
        }else{
            return (Integer)numbers.get(index) + listSum(numbers,index+1);
        }
    }
    //Sum of linked list
    public static void stars(){
        for(int i =0;i<75;i++){
            System.out.print("*");
        }
        System.out.println();
    }
}