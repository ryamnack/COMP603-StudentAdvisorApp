/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author xrf6688
 */
public class PapersPanel extends JPanel{
    private final DefaultTableModel tableModel; //holds the table's data
    private final JButton backButton = new JButton("Back to Menu");

    public PapersPanel() {
        setLayout(new BorderLayout(10, 10)); //gaps between the areas
        add(new JLabel("Software Development Papers", JLabel.CENTER), BorderLayout.NORTH);

        String[] columns = {"Code", "Name", "Points", "Semester", "Prerequisite"};
        tableModel = new DefaultTableModel(columns, 0) { //start with no rows
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //stop users typing into the table
            }
        };
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER); 
        JPanel bottom = new JPanel();                     
        bottom.add(backButton);
        add(bottom, BorderLayout.SOUTH);
    }

    //controller uses to clear tabel
    public void clearTable() {
        tableModel.setRowCount(0); //remove all rows
    }

    //controller uses to adds each papers' row
    public void addPaperRow(String code, String name, int points, String semester,
                            String prereq) {
        tableModel.addRow(new Object[]{code, name, points, semester, prereq});
    }

    public JButton getBackButton() {
        return backButton;
    }
}    
    

