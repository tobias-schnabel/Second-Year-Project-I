import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        City[] cityList = null;

        String filename = "InputExample.txt";
        try{
            cityList = importList(filename);
            System.out.println("Import successful.\n");
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

       //test
        Test test = new Test(cityList);
        assert cityList != null;
        test.cityMethods((int) cityList[0].distance(cityList[1]) + 1);


        //set up graph
        Graph graph = new Graph(cityList.length);
        int maxDist = graph.initialize(cityList); //fill distance matrix
        graph.adjacency(cityList, maxDist); //fill adjacency matrix


        //construct payoff matrix
        double[][]payoffMatrix = graph.payoffMatrix(cityList);
        //use payoff matrix to construct game
        Game game = new Game(cityList, payoffMatrix);
        //solveIESDS game
        game.solveIESDS();
        //print outcome
        graph.setOutcomeMatrix(game.passMatrix());
        graph.printMatrix("outcome");

        //Test with custom threshold
        Scanner in = new Scanner(System.in);
        Test threshold_test = new Test(cityList);
        System.out.println("Please enter an integer number as a threshold for the adjacency matrix: ");
        int threshold = in.nextInt();
        in.close();

        threshold_test.solveIESDS(threshold);
        threshold_test.localSearch(100, threshold);

        Test gameTest = new Test(cityList);
        gameTest.compareLocalSearch(100, maxDist);

    } //close main

    public static City[] importList(String filename)

        throws java.io.FileNotFoundException{
        File file = new File(filename);
        Scanner input = new Scanner (file);
        int n = input.nextInt();

        City[] list = new City[n];

        for(int i=0; i<n; i++){
            String cityName = input.next();
            int numberInhab = input.nextInt();
            double latitude = cleanData(input.next());
            double longitude = cleanData(input.next());

            list[i] = new City(cityName, numberInhab, latitude, longitude);
        }

        input.close();

        return list;
    }

    public static double cleanData(String rawData) { //replaceAll("[.\\s']", "").replaceAll(",", ".").
        String cleanstring = rawData.replaceAll("°",  "").replaceAll("′", "");
        return Double.parseDouble(cleanstring);
    }
}
