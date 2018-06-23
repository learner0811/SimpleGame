/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban;

import java.awt.Point;

/**
 *
 * @author ns_red
 */
public class Level {
    private String name;
    private char[][] maps;
    private int batManX;
    private int batManY;
    public Level() {
    
    }
            
    ////////////////////////////////////SETTER//////////////////////////////////
    public void setName(String name) {
        this.name = name;
    }
   
    public void setMaps(char[][] maps) {
        this.maps = maps;
    }

    ////////////////////////////////////GETTER//////////////////////////////////
    public String getName() {
        return name;
    }
   

    public char[][] getMaps() {
        return maps;
    }
    
    public Point getBatmanLoc(){
        Point loc = new Point();
        for (int i = 0; i < maps.length; i++)
            for (int j = 0; j < maps[0].length; j++)
                if (maps[i][j] == '@'){
                    loc.setLocation(i, j);                    
                    break;
                }
    return loc;
    }
    
}
