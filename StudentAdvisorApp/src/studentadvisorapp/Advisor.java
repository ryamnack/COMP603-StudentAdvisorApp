
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package studentadvisorapp;

/**
 *
 * @author xrf6688
 */
import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

public class Advisor {

    private final Scanner sc = new Scanner(System.in);
    private Programme major;
    //private Program minor;
    private Student student;

    public static void main(String[] args) {
        new Advisor().run();

    }

    public void run() {

        System.out.println("___________________________");
        System.out.println("|Virtual Academic Advisor|");
        System.out.println("|________________________|");
        System.out.println("\n*Enter 'q' at any time to exit.*\n");
        LoadPapers loadPapers = new LoadPapers("Paper_Data/papers.txt");
        loadPapers.process();
        major = loadPapers.getProgramme();
        setupStudent();
        boolean running = true;
        while (running) {
            printMenu();
            String choice = readLine();
            switch (choice) {
                case "1":
                    viewPapers();
                    break;
                case "2":
                    markPaperCompleted();
                    break;
                case "3":
                    checkCanTake();
                    break;
                case "4":
                    deleteCompletedPaper();
                    break;
                case "5":
                    exportProgressReport();
                    break;
                case "6":
                    exitProgramme();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1-6.");
            }
        }
        sc.close();
    }

    private void setupStudent() {
        //getting students details
        System.out.print("Enter your name: ");
        String name = readLine();
        String path = "Completed_Papers/" + name + ".txt";
        File saveFile = new File(path); //object representing the path of the students completed papers.
        if (saveFile.exists()) {
            LoadCompletedPapers loadCompleted = new LoadCompletedPapers(path, major);
            loadCompleted.process();
            student = loadCompleted.getStudent();
            System.out.println("Welcome, " + student + "!");
            System.out.println("Programme: " + major.getCourseName());
            if (student.getPathway().getPapersCompleted().isEmpty()) {
                System.out.println("Papers Completed: None");
            } else {
                String completedCourseCodes = "";
                for (Paper p : student.getPathway().getPapersCompleted()) {

                    completedCourseCodes += p.getCourseCode() + ", ";
                }
                completedCourseCodes = completedCourseCodes.substring(0, completedCourseCodes.length() - 2); //removes the comma at the end of the line
                System.out.println("Completed Papers: " + completedCourseCodes);
            }
        } else {
            System.out.print("Enter your year level (1-3): ");
            Integer year = readInt();
            student = new Student(name, year);
            student.setPathway(new Pathway(major, new HashSet<>()));
            System.out.println("Welcome, " + student + "!");
        }

    }

    private String readLine() { //method to read input and exit at any time if input = 4;
        String input = sc.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
            exitProgramme();
        }
        return input;
    }

    private void exitProgramme() { //method to save student details and exit programme
        if (student != null) { //only saves if student has been created yet
            SaveCompletedPapers saveCompletedPapers = new SaveCompletedPapers("Completed_Papers/" + student.getName() + ".txt", student);
            saveCompletedPapers.process();
            System.out.println("Goodbye, " + student.getName() + "!");

        } else {
            System.out.println("Goodbye!");
        }
        System.exit(0);

    }

    private int readInt() { //method to read year level that checks if its an integer
        while (true) {
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("q")) {
                exitProgramme();
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }

    }

    private void printMenu() { //visual menu
        System.out.println("\n1. View available papers");
        System.out.println("2. Mark a paper as completed");
        System.out.println("3. Check if I can take a paper");
        System.out.println("4. Delete a completed paper");
        System.out.println("5. Export progress report");
        System.out.println("6. Quit");
        System.out.print("Enter choice: ");
    }

    public void viewPapers() { //prints list of papers
        System.out.println("\n" + major.getCourseName() + "papers: ");
        for (Paper p : major.getPapers()) {
            System.out.println(p);
        }
    }

    public void markPaperCompleted() { //adds a paper to the completed papers object
        System.out.print("Enter all the codes of papers you've completed separated by commas: ");
        String input = readLine();
        String[] codes = input.split(",");
        for (String code : codes) {
            Paper paper = major.findPaper(code.trim());
            if (paper == null) {
                System.out.println("No such paper found: " + code);
                continue;
            }
            student.getPathway().addCompletedPaper(paper);
            System.out.println("Marked " + paper.getCourseCode() + " as completed.");
        }
    }

    public void exportProgressReport() { //writes out a progress report containing summary of points completed compared to points required to graduate
        ExportProgressReport report = new ExportProgressReport("Progress_Report/" + student.getName() + "_progress.txt", student);
        report.process();
        System.out.println("Progress report saved for " + student.getName() + ".");
    }

    public void deleteCompletedPaper() { //undoes marking a paper completed, in case it was a mistake
        System.out.print("Enter the code of the completed paper to delete: ");
        String code = readLine();
        Paper paper = major.findPaper(code);
        if (paper == null) {
            System.out.println("No such paper found: " + code);
            return;
        }
        if (!student.getPathway().hasCompleted(paper)) {
            System.out.println(paper.getCourseCode() + " isn't marked as completed.");
            return;
        }
        student.getPathway().removeCompletedPaper(paper);
        System.out.println("Deleted " + paper.getCourseCode() + " from your completed papers.");
    }

    public void checkCanTake() { //check if student has fullfilled prerequisites
        System.out.print("Enter paper code to check: ");
        String code = readLine();
        Paper paper = major.findPaper(code);
        if (paper == null) {
            System.out.println("No such paper found: " + code);
            return;
        }
        if (student.canTake(paper)) {
            System.out.println("Yes, you can take " + paper.getCourseCode() + ".");
        } else {
            String missingCodes = ""; //builds up every not-yet-completed paper in the prereq chain, not just the immediate one
            for (Paper p : student.getMissingPrerequisites(paper)) {
                missingCodes += p.getCourseCode() + ", ";
            }
            missingCodes = missingCodes.substring(0, missingCodes.length() - 2);
            System.out.println("No — you need to complete " + missingCodes + " first.");
        }
    }

}
