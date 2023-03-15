package nodes;

public class Node<T> {
    private T data;
    private int weight = 0;
    private Node<T> next;

    /**
     * Constructor sets a node's data to the value passed in, and the next node to null.
     * @param data The value to be held by the node.
     */
    public Node(T data) {
        this.data = data;
        next = null;
    }

    public Node(T data, int w) {
        this.data = data;
        next = null;
        weight = w;
    }

    /**
     * setData Sets the node's data to the value passed in.
     * @param data The data to be held by the node.
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * getData Returns the data held by the node.
     * @return The data held by the node.
     */
    public T getData() {
        return data;
    }

    /**
     * setNext Sets the next node reference.
     * @param next The next node reference.
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }

    /**
     * getNext Returns the next node reference.
     * @return The next node reference.
     */
    public Node<T> getNext() {
        return next;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int w) {
        weight = w;
    }
}
