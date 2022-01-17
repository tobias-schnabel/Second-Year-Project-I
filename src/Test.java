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
    }
}
