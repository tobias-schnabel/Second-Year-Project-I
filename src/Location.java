public class Location extends City {
int customers;

    public Location(String cityName, int numberInhab, double latitude, double longitude, int customers) {
        super(cityName, numberInhab, latitude, longitude);
        this.customers = 0;
    }

    public Location[] clonelist (City[] cityList) {
        assert cityList != null;
        Location[] locationList = new Location[cityList.length];
        for (int i = 0; i < cityList.length; i++) {
            locationList[i] = new Location(cityList[i].getName(), cityList[i].getNumInhab(), cityList[i].getLatCoord(), cityList[i].getLongCoord(), 0);
        }
        return locationList;
    }
        public void setCustomers(int x) {
            this.customers = x;
        }

        public void addCustomers(int x) {
            this.customers += x;
        }

}
