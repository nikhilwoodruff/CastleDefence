/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleboardgame2ui;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author 12nwoodruff
 */
public class MainPanel extends javax.swing.JPanel {

    /**
     * Creates new form MainPanel
     */
    boolean rolled;
    int moving;
    ArrayList<MovementAnimation> anim = new ArrayList<>();
    ArrayList<MovementAnimation> toRemove = new ArrayList<>();
    ArrayList<JLabel> pieces = new ArrayList<>();
    ArrayList<JLabel> highlighters = new ArrayList<>();
    int[][] directions = new int[][] {{1, 1}, {0, 1}, {-1, 1}, {1, 0}, {-1, 0}, {1, -1}, {0, -1}, {-1, -1}};
    public MainPanel() {
        initComponents();
        
         BufferedImage imgG = null;
        try {
            imgG = ImageIO.read(CastleBoardGame2UI.class .getResourceAsStream("/Resorces/red.jpg"));
        } catch (IOException e) {
        }
        Image dimgG = imgG.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon grassImage = new ImageIcon(dimgG);
        
     //   JLabel[] hTemp =  {jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9, jLabel10};
            JLabel[] hTemp = new JLabel[10];
        
            for (int i = 3; i < 10; i++) {
            hTemp[i] = new JLabel();
            hTemp[i].setIcon(grassImage);
        }
            
        JLabel[] pTemp = {jLabel1, jLabel2, jLabel11};
        highlighters.addAll(Arrays.asList(hTemp));
        pieces.addAll(Arrays.asList(pTemp));
        moving = 0;
        pieces.add(jLabel1);
        pieces.add(jLabel2);
        MovementAnimation.enableAnimation(anim, toRemove);
        int count1 = 0;
        for(JLabel piece : pieces)
        {
            final int countFinal = count1;
            piece.setSize(50, 50);
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("H:\\Downloads\\red.jpg"));
            } catch (IOException e) {
            }
            Image dimg = img.getScaledInstance(piece.getWidth(), piece.getHeight(),Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dimg);
            piece.setIcon(imageIcon);
            MouseListener ml = new MouseListener() {
                @Override
                protected Object clone() throws CloneNotSupportedException {
                    return super.clone();
                }
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    moving = countFinal;
                    setUpHighlighters();
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
            label.setLocation(625, 1100);
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
                    if(rolled)
                    {
                        jLabel12.setLocation(520, 20);
                    }
                    int rand = (int) Math.floor(Math.random() * 6);
                    MovementAnimation.newAnimation(anim, jLabel12, 0, -(rand * 51 + 615), 2000);
                    rolled = true;
                    JLabel target = pieces.get(moving);
                    for(JLabel label : highlighters)
                    {
                        //Pack away all pointers
                        MovementAnimation.newAnimation(anim, label, 625 - label.getLocation().x, 1100 - label.getLocation().y, 750);
                    }
                    //Move the piece
                    MovementAnimation.newAnimation(anim, target, -directions[countFinal][0] * 50, -directions[countFinal][1] * 50, 1500);
                    Point proxy = target.getLocation();
                    proxy.translate(-directions[countFinal][0] * 50, -directions[countFinal][1] * 50);
                    //System.out.println("Moving to " + (proxy.x / 50) + ", " + (proxy.y / 50));
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        setLayout(null);

        jButton1.setText("jButton1");
        add(jButton1);
        jButton1.setBounds(510, -20, 73, 40);

        jButton2.setText("jButton1");
        add(jButton2);
        jButton2.setBounds(510, 70, 73, 880);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movement/red.jpg"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel1MouseReleased(evt);
            }
        });
        add(jLabel1);
        jLabel1.setBounds(0, 50, 40, 40);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movement/red.jpg"))); // NOI18N
        jLabel2.setText("jLabel1");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel2MouseReleased(evt);
            }
        });
        add(jLabel2);
        jLabel2.setBounds(0, 0, 40, 40);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movement/highlight.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel3MouseReleased(evt);
            }
        });
        add(jLabel3);
        jLabel3.setBounds(440, 440, 0, 50);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movement/highlight.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel4MouseReleased(evt);
            }
        });
        add(jLabel4);
        jLabel4.setBounds(30, 440, 0, 50);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movement/highlight.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel5MouseReleased(evt);
            }
        });
        add(jLabel5);
        jLabel5.setBounds(200, 440, 0, 50);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movement/highlight.png"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel6MouseReleased(evt);
            }
        });
        add(jLabel6);
        jLabel6.setBounds(80, 440, 0, 50);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movement/highlight.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel7MouseReleased(evt);
            }
        });
        add(jLabel7);
        jLabel7.setBounds(140, 440, 0, 50);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movement/highlight.png"))); // NOI18N
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel8MouseReleased(evt);
            }
        });
        add(jLabel8);
        jLabel8.setBounds(250, 440, 0, 50);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movement/highlight.png"))); // NOI18N
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel9MouseReleased(evt);
            }
        });
        add(jLabel9);
        jLabel9.setBounds(320, 440, 0, 50);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movement/highlight.png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel10MouseReleased(evt);
            }
        });
        add(jLabel10);
        jLabel10.setBounds(380, 440, 0, 50);

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movement/red.jpg"))); // NOI18N
        jLabel11.setText("jLabel1");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel11MouseReleased(evt);
            }
        });
        add(jLabel11);
        jLabel11.setBounds(0, 100, 40, 40);
        jLabel11.getAccessibleContext().setAccessibleName("jLabel3");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movement/DiceImage2.png"))); // NOI18N
        add(jLabel12);
        jLabel12.setBounds(520, 20, 50, 920);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:
        //MovementAnimation.newAnimation(anim, sprites.get(moving), evt.getX() - sprites.get(moving).getLocation().x, evt.getY() - sprites.get(moving).getLocation().y, 1000);
    }//GEN-LAST:event_formMouseReleased

    private void jLabel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseReleased
        // TODO add your handling code here:
        moving = 0;
        setUpHighlighters();
    }//GEN-LAST:event_jLabel1MouseReleased

    public void setUpHighlighters()
    {
        JLabel target = pieces.get(moving);
        int count = 0;
        for(JLabel pointer : highlighters)
        {
            MovementAnimation.newAnimation(anim, pointer, target.getLocation().x - pointer.getLocation().x - (directions[count][0] * 50), target.getLocation().y - 30 - pointer.getLocation().y - (directions[count][1] * 50), 900);
            count++;
        }
    }
    
    private void jLabel3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jLabel3MouseReleased
    
    
    private void jLabel4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel4MouseReleased

    private void jLabel5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel5MouseReleased

    private void jLabel6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel6MouseReleased

    private void jLabel7MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel7MouseReleased

    private void jLabel8MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel8MouseReleased

    private void jLabel9MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel9MouseReleased

    private void jLabel10MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel10MouseReleased

    private void jLabel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel2MouseReleased

    private void jLabel11MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel11MouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
