package studentadvisorapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DerbyPaperDAOTest {

    private PaperDAO paperDAO;

    @Before
    public void setUp() {
        new PaperSeeder().seedIfEmpty("Paper_Data/papers.txt");
        paperDAO = new DerbyPaperDAO();
    }

    @Test
    public void allThirteenPapersAreLoaded() {
        assertEquals(13, paperDAO.countPapers());
    }

    @Test
    public void findPaperReturnsItsPrerequisite() {
        Paper paper = paperDAO.findPaper("COMP603");
        assertNotNull(paper);
        assertEquals("COMP503", paper.getPrerequisite().getCourseCode());
    }

    @Test
    public void findPaperReturnsNullForUnknownCode() {
        assertNull(paperDAO.findPaper("NOPE999"));
    }
}