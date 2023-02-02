package repository;

import model.DbLocation;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class HibernateRepository {

    private SessionFactory sessionFactory;

    public HibernateRepository(){
        initConnection();
    }


    //connect
    private void initConnection(){
        sessionFactory = new Configuration()
                .configure()
                .addAnnotatedClass(DbLocation.class)
                .buildSessionFactory();
        System.out.println("init connection");
    }
    public void initTable() {
    }

    public void save(DbLocation dbLocation)  {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()){ // try with resources
            transaction = session.beginTransaction();
            session.save(dbLocation);
            transaction.commit();
        }catch (HibernateException e){
            e.printStackTrace();
            if(transaction !=null){
               transaction.rollback();
            }
        }
        //   session.close();
    }

    public void update (int id, int location){
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        DbLocation dbLocation = currentSession.get(DbLocation.class,id);
        dbLocation.setLocation(location);
        transaction.commit();
        currentSession.close();
    }
    public List<DbLocation> findAll()  {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()){ // try with resources
            transaction = session.beginTransaction();
            List<DbLocation> location = session.createQuery("FROM Location", DbLocation.class).getResultList();
            transaction.commit();
            return location;
        }catch (HibernateException e){
            e.printStackTrace();
            if(transaction !=null){
                transaction.rollback();
            }
        }
        return new ArrayList<>();
    }

    public void closeAllResources() {

        //   session.close();

    }

}