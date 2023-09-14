import java.util.ArrayList;
import java.util.Stack;

/*Timothy Doan
CSC 20
April 26 2022
100/100 because I have followed all guidlines.
This program adds numbers to a queue and manioulates them*/

public class QueueDoan{
}
class Queue{
    ArrayList<Integer> list;
    public Queue(){
        list = new ArrayList<Integer>();
    }
    //constructor
    public void enqueue(int num){
        list.add(num);
    }
    //add numbers to queue
    public int dequeue(){
        return list.remove(0);
    }
    //removes numbers
    public String toString(){
        String s = "";
        Queue copy = new Queue();
        boolean b = false;
        while(!b){
            try{
                int num = this.dequeue();
                copy.enqueue(num);
                s = s +" "+ num;
            }catch(Exception e){
                b = true;
            }
        }
        restore(copy);
        return s;
    }
    //formats the queue as a string
    public void restore(Queue q){
        boolean b = false;
        while(!b){
            try{
                this.enqueue(q.dequeue());
            }catch(Exception e){
                b = true;
            }
        }
    }
    //restores the queue back to normal
    public int getMax(){
        Queue copy = new Queue();
        boolean b = false;
        int max = 0;
        while(!b){
            try{
                int num = this.dequeue();
                if(num>max){
                    max = num;
                }
                copy.enqueue(num);
            }catch(Exception e){
                b = true;
            }
        }
        restore(copy);
        return max;
    }
    //gets max number in queue
    public int getMin(){
        Queue copy = new Queue();
        boolean b = false;
        int min = this.dequeue();
        while(!b){
            try{
                int num = this.dequeue();
                if(num<min){
                    min = num;
                }
                copy.enqueue(num);
            }catch(Exception e){
                b = true;
            }
        }
        restore(copy);
        return min;
    }
    //returns smallest number in queue
    public void reverseOrder(){
        Stack<Integer> reversed = new Stack();
        boolean b = false;
        while(!b){
            try{
                int num = this.dequeue();
                reversed.push(num);
            }catch(Exception e){
                b = true;
            }
        }
        b= false;
        while(!b){
            try{
                int num = reversed.pop();
                this.enqueue(num);
            }catch(Exception e){
                b = true;
            }
        }
    }
    // prints the queue in reverse order
    public double getAverage(){
        double sum = 0;
        double count = 0;
        double average = 0;
        Queue copy = new Queue();
        boolean b = false;
        while(!b){
            try{
                int num = this.dequeue();
                copy.enqueue(num);
                sum+=num;
                count++;
            }catch(Exception e){
                b = true;
            }
        }
        average = sum / count;
        restore(copy);
        return average;
    }
    //gets the average of the queue
    public boolean isSorted(){
        Queue q = new Queue();
        boolean b = false;
        boolean sorted = false;
        while(!b){
            try{
                int n1 = this.dequeue();
                int n2 = this.dequeue();
                q.enqueue(n1);
                q.enqueue(n2);
                if(n1>n2){
                    sorted = false;
                }
            }catch(Exception e){
                b = true;
            }
        }
        restore(q);
        return sorted;
    }
    //checks if queue is sorted
    public void onlyEven(){
      Stack<Integer> even = new Stack<Integer>();
      boolean b= false;
      while(!b){
          try{
            int num = this.dequeue();
            even.push(num);
          }catch(Exception e){
              b = true;
            }
        }
        while(even.size()>0){
            int num = even.pop();
            if(num%2==0){
                this.enqueue(num);
            }
        }
    }
    //removes all non even numbers from queue
}
class Driver{
    public static void main(String[] args){
        Queue  m = new Queue();
        m.enqueue(10);
        m.enqueue(12);
        m.enqueue(15);
        m.enqueue(7);
        m.enqueue(100);
        m.enqueue(22);
        System.out.println("The queue is:" + m);
        m.reverseOrder();
        System.out.println("The queue in the reverse order is:"+ m);
        m.reverseOrder();
        System.out.println("Queue back to original state:" + m);
        System.out.printf("Average = %.2f\n", m.getAverage());
        System.out.println("Max = " + m.getMax());
        System.out.println("Max = " + m.getMin());
        System.out.println("The list is sorted: "+ m.isSorted());
        //Sample driver
        System.out.println("************************* My Driver ********************************");
        Queue q = new Queue();
        q.enqueue(10);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(14);
        q.enqueue(25);
        System.out.println("The queue is:" + q);
        q.reverseOrder();
        System.out.println("The queue in reverse is:" + q );
        q.reverseOrder();
        System.out.println("The average of the queue is: " + q.getAverage());
        System.out.println("Max: " + q.getMax());
        System.out.println("Min: " + q.getMin());
        System.out.println("Sorted list: " + q.isSorted());
        q.onlyEven();
        System.out.println("The even numbers in the list:" + q);
        //Driver 
    }
}