import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * class to represent an undirected graph using adjacency lists
 */
public class Graph {

    public ArrayList<Vertex> vertices; // list of vertices in the graph

    public Graph() {
        vertices = new ArrayList<>();
    }

    public Vertex addVertex(int n, String word) {
        Vertex v = new Vertex(n);
        v.setWord(word);
        vertices.add(n, v);
        return vertices.get(n);
    }

    public Vertex getVertex(int i) {
        return vertices.get(i);
    }

    /**
     * Find shortest word ladder from startWord to endWord.
     * Implemented using Dijkstra's algorithm.
     *
     * @param start vertex to begin ladder from.
     * @param end   vertex to end ladder at.
     * @return shortest word ladder between start and end if possible, or null if not possible.
     */
    public ArrayList<String> findWordLadder(Vertex start, Vertex end) {
        // find start vertex
        start.setBestDistance(0);
        start.setPredecessor(-1);

        // set initial best distances for vertices adjacent to starting vertex
        for (AdjListNode node : start.getAdjList()) {
            Vertex adjVertex = vertices.get(node.getVertexNumber());
            adjVertex.setBestDistance(node.getWeight());
            adjVertex.setPredecessor(start.getIndex());
        }

        // initialise list of unvisited vertices
        PriorityQueue<Vertex> unvisited = new PriorityQueue<>();
        for (Vertex v : vertices) {
            if (!v.equals(start)) unvisited.add(v);
        }

        // main traversal loop
        Vertex cursor;
        boolean endFound = false;
        while (!unvisited.isEmpty()) {
            // get unvisited vertex with best distance
            cursor = unvisited.poll();
            if (cursor.getBestDistance() == Integer.MAX_VALUE) return null; // path impossible; no more reachable vertices

            // check if endWord is found
            if (cursor.equals(end)) {
                endFound = true;
                break;
            }

            for (AdjListNode node : cursor.getAdjList()) {
                Vertex adjVertex = vertices.get(node.getVertexNumber());
                int distanceThroughCursor = cursor.getBestDistance() + node.getWeight();
                if (distanceThroughCursor < adjVertex.getBestDistance()) {
                    adjVertex.setBestDistance(distanceThroughCursor);
                    adjVertex.setPredecessor(cursor.getIndex());

                    // update position of updated vertex on min-heap
                    unvisited.remove(adjVertex);
                    unvisited.add(adjVertex);

                }
            }
        }

        if (!endFound) return null; // word ladder is impossible

        // construct and return word ladder
        ArrayList<String> wordLadder = new ArrayList<>();
        cursor = end;
        wordLadder.add(cursor.getWord());
        while (cursor.getPredecessor() != -1) { // while predecessor exists
            cursor = vertices.get(cursor.getPredecessor());
            wordLadder.add(0, cursor.getWord());
        }
        return wordLadder;
    }

}
