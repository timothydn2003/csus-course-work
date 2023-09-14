/*Timothy Doan
02/15/22
CSC 20
This program has objects of different employees and prints their information
100/100 because I have followed all guidelines on both assignment description and rubric
*/

public class PayrollDoan {
}
class Payroll{
    private String name;
    private String id;
    private double hoursWorked;
    private double hourlyRate;
    //These are the instance variables
    public Payroll(String name, String id, double hoursWorked, double hourlyRate){
       this.name = name;
       this.id = id;
       this.hoursWorked = hoursWorked;
       this.hourlyRate = hourlyRate;
    }
    //This is the constructor method that initializes the instance variables.
    public String getName(){
        return name;
    }
    public String getId(){
        return id;
    }
    public double getHourlyRate(){
        return hourlyRate;
    }
    public double getHoursWorked(){
        return hoursWorked;
    }
    //These are the getter methods that return the variables.
    public void setName(String newName){
        name = newName;
    }
    public void setHourlyRate(double newRate){
        hourlyRate = newRate;

    }
    public void setHoursWorked(double newHours){
        hoursWorked = newHours;
    }
    //These are the setter methods that put values into the variables.
    public double getPay(){
        double pay = hourlyRate * hoursWorked;
        return pay;
    }
    //This method calculates the pay of the employee
    public String toString(){
        String s = "Name: " + name;
        s = s + "\nID: " + id;
        s = s + "\nHours Worked: " + hoursWorked;
        s = s + "\nHourly Rate: " + hourlyRate;
        return s;
    }
    //This method puts all the variables into a string that prints the information.
}
class PayrollDriver{
    public static void main(String[] args){
        Payroll p1 = new Payroll("Alex Martinez","123456",20.0,25.0);
        Payroll p2 = new Payroll("Ali Santos","986747",45.0,125.0);
        Payroll p3 = new Payroll("Jose Busta", "45678",30.0,55.0);
        //These are the objects and their attrivutes
        System.out.println("Creating Payroll objects\ntesting the toString method\nList of employees\n");
        System.out.println(p1.toString()+"\nSalary is: " + p1.getPay()+"\n");
        stars();
        System.out.println(p2.toString()+"\nSalary is: " + p2.getPay()+"\n");
        stars();
        System.out.println(p3.toString()+"\nSalary is: " + p3.getPay()+"\n");
        stars();
        //This section is to print out the information of each objects.

        System.out.println("Testing the setter methods\n\nThe hourly pay of Alex Martinez is being changed to $40/hr");
        p1.setHourlyRate(40.0);
        System.out.println(p1.toString()+"\nSalary is: " + p1.getPay()+"\n");
        stars();
        System.out.println("The hourly pay of Ali Santos is being changed to $100/hr");
        p2.setHourlyRate(100.0);
        System.out.println(p2.toString()+"\nSalary is: " + p2.getPay()+"\n");
        stars();
        System.out.println("The hourly rate of Jose Busta is being changed to $70/hr");
        p3.setHourlyRate(70.0);
        System.out.println(p3.toString()+"\nSalary is: " + p3.getPay()+"\n");
        //This part sets the hourly rate to something different.


        
        
    }    
    public static void stars(){
        for(int i = 0;i<30;i++){
            System.out.print("*");
        }
        System.out.println();
    }
    //This is an extra method that I added to have a row of stars to seperate each object

}
