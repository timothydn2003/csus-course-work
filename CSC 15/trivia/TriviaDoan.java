import java.util.Scanner;

public class TriviaDoan {
    public static final int SIZE = 5;

    public static void main(String[] args){
        System.out.println("hello world");
        run();
    }

    public static void run(){
        Scanner scan = new Scanner(System.in);
        String[] questions = new String[SIZE];
        String[] answers = new String[SIZE];
        int[] points = new int[SIZE];
        initialize(questions, answers, points);
        boolean more = true;
        while(more){
            int score = play(questions, answers, points, scan);
            System.out.println("Your score: " + score);
            System.out.print("Is there another person? ");
            String person = scan.next();
            if(person.equalsIgnoreCase("no")){
                more = false;
                System.out.println("Thanks for playing!");
            }
        }
    }
    
    public static void initialize(String[] questions, String[] answers, int[]points){
        questions[0] = "What NBA team is associated with Sacramento? ";
        answers[0] = "kings";
        points[0] = 2;
        questions[1] = "What is the name of the closest star? ";
        answers[1] = "sun";
        points[1] = 3;
        questions[2] = "Who was the first president? ";
        answers[2] = "washington";
        points[2] = 1;
        questions[3] = "What city is the Eiffel Tower in? ";
        answers[3] = "paris";
        points[3] = 3;
        questions[4] = "How many feet are in a yard? ";
        answers[4] = "3";
        points[4] = 1; 
    }
    public static int play(String[] questions, String[] answers, int[]points, Scanner scan){
        int score = 0;
        for(int i = 0 ; i < questions.length; i++){
            System.out.print(questions[i]);
            String reply = scan.nextLine();
            reply = reply.toLowerCase();
            if(reply.equals(answers[i])){
                System.out.println("That is correct");
                score += points[i];
            }else{
                System.out.println("Wrong. The correct answer is " + answers[i]);
            }
        }
        return score;
    }
}
