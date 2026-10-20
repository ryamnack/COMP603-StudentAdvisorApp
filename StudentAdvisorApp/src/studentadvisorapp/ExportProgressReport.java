/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xrf6688
 */
public class ExportProgressReport extends FileHandler { //class to inherit from FileHandler with method to output a report of points completed and remaining
        private final Student student;
        private boolean saved = false; //becomes true when file is written
        private String reportText = "";
        public ExportProgressReport (String filePath, Student student){
            super(filePath);
            this.student = student;
        }

        @Override
        public void process(){
            int pointsCompleted = student.getPathway().getPointsCompleted();
            int pointsRemaining = Programme.pointsRequired - pointsCompleted;
            List<Paper> completed = new ArrayList<>(student.getPathway().getPapersCompleted());
            
            reportText = "Progress report for " + student.getName() + "\nPoints completed: "
                    + pointsCompleted + "\nPoints Remaining: " + pointsRemaining + " (out of " + Programme.pointsRequired + ")\n\nPapers Completed:\n";
            
            if(completed.isEmpty()){
                reportText += "None";
            }else{
                for(Paper p : completed){
                    reportText += p.getCourseCode() + " - " + p.getPoints() + " points\n";
                }
            }
            new File(filePath).getParentFile().mkdirs(); //create Progress_Report if missing
            
            try(FileWriter writer = new FileWriter(filePath)){
                writer.write(reportText); //write the data to the file
                saved = true;
            } catch (IOException e) {
                handleError(e);

            }
        }
        public boolean isSaved(){
            return saved;
        }
        public String getReportText(){
            return reportText;
        }
    }
