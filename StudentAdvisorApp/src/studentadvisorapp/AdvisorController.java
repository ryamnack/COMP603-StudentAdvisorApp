/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        frame.getMenuPanel().getCheckCanTake().addActionListener(e -> handleCheckCanTake());
        frame.getMenuPanel().getDeleteCompletedPaper().addActionListener(e -> handleDeleteCompletedPaper());
        frame.getMenuPanel().getExportProgressReport().addActionListener(e -> handleExportProgressReport());
        frame.getMenuPanel().getViewPapers().addActionListener(e -> handleViewPapers());
        frame.getPapersPanel().getBackButton().addActionListener(e -> frame.showCard(MainFrame.MENU));
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
                    alreadyDone += paper.getCourseCode() + " ";
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
        if (!marked.isEmpty()) {
            message += "Marked as completed: " + marked;
        }
        if (!alreadyDone.isEmpty()) {
            message += "\nAlready completed: " + alreadyDone;
        }
        if (!notFound.isEmpty()) {
            message += "\nNot found: " + notFound + "\n";
        }
        JOptionPane.showMessageDialog(frame, message);
    }

    //tells student if they can or cant take a paper and what prerequisites they are missing if any
    private void handleCheckCanTake() {
        String code = JOptionPane.showInputDialog(frame, "Enter paper code to check: "); //input popup
        if (code == null) { //cancel
            return;
        }
        code = code.trim(); //trim white spaces
        if (code.isEmpty()) { //error handling for blank input
            JOptionPane.showMessageDialog(frame, "Please enter a paper code.", "Nothing entered", JOptionPane.ERROR_MESSAGE);
            return;

        }

        Paper paper = programme.findPaper(code);
        if (paper == null) { //error handling for incorrect paper
            JOptionPane.showMessageDialog(frame, "No paper found with code " + code + ".", "Paper not found", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (student.getPathway().hasCompleted(paper)) { //output for if a paper is already completed
            JOptionPane.showMessageDialog(frame, "You have already completed " + paper.getCourseCode() + ".");
            return;
        }

        List<Paper> missing = student.getMissingPrerequisites(paper);
        if (missing.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Yes you can take " + paper.getCourseCode() + "."); //output for if student meets requiredments
        } else {
            String needed = "";
            for (Paper p : missing) {
                needed += p.getCourseCode() + " ";
            }
            JOptionPane.showMessageDialog(frame, "Cant yet take " + paper.getCourseCode() + " you first need to complete: " + needed); //output for if student hasnt completed prerequisites
        }
    }

    //lets the student remove a paper the marked as completed
    private void handleDeleteCompletedPaper() {
        List<String> codes = new ArrayList<>(); //make list of completed papers
        for (Paper p : student.getPathway().getPapersCompleted()) {
            codes.add(p.getCourseCode());
        }
        if (codes.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "You havnt marked any papers completed yet.");
            return;
        }
        Collections.sort(codes); //sort in order
        String[] options = codes.toArray(new String[0]);

        //show dropdown of completed papers
        Object choice = JOptionPane.showInputDialog(frame, "Choose paper to remove: ", "Remove completed paper", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == null) { //cancel
            return;
        }

        String code = (String) choice;
        Paper paper = programme.findPaper(code);

        //remove from database then memory
        if (completedDAO.removeCompleted(student.getName(), code)) {
            student.getPathway().removeCompletedPaper(paper);
            JOptionPane.showMessageDialog(frame, "Removed " + code + " from completed papers.");
        } else {
            JOptionPane.showMessageDialog(frame, "Couldnt remove " + code + ". Try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //write progress report to a file and show popup
    private void handleExportProgressReport() {
        String path = "Progress_Report/" + student.getName() + "_progress.txt";
        ExportProgressReport report = new ExportProgressReport(path, student);
        report.process();

        if (report.isSaved()) { //only produces saved message if its saved
            JOptionPane.showMessageDialog(frame, report.getReportText() + "\n\nSaved to " + path, "Progress Report", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Progress report couldnt be saved.", "Export failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    //fills papers table and opens panel
    private void handleViewPapers() {
        PapersPanel panel = frame.getPapersPanel();
        panel.clearTable(); //empty first otherwise rows pile up each time button is clicked

        for (Paper p : programme.getPapers()) {
            String prereq = "None";
            if(p.hasPrerequisite()){
                prereq = p.getPrerequisite().getCourseCode();
            }
                
            panel.addPaperRow(p.getCourseCode(), p.getCourseName(), p.getPoints(), p.getSemester(), prereq);
        }
        frame.showCard(MainFrame.PAPERS);
    }

    public static void main(String[] args) {
        new PaperSeeder().seedIfEmpty("Paper_Data/papers.txt");
        MainFrame frame = new MainFrame();
        new AdvisorController(frame, new DerbyPaperDAO(), new DerbyStudentDAO(), new DerbyCompletedDAO());
        frame.setVisible(true);
    }

}
