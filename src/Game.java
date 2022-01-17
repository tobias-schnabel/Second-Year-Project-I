import java.util.Random;

public class Game {

    private final City[] locations;
    private final double[][] payoffMatrix;
    private final double totalDemand;
    private int[] rationalStrategies;

    public Game(City[] cityList, double[][]payoffMatrix) {
        this.locations = cityList;
        int numberOfPlayers = 2;
        int n = cityList.length;
        this.rationalStrategies = new int[n];
        this.totalDemand = cityList[0].getTotalPop(this.locations);
        this.payoffMatrix = payoffMatrix;

    }

    public void solveIESDS() {
        int n = this.locations.length;
        for(int i = 0; i < n-1; i++){
            this.reduce();
        }
        System.out.println("********************************************************************");
        System.out.println("Solution of iterative elimination of strictly dominated strategies:");
        for(int i = 0; i < n; i++){
            if(this.rationalStrategies[i] > 0){
                for(int j = 0; j < n; j++){
                    if(this.rationalStrategies[j] > 0){
                        System.out.println("Player 1 chooses " + this.locations[i].getName() + ", Player 2 chooses " + this.locations[j].getName() + ".");
                        System.out.print("They get " + this.payoffMatrix[i][j] + " and ");
                        System.out.print(this.totalDemand - this.payoffMatrix[i][j]);
                        System.out.println(" customers respectively.");
                        System.out.println("********************************************************************");
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

    public double[][] passMatrix() {
        return this.payoffMatrix; //passes outcome matrix to print method
    }

    public void localSearchSolve(City[] cityList, Graph graph){
        int n = cityList.length;
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
                if((graph.adjMatrix[a][i] == 1) && (payoff1 < payoffMatrix[i][b])){
                    moved1 = true;
                    new_a = i;
                    payoff1 = payoffMatrix[i][b];
                }
            }
            a = new_a; //move is finalized at the place with highest utility

            for(int j = 0; j < n; j++){ //P2 considers moving
                if((graph.adjMatrix[b][j] == 1) && (payoff2 < payoffMatrix[j][a])){
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
} //close class
