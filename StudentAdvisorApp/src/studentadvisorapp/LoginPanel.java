/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author xrf6688
 */
public class LoginPanel extends JPanel{
    
    private final JTextField nameField = new JTextField(15);
    private final JButton continueButton = new JButton("Continue");
    
    public LoginPanel(){
        setLayout(new GridBagLayout());
        JPanel form = new JPanel(new FlowLayout());
        form.add(new JLabel("Enter your name:"));
        form.add(nameField);
        form.add(continueButton);
        add(form);
    }
    
    //for controller
    public String getNameInput() {
        return nameField.getText().trim();
    }
    
    public JButton getContinueButton(){
        return continueButton;
    }
    
    public void clear(){ //for logout
        nameField.setText("");
    }
}
