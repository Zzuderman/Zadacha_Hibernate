package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final SessionFactory factory = new Util().getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction t = null;

        try (Session session = factory.getCurrentSession())
         {
            t = session.beginTransaction();

            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (\n" +
                    "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "    name VARCHAR(100),\n" +
                    "    lastName VARCHAR(100),\n" +
                    "    age TINYINT" +
                    ")").addEntity(User.class).executeUpdate();
            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        }

    }

    @Override
    public void dropUsersTable() {

        Transaction t = null;
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users  ").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction t = null;
        try (Session session = factory.getCurrentSession()) {

            User user = new User(name,lastName,age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {

        Transaction t = null;
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class,id));
            session.getTransaction().commit();
        }catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {

        List <User> userList = new ArrayList<>();
        Transaction t = null;

        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            userList = session.createQuery("from User").getResultList();

            for (User e: userList)
                System.out.println(e);

            session.getTransaction().commit();

            }catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        }

        return userList;
    }

    @Override
    public void cleanUsersTable() {

        Transaction t = null;
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        }
    }
}
