package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import jm.task.core.jdbc.model.User;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Configuration;

//import java.lang.module.Configuration;
import javax.imageio.spi.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver"; //для JDBC
    private static final String URL = "jdbc:mysql://localhost:3306/clone_zadacha";//для JDBC
    private static final String USERNAME = "root";//для JDBC
    private static final String PASSWORD = "Ctrnevctvghf11";//для JDBC

    private static SessionFactory sessionFactory;//для Hibernate


    public static Connection getConnection() { //для JDBC
        Connection connection = null;

        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("Connection OK");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return  connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/clone_zadacha?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "Ctrnevctvghf11");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "");//Меняем propertiesSetting.put(Environment.HBM2DDL_AUTO, ""); теперь таблица не будет удаляться и создаваться автоматически

                configuration.setProperties(settings);

//                configuration.addAnnotatedClass(User.class);

                sessionFactory = configuration.addAnnotatedClass(User.class).buildSessionFactory();


                System.out.println("Connection OK");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Connection ERROR");
            }
        }
        return sessionFactory;
    }

}


