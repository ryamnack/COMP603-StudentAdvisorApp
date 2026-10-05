package studentadvisorapp;


import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author calum
 */
public class MenuPanel extends JPanel{
    //buttons for each menu option
    private final JButton viewPapers = new JButton("View Available Papers");
    private final JButton markPapersCompleted = new JButton("Mark Papers as Completed");
    private final JButton checkCanTake = new JButton("Check if I Can Take a Paper");
    private final JButton deleteCompletedPaper = new JButton("Remove a Completed Paper");
    private final JButton exportProgressReport = new JButton("Export Progress Report");
    private final JButton logOut = new JButton("Log Out");
    private final JButton closeApp = new JButton("Close App");


    
    public MenuPanel(){
        setLayout(new GridBagLayout());
        JPanel form = new JPanel(new GridLayout(0, 1, 5, 5));
        form.add(new JLabel("Select Choice:"));
        form.add(viewPapers);
        form.add(markPapersCompleted);
        form.add(checkCanTake);
        form.add(deleteCompletedPaper);
        form.add(exportProgressReport);
        form.add(logOut);
        form.add(closeApp);

        add(form);
    }

    
    //for controller
    /**
     * @return the viewPapers
     */
    public JButton getViewPapers() {
        return viewPapers;
    }

    /**
     * @return the markPapersCompleted
     */
    public JButton getMarkPapersCompleted() {
        return markPapersCompleted;
    }

    /**
     * @return the checkCanTake
     */
    public JButton getCheckCanTake() {
        return checkCanTake;
    }

    /**
     * @return the deleteCompletedPaper
     */
    public JButton getDeleteCompletedPaper() {
        return deleteCompletedPaper;
    }

    /**
     * @return the exportProgressReport
     */
    public JButton getExportProgressReport() {
        return exportProgressReport;
    }
    
    public JButton getLogOut(){
        return logOut;
    }
    
    public JButton getCloseApp(){
        return closeApp;
    }
    
}
