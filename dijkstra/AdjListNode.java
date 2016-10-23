
/**
 * class to represent an entry in the adjacency list of a vertex
 * in a graph
 */
public class AdjListNode {

    private int vertexNumber;
    private int weight;

    public AdjListNode(int n, int weight) {
        vertexNumber = n;
        this.weight = weight;
    }

    public int getVertexNumber() {
        return vertexNumber;
    }

    public int getWeight() {
        return weight;
    }
}
