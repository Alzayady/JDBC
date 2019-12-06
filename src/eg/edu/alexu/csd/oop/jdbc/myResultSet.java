package eg.edu.alexu.csd.oop.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Map;

public class myResultSet implements ResultSet {
    private LinkedList<Object[]> selectedTable;
    private String tableName;
    private String[] selectedColumns;
    private Class[] selectedTypes;
    private Object[] currentRow;
    private boolean beforeFirst;
    private boolean afterLast;
    private Statement statement;
    private boolean closed;
    private int cursor;

    public myResultSet(Statement statement, Object[][] selectedTable, String[] selectedColumns, Class[] selectedTypes, String tableName) throws SQLException {
        this(statement,selectedTable,selectedColumns,selectedTypes);
        this.tableName = tableName;
    }

    // use this constructor when initializing from statement
    public myResultSet(Statement statement, Object[][] selectedTable, String[] selectedColumns, Class[] selectedTypes) throws SQLException {
        this(selectedTable,selectedColumns,selectedTypes);
        this.statement = statement;
    }

    public myResultSet(Object[][] selectedTable, String[] selectedColumns, Class[] selectedTypes) throws SQLException {
        // popularize the selectedTable
        this.selectedTable = new LinkedList<>();
        for (int i = 0; i < selectedTable.length; i++) {
            Object[] tempRow = new Object[selectedTable[i].length];
            for (int j = 0; j < selectedTable[i].length; j++) {
                tempRow[j] = selectedTable[i][j];
            }
            this.selectedTable.add(tempRow);
        }
        this.selectedColumns = selectedColumns;
        this.selectedTypes = selectedTypes;
        this.beforeFirst = true;
        this.afterLast = false;
        this.cursor = -1;
        this.closed = false;
    }

    public String getTableName() {
        return this.tableName;
    }

    @Override
    public boolean next() throws SQLException {
        if (isClosed()){
            throw new SQLException("already Closed");
        }
        if (!this.afterLast && this.beforeFirst) { // go to first one in the table
            if (this.selectedTable.isEmpty()) { // if table is empty, then it is after last
                afterLast();
                return false;
            } else { // if table isn't empty, then it currents row is the first one in table
                first();
                return true;
            }
        } else if (this.afterLast && !this.beforeFirst) { // go to beyond the after last, not happeneing
            return false;
        } else if (!this.afterLast) { // go to somewhere in the meddle
            if (isLast()) { // last in the table
                afterLast();
                return false;
            } else { // get the next row, if not empty
                this.cursor = this.selectedTable.indexOf(currentRow) + 1;
                this.currentRow = this.selectedTable.get(this.cursor);
                return true;
            }
        }
        return false;
    }

    @Override
    public void close() throws SQLException {
        if (this.isClosed()){
            throw new SQLException("already closed");
        }
        this.closed = true;
    }

    @Override
    public boolean wasNull() throws SQLException {
        throw new java.lang.UnsupportedOperationException();

    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        if (currentRow != null) {
            if ((columnIndex >= 1) && (columnIndex <= this.currentRow.length)) { // safe region
                if (selectedTypes[columnIndex - 1] == String.class) { // still in safe region as, this method returns only the String
                    if (currentRow[columnIndex - 1] == null) { // if the value of some cell is null (sql) then the return value is 0
                        return null;
                    }
                    return (String) currentRow[columnIndex - 1];
                }
            }
        }
        throw new SQLException("index " + columnIndex + " is not valid");
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    // they are dealing with indexes from 1 to up, not from 0
    @Override
    public int getInt(int columnIndex) throws SQLException {
        if (currentRow != null) {
            if ((columnIndex >= 1) && (columnIndex <= this.currentRow.length)) { // safe region
                if (selectedTypes[columnIndex - 1] == Integer.class) { // still in safe region as, this method returns only the integer
                    if (currentRow[columnIndex - 1] == null) { // if the value of some cell is null (sql) then the return value is 0
                        return 0;
                    }
                    return (Integer) currentRow[columnIndex - 1];
                }
            }
        }
        throw new SQLException("index " + columnIndex + " is not valid");
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public String getString(String columnLabel) throws SQLException {
        for (int i = 0; i < this.selectedColumns.length; i++) {
            if (this.selectedColumns[i].equals(columnLabel)) {
                return this.getString(i + 1);
            }
        }
        throw new SQLException("column: " + columnLabel + " not found");
    }

    @Override
    public boolean getBoolean(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public byte getByte(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public short getShort(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public int getInt(String columnLabel) throws SQLException {
        for (int i = 0; i < this.selectedColumns.length; i++) {
            if (this.selectedColumns[i].equals(columnLabel)) {
                return this.getInt(i + 1);
            }
        }
        throw new SQLException("column: " + columnLabel + " not found");
    }

    @Override
    public long getLong(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public float getFloat(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public double getDouble(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public byte[] getBytes(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Date getDate(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Time getTime(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public InputStream getAsciiStream(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public InputStream getUnicodeStream(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public InputStream getBinaryStream(String columnLabel) throws SQLException {
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
    public String getCursorName() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return new myResultSetMetaData(this.selectedColumns, this.selectedTypes, this.tableName);
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        if (currentRow != null) {
            if ((columnIndex >= 1) && (columnIndex <= this.currentRow.length)) { // safe region
                if (currentRow[columnIndex - 1] == null) { // if the value of some cell is null (sql) then the return value is 0
                    return null;
                }
                return currentRow[columnIndex - 1];
            }
        }
        throw new SQLException("index " + columnIndex + " is not valid");
//        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Object getObject(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    // returns the index of the column labeled by columnLabel
    @Override
    public int findColumn(String columnLabel) throws SQLException {
        for (int i = 0; i < this.selectedColumns.length; i++) {
            if (selectedColumns[i].equals(columnLabel)) {
                return i + 1; // found it
            }
        }
        throw new SQLException("no column found matches: " + columnLabel);
    }

    @Override
    public Reader getCharacterStream(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Reader getCharacterStream(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        if (isClosed()){
            throw new SQLException("already closed");
        }
        return this.beforeFirst && !this.afterLast;
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        if (isClosed()){
            throw new SQLException("already closed");
        }
        return this.afterLast && !this.beforeFirst;
    }

    @Override
    public boolean isFirst() throws SQLException {
        if (isClosed()){
            throw new SQLException("already Closed");
        }
        return this.cursor == 0;
    }

    @Override
    public boolean isLast() throws SQLException {
        if (isClosed()){
            throw new SQLException("already Closed");
        }
        return this.cursor == this.selectedTable.size() - 1;
    }

    @Override
    public void beforeFirst() throws SQLException {
        if (isClosed()){
            throw new SQLException("already Closed");
        }
        this.afterLast = false;
        this.beforeFirst = true;
        this.currentRow = null;
        this.cursor = -1;
    }

    @Override
    public void afterLast() throws SQLException {
        if (isClosed()){
            throw new SQLException("already Closed");
        }
        this.afterLast = true;
        this.beforeFirst = false;
        this.currentRow = null;
        this.cursor = -1;
    }

    // move the courser to the first row in the data
    @Override
    public boolean first() throws SQLException {
        if (this.isClosed()){
            throw new SQLException("already closed");
        }
        if (this.selectedTable.isEmpty()){
            return false;
        }
        // we can do it
        this.beforeFirst = false;
        this.afterLast = false;
        this.currentRow = this.selectedTable.getFirst();
        this.cursor = 0;
        return true;
    }

    @Override
    public boolean last() throws SQLException {
        if (this.isClosed()){
            throw new SQLException("already closed");
        }
        if (this.selectedTable.isEmpty()){
            return false;
        }
        this.cursor = this.selectedTable.size() - 1;
        this.currentRow = this.selectedTable.getLast();
        this.beforeFirst = false;
        this.afterLast = false;
        return true;
    }

    @Override
    public int getRow() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public boolean absolute(int row) throws SQLException {
        if (isClosed()){
            throw new SQLException("already closed");
        }
        if (row == 0){ // before first
            this.beforeFirst();
            return false;
        } else if (row == this.selectedTable.size() + 1){ // after last
            this.afterLast();
            return false;
        }else if (row == 1){ // first
            this.first();
            return true;
        } else if (row == -1){ // last
            this.last();
            return true;
        } else if (row > 1 && row <= this.selectedTable.size()) { // from the second to the last
            this.cursor = row - 1;
            this.currentRow = this.selectedTable.get(this.cursor);
            return true;
        } else if (row < 0 && (row * -1) <= this.selectedTable.size()){ // negative come from the back, and not exceeding the size
            this.cursor = row + this.selectedTable.size(); // -1 + size() = size() - 1 : last, size() - size() = 0: first
            if (this.cursor == 0){
                this.first();
            }
            this.currentRow = this.selectedTable.get(this.cursor); // j + size() : j(th) one from the back as j is (-ve)
            return true;
        }
        return false;
    }

    @Override
    public boolean relative(int rows) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public boolean previous() throws SQLException {
        if (isClosed()){
            throw new SQLException("already Closed");
        }
        if (this.beforeFirst && !this.afterLast){ // before first position
            return false;  // can't go back one more step
        } else if (!this.beforeFirst && this.afterLast){ // at after last, no more progress, just go back.
            if (this.selectedTable.isEmpty()){ // go to before first
                beforeFirst();
                return false;
            } else { // go to the meddle
                last();
                return true;
            }
        } else if (!this.beforeFirst){ // in the meddle
            if (isFirst()){ // go to before first
                beforeFirst();
                return false;
            } else {
                this.cursor = this.selectedTable.indexOf(currentRow) - 1;
                this.currentRow = this.selectedTable.get(this.cursor);
                return true;
            }
        }
        return false;
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public int getFetchDirection() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public int getFetchSize() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public int getType() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public int getConcurrency() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public boolean rowInserted() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateNull(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateByte(int columnIndex, byte x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateShort(int columnIndex, short x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateInt(int columnIndex, int x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateLong(int columnIndex, long x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateFloat(int columnIndex, float x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateDouble(int columnIndex, double x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateString(int columnIndex, String x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateDate(int columnIndex, Date x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateTime(int columnIndex, Time x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateObject(int columnIndex, Object x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateNull(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBoolean(String columnLabel, boolean x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateByte(String columnLabel, byte x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateShort(String columnLabel, short x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateInt(String columnLabel, int x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateLong(String columnLabel, long x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateFloat(String columnLabel, float x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateDouble(String columnLabel, double x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateString(String columnLabel, String x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBytes(String columnLabel, byte[] x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateDate(String columnLabel, Date x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateTime(String columnLabel, Time x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateObject(String columnLabel, Object x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void insertRow() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateRow() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void deleteRow() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void refreshRow() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void moveToInsertRow() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void moveToCurrentRow() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Statement getStatement() throws SQLException {
        if (isClosed()){
            throw new SQLException("already closed");
        }
        return this.statement;
    }

    @Override
    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Ref getRef(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Array getArray(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Ref getRef(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Blob getBlob(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Clob getClob(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Array getArray(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Date getDate(String columnLabel, Calendar cal) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Time getTime(String columnLabel, Calendar cal) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public URL getURL(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public URL getURL(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateRef(int columnIndex, Ref x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateRef(String columnLabel, Ref x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBlob(String columnLabel, Blob x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateClob(int columnIndex, Clob x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateClob(String columnLabel, Clob x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateArray(int columnIndex, Array x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateArray(String columnLabel, Array x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public RowId getRowId(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public RowId getRowId(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public int getHoldability() throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return closed;
    }

    @Override
    public void updateNString(int columnIndex, String nString) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateNString(String columnLabel, String nString) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public NClob getNClob(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public NClob getNClob(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public String getNString(int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public String getNString(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateObject(int columnIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateObject(String columnLabel, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateObject(int columnIndex, Object x, SQLType targetSqlType) throws SQLException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public void updateObject(String columnLabel, Object x, SQLType targetSqlType) throws SQLException {
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
