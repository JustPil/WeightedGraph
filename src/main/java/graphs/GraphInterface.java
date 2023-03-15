package graphs;

import java.util.Queue;

public interface GraphInterface<T> {
    boolean addVertex(T vertex);
    boolean hasVertex(T vertex);
    boolean removeVertex(T vertex);
    int getTotalVertices();
    boolean addEdge(T source, T destination, int weight);
    int getEdge(T source, T destination);
    boolean containsEdge(T source, T destination);
    boolean removeEdge(T source, T destination);
    boolean isEmpty();
    boolean isFull();
    Queue<T> getAdjacentVertices(T vertex);
    void markVertex(T vertex);
    boolean isMarked(T vertex);
    void clearAllMarks();
    T getAnUnmarkedVertex();
}
