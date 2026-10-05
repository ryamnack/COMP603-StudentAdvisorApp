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
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author AUT
 */
public class PaperDAO {

    private static final String NO_PREREQUISITE = "NONE";
    private Connection conn;

    public PaperDAO() {
        conn = DBManager.getInstance().getConnection();
    }

    public boolean insertPaper(Paper paper) {
        String prereqCode = NO_PREREQUISITE;
        if (paper.hasPrerequisite()) {
            prereqCode = paper.getPrerequisite().getCourseCode();
        }
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO PAPERS (CODE, NAME, POINTS, SEMESTER, PREREQ_CODE) VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1, paper.getCourseCode());
            pstmt.setString(2, paper.getCourseName());
            pstmt.setInt(3, paper.getPoints());
            pstmt.setString(4, paper.getSemester());
            pstmt.setString(5, prereqCode);
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            return false;
        }
    }

    public Paper findPaper(String code) {
        Paper paper = null;
        String prereqCode = NO_PREREQUISITE;
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT NAME, POINTS, SEMESTER, PREREQ_CODE FROM PAPERS WHERE CODE = ?");
            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                paper = new Paper(code, rs.getString("NAME"), rs.getInt("POINTS"), rs.getString("SEMESTER"), null);
                prereqCode = rs.getString("PREREQ_CODE");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
        if (paper != null && !NO_PREREQUISITE.equals(prereqCode)) {
            paper.setPrerequisite(findPaper(prereqCode));
        }
        return paper;
    }

    public List<Paper> getAllPapers() {
        List<Paper> papers = new ArrayList<>();
        HashMap<String, Paper> byCode = new HashMap<>();
        HashMap<String, String> prereqCodes = new HashMap<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT CODE, NAME, POINTS, SEMESTER, PREREQ_CODE FROM PAPERS ORDER BY CODE");
            while (rs.next()) {
                Paper paper = new Paper(rs.getString("CODE"), rs.getString("NAME"),
                        rs.getInt("POINTS"), rs.getString("SEMESTER"), null);
                papers.add(paper);
                byCode.put(paper.getCourseCode(), paper);
                prereqCodes.put(paper.getCourseCode(), rs.getString("PREREQ_CODE"));
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
        for (Paper paper : papers) {
            String prereqCode = prereqCodes.get(paper.getCourseCode());
            if (!NO_PREREQUISITE.equals(prereqCode)) {
                paper.setPrerequisite(byCode.get(prereqCode));
            }
        }
        return papers;
    }

    public int countPapers() {
        int count = 0;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM PAPERS");
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
        return count;
    }
}