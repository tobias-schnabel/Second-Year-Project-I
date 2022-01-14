import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        City[] cityList = null;

        String filename = "personList.txt";
        try{
            cityList = importList(filename);
            System.out.println("Import successful.");
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static City[] importList(String filename)

        throws java.io.FileNotFoundException{
        File file = new File(filename);
        Scanner input = new Scanner (file);
        int n = input.nextInt();

        City[] list = new City[n];

        for(int i=0; i<n; i++){
            String cityName = input.next();
            int numberInhab = input.nextInt();
            double latitude = input.nextDouble();
            double longitude = input.nextDouble();

            list[i] = new City(cityName, numberInhab, latitude, longitude);
        }

        input.close();

        return list;
    }
}

/* needs methods for
1. Data import #done
2. instantiating Cities as objects using City class #done
3. iterative elimination method
4. local search method

 */