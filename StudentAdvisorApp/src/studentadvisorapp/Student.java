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

//this class is to manage students' details
public class Student {
    private String name;
    private int yearLevel;
    private Pathway pathway;

    public Student(String name, int yearLevel) {
        this.name = name;
        this.yearLevel = yearLevel;
        
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the yearLevel
     */
    public int getYearLevel() {
        return yearLevel;
    }

    /**
     * @param yearLevel the yearLevel to set
     */
    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }

    /**
     * @return the pathway
     */
    public Pathway getPathway() {
        return pathway;
    }

    /**
     * @param pathway the pathway to set
     */
    public void setPathway(Pathway pathway) {
        this.pathway = pathway;
    }
    /*this method checks if student can take a paper based on whether they
    have completed its prerequisite if it has any.
    */
    public boolean canTake (Paper paper){
        if (pathway == null){
            return !paper.hasPrerequisite();
        }
        if (!paper.hasPrerequisite()){
            return true;
        }
        return pathway.hasCompleted(paper.getPrerequisite());

    }

    /*walks the whole prerequisite chain above a paper (not just the immediate one) and
    returns any of them the student hasn't completed yet, so checkCanTake can tell the
    student everything they still need, not just the first step.*/
    public List<Paper> getMissingPrerequisites(Paper paper){
        List<Paper> missing = new ArrayList<>();
        Paper current = paper.getPrerequisite();
        while (current != null){
            if (pathway == null || !pathway.hasCompleted(current)){
                missing.add(current);
            }
            current = current.getPrerequisite();
        }
        return missing;
    }

    @Override
    public String toString() {
        return name + " (Year " + yearLevel + ")";
    }
    
}
