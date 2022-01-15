import java.util.Arrays;

public class Graph {
    private int vertex;
    double adjMatrix[][];
    double distMatrix[][];

    public Graph(int numCities) { //constructs empty adjacency matrix
        this.vertex = numCities;
        adjMatrix = new double[vertex][vertex];
        distMatrix = new double[vertex][vertex];
    }

    public int initialize (City[] cityList) {
        double maxDistance = 0;
        for (int i =0; i < cityList.length; i++) {
            for (int j = cityList.length - 1; j >= 0; j--) {
                double distance = cityList[i].distance(cityList[j]);
                distMatrix[i][j] = distance;
                distMatrix[j][i] = distance;
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            } //inner for
        } //outer for
        return (int) Math.round(maxDistance + 1);
        } //close method


    public void populate (City[] cityList, int threshold) {
        for (int i =0; i < cityList.length; i++) {
            for (int j = cityList.length -1 ; j >=0; j--){
                if (cityList[i].isAdjacent(cityList[j], threshold)){
                    adjMatrix[i][j] = cityList[i].distance(cityList[j]); //adds weighted edge
                    adjMatrix[j][i] = cityList[j].distance(cityList[i]); //adds weighted edge back (undirected graph)
                }
            } //inner for
        } //outer for
    } //close method

    public void printAdjMatrix() {
        int displayThreshold = this.vertex;
        for (int i = 0; i < this.vertex; i++) {
            int lineBreakCounter = 0;
            for (int j = 0; j < this.vertex; j++) {
                System.out.print(String.format("%,5.2f", adjMatrix[i][j]) + " ");
                lineBreakCounter ++;
                if (lineBreakCounter == displayThreshold) {
                    System.out.print("\n");
                    lineBreakCounter = 0;
            }
            } //inner for
        } // outer for
    } //close method

    public void printDistMatrix() {
        int displayThreshold = this.vertex;
        for (int i = 0; i < this.vertex; i++) {
            int lineBreakCounter = 0;
            for (int j = 0; j < this.vertex; j++) {
                System.out.print(String.format("%,5.2f", distMatrix[i][j]) + " ");
                lineBreakCounter ++;
                if (lineBreakCounter == displayThreshold) {
                    System.out.print("\n");
                    lineBreakCounter = 0;
                }
            } //inner for
        } // outer for
    } //close method
} //close class

//adjacency matrix or
//adjacency lists (arraylists)
