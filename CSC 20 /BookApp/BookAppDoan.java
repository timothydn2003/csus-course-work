/*Timothy Doan
CSC 20
March 29, 2022
100/100 because I have followed all guidelines and instructions.
This program adds books to a list and sorts them
*/
import java.util.ArrayList;
import java.util.Scanner;

public class BookAppDoan{
}
class Book implements Comparable{
    private String title;
    private String author;
    private String ISBN;
    private double price;
    //Instance variables
    public Book(String title, String author, String ISBN,double price){
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.price = price;
    }
    //Constructor
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public String getISBN(){
        return ISBN;
    }
    public double getPrice(){
        return price;
    }
    //Getter methods
    public void setTitle(String t){
        title = t;
    }
    public void setPrice(double p){
        price = p;
    }
    public void setIsbn(String sb){
        ISBN = sb;
    }
    //setter methods
    public String toString(){
        return title + ", " + author + ", " + price + ", " + ISBN;
    }
    //Returns object in string form
    public boolean equals(Book o){
        return this.ISBN.equals(o.ISBN);
    }
    //checks if 2 books are equal based on isbn
    public int compareTo(Object o){
        Book b = (Book)o;
        return (this.title).compareTo(b.title);
    }
    //#1: Selection Sort
    public int compares(Book b){
        return this.author.compareTo(b.author);
    }
    //#2 Insertion sort
    public double compare(Book b){
        if(this.author.equals(b.author)){
            return this.price - b.price;
        }else{
            return this.author.compareTo(b.author);
        }
    }
    //#3: Bubble sort
}
class BookStore{
    private ArrayList<Book> books;
    public BookStore(){
        books = new ArrayList<Book>();
    }
    public void add(String title, String author, double price, String isbn){
        books.add(new Book(title, author,isbn, price));
    }
    //adds a book to the list
    public String toString(){
        String s = "";
        for(int i = 0; i < books.size(); i++){
            s = s + books.get(i).toString()+"\n";
        }
        return s;
    }
    //formats the list into a string
    public boolean delete(String isbn){
        for(int i = 0; i < books.size(); i++){
            if (books.get(i).getISBN().equals(isbn)){
                books.remove(i);
                return true;
            }
        }
        return false;
    }
    //deletes a book
    public void selectionSort(){
        for(int i = 0; i < books.size()-1; i++){
            int index = -1;
            boolean found = false;
            Book min = books.get(i);
            for(int j = i + 1; j < books.size()-1;j++){
                int x = books.get(j).compareTo(min);
                if(x<0){
                    index = j;
                    min = books.get(j);
                    found = true;
                }
            }
            if(found == true){
                Book temp = books.get(i);
                books.set(i,min);
                books.set(index,temp);
            }

        }
    }
    //Using selection sort to sort list by title
    public void insertionSort(){
        for(int i = 0; i < books.size()-1;i++){
            int j = i + 1;
            Book b = books.get(j);
            while(j>0 && b.compares(books.get(j-1))<0){
                books.set(j,books.get(j-1));
                j--;
            }
            books.set(j,b);
        }
    }
    //sorts the list using insertion sort
    public void bubbleSort(){
        for(int i = 0; i < books.size();i++){
            for(int j = 0; j<books.size()-1-i;j++){
                if(books.get(j+1).compare(books.get(j))<0){
                    Book temp = books.get(j);
                    books.set(j,books.get(j+1));
                    books.set(j+1, temp);

                }
            }
        }
    }
    //Sorts the list using bubble sort
    public Book binarySearch(String title){
        selectionSort();
        boolean found = false;
        int first = 0;
        int last = books.size()-1;
        int mid = (first + last)/2;
        while(first<=last){
            if(books.get(mid).getTitle().equalsIgnoreCase(title)){
                found = true;
                return books.get(mid);
            }else if(title.compareTo(books.get(mid).getTitle())>0){
                first = mid + 1;
            }else{
                last = mid - 1;
            }mid = (first+last)/2;
        }
        return null;
    }
    //Searches for book in list
}
class Driver{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        BookStore b1 = new BookStore();
        b1.add("Java","Zoie",23.56,"12345678");
        b1.add("Python","Elina",23.56,"2");
        b1.add("Advance Java","Stewart",98,"767676576");
        b1.add("Humanity","Smith",100.56,"234545657");
        b1.add("Build Java","Liang",45,"56786565y76");
        b1.add("C++","Elina",23.56,"2645556");
        b1.add("Programming Java","Stewart",124,"75465666");
        b1.add("Zip lining", "Stewart",12,"1234566576");
        //Adding books to the list
        boolean b = false;
        while(b==false){
            System.out.println("Enter 1 to sort based on the title");
            System.out.println("Enter 2 to sort based on the author");
            System.out.println("Enter 3 to sort based on the author, and the price");
            System.out.print("Enter your choice: ");
            int answer = scan.nextInt();
            System.out.println("********************************");
            switch(answer){
                case 1:
                    System.out.println("Sorted based on title\n");
                    b1.selectionSort();
                    System.out.println(b1);
                    break;
                case 2:
                    System.out.println("Sorted based on author\n");
                    b1.insertionSort();
                    System.out.println(b1);
                    break;
                case 3:
                    System.out.println("Sorted absed on author and price\n");
                    b1.bubbleSort();
                    System.out.println(b1);
                    break;
            }
            //User picks how they want to sort the list
            scan.nextLine();
            System.out.print("Enter a name to search for a book: ");
            String name = scan.nextLine();
            Book book = b1.binarySearch(name);
            if(book != null){
                System.out.println(name + " was found!\n");
                System.out.println(book);
            }else{
                System.out.println(name + " was not found");
            }
            System.out.println("****************************");
            System.out.print("Would you like to still use the program? Yes or No: ");
            String run = scan.next();
            if(run.equalsIgnoreCase("yes")){
                b = false;
            }else{
                b = true;
                System.out.println("Bye!");
            }
        }
        //Checks if title is in the list
    }
}