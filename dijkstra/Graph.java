import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * class to represent an undirected graph using adjacency lists
 */
public class Graph {

    public Vertex[] vertices; // list of vertices in the graph
    int numVertices;

    public Graph(int n) {
        vertices = new Vertex[n];
        numVertices = n;
        for (int i = 0; i < n; i++) vertices[i] = new Vertex(i);
    }

    public void setVertex(int n, String word) {
        vertices[n].setWord(word);
    }

    public Vertex getVertex(int i) {
        return vertices[i];
    }

    /* Resets traversal helper fields of every Vertex in vertices. */
    private void clean() {
        for (Vertex vertex : vertices) {
            vertex.setVisited(false);
            vertex.setBestDistance(Integer.MAX_VALUE);
        }
    }

    /**
     * Find vertex in graph which represents the given word.
     *
     * @param word to be searched for.
     * @return Vertex which represents word, or null if Vertex not found.
     */
    public Vertex breadthFirstSearch(String word) {
        this.clean();
        LinkedList<Vertex> queue = new LinkedList<>();
        for (Vertex v : vertices) {
            if (v.getWord().equals(word)) return v;
            if (!v.getVisited()) {
                v.setVisited(true);
                v.setPredecessor(v.getIndex());
                queue.addLast(v);
                while (!queue.isEmpty()) {
                    Vertex u = queue.removeFirst();
                    for (AdjListNode adjacentNode : u.getAdjList()) {
                        Vertex w = vertices[adjacentNode.getVertexNumber()];
                        if (w.getWord().equals(word)) return w;
                        if (!w.getVisited()) {
                            w.setVisited(true);
                            w.setPredecessor(u.getIndex());
                            queue.addLast(w);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Find shortest word ladder from startWord to endWord.
     * Implemented using Dijkstra's algorithm.
     *
     * @param startWord of the word ladder to be found.
     * @param endWord   of the word ladder to be found.
     * @return Shortest word ladder between startWord and endWord if possible, or null if not possible.
     * Returned ArrayList begins with minimum path length.
     */
    public ArrayList<String> findWordLadder(String startWord, String endWord) {
        // find start vertex
        Vertex start = breadthFirstSearch(startWord);
        if (start.getAdjList().isEmpty()) return null; // start vertex has no adjacent vertices
        this.clean();
        start.setBestDistance(0);
        start.setPredecessor(-1);

        // set initial best distances for vertices adjacent to starting vertex
        for (AdjListNode node : start.getAdjList()) {
            Vertex adjVertex = vertices[node.getVertexNumber()];
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
        Vertex end = null;
        while (!unvisited.isEmpty()) {
            // get unvisited vertex with best distance
            cursor = unvisited.poll();
            if (cursor.getBestDistance() == Integer.MAX_VALUE) return null; // path impossible; no more reachable vertices

            // check if endWord is found
            if (cursor.getWord().equals(endWord)) {
                end = cursor;
                break;
            }

            for (AdjListNode node : cursor.getAdjList()) {
                Vertex adjVertex = vertices[node.getVertexNumber()];
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

        if (end == null) return null; // word ladder is impossible

        // construct and return word ladder
        ArrayList<String> wordLadder = new ArrayList<>();
        cursor = end;
        wordLadder.add(cursor.getWord());
        while (cursor.getPredecessor() != -1) { // while predecessor exists
            cursor = vertices[cursor.getPredecessor()];
            wordLadder.add(0, cursor.getWord());
        }
        wordLadder.add(0, Integer.toString(end.getBestDistance()));
        return wordLadder;
    }

}
