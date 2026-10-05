/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author AUT
 */
public class DerbyCompletedDAO implements CompletedDAO {

    private Connection conn;
    private PaperDAO paperDAO;

    public DerbyCompletedDAO() {
        conn = DBManager.getInstance().getConnection();
        paperDAO = new DerbyPaperDAO();
    }

    @Override
    public boolean addCompleted(String studentName, String paperCode) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO COMPLETED (STUDENT_NAME, PAPER_CODE) VALUES (?, ?)");
            pstmt.setString(1, studentName);
            pstmt.setString(2, paperCode);
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean removeCompleted(String studentName, String paperCode) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM COMPLETED WHERE STUDENT_NAME = ? AND PAPER_CODE = ?");
            pstmt.setString(1, studentName);
            pstmt.setString(2, paperCode);
            int rows = pstmt.executeUpdate();
            pstmt.close();
            return rows > 0;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public Set<Paper> getCompletedPapers(String studentName) {
        List<String> codes = new ArrayList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT PAPER_CODE FROM COMPLETED WHERE STUDENT_NAME = ?");
            pstmt.setString(1, studentName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                codes.add(rs.getString("PAPER_CODE"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
        Set<Paper> papers = new HashSet<>();
        for (String code : codes) {
            Paper paper = paperDAO.findPaper(code);
            if (paper != null) {
                papers.add(paper);
            }
        }
        return papers;
    }
}