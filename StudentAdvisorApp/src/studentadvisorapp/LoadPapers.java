package studentadvisorapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

//class to inherit from FileHandler with method to read the master papers file and build the programme
public class LoadPapers extends FileHandler {

    private Programme programme;

    public LoadPapers(String filePath) {
        super(filePath);
    }

    @Override
    public void process() {

        programme = new Programme("Software Developement");
        HashMap<String, String> prereqMap = new HashMap(); //collection to hold prereq data untill all paper objects are created

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) { //reading file holding papers
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); //trims white spaces
                if (line.isEmpty()) {
                    continue; //skips blank lines
                }
                String[] parts = line.split(","); //separates each field of paper
                if (parts.length != 5) { //error handling
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }
                String code = parts[0];
                String name = parts[1];
                int points = Integer.parseInt(parts[2]);
                String semester = parts[3];
                String prereqCode = parts[4];

                Paper paper = new Paper(code, name, points, semester, null); //create paper object
                programme.addPaper(paper);

                if (!prereqCode.equals("NONE")) {
                    prereqMap.put(code, prereqCode); //record papers prereq so it can be added once all papers are in memory
                }

            }
        } catch (IOException e) {
            handleError(e);
        }
        for (String key : prereqMap.keySet()) {
            Paper paper = programme.findPaper(key); //paper that needs prereq
            Paper prereqPaper = programme.findPaper(prereqMap.get(key)); //the prereq of the paper

            paper.setPrerequisite(prereqPaper); //setting the prereq
        }

        System.out.println("Loaded " + programme.getPapers().size() + " papers.");

    }

    public Programme getProgramme() { //required so the advisor app can retrieve the built programme once process has finished
        return programme;
    }
}
