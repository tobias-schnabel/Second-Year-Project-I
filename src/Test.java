import java.util.Objects;

public class Test {

    final City[] cityList;
    final Location[] locationList;

     public Test(City[] cityList, Location[] locationList) {
        this.cityList = cityList;
        this.locationList = locationList;

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

    public void locationMethods () {
        System.out.println("\n**************************TEST**************************");
        for (Location loc : this.locationList) {
            System.out.println(loc.getName() + ", " + loc.getNumInhab() + " inhabitants, " + loc.customers + " customers.");
        }
        System.out.println("**************************END**************************\n");
    } //close method

    public void localSearch (int iterations, int threshold) {
         System.out.println("\n***************************************TEST***************************************");
         System.out.print("When solving the game using the local search method " + iterations + " times with a threshold of " + threshold);
         System.out.print(" km, we obtain the following results: \n");
         //instantiate graph and game
        Graph test_graph = new Graph(this.cityList.length);
        test_graph.adjacency(cityList, threshold); //fill adjacency matrix

        double[][]payoffMatrix = test_graph.payoffMatrix(cityList);

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
                System.out.println(cityList[i].getName() + " was chosen " + resultList[i] + " times.");
            }
        } //close for
        System.out.println("**********************************TEST END***************************************\n");
    } //close method

} //cose class
