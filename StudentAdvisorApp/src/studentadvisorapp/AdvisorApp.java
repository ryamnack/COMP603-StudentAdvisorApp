
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package studentadvisorapp;

/**
 *
 * @author xrf6688
 */
public class AdvisorApp {

    public static void main(String[] args) {
        new PaperSeeder().seedIfEmpty("Paper_Data/papers.txt");
        MainFrame frame = new MainFrame();
        new AdvisorController(frame, new DerbyPaperDAO(), new DerbyStudentDAO(), new DerbyCompletedDAO());
        frame.setVisible(true);
    }

}
