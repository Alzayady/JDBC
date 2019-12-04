package eg.edu.alexu.csd.oop.jdbc;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class myDriver implements Driver {
    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        return null;
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return false;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
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
