import java.util.Arrays;

public class Graph {
    private int vertex;
    double adjMatrix[][];

    public Graph(int numCities) { //constructs empty adjacency matrix
        this.vertex = numCities;
        adjMatrix = new double[vertex][vertex];
    }

    public void populateGraph (City[] cityList, int threshold) {
        for (int i =0; i < cityList.length; i++) {
            for (int j = cityList.length -1 ; j >=0; j--){
                if (cityList[i].isAdjacent(cityList[j], threshold)){
                    adjMatrix[i][j] = cityList[i].distance(cityList[j]); //adds weighted edge
                    adjMatrix[j][i] = cityList[i].distance(cityList[j]); //adds weighted edge back (undirected graph)
                }
            } //inner for
        } //outer for
    } //close method

    public void printMatrix() {
        for (int i = 0; i < this.vertex; i++) {
            for (int j = 0; j < this.vertex; j++) {
                System.out.print(adjMatrix[i][j] + " ");
            } //inner for
        } // outer for

    }

//    @Override
//    public String toString() {
//        return "Graph{" +
//                "Number of vertices: " + vertex +
//                ", adjMatrix={" + for () +
//                '}}';
//    }
} //close class

//adjacency matrix or
//adjacency lists (arraylists)
