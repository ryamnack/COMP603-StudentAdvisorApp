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
                            CompletedDAO completedDAO){
        
        this.frame = frame;
        this.studentDAO = studentDAO;
        this.completedDAO = completedDAO;
        
        programme = new Programme("Software Development");
        for(Paper p : paperDAO.getAllPapers()){
            programme.addPaper(p);
        }
        
        frame.getLoginPanel().getContinueButton().addActionListener(e -> handleLogin());
    }
    
    //login controller
    private void handleLogin(){
        String name = frame.getLoginPanel().getNameInput();
        
        //error handling for empty name field
        if(name.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Please Enter your name.", "Missing Name", 
                                          JOptionPane.ERROR_MESSAGE);
            return;
            
        }
        
        //error handling for name with invalid characters
        if(!name.matches("[a-zA-Z]+")){
            JOptionPane.showMessageDialog(frame, "Please enter your name.", "Invalid name",
                                          JOptionPane.ERROR_MESSAGE );
            return;
        }
        
        student = studentDAO.findStudent(name);
        
        //if student doesnt already exist in DB, ask for year level
        if(student == null){
            Integer year = askForYearLevel();
            if(year == null){
                return;
            }
            student = new Student(name, year); //create new student object with input details
            if(!studentDAO.insertStudent(student)){
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
    
    private Integer askForYearLevel(){
        String input = JOptionPane.showInputDialog(frame, "Enter your year level (1-3):");
        if(input == null){
            return null;
        }
        try{
            int year = Integer.parseInt(input.trim());
            if(year >= 1 && year <= 3){
                return year;
            }
            
        }catch(NumberFormatException e){
            //fall through to error message below
        }
        JOptionPane.showMessageDialog(frame, "Year level must be a number from 1 to 3.", "Invalid year", 
                                      JOptionPane.ERROR_MESSAGE);
        return null;
    }
        
    
    public static void main(String[] args) {
        new PaperSeeder().seedIfEmpty("Paper_Data/papers.txt");
        MainFrame frame = new MainFrame();
        new AdvisorController(frame, new DerbyPaperDAO(), new DerbyStudentDAO(), new DerbyCompletedDAO());
        frame.setVisible(true);
    }
            
            
    
}
