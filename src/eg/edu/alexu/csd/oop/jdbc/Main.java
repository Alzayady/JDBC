package eg.edu.alexu.csd.oop.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        Driver driver = new myDriver();
        Properties info = new Properties();
        File dbDir = new File("C:\\Users\\Zahran\\Desktop\\dbms\\DataBasesDirectory\\TestDB");
        info.put("path", dbDir.getAbsoluteFile());
        Connection connection = driver.connect("jdbc:xmldb://localhost", info);
        Statement statement = connection.createStatement();
        statement.execute("create database ramy drop if exist");
        statement.execute("CREATE TABLE table_name3(column_name1 varchar, column_name2 int, column_name3 varchar)");
        statement.execute("insert into table_name3 values ('ahmed',12,'vv')");
        statement.close();
    }
}
