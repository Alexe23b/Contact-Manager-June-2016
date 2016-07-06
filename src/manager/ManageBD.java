package manager;

import utils.ConnectionDB;
import utils.FileNameFilter;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static utils.ChoiceDB.choiceBD;

/**
 * Created by alexe on 28.06.2016.
 */
public class ManageBD {
    private static ArrayList<String> users = new ArrayList<>();

    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;

    public static void checkUser(String userName, int typeBD) {
        String nameBD = userName + "AddressBook";
        String query;
        try {

            conn = ConnectionDB.getConnection(choiceBD(typeBD));
            stmt = conn.createStatement();


            query = "CREATE DATABASE IF NOT EXISTS users";
            stmt.executeUpdate(query);

            query = "CREATE TABLE IF NOT EXISTS users.user("
                    + "user_id INT NOT NULL AUTO_INCREMENT,"
                    + "user_name VARCHAR(45) NOT NULL,"
                    + "user_password VARCHAR(45),"
                    + "PRIMARY KEY (user_id)"
                    + ")";
            stmt.execute(query);

            query = "SELECT user_name FROM users.user";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                users.add(rs.getString(1));
            }
            boolean check = true;
            for (String user : users) {
                if (user.equals(userName)) {
                    System.out.println("User with nicName " + userName + " was found.");
                    System.out.println("-------------------------------------");
                    check = false;
                    break;
                }
            }
            if (check == true) {
                query = "INSERT INTO users.user (`user_name`) VALUES ('" + userName + "');";
                stmt.execute(query);

                System.out.println("User with nicName " + userName + " was added.");
                System.out.println("-------------------------------------");
            }
            Path userPath = FileSystems.getDefault().getPath("files/" + userName);
            Files.deleteIfExists(userPath);
            Files.createDirectory(userPath);

            File[] listFiles = FileNameFilter.findFiles("files", "json");

            for (File f : listFiles) {

                Path sourcePath = FileSystems.getDefault().getPath("files", f.getName());
                Path goalPath = FileSystems.getDefault().getPath("files/" + userName, f.getName());

                Files.deleteIfExists(goalPath);

                Files.copy(sourcePath, goalPath, REPLACE_EXISTING);
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

            query = "CREATE TABLE IF NOT EXISTS " + nameBD + ".contacts("
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
                    + "FOREIGN KEY (user_id) REFERENCES " + nameBD + ".contacts (user_id)"
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
                    + "FOREIGN KEY (user_id) REFERENCES " + nameBD + ".contacts(user_id)"
                    + "ON DELETE CASCADE ON UPDATE CASCADE,"
                    + "FOREIGN KEY (phone_number_id) REFERENCES " + nameBD + ".phones(phone_number_id)"
                    + "ON DELETE CASCADE ON UPDATE CASCADE"
                    + ")";
            stmt.execute(query);

            System.out.println("Database for User " + userName + " was added.");
            System.out.println("-------------------------------------");
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
