package org.example.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "location")
public class DbLocation implements DbObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "country")
    private String country;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "accuweather_key", unique = true)
    private String accuweatherKey;

    public DbLocation(String city, String state, String country, Double longitude, Double latitude,
                      String accuweatherKey) {
        if (accuweatherKey == null || accuweatherKey.isBlank()) {
            throw new IllegalArgumentException("Brakuje klucza ApiWeather, nie udało się stworzyć obiektu DbLocation " +
                    "dla danych: " + city + " " + state + " " + country + " " + longitude + " " + latitude);
        }

        this.city = city;
        this.state = state;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
        this.accuweatherKey = accuweatherKey;
    }

    private DbLocation() { }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAccuweatherKey() {
        return accuweatherKey;
    }

    public void setAccuweatherKey(String accuweatherKey) {
        this.accuweatherKey = accuweatherKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DbLocation that = (DbLocation) o;

        return accuweatherKey.equals(that.accuweatherKey);
    }

    @Override
    public int hashCode() {
        return accuweatherKey.hashCode();
    }

    @Override
    public String toString() {
        return city + " " + state + " " + country;
    }
}