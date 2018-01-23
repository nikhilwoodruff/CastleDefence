/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleboardgame2ui;

import castleboardgame2ui.Resorces.DiceThread;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author 12lstephens
 */
public class CastleBoardGame2UI {

     JFrame frame = new JFrame();
    
    public CastleBoardGame2UI(){        
      
        
          //Creates a new Jframe
       //HELLO from Nikhil
        //HI

        //adds the panels to the frame and sets the size of the frame
        frame.add(RefreshPanel2(null));
        frame.add(RefreshPanel1());
        frame.setSize(1250, 1025);

        //sets the default operation of the frame and set it to be visible to the user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
         // RefreshFrame();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CastleBoardGame2UI cbg = new CastleBoardGame2UI();
    }

    static String rollTheDice() {

        //declares and intialises the variables needed
        String diceNumber = "";
        Integer diceInt = 0;
        Random rand = new Random();

        //generates a random interger from 1 to 6
        diceInt = rand.nextInt(6) + 1;
        System.out.println("Hello");

        // passes the interger to a string
        diceNumber = diceInt.toString();

        //returns the string value
        return diceNumber;
    }

    public JPanel RefreshPanel2(String overide) {

        //creates a new JPanel 
        JPanel panel2 = new JPanel();

        //creates and itialises the needed JLabels
        JLabel text = new JLabel("Status");
        Button Dice = new Button("Roll the dice");
        JLabel diceLabel = new JLabel("0");
        JLabel CombatStrenght = new JLabel("PLACEHOLDER");
        BufferedImage imgL = null;
        try {
            imgL = ImageIO.read(new File("H:\\Downloads\\Computing\\images (2).jpg"));
        } catch (IOException e) {
        }
        Image dimgL = imgL.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon leatherImage = new ImageIcon(dimgL);

        //sets up the settings of the Panel
        panel2.setSize(new Dimension(200, 1000));
        panel2.setBackground(Color.lightGray);
        panel2.setLocation(1050, 0);

        //sets the sizes of the JLables
        Dice.setMaximumSize(new Dimension(200, 50));
        Dice.setMinimumSize(new Dimension(200, 50));

        //adds the JLables to the Panel
        panel2.add(text);
        panel2.add(Dice);
        panel2.add(diceLabel);
        panel2.add(CombatStrenght);

        //sets the Panel layout to BoxLayout
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));

        //adds a new action listener that cretates a new thread when the button is pressed to activate the mouse throwing animation
        Dice.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent ae) {

                Button btn = (Button) ae.getSource();
                DiceThread dt = new DiceThread(diceLabel);
                Thread t = new Thread(dt);

                t.start();
                

                
                 HandleSound(CastleBoardGame2UI.class .getResourceAsStream("/Resorces/DiceSound.wav"));
                //sound.HandleSound(new File("DiceSound.wav"));

            }
        });

        //returns the value of the Panel
        return panel2;

    }

     void RefreshFrame() {

         
      
        frame.repaint();
        frame.validate();
    }
       
    public JPanel RefreshPanel1() {

        //creates a new Jpanel
        JPanel panel = new JPanel();

        //creates the variables needed 
        JLabel dirt = new JLabel("");
        JLabel grass = new JLabel("");
        JLabel[] stone = new JLabel[100];
        int boardWidth = 21;
        int boardHeight = 20;
        
       
       
        
        //loads dirt image
        BufferedImage imgD = null;    
        try {
            imgD = ImageIO.read((CastleBoardGame2UI.class.getResourceAsStream("/Resorces/DirtTexture.jpg")));
        } catch (Exception e) {
            System.out.println(e);
        }
        Image dimgD = imgD.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon dirtImage = new ImageIcon(dimgD);

        //loads stone image
        BufferedImage imgG = null;
        try {
            imgG = ImageIO.read(CastleBoardGame2UI.class .getResourceAsStream("/Resorces/GrassTexture.jpg"));
        } catch (IOException e) {
        }
        Image dimgG = imgG.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon grassImage = new ImageIcon(dimgG);

        //loads stone image
        BufferedImage imgS = null;
        try {
            imgS = ImageIO.read(CastleBoardGame2UI.class .getResourceAsStream("/Resorces/StoneTexture.jpg"));
        } catch (IOException e) {
        }
        Image dimgS = imgS.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon stoneImage = new ImageIcon(dimgS);

        //loads castle interior iamge
        BufferedImage imgI = null;
        try {
            imgI = ImageIO.read(CastleBoardGame2UI.class .getResourceAsStream("/Resorces/InternalTexture.jpg"));
        } catch (IOException e) {
        }
        Image dimgI = imgI.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon InteriorImage = new ImageIcon(dimgI);

        //set the layout to null
        panel.setLayout(null);

        //adds a new mouse lisnter that enables interactions with the board tiles when clicked
        MouseListener m1 = new MouseListener() {
            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            public void mouseClicked(java.awt.event.MouseEvent evt) {

            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {

                JLabel label = (JLabel) e.getSource();

                //label.setBackground(Color.red);
                System.out.println(label.getLocation());

                HandleSound(CastleBoardGame2UI.class .getResourceAsStream("/Resorces/ClickSound.wav"));

               // sound.HandleSound(CastleBoardGame2UI.class .getResourceAsStream("/Resorces/ClickSound.wav"));

            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {

            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {

            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {

            }

        };

        //for loop which intialises the JLable array as well as setting their size color and location on the JPanel
        for (int i = 0; i < boardHeight; i++) {

            for (int j = 0; j < boardWidth; j++) {
                stone[i] = new JLabel();
                stone[i].setOpaque(true);

                if (i >= 5 && i < 15) {

                    if ((j == 5) || (j == 15)) {

                        //stone color
                        stone[i].setIcon(stoneImage);
                    } else {

                        Integer diceInt = 0;
                        Random rand = new Random();

                        diceInt = rand.nextInt(10) + 1;

                        if (diceInt > 2) {
                            //grass color
                            stone[i].setIcon(grassImage);
                        } else {
                            //dirt color
                            stone[i].setIcon(dirtImage);
                        }

                        if ((i >= 5 && i < 15) && j >= 6 && j < 15) {
                            //caslte color
                            stone[i].setIcon(InteriorImage);

                        }

                        //sets the color of the walls
                        if (i == 5 && j < 15 && j >= 5) {
                            //stone color
                            stone[i].setIcon(stoneImage);
                        }

                        if (i == 14 && j < 15 && j >= 5) {
                            //stone color
                            stone[i].setIcon(stoneImage);
                        }
                        //sets the color of the keep
                        if ((i == 9 || i == 10) && j == 10) {
                            //stone color
                            stone[i].setIcon(stoneImage);
                        }

                    }

                } else {
                    Integer diceInt = 0;
                    Random rand = new Random();

                    diceInt = rand.nextInt(10) + 1;

                    if (diceInt > 2) {
                        //grass color
                        stone[i].setIcon(grassImage);
                    } else {

                        //dirt color
                        stone[i].setIcon(dirtImage);
                    }

                }

                stone[i].setLocation(j * 50, i * 50);
                stone[i].setSize(new Dimension(50, 50));
//                stone[i].setBorder(BorderFactory.createLineBorder(Color.black));
                stone[i].addMouseListener(m1);
                panel.add(stone[i]);
            }

        }

        //returns the panel
        return panel;
    }

    public void HandleSound(InputStream file) {

        System.out.println("castleboardgame2ui.CastleBoardGame2UI.HandleSound()");
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

            Thread.sleep(100);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
