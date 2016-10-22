
/**
 * class to represent an entry in the adjacency list of a vertex
 * in a graph
 */
public class AdjListNode {

    private int vertexNumber;
    private int weight;

    public AdjListNode(int n, int weight) {
        vertexNumber = n;
        weight = weight;
    }

    public int getVertexNumber() {
        return vertexNumber;
    }

    public void setVertexNumber(int n) {
        vertexNumber = n;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
