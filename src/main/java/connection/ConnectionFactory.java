/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Vinicius Augusto
 */
public class ConnectionFactory {

    public Connection getConnection() {
        try {

            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "db123");
            properties.setProperty("useTimezone", "true");
            properties.setProperty("serverTimezone", "UTC");

            String con = "jdbc:mysql://localhost:3306/franquia_medica";
    
            return DriverManager.getConnection(con, properties);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
