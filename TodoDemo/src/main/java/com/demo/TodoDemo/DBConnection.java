package com.demo.TodoDemo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBConnection {
 private static Connection connection = null;

    public static Connection getConnection() {if (connection != null)
        return connection;
    else {
        try {
        	System.out.println("Entering to getConnection method");
         Properties prop = new Properties();
            InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("application.properties");
           // InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("D:/GST_WorkSpace_26_2_2018/spring-data-rest-angular/src/main/resources/application.properties");
            prop.load(inputStream);
            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Exiting to getConnection method");
        } catch (ClassNotFoundException e) {
        	System.out.println("ClassNotFoundException Exception in getConnection method");
            e.printStackTrace();
        } catch (SQLException e) {
        	System.out.println("SQLException Exception in getConnection method");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
        	System.out.println("FileNotFoundException Exception in getConnection method");
            e.printStackTrace();
        } catch (IOException e) {
        	System.out.println("IOException Exception in getConnection method");
            e.printStackTrace();
        }
        return connection;
    }
    
    }

}