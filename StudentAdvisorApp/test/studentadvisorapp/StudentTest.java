package studentadvisorapp;

import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class StudentTest {

    private Paper comp501;
    private Paper comp502;
    private Paper comp603;
    private Student student;

    @Before
    public void setUp() {
        comp501 = new Paper("COMP501", "Programming 1", 15, "S1", null);
        comp502 = new Paper("COMP502", "Programming 2", 15, "S2", comp501);
        comp603 = new Paper("COMP603", "Software Construction", 15, "S1", comp502);
        Programme programme = new Programme("Software Development");
        programme.addPaper(comp501);
        programme.addPaper(comp502);
        programme.addPaper(comp603);
        student = new Student("Test", 2);
        student.setPathway(new Pathway(programme, new HashSet<>()));
    }

    @Test
    public void cannotTakePaperWhenPrerequisiteNotCompleted() {
        assertFalse(student.canTake(comp502));
    }
    
        @Test
    public void canTakePaperWhenPrerequisiteCompleted() {
        student.getPathway().addCompletedPaper(comp501);
        assertTrue(student.canTake(comp502));
    }
    
        @Test
    public void missingPrerequisitesListsWholeChain() {
        List<Paper> missing = student.getMissingPrerequisites(comp603);
        assertEquals(2, missing.size());
        assertTrue(missing.contains(comp501));
    }
}

