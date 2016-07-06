package controller;


import manager.ManageBD;
import parse.JsonToMySQL;
import utils.FileNameFilter;

import java.io.File;
import java.util.Scanner;

import static manager.ManageBD.checkUser;
import static parse.JsonToMySQL.parseJson;

//import static manager.ManageBD.createBD;

/**
 * Created by alexe on 28.06.2016.
 */
public class Controller {

    private static Scanner in;
    private static int typeBD;
    //    private static int versionBD;

    public static void main(String[] args) throws Exception {

        in = new Scanner(System.in);
        System.out.println("Input user NicName:");
        String userName = in.nextLine();

        typeBD = 1; // mySQL or 2 PostGrey

        ManageBD.checkUser(userName, typeBD);

        File[] listFiles = FileNameFilter.findFiles("files/" + userName, "json");

        for (File f : listFiles) {

            String pathToParseFile = "files/" + userName + "/" + f.getName();
            System.out.println(pathToParseFile);
            JsonToMySQL.parseJson(pathToParseFile, typeBD, userName + "AddressBook");
        }

    }
}


