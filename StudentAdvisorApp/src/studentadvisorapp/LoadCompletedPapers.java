package studentadvisorapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class LoadCompletedPapers extends FileHandler {

    private Student student;
    private Programme major;

    public LoadCompletedPapers(String filePath, Programme major) {
        super(filePath);
        this.major = major;
    }

    @Override
    public void process() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) { //reading file holding papers
            String line = reader.readLine();
            line = line.trim(); //trims white spaces

            String[] parts = line.split(",");
            String name = parts[0];
            int year = Integer.parseInt(parts[1]);
            Student loadedStudent = new Student(name, year);

            Pathway pathway = new Pathway(major, new HashSet<>());
            if (parts.length > 2) {
                for (int i = 2; i < parts.length; i++) {
                    Paper paper = major.findPaper(parts[i]);
                    if (paper != null) {
                        pathway.addCompletedPaper(paper);
                    }
                }
            }

            loadedStudent.setPathway(pathway);
            this.student = loadedStudent;

        } catch (IOException e) {
            handleError(e);
        }
    }

    public Student getStudent() { //required so the advisor app can retrieve the student details before the 'process' method finishes running and loses it
        return student;
    }
}
