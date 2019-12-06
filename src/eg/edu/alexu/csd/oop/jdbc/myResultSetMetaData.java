package eg.edu.alexu.csd.oop.jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class myResultSetMetaData implements ResultSetMetaData {

    private String[] Labels;
    private Class [] types;
    private String name;
    myResultSetMetaData(String[] Labels ,Class [] types , String name){
        this.Labels=Labels;
        this.types=types;
        this.name=name;
    }
    @Override
    public int getColumnCount() throws SQLException {
        return Labels.length;
    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        if(column<1||column> Labels.length)throw new SQLException(column +" isn't in [ 1 ," + Labels.length+"]");
        return Labels[column-1];
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        return getColumnLabel(column);
    }

    @Override
    public int getColumnType(int column) throws SQLException {
        if(column<1||column> Labels.length)throw new SQLException(column +" isn't in [ 1 ," + Labels.length+"]");
        if(types[column-1]==Integer.class||types[column-1]==int.class)return 4;
        if(types[column-1]==Boolean.class||types[column-1]==boolean.class) return 16;
        if(types[column-1]==String.class)return 12;
        throw new SQLException("unsupported Type");
    }
    @Override
    public String getTableName(int column) throws SQLException {
        if(column<1||column> Labels.length)throw new SQLException(column +" isn't in [ 1 ," + Labels.length+"]");
        return name;
    }






    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();

    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        return true;

    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();

    }

    @Override
    public int isNullable(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();

    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();

    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();

    }



    @Override
    public String getSchemaName(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();

    }

    @Override
    public int getPrecision(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();

    }

    @Override
    public int getScale(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();

    }



    @Override
    public String getCatalogName(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();

    }



    @Override
    public String getColumnTypeName(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();

    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();

    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();

    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();

    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();

    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new java.lang.UnsupportedOperationException();

    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new java.lang.UnsupportedOperationException();

    }
}
