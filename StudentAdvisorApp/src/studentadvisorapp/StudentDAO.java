/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AUT
 */
public class StudentDAO {

    private Connection conn;

    public StudentDAO() {
        conn = DBManager.getInstance().getConnection();
    }

    public boolean insertStudent(Student student) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO STUDENTS (NAME, YEAR_LEVEL) VALUES (?, ?)");
            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getYearLevel());
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            return false;
        }
    }

    public boolean updateYearLevel(Student student) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE STUDENTS SET YEAR_LEVEL = ? WHERE NAME = ?");
            pstmt.setInt(1, student.getYearLevel());
            pstmt.setString(2, student.getName());
            int rows = pstmt.executeUpdate();
            pstmt.close();
            return rows > 0;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            return false;
        }
    }

    public Student findStudent(String name) {
        Student student = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT YEAR_LEVEL FROM STUDENTS WHERE NAME = ?");
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                student = new Student(name, rs.getInt("YEAR_LEVEL"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
        return student;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT NAME, YEAR_LEVEL FROM STUDENTS ORDER BY NAME");
            while (rs.next()) {
                students.add(new Student(rs.getString("NAME"), rs.getInt("YEAR_LEVEL")));
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
        return students;
    }
}