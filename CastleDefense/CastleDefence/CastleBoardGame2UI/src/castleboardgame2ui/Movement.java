/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleboardgame2ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import castleboardgame2ui.MainPanel;

/**
 *
 * @author 12nwoodruff
 */
public class Movement{

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        Movement m = new Movement();
    }
    public static JFrame jf = new JFrame();
    public Movement()
    {
        MainPanel mp = new MainPanel();
        jf.add(mp);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(1250, 1050);
        jf.setVisible(true);
    }
    
}
