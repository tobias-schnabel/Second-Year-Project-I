public class City {

    //declare immutable city attributes
    private final String name;
    private final int numInhab;
    private final double latCoord;
    private final double longCoord;

    //constructor
    public City(String cityName, int numberInhab, double latitude, double longitude) {
        this.name = cityName;
        this.numInhab = numberInhab;
        this.latCoord = latitude;
        this.longCoord = longitude;
    }


    public double distance (City that) {
        //method applies Haversine's formula to calculate as-the-crow-flies distance between cities
/*        a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
          c = 2 ⋅ atan2( √a, √(1−a) )
          d = R ⋅ c
        where	φ is latitude, λ is longitude, R is earth’s radius (mean radius = 6,371km);
        note that angles need to be in radians to pass to trig functions*/
        final double radius = 6371e3; //earth radius in m
        //convert to radians
        final double phi_one = this.getLatCoord() * Math.PI / 180;
        final double phi_two = that.getLatCoord() * Math.PI / 180;
        final double delta_phi = (this.latCoord - that.latCoord) * Math.PI / 180;
        final double delta_lambda = (this.longCoord - that.longCoord) * Math.PI / 180;

        final double a = Math.pow(Math.sin(delta_phi / 2), 2) + Math.pow(Math.sin(delta_lambda / 2), 2) * Math.cos(phi_one) * Math.cos(phi_two);
        final double c =  2 * Math.asin(Math.sqrt(a));
        return (radius * c) / 1000; // in km
    }

    public boolean isAdjacent (City that, int threshold) {
        return this.distance(that) <= threshold; //comparison in km
    }

    public void isAdjacentTest (City that, int threshold) {
        boolean retVal = this.distance(that) <= threshold; //comparison in km
        if (retVal) {
            System.out.println(this.name + " is adjacent to " + that.name + " for a threshold of " + threshold + "km, distance: " + this.distance(that));
        } else {
            System.out.println(this.name + " is not adjacent to " + that.name + " for a threshold of " + threshold + "km, distance: " + this.distance(that));
        }
    }

    public double  getRevenue (City that, int threshold, int budget) {
        double distance = this.distance(that);
        if (distance <= threshold) {
            double revenue = ((1-(distance / (double) threshold)) * budget); //first part of calculation is percentage of "max" distance, which multiplied with budget gives us "wilingness to pay"
            return revenue * that.getNumInhab();
        } else {
            return 0;
        }
    }

    //getters for attributes
    public String getName() {
        return name;
    }

    public int getNumInhab() {
        return numInhab;
    }

    public double getLatCoord() {
        return latCoord;
    }

    public double getLongCoord() {
        return longCoord;
    }


    @Override
    public String toString() {
        return "City{" +
                "name: '" + name + '\'' +
                ", number inhabitants: " + numInhab +
                ", latCoord=" + latCoord +
                ", longCoord=" + longCoord +
                '}';
    }

    public  int getTotalPop (City[] cityList) {
        int totalPop = 0;
        for (City city : cityList){
            totalPop += (double) city.getNumInhab();
        }
        return totalPop;
    }

    public Location[] clonelist (City[] cityList) {
        assert cityList != null;
        Location[] locationList = new Location[cityList.length];
        for (int i = 0; i < cityList.length; i++) {
            locationList[i] = new Location(cityList[i].getName(), cityList[i].getNumInhab(), cityList[i].getLatCoord(), cityList[i].getLongCoord(), 0);
        }
        return locationList;
    }
} //close class

