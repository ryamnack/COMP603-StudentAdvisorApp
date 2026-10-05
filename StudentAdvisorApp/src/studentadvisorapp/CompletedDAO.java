/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package studentadvisorapp;

import java.util.Set;

/**
 *
 * @author AUT
 */
public interface CompletedDAO {

    boolean addCompleted(String studentName, String paperCode);

    boolean removeCompleted(String studentName, String paperCode);

    Set<Paper> getCompletedPapers(String studentName);
}