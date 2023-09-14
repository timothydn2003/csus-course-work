/*Timothy Doan
2/23/2022
CSC 20
This program displays a sent and forwarded email using object oriented programming.
100/100 because I followed all guidlines and instruction on assignment description.
*/
import java.util.Date;
import java.util.Scanner;;

public class EmailAppDoan{
}
//This is the document class that holds the methods that have to do with content of the email.
class Document{
    private String content;
    public Document(String content){
        this.content = content;
    }
    //This is to intialize the instance variables.
    public String getContent(){
        return content;
    }
    //This method returns the content of the email.
    public void setContent(String newContent){
        content = newContent;
    }
    //This is the setter method that puts the content into the variable.
    public String toString(){
        return "Content: "+ content;
    }
    //This method returns the content into the right format.
    public int getContentLength(){
        return content.length();
    }
    //This returns the length of the content.
    public Boolean contains(String keyword){
        if(content.contains(keyword))
        return true;
        return false;
    }
    //This checks if a keyword is in the content.
    public Boolean equals(Document other){
        if(this.content.equalsIgnoreCase(other.content)){
            return true;
        }
        return false;
    }
    //This compares the content.
}
class Email extends Document{//Implementing inheritance
    private String sender;
    private String reciepient;
    private String subject;
    private Date date;
    private String cc;
    private Boolean isSent;
    //These are the instance variables
    public Email(String sender, String reciepient, String subject, String cc,String content){
        super(content);//Using the super class to have inheritance from document class.
        this.sender = sender;
        this.reciepient = reciepient;
        this.subject = subject;
        this.cc = cc;
        date = new Date();
        this.isSent = false;
        //This initializes the instance variables.
    }
    public void send(){
        isSent = true;
    }
    //This stores true into the isSent variable.
    public Boolean getSent(){
        return isSent;
    }
    public String getSender(){
        return sender;
    }
    public String getReciepient(){
        return reciepient;
    }
    public String getSubject(){
        return subject;
    }
    //These are the getter methods that retruns the variables.
    public String getCC(){
        return cc;
    }
    public Date Date(){
        return date;
    }
    public void setSender(String s){
        if(isSent==false){
            sender = s;
        }else{
            System.out.println("This email has been sent and cannot be modified");
        }
    }
    public void setReciepient(String r){
        if(isSent==false){
            reciepient=r;
        }else{
            System.out.println("This email has been sent and reciepent cannot be modified");
        }
    }
    public void setSubject(String sub){
        if(isSent==false){
            subject = sub;
        }else{
            System.out.println("This email has been sent and subject cannot be modified.");
        }
    }
    public void setCC(String c){
        if(isSent==false){
            cc = c;
        }else{
            System.out.println("This email has been sent and CC cannot be modified.");
        }
    }
    //These are the setter methods that allow the content of email to be changed if it isn't sent yet.
    public String toString(){
        String finalEmail = "";
        finalEmail = "Sender: " + sender + "\n";
        finalEmail = finalEmail + "Reciepient: " + reciepient +"\n";
        finalEmail = finalEmail + "CC: " + cc + "\n";
        finalEmail = finalEmail + "Subject: " + subject + "\n";
        finalEmail = finalEmail + "Date: " + date + "\n";
        finalEmail = finalEmail + super.toString();
        return finalEmail;
    }
    //This prints everything out as a String.
    public void modifyContent(String newContent){
        if(isSent == false){
            super.setContent(newContent);
        }else{
            System.out.println("This email has been sent and content cannot be modified");
        }
    }
    //Allows the content to be changed if email isnt sent yet.
   public Email forward(String rec,String sender, String cc){
        Email f = new Email (sender, rec, subject, cc, this.getContent());
        f.date = new Date();
        f.isSent = true;
        return f; 
   }
   //This forwards the email
}
class Driver{
    public static void main(String[] args){
        Email e1 = new Email("Gita","Tim","Meeting","","Hello everyone, we will have a meeting tomorrow at 10.");
        Email e2 = new Email("Tim", "Gita","Meeting", "", "");
        //This declares the objects.
        e1.send();
        //This sends the email.
        System.out.println(e1.toString()+"\n\n");
        boolean b = e1.contains("tomorrow");
        //Checks if keyword is in content.
        if(b){
            System.out.println("The word \"tomorrow\" was found in the email");
        }else{
            System.out.println("The word \"tomorrow\" was not found in the email");
        }
        //If statement to print if keyword is found or not.
        System.out.println("The content of this email is: " + e1.getContent());
        System.out.println();
        e1.modifyContent("bye");
        e1.setReciepient("John");
        //Trying to change content and recipient but can't because email has been sent.
        System.out.println();

        Email forward = e1.forward("Tim","Gita","Maria");
        System.out.println("Forwarding the message to Maria");
        System.out.println("Forwarded message:\n" + forward);
        System.out.println();
        System.out.println("Then number of letters in this message is: " + e1.getContentLength() + "\n");;
        //Forwarding the message
        System.out.print("Do you want to reply to the email? Yes or No: ");
        Scanner scan = new Scanner(System.in);
        String reply = scan.next();
        if(reply.equalsIgnoreCase("yes")){
                scan.nextLine();
                System.out.print("Input content of reply: ");
                String newContent = scan.nextLine();
                e2.modifyContent(newContent);
                System.out.println();
                e2.send();
                System.out.println(e2.toString());
                System.out.println();
                System.out.println("Then number of letters in this message is: " + e2.getContentLength());;
                boolean key = e2.contains("thanks");
                if(key){
                    System.out.println("The word \"thanks\" was found in the email");
                }else{
                    System.out.println("The word \"thanks\" was not found in the email");
                }
                System.out.println();
                e2.modifyContent("no");
        }else{
            System.out.println("No reply was sent");
        }
    }
    //This is an extra section I added to to implement an e2 object.
}