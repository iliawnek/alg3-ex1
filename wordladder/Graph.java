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

    public void addVertex(int n, String word) {
        Vertex v = new Vertex(n);
        v.setWord(word);
        vertices.add(n, v);
    }

    public Vertex getVertex(int i) {
        return vertices.get(i);
    }

    /* Resets traversal helper fields of every Vertex in vertices. */
    private void clean() {
        for (Vertex vertex : vertices) vertex.setVisited(false);
    }

    /**
     * Find vertex in graph which represents the given word.
     * Implemented using breadth-first search.
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
                        Vertex w = vertices.get(adjacentNode.getVertexNumber());
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
     *
     * @param startWord of the word ladder to be found.
     * @param endWord   of the word ladder to be found.
     * @return shortest word ladder between startWord and endWord if possible, or null if not possible.
     */
    public ArrayList<String> findWordLadder(String startWord, String endWord) {
        // find vertex corresponding to startWord
        Vertex start = breadthFirstSearch(startWord);
        this.clean();

        // find shortest word ladder using breadth-first search
        LinkedList<Vertex> queue = new LinkedList<>();
        start.setVisited(true);
        start.setPredecessor(-1);
        queue.addLast(start);
        Vertex end = null;

        while (!queue.isEmpty() && end == null) {
            Vertex cursor = queue.removeFirst();
            for (AdjListNode adjacentNode : cursor.getAdjList()) {
                Vertex adjacent = vertices.get(adjacentNode.getVertexNumber());
                // else, continue search
                if (!adjacent.getVisited()) {
                    adjacent.setPredecessor(cursor.getIndex());
                    // check if endWord is found
                    if (adjacent.getWord().equals(endWord)) {
                        end = adjacent;
                        break;
                    }
                    adjacent.setVisited(true);
                    queue.addLast(adjacent);
                }
            }
        }

        // word ladder is impossible
        if (end == null) return null;

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
