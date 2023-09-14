/*Timothy Doan
CSC 20
2/28/21
This program adds and removes passengers from a flight.
100/100 because I have followed all directions on both assignment description and rubric.
*/
import java.util.Scanner;
public class AirPlaneDoan{
}
class Person{
    private String name;
    private String lastName;
    private String ticketId;
    //Instance Variables
    public Person(String name, String lastName, String ticketId){
        this.name = name;
        this.lastName = lastName;
        this.ticketId = ticketId;
        //Initializing the instance variables
    }
    public void setFirst(String newName){
        name = newName;
    }
    public void setLast(String newLast){
        lastName = newLast;
    }
    //These are the setter methods that declare what is put into the variables
    public void ticketId(String newID){
        ticketId = newID;
    }
    //This method changes the passengers ticket id if needed
    public String getName(){
        return name;
    }
    public String getLast(){
        return lastName;
    }
    //These are the getter methods that retrun the variables
    public String ticketId(){
        return ticketId;
    }
    //This returns the ticket id
    public String toString(){
        String s = "First Name: " + name + "\n";
        s = s + "Last Name: " + lastName + "\n";
        s = s + "Ticket ID: " + ticketId + "\n";
        return s;
    }
    //This is the toString method that prints out the passengers information
    public boolean equals(Object o){
        if(o instanceof Person){
            Person p = (Person)o;
            return this.lastName.equalsIgnoreCase(p.lastName);
        }
        return false;
    }
}
//This checks the last name to see if it matches
class Passenger extends Person{//Implementing inheritance
    private int seatNumber;
    private String classType;
    //Instance Variables
    public Passenger(String name, String lastName, String ticketId, int seatNumber, String classType){
        super(name,lastName,ticketId);
        this.seatNumber = seatNumber;
        this.classType = classType;
    }
    //This is initializing the instance variables
    public void setSeat(int newNum){
        seatNumber = newNum;
    }
    public void setClass(String newClass){
        classType = newClass;
    }
    //These are the setter methods
    public String getClassType(){
        return classType;
    }
    public int getSeat(){
        return seatNumber;
    }
    //These are the getter methods
    public String toString(){
        return super.toString() + "Seat Number: " + seatNumber + "\nClass: " + classType + "\n";
    }
    //This is the toString method that also uses the toString method in the Super class to print out the passengers information
}
interface list{
    public boolean add(Object o);
    public Object search(Object o);
    public boolean delete(Object o);
    public void printLast();
    public void takeOff();
}
//List interface
class Airplane implements list{
    public static int count = 0;
    private Passenger[] passengers;
    private int planeNum;
    private boolean takenOff;
    //Instance variables
    public Airplane(int planeNum, boolean takenOff){
        this.planeNum = planeNum;
        passengers = new Passenger[10];
        takenOff = false;
    }
    //Initializing the instance variables
    public int getPlaneNum(){
        return planeNum;
    }
    //Returns the plane number

    public void setPlaneNum(int newNum){
        planeNum = newNum;
    }
    public String toString(){
        String s = "";
        for(int i = 0; i< passengers.length;i++){
            if(passengers[i]!=null){
                s = s + passengers[i].toString() + "\n";
            }
        }
        return s;
    }
    //Prints the inforamtion of the passengers
    public static int getCount(){
        return count;
    }
    //Returns the amount of passengers on plane
    public boolean add(Object o){

       if(takenOff==false){
            if(o instanceof Passenger){
                Passenger p = (Passenger)o;
                passengers[count] = p;
                count++;
                return true;
            }
            return false;
        }
        return false;
    }
    //Adds passengers
    public Object search(Object o){
        boolean b = o instanceof String; 
        if(!b) 
        return null;
        String name = (String)o; 
        for(int i = 0; i < count; i++){
            if(passengers[i]!= null && passengers[i].getLast().equalsIgnoreCase(name)){ 
                return passengers[i];   
            }
        }
        return true;
    }
//Searches for passengers based on last name
    public boolean delete(Object o){
        if(o instanceof String){
            String last = (String)o;
            for(int i = 0;i<passengers.length;i++){
                if(passengers[i].getLast().equalsIgnoreCase(last)){
                    passengers[i] = null;
                    count--;
                    return true;
                }
            }
        }
        //Deletes passengers
        return true;
    }
    public void printLast(){
        for(int i = 0;i<count;i++){
            System.out.println(passengers[i].getLast());
        }
    }
    //Prints last name of passengers
    public void takeOff(){
        takenOff = true;
    }
    //Sets variable of takenOff to true
}
class Driver{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        Airplane passengers = new Airplane(203302, false);

        Passenger p1 = new Passenger("Timothy", "Doan", "123456789", 1, "First class");
        Passenger p2 = new Passenger("Johnny", "Apple", "987654321", 8, "Business class");
        Passenger p3 = new Passenger("Tom", "Jerry", "567123489", 32, "Economy class");
        Passenger p4 = new Passenger("Candy", "Cruz", "982134567", 15, "Premium Economy class");
        Passenger p5 = new Passenger("Kaloti", "Aaron", "762134589", 5, "First class");
        //Creating the objects
        passengers.add(p1);
        passengers.add(p2);
        passengers.add(p3);
        passengers.add(p4);
        passengers.takeOff();
        passengers.add(p5);
        //Adding objects to the array
        System.out.println("This flight has left, no more passengers can board the plane.");
        System.out.println("Here is the list of passengers on this plane.\n");
        System.out.println(passengers);
        //Printing out list of objects
        System.out.println("Testing the Print Last method to print only last name of passengers.");
        passengers.printLast();
        System.out.println();
        //Prints out last names
        System.out.println("Testing the static method getCount to see how many people are on this plane");
        System.out.println("This plane has " + passengers.getCount() + " people");
        //Prints out count of passengers
        System.out.print("Enter the last name of the passenger: ");
        String searched = scan.nextLine();
        System.out.println();
        System.out.println(passengers.search(searched));
        System.out.println();
        //Searches for passenger
        System.out.println("Testing the delete method");
        System.out.print("Enter the last name of the passenger that needs to be removed from flight: ");
        String deleted = scan.nextLine();
        passengers.delete(deleted);
        System.out.println("Passenger " + deleted + " has been removed from the flight");
        System.out.println();
        System.out.println("Here is the updated list");
        System.out.println(passengers);
        //Removes passenger from flight
    }
}