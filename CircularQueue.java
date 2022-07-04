
/**
 * A basic circular queue that stores items up to size N. As the enqueue & dequeue are handled by the controller class,
 * it is safe to ignore any issue regarding full queues and empty queue
 *
 * @author Kwan Yui Chiu (K21003778)
 * @version 18-03-2022
 */
public class CircularQueue<E>
{
    /*
       Instance variables for the queue
    */
    private int rear;
    private int front;
    private int maxSize;

    private E[] data;
    /**
     * Constructor for circular queue to size n
     */
    public CircularQueue(int n)
    {
        maxSize = n;
        data = (E[]) new Object[n];
        front = -1;
        rear = -1;
    }
    
    /**
     * Method for adding element
     * @param E an instance of type E is added to the rear of the queue
     */
    public void enqueue(E element){
        rear = (rear + 1) % maxSize;
        data[rear] = element;
        if (front == -1){
            front = rear;
        }
    }
    
    /**
     * Method for removing item
     * @return element at the front of the queue
     */
    public E dequeue(){
        E element = data[front];
        front = (front + 1) % maxSize;
        return element;
    }
    
}
