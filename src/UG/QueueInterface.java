package UG;

public interface QueueInterface<T>
{
    void enqueue(T element);
    T dequeue();
    boolean isFull();
    boolean isEmpty();
    int size();
}
