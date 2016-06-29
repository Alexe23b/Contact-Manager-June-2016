package utils;

import java.util.Properties;

import static utils.connectionDB.getConnection;

/**
 * Created by alexe on 28.06.2016.
 */
public class choiceDB {

    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public static final String MYSQL_URL_CONNECTION = "jdbc:mysql://localhost:3306";
    public static final String MYSQL_USER = "root";
    public static final String MYSQL_PASSWORD = "1608";


    public static Properties choiceBD(int typeBD) {

        Properties connectionProperties = new Properties();

        if (typeBD == 1) {
            connectionProperties.put("driver", MYSQL_DRIVER);
            connectionProperties.put("url", MYSQL_URL_CONNECTION);
            connectionProperties.put("user", MYSQL_USER);
            connectionProperties.put("password", MYSQL_PASSWORD);
        }
        return connectionProperties;
    }
}
