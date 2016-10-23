import java.util.ArrayList;
import java.util.LinkedList;

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
     *
     * @param start vertex to begin ladder from.
     * @param end   vertex to end ladder at.
     * @return shortest word ladder between start and end if possible, or null if not possible.
     */
    public ArrayList<String> findWordLadder(Vertex start, Vertex end) {
        // find shortest word ladder using breadth-first search
        LinkedList<Vertex> queue = new LinkedList<>();
        start.setVisited(true);
        start.setPredecessor(-1);
        queue.addLast(start);
        boolean endFound = false;

        while (!queue.isEmpty() && !endFound) {
            Vertex cursor = queue.removeFirst();
            for (AdjListNode adjacentNode : cursor.getAdjList()) {
                Vertex adjacent = vertices.get(adjacentNode.getVertexNumber());
                if (!adjacent.getVisited()) {
                    adjacent.setPredecessor(cursor.getIndex());
                    // check if endWord is found
                    if (adjacent.equals(end)) {
                        endFound = true;
                        break;
                    }
                    adjacent.setVisited(true);
                    queue.addLast(adjacent);
                }
            }
        }

        // word ladder is impossible
        if (!endFound) return null;

        // construct and return word ladder
        ArrayList<String> wordLadder = new ArrayList<>();
        Vertex cursor = end;
        wordLadder.add(cursor.getWord());
        while (cursor.getPredecessor() != -1) { // while predecessor exists
            cursor = vertices.get(cursor.getPredecessor());
            wordLadder.add(0, cursor.getWord());
        }
        return wordLadder;
    }
}
