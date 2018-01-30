/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleboardgame2ui;

/**
 *
 * @author 12nwoodruff
 */
public class Terrain {
    public String terrainType;
    public int teamOccupying;
    public int defensiveBonus;
    public static String grass = "grass";
    public static String stone = "stone";
    
    public Terrain(String type)
    {
        terrainType = type;
        teamOccupying = -1;
        switch(type)
        {
            case "grass":
                defensiveBonus = 5;
                break;
            case "stone":
                defensiveBonus = 15;
                break;
        }
    }
}
