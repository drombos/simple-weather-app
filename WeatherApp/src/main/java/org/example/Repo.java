package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Repo {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static EntityManager em = factory.createEntityManager();


    public void createNewParam(String city, String state, String country)  {
        System.out.println("=========== create ============");
        WeatherParam newOne = new WeatherParam(city,state,country);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        System.out.println("======== persisting in new transacion =============");
        em.persist(newOne);
        System.out.println(" new  ID " + newOne.getId());
        System.out.println("======== closing transacion =============");
        transaction.commit(); // zapis do bazy danych
    }

    public WeatherParam findById(long id) {
        System.out.println("======== FIND BY ID ===========");
        return em.find(WeatherParam.class, id);
    }

    public void updateCity(WeatherParam param, String newCity) {
        EntityTransaction  transaction = em.getTransaction();
        System.out.println("======== UPDATE =========");
        transaction.begin();
        param.setCity(newCity);
        transaction.commit();

    }

    public void delete(WeatherParam param) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(param);
        transaction.commit();
    }
}
