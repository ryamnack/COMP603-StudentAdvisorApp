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
 * @author xrf6688
 */
public class MainFrame extends JFrame {
    
    //names for panels
    public static final String LOGIN = "Login";
    public static final String MENU = "Menu";
    public static final String PAPERS = "Papers";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);
    private final LoginPanel loginPanel = new LoginPanel();
    private final MenuPanel menuPanel = new MenuPanel();
    private final PapersPanel papersPanel = new PapersPanel();
    
    public MainFrame() {
        setTitle("Virtual Academic Advisor");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        cards.add(loginPanel, LOGIN);
        cards.add(menuPanel, MENU);
        cards.add(papersPanel, PAPERS);
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
    
    public PapersPanel getPapersPanel(){
        return papersPanel;
    }
    
}
