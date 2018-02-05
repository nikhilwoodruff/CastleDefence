/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleboardgame2ui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;

/**
 *
 * @author Nikhil
 */
public class MovementAnimation 
{
    //Movement animation:
    //To use in a JFrame Panel (with null layout): define two ArrayLists of type MovementAnimation; one to store global animations, the other 
    //as the bin for completed animations. Then, call enableAnimation and pass in the ArrayLists.
    //To add/trigger an animation: call newAnimation and pass in the animation ArrayList, the jLabel to animate (currently only jLabels are 
    //supported), the number of pixels right, the number of pixels down (to invert, just invert parameter 'int dy' below), and the duration of
    //the animation in milliseconds.
    JLabel image;
    long startTime;
    long duration;
    Point translation;
    Point startPos;
    Point targetPos;
    static void enableAnimation(ArrayList<MovementAnimation> anim, ArrayList<MovementAnimation> toRemove)
    {
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
            try
            {
                animateAll(anim, toRemove);
            }
            catch (Exception e)
            {
            }
        }, 0, 1, TimeUnit.MILLISECONDS);
    }
    MovementAnimation(JLabel newImage, long newDuration, int dx, int dy)
    {
        image = newImage;
        duration = newDuration;
        startTime = System.currentTimeMillis();
        translation = new Point(dx, dy);
        startPos = newImage.getLocation();
        targetPos = new Point(startPos.x + dx, startPos.y + dy);
    }
    private static void animateAll(ArrayList<MovementAnimation> anim, ArrayList<MovementAnimation> toRemove)
    {
        for(MovementAnimation animation : anim)
                {
                    float percentile;
                    int x = animation.image.getLocation().x;
                    int y = animation.image.getLocation().y;
                    long currentTime = System.currentTimeMillis();
                    if(differenceBetween(x, animation.targetPos.x) > 1 || differenceBetween(y, animation.targetPos.y) > 1)
                    {
                        if(animation.startTime == currentTime)
                        {
                            percentile = 0;
                        }
                        percentile = (float) (currentTime - animation.startTime) / animation.duration;
                        if(percentile >= 0.97)
                        {
                            percentile = 1;
                            toRemove.add(animation);
                        }
                        percentile = cubicInOut(percentile);
                        animation.image.setLocation(new Point(getPercentile(percentile * 100, animation.startPos.x, animation.targetPos.x), getPercentile(percentile * 100, animation.startPos.y, animation.targetPos.y)));
                    }
                    else
                    {
                        percentile = 1;
                        animation.image.setLocation(new Point(getPercentile(percentile * 100, animation.startPos.x, animation.targetPos.x), getPercentile(percentile * 100, animation.startPos.y, animation.targetPos.y)));
                        toRemove.add(animation);
                    }
//                    if(percentile > 1)
//                    {
//                        percentile = 1;
//                        animation.image.setLocation(new Point(getPercentile(percentile * 100, animation.startPos.x, animation.targetPos.x), getPercentile(percentile * 100, animation.startPos.y, animation.targetPos.y)));
//                        toRemove.add(animation);
//                    }
                }
                anim.removeAll(toRemove);
                toRemove.clear();
    }
    static void newAnimation(ArrayList<MovementAnimation> animList, JLabel image, int dx, int dy, int duration)
    {
        int resultOfQuery = MovementAnimation.queued(animList, image);
        if(resultOfQuery == -1)
        {
            animList.add(new MovementAnimation(image, duration, dx, dy));
        }
        else
        {
            System.out.println("Already an animation queued for that object");
            animList.clear();
        }
    }
    private static int queued(ArrayList<MovementAnimation> list, JLabel image)
    {
        for(MovementAnimation anim : list)
        {
            if(anim.image == image)
            {
                return list.indexOf(anim);
            }
        }
        return -1;
    }
    private static int differenceBetween(int number1, int number2)
    {
        return (int) Math.abs(number1 - number2);
    }
    private static int getPercentile(float percentile, int number1, int number2)
    {
        return number1 + (int) Math.floor(percentile * (number2 - number1) / 100);
    }
    private static float cubicIn(float t) {
        return (float) Math.pow(t,3);
    }
    private static float cubicInOut(float t) {
     if(t < 0.5) return cubicIn(t*2)/2f;
     return 1-cubicIn((1-t)*2)/2;                
    }
}
