/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xrf6688
 */
public class Programme {
    public static final int pointsRequired = 360; //points needed to complete the programme, used for the progress report
    private String courseName;
    private List<Paper> papers;

    public Programme(String courseName) {
        this.courseName = courseName;
        this.papers = new ArrayList();
    }

    /**
     * @return the courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * @return the Papers
     */
    public List<Paper> getPapers() {
        return papers;
    }
    
    public void addPaper(Paper paper){
        papers.add(paper);
    }
    
    public Paper findPaper(String courseCode){
        for (Paper p : papers) {
            if (p.getCourseCode().equalsIgnoreCase(courseCode)) {
                return p;
            }
        }
        return null;
        
    }
}
