package controller;


import DataBaseWork.JavaToDB;
import DataBaseWork.ManageBD;
import parse.Contact;
import parse.JsonToJava;
import utils.FileNameFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//import static DataBaseWork.ManageBD.checkUser;
//import static parse.JsonToJava.parseJson;

//import static DataBaseWork.ManageBD.createBD;

/**
 * Created by alexe on 28.06.2016.
 */
public class Controller {

    private static Scanner in;
    private static int typeBD;
    static JsonToJava parser;

    public static void main(String[] args) throws Exception {

        in = new Scanner(System.in);
        System.out.println("Input user NicName:");
        String userName = in.nextLine();

        typeBD = 1; // mySQL or 2 PostGrey

        ManageBD.checkUser(userName, typeBD);

        File[] listFiles = FileNameFilter.findFiles("files/" + userName, "json");

//        for (File f : listFiles) {
//
//            String pathToParseFile = "files/" + userName + "/" + f.getName();
//
//            parser = new JsonToJava(pathToParseFile);
//
//            Thread thread = new Thread(parser);
//            thread.start();
//            System.out.println("Parsing was started: " + pathToParseFile);
//
//        }
        List<Contact> contactsAll = new ArrayList<>();
        for (File f : listFiles) {
            String pathToParseFile = "files/" + userName + "/" + f.getName();
            List<Contact> contacts = JsonToJava.readJson(pathToParseFile);
            contactsAll.addAll(contacts);
        }

        JavaToDB.JavaToDB(typeBD,  userName + "_AddressBook", contactsAll);
    }
}


