package eg.edu.alexu.csd.oop.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        Driver driver = new myDriver();
        Properties info = new Properties();
        File dbDir = new File("C:\\Users\\Zahran\\Desktop\\dbms\\DataBasesDirectory\\TestDB");
        info.put("path", dbDir.getAbsoluteFile());
        Connection connection = driver.connect("jdbc:xmldb://localhost", info);
    }
}
