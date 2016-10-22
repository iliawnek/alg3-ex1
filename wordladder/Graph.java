import java.util.ArrayList;
import java.util.LinkedList;

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

    public int size() {
        return numVertices;
    }

    public void setVertex(int n, String word) {
        vertices[n].setWord(word);
    }

    public Vertex getVertex(int i) {
        return vertices[i];
    }

    /* Resets all traversal helper fields of every Vertex in vertices. */
    private void clean() {
        for (Vertex vertex : vertices) vertex.setVisited(false);
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
     * Find word ladder from startWord to endWord.
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
        start.setPredecessor(start.getIndex());
        queue.addLast(start);
        Vertex end = null;

        while (!queue.isEmpty() && end == null) {
            Vertex cursor = queue.removeFirst();
            for (AdjListNode adjacentNode : cursor.getAdjList()) {
                Vertex adjacent = vertices[adjacentNode.getVertexNumber()];
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
        if (end == null) {
            return null;
        }

        // construct and return word ladder
        ArrayList<String> wordLadder = new ArrayList<>();
        Vertex cursor = end;
        wordLadder.add(cursor.getWord());
        while (cursor.getPredecessor() != cursor.getIndex()) { // while predecessor exists
            cursor = vertices[cursor.getPredecessor()];
            wordLadder.add(0, cursor.getWord());
        }
        return wordLadder;
    }

    public String adjListToString(Vertex v) {
        String s = "[";
        LinkedList<AdjListNode> adjList = v.getAdjList();
        for (int i = 0; i < adjList.size(); i++) {
            int n = adjList.get(i).getVertexNumber();
            if (i != adjList.size() - 1) s += vertices[n] + ", ";
        }
        return s;
    }
}
