/*Timothy Doan
CSC 20
April 15,2022
100/100 because I followed all guidlines
This program converts infix to postfix*/
import java.util.ArrayList;
import java.util.StringTokenizer;

public class StackDoan{
}
interface myStack{
    public void push(String token);
    public String peek();
    public boolean isEmpty();
    public String pop();
}
//Stack Interface
class Stack implements myStack{
    ArrayList<String> s;
    int top;
    public Stack(){
        s = new ArrayList<String>();
        top = 0;
    }
    //initializing stack
    public void push(String token){
        s.add(token);
        top++;
    }
    //push to top of stack
    public String peek(){
        String peek = "";
        if(!isEmpty()){
            peek += s.get(top-1);
        }
        return peek;
    }
    //returns top of stack
    public boolean isEmpty(){
        return s.size() == 0;
    }
    //checks if stack is empty
    public String pop(){
        if(!isEmpty()){
            top--;
            String o = s.get(top);
            s.remove(top);
            return o;
        }
        return null;
    }
    //removes top of stack
    public String toString(){
        return s.toString();
    }
    //converts to string
}
class Expression extends Stack{
    private String exp;
    public Expression(String e){
        exp = e;
    }
    private static int precedence(String opr){
        int num = 0;
        if(opr.equals("*")||opr.equals("/")){
            num = 3;
        }else if(opr.equals("+") || opr.equals("-")){
            num = 2;
        }
        return num;
    }
    //determines which sign has higher precedence
    private static int calculate(int num1, int num2, String opr){
        switch(opr){
            case "+":
                return num1 + num2;
            case "/":
                return num2/num1;
            case "*":
                return num1*num2;
            case "-":
                return num2-num1;
        }
        return 0;
    }
    //calculates the 2 inputted numbers
    public String getPostFix(){
        String post = "";
        StringTokenizer st = new StringTokenizer(exp, " ");
        Stack stack = new Stack();
        while(st.hasMoreTokens()){
            String token = st.nextToken();
            if(token.equals("*") || token.equals("/") || token.equals("+") || token.equals("-")){
                int num = precedence(token);
                if(num == 3){
                    while(!stack.isEmpty() && precedence(stack.peek())==3){
                        String pop = stack.pop();
                        post = post + " " + pop;
                    }
                }else if(num == 2){
                    while(!stack.isEmpty() && precedence(stack.peek())>=2 ){
                        String pop = stack.pop();
                        post = post + " " + pop;
                    }
                }
                stack.push(token);
            }else{
                post = post + " " + token;
            }
        }
        while(!stack.isEmpty()){
            String pop = stack.pop();
            post = post + " " + pop;  
        }
        return post;
    }
    //Converts infix to postfix
    public int evalPostFix(){
        String post = this.getPostFix();
        Stack s1 = new Stack();
        int result = 0;
        StringTokenizer st = new StringTokenizer(post, " ");
        while(st.hasMoreTokens()){
            String token = st.nextToken();
            if(!token.equals("+")&&!token.equals("-")&&!token.equals("*")&&!token.equals("/")){
                s1.push(token);
            }else{
                String n1 = s1.pop();
                String n2 = s1.pop();
                int num1 = Integer.parseInt(n1);
                int num2 = Integer.parseInt(n2);
                result = calculate(num1,num2,token);
                s1.push(""+result+"");
            }
        }
        return Integer.parseInt(s1.pop());
        
    }
    //evaluates postfix
}
class Driver{
    public static void main(String[] args){
        ArrayList<String> exp = new ArrayList<String>();
        exp.add("2 - 5 * 8 / 10 + 9");
        exp.add("10 + 3 - 4 / 8 - 9");
        exp.add("4 + 5 * 6 / 9 - 6 + 7");
        exp.add("12 + 9 - 7 / 8 * 2");
        exp.add("2 + 3 + 7 * 4 - 2 / 3");
        //adding expressions to list
        for(int i = 0; i < exp.size(); i++){
            Expression e1 = new Expression(exp.get(i));
            String post = e1.getPostFix();
            int result = e1.evalPostFix();
            System.out.println("infix: " + exp.get(i) + ", postfix:" + post + ", Answer: " + result);
            stars();
            System.out.println();
        }
        //printing out postfix and evaluations
    }
    public static void stars(){
        for(int i = 0; i < 60; i++){
            System.out.print("*");
        }
    }
}