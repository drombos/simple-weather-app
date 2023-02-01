package main.java.org.example;

public class CityQuery {
    private String city;
    private String state;
    private String country;

    public CityQuery(String city, String state, String country) {
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getCountry() {
        return this.country;
    }

    @Override
    public String toString() {
        return city+" ("+state+") "+country;
    }





}
