package utils;

import java.sql.*;
import java.util.Properties;

/**
 * Created by alexe on 28.06.2016.
 */
public class ConnectionDB {

    private static Connection con;

    public static Connection getConnection(Properties conProp) {

        con = null;
        try {
            Class.forName(conProp.getProperty("driver"));
            con = DriverManager.getConnection(conProp.getProperty("url"), conProp.getProperty("user"), conProp.getProperty("password"));
        } catch (ClassNotFoundException cnfEx) {
            System.out.println(cnfEx);
        } catch (SQLException sqlEx) {
            System.out.println(sqlEx);
        }

        return con;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ignored) {
        }
    }
}
