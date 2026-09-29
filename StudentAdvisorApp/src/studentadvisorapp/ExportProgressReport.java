/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author calum
 */
public class ExportProgressReport extends FileHandler { //class to inherit from FileHandler with method to output a report of points completed and remaining
        private Student student;
        public ExportProgressReport (String filePath, Student student){
            super(filePath);
            this.student = student;
        }

        @Override
        public void process(){
            try(FileWriter writer = new FileWriter(filePath)){
                int pointsCompleted = student.getPathway().getPointsCompleted();
                int pointsRemaining = Programme.pointsRequired - pointsCompleted; //points left to reach 360
                String data = "Progress Report for " + student.getName() + "\n";
                data += "Points Completed: " + pointsCompleted + "\n";
                data += "Points Remaining: " + pointsRemaining + " (out of " + Programme.pointsRequired + ")";
                writer.write(data); //write the data to the file
            } catch (IOException e) {
                handleError(e);

            }
        }
    }
