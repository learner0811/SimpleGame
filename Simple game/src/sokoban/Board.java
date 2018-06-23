/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 *
 * @author ns_red
 */
public class Board extends JPanel implements ActionListener, KeyListener{
    private Dimension expected;
    private ArrayList<Level> list;
    private Level level;
    private String levelName = "";
    private char[][] curMap;
    private int offset = 50;
    private int delay = 140;       
    private BatMan BatmanLoc;
    private boolean winningFlag;    
    
    
    public Board(){
        init();
        this.setFocusable(true);
        this.addKeyListener(this);
        this.setSize(expected);
        this.setPreferredSize(expected);
        this.setMaximumSize(expected);
        this.setMinimumSize(expected);
        this.setBackground(new Color(0,183,64));
    }
    
    ///////////////////////////////////////SUPPORT FUNCTION/////////////////////////////
    public void init(){
        expected = new Dimension(500,500);
        list = Loader.mapLoader();
        
        
        boolean invalid = true;
        do{     
            levelName = JOptionPane.showInputDialog(this, "Type the level in number");
            int levelNumber = 0;
            try{
                levelNumber = Integer.parseInt(levelName);
            } catch (NumberFormatException ex){
                continue;
            }
            if (levelName.matches("\\d+"))
                invalid = false;            
            if (levelNumber > 89){
                invalid = true;
            }
        } while (invalid);
        
        level = list.get(Integer.parseInt(levelName) - 1);
        curMap = level.getMaps(); 
        BatmanLoc = new BatMan();
        BatmanLoc.point = level.getBatmanLoc();
        winningFlag = false;        
        
    }
    
    private void doDrawing(Graphics g){
        for (int i = 0; i < curMap.length; i++) {
            for (int j = 0; j < curMap[0].length; j++) {
                char a = curMap[i][j];
                switch (a) {
                    case '@':
                        g.drawImage(getBatman(), BatmanLoc.point.y * offset, BatmanLoc.point.x * offset, offset, offset, this);
                        break;
                    case '#':
                        g.drawImage(getWall(), j * offset, i * offset, offset, offset, this);
                        break;
                    case '$':
                        g.drawImage(getBox(), j * offset, i * offset, offset, offset, this);
                        break;
                    case '*':
                        g.drawImage(getBlueBox(), j * offset, i * offset, offset, offset, this);
                        break;
                    case '.':
                        g.drawImage(getDest(), j * offset, i * offset, offset, offset, this);
                        break;
                    case '%':
                        g.drawImage(getDestP(), j * offset, i * offset, offset, offset, this);
                }
            }
        }
    }
    
    ////////////////////////////////////GAME LOGIC FUNCTION////////////////////////////////////
    private String checkCollision(int key){        
        if (key == KeyEvent.VK_LEFT){
            int x = BatmanLoc.point.x;
            int y = BatmanLoc.point.y;
            switch(curMap[x][y-1]){
                case ' ':
                    return "space";
                case '.':
                    return "Hit dest";
                case '$':
                    switch (curMap[x][y-2]){
                        case ' ':
                            return "Hit box and space";
                        case '.':
                            return "Hit box and dest";
                        default:
                            return "Blocked";
                    }
                case '*':
                    switch (curMap[x][y-2]){
                        case ' ':
                            return "Hit blue box and space";
                        case '.':
                            return "Hit blue box and dest";
                        default:
                            return "Blocked";
                    }
            }               
        }
        else if (key == KeyEvent.VK_RIGHT){
            int x = BatmanLoc.point.x;
            int y = BatmanLoc.point.y;
            switch(curMap[x][y+1]){
                case ' ':
                    return "space";
                case '.':
                    return "Hit dest";
                case '$':
                    switch (curMap[x][y+2]){
                        case ' ':
                            return "Hit box and space";
                        case '.':
                            return "Hit box and dest";
                        default:
                            return "Blocked";
                    }
                case '*':
                    switch (curMap[x][y+2]){
                        case ' ':
                            return "Hit blue box and space";
                        case '.':
                            return "Hit blue box and dest";
                        default:
                            return "Blocked";
                    }
            }               
        }
        else if (key == KeyEvent.VK_UP){
            int x = BatmanLoc.point.x;
            int y = BatmanLoc.point.y;
            switch(curMap[x-1][y]){
                case ' ':
                    return "space";
                case '.':
                    return "Hit dest";
                case '$':
                    switch (curMap[x-2][y]){
                        case ' ':
                            return "Hit box and space";
                        case '.':
                            return "Hit box and dest";
                        default:
                            return "Blocked";
                    }
                case '*':
                    switch (curMap[x-2][y]){
                        case ' ':
                            return "Hit blue box and space";
                        case '.':
                            return "Hit blue box and dest";
                        default:
                            return "Blocked";
                    }
            }               
        }
        else if (key == KeyEvent.VK_DOWN){
            int x = BatmanLoc.point.x;
            int y = BatmanLoc.point.y;
            switch(curMap[x+1][y]){
                case ' ':
                    return "space";
                case '.':
                    return "Hit dest";
                case '$':
                    switch (curMap[x+2][y]){
                        case ' ':
                            return "Hit box and space";
                        case '.':
                            return "Hit box and dest";
                        default:
                            return "Blocked";
                    }
                case '*':
                    switch (curMap[x+2][y]){
                        case ' ':
                            return "Hit blue box and space";
                        case '.':
                            return "Hit blue box and dest";
                        default:
                            return "Blocked";
                    }
            }               
        }        
        return "Blocked";
    }
    
    private void move(String message, int key){
        if (message.equals("Blocked"))
            return;
        int x = BatmanLoc.point.x;
        int y = BatmanLoc.point.y;
        
        //left
        if (key == KeyEvent.VK_LEFT){            
            if (message.equals("space")){
                curMap[x][y-1] = '@';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = false;
                BatmanLoc.point.setLocation(x,y-1);
            }
            else if (message.equals("Hit dest")){
                curMap[x][y-1] = '%';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = true;
                BatmanLoc.point.setLocation(x,y-1);  
            }
            else if (message.equals("Hit box and space")){
                curMap[x][y-1] = '@';
                curMap[x][y-2] = '$';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = false;
                BatmanLoc.point.setLocation(x,y-1);
            }
            else if (message.equals("Hit box and dest")){
                curMap[x][y-1] = '@';
                curMap[x][y-2] = '*';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = false;
                BatmanLoc.point.setLocation(x,y-1);
            }
            else if (message.equals("Hit blue box and space")){
                curMap[x][y-1] = '%';
                curMap[x][y-2] = '$';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = true;
                BatmanLoc.point.setLocation(x,y-1);
            }
            else if (message.equals("Hit blue box and dest")){
                curMap[x][y-1] = '%';
                curMap[x][y-2] = '*';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = true;
                BatmanLoc.point.setLocation(x,y-1);
            }
        }
        //right
        else if (key == KeyEvent.VK_RIGHT){            
            if (message.equals("space")){
                curMap[x][y+1] = '@';      
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = false;
                BatmanLoc.point.setLocation(x,y+1);
            }
            else if (message.equals("Hit dest")){
                curMap[x][y+1] = '%';  
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = true;
                BatmanLoc.point.setLocation(x,y+1);  
            }
            else if (message.equals("Hit box and space")){
                curMap[x][y+1] = '@';
                curMap[x][y+2] = '$';  
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = false;
                BatmanLoc.point.setLocation(x,y+1);
            }
            else if (message.equals("Hit box and dest")){
                curMap[x][y+1] = '@';
                curMap[x][y+2] = '*';  
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = false;
                BatmanLoc.point.setLocation(x,y+1);
            }
            else if (message.equals("Hit blue box and space")){
                curMap[x][y+1] = '%';
                curMap[x][y+2] = '$';  
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = true;
                BatmanLoc.point.setLocation(x,y+1);
            }
            else if (message.equals("Hit blue box and dest")){
                curMap[x][y+1] = '%';
                curMap[x][y+2] = '*';       
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = true;
                BatmanLoc.point.setLocation(x,y+1);
            }            
        }
        
        //up
        else if (key == KeyEvent.VK_UP){            
            if (message.equals("space")){
                curMap[x-1][y] = '@';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = false;
                BatmanLoc.point.setLocation(x-1,y);
            }
            else if (message.equals("Hit dest")){
                curMap[x-1][y] = '%';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = true;
                BatmanLoc.point.setLocation(x-1,y);  
            }
            else if (message.equals("Hit box and space")){
                curMap[x-1][y] = '@';
                curMap[x-2][y] = '$';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = false;
                BatmanLoc.point.setLocation(x-1,y);
            }
            else if (message.equals("Hit box and dest")){
                curMap[x-1][y] = '@';
                curMap[x-2][y] = '*';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';                
                BatmanLoc.standIn = false;
                BatmanLoc.point.setLocation(x-1,y);
            }
            else if (message.equals("Hit blue box and space")){
                curMap[x-1][y] = '%';
                curMap[x-2][y] = '$';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = true;
                BatmanLoc.point.setLocation(x-1,y);
            }
            else if (message.equals("Hit blue box and dest")){
                curMap[x-1][y] = '%';
                curMap[x-2][y] = '*';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = true;
                BatmanLoc.point.setLocation(x-1,y);
            }
        }
        
        //down
        else if (key == KeyEvent.VK_DOWN){            
            if (message.equals("space")){
                curMap[x+1][y] = '@';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = false;
                BatmanLoc.point.setLocation(x+1,y);
            }
            else if (message.equals("Hit dest")){
                curMap[x+1][y] = '%';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = true;
                BatmanLoc.point.setLocation(x+1,y);  
            }
            else if (message.equals("Hit box and space")){
                curMap[x+1][y] = '@';
                curMap[x+2][y] = '$';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = false;
                BatmanLoc.point.setLocation(x+1,y);
            }
            else if (message.equals("Hit box and dest")){
                curMap[x+1][y] = '@';
                curMap[x+2][y] = '*';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';                
                BatmanLoc.standIn = false;
                BatmanLoc.point.setLocation(x+1,y);
            }
            else if (message.equals("Hit blue box and space")){
                curMap[x+1][y] = '%';
                curMap[x+2][y] = '$';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = true;
                BatmanLoc.point.setLocation(x+1,y);
            }
            else if (message.equals("Hit blue box and dest")){
                curMap[x+1][y] = '%';
                curMap[x+2][y] = '*';
                if (!BatmanLoc.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                BatmanLoc.standIn = true;
                BatmanLoc.point.setLocation(x+1,y);
            }
        }
        
        for (int i = 0; i < curMap.length; i++){
            for (int j = 0; j < curMap[0].length; j++)
                System.out.print(curMap[i][j]);
            System.out.println();
        }
    }
    
    private void checkWinning(){
        
    }
    ////////////////////////////////////Override///////////////////////////////////////////////
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.translate(15, 50);
        doDrawing(g);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();
        //System.out.println(key);
        String message = checkCollision(key);
        System.out.println(message);
        move(message,key);        
        checkWinning();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
    }

    
    
    /////////////////////////////////////GETTER/////////////////////////////////
    private Image getBox(){
        ImageIcon box = new ImageIcon("resource/box.png");
        return box.getImage();
    }
    
    private Image getBlueBox(){
        ImageIcon bluebox = new ImageIcon("resource/bluebox.png");
        return bluebox.getImage();
    }
    
    private Image getBatman(){
        ImageIcon batman = new ImageIcon("resource/batman.png");
        return batman.getImage();
    }
    
    private Image getWall(){
        ImageIcon wall = new ImageIcon("resource/wall.png");
        return wall.getImage();
    }
    
    private Image getDest(){
        ImageIcon Dest = new ImageIcon("resource/dest.png");
        return Dest.getImage();
    }

    private Image getDestP(){
        ImageIcon DestP = new ImageIcon("resource/DestP.png");
        return DestP.getImage();
    }    
}
