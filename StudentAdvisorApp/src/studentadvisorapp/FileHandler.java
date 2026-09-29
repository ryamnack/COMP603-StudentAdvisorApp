/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

import java.io.IOException;

/**
 *
 * @author calum
 */
public abstract class FileHandler{ //to handle input and output of files
        protected String filePath;
        public FileHandler(String filePath){
            this.filePath = filePath;
        }
        
        public abstract void process();
        
        protected void handleError(IOException e){
            System.out.println("Error handling file: " + e.getMessage());
        }
        
    }
