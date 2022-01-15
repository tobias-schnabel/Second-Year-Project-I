public class Graph {
    private final int vertex;
    double[][] adjMatrix;
    double[][] distMatrix;
    double[][] closerMatrix;
    double[][] popMatrix;
    int[][] stratMatrix;

    public Graph(int numCities) { //constructs empty adjacency matrix
        this.vertex = numCities;
        adjMatrix = new double[vertex][vertex];
        distMatrix = new double[vertex][vertex];
        closerMatrix = new double[vertex][vertex];
        popMatrix = new double[vertex][vertex];
        stratMatrix = new int[vertex][vertex];
    }

    public int initialize (City[] cityList) {
        double maxDistance = 0;
        for (int i =0; i < cityList.length; i++) {
            for (int j =0; j < cityList.length; j++) {
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

    public void populate (City[] cityList) {
        for (int i = 0; i < cityList.length; i++) {
            for (int j = 0; j < cityList.length; j++) {
                popMatrix[i][j] = cityList[j].getNumInhab() * closerMatrix[i][j];
            } //inner for
        } //outer for
    } //close method

    public void choices (City[] cityList, Location[] locationList) {
        int totalPop = 0;
        for (City city : cityList){
            totalPop += city.getNumInhab();
        }
        System.out.println(totalPop);

        for (int i = 0; i < cityList.length; i++) {
            double numAvailCustomers = 0;
            double totalAvailCustomers = 0;
            for (int j = 0; j < cityList.length; j++) {
                numAvailCustomers = cityList[j].getNumInhab() * closerMatrix[i][j];
                totalAvailCustomers += numAvailCustomers;
                popMatrix[i][j] = numAvailCustomers;
            } //inner for

//            for (int k = 0; k < cityList.length; k++) {
//                numAvailCustomers = cityList[k].getNumInhab() * closerMatrix[i][k];
//                totalAvailCustomers += numAvailCustomers;
//                popMatrix[i][k] = numAvailCustomers;
//                stratMatrix[i][k] =
//                        locationList[i].addCustomers(numAvailCustomers);
//            }
        } //outer for
    } //close method


    public void adjacency (City[] cityList, int threshold) {
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
        System.out.println("");
    } //close method

    public void printCloserMatrix() {
        int displayThreshold = this.vertex;
        for (int i = 0; i < this.vertex; i++) {
            int lineBreakCounter = 0;
            for (int j = 0; j < this.vertex; j++) {
                System.out.print(closerMatrix[i][j] + " ");
                lineBreakCounter ++;
                if (lineBreakCounter == displayThreshold) {
                    System.out.print("\n");
                    lineBreakCounter = 0;
                }
            } //inner for
        } // outer for
        System.out.println("");
    } //close method

    public void printPopMatrix() {
        int displayThreshold = this.vertex;
        for (int i = 0; i < this.vertex; i++) {
            int lineBreakCounter = 0;
            for (int j = 0; j < this.vertex; j++) {
                System.out.print(popMatrix[i][j] + " ");
                lineBreakCounter ++;
                if (lineBreakCounter == displayThreshold) {
                    System.out.print("\n");
                    lineBreakCounter = 0;
                }
            } //inner for
        } // outer for
        System.out.println("");
    } //close method

} //close class

//adjacency matrix or
//adjacency lists (arraylists)
