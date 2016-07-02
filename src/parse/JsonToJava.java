package parse;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexe on 10.06.2016.
 */
public class JsonToJava {
    private static Gson gson = new Gson();

    public static void parseJson(String path) throws IOException {

        List<Contact> contacts = readJsonStream(new FileInputStream(path));

        for (Contact contact : contacts) {
            Address address = contact.getAddress();

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
