package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    static final String URL = "jdbc:mysql://127.0.0.1:3306/payment_gateway";
    static final String USER = "root";
    static final String PASSWORD = "mum&dad010796";

    public static Connection getConnection() {

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}