import java.util.LinkedList;


/**
 * class to represent a vertex in a graph
 */
class Vertex {

    private LinkedList<AdjListNode> adjList; // the adjacency list of the vertex
    private int index; // the index of the vertex

    private String word; // word represented by the vertex
    private boolean visited; // whether vertex has been visited in a traversal
    private int predecessor; // index of predecessor vertex in a traversal

    /**
     * creates a new instance of Vertex
     */
    public Vertex(int n) {
        adjList = new LinkedList<>();
        index = n;
        visited = false;
    }

    /**
     * copy constructor
     */
    public Vertex(Vertex v) {
        adjList = v.getAdjList();
        index = v.getIndex();
        visited = v.getVisited();
    }

    LinkedList<AdjListNode> getAdjList() {
        return adjList;
    }

    int getIndex() {
        return index;
    }

    void setIndex(int n) {
        index = n;
    }

    String getWord() {
        return word;
    }

    void setWord(String word) {
        this.word = word;
    }

    boolean getVisited() {
        return visited;
    }

    void setVisited(boolean b) {
        visited = b;
    }

    int getPredecessor() {
        return predecessor;
    }

    void setPredecessor(int n) {
        predecessor = n;
    }

    void addToAdjList(int n) {
        adjList.addLast(new AdjListNode(n));
    }

    int vertexDegree() {
        return adjList.size();
    }

    /**
     * @param v vertex to which this vertex is compared
     * @return true if this vertex and vertex v differ by only one character, false otherwise
     */
    boolean oneLetterDifference(Vertex v) {
        String thisWord = this.word;
        String thatWord = v.getWord();
        boolean oneDifferenceFound = false;

        for (int i = 0; i < thisWord.length(); i++) {
            if (thisWord.charAt(i) != thatWord.charAt(i)) {
                if (oneDifferenceFound) return false; // two differences found
                oneDifferenceFound = true;
            };
        }

        return oneDifferenceFound;
    }

    @Override
    public String toString() {
        return word;
    }

}
