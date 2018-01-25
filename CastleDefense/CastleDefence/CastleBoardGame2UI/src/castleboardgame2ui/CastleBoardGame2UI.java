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
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
    
    
    //ANIMATION VALUES
    int moveSpeed = 500; //Time in ms for piece to move
    int highlighterSpeed = 500; //Time in ms for highlighters to come and go
    int turn;
    
     JFrame frame = new JFrame();
    
    public CastleBoardGame2UI(){        
      
        
        //Creates a new Jframe
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
        //test
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
//        BufferedImage imgL = null;
//        try {
//            imgL = ImageIO.read(new File("H:\\Downloads\\Computing\\images (2).jpg"));
//        } catch (IOException e) {
//        }
//        Image dimgL = imgL.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//        ImageIcon leatherImage = new ImageIcon(dimgL);

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
       int moving = 0;
       ArrayList<MovementAnimation> anim = new ArrayList<>();
       ArrayList<MovementAnimation> toRemove = new ArrayList<>();
    public JPanel RefreshPanel1() {
        MovementAnimation.enableAnimation(anim, toRemove);
        //creates a new Jpanel
        JPanel panel = new JPanel();
        //creates the variables needed 
        JLabel dirt = new JLabel("");
        JLabel grass = new JLabel("");
        JLabel[] stone = new JLabel[100];
        ArrayList<JLabel> pieces = new ArrayList<>();
        ArrayList<JLabel> highlighters = new ArrayList<>();
        
        int[][] directions = new int[][] {{1, 1}, {0, 1}, {-1, 1}, {1, 0}, {-1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        
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
        
        //load counter images
        BufferedImage imgC = null;
        try {
            imgC = ImageIO.read(CastleBoardGame2UI.class .getResourceAsStream("/Resorces/red.png"));
        } catch (IOException e) {
        }
        Image dimgC = imgC.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon CounterImage = new ImageIcon(dimgC);
        BufferedImage imgC2 = null;
        try {
            imgC2 = ImageIO.read(CastleBoardGame2UI.class .getResourceAsStream("/Resorces/blue.png"));
        } catch (IOException e) {
        }
        Image dimgC2 = imgC2.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon CounterImage2 = new ImageIcon(dimgC2);
        //load highlighter image
        BufferedImage imgH = null;
        try {
            imgH = ImageIO.read(CastleBoardGame2UI.class .getResourceAsStream("/Resorces/highlight.png"));
        } catch (IOException e) {
        }
        Image dimgH = imgH.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon HighlightImage = new ImageIcon(dimgH);
        
        BufferedImage imgDi = null;
        try {
            imgDi = ImageIO.read(CastleBoardGame2UI.class .getResourceAsStream("/Resorces/DiceImage2.png"));
        } catch (IOException e) {
        }
        Image dimgDi = imgDi.getScaledInstance(50, 920, Image.SCALE_SMOOTH);
        ImageIcon DiceImage = new ImageIcon(imgDi);
        //create pieces
        for(int i = 0; i < 5; i++)
        {
            pieces.add(new JLabel());
            pieces.get(pieces.size() - 1).setVisible(true);
            pieces.get(pieces.size() - 1).setIcon(CounterImage);
            pieces.get(pieces.size() - 1).setLocation(50 * (i+1), 50);
            pieces.get(pieces.size() - 1).setSize(50, 50);
            pieces.add(new JLabel());
            pieces.get(pieces.size() - 1).setVisible(true);
            pieces.get(pieces.size() - 1).setIcon(CounterImage2);
            pieces.get(pieces.size() - 1).setLocation(50 * (i+11), 50);
            pieces.get(pieces.size() - 1).setSize(50, 50);
        }
        //create highlighters
        for(int j = 0; j < 8; j++)
        {
            highlighters.add(new JLabel());
            highlighters.get(highlighters.size() - 1).setVisible(true);
            highlighters.get(highlighters.size() - 1).setIcon(HighlightImage);
            highlighters.get(highlighters.size() - 1).setLocation(50 * (j+1), 250);
            highlighters.get(highlighters.size() - 1).setSize(50, 50);
        }
        //create dice
        JLabel dice = new JLabel();
        dice.setBackground(new Color(0, 0, 0, 0));
        dice.setVisible(true);
        dice.setIcon(DiceImage);
        dice.setLocation(1000, 0);
        dice.setSize(50, 920);
        panel.add(dice);
        //give the pieces behaviour
        int count1 = 0;
        for(JLabel piece : pieces)
        {
            final int countFinal = count1;
            MouseListener ml = new MouseListener() {
                @Override
                protected Object clone() throws CloneNotSupportedException {
                    return super.clone();
                }
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if(pieces.indexOf(piece) % 2 == 0)
                    {
                        
                    }
                    moving = countFinal;
                    setUpHighlighters(pieces, moving, highlighters, anim, directions);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            };
            count1++;
            piece.addMouseListener(ml);
        }
        int count = 0;
        for(JLabel label : highlighters)
        {
//            label.setLocation(625, 1100);
            label.setLocation(250, 250);
            final int countFinal = count;
            MouseListener ml = new MouseListener() {
                @Override
                protected Object clone() throws CloneNotSupportedException {
                    return super.clone();
                }
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt){
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //When a direction pointer is clicked...
                    if(dice.getLocation().y < 0)
                    {
                        dice.setLocation(1000, 0);
                    }
                    int rand = (int) Math.floor(Math.random() * 6);
                    MovementAnimation.newAnimation(anim, dice, 0, -(rand * 51 + 615), 2000);
                    JLabel target = pieces.get(moving);
                    for(JLabel label : highlighters)
                    {
                        //Pack away all pointers
                        MovementAnimation.newAnimation(anim, label, 625 - label.getLocation().x, 1100 - label.getLocation().y, highlighterSpeed);
                    }
                    //Move the piece
                    MovementAnimation.newAnimation(anim, target, -directions[countFinal][0] * 50, -directions[countFinal][1] * 50, moveSpeed);
                    Point proxy = target.getLocation();
                    proxy.translate(-directions[countFinal][0] * 50, -directions[countFinal][1] * 50);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            };
            count++;
            label.addMouseListener(ml);
        }
        //set the layout to null
        panel.setLayout(null);
        for(JLabel piece : pieces)
        {
            panel.add(piece);
        }
        for(JLabel highlighter : highlighters)
        {
            panel.add(highlighter);
        }
        
        //adds a new mouse listener that enables interactions with the board tiles when clicked
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
    public void setUpHighlighters(ArrayList<JLabel> pieces, int moving, ArrayList<JLabel> highlighters, ArrayList<MovementAnimation> anim, int[][] directions)
    {
        JLabel target = pieces.get(moving);
        int count = 0;
        for(JLabel pointer : highlighters)
        {
            MovementAnimation.newAnimation(anim, pointer, target.getLocation().x - pointer.getLocation().x - (directions[count][0] * 50), target.getLocation().y - 30 - pointer.getLocation().y - (directions[count][1] * 50) + 30, highlighterSpeed);
            count++;
        }
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
