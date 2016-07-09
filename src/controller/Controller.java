package controller;


import DataBaseWork.JavaToDB;
import DataBaseWork.ManageBD;
import parse.JsonToJava;
import utils.FileNameFilter;
import utils.ThreadsBuffer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by alexe on 28.06.2016.
 */
public class Controller {


    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);
        System.out.println("Input user NicName:");
        String userName = in.nextLine();

        int typeBD = 1;

        ManageBD.checkUser(userName, typeBD);

        File[] listFiles = FileNameFilter.findFiles("files/" + userName, "json");

//        List<Contact> contactsAll = new ArrayList<>();
//        for (File f : listFiles) {
//            String pathToParseFile = "files/" + userName + "/" + f.getName();
//            List<Contact> contacts = JsonToJava.readJson(pathToParseFile);
//            contactsAll.addAll(contacts);
//        }

        ThreadsBuffer buffer = new ThreadsBuffer();
        List<Thread> threadsReader = new ArrayList<>();
        int i = 0;
        for (File f : listFiles) {
            i++;
            String pathToParseFile = "files/" + userName + "/" + f.getName();
            JsonToJava parser = new JsonToJava(pathToParseFile, buffer);
            Thread threadReader = new Thread(parser);
            threadsReader.add(threadReader);
            threadReader.start();
            System.out.println("Thread-Reader " + i + " was started.");
        }
        String nameBD = userName + "_AddressBook";
        JavaToDB writer = new JavaToDB(typeBD, nameBD, buffer);
        Thread threadWriter = new Thread(writer);
        System.out.println("-------------------------------");
        System.out.println("Thread-Writer was started.");
        System.out.println("-------------------------------");
        threadWriter.start();

        for (Thread thread : threadsReader) {
            thread.join();
        }
//        Thread myThread = Thread.currentThread();
//        myThread.sleep(5000);
        threadWriter.interrupt();
//        threadWriter.start();
//        threadWriter.interrupt();
        System.out.println("-------------------------------");
        System.out.println("ФСЕ ЗАШИБИСЬ!!!!");
    }

}


