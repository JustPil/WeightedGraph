package graphtests;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import graphs.DirectedWeightedGraph;
import graphs.GraphInterface;
import java.util.Queue;

public class DirectedWeightedGraphTests {
    private final GraphInterface<Integer> graph = new DirectedWeightedGraph<>();

    @Test
    public void addInitialVertex() {
        graph.addVertex(1);
        var result = graph.hasVertex(1) && !graph.isEmpty() && graph.getTotalVertices() == 1;
        Assertions.assertTrue(result);
    }
    @Test
    public void addMultipleVertices() {
        for(int i = 0; i < 3; i++) {
            graph.addVertex(i + 1);
        }
        var result = graph.hasVertex(1) && graph.hasVertex(2) && graph.hasVertex(3) && graph.getTotalVertices() == 3;
        Assertions.assertTrue(result);
    }
    @Test
    public void addVertexToFullGraph() {
        for(int i = 0; i < 25; i++) {
            graph.addVertex(i + 1);
        }
        var result = graph.addVertex(26);
        Assertions.assertFalse(result);
    }
    @Test
    public void addVertexToFormerlyFullGraph() {
        for(int i = 0; i < 25; i++) {
            graph.addVertex(i + 1);
        }
        graph.removeVertex(1);
        graph.addVertex(0);
        var result = graph.hasVertex(0) && !graph.hasVertex(1);
        Assertions.assertTrue(result);
    }
    @Test
    public void removeAllVerticesFromFullGraph() {
        for(int i = 0; i < 25; i++) {
            graph.addVertex(i + 1);
        }
        for(int i = 0; i < 25; i++) {
            graph.removeVertex(i + 1);
        }
        var result = graph.isEmpty() && graph.getTotalVertices() == 0;
        Assertions.assertTrue(result);
    }
    @Test
    public void addInitialEdge() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2, 100);
        var result = graph.getEdge(1, 2);
        Assertions.assertEquals(100, result);
    }
    @Test
    public void attemptReverseDirectionEdgeTraversal() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2, 100);
        var result = graph.getEdge(2, 1);
        Assertions.assertEquals(-1, result);
    }
    @Test
    public void addMultipleEdgesToOneVertex() {
        for(int i = 0; i < 4; i++) {
            graph.addVertex(i + 1);
            if(i > 0) {
                graph.addEdge(1, i + 1, i * 10);
            }
        }
        Queue<Integer> queue = graph.getAdjacentVertices(1);
        var result = queue.poll() == 2 && queue.poll() == 3 && queue.poll() == 4;
        Assertions.assertTrue(result);
    }
    @Test
    public void checkIfValidEdgeIsPresent() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2, 10);
        var result = graph.containsEdge(1, 2);
        Assertions.assertTrue(result);
    }
    @Test
    public void checkIfInvalidEdgeIsPresent() {
        var result = graph.containsEdge(1, 2);
        Assertions.assertFalse(result);
    }
    @Test
    public void removeValidEdge() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2, 10);
        graph.removeEdge(1, 2);
        var result = graph.getEdge(1, 2) == -1 && !graph.containsEdge(1, 2);
        Assertions.assertTrue(result);
    }
    @Test
    public void removeInvalidEdge() {
        var result = graph.removeEdge(1, 2);
        Assertions.assertFalse(result);
    }
    @Test
    public void removeOneEdgeFromVertexWithMultipleEdges() {
        for(int i = 0; i < 4; i++) {
            graph.addVertex(i + 1);
            if(i > 0) {
                graph.addEdge(1, i + 1, i * 10);
            }
        }
        graph.removeEdge(1, 3);
        var result = !graph.containsEdge(1, 3) && graph.getEdge(1, 3) == -1;
        Assertions.assertTrue(result);
    }
    @Test
    public void removeAllEdgesFromVertexWithMultipleEdges() {
        for(int i = 0; i < 4; i++) {
            graph.addVertex(i + 1);
            if(i > 0) {
                graph.addEdge(1, i + 1, i * 10);
            }
        }
        for(int i = 1; i < 4; i++) {
            graph.removeEdge(1, i + 1);
        }
        Queue<Integer> queue = graph.getAdjacentVertices(1);
        var result = queue.isEmpty();
        Assertions.assertTrue(result);
    }
    @Test
    public void checkifMaximumCapacityGraphIsFull() {
        for(int i = 0; i < 25; i++) {
            graph.addVertex(i + 1);
        }
        var result = graph.isFull();
        Assertions.assertTrue(result);
    }
    @Test
    public void checkifGraphIsEmptyAfterRemovingAllVertices() {
        for(int i = 0; i < 25; i++) {
            graph.addVertex(i + 1);
        }
        for(int i = 0; i < 25; i++) {
            graph.removeVertex(i + 1);
        }
        var result = graph.isEmpty();
        Assertions.assertTrue(result);
    }
    @Test
    public void markVertex() {
        graph.addVertex(1);
        graph.markVertex(1);
        var result = graph.isMarked(1);
        Assertions.assertTrue(result);
    }
    @Test
    public void markInvalidVertex() {
        graph.markVertex(1);
        var result = graph.isMarked(1);
        Assertions.assertFalse(result);
    }
    @Test
    public void clearAllMarkedVertices() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.markVertex(1);
        graph.markVertex(2);
        graph.clearAllMarks();
        var result = !graph.isMarked(1) && !graph.isMarked(2);
        Assertions.assertTrue(result);
    }
    @Test
    public void getUnmarkedVertex() {
        graph.addVertex(1);
        var result = graph.getAnUnmarkedVertex();
        Assertions.assertEquals(1, result);
    }
    @Test
    public void getUnmarkedVertexFromEmptyGraph() {
        var result = graph.getAnUnmarkedVertex();
        Assertions.assertNull(result);
    }
}
