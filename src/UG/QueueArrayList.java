package UG;

import java.util.ArrayList;

public class QueueArrayList<T> implements QueueInterface<T>
{
    private ArrayList<T> items = new ArrayList<>();
    private int totalItems = 0;
    private int front = 0;
    private int rear = 0;

    /**
     * enqueue Adds a value to the rear of the ArrayList.
     * @param input The value to be placed in the ArrayList.
     */
    public void enqueue(T input)
    {
        items.add(rear, input);
        rear++;
        totalItems++;
    }

    /**
     * dequeue Removes a value from the front of the ArrayList.
     * @return The value of the removed index.
     */
    public T dequeue()
    {
        if(isEmpty())
        {
            return null;
        }
        T temp = items.get(front);
        items.remove(front);
        rear--;
        totalItems--;
        return temp;
    }

    /**
     * isFull Checks if the ArrayList is full.
     * @return False because an ArrayList cannot be full.
     */
    public boolean isFull()
    {
        return false;
    }

    /**
     * isEmpty Checks if there are no values in the ArrayList.
     * @return True if there are no values in the ArrayList, false otherwise.
     */
    public boolean isEmpty()
    {
        return totalItems == 0;
    }

    /**
     * size Returns the number of values in the ArrayList.
     * @return The count of values in the ArrayList.
     */
    public int size()
    {
        return totalItems;
    }
}
