package org.example;

import repository.HibernateRepository;
import model.DbLocation;

public class DemoApp {
    public static void main(String[] args) {
        HibernateRepository repository = new HibernateRepository();
        DbLocation dbLocation = new DbLocation("polska","krakow","malopo", 42334524,"25425");
        repository.save(dbLocation);
       // repository.update(0,2021);
        //System.out.println(repository.findAll());

    }
}
