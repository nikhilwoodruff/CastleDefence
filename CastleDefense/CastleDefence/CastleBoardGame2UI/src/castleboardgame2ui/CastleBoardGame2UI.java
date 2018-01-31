/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleboardgame2ui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
    
    int blueScore = 0;
    int diceRoll = 1;
    int moving = 1;
    int currentTeam = 0;
    int[] numberOfMoves = {5, 5};
    ArrayList<MovementAnimation> anim = new ArrayList<>();
    ArrayList<MovementAnimation> toRemove = new ArrayList<>();
    JFrame frame = new JFrame();
    ArrayList<JLabel> pieces = new ArrayList<>();
    ArrayList<JLabel> highlighters = new ArrayList<>();
    int[][] directions = new int[][] {{1, 1}, {0, 1}, {-1, 1}, {1, 0}, {-1, 0}, {1, -1}, {0, -1}, {-1, -1}};
    Terrain[][] grid = new Terrain[22][21];
    
    public CastleBoardGame2UI(){     
        //Creates a new JFrame
        //Adds the panels to the frame and sets the size of the frame
        frame.add(RefreshPanel1());
        frame.setSize(1250, 1025);
        //Sets the default operation of the frame and set it to be visible to the user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        //RefreshFrame();
        //nested for loops assign the selected values of the
        //array the different terrain types available
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CastleBoardGame2UI cbg = new CastleBoardGame2UI();
    }
     void RefreshFrame() {
        frame.repaint();
        frame.validate();
    }
    public JPanel RefreshPanel1() {
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 19; j++){
                grid[i][j] = new Terrain();
            }
        }
        for (int i = 5; i < 15; i++){
            for(int j = 5; j < 15; j++){
                grid[i][j] = new Terrain();
            }
        }
        MovementAnimation.enableAnimation(anim, toRemove);
        //Creates a new JPanel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(195, 195, 195));
        ImageIcon DiceImage = readImage("DiceImage2.png", 50, 1227);
        JLabel dice = new JLabel();
        dice.setBackground(new Color(0, 0, 0, 0));
        dice.setVisible(true);
        dice.setIcon(DiceImage);
        dice.setLocation(1100, 100);
        dice.setSize(50, 1227);
        Button Dice = new Button("Roll the dice");
        Dice.setLocation(1075, 25);
        Dice.setSize(150, 50);
        Dice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {                    
                rollDice(dice);
            }
        });
        JLabel scoreIndicator = new JLabel();
        scoreIndicator.setLocation(1075, 150);
        scoreIndicator.setSize(150, 150);
        JLabel turnIndicator = new JLabel();
        turnIndicator.setLocation(1075, 300);
        turnIndicator.setSize(150, 150);
        JLabel groundInfo = new JLabel();
        groundInfo.setLocation(1075, 350);
        groundInfo.setSize(150, 500);
        Button doNothing = new Button("Do nothing");
        doNothing.setLocation(1075, 200);
        doNothing.setSize(150, 50);
        doNothing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {                    
                int team = (pieces.indexOf(pieces.get(moving)) % 2 == 0) ? 1 : 0;
                haveGo(team, turnIndicator, scoreIndicator);
            }
        });
        JLabel concealer1 = new JLabel();
        concealer1.setSize(50, 869);
        concealer1.setLocation(1100, 151);
        concealer1.setIcon(readImage("grey.png", 50, 869));
        JLabel concealer2 = new JLabel();
        concealer2.setSize(50, 100);
        concealer2.setLocation(1100, 0);
        concealer2.setIcon(readImage("grey.png", 50, 100));
        panel.add(Dice);
        panel.add(turnIndicator);
        
        panel.add(groundInfo);
        panel.add(doNothing);
        panel.add(scoreIndicator);
        panel.add(concealer1);
        panel.add(concealer2);
        panel.add(dice);
        
        
        
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
            pieces.get(pieces.size() - 1).setLocation(50 * (i), 0);
            pieces.get(pieces.size() - 1).setSize(50, 50);
            grid[i][0].teamOccupying = 1;
            pieces.add(new JLabel());
            pieces.get(pieces.size() - 1).setVisible(true);
            pieces.get(pieces.size() - 1).setIcon(CounterImage2);
            pieces.get(pieces.size() - 1).setLocation(50 * (i+7), 50 * 9);
            pieces.get(pieces.size() - 1).setSize(50, 50);
            grid[i+7][9].teamOccupying = 0;
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
                        //System.out.println("Not your piece!");
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
                    int index = highlighters.indexOf(label);
                    if(pieces.get(moving).getLocation().x - label.getLocation().x == directions[index][0] * 50)
                    {
                        //When a direction pointer is clicked...
                    int team = (pieces.indexOf(pieces.get(moving)) % 2 == 0) ? 1 : 0;
                        JLabel target = pieces.get(moving);
                        int x = (int) Math.round(target.getLocation().x / 50);
                        int y = (int) Math.round(target.getLocation().y / 50);
                        
                        int newX = x - directions[countFinal][0];
                        int newY = y - directions[countFinal][1];
                        for(JLabel label : highlighters)
                        {
                            //Pack away all pointers
                            MovementAnimation.newAnimation(anim, label, 625 - label.getLocation().x, 1100 - label.getLocation().y, highlighterSpeed);
                        }
                        boolean okToMove = true;
//                        System.out.println("Moving from " + target.getLocation().x + ", " + target.getLocation().y + ": " + team);
//                        System.out.println("To " + newX * 50 + ", " + newY * 50 + ": " + grid[newX][newY].teamOccupying);
                        if(grid[newX][newY].teamOccupying == 1 - team)
                        {
                            
//                            System.out.println(team + " : " + grid[newX][newY].teamOccupying);
                            int diceOutput = rollDice(dice);
                            int rollTarget = (int) Math.floor((50 + grid[newX][newY].defendingBonus - grid[x][y].attackingBonus) / 17);

                            groundInfo.setText("<html><body style='width: 120px'>Summary\nYour ground: " + grid[x][y].terrainType + ", your attack bonus: " + grid[x][y].attackingBonus + "; their ground: " + grid[newX][newY].terrainType + ", their defending bonus: " + grid[newX][newY].defendingBonus + "; need a " + rollTarget + " to take the piece. (rolled a " + diceOutput + ")");
//                            System.out.println("Collision with enemy!");
//                            System.out.println("Rolled a " + diceOutput + ", needed a " + rollTarget + " to take the piece.");
//                            System.out.println("Generated " + rand + ", needed " + (50 + grid[newX][newY].defendingBonus));
                            if(diceOutput * 17  + grid[x][y].attackingBonus > 50 + grid[newX][newY].defendingBonus)
                            {
                                int cX = findCounterByLocation(newX, newY, pieces).getLocation().x;
                                int cY = findCounterByLocation(newX, newY, pieces).getLocation().y;
                                int cTargetX = (1 - grid[newX][newY].teamOccupying) * 11 * 50;
                                int cTargetY = (1 - grid[newX][newY].teamOccupying) * 50 * 50;
                                MovementAnimation.newAnimation(anim, findCounterByLocation(newX, newY, pieces), cTargetX-cX, cTargetY-cY, 3000);
                                grid[newX][newY].teamOccupying = -1;
                                
                            }
                            haveGo(team, turnIndicator, scoreIndicator);
                            okToMove = false;
                        }
                        else if(grid[newX][newY].teamOccupying == team)
                        {
//                            System.out.println(team + " : " + grid[newX][newY].teamOccupying);
//                            System.out.println("Collision with team!");
                            okToMove = false;
                        }
                        //Move the piece
                        if(okToMove)
                        {
                            grid[x][y].teamOccupying = -1;
                            grid[newX][newY].teamOccupying = team;
                            //MovementAnimation.newAnimation(anim, target, -directions[countFinal][0] * 50, -directions[countFinal][1] * 50, moveSpeed);
                            MovementAnimation.newAnimation(anim, target, 50 * (newX - x), 50 * (newY - y), moveSpeed);
                            haveGo(team, turnIndicator, scoreIndicator);
                            
                        }
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
                HandleSound(CastleBoardGame2UI.class .getResourceAsStream("/Resources/ClickSound.wav"));
               //Sound.HandleSound(CastleBoardGame2UI.class .getResourceAsStream("/Resources/ClickSound.wav"));
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
                        grid[i][j] = new Terrain("stone");
                    } else {
                        Integer diceInt = 0;
                        Random rand = new Random();
                        diceInt = rand.nextInt(10) + 1;
                        if (diceInt > 2) {
                            //Grass color
                            stone[i].setIcon(grassImage);
                            grid[i][j] = new Terrain("grass");
                        } else {
                            //Dirt color
                            stone[i].setIcon(dirtImage);
                            grid[i][j] = new Terrain("hill");
                        }
                        if ((i >= 5 && i < 15) && j >= 6 && j < 15) {
                            //Castle color
                            stone[i].setIcon(InteriorImage);
                            grid[i][j] = new Terrain("wood");
                        }
                        //Sets the color of the walls
                        if (i == 5 && j < 15 && j >= 5) {
                            //Stone color
                            stone[i].setIcon(stoneImage);
                            grid[i][j] = new Terrain("wall");
                        }
                        if (i == 14 && j < 15 && j >= 5) {
                            //Stone color
                            stone[i].setIcon(stoneImage);
                            grid[i][j] = new Terrain("wall");
                        }
                        //Sets the color of the keep
                        if ((i == 9 || i == 10) && j == 10) {
                            //Stone color
                            stone[i].setIcon(stoneImage);
                            grid[i][j] = new Terrain("wood");
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
    JLabel findCounterByLocation(int x, int y, ArrayList<JLabel> list)
    {
        for(JLabel label : list)
        {
            if(Math.abs(label.getLocation().x / 50 - x) < 1 && Math.abs(label.getLocation().y / 50 - y) < 1)
            {
                return label;
            }
        }
        return null;
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
    public int rollDice(JLabel dice)
    {
        HandleSound(CastleBoardGame2UI.class .getResourceAsStream("/Resources/DiceSound.wav"));
        dice.setLocation(1100, 153 - diceRoll * 51);
        int rand = (int) Math.floor(Math.random() * 6);
        MovementAnimation.newAnimation(anim, dice, 0, -((6 - diceRoll) * 51 + 612 + rand * 51 + 51), 2000);
        diceRoll = rand + 1;
        return diceRoll;
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
        //File must be in /Resources/!
        BufferedImage image = null;    
        try {
            image = ImageIO.read((CastleBoardGame2UI.class.getResourceAsStream("/Resources/" + fileName)));
        } catch (Exception e) {
            System.out.println(e);
        }
        Image sImage = image.getScaledInstance(height, width, Image.SCALE_SMOOTH);
        return new ImageIcon(sImage);
    }
    public void haveGo(int team, JLabel turnIndicator, JLabel scoreIndicator)
    {
        numberOfMoves[team]--;
        if(numberOfMoves[team] < 1)
        {
            numberOfMoves[team] = 5;
            currentTeam = 1 - currentTeam;
        }
        String teamName = "";
        if(currentTeam == 1)
        {
            teamName = "reds";
        }
        else
        {
            teamName = "blues";
        }
        turnIndicator.setText("<html><body style='width: 120px'>Current team: " + teamName + ", number of moves left: " + numberOfMoves[team]);
        //Victory check
        if(grid[10][9].teamOccupying == 1 && grid[10][10].teamOccupying == 1)
        {
            System.out.println("VICTORY FOR REDS!");
        }
        //Score check
        int deltaScore = 0;
        for(JLabel label : pieces)
        {
            if(pieces.indexOf(label) % 2 != 0)
            {
                int x = label.getLocation().x / 50;
                int y = label.getLocation().y / 50;
                deltaScore += grid[x][y].attackingBonus;
                deltaScore += grid[x][y].defendingBonus;
            }
        }
        blueScore += deltaScore;
        scoreIndicator.setText("Blue score: " + blueScore);
    }
}