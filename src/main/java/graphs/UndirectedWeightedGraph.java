package graphs;

import java.util.Queue;
import java.util.Arrays;
import java.util.LinkedList;

public class UndirectedWeightedGraph<T> implements GraphInterface<T>{
    private final int UNINITIALIZED_EDGE = -1;
    private int capacity;
    private int totalVertices = 0;
    private T[] vertexArray;
    private int[][] edgeMatrix;
    private boolean[] seenVertexArray;

    /**
     * Constructor initializes capacity to 25 elements.
     */
    public UndirectedWeightedGraph() {
        vertexArray = (T[])new Object[capacity = 25];
        edgeMatrix = new int[capacity][capacity];
        seenVertexArray = new boolean[capacity];
    }

    /**
     * Constructor initializes capacity to a positive user-defined number. If the parameter is negative the default
     * capacity is invoked.
     * @param cap The non-negative vertex capacity.
     */
    public UndirectedWeightedGraph(int cap) {
        capacity = cap > 0 ? cap : 25;
        vertexArray = (T[])new Object[capacity];
        edgeMatrix = new int[capacity][capacity];
        seenVertexArray = new boolean[capacity];
    }

    /**
     * addVertex Adds a vertex to the Graph.
     * @param vertex The vertex to add.
     * @return True if the vertex is added, false otherwise.
     */
    public boolean addVertex(T vertex) {
        if(totalVertices < capacity) {
            if(vertexArray[totalVertices] == null) {
                vertexArray[totalVertices] = vertex;
                for(int i = 0; i < totalVertices; i++) {
                    edgeMatrix[totalVertices][i] = UNINITIALIZED_EDGE;
                    edgeMatrix[i][totalVertices] = UNINITIALIZED_EDGE;
                }
            } else {
                for(int i = 0; i < vertexArray.length; i++) {
                    if(vertexArray[i] == null) {
                        vertexArray[i] = vertex;
                        for(int j = 0; j < totalVertices; j++) {
                            edgeMatrix[totalVertices][j] = UNINITIALIZED_EDGE;
                            edgeMatrix[j][totalVertices] = UNINITIALIZED_EDGE;
                        }
                    }
                }
            }
            totalVertices++;
            return true;
        }
        return false;
    }

    /**
     * hasVertex Checks if a vertex is present in the Graph.
     * @param vertex The vertex to search for.
     * @return True if the vertex is present, false otherwise.
     */
    public boolean hasVertex(T vertex) {
        for(int i = 0; i < totalVertices; i++) {
            try {
                if(vertexArray[i].equals(vertex)) {
                    return true;
                }
            } catch(NullPointerException e) {
                continue;
            }
        }
        return false;
    }

    /**
     * removeVertex Removes a vertex from the Graph if present and edges to this vertex if found.
     * @param vertex The vertex to remove.
     * @return True if the vertex is removed, false otherwise.
     */
    public boolean removeVertex(T vertex) {
        int index = indexFinder(vertex);
        if(index < 0) {
            return false;
        }
        Queue<Integer> queue = getAdjacentVertexIndices(vertex);
        while(!queue.isEmpty()) {
            int dequeue = queue.poll();
            if(edgeMatrix[index][dequeue] != UNINITIALIZED_EDGE) {
                edgeMatrix[index][dequeue] = UNINITIALIZED_EDGE;
                edgeMatrix[dequeue][index] = UNINITIALIZED_EDGE;
            }
        }
        vertexArray[index] = null;
        totalVertices--;
        return true;
    }

    /**
     * getTotalVertices Returns the total number of vertices present in the Graph.
     * @return The total number of vertices.
     */
    public int getTotalVertices() {
        return totalVertices;
    }

    /**
     * addEdge Adds a weighted edge between two vertices in the Graph.
     * @param source The source vertex.
     * @param destination The destination vertex.
     * @param weight The weight of the edge.
     * @return True if edge is added successfully, false otherwise.
     */
    public boolean addEdge(T source, T destination, int weight) {
        if(weight < 0) {
            return false;
        }
        int row = indexFinder(source);
        int col = indexFinder(destination);
        if(row < 0 || col < 0) {
            return false;
        }
        edgeMatrix[row][col] = weight;
        edgeMatrix[col][row] = weight;
        return true;
    }

    /**
     * getEdge Returns the weight of an edge between two vertices if present.
     * @param source The source vertex.
     * @param destination The destination vertex.
     * @return the weight of the edge if found, a negative weight if there was no edge.
     */
    public int getEdge(T source, T destination) {
        int row = indexFinder(source);
        int col = indexFinder(destination);
        if(row < 0 || col < 0) {
            return -1;
        }
        return edgeMatrix[row][col];
    }

    /**
     * containsEdge Checks if an edge exists between two vertices.
     * @param source The source vertex.
     * @param destination The destination vertex.
     * @return True if an edge exists, false otherwise.
     */
    public boolean containsEdge(T source, T destination) {
        int row = indexFinder(source);
        int col = indexFinder(destination);
        if(row < 0 || col < 0) {
            return false;
        }
        return edgeMatrix[row][col] != UNINITIALIZED_EDGE;
    }

    /**
     * removeEdge Removes an edge between two vertices if present.
     * @param source The source vertex.
     * @param destination The destination vertex.
     * @return True if the edge is removed, false if the edge was not found.
     */
    public boolean removeEdge(T source, T destination) {
        int row = indexFinder(source);
        int col = indexFinder(destination);
        if(row < 0 || col < 0) {
            return false;
        }
        edgeMatrix[row][col] = UNINITIALIZED_EDGE;
        edgeMatrix[col][row] = UNINITIALIZED_EDGE;
        return true;
    }

    /**
     * indexFinder Returns the index of a vertex.
     * @param vertex The vertex to search for.
     * @return The index of the vertex, or a negative number if the vertex was not found.
     */
    private int indexFinder(T vertex) {
        for(int i = 0; i < vertexArray.length; i++) {
            try {
                if (vertexArray[i].equals(vertex)) {
                    return i;
                }
            } catch(NullPointerException e) {
                continue;
            }
        }
        return -1;
    }

    /**
     * isEmpty Checks if the Graph is empty.
     * @return True if the Graph is empty, false otherwise.
     */
    public boolean isEmpty() {
        return totalVertices == 0;
    }

    /**
     * isFull Checks if the Graph is full.
     * @return True if the Graph is full, false otherwise.
     */
    public boolean isFull() {
        return totalVertices == capacity;
    }

    /**
     * getAdjacentVertices Creates a Queue of all vertices that are adjacent to a particular vertex.
     * @param vertex The vertex where all of its adjacent vertices are desired.
     * @return A Queue of adjacent vertices.
     */
    public Queue<T> getAdjacentVertices(T vertex) {
        Queue<T> queue = new LinkedList<>();
        int source = indexFinder(vertex);
        if(source < 0) {
            return queue;
        }
        for(int i = 0; i < vertexArray.length; i++) {
            if(edgeMatrix[source][i] != UNINITIALIZED_EDGE && vertexArray[i] != vertex && vertexArray[i] != null) {
                queue.offer(vertexArray[i]);
            }
        }
        return queue;
    }

    /**
     * getAdjacentVertexIndices Creates a Queue of the indexes of all vertices that are adjacent to a particular vertex.
     * @param vertex The vertex where all of its adjacent vertices are desired.
     * @return A Queue of adjacent vertex indices.
     */
    private Queue<Integer> getAdjacentVertexIndices(T vertex) {
        Queue<Integer> queue = new LinkedList<>();
        int source = indexFinder(vertex);
        for(int i = 0; i < totalVertices; i++) {
            if(edgeMatrix[source][i] != UNINITIALIZED_EDGE) {
                queue.offer(i);
            }
        }
        return queue;
    }

    /**
     * markVertex Marks a vertex as seen.
     * @param vertex The vertex to mark.
     */
    public void markVertex(T vertex) {
        for(int i = 0; i < totalVertices; i++) {
            try {
                if(vertexArray[i].equals(vertex)) {
                    seenVertexArray[i] = true;
                }
            } catch(NullPointerException e) {
                continue;
            }
        }
    }

    /**
     * isMarked Checks if a vertex is marked as seen.
     * @param vertex The vertex to check.
     * @return True if the vertex is marked, false otherwise.
     */
    public boolean isMarked(T vertex) {
        for(int i = 0; i < totalVertices; i++) {
            try {
                if (vertexArray[i].equals(vertex) && seenVertexArray[i]) {
                    return true;
                }
            } catch(NullPointerException e) {
                continue;
            }
        }
        return false;
    }

    /**
     * clearAllMarks Clears the seenVertexArray of all seen marks, setting each value to false.
     */
    public void clearAllMarks() {
        Arrays.fill(seenVertexArray, false);
    }

    /**
     * getAnUnmarkedVertex Returns a vertex that is not marked in no particular order.
     * @return A vertex that is not marked.
     */
    public T getAnUnmarkedVertex() {
        for(int i = 0; i < totalVertices; i++) {
            if(!seenVertexArray[i]) {
                return vertexArray[i];
            }
        }
        return null;
    }
}
