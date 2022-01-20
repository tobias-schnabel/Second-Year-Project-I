/* File: Game.java
 * Authors: Emil Dotchev & Tobias Schnabel
 * Student ids: i6244005 & i6255807 (respectively)
 *   
 * A class that creates a game of competing firms looking for an optimal location to start their business.
 * Implements iterative elimination of strictly dominated strategies and local search methods for solving 
 * the problem.
 *
 */

import java.util.Random;

public class Game {

    private final City[] locations;
    private final double[][] payoffMatrix;
    private final double totalDemand;
    private int[] rationalStrategies;
    private double[][] adjMatrix;

    public int[] getResult() {
        return result;
    }

    private int[] result;

    public Game(City[] cityList, double[][]payoffMatrix) {
        this.locations = cityList;
        int numberOfPlayers = 2;
        int n = cityList.length;
        this.rationalStrategies = new int[n];
        this.totalDemand = cityList[0].getTotalPop(this.locations);
        this.payoffMatrix = payoffMatrix;
        this.result = new int[]{0, 0};

    }
    public void setAdjMatrix(Graph graph) {
        this.adjMatrix = graph.adjMatrix;
    }

    public City[] getLocations() {
        return locations;
    }

   //Implements iterative elimination of strictly dominated strategies and prints out solution
    public void solveIESDS() {
        int n = this.locations.length;
        for(int i = 0; i < n-1; i++){ //we need at most n-1 eliminations (at least one strategy remains)
            this.reduce();
        }
        System.out.println("********************************************************************");
        System.out.println("Solution of iterative elimination of strictly dominated strategies:");
        for(int i = 0; i < n; i++){
            if(this.rationalStrategies[i] > 0){
                for(int j = 0; j < n; j++){
                    if(this.rationalStrategies[j] > 0){
                        System.out.println("Player 1 chooses " + this.locations[i].getName() + ", Player 2 chooses " + this.locations[j].getName() + ".");
                        System.out.print("They get " + this.payoffMatrix[i][j] + " and " + this.payoffMatrix[j][i]);
                        System.out.println(" customers respectively.");
                        System.out.println("********************************************************************");
                    }
                } //close for
            }
        }
    } //close method

   //Method implements one round of elimination of strictly dominated strategies
    public void reduce() {
        int n = this.locations.length;
        //we create an array to keep track of optimality of strategies
        int[] strategies = new int[n]; 

        int p;
        double optimal; //double keeps track of the optimal (highest) value in a column
        for(int j = 0; j < n; j++){ //we go through all columns (strategies of P2)
            p = 0;
            optimal = this.payoffMatrix[p][j]; //we initialize it as the first value

            //we skip already nullified entries, because they can't be optimal
            while(optimal == 0 && p < n){ 
                optimal = this.payoffMatrix[p][j];
                p++;
            } //close while
            
            //if the whole column is nullified, continue to the next column
            if(optimal == 0 && p == n){
                continue;
            }
            for(int i = 0; i < n; i++){
                //if we find a higher value in the column, we record it as optimal
                if(optimal < this.payoffMatrix[i][j]){ 
                    optimal = this.payoffMatrix[i][j];
                }
            }
            //all the rows which have the optimal value at the end are optimal strategies given P2 plays j
            for(int k = 0; k < n; k++){
                if(this.payoffMatrix[k][j] == optimal){
                    strategies[k]++; //we keep track of which P1 strategies are optimal
                }
            }
        } //close outer for

        for(int i = 0; i < n; i++){ 
            //if a strategy was never optimal, it is an irrational choice and thus strictly dominated (Pearce's Lemma)
            if(strategies[i] == 0){
                for(int j = 0; j < n; j++){
                    this.payoffMatrix[i][j] = 0; //we nullify the row (strategy of P1)
                    this.payoffMatrix[j][i] = 0; //and also the column (P2's strategy) since the problem is symmetric
                }
            }
        }
        this.rationalStrategies = strategies;
    } //close method

    public double[][] passMatrix() {
        return this.payoffMatrix; //passes outcome matrix to print method
    }

    //Implements local search algorithm and prints out solution
    public void solveLocalSearch(Graph graph){
        this.setAdjMatrix(graph);
        int n = this.locations.length;
        Random random = new Random();
        //each player gets assigned a random location
        int a = random.nextInt(n);
        int b = random.nextInt(n);
        //helping variables
        double payoff1, payoff2;
        boolean moved1, moved2;
        int new_a = a, new_b = b;
        int old_a = a, old_b = b;
        int counter = 0;

        do{
            //at the start of each round no one has moved
            moved1 = false; 
            moved2 = false;
            payoff1 = payoffMatrix[a][b]; //updates payoff1
            for(int i = 0; i < n; i++){ //P1 considers moving
                if((this.adjMatrix[a][i] > 0) && (payoff1 < payoffMatrix[i][b])){
                    //if he finds a better payoff in an adjacent city, he moves
                    moved1 = true;
                    //choice is recorded and payoff is updated 
                    new_a = i;                   
                    payoff1 = payoffMatrix[i][b]; 
                } //loop continues since we need the best improvement
            }
            a = new_a; //move is finalized at the place with highest utility
            payoff2 = payoffMatrix[b][a]; //updates payoff2

            for(int j = 0; j < n; j++){ //P2 considers moving
                if((graph.adjMatrix[b][j] > 0) && (payoff2 < payoffMatrix[j][a])){
                    moved2 = true;
                    new_b = j;
                    payoff2 = payoffMatrix[j][a];
                }
            }
            b = new_b;
            counter++; //counter prevents an infinite loop
            //loop continues until no one has incentive to improve (and thus doesn't move) or the counter clocks out
        } while((moved1 || moved2) && (counter < n*n) );

        System.out.println("********************************************************************");
        System.out.println("Local Search:");
        System.out.println("Player 1 initially chooses " + this.locations[old_a].getName());
        System.out.println("Player 2 initially chooses " + this.locations[old_b].getName());
        System.out.println("---------------------------------------------");
        System.out.println("Local Search Solution:");
        System.out.println("Player 1 chooses " + this.locations[a].getName());
        System.out.println("Player 2 chooses " + this.locations[b].getName());
        System.out.println("---------------------------------------------");
        System.out.print("They get " + payoffMatrix[a][b] + " and " + payoffMatrix[b][a]);
        System.out.println(" customers respectively.");
        System.out.println("********************************************************************");
        
        //keep track of results
        this.result[0] = a;
        this.result[1] = b;
    } //close method

    //Implements local search algorithm without printing out solution
    public void solveLocalSearchQuietly(Graph graph) {
        this.setAdjMatrix(graph);
        int n = this.locations.length;
        Random random = new Random();
        //declare new vars
        int a = random.nextInt(n);
        int b = random.nextInt(n);
        double payoff1, payoff2;
        boolean moved1, moved2;
        int new_a = a, new_b = b;
        int old_a = a, old_b = b;
        int counter = 0;

        do {
            moved1 = false;
            moved2 = false;
            payoff1 = payoffMatrix[a][b]; //updates payoff1
            for (int i = 0; i < n; i++) { //P1 considers moving
                if ((this.adjMatrix[a][i] > 0) && (payoff1 < payoffMatrix[i][b])) {
                    moved1 = true;
                    new_a = i;
                    payoff1 = payoffMatrix[i][b];
                }
            }
            a = new_a; //move is finalized at the place with highest utility
            payoff2 = payoffMatrix[b][a]; //updates payoff2
            
            for (int j = 0; j < n; j++) { //P2 considers moving
                if ((graph.adjMatrix[b][j] > 0) && (payoff2 < payoffMatrix[j][a])) {
                    moved2 = true;
                    new_b = j;
                    payoff2 = payoffMatrix[j][a];
                }
            }
            b = new_b;
            counter++;
        } while ((moved1 || moved2) && (counter < n*n));
        this.result[0] = a;
        this.result[1] = b;
    }
} //close class
