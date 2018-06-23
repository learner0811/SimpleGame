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
    private Point loc;
    private char[][] maps;
    private char[][] origin;    
    private int width;
    private int height;
    private int starNum;
    private int currentPoint;
    
    
    public Level() {
        loc = new Point();
    }
            
    ////////////////////////////////////SETTER//////////////////////////////////
    public void setName(String name) {
        this.name = name;
    }

    public void setOrigin(char[][] origin) {
        this.origin = origin;
        this.width = origin.length;
        this.height = origin[0].length;
        setMaps();
    }

    public void setMaps(){
        maps = new char[origin.length][origin[0].length];
        for (int i = 0; i < origin.length; i++){
            for (int j = 0; j < origin[0].length; j++)
                maps[i][j] = origin[i][j];
        }
    }
    
    ////////////////////////////////////GETTER//////////////////////////////////
    public String getName(){
        return name;
    }     

    public char[][] getMaps() {
        return maps;
    }
    
    public Point getBatmanLoc(){                
        return loc;
    }
    
    public int getStarNum(){
        return starNum;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }              

    public int getCurrentPoint() {
        return currentPoint;
    }        
    
    ///////////////////////////////////////////FUNCTION///////////////////////////
    public void process(){        
        starNum = 0;
        currentPoint = 0;
        for (int i = 0; i < origin.length; i++){
            for (int j = 0; j < origin[0].length; j++){
                if (origin[i][j] == '@'){
                    loc.setLocation(i, j);                                        
                }    
                else if (origin[i][j] == '.' || origin[i][j] == '*'){
                    starNum++;
                    if (origin[i][j] == '*')
                        currentPoint++;
                }
                
            }
        }
    }          
}
