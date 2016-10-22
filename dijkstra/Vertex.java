import java.util.LinkedList;


/**
 * class to represent a vertex in a graph
 */
class Vertex implements Comparable<Vertex> {

    private LinkedList<AdjListNode> adjList; // the adjacency list of the vertex
    private int index; // the index of the vertex

    private String word; // word represented by the vertex
    private boolean visited; // whether vertex has been visited in a traversal
    private int predecessor; // index of predecessor vertex in a traversal
    private int bestDistance; // best distance from starting vertex in Dijkstra's algorithm

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

    public int getBestDistance() {
        return bestDistance;
    }

    public void setBestDistance(int bestDistance) {
        this.bestDistance = bestDistance;
    }

    void addToAdjList(int n, int weight) {
        adjList.addLast(new AdjListNode(n, weight));
    }

    int vertexDegree() {
        return adjList.size();
    }

    /**
     * @param v vertex to which this vertex is compared
     * @return numerical difference between single different character of this word and vertex v's word,
     * return -1 if words differ by more or less than 1 character
     */
    int letterDifference(Vertex v) {
        String thisWord = this.word;
        String thatWord = v.getWord();
        int differentCharacter = -1;

        for (int i = 0; i < thisWord.length(); i++) {
            if (thisWord.charAt(i) != thatWord.charAt(i)) {
                if (differentCharacter != -1) return -1; // two differences found
                differentCharacter = i;
            };
        }

        return Math.abs(thisWord.charAt(differentCharacter) - thatWord.charAt(differentCharacter));
    }

    @Override
    public String toString() {
        return word;
    }

    /**
     * @param obj to compare this vertex against
     * @return true if index are the same in each vertex
     * (not best implementation, but is appropriate enough for this scenario)
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Vertex && index == ((Vertex) obj).getIndex();
    }

    @Override
    public int compareTo(Vertex that) {
        return this.bestDistance - that.getBestDistance();
    }
}
