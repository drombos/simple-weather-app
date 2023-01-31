public class City {
    private final String city;
    private final String state;
    private final String country;


    public City(String city, String state, String country) {
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }


    @Override
    public String toString() {
        return city+" ("+state+") "+country;
    }


    }



