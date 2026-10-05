/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

/**
 *
 * @author xrf6688
 */
public class Paper {

    private final String courseCode;
    private final String courseName;
    private final int points;
    private final String semester;
    private Paper prerequisite;

    public Paper(String courseCode, String courseName, int points, String semester, Paper prerequisite) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.points = points;
        this.semester = semester;
        this.prerequisite = prerequisite;

    }

    /**
     * @return the courseCode
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * @return the courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * @return the semester
     */
    public String getSemester() {
        return semester;
    }

    /**
     * @return the prerequisite
     */
    public Paper getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(Paper prerequisite) {
        this.prerequisite = prerequisite;
    }

    public boolean hasPrerequisite() {
        return prerequisite != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Paper other = (Paper) obj;
        return courseCode.equals(other.courseCode);
    }

    @Override
    public int hashCode() {
        return courseCode.hashCode();
    }

    @Override
    public String toString() {
        String prereqText;
        if (hasPrerequisite()) { //if there is no prereq, prereq = 'None' when printing paper data because when reading paper data prereq is null
            prereqText = prerequisite.getCourseCode();
        } else {
            prereqText = "None";
        }
        return courseCode + " - " + courseName + " (" + points + " pts) | Prerequisite: " + prereqText;
    }
}