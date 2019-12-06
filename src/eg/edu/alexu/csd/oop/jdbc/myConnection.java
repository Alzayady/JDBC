package eg.edu.alexu.csd.oop.jdbc;

import eg.edu.alexu.csd.oop.db.DB;

import java.sql.*;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;


public class myConnection implements Connection {
    public static final myConnection controls =new myConnection();
    private static Stack<ref> availableConnection =new Stack<>();
    private LinkedList<myStatement> statements =new LinkedList<>();
    private ReentrantLock lock = new ReentrantLock();
    private ref r;

    public DB getDataBase() {
        return r.getDataBase();
    }

    private myConnection ( ref r ){
        this.r=r;
    }
    private myConnection(){}

    public  Connection getConnection(String path) {
        if(availableConnection.empty()){
            return new myConnection( new ref( makeDB(path)) );
        }
        ref r =availableConnection.pop();
        DB db =setDb(path,r.getDataBase());
        r.setDataBase(db);
        return new myConnection( r);
    }



    private static DB makeDB(String path){
        DB Db=new DB();
        try {
            Db.executeStructureQuery("create database " + path);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Db;
    }

    private static DB setDb(String path , DB db){
        try {
            db.executeStructureQuery("create database " + path);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return db;
    }
    public void removeStatement(myStatement statement){
        statements.remove(statement);
    }



    @Override
    public Statement createStatement() throws SQLException {
        if(this.isClosed())throw new SQLException("this connection is already  closed ");
        myStatement statement=new myStatement(this);
        statements.add(statement);
        return statement;
    }

    @Override
    public void close() throws SQLException {
        if(this.isClosed())throw new SQLException("this connection is already  closed ");
        for(myStatement s :statements){
            s.close();
        }
        availableConnection.push(r);
        lock.lock();

    }


    @Override
    public boolean isClosed() throws SQLException {
        return lock.isLocked();
    }


    private  class ref{
        public DB getDataBase() {
            return DataBase;
        }

        public void setDataBase(DB dataBase) {
            DataBase = dataBase;
        }

        private DB DataBase;
        ref(DB Database){
            this.DataBase=Database;
        }
    }
























    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void commit() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void rollback() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }



    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public String getCatalog() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void clearWarnings() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public int getHoldability() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Clob createClob() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Blob createBlob() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public NClob createNClob() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public String getSchema() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
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

