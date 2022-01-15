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
        System.out.println(cityList[0].isAdjacent(cityList[1], 25));
        System.out.println(cityList[0].isAdjacent(cityList[1], 15));

        //initial testing of graph methods
        Graph graph = new Graph(cityList.length);
        graph.populateGraph(cityList, 5);
        System.out.println(graph);
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



/* needs methods for
1. Data import #done
2. instantiating Cities as objects using City class #done
3. iterative elimination method
4. local search method

 */