/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

import java.util.List;

/**
 *
 * @author AUT
 */
public interface PaperDAO {

    boolean insertPaper(Paper paper);

    Paper findPaper(String code);

    List<Paper> getAllPapers();

    int countPapers();
}