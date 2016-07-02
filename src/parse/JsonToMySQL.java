package parse;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import utils.ChoiceDB;

import java.awt.*;
import java.beans.Statement;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static utils.ChoiceDB.choiceBD;
import static utils.ConnectionDB.getConnection;

/**
 * Created by alexe on 10.06.2016.
 */
public class JsonToMySQL {
    private static Gson gson = new Gson();

    public static void parseJson(String path, int typeBD, String nameBD) throws IOException {

        List<Contact> contacts = readJsonStream(new FileInputStream(path));
        PreparedStatement prepStat;

        try {
            Connection con = getConnection(choiceBD(typeBD)); // 1 typeBD - MySQL

            for (Contact contact : contacts) {
                Address address = contact.getAddress();

                String address_record = "INSERT INTO "+ nameBD +".address (`country`, `city`, `street`, `house_number`, `house_suffix`, `appartment`, `post_code`) VALUES"
                        + "(?, ?, ?, ?, ?, ?, ?);";

                prepStat = con.prepareStatement(address_record);

                prepStat.setString(1, address.getCountry());
                prepStat.setString(2, address.getCity());
                prepStat.setString(3, address.getStreet());
                prepStat.setString(4, address.getHouseNumber());
                prepStat.setString(5, address.getHouseSuffix());
                prepStat.setString(6, address.getApartment());
                prepStat.setString(7, address.getPostCode());

                prepStat.executeUpdate();

                System.out.println("Uids = " + contact.getUids());
                System.out.println("First Name = " + contact.getFirstName());
                System.out.println("Last Name = " + contact.getLastName());
                System.out.println("Address:");
                System.out.println("Country = " + address.getCountry());
                System.out.println("City = " + address.getCity());
                System.out.println("Street = " + address.getStreet());
                System.out.println("House Number = " + address.getHouseNumber());
                System.out.println("House Suffix = " + address.getHouseSuffix());
                System.out.println("Apartment = " + address.getApartment());
                System.out.println("Post Code = " + address.getPostCode());
                System.out.println("Phones = " + contact.getPhones());
                System.out.println("Emails = " + contact.getEmails());
                System.out.println("Photo Path = " + contact.getPhotoPath());
                System.out.println("Birthday = " + contact.getBirthday());
                System.out.println("----------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<Contact> readJsonStream(FileInputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

        List<Contact> contacts = new ArrayList<>();


        reader.beginArray();
        while (reader.hasNext()) {
            Contact contact = gson.fromJson(reader, Contact.class);
            contacts.add(contact);
        }
        reader.endArray();
        reader.close();

        return contacts;
    }
}
