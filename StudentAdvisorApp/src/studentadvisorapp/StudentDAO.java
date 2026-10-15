/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package studentadvisorapp;

import java.util.List;

/**
 *
 * @author AUT
 */
public interface StudentDAO {

    boolean insertStudent(Student student);

    boolean updateYearLevel(Student student);

    Student findStudent(String name);

    List<Student> getAllStudents();
}