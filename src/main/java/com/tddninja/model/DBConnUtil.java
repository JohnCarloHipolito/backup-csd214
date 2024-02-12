package com.tddninja.model;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBConnUtil {
    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            if (sessionFactory == null) {
                Configuration config = getConfiguration();
                sessionFactory = config.buildSessionFactory();
            }
            return sessionFactory;
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Configuration getConfiguration() {
        Configuration config = new Configuration();
        config.setProperty("hibernate.connection.driver_class", "org.mariadb.jdbc.Driver");
        config.setProperty("hibernate.connection.url", "jdbc:mariadb://localhost:3306/testdb");
        config.setProperty("hibernate.connection.username", "admin");
        config.setProperty("hibernate.connection.password", "admin");
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
        config.setProperty("logging.level.org.hibernate", "INFO");
        setAnnotatedClasses(config);
        return config;
    }

    private static void setAnnotatedClasses(Configuration config) {
        config.addAnnotatedClass(User.class);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}