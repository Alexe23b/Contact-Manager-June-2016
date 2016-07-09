package parse;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import controller.Controller;
import utils.ThrowsBuffer;

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
    private String path;
    private final ThrowsBuffer buffer;

    public JsonToJava(String path, ThrowsBuffer buffer) {
        this.path = path;
        this.buffer = buffer;
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

    public void parseJson() throws IOException {

        JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));

        reader.beginArray();
        while (reader.hasNext()) {
            Contact contact = gson.fromJson(reader, Contact.class);
            try {
                buffer.put(contact);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        reader.endArray();
        reader.close();
    }

    @Override
    public void run() {
        try {
            parseJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
