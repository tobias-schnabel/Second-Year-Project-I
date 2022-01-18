import java.util.Objects;

public class Test {

    final City[] cityList;


     public Test(City[] cityList) {
        this.cityList = cityList;
     }



    public void cityMethods (int threshold) {
        //initial testing of I/O, city methods
        System.out.println("\n***************************************TEST***************************************");
        System.out.println(Objects.requireNonNull(cityList)[0]);
        System.out.println(Objects.requireNonNull(cityList[1]));
        cityList[0].isAdjacentTest(cityList[1], threshold);
        cityList[0].isAdjacentTest(cityList[1], (threshold / 2));
        System.out.println("**********************************TEST END***************************************\n");

    }
    public void solveIESDS (int threshold) {
        System.out.println("\n***************************************TEST***************************************");
        System.out.println("For a threshold of " + threshold + " km: ");
        Graph threshold_test_graph = new Graph(this.cityList.length);
        threshold_test_graph.adjacency(cityList, threshold);
        double[][] payoffMatrix = threshold_test_graph.payoffMatrixCustom(cityList, threshold);

        Game threshold_test_game = new Game(cityList, payoffMatrix);
        threshold_test_game.solveIESDS();
        //print outcome
        threshold_test_graph.setOutcomeMatrix(threshold_test_game.passMatrix());
        threshold_test_graph.printMatrix("outcome");

        System.out.println("**********************************TEST END***************************************\n");
    }

    public void localSearch (int iterations, int threshold) {

         //instantiate graph and game
        Graph test_graph = new Graph(this.cityList.length);
        test_graph.adjacency(cityList, threshold); //fill adjacency matrix
        int maxDist = test_graph.initialize(cityList);

        double[][]payoffMatrix = test_graph.payoffMatrix(cityList);

        System.out.println("\n***************************************TEST***************************************");
        System.out.println("The maximal distance between any two cities in the list is " + maxDist + " km.");
        System.out.print("When solving the game using the local search method " + iterations + " times with a threshold of " + threshold);
        System.out.print(" km, we obtain the following results: \n");

        Game test_game = new Game(cityList, payoffMatrix);
        int[] resultList = new int[cityList.length];

        for (int i = 0; i <= iterations; i++) {
            test_game.solveLocalSearchQuietly(test_graph);

            int[] results = test_game.getResult();
            resultList[results[0]] += 1;
            resultList[results[1]] += 1;
        } //close for

        for (int i = 0; i < cityList.length; i++) {
            if (resultList[i] > 0) {
                System.out.println(cityList[i].getName() + " was chosen " + resultList[i] + "/" + iterations + " times.");
            }
        } //close for
        System.out.println("**********************************TEST END***************************************\n");
    } //close method

    public void compareLocalSearch (int iterations, int maxDistance) {

        this.localSearch(iterations, maxDistance);
        this.localSearch(iterations, maxDistance / 2);
        this.localSearch(iterations, maxDistance / 4);
    }
} //cose class
