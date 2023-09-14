/*Timothy Doan
CSC 20
March 17 2022
This program prints a grocery list and includes many functions such as add, delete etc.
100/100 because I have followed all guidelines on rubric and assignment description.
*/
public class GroceryAppDoan{
}
class Item implements Comparable<Object>{
    private String food;
    private double price;
    private String expire;
    //Instance variables
    public Item(String food, double price, String expire){
        this.food = food; 
        this.price = price;
        this.expire = expire;
    }
    //Constructor
    public String getFood(){
        return food;
    }
    public double getPrice(){
        return price;
    }
    public String getExpiration(){
        return expire;
    }
    //Getter methods
    public void setFood(String newFood){
        food = newFood;
    }
    public void setPrice(double newPrice){
        price = newPrice;
    }
    public void setExpiration(String newExpire){
        expire = newExpire;
    }
    //Setter methods
    public boolean equals(Item o){
        return this.food.equals(o.food) && this.price == o.price;
    }
    //This checsk if 2 items are the same
    public int compareTo(Object o){
        Item i = (Item)o;
        return this.food.compareToIgnoreCase(i.food);
    }
    //This compares 2 items
    public String toString(){
        String s = "Food: " + food + "\n";
        s += "Price: " + price + "\n";
        s += "Expiration Date: " + expire +"\n";
        return s;
    }
    //Displays the list
}
class ListNode{
    private Item i;
    private ListNode next;
    //Instance variables
    public ListNode(){
    }
    public ListNode(Item item){
        i = item;
    }
    public ListNode(Item i, ListNode next){
        this.i = i;
        this.next = next;
    }
    //Constructors
    public Item getItem(){
        return i;
    }
    public ListNode getNext(){
        return next;
    }
    //getter methods
    public void setNext(ListNode next){
        this.next = next;
    }
    //setter methods
}
interface List{
    public void add(String food, double price, String expire);
    public void add(String food, double price, String expire, int index);
    public int indexOf(String food); 
    public int size(); 
    public String toString(); 
    public Item get(int position); 
    public Item mostExpensive(); 
}
//List of methods that next class uses
class GroceryList implements List{
    private ListNode head;
    public static int size = 0;
    //Instance variables
    public GroceryList(){
        head = null;
    }
    public void add(String food, double price, String expire){
        Item i = new Item(food,price,expire);
        ListNode curr = head;
        if(head == null){
            head = new ListNode(i);
            size++;
            return;
        }
        ListNode n = new ListNode(i);
        while(curr.getNext() != null){
            curr = curr.getNext();
        }
        curr.setNext(n);
        size++;
    }
    //Adds an item to the list
    public void add(String food, double price, String expire, int index){
        Item i = new Item(food,price,expire);
        if(index> size){
            return;
        }
        if(index == 0){
            ListNode n = new ListNode(i);
            n.setNext(head);
            head = n;
            size++;
            return;
        }
        ListNode curr = head;
        int j = 0;
        while(curr.getNext() != null && j < index-1){
            curr= curr.getNext();
            j++;
        }
        ListNode n = new ListNode(i);
        n.setNext(curr.getNext());
        curr.setNext(n);
        size++;

    }
    //Adds an item at a given index
    public int indexOf(String food){    
        if(head == null){
            return -1;
        }
        if(food.equals(head.getItem().getFood())){
            return 0;
        }
        ListNode curr = head;
        int index = 0;
        while(curr != null && index <= size){

            if(curr.getItem().getFood().equals(food)){
                return index;
            }
            curr = curr.getNext(); 
            index++;  
        }   
        return -1;
    }//Finds index of an item
    public void remove(String food){
        if(head == null){
            return;
        }
        if(head.getItem().getFood().equals(food)){
            head = head.getNext();
        }
        ListNode pre = head;
        ListNode curr = head;
        while(curr!=null && !(curr.getItem().getFood().equals(food))){
            pre = curr;
            curr = curr.getNext();
        }
        if(curr!= null && curr.getNext() == null && (curr.getItem().getFood().equals(food))){
            pre.setNext(null);
            size--;
        }else if(curr == null){
            System.out.println("Item not found");
        }else{
            pre.setNext(curr.getNext());
            size--;
            System.out.println("**Item removed**");
        }
    }
    //Removes given item
    public int size(){
       ListNode curr = head;
       int num = 0;
       while(curr!= null && num<=size){
           num++;
           curr = curr.getNext();
       }
       return num;
    }
    //Returns size of list
    public String toString(){
        if(head == null){
            return "";
        }
        ListNode curr = head;
        String s = "";
        while(curr!=null){
            s += curr.getItem().toString() +"\n";
            curr = curr.getNext();
        }
        return s;
    }
    //Displays data from list
    public Item get(int index){
        if(head == null)
            return null;
        if(index>size)
            return null;
        ListNode curr = head;
        int pos = 0;
        while(curr!=null && index!= pos){
            pos++;
            curr = curr.getNext();
        }
        if(curr == null)
            return null;
        return curr.getItem();
    }
    //Gets an item at speciifc position
    public Item mostExpensive(){
        ListNode curr = head;
        Item most = curr.getItem();
        while(curr!=null){
            if(curr.getItem().getPrice()>most.getPrice()){
                most = curr.getItem();
            }
            curr = curr.getNext();
        }
        return most;
    }
}
//Finds most expensive item
class Driver{
    public static void main(String[] args){
        GroceryList list = new GroceryList();
        list.add("Cereal", 5.99, "3/20/2022");
        list.add("Milk", 3.99, "2/1/2002");
        list.add("Ham", 10.99, "12/30/2025");
        list.add("Rice", 35.50, "8/15/2030");
        //Adding items to the list

        System.out.println("Here is the list of food items");
        stars();
        System.out.println(list);
        //Prints the list

        System.out.println("Here is the most expensive item on the list");
        stars();
        System.out.println(list.mostExpensive());
        //Prints most expensive item

        System.out.println("Removing Milk from the list and adding a new expensive item on the list in the 2nd node");
        list.remove("Milk");
        stars();
        //Removes given item

        list.add("Truffle", 800, "4/20/2050",1);
        System.out.println(list);
        //Adds new item at given index

        System.out.println("Testing the mostExpensive method to see what is the most expensive item now");
        stars();
        System.out.println(list.mostExpensive());
        //Prints new most expensive item

        System.out.println("Testing the get method to get the item at the 3rd node");
        stars();
        System.out.println(list.get(2));
        //Prints item at speciifc node

        System.out.println("The size of the list is " + list.size());
        //Prints out the size of the list
    }
    public static void stars(){
        for(int i = 0; i < 40; i++){
            System.out.print("*");
        }
        System.out.println();
    }
}
