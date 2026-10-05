/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentadvisorapp;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author calum
 */
public class MainFrame extends JFrame {
    
    //names for panels
    public static final String LOGIN = "Login";
    public static final String MENU = "Menu";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);
    private final LoginPanel loginPanel = new LoginPanel();
    private final MenuPanel menuPanel = new MenuPanel();
    
    public MainFrame() {
        setTitle("Virtual Academic Advisor");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        cards.add(loginPanel, LOGIN);
        cards.add(menuPanel, MENU);
        add(cards);
    }
    
    public void showCard(String name){
        cardLayout.show(cards, name);
        
    }
    
    public LoginPanel getLoginPanel(){
        return loginPanel;
    }

    public MenuPanel getMenuPanel(){
        return menuPanel;
    }
    
}
