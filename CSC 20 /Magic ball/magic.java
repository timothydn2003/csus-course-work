import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
public class magic{
    public static void main(String[] args) throws FileNotFoundException{
        Scanner scan = new Scanner(System.in);
        playGame(scan);
        // readAnswers();
    }

    public static void playGame(Scanner scan){
        boolean play = true;
        while(play){
            System.out.print("Enter a question: ");
            String question = scan.nextLine();
            System.out.print("Ask another question? (yes or no)");
            String ans = scan.nextLine();
            if(ans.equals("no")) play = false;
        }
    }
    // public static void readAnswers() throws FileNotFoundException{
    //     File file = new File("answer.txt");
    //     Scanner s = new Scanner(file);
    //     int[] answers = new int[20];
    //     int count = 0;

    //     while(s.hasNextLine()){
    //         answers[count] = s.nextLine();
    //     }
    // }
}