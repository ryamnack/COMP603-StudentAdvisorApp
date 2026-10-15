/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

/**
 *
 * @author calum
 */
import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PathwayTest {
    private Paper comp503;
    private Pathway pathway;

    @Before  //runs before every test
    public void setUp() {
        comp503 = new Paper("COMP503", "Programming 2", 15, "S1", null); //test data
        Programme programme = new Programme("Software Development");
        programme.addPaper(comp503);
        pathway = new Pathway(programme, new HashSet<>()); // starts with nothing completed
    }

    @Test //checking if adding the same paper twice means the points count gets doubled                         
    public void addingSamePaperTwiceOnlyCountsPointsOnce() {
        pathway.addCompletedPaper(comp503);
        pathway.addCompletedPaper(comp503);
        assertEquals(15, pathway.getPointsCompleted()); //expected vs actual
    }
    
    @Test //checking that removing a completed paper actually marks it as removed
    public void removingCompletedPaperMeansNotCompleted() {
        pathway.addCompletedPaper(comp503);
        pathway.removeCompletedPaper(comp503);
        assertFalse(pathway.hasCompleted(comp503));
    }

}
