package org.example.persistence;

import org.example.persistence.model.DbLocation;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HibernateRepository implements Dao {
    private static final String CREDENTIALS_PATH = "mysql";
    private SessionFactory sessionFactory;

    public HibernateRepository() {
        ResourceBundle credentialsFile = ResourceBundle.getBundle(CREDENTIALS_PATH);
        String url = credentialsFile.getString("url");
        String username = credentialsFile.getString("username");
        String password = credentialsFile.getString("password");

        initConnection(url, username, password);
    }


    //connect
    private void initConnection(String url, String username, String password) {
        sessionFactory = new Configuration()
                .configure()
                .setProperty("hibernate.connection.url", url)
                .setProperty("hibernate.connection.username", username)
                .setProperty("hibernate.connection.password", password)
                .addAnnotatedClass(DbLocation.class)
                .buildSessionFactory();
        System.out.println("init connection");
    }

    @Override
    public boolean create(DbLocation dbLocation) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) { // try with resources
            transaction = session.beginTransaction();
            session.save(dbLocation);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
        return true;
    }

//    public void update (int id, int location){
//        Session currentSession = sessionFactory.getCurrentSession();
//        Transaction transaction = currentSession.beginTransaction();
//        DbLocation dbLocation = currentSession.get(DbLocation.class,id);
//        dbLocation.setLocation(location);
//        transaction.commit();
//        currentSession.close();
//    }

    @Override
    public List<DbLocation> readAll() {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) { // try with resources
            transaction = session.beginTransaction();
            List<DbLocation> locations = session.createQuery("FROM Location", DbLocation.class).getResultList();
            transaction.commit();

            return locations;
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return new ArrayList<>();
    }

    public void closeAllResources() {
        sessionFactory.close();
    }

}
