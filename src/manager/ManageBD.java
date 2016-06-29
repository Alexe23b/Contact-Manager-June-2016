package manager;

import utils.ConnectionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static utils.ChoiceDB.choiceBD;

/**
 * Created by alexe on 28.06.2016.
 */
public class ManageBD {
    private static ArrayList<String> users = new ArrayList<>();

    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;
    private static boolean check;
    private static String nameBD;

    public static void checkUser(String userName, int typeBD) {
        nameBD = userName + "AddressBook";
        String query = "SELECT userName FROM users.user";
        try {
            conn = ConnectionDB.getConnection(choiceBD(typeBD));
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                users.add(rs.getString(1));
            }
            check = true;
            for (String user : users) {
                if (user.equals(userName)) {
                    System.out.println("User with nicName " + userName + " was found.");
                    check = false;
                    break;
                }
            }
            if (check == true) {
                query = "INSERT INTO users.user (`userName`) VALUES ('" + userName + "');";
                stmt.execute(query);
                System.out.println("User with nicName " + userName + " was added.");
            }
            query = "CREATE DATABASE IF NOT EXISTS " + nameBD;
            stmt.execute(query);

            query = "CREATE TABLE IF NOT EXISTS " + nameBD + ".address ("
                    + "address_id INT NOT NULL AUTO_INCREMENT,"
                    + "country VARCHAR(45) NOT NULL,"
                    + "city VARCHAR(45) NOT NULL,"
                    + "street VARCHAR(45) NOT NULL,"
                    + "house_number INT NOT NULL,"
                    + "house_suffix VARCHAR(45) NULL,"
                    + "appartment INT NULL,"
                    + "post_code INT NOT NULL,"
                    + "PRIMARY KEY (address_id),"
                    + "UNIQUE INDEX address_id_UNIQUE (address_id ASC)"
                    + ")";
            stmt.execute(query);

            query = "CREATE TABLE IF NOT EXISTS " + nameBD + ".contact("
                    + "user_id INT NOT NULL AUTO_INCREMENT,"
                    + "user_firstName VARCHAR(30) NOT NULL,"
                    + "user_lastName VARCHAR(20) NOT NULL,"
                    + "birthday DATE NOT NULL,"
                    + "address_id INTEGER,"
                    + "PRIMARY KEY (user_id),"
                    + "FOREIGN KEY (address_id) REFERENCES " + nameBD + ".address(address_id)"
                    + "ON DELETE SET NULL"
                    + ")";
            stmt.execute(query);


            query = "CREATE TABLE IF NOT EXISTS " + nameBD + ".emails("
                    + "email_id INTEGER NOT NULL AUTO_INCREMENT,"
                    + "email VARCHAR(100) NOT NULL,"
                    + "user_id INTEGER NOT NULL,"
                    + "PRIMARY KEY (email_id),"
                    + "FOREIGN KEY (user_id) REFERENCES " + nameBD + ".contact (user_id)"
                    + "ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")";
            stmt.execute(query);

            query = "CREATE TABLE IF NOT EXISTS " + nameBD + ".phones("
                    + "phone_number_id INTEGER NOT NULL AUTO_INCREMENT,"
                    + "phone_number VARCHAR (20) NOT NULL,"
                    + "PRIMARY KEY (phone_number_id)"
                    + ")";
            stmt.execute(query);

            query = "CREATE TABLE IF NOT EXISTS " + nameBD + ".contact_phone("
                    + "user_id INTEGER NOT NULL,"
                    + "phone_number_id INTEGER NOT NULL,"
                    + "PRIMARY KEY (user_id, phone_number_id),"
                    + "FOREIGN KEY (user_id) REFERENCES " + nameBD + ".contact(user_id)"
                    + "ON DELETE CASCADE ON UPDATE CASCADE,"
                    + "FOREIGN KEY (phone_number_id) REFERENCES " + nameBD + ".phones(phone_number_id)"
                    + "ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")";
            stmt.execute(query);

            System.out.println("Database for User " + userName + " was added.");
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }
    }
}