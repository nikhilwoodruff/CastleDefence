/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;

import javax.swing.JLabel;

/**
 *
 * @author 12lstephens
 */
public class DiceThread implements Runnable{
    
    JLabel diceLabel;
    
    public DiceThread(JLabel passedDiceLabel){
    
    diceLabel = passedDiceLabel;
    }
    
    public void run(){
    
        for (int i = 0; i < 12; i++) {
            
            diceLabel.setText(String.valueOf((int)(Math.random()*6+1)));
            
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
            
        }
    
    
    
    
    
    
    }
    
    
    
    
}
