/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

/**
 *
 * @author calum
 */
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProgrammeTest {
    private Programme programme;
    private Paper comp503;

    @Before //runs before every test
    public void setUp() { //test data
        programme = new Programme("Software Development"); 
        comp503 = new Paper("COMP503", "Programming 2", 15, "S1", null); 
        programme.addPaper(comp503);
    }

    @Test //testing if lowercase paper code still works
    public void findPaperIgnoresCase() {
        assertEquals(comp503, programme.findPaper("comp503")); //lower case still finds it
    }

    @Test //testing if an unknown paper code returns null
    public void findPaperReturnsNullForUnknownCode() {
        assertNull(programme.findPaper("XXXX999"));
    }

}
