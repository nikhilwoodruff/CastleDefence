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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
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
    int moving = 0;
    int currentTeam = 0;
    int[] numberOfMoves = {5, 5};
    ArrayList<MovementAnimation> anim = new ArrayList<>();
    ArrayList<MovementAnimation> toRemove = new ArrayList<>();
    JFrame frame = new JFrame();
    ArrayList<JLabel> pieces = new ArrayList<>();
    ArrayList<JLabel> highlighters = new ArrayList<>();
    int[][] directions = new int[][] {{1, 1}, {0, 1}, {-1, 1}, {1, 0}, {-1, 0}, {1, -1}, {0, -1}, {-1, -1}};
    
    public CastleBoardGame2UI(){     
        //Creates a new JFrame
        //Adds the panels to the frame and sets the size of the frame
        frame.add(RefreshPanel2(null));
        frame.add(RefreshPanel1());
        frame.setSize(1250, 1025);
        //Sets the default operation of the frame and set it to be visible to the user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        //RefreshFrame();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CastleBoardGame2UI cbg = new CastleBoardGame2UI();
    }
    // TO REPLACE WITH ANIMATED DICE...
    static String rollTheDice() {
        //Declares and intialises the variables needed
        String diceNumber = "";
        Integer diceInt = 0;
        Random rand = new Random();
        //Generates a random interger from 1 to 6
        diceInt = rand.nextInt(6) + 1;
        System.out.println("Hello");
        //Passes the interger to a string
        diceNumber = diceInt.toString();
        //Returns the string value
        return diceNumber;
    }
    public JPanel RefreshPanel2(String overide) {
        //Creates a new JPanel 
        JPanel panel2 = new JPanel();
        panel2.setLayout(null);
        //Creates and initialises the needed JLabels
        //JLabel text = new JLabel("Status");
        //text.setLocation(25, 25);
        //text.setSize(50, 50);
        Button Dice = new Button("Roll the dice");
        Dice.setLocation(50, 25);
        Dice.setSize(150, 50);
        JLabel concealer1 = new JLabel("test");
        concealer1.setSize(50, 869);
        concealer1.setLocation(100, 151);
        concealer1.setIcon(readImage("grey.png", 50, 869));
        JLabel concealer2 = new JLabel("test");
        concealer2.setSize(50, 100);
        concealer2.setLocation(100, 0);
        concealer2.setIcon(readImage("grey.png", 50, 100));
        //JLabel diceLabel = new JLabel("0");
        //diceLabel.setLocation(75, 25);
        //diceLabel.setSize(50, 50);
        //JLabel CombatStrength = new JLabel("PLACEHOLDER");
        //CombatStrength.setLocation(100, 25);
        //CombatStrength.setSize(50, 50);
//        BufferedImage imgL = null;
//        try {
//            imgL = ImageIO.read(new File("H:\\Downloads\\Computing\\images (2).jpg"));
//        } catch (IOException e) {
//        }
//        Image dimgL = imgL.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//        ImageIcon leatherImage = new ImageIcon(dimgL);
        //Sets up the settings of the Panel
        panel2.setSize(new Dimension(200, 1000));
        panel2.setBackground(new Color(195, 195, 195));
        panel2.setLocation(1050, 0);
        //Sets the sizes of the JLabels
        Dice.setMaximumSize(new Dimension(200, 50));
        Dice.setMinimumSize(new Dimension(200, 50));
        //Adds the JLables to the Panel
        //panel2.add(text);
        panel2.add(Dice);
//        panel2.add(diceLabel);
//        panel2.add(CombatStrength);
        //Loads dice image
        ImageIcon DiceImage = readImage("DiceImage2.png", 50, 920);
        JLabel dice = new JLabel();
        dice.setBackground(new Color(0, 0, 0, 0));
        dice.setVisible(true);
        dice.setIcon(DiceImage);
        dice.setLocation(100, 100);
        dice.setSize(50, 920);
        panel2.add(concealer2);
        panel2.add(concealer1);
        panel2.add(dice);
        //Sets the Panel layout to BoxLayout
        
        //panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
        //Adds a new action listener that cretates a new thread when the button is pressed to activate the mouse throwing animation
        Dice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Button btn = (Button) ae.getSource();
                //DiceThread dt = new DiceThread(diceLabel);
                //Thread t = new Thread(dt);
                //t.start();                          
                HandleSound(CastleBoardGame2UI.class .getResourceAsStream("/Resorces/DiceSound.wav"));
                if(dice.getLocation().y < 0)
                    {
                        dice.setLocation(100, 100);
                    }
                    int rand = (int) Math.floor(Math.random() * 6);
                    MovementAnimation.newAnimation(anim, dice, 0, -(rand * 51 + 615), 2000);
                    System.out.println(rand + 1);
                //Sound.HandleSound(new File("DiceSound.wav"));
            }
        });
        //Returns the value of the JPanel
        return panel2;
    }
     void RefreshFrame() {
        frame.repaint();
        frame.validate();
    }
    public JPanel RefreshPanel1() {
        MovementAnimation.enableAnimation(anim, toRemove);
        //Creates a new JPanel
        JPanel panel = new JPanel();
        //Creates the variables needed 
        JLabel dirt = new JLabel("");
        JLabel grass = new JLabel("");
        JLabel[] stone = new JLabel[100];
        int boardWidth = 21;
        int boardHeight = 20;
        //Loads dirt image
        ImageIcon dirtImage = readImage("DirtTexture.jpg", 50, 50);
        //Loads stone image
        ImageIcon grassImage = readImage("GrassTexture.jpg", 50, 50);
        //Loads stone image
        ImageIcon stoneImage = readImage("StoneTexture.jpg", 50, 50);
        //Loads castle interior iamge
        ImageIcon InteriorImage = readImage("InternalTexture.jpg", 50, 50);
        //Loads counter images
        ImageIcon CounterImage = readImage("red.png", 50, 50);
        ImageIcon CounterImage2 = readImage("blue.png", 50, 50);
        //Loads highlighter image
        ImageIcon HighlightImage = readImage("highlight.png", 50, 50);
        //Create highlighters
        for(int j = 0; j < 8; j++)
        {
            highlighters.add(new JLabel());
            highlighters.get(highlighters.size() - 1).setVisible(true);
            highlighters.get(highlighters.size() - 1).setIcon(HighlightImage);
            highlighters.get(highlighters.size() - 1).setLocation(50 * (j+1), 250);
            highlighters.get(highlighters.size() - 1).setSize(50, 50);
        }
        //Create pieces
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
        //Give the pieces behaviour (this is a long one)
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
                public void mouseClicked(java.awt.event.MouseEvent evt){}
                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseReleased(MouseEvent e) {
                    moving = countFinal;
                    int team = (pieces.indexOf(pieces.get(moving)) % 2 == 0) ? 1 : 0;
                    if(team == currentTeam)
                    {
                        setUpHighlighters(pieces, moving, highlighters, anim, directions);
                    }
                    else
                    {
                        System.out.println(team);
                        System.out.println(currentTeam);
                        System.out.println("Not your piece!");
                    }
                }
                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}
            };
            count1++;
            piece.addMouseListener(ml);
        }
        int count = 0;
        for(JLabel label : highlighters)
        {
            label.setLocation(625, 1100);
            final int countFinal = count;
            MouseListener ml = new MouseListener() {
                @Override
                protected Object clone() throws CloneNotSupportedException {
                    return super.clone();
                }
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt){}
                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseReleased(MouseEvent e) {
                    //When a direction pointer is clicked...
                    int team = (pieces.indexOf(pieces.get(moving)) % 2 == 0) ? 1 : 0;
                        JLabel target = pieces.get(moving);
                        for(JLabel label : highlighters)
                        {
                            //Pack away all pointers
                            MovementAnimation.newAnimation(anim, label, 625 - label.getLocation().x, 1100 - label.getLocation().y, highlighterSpeed);
                        }
                        //Move the piece
                        MovementAnimation.newAnimation(anim, target, -directions[countFinal][0] * 51, -directions[countFinal][1] * 51, moveSpeed);

                        Point proxy = target.getLocation();
                        proxy.translate(-directions[countFinal][0] * 50, -directions[countFinal][1] * 50);
                        numberOfMoves[team]--;
                        if(numberOfMoves[team] < 1)
                        {
                            numberOfMoves[team] = 5;
                            currentTeam = 1 - currentTeam;
                        }
                }
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            };
            count++;
            label.addMouseListener(ml);
        }
        //Set the layout to null
        panel.setLayout(null);
        for(JLabel highlighter : highlighters)
        {
            panel.add(highlighter);
        }
        for(JLabel piece : pieces)
        {
            panel.add(piece);
        }
        //Adds a new mouse listener that enables interactions with the board tiles when clicked
        MouseListener m1 = new MouseListener() {
            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {}
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                JLabel label = (JLabel) e.getSource();
                //Label.setBackground(Color.red);
                //System.out.println(label.getLocation());
                HandleSound(CastleBoardGame2UI.class .getResourceAsStream("/Resorces/ClickSound.wav"));
               //Sound.HandleSound(CastleBoardGame2UI.class .getResourceAsStream("/Resorces/ClickSound.wav"));
            }           
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {}
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {}
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {}
        };
        //For loop which intialises the JLable array as well as setting their size color and location on the JPanel
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                stone[i] = new JLabel();
                stone[i].setOpaque(true);
                if (i >= 5 && i < 15) {
                    if ((j == 5) || (j == 15)) {
                        //Stone color
                        stone[i].setIcon(stoneImage);
                    } else {
                        Integer diceInt = 0;
                        Random rand = new Random();
                        diceInt = rand.nextInt(10) + 1;
                        if (diceInt > 2) {
                            //Grass color
                            stone[i].setIcon(grassImage);
                        } else {
                            //Dirt color
                            stone[i].setIcon(dirtImage);
                        }
                        if ((i >= 5 && i < 15) && j >= 6 && j < 15) {
                            //Castle color
                            stone[i].setIcon(InteriorImage);
                        }
                        //Sets the color of the walls
                        if (i == 5 && j < 15 && j >= 5) {
                            //Stone color
                            stone[i].setIcon(stoneImage);
                        }
                        if (i == 14 && j < 15 && j >= 5) {
                            //Stone color
                            stone[i].setIcon(stoneImage);
                        }
                        //Sets the color of the keep
                        if ((i == 9 || i == 10) && j == 10) {
                            //Stone color
                            stone[i].setIcon(stoneImage);
                        }
                    }
                } else {
                    Integer diceInt = 0;
                    Random rand = new Random();
                    diceInt = rand.nextInt(10) + 1;
                    if (diceInt > 2) {
                        //Grass color
                        stone[i].setIcon(grassImage);
                    } else {
                        //dirt color
                        stone[i].setIcon(dirtImage);
                    }
                }
                stone[i].setLocation(j * 50, i * 50);
                stone[i].setSize(new Dimension(50, 50));
                //Border
                stone[i].setBorder(BorderFactory.createLineBorder(Color.black));
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
        //System.out.println("castleboardgame2ui.CastleBoardGame2UI.HandleSound()");
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
    public ImageIcon readImage(String fileName, int height, int width)
    {
        //File must be in /Resorces/!
        BufferedImage image = null;    
        try {
            image = ImageIO.read((CastleBoardGame2UI.class.getResourceAsStream("/Resorces/" + fileName)));
        } catch (Exception e) {
            System.out.println(e);
        }
        Image sImage = image.getScaledInstance(height, width, Image.SCALE_SMOOTH);
        return new ImageIcon(sImage);
    }
}