package org.example;




import javax.persistence.*;

@Entity
public class WeatherParam {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;




    private String city;

    private String state;

    private String country;

    public WeatherParam(String city, String state, String country) {

        this.city = city;
        this.state = state;
        this.country = country;
    }

    public WeatherParam() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public long getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

