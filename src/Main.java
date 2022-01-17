import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        City[] cityList = null;

        String filename = "src/InputExample.txt";
        try{
            cityList = importList(filename);
            System.out.println("Import successful.\n");
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

       //i created a new class to centralize all the testing of everything
        assert cityList != null;
        Location[] locationList = new Location[cityList.length];
        for (int i = 0; i < cityList.length; i++) {
            locationList[i] = new Location(cityList[i].getName(), cityList[i].getNumInhab(), cityList[i].getLatCoord(), cityList[i].getLongCoord(), 0);
        }
        Test test = new Test(cityList, locationList);
        test.cityMethods((int) cityList[0].distance(cityList[1]) + 1);


        //set up graph
        Graph graph = new Graph(cityList.length);
        int maxDist = graph.initialize(cityList); //fill distance matrix
        graph.adjacency_binary(cityList, maxDist); //fill adjacency_binary matrix


        //construct payoff matrix
        double[][]payoffMatrix = graph.payoffMatrix(cityList);
        //use payoff matrix to construct game
        Game game = new Game(cityList, payoffMatrix);
        //solveIESDS game
        game.solveIESDS();
        //print outcome
        graph.setOutcomeMatrix(game.passMatrix());
        graph.printMatrix("outcome");



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
