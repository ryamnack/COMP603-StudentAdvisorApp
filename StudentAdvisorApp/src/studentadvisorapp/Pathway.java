/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

//TODO if possible: add minor object

import java.util.Set;

/**
 *
 * @author xrf6688
 */
public class Pathway {
    private Programme major;
    private Set<Paper> papersCompleted; //set so the same paper cant be marked completed twice

    public Pathway(Programme major, /*Programme minor*/ Set<Paper> papersCompleted) {
        this.major = major;
        this.papersCompleted = papersCompleted;
    }
    
    public void addCompletedPaper(Paper paper){
        if(!hasCompleted(paper)){
                getPapersCompleted().add(paper);

        }
    }
    
    public boolean hasCompleted(Paper paper){
        return getPapersCompleted().contains(paper);

    }

    public void removeCompletedPaper(Paper paper){ //undo marking a paper completed by mistake
        getPapersCompleted().remove(paper);
    }

    public int getPointsCompleted(){ //adds up the points of every completed paper, for the progress report
        int pointsCompleted = 0;
        for (Paper p : getPapersCompleted()){
            pointsCompleted += p.getPoints();
        }
        return pointsCompleted;
    }

    /**
     * @return the major
     */
    public Programme getMajor() {
        return major;
    }

    /**
     * @return the papersCompleted
     */
    public Set<Paper> getPapersCompleted() {
        return papersCompleted;
    }
    
    
}
