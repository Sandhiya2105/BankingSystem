package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {

    public static String getConnectionString(String fileName) throws IOException {
        String connStr;
        Properties props = new Properties();

        FileInputStream fis = new FileInputStream("resources/" + fileName);
        props.load(fis);

        String user = props.getProperty("user");
        String password = props.getProperty("password");
        String protocol = props.getProperty("protocol");
        String system = props.getProperty("system");
        String port = props.getProperty("port");
        String database = props.getProperty("database");

        connStr = protocol + "//" + system + ":" + port + "/" + database +
                "?user=" + user + "&password=" + password;

        return connStr;
    }
}

