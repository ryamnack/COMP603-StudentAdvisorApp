/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

/**
 *
 * @author AUT
 */
public class PaperSeeder {

    private PaperDAO paperDAO;

    public PaperSeeder() {
        paperDAO = new PaperDAO();
    }

    public void seedIfEmpty(String filePath) {
        if (paperDAO.countPapers() > 0) {
            return;
        }
        LoadPapers loadPapers = new LoadPapers(filePath);
        loadPapers.process();
        for (Paper paper : loadPapers.getProgramme().getPapers()) {
            paperDAO.insertPaper(paper);
        }
    }
}