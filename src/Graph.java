import java.util.*;

public class Graph {
   private final int vertex;
   double[][] adjMatrix;
   double[][] distMatrix;
   double[][] payoffMatrix;
   double[][] popMatrix;
   int[][] stratMatrix;

   public Graph(int numCities) { //constructs empty adjacency matrix
      this.vertex = numCities;
      adjMatrix = new double[vertex][vertex];
      distMatrix = new double[vertex][vertex];
      payoffMatrix = new double[vertex][vertex];
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

//    public void populate (City[] cityList) {
//        for (int i = 0; i < cityList.length; i++) {
//            for (int j = 0; j < cityList.length; j++) {
//                popMatrix[i][j] = cityList[j].getNumInhab() * closerMatrix[i][j];
//            } //inner for
//        } //outer for
//    } //close method

   public void payoffMatrix(City[] cityList) {
      int totalPop = 0;
      for (City city : cityList){
         totalPop += city.getNumInhab();
      }
      System.out.println(totalPop);
      int n = cityList.length;
   
      for(int i = 0; i < n; i++){
         for(int j = 0; j < n; j++){
            if(i == j){
               payoffMatrix[i][j] += totalPop / (double) 2;
            } else {
               for(int k = 0; k < n; k++){
                  if(cityList[i].distance(cityList[k]) < cityList[j].distance(cityList[k])){
                     payoffMatrix[i][j] += cityList[k].getNumInhab();
                  }
               } //innermost for
            } //close else
         } //close second for
      } //close outer for
   } //close method

//    public void reduce (City[] cityList) {
//        Stack<double[]> stack1 = new Stack<double[]>();
//        Stack<double[]> stack2 = new Stack<double[]>();
//
//        for (int i = 0; i < cityList.length; i++) {
//            stack1.push(payoffMatrix[i]) ;
//        }
//        //every row now on first stack
//        double[] candidateRow = new double[cityList.length];
//        while (! stack1.isEmpty()) {
//            candidateRow = stack1.pop();
//        }
//        System.out.println(stack1.lastElement());
//    } //close method

   /*public void reduce2 (City[] cityList) {
      int n = cityList.length;
      int[] optimalStrategies = new int[n];
      for(int strategy: optimalStrategies){
         strategy = 0;
      }
      int optimal;
      for(int j = 0; j < n; j++){
         optimal = 0;
         for(int i = 0; i < n; i++){
            if((i != optimal) && (payoffMatrix[optimal][j] < payoffMatrix[i][j])){
               optimal = i;
            }
         }
         optimalStrategies[optimal]++;
      }
      for(int i = 0; i < n; i++){
         if(optimalStrategies[i] == 0){
            for(int j = 0; j < n; j++){
               payoffMatrix[i][j] = 0;
               payoffMatrix[j][i] = 0;
            }
         }  
      } 
   }*/
   
   public void localSearchSolve(City[] cityList){
      int n = this.vertex;
      Random random = new Random();
      int a = random.nextInt(n);
      int b = random.nextInt(n);
      double payoff1 = payoffMatrix[a][b];
      double payoff2 = payoffMatrix[b][a];
      boolean moved1, moved2;
      int new_a = a, new_b = b;
      int old_a = a, old_b = b;
      do{
         moved1 = false;
         moved2 = false;
         for(int i = 0; i < n; i++){ //P1 considers moving
            if((this.adjMatrix[a][i] == 1) && (payoff1 < payoffMatrix[i][b])){
               moved1 = true;
               new_a = i;
               payoff1 = payoffMatrix[i][b];
            }
         }
         a = new_a; //move is finalized at the place with highest utility
      
         for(int j = 0; j < n; j++){ //P2 considers moving
            if((this.adjMatrix[b][j] == 1) && (payoff2 < payoffMatrix[j][a])){
               moved2 = true;
               new_b = j;
               payoff2 = payoffMatrix[j][a];
            }
         }
         b = new_b;
      } while((moved1 || moved2) && ((a != old_a) || (b != old_b)));
      
      System.out.println("Player 1 initially chooses " + cityList[old_a].getName());
      System.out.println("Player 2 initially chooses " + cityList[old_b].getName());
      System.out.println("---------------------------------------------");
      System.out.println("Local Search Solution:");
      System.out.println("Player 1 chooses " + cityList[a].getName());
      System.out.println("Player 2 chooses " + cityList[b].getName());
      System.out.print("They get " + payoffMatrix[a][b] + " and " + payoffMatrix[b][a]);
      System.out.println(" customers respectively.");
      System.out.println("---------------------------------------------");
   
   }
   

   public void adjacency (City[] cityList, int threshold) {
      for (int i =0; i < cityList.length; i++) {
         for (int j = cityList.length -1 ; j >=0; j--){
            if (cityList[i].isAdjacent(cityList[j], threshold)){
            if(i == j){
            adjMatrix[i][j] = 0;
            } else {
               adjMatrix[i][j] = 1;
               adjMatrix[j][i] = 1;
               //adjMatrix[i][j] = cityList[i].distance(cityList[j]); //adds weighted edge
               //adjMatrix[j][i] = cityList[j].distance(cityList[i]); //adds weighted edge back (undirected graph)
               }
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

   public void printPayoffMatrix() {
      int displayThreshold = this.vertex;
      for (int i = 0; i < this.vertex; i++) {
         int lineBreakCounter = 0;
         for (int j = 0; j < this.vertex; j++) {
            System.out.print(payoffMatrix[i][j] + " ");
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
