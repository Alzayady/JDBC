package eg.edu.alexu.csd.oop.jdbc;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class myDriver implements Driver {
    public Connection connect(String s, Properties info) throws SQLException {
        if(!acceptsURL(s)){
            throw new SQLException("not accepted url");
        }
        Object test=info.get("path");
        if(test==null){
            throw new SQLException("path doesn't in Properties");
        }
        String path = null;
        if(test.getClass()== File.class){
            File file=(File) test;
            try {
                path=file.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            path= (String)info.get("path");

        }
        System.out.println(path);
        return myConnection.controls.getConnection(path);
    }

    @Override
    public boolean acceptsURL(String s) throws SQLException {
        if(s.matches("^jdbc:xmldb://localhost$")){
            return true;
        }
        return false;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        if(!acceptsURL(url)){
            throw new SQLException("not accepted url");
        }
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public int getMinorVersion() {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public boolean jdbcCompliant() {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new java.lang.UnsupportedOperationException();    }
}
