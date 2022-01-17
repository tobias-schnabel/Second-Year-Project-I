import java.util.*;

public class StaticGame {
   private final City[] locations;
   private final double[][] payoffMatrix;
   private final double totalDemand;
   private int[] rationalStrategies;

   public StaticGame(City[] cityList) {
      this.locations = cityList;
      int numberOfPlayers = 2;
      int n = cityList.length;
      this.rationalStrategies = new int[n];
      double totalPop = 0;
      //get total population
      for (City city : cityList){
         totalPop += city.getNumInhab();
      }
      this.totalDemand = totalPop;
      //initialize payoff matrix
      this.payoffMatrix = new double[n][n];
      //Choice[][] payoffs = new Choice[n][n];

      for(int i = 0; i < n; i++) {
         for(int j = 0; j < n; j++) {
            if(i == j){
               this.payoffMatrix[i][j] +=  this.totalDemand / 2;
            } else {
               for(int k = 0; k < n; k++){
                  if(cityList[i].distance(cityList[k]) < cityList[j].distance(cityList[k])){
                     this.payoffMatrix[i][j] += cityList[k].getNumInhab(); //if k closer to i, i gets population
                  }
               } 
            } 
         } 
      }

   } 


   
   public void solve() {
   int n = this.locations.length;
   for(int i = 0; i < n-1; i++){
   this.reduce();
   }
   System.out.println("Solution:");
   for(int i = 0; i < n; i++){
      if(this.rationalStrategies[i] > 0){
         for(int j = 0; j < n; j++){
            if(this.rationalStrategies[j] > 0){
               System.out.println("Player 1 chooses " + this.locations[i].getName());
               System.out.println("Player 2 chooses " + this.locations[j].getName());
               System.out.print("They get " + this.payoffMatrix[i][j] + " and ");
               System.out.print(this.totalDemand - this.payoffMatrix[i][j]);
               System.out.println(" customers respectively.");
               System.out.println("---------------------------------------------");
            }
         } //close for
      }
   }
   } //close method
   
   public void reduce() {
      int n = this.locations.length;
      int[] strategies = new int[n];

      int p;
      double optimal;
      for(int j = 0; j < n; j++){
         p = 0;
         optimal = this.payoffMatrix[p][j];

         while(optimal == 0 && p < n){
         optimal = this.payoffMatrix[p][j];
         p++;
         } //close while

         if(optimal == 0 && p == n){
            continue;
         }
         for(int i = 0; i < n; i++){
            if(optimal < this.payoffMatrix[i][j]){
               optimal = this.payoffMatrix[i][j];
            }
         }
         for(int k = 0; k < n; k++){
            if(this.payoffMatrix[k][j] == optimal){
               strategies[k]++;
            }
         }
         } //close outer for
      
      for(int i = 0; i < n; i++){
         if(strategies[i] == 0){
            for(int j = 0; j < n; j++){
               this.payoffMatrix[i][j] = 0;
               this.payoffMatrix[j][i] = 0;
            }
         }
       }
       this.rationalStrategies = strategies;  
      } //close method

   public void printPayoffMatrix() {
      int displayThreshold = this.locations.length;
      for (int i = 0; i < this.locations.length; i++) {
         int lineBreakCounter = 0;
         for (int j = 0; j < this.locations.length; j++) {
            System.out.print(payoffMatrix[i][j] + " ");
            lineBreakCounter ++;
            if (lineBreakCounter == displayThreshold) {
               System.out.print("\n");
               lineBreakCounter = 0;
            }
         }
      }
      System.out.println("");
   } //close method


} //close class

/*class Choice{
   private final String city;
   private final double payoff;

   public Choice(){
      this.city = "";
      this.payoff = 0;
   }
   
   public void setCity(City city){
      this.city = city.getName;
   }
   
   public void setPayoff(double payoff){
      this.payoff = payoff;
   }
}*/