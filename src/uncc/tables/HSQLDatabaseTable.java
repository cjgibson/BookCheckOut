package uncc.tables;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

import org.hsqldb.*;

import uncc.objects.HSQLDatabaseObject;

abstract class HSQLDatabaseTable<E extends HSQLDatabaseObject> {
    private static final String DBPATH = "jdbc:hsqldb:file:";
    private Type type = ((ParameterizedType) this.getClass()
                                                 .getGenericSuperclass())
                                                 .getActualTypeArguments()[0];
    private final String tbname = ((Class) type).getSimpleName();
    static Connection dbconn;
    static HSQLDatabaseTable instance;
    private PreparedStatement stmtGetLastID, stmtGetRowCount, stmtCreate,
        stmtInsert, stmtUpdate, stmtDelete, stmtGetByID, stmtIterAll;
    private String strCreate, strInsert, strUpdate, strDelete, strGetByID, strIterAll;
    private static final HashMap<Type, String> TYPEMAP = new HashMap<>();
    static {
        TYPEMAP.put(boolean.class, "BOOLEAN");
        TYPEMAP.put(byte.class, "INTEGER");
        TYPEMAP.put(char.class, "CHAR(1)");
        TYPEMAP.put(double.class, "DOUBLE");
        TYPEMAP.put(float.class, "DOUBLE");
        TYPEMAP.put(int.class, "INTEGER");
        TYPEMAP.put(long.class, "INTEGER");
        TYPEMAP.put(short.class, "INTEGER");
                
        TYPEMAP.put(Boolean.class, "BOOLEAN");
        TYPEMAP.put(Byte.class, "INTEGER");
        TYPEMAP.put(Double.class, "DOUBLE");
        TYPEMAP.put(Float.class, "DOUBLE");
        TYPEMAP.put(Integer.class, "INTEGER");
        TYPEMAP.put(Long.class, "INTEGER");
        TYPEMAP.put(String.class, "VARCHAR(1024)");
    }
    
    HSQLDatabaseTable(String dbname) {
        identifyFields();
        initializeDriver();
        try {
            dbconn = DriverManager.getConnection(DBPATH + dbname, "sa", "");
        } catch (SQLException e) {
            throw new IllegalStateException("SQL Server Connection failed with"
                    + " error code " + e.getErrorCode() + ".");
        }
        initializeStatements();
        instance = this;
    }
    
    private void initializeDriver() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }
    
    private void initializeStatements() {
        try {
            this.stmtCreate = coerce(this.strCreate);
            this.stmtInsert = coerce(this.strInsert);
            this.stmtUpdate = coerce(this.strUpdate);
            this.stmtDelete = coerce(this.strDelete);
            this.stmtGetByID = coerce(this.strGetByID);
            this.stmtIterAll = coerce(this.strIterAll);
            this.stmtGetLastID = dbconn.prepareStatement("call identity()");
            this.stmtGetRowCount = dbconn.prepareStatement(
                    "SELECT COUNT(*) FROM " + tbname + ";");
        } catch (SQLException e) {
            throw new IllegalStateException();
        }
        if (getRowCount() == -1) {
            execute(this.stmtCreate);
        }
    }
    
    private PreparedStatement coerce(String str) {
        try {
            return dbconn.prepareStatement(str);
        } catch (SQLException e) {
            throw new IllegalStateException();
        }
    }
    
    private int getRowCount() {
        ResultSet res = execute(stmtGetRowCount);
        if (res == null)
            return -1;
        try {
            res.next();
            int rc = res.getInt(1);
            res.close();
            return rc;
        } catch (SQLException e) {
            throw new IllegalStateException();
        }
    }
    
    protected ResultSet execute(PreparedStatement stmt) {
        try {
            ResultSet res = stmt.executeQuery();
            return res;
        } catch (SQLException e) {
            return null;
        }
    }
    
    protected ResultSet execute(String str) {
        try {
            PreparedStatement stmt = dbconn.prepareStatement(str);
            return execute(stmt);
        } catch (SQLException e) {
            return null;
        }
    }
    
    private void identifyFields() {
        Field[] fields = ((Class<E>) type).getDeclaredFields();
        int numOfFields = fields.length;
        for (int i = 0 ; i < fields.length ; i++) {
            if (Modifier.isPrivate(fields[i].getModifiers())) {
                if (Modifier.isFinal(fields[i].getModifiers()) ||
                        Modifier.isTransient(fields[i].getModifiers())) {
                    fields[i] = null;
                    numOfFields--;
                }
            }
        }
        
        this.strCreate = ""
                + "CREATE CACHED TABLE " + this.tbname
                + "(\n  Obj OBJECT,"
                + "\n  ID INTEGER IDENTITY";
        this.strInsert = ""
                + "INSERT INTO " + this.tbname
                + "(\n  Obj,\n  ID";
        this.strUpdate = ""
                + "UPDATE " + this.tbname + " SET"
                + "\n  Obj = ?";
        
        for (Field field : fields) {
            if (field != null) {
                if (TYPEMAP.containsKey(field.getType())) {
                    this.strCreate += ",\n  " + field.getName() + " " 
                            + TYPEMAP.get(field.getType());
                    this.strInsert += ",\n  " + field.getName();
                    this.strUpdate += ",\n  " + field.getName() + " = ?";
                } else {
                    this.strCreate += ",\n  " + field.getName() + " OBJECT";
                    this.strInsert += ",\n  " + field.getName();
                    this.strUpdate += ",\n  " + field.getName() + " = ?";
                }
            }
        }
        
        this.strCreate += ",\n  UNIQUE(ID) \n);\n";
        this.strInsert += "\n) VALUES (?, ?";
        for (int i = 0; i < numOfFields; i++) {
            this.strInsert += ", ?";
        }
        this.strInsert += ");\n";
        this.strUpdate += "\n  WHERE ID = ?;\n";
        
        this.strDelete = ""
                + "DELETE FROM " + this.tbname
                + "\n  WHERE ID = ?;\n";
        this.strGetByID = ""
                + "SELECT Obj\n  FROM " + this.tbname
                + "\n  WHERE ID = ?;\n";
        this.strIterAll = ""
                + "SELECT Obj, ID\n  FROM " + this.tbname
                + "\n  ORDER BY ID;\n";
        
        System.out.println(this.strCreate);
        System.out.println(this.strInsert);
        System.out.println(this.strUpdate);
        System.out.println(this.strDelete);
        System.out.println(this.strGetByID);
        System.out.println(this.strIterAll);
    }
}
