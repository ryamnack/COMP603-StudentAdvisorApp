/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

import java.io.IOException;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author calum
 */
public class AdvisorController {

    private final MainFrame frame;
    private final StudentDAO studentDAO;
    private final CompletedDAO completedDAO;
    private final Programme programme;
    private Student student;

    //contructor
    public AdvisorController(MainFrame frame, PaperDAO paperDAO, StudentDAO studentDAO,
            CompletedDAO completedDAO) {

        this.frame = frame;
        this.studentDAO = studentDAO;
        this.completedDAO = completedDAO;

        programme = new Programme("Software Development");
        for (Paper p : paperDAO.getAllPapers()) {
            programme.addPaper(p);
        }

        frame.getLoginPanel().getContinueButton().addActionListener(e -> handleLogin());
        frame.getMenuPanel().getLogOut().addActionListener(e -> handleLogOut());
        frame.getMenuPanel().getCloseApp().addActionListener(e -> handleExit());
        frame.getMenuPanel().getMarkPapersCompleted().addActionListener(e -> handleMarkPapersCompleted());
    }

    //login controller
    private void handleLogin() {
        String name = frame.getLoginPanel().getNameInput();

        //error handling for empty name field
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please Enter your name.", "Missing Name",
                    JOptionPane.ERROR_MESSAGE);
            return;

        }

        //error handling for name with invalid characters
        if (!name.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(frame, "Please enter your name.", "Invalid name",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        student = studentDAO.findStudent(name);

        //if student doesnt already exist in DB, ask for year level
        if (student == null) {
            Integer year = askForYearLevel();
            if (year == null) {
                return;
            }
            student = new Student(name, year); //create new student object with input details
            if (!studentDAO.insertStudent(student)) {
                JOptionPane.showMessageDialog(frame, "Couldn't save student details. Try Again.",
                        "Database error", JOptionPane.ERROR_MESSAGE);
                student = null;
                return;
            }
        }

        Set<Paper> completed = completedDAO.getCompletedPapers(name);
        student.setPathway(new Pathway(programme, completed));
        JOptionPane.showMessageDialog(frame, "Welcome, " + name + " (Year " + student.getYearLevel() + ").");
        frame.showCard(MainFrame.MENU);
    }

    private Integer askForYearLevel() {
        String input = JOptionPane.showInputDialog(frame, "Enter your year level (1-3):");
        if (input == null) {
            return null;
        }
        try {
            int year = Integer.parseInt(input.trim());
            if (year >= 1 && year <= 3) {
                return year;
            }

        } catch (NumberFormatException e) {
            //fall through to error message below
        }
        JOptionPane.showMessageDialog(frame, "Year level must be a number from 1 to 3.", "Invalid year",
                JOptionPane.ERROR_MESSAGE);
        return null;
    }

    //logs the current student out and returns to login screen
    private void handleLogOut() {
        student = null;
        frame.getLoginPanel().clear(); //empties name field
        frame.showCard(MainFrame.LOGIN); //gos to login panel
    }

    //closes database and app
    private void handleExit() {
        int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            DBManager.getInstance().close(); //closes db conn
            System.exit(0); //end programme
        }
        //if clicked no nothing happens
    }

    //lets student mark papers as completed
    private void handleMarkPapersCompleted() {
        String input = JOptionPane.showInputDialog(frame, "Enter the codes of the papers you've completed (Separated by commas):"); //receive input
        if (input == null) { //pressed cancel
            return;
        }
        if (input.trim().isEmpty()) { //typed nothing
            JOptionPane.showMessageDialog(frame, "Please enter atleast one paper code.", "Nothing entered", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //for result message
        String marked = "";
        String alreadyDone = "";
        String notFound = "";

        for (String code : input.split(",")) { //split and trim codes
            code = code.trim();
            if (code.isEmpty()) { //skip blanks
                continue;
            }
            Paper paper = programme.findPaper(code);
            {
                if (paper == null) {
                    notFound += code + " ";
                } else if (student.getPathway().hasCompleted(paper)) {
                    alreadyDone += code + " ";
                } else if (completedDAO.addCompleted(student.getName(), paper.getCourseCode())) {
                    student.getPathway().addCompletedPaper(paper); //updates memory if db saved successfully
                    marked += paper.getCourseCode() + " ";
                } else {
                    notFound += code + " (couldnt save)";
                }
            }
        }
        
        //summary message
        String message = "";
        if(!marked.isEmpty()){
            message += "Marked as completed: " + marked;
        }
        if(!alreadyDone.isEmpty()){
            message += "\nAlready completed: " + alreadyDone;
        }
        if(!notFound.isEmpty()){
            message += "\nNot found: " + notFound + "\n";
        }
        JOptionPane.showMessageDialog(frame, message);
    }

    public static void main(String[] args) {
        new PaperSeeder().seedIfEmpty("Paper_Data/papers.txt");
        MainFrame frame = new MainFrame();
        new AdvisorController(frame, new DerbyPaperDAO(), new DerbyStudentDAO(), new DerbyCompletedDAO());
        frame.setVisible(true);
    }

}
