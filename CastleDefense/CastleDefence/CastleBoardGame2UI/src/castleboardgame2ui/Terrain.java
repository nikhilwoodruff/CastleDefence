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
    public int defendingBonus;
    public int attackingBonus;

    public Terrain() {
        teamOccupying = -1;
    }

    public Terrain(String type) {
        terrainType = type;
        teamOccupying = -1;
        switch (type) {
            case "grass":
                defendingBonus = 0;
                attackingBonus = 0;
                break;
            case "hill":
                defendingBonus = 25;
                attackingBonus = 30;
                break;
            case "stone":
                defendingBonus = 35;
                attackingBonus = 20;
                break;
            case "wood":
                defendingBonus = 15;
                attackingBonus = 0;
            case "wall":
                defendingBonus = 40;
                attackingBonus = 15;
            case "house":
                defendingBonus = 35;
                attackingBonus = 10;

        }
    }
}
