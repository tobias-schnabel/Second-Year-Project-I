import java.util.*;

public class Graph {
   private final int vertex;
   double[][] adjMatrix;
   double[][] distMatrix;
   double[][] payoffMatrix;
   double[][] outcomeMatrix;

   public Graph(int numCities) { //constructs empty adjacency_binary matrix
      this.vertex = numCities;
      adjMatrix = new double[vertex][vertex];
      distMatrix = new double[vertex][vertex];
      payoffMatrix = new double[vertex][vertex];
      outcomeMatrix = new double[vertex][vertex];

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

   public double[][] payoffMatrix(City[] cityList) {
      int totalPop = cityList[0].getTotalPop(cityList);
      int n = cityList.length;
   
      for(int i = 0; i < n; i++){
         for(int j = 0; j < n; j++){
            if(i == j){
               payoffMatrix[i][j] += totalPop / (double) 2;
            } else {
               for (City city : cityList) {
                  if (cityList[i].distance(city) < cityList[j].distance(city)) {
                     payoffMatrix[i][j] += city.getNumInhab();
                  }
               } //innermost for
            } //close else
         } //close second for
      } //close outer for
      return payoffMatrix;
   } //close method

   public double[][] payoffMatrixCustom(City[] cityList, int threshold) {
      int totalPop = cityList[0].getTotalPop(cityList);
      int n = cityList.length;

      for(int i = 0; i < n; i++){
         for(int j = 0; j < n; j++){
            for (City city : cityList) {
               if (i != j) {
                  if (cityList[i].isAdjacent(city, threshold) && cityList[i].distance(city) < cityList[j].distance(city)) {
                     payoffMatrix[i][j] += city.getNumInhab();
                  }
               } else { //if (cityList[i].isAdjacent(city, threshold)) {
                  payoffMatrix[i][j] += city.getNumInhab() / (double) 2;
               }
            } //innermost for
         } //close second for
      } //close outer for
      return payoffMatrix;
   } //close method

   public double[][] payoffMatrixBudget(City[] cityList, int threshold, int budget) {
      int totalPop = cityList[0].getTotalPop(cityList);
      int n = cityList.length;

      for(int i = 0; i < n; i++){
         for(int j = 0; j < n; j++){
            for (City city : cityList) {
               if (i != j) {
                  if (cityList[i].isAdjacent(city, threshold) && cityList[i].distance(city) < cityList[j].distance(city)) {

                     payoffMatrix[i][j] += cityList[i].getRevenue(city, threshold, budget);
                  }
               } else { //if (cityList[i].isAdjacent(city, threshold)) {
                  payoffMatrix[i][j] += cityList[i].getRevenue(city, threshold, budget) / (double) 2;
               }
            } //innermost for
         } //close second for
      } //close outer for
      return payoffMatrix;
   } //close method

   public void setOutcomeMatrix(double[][] result) {
      this.outcomeMatrix = result;
   }

   public void adjacency(City[] cityList, int threshold) {
      for (int i =0; i < cityList.length; i++) {
         for (int j = cityList.length -1 ; j >=0; j--){
            if (cityList[i].isAdjacent(cityList[j], threshold)){
               if(i == j){
                  adjMatrix[i][j] = 0;
               } else {
                  adjMatrix[i][j] = cityList[i].distance(cityList[j]); //adds weighted edge
                  adjMatrix[j][i] = cityList[j].distance(cityList[i]); //adds weighted edge back (undirected graph)
               }
            }
         } //inner for
      } //outer for
   } //close method



   public void printMatrix(String matrixName) {
      double[][] mat = new double[this.vertex][this.vertex];
      if (matrixName.equalsIgnoreCase("adjacency")){
         mat = adjMatrix;
      } else if (matrixName.equalsIgnoreCase("distance")) {
         mat = distMatrix;
      } else if (matrixName.equalsIgnoreCase("payoff")) {
         mat = payoffMatrix;
      } else if (matrixName.equalsIgnoreCase("outcome")) {
         mat = outcomeMatrix;
      }

      System.out.println("\n" + matrixName + " matrix:");
      for (int i = 0; i < this.vertex; i++) {
         int lineBreakCounter = 0;
         for (int j = 0; j < this.vertex; j++) {
            System.out.print(mat[i][j] + " ");
            lineBreakCounter ++;
            if (lineBreakCounter == this.vertex) {
               System.out.print("\n");
               lineBreakCounter = 0;
            }
         } //inner for
      } // outer for
      System.out.print("\n");
   } //close method


} //close class

