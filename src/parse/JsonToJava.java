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
public class JsonToJava implements Runnable {


    private static Gson gson = new Gson();
    public String path;

    public JsonToJava(String path) {
        this.path = path;
    }

//    public void parseJson(String path) throws IOException {
//
//        List<Contact> contacts = readJson(new FileInputStream(path));
//
//    }

    public static List<Contact> readJson(String path) throws IOException {

        JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));

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

    @Override
    public void run() {
        try {
            readJson(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
