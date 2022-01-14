public class Graph {

    private final int numVertices;

    public Graph(int numCities) {
        this.numVertices = numCities;
        double[][] adjMatrix = new double[this.numVertices][this.numVertices];
    }
}
//adjacency matrix or
//adjacency lists (arraylists)
