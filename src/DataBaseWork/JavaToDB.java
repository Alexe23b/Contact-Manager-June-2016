package DataBaseWork;

import parse.Address;
import parse.Contact;
import utils.ThreadsBuffer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import static utils.ChoiceDB.choiceBD;
import static utils.ConnectionDB.closeConnection;
import static utils.ConnectionDB.getConnection;

/**
 * Created by alexe on 07.07.2016.
 */
public class JavaToDB implements Runnable {
    private static Connection con = null;
    private static PreparedStatement prepStat = null;
    private final ThreadsBuffer buffer;
    private int typeBD;
    private String nameBD;


    public JavaToDB(int typeBD, String nameBD, ThreadsBuffer buffer) {
        this.buffer = buffer;
        this.typeBD = typeBD;
        this.nameBD = nameBD;
    }

//    public static void JavaToDB(int typeBD, String nameBD, List<Contact> contacts) {
//
//        try {
//            con = getConnection(choiceBD(typeBD));
//
//            for (Contact contact : contacts) {
//                Address address = contact.getAddress();
//
//                String address_record = "INSERT INTO " + nameBD + ".address (`country`, `city`, `street`, " +
//                        "`house_number`, `house_suffix`, `appartment`, `post_code`) VALUES" +
//                        "(?, ?, ?, ?, ?, ?, ?);";
//
//                prepStat = con.prepareStatement(address_record);
//
//                prepStat.setString(1, address.getCountry());
//                prepStat.setString(2, address.getCity());
//                prepStat.setString(3, address.getStreet());
//                prepStat.setString(4, address.getHouseNumber());
//                prepStat.setString(5, address.getHouseSuffix());
//                prepStat.setString(6, address.getApartment());
//                prepStat.setString(7, address.getPostCode());
//
//                prepStat.executeUpdate();
//
//                long idAdr = ((com.mysql.jdbc.Statement) prepStat).getLastInsertID();
//                String idAddress = String.valueOf(idAdr);
//
//                String contact_record = "INSERT INTO " + nameBD + ".contacts (`user_firstName`, `user_lastName`, " +
//                        "`birthday`, `address_id`)" +
//                        "VALUES (?, ?, ?, ?);";
//
//                prepStat = con.prepareStatement(contact_record);
//
//                prepStat.setString(1, contact.getFirstName());
//                prepStat.setString(2, contact.getLastName());
//
//                java.util.Date input = contact.getBirthday();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                String date = dateFormat.format(input);
//
//                prepStat.setString(3, date);
//                prepStat.setString(4, idAddress);
//
//                prepStat.executeUpdate();
//
//                long idCont = ((com.mysql.jdbc.Statement) prepStat).getLastInsertID();
//                String idContact = String.valueOf(idCont);
//
//                for (String phone : contact.getPhones()) {
//
//                    String phone_record = "INSERT INTO " + nameBD + ".phones (`phone_number`) VALUES (?);";
//
//                    prepStat = con.prepareStatement(phone_record);
//                    prepStat.setString(1, phone);
//                    prepStat.executeUpdate();
//
//                    long idPhon = ((com.mysql.jdbc.Statement) prepStat).getLastInsertID();
//                    String idPhone = String.valueOf(idPhon);
//
//                    String phonetocontact_record = "INSERT INTO " + nameBD + ".contact_phone VALUES (?, ?);";
//
//                    prepStat = con.prepareStatement(phonetocontact_record);
//                    prepStat.setString(1, idContact);
//                    prepStat.setString(2, idPhone);
//
//                    prepStat.executeUpdate();
//                }
//                for (String email : contact.getEmails()) {
//                    String email_record = "INSERT INTO " + nameBD + ".emails (`email`,`user_id`) VALUES (?, ?);";
//
//                    prepStat = con.prepareStatement(email_record);
//                    prepStat.setString(1, email);
//                    prepStat.setString(2, idContact);
//
//                    prepStat.executeUpdate();
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            closeConnection(con);
//            try {
//                prepStat.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private void ThreadToDB() {

        Contact contact = null;
        try {
            contact = buffer.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            con = getConnection(choiceBD(typeBD));

            Address address = contact.getAddress();

            String address_record = "INSERT INTO " + nameBD + ".address (`country`, `city`, `street`, " +
                    "`house_number`, `house_suffix`, `appartment`, `post_code`) VALUES" +
                    "(?, ?, ?, ?, ?, ?, ?);";

            prepStat = con.prepareStatement(address_record);

            prepStat.setString(1, address.getCountry());
            prepStat.setString(2, address.getCity());
            prepStat.setString(3, address.getStreet());
            prepStat.setString(4, address.getHouseNumber());
            prepStat.setString(5, address.getHouseSuffix());
            prepStat.setString(6, address.getApartment());
            prepStat.setString(7, address.getPostCode());

            prepStat.executeUpdate();

            long idAdr = ((com.mysql.jdbc.Statement) prepStat).getLastInsertID();
            String idAddress = String.valueOf(idAdr);

            String contact_record = "INSERT INTO " + nameBD + ".contacts (`user_firstName`, `user_lastName`, " +
                    "`birthday`, `address_id`)" +
                    "VALUES (?, ?, ?, ?);";

            prepStat = con.prepareStatement(contact_record);

            prepStat.setString(1, contact.getFirstName());
            prepStat.setString(2, contact.getLastName());

            java.util.Date input = contact.getBirthday();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(input);

            prepStat.setString(3, date);
            prepStat.setString(4, idAddress);

            prepStat.executeUpdate();

            long idCont = ((com.mysql.jdbc.Statement) prepStat).getLastInsertID();
            String idContact = String.valueOf(idCont);

            for (String phone : contact.getPhones()) {

                String phone_record = "INSERT INTO " + nameBD + ".phones (`phone_number`) VALUES (?);";

                prepStat = con.prepareStatement(phone_record);
                prepStat.setString(1, phone);
                prepStat.executeUpdate();

                long idPhon = ((com.mysql.jdbc.Statement) prepStat).getLastInsertID();
                String idPhone = String.valueOf(idPhon);

                String phonetocontact_record = "INSERT INTO " + nameBD + ".contact_phone VALUES (?, ?);";

                prepStat = con.prepareStatement(phonetocontact_record);
                prepStat.setString(1, idContact);
                prepStat.setString(2, idPhone);

                prepStat.executeUpdate();
            }
            for (String email : contact.getEmails()) {
                String email_record = "INSERT INTO " + nameBD + ".emails (`email`,`user_id`) VALUES (?, ?);";

                prepStat = con.prepareStatement(email_record);
                prepStat.setString(1, email);
                prepStat.setString(2, idContact);

                prepStat.executeUpdate();
            }
        } catch (
                SQLException e
                )

        {
            e.printStackTrace();
        } finally

        {
            closeConnection(con);
            try {
                prepStat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        Thread myThread = Thread.currentThread();
        while (!myThread.isInterrupted()) {
            ThreadToDB();
        }
    }

}
