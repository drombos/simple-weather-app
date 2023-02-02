package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Repo repo = new Repo();
        //create
        repo.createNewParam("kraków", "Malopolskie", "Polska");
        //read
        WeatherParam param = repo.findById(1L);
        System.out.println(param.getId());
        //update
        repo.updateCity(param, "kraków");
        //delete
        repo.delete(param);
    }
}