package uncc.tables;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.hsqldb.*;

import uncc.HSQLDatabaseConnection;
import uncc.objects.HSQLDatabaseObject;

public abstract class HSQLDatabaseTable<E extends HSQLDatabaseObject> {
    /**
     * 
     */
    private final Type type = ((ParameterizedType) this.getClass()
                                                       .getGenericSuperclass())
                                                       .getActualTypeArguments()[0];
    private final String tbname = ((Class) type).getSimpleName();
    static HSQLDatabaseTable instance;
    static HSQLDatabaseConnection hsqldb;
    static Connection dbconn;
    private LinkedHashMap<Field, Method> getterMap = new LinkedHashMap<>();
    private PreparedStatement stmtGetLastID, stmtCreate, stmtInsert, stmtUpdate,
        stmtDelete, stmtGetAll, stmtGetByID;
    private String strCreate, strInsert, strUpdate, strDelete, strGetByID, strGetAll;
    private boolean verbose = false;
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
    
    /**
     * @param hsqldb
     * @throws SQLException
     */
    HSQLDatabaseTable(HSQLDatabaseConnection hsqldb) throws SQLException {
        instance = this;
        identifyFieldsAndAssocMethods();
        HSQLDatabaseTable.hsqldb = hsqldb;
        HSQLDatabaseTable.dbconn = hsqldb.getConnection();
        initializeStatements();
    }
    
    /**
     * @throws SQLException
     */
    private void initializeStatements() throws SQLException {
        try {
            this.stmtGetLastID = coerce("call identity()");
            this.stmtCreate = coerce(this.strCreate);
            if (getRowCount() == -1) {
                System.err.println("Table " + tbname + " not found. Attempting "
                        + "to create with command: \n" + this.strCreate + "\n");
                update(this.stmtCreate);
                if (getRowCount() == -1) {
                    System.err.println("Table creation failed.");
                    throw new IllegalStateException();
                }
            }
            this.stmtInsert = coerce(this.strInsert);
            this.stmtUpdate = coerce(this.strUpdate);
            this.stmtDelete = coerce(this.strDelete);
            this.stmtGetAll = coerce(this.strGetAll);
            this.stmtGetByID = coerce(this.strGetByID);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * @param str
     * @return
     * @throws SQLException
     */
    private PreparedStatement coerce(String str) throws SQLException {
        try {
            return dbconn.prepareStatement(str);
        } catch (SQLException e) {
            System.err.println("Failed to coerce String into PreparedStatement"
                    + " with error " + e.getErrorCode() + ":\n  " + e + "\n");
            throw e;
        }
    }
    
    /**
     * @return
     */
    private int getRowCount() {
        ResultSet res = execute("SELECT COUNT(*) FROM " + this.tbname + ";");
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
    
    /**
     * @param str
     * @return
     */
    protected int update(String str) {
        try {
            PreparedStatement stmt = coerce(str);
            return update(stmt);
        } catch (SQLException e) {
            System.err.println("Failed to update SQL String with error: " 
                    + e.getErrorCode() + ": " + e + "\n" + str + "\n");
            return -1;
        }
    }
    
    /**
     * @param stmt
     * @return
     */
    protected int update(PreparedStatement stmt) {
        try {
            int status = stmt.executeUpdate();
            return status;
        } catch (SQLException e) {
            System.err.println("Failed to update SQL String with error: " 
                    + e.getErrorCode() + ": " + e + "\n" + stmt + "\n");
            return -1;
        }
    }
    
    /**
     * @param str
     * @return
     */
    protected ResultSet execute(String str) {
        try {
            PreparedStatement stmt = coerce(str);
            return execute(stmt);
        } catch (SQLException e) {
            System.err.println("Failed to execute SQL String with error "
                    + e.getErrorCode() + ": " + e + "\n" + str + "\n");
            return null;
        }
    }
    
    /**
     * @param stmt
     * @return
     */
    protected ResultSet execute(PreparedStatement stmt) {
        try {
            ResultSet res = stmt.executeQuery();
            return res;
        } catch (SQLException e) {
            System.err.println("Failed to execute SQL String with error "
                    + e.getErrorCode() + ": " + e + "\n" + stmt + "\n");
            return null;
        }
    }
    
    /**
     * @throws SQLException
     */
    private void identifyFieldsAndAssocMethods() throws SQLException {
        Field[] fields = null;
        try {
            fields = ((Class<E>) type).getDeclaredFields();
        } catch (NullPointerException e) {
            System.err.println("Classes that extend HSQLDatabaseTable must "
                + "parametize the generalized class.\n"
                + "Extension of the general class is disallowed. For example:\n"
                + ">>  class ExtendedTable extends HSQLDatabaseTable {}\n"
                + "          Must become:\n"
                + ">>  class ExtendedTable extends HSQLDatabaseTable<Obj> {}\n"
                + "Where Class Type Obj extends uncc.objects.HSQLDatabaseObject.");
            throw new IllegalStateException();
        }
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
                + " (\n  Obj OBJECT NOT NULL,"
                + "\n  ID INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL";
        this.strInsert = ""
                + "INSERT INTO " + this.tbname
                + " (Obj, ID";
        this.strUpdate = ""
                + "UPDATE " + this.tbname + " SET"
                + "\n  Obj = ?";
        
        Method[] methods = null;
        try {
            methods = ((Class<E>) type).getMethods();
        } catch (NullPointerException e) {
            throw new IllegalStateException();
        }
        
        for (Field field : fields) {
            if (field != null) {
                for (Method method : methods) {
                    if (method.getGenericReturnType() == field.getType()) {
                        String methodName = method.getName().toLowerCase();
                        String fieldName = field.getName().toLowerCase();
                        if (methodName.contains("get") &&
                                methodName.contains(fieldName)) {
                            getterMap.put(field, method);
                        }
                    }
                }
                
                if (!getterMap.containsKey(field)) {
                    System.err.println("Could not find getter method for field "
                            + tbname + "." + field.getName() + ".\nAs such,"
                            + " it will be ignored in table creation.");
                    numOfFields--;
                } else {
                    if (TYPEMAP.containsKey(field.getType())) {
                        this.strCreate += ",\n  " + field.getName() + " " 
                                + TYPEMAP.get(field.getType());
                        this.strInsert += ", " + field.getName();
                        this.strUpdate += ",\n  " + field.getName() + " = ?";
                    } else {
                        this.strCreate += ",\n  " + field.getName() + " OBJECT";
                        this.strInsert += ", " + field.getName();
                        this.strUpdate += ",\n  " + field.getName() + " = ?";
                    }
                }
            }
        }
        
        this.strCreate += ",\n  PRIMARY KEY (ID)\n);";
        this.strInsert += ")\n  VALUES (?, ?";
        for (int i = 0; i < numOfFields; i++) {
            this.strInsert += ", ?";
        }
        this.strInsert += ");";
        this.strUpdate += "\n  WHERE ID = ?;";
        this.strDelete = ""
                + "DELETE FROM " + this.tbname
                + "\n  WHERE ID = ?;";
        this.strGetAll = ""
                + "SELECT Obj, ID\n  FROM " + this.tbname
                + "\n  ORDER BY ID;";
        this.strGetByID = ""
                + "SELECT Obj\n  FROM " + this.tbname
                + "\n  WHERE ID = ?;";
        
        if (this.verbose) {
            System.out.println(this.strCreate);
            System.out.println(this.strInsert);
            System.out.println(this.strUpdate);
            System.out.println(this.strDelete);
            System.out.println(this.strGetAll);
            System.out.println(this.strGetByID);
        }
    }
    
    /**
     * @param obj
     * @return
     */
    public boolean insert(HSQLDatabaseObject obj) {
        try {
            this.stmtInsert.setObject(1, obj, Types.JAVA_OBJECT);
            this.stmtInsert.setNull(2, Types.INTEGER);
            int idx = buildStatement(obj, this.stmtInsert, 3);
            if (idx > 0) {
                update(this.stmtInsert);
                obj.setID(getLastID());
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * @param obj
     * @return
     */
    public boolean update(HSQLDatabaseObject obj) {
        try {
            this.stmtUpdate.setObject(1, obj, Types.JAVA_OBJECT);
            int idx = buildStatement(obj, this.stmtUpdate, 2);
            if (idx > 0) {
                update(this.stmtUpdate);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * @param obj
     * @return
     */
    public boolean delete(HSQLDatabaseObject obj) {
        try {
            this.stmtDelete.setInt(1, obj.getID());
            execute(this.stmtDelete);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * @param obj
     * @param stmt
     * @param idx
     * @return
     */
    private int buildStatement(HSQLDatabaseObject obj, PreparedStatement stmt, int idx) {
        try {
            for (Field field : getterMap.keySet()) {
                try {
                    stmt.setObject(idx, getterMap.get(field).invoke(obj));
                } catch (IllegalAccessException | IllegalArgumentException |
                         NullPointerException   | InvocationTargetException e) {
                    e.printStackTrace();
                    return -1;
                }
                idx++;
            }
            return idx;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    /**
     * @param ID
     * @return
     */
    public HSQLDatabaseObject getByID(int ID) {
        try {
            this.stmtGetByID.setInt(1, ID);
            ResultSet res = execute(this.stmtGetByID);
            if (res.next()) {
                E obj = (E) res.getObject(1);
                res.close();
                return obj;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * @return
     */
    public int getLastID() {
        ResultSet res = execute(this.stmtGetLastID);
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
    
    /**
     * @return
     */
    public HSQLDatabaseObject[] getAll() {
        try {
            ResultSet res = execute(this.stmtGetAll);
            HSQLDatabaseObject[] obj = new HSQLDatabaseObject[getRowCount()];
            int idx = 0;
            while (res.next()) {
                obj[idx] = (E) res.getObject(1);
            }
            return (E[]) obj;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
