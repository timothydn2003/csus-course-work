/*Timothy Doan
CSC 20
March 7 2021
Social Media App
100/100 because I have followed all directions and guidelines on rubric.
This app adds followers to list and includes many fuctions like a social media app.
*/
import java.util.ArrayList;
import java.util.Scanner;

public class SocialMediaDoan{
}
class User implements Comparable{
    private String first;
    private String last;
    private String username;
    private boolean followBack;
    //instance variables
    public User(String first, String last, String username, boolean followBack){
        this.first = first;
        this.last = last;
        this.username = username;
        this.followBack = followBack;
    }
    //initialzing the instance variables
    public String getFirst(){
        return first;
    }
    public String getLast(){
        return last;
    }
    public boolean getFollow() {
        return followBack;
    }
    //getter methods
    public void setFirst(String newFirst){
        first = newFirst;
    }
    public void setLast(String newLast){
        last = newLast;
    }
    //setter methods
    public void unfollow(){
        followBack = false;
    }
    public void follow(){
        followBack = true;
    }
    // setting the followBack variable to true or false
    public boolean equals(User other){
        return this.first.equalsIgnoreCase(other.first) && this.last.equalsIgnoreCase(other.last);
    }
    //checks if first and last name is equal to someone else
    public String toString(){
        String s = "";
        s += "User name: " + username + "\n";
        s += "First name: " + first + "\n";
        s += "Last name: " + last + "\n";
        String follow = "";
        if(followBack == true){
            follow = "You are following this person.";
        }else{
            follow = "You are not following this person";
        }
        s += follow;
        return s + "\n****************************************";
    }
    //Formats all the followers 
    public int compareTo(Object o){
        User u = (User)o;
        return this.username.compareTo(u.username);
    }
    //Compares the usernames
}
class SocialMedia{
    private ArrayList<User> app;
    public SocialMedia(){
        app = new ArrayList<User>();
    }
    //Initializing array list
    public void followBack(String first, String last){
        String s = first + " " + last; 
        for(int i = 0; i < app.size(); i++) {
            String s1 = app.get(i).getFirst() + " " + app.get(i).getLast(); 
            if(s.equalsIgnoreCase(s1)) {
                app.get(i).follow();
            } 
        }
    }
    //This method follows people back
    public boolean follow(String first, String last, String username, boolean followBack){
        User u = new User(first, last, username, followBack);
        boolean b = search(first, last);
        boolean followed = false;
        if(b){
            return false;
        }   
        if(app.size() == 0){
            app.add(u);
            followed = true;
        }
        for(int i = 0;i < app.size(); i++){
            if(u.compareTo(app.get(i))<0){
                app.add(i,u);
                followed = true;
                break;
            }
        }
        if(!followed){
            app.add(u);
            followed = true;
        }
        return followed;

    }
    //This method adds followers to the list
    public boolean remove(String first, String last){
        for(int i = 0; i < app.size(); i++){
            if(app.get(i).getFirst().equals(first) && app.get(i).getLast().equals(last)){
                app.remove(i);
                return true;
            }
        }
        return false;
    }
    //THis removes followers
    public boolean search(String first, String last){
        for(int i= 0; i < app.size(); i++){
            if(first.equals(app.get(i).getFirst()) && last.equals(app.get(i).getLast()))
            return true;
        }
        return false;
    }
    //This searches through the list
    public int followerCounts(){
       return app.size();
    }
    public int followingCounts(){
        int count = 0;
        for(int i = 0; i < app.size();i++){
            if(app.get(i).getFollow() == true){
                count++;
            }
        }
        return count;
    }
    //Gets follower and following amounts
    public ArrayList<User> getList(){
        return app;
    }
    public String toString(){
        String s = "";
        for(int i = 0;i<app.size();i++){
            s += app.get(i).toString() + "\n\n";
        }
        return s;
    }
}
//Formats all the followers
class Driver{
    public static void main(String[] args){
        SocialMedia s1  = new SocialMedia();
        //creating the object for Social media class
        s1.follow("Lebron", "James",  "LebronJ", false); 
        s1.follow("Justin", "Beiber",  "JustinB", true); 
        s1.follow("Timothy", "Doan", "TimothyD", true);
        s1.follow("Bill", "Fitch", "BillF",true);
        s1.follow("Tommy", "Hilfiger", "TommyH", false);
        //adding objects to the list

        System.out.println("Your followers informations\n");
        System.out.println(s1.toString());
        //Prints out followers
        System.out.println("Removing Justin Beiber from your followers list");
        s1.remove("Justin", "Beiber");
        //Testing remove method
        
        System.out.println("Adding Elon Musk to your list of followers\n");
        stars();
        s1.follow("Elon", "Musk", "ElonM", true);
        //Testing follow method
        System.out.println("Updated list of followers");
        stars();
        System.out.println(s1);
     
        System.out.print("Enter the first and last name of someone you want to search for: ");
        Scanner scan = new Scanner(System.in);
        String first = scan.next();
        String last = scan.next();
        if(s1.search(first, last) == true){
            System.out.println(first + " " + last + " was found in your list.");
            stars();
        }else{
            System.out.println(first + " " + last + " was not found in your list.");
            stars();
        }
        System.out.println();
        //testing search method

        System.out.println("You are following " + s1.followingCounts() + " people");
        System.out.println("You have " + s1.followerCounts() + " followers");
        stars();
        //Testing follow methods
        System.out.println(s1);

        System.out.print("Enter the first and last name of someone you want to follow back: ");
        String first2 = scan.next();
        String last2 = scan.next();

        s1.followBack(first2, last2);
        System.out.println("You are now following " + first2 + " " + last2 + " and you are now following " + s1.followingCounts()+" people.");
        //Testing the follow back method
    }
    public static void stars(){
        for(int i = 0; i < 40; i++){
            System.out.print("*");
        }
        System.out.println();
    }
}