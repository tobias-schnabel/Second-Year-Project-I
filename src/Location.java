public class Location extends City {
int customers;

    public Location(String cityName, int numberInhab, double latitude, double longitude, int customers) {
        super(cityName, numberInhab, latitude, longitude);
        this.customers = 0;
    }

    public void setCustomers(int x) {
        this.customers = x;
    }

    public void addCustomers(int x) {
        this.customers += x;
    }


}
