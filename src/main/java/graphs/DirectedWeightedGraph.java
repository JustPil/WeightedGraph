package graphs;

import Nodes.Node;
import java.util.*;

public class DirectedWeightedGraph<T> implements GraphInterface<T> {
    private int capacity;
    private int totalVertices = 0;
    private Node<T>[] vertexArray;
    private boolean[] seenVertexArray;
    private final double LOAD_THRESHOLD = .75;

    /**
     * Constructor initializes capacity to 25 elements.
     */
    public DirectedWeightedGraph() {
        vertexArray = new Node[capacity = 25];
        seenVertexArray = new boolean[capacity];
    }

    /**
     * Constructor initializes capacity to a positive user-defined number. If the parameter is negative the default
     * capacity is invoked.
     * @param cap The non-negative vertex capacity.
     */
    public DirectedWeightedGraph(int cap) {
        capacity = cap > 0 ? cap : 25;
        vertexArray = new Node[capacity];
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
                vertexArray[totalVertices] = new Node<>(vertex);
            } else {
                for(int i = 0; i < vertexArray.length; i++) {
                    if(vertexArray[i] == null) {
                        vertexArray[i] = new Node<>(vertex);
                    }
                }
            }
            totalVertices++;
            if((double) (totalVertices / capacity) >= LOAD_THRESHOLD) {
                resize();
            }
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
                if(vertexArray[i].getData().equals(vertex)) {
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
            Node<T> parser = vertexArray[dequeue];
            while(true) {
                if(parser.getNext().getData() == vertex) {
                    parser.setNext(parser.getNext().getNext());
                    break;
                }
                parser = parser.getNext();
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
        int sourceIndex = indexFinder(source);
        if(sourceIndex < 0) {
            return false;
        }
        Node<T> parser = vertexArray[sourceIndex];
        while(true) {
            if(parser.getNext() == null) {
                parser.setNext(new Node<>(destination, weight));
                break;
            }
            parser = parser.getNext();
        }
        return true;
    }

    /**
     * getEdge Returns the weight of an edge between two vertices if present.
     * @param source The source vertex.
     * @param destination The destination vertex.
     * @return the weight of the edge if found, a negative weight if there was no edge.
     */
    public int getEdge(T source, T destination) {
        int index = indexFinder(source);
        if(index < 0) {
            return -1;
        }
        Node<T> parser = vertexArray[index];
        while(parser != null) {
            if(parser.getData() == destination) {
                return parser.getWeight();
            }
            parser = parser.getNext();
        }
        return -1;
    }

    /**
     * containsEdge Checks if an edge exists between two vertices.
     * @param source The source vertex.
     * @param destination The destination vertex.
     * @return True if an edge exists, false otherwise.
     */
    public boolean containsEdge(T source, T destination) {
        int index = indexFinder(source);
        if(index < 0) {
            return false;
        }
        Node<T> parser = vertexArray[index];
        while(parser != null) {
            if(parser.getData() == destination) {
                return true;
            }
            parser = parser.getNext();
        }
        return false;
    }

    /**
     * removeEdge Removes an edge between two vertices if present.
     * @param source The source vertex.
     * @param destination The destination vertex.
     * @return True if the edge is removed, false if the edge was not found.
     */
    public boolean removeEdge(T source, T destination) {
        int index = indexFinder(source);
        if(index < 0) {
            return false;
        }
        Node<T> parser = vertexArray[index];
        while(parser != null) {
            if(parser.getNext().getData() == destination) {
                parser.setNext(parser.getNext().getNext());
                return true;
            }
            parser = parser.getNext();
        }
        return false;
    }

    /**
     * indexFinder Returns the index of a vertex.
     * @param vertex The vertex to search for.
     * @return The index of the vertex, or a negative number if the vertex was not found.
     */
    private int indexFinder(T vertex) {
        for(int i = 0; i < vertexArray.length; i++) {
            try {
                if (vertexArray[i].getData().equals(vertex)) {
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
        Node<T> parser = vertexArray[source];
        while(parser != null) {
            if(parser != vertexArray[source]) {
                queue.offer(parser.getData());
            }
            parser = parser.getNext();
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
        int index = indexFinder(vertex);
        if(vertexArray[index].getNext() == null) {
            return queue;
        }
        Set<T> set = new HashSet<>();
        Node<T> parser = vertexArray[index];
        while(parser != null) {
            set.add(parser.getData());
            parser = parser.getNext();
        }
        for(T t : set) {
            queue.offer(indexFinder(t));
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
                if(vertexArray[i].getData().equals(vertex)) {
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
                if(vertexArray[i].getData().equals(vertex) && seenVertexArray[i]) {
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
            if(!seenVertexArray[i] && vertexArray[i] != null) {
                return vertexArray[i].getData();
            }
        }
        return null;
    }

    /**
     * resize Resizes the internal arrays to double capacity if the original array's load threshold is met or exceeded.
     */
    private void resize() {
        Node<T>[] resizedArray = new Node[capacity *= 2];
        boolean[] resizedSeenArray = new boolean[capacity];
        for(int i = 0; i < vertexArray.length; i++) {
            if(vertexArray[i] != null) {
                resizedArray[i] = vertexArray[i];
            }
            if(seenVertexArray[i]) {
                resizedSeenArray[i] = true;
            }
        }
        vertexArray = resizedArray;
        seenVertexArray = resizedSeenArray;
    }
}
