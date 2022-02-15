package UG;

import java.util.Queue;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        UndirectedWeightedGraph<String> graph = new UndirectedWeightedGraph<>();
        String choice = "";
        while(!choice.equalsIgnoreCase("X")) {
            menu();
            choice = scn.nextLine();
            if(choice.equals("1")) {
                System.out.print("Enter a name for a vertex to add: ");
                String input = scn.nextLine();
                graph.addVertex(input);
                System.out.println("Added: " + input);
            } else if(choice.equals("2")) {
                System.out.print("Enter a name of a vertex to search for: ");
                String input = scn.nextLine();
                System.out.println("Graph contains " + input + ": " + graph.hasVertex(input));
            } else if(choice.equals("3")) {
                System.out.print("Enter a name of a vertex to remove: ");
                String input = scn.nextLine();
                System.out.println(input + " removed: " + graph.removeVertex(input));
            } else if(choice.equals("4")) {
                System.out.println("Total vertices: " + graph.getTotalVertices());
            } else if(choice.equals("5")) {
                System.out.print("To establish an edge between two vertices, enter the first vertex: ");
                String first = scn.nextLine();
                System.out.print("Enter the second vertex: ");
                String second = scn.nextLine();
                System.out.print("Enter the weight (positive integer) of the edge: ");
                int weight = scn.nextInt();
                scn.nextLine();
                System.out.println("Edge added: " + graph.addEdge(first, second, weight));
            } else if(choice.equals("6")) {
                System.out.print("To retrieve an edge between two vertices, enter the first vertex: ");
                String first = scn.nextLine();
                System.out.print("Enter the second vertex: ");
                String second = scn.nextLine();
                System.out.println("Edge between " + first + " and " + second + ": " + graph.getEdge(first, second));
            } else if(choice.equals("7")) {
                System.out.print("To verify that an edge between two vertices exists, enter the first vertex: ");
                String first = scn.nextLine();
                System.out.print("Enter the second vertex: ");
                String second = scn.nextLine();
                System.out.println("Edge between " + first + " and " + second + ": " + graph.containsEdge(first, second));
            } else if(choice.equals("8")) {
                System.out.print("To remove an edge between two vertices, enter the first vertex: ");
                String first = scn.nextLine();
                System.out.print("Enter the second vertex: ");
                String second = scn.nextLine();
                System.out.println("Edge removed: " + graph.removeEdge(first, second));
            } else if(choice.equals("9")) {
                System.out.println("Empty graph: " + graph.isEmpty());
            } else if(choice.equals("10")) {
                System.out.println("Full graph: " + graph.isFull());
            } else if(choice.equals("11")) {
                System.out.print("To find all adjacent vertices to a particular vertex, enter the vertex: ");
                String input = scn.nextLine();
                Queue<String> queue = graph.adjacentVertices(input);
                if(queue.isEmpty()) {
                    System.out.println("No adjacent vertices");
                } else {
                    System.out.println("Adjacent vertices to " + input + ": ");
                    while(!queue.isEmpty()) {
                        System.out.println(queue.remove());
                    }
                }
            } else if(choice.equals("12")) {
                System.out.print("To mark a vertex as seen, enter that vertex: ");
                String input = scn.nextLine();
                graph.markVertex(input);
                System.out.println("If present, the vertex has been marked");
            } else if(choice.equals("13")) {
                System.out.print("To verify whether a vertex is marked as seen, enter that vertex: ");
                String input = scn.nextLine();
                System.out.println("Vertex " + input + " marked: " + graph.isMarked(input));
            } else if(choice.equals("15")) {
                graph.clearAllMarks();
                System.out.println("All marks have been cleared");
            } else if(choice.equals("16")) {
                System.out.print("A single, random unmarked vertex is: " + graph.getAnUnmarkedVertex());
            } else if(choice.equalsIgnoreCase("X")) {
                break;
            } else {
                System.out.println("Invalid choice");
            }
        }
        scn.close();
    }

    /**
     * menu A menu of choices for interacting with the Graph.
     */
    public static void menu() {
        System.out.print("\nMethods for Weighted Graph (Undirected)\n1 - ADD VERTEX - Adds a vertex to the " +
                "Graph\n2 - HAS VERTEX - Checks if a vertex is present\n3 - REMOVE VERTEX - Removes a specified " +
                "vertex if present\n4 - GET TOTAL VERTICES - Returns the total number of vertices in the graph" +
                "\n5 - ADD EDGE - Adds an edge between two vertices\n6 - GET EDGE - Returns the edge weight " +
                "between two vertices\n7 - CONTAINS EDGE - Checks if an edge is present between two vertices\n8" +
                " - REMOVE EDGE - Removes an edge between two vertices\n9 - IS EMPTY - Checks if the Graph is " +
                "empty of vertices\n10 - IS FULL - Checks if the Graph contains maximum allowed vertices\n11 - " +
                "ADJACENT VERTICES - Returns all adjacent vertices to a specified vertex\n12 - MARK VERTEX - "+
                "Marks a vertex as seen\n13 - IS MARKED - Checks if a vertex is marked as seen\n14 - CLEAR ALL " +
                "MARKS - Removes all marks from the Graph\n15 - GET AN UNMARKED VERTEX - Returns a single " +
                "unmarked vertex\nX - TERMINATE\n\nEnter choice: ");
    }
}
