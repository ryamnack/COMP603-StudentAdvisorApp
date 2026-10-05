/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author AUT
 */
public class DBManager {

    private static final String URL = "jdbc:derby:AdvisorDB;create=true";
    private static DBManager instance;
    private Connection conn;

    private DBManager() {
        try {
            conn = DriverManager.getConnection(URL);
            createTable("PAPERS", "CREATE TABLE PAPERS (CODE VARCHAR(20) PRIMARY KEY, NAME VARCHAR(100), "
                    + "POINTS INT, SEMESTER VARCHAR(20), PREREQ_CODE VARCHAR(20))");
            createTable("STUDENTS", "CREATE TABLE STUDENTS (NAME VARCHAR(50) PRIMARY KEY, YEAR_LEVEL INT)");
            createTable("COMPLETED", "CREATE TABLE COMPLETED (STUDENT_NAME VARCHAR(50), PAPER_CODE VARCHAR(20), "
                    + "PRIMARY KEY (STUDENT_NAME, PAPER_CODE))");
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return conn;
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
            instance = null;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }

    private boolean tableExists(String name) throws SQLException {
        ResultSet rs = conn.getMetaData().getTables(null, null, name, null);
        boolean found = rs.next();
        rs.close();
        return found;
    }

    private void createTable(String name, String sql) throws SQLException {
        if (!tableExists(name)) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}