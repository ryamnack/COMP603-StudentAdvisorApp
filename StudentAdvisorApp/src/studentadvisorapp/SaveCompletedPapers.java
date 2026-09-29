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
public class SaveCompletedPapers extends FileHandler { //class to inherit from FileHandler with method to output file with completed papers as a save function
        private Student student;
        public SaveCompletedPapers (String filePath, Student student){
            super(filePath);
            this.student = student;
        }
        
        @Override
        public void process(){
            try(FileWriter writer = new FileWriter(filePath)){
                String data = student.getName() + "," + student.getYearLevel();
                for(Paper p : student.getPathway().getPapersCompleted()){ //iterate through completed papers and add them to a string
                    data += "," + p.getCourseCode();
                }
                writer.write(data); //write the data to the file
            } catch (IOException e) {
                handleError(e);

            }
        }
    }