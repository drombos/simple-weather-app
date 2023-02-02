package model;


import javax.persistence.*;

@Entity
@Table(name = "Location")
public class DbLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String city;
    private String state;
    private Integer location;
    private String accuweatherkey;



    public DbLocation(String country, String city, String state, Integer location, String accuweatherkey) {
        this.country = country;
        this.city = city;
        this.state = state;
        this.location = location;
        this.accuweatherkey = accuweatherkey;
    }





    private DbLocation(){

    }


    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
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

    public void setAccuweatherkey(String accuweatherkey) {
        this.accuweatherkey = accuweatherkey;
    }
    public String getAccuweatherkey() {
        return accuweatherkey;
    }
    public int getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "DbLocation{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", location=" + location +
                ", key=" + accuweatherkey +
                '}';
    }
}
