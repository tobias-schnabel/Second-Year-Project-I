import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        City[] cityList = null;

        String filename = "InputExample.txt";
        try{
            cityList = importList(filename);
            System.out.println("Import successful.");
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        //initial testing of I/O, city methods
        System.out.println(Objects.requireNonNull(cityList)[0]);
        System.out.println(Objects.requireNonNull(cityList[1]));
        System.out.println(cityList[0].distance(cityList[1]));
        cityList[0].isAdjacentTest(cityList[1], 25);
        cityList[0].isAdjacentTest(cityList[1], 254);

        for (int i = 0; i < cityList.length; i++){
            for (int j = 0; j < cityList.length; j++){
                System.out.println(cityList[i].getName()+ " to " + cityList[j].getName() + ": " + cityList[i].distance(cityList[j]));
            }
        }
        System.out.println();

        //initial testing of graph methods
        Graph graph = new Graph(cityList.length);
        int maxDist = graph.initialize(cityList);

        graph.adjacency(cityList, maxDist);
        //graph.printDistMatrix();
        graph.payoffMatrix(cityList);
        graph.printPayoffMatrix();
        StaticGame game = new StaticGame(cityList);
        game.printPayoffMatrix();
        game.solve();
        game.printPayoffMatrix();

        Location[] locationList = new Location[cityList.length];
        for (int i = 0; i < cityList.length; i++) {
            locationList[i] = new Location(cityList[i].getName(), cityList[i].getNumInhab(), cityList[i].getLatCoord(), cityList[i].getLongCoord(), 0);
        }
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
