public class Graph {

    private final int numVertices;

    public Graph(int numCities) { //constructs empty adjacency matrix
        this.numVertices = numCities;
        double[][] adjMatrix = new double[this.numVertices][this.numVertices];
    }

    public void populateGraph (City[] cityList0, double threshold) {

    }
}
//adjacency matrix or
//adjacency lists (arraylists)
