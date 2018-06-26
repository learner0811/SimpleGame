/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    private BatMan Batman;
    private int starNum;
    private int currentPoint;
    private char[][] curMap;
    private int offset = 50;        
    private boolean winningFlag;    
    
    
    public Board(){
        init();       
        expected = new Dimension(400,600);
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
        list = Loader.mapLoader();                      
        ChooserLevel();
    }
    
    public void ChooserLevel(){
        winningFlag = false;  
        int levelNum = 0;
        boolean valid = false;
        
        while (!valid){
            levelNum = chooseLevelPane();
            if (levelNum != -1)
                valid = true;            
        }               
        
        level = list.get(levelNum);        
        curMap = level.getMaps(); 
        Batman = new BatMan();
        Batman.point = level.getBatmanLoc();
        starNum = level.getStarNum();
        currentPoint = level.getCurrentPoint();
    }
    
    private int chooseLevelPane(){
        levelName = JOptionPane.showInputDialog(this, "Type the level in number in range of 1 to 88");
        if (levelName == null)
            System.exit(0);
        int levelNum = 0;
        try{
            levelNum = Integer.parseInt(levelName);
        } catch (NumberFormatException ex){
            return -1;
        }
        if (levelNum > 88 || levelNum <= 0)
            return -1;
        return levelNum;
    }
    
    public void reset(){        
        winningFlag = false;
        /*level.setMaps();
        level.process();*/
        curMap = level.getMaps();               
        Batman = new BatMan();
        Batman.point = level.getBatmanLoc();
        starNum = level.getStarNum();        
        currentPoint = level.getCurrentPoint();        
        repaint();        
    }
    
    private void doDrawing(Graphics g){                
        g.setColor(Color.red);
        g.setFont(new Font("Latha", Font.PLAIN, 30));
        g.drawString("Level " + levelName, -40, -10);
        for (int i = 0; i < curMap.length; i++) {
            for (int j = 0; j < curMap[0].length; j++) {
                char a = curMap[i][j];
                switch (a) {
                    case '@':
                        g.drawImage(getBatman(), Batman.point.y * offset, Batman.point.x * offset, offset, offset, this);
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
        if (winningFlag)
            drawWin(g);
    }
    
    
    private void drawWin(Graphics g){
        Font a = new Font("Raavi", Font.CENTER_BASELINE, 40);
        FontMetrics fm = getFontMetrics(a);
        g.setColor(new Color(77, 9, 117));
        g.setFont(a);
        String msg = "State Completed!";
        g.drawString(msg, 280 - fm.stringWidth(msg)/2, 400);   
    }
    ////////////////////////////////////GAME LOGIC FUNCTION////////////////////////////////////
    private String checkCollision(int key){        
        if (key == KeyEvent.VK_LEFT){
            int x = Batman.point.x;
            int y = Batman.point.y;
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
            int x = Batman.point.x;
            int y = Batman.point.y;
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
            int x = Batman.point.x;
            int y = Batman.point.y;
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
            int x = Batman.point.x;
            int y = Batman.point.y;
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
        int x = Batman.point.x;
        int y = Batman.point.y;
        
        //left
        if (key == KeyEvent.VK_LEFT){            
            if (message.equals("space")){
                curMap[x][y-1] = '@';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = false;
                Batman.point.setLocation(x,y-1);
            }
            else if (message.equals("Hit dest")){
                curMap[x][y-1] = '%';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = true;
                Batman.point.setLocation(x,y-1);  
            }
            else if (message.equals("Hit box and space")){
                curMap[x][y-1] = '@';
                curMap[x][y-2] = '$';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = false;
                Batman.point.setLocation(x,y-1);
            }
            else if (message.equals("Hit box and dest")){
                curMap[x][y-1] = '@';
                curMap[x][y-2] = '*';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = false;
                Batman.point.setLocation(x,y-1);
                currentPoint++;
            }
            else if (message.equals("Hit blue box and space")){
                curMap[x][y-1] = '%';
                curMap[x][y-2] = '$';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = true;
                Batman.point.setLocation(x,y-1);
                currentPoint--;
            }
            else if (message.equals("Hit blue box and dest")){
                curMap[x][y-1] = '%';
                curMap[x][y-2] = '*';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = true;
                Batman.point.setLocation(x,y-1);                
            }
        }
        //right
        else if (key == KeyEvent.VK_RIGHT){            
            if (message.equals("space")){
                curMap[x][y+1] = '@';      
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = false;
                Batman.point.setLocation(x,y+1);
            }
            else if (message.equals("Hit dest")){
                curMap[x][y+1] = '%';  
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = true;
                Batman.point.setLocation(x,y+1);                  
            }
            else if (message.equals("Hit box and space")){
                curMap[x][y+1] = '@';
                curMap[x][y+2] = '$';  
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = false;
                Batman.point.setLocation(x,y+1);
            }
            else if (message.equals("Hit box and dest")){
                curMap[x][y+1] = '@';
                curMap[x][y+2] = '*';  
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = false;
                Batman.point.setLocation(x,y+1);
                currentPoint++;
            }
            else if (message.equals("Hit blue box and space")){
                curMap[x][y+1] = '%';
                curMap[x][y+2] = '$';  
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = true;
                Batman.point.setLocation(x,y+1);
                currentPoint--;
            }
            else if (message.equals("Hit blue box and dest")){
                curMap[x][y+1] = '%';
                curMap[x][y+2] = '*';       
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = true;
                Batman.point.setLocation(x,y+1);                
            }            
        }
        
        //up
        else if (key == KeyEvent.VK_UP){            
            if (message.equals("space")){
                curMap[x-1][y] = '@';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = false;
                Batman.point.setLocation(x-1,y);
            }
            else if (message.equals("Hit dest")){
                curMap[x-1][y] = '%';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = true;
                Batman.point.setLocation(x-1,y);  
            }
            else if (message.equals("Hit box and space")){
                curMap[x-1][y] = '@';
                curMap[x-2][y] = '$';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = false;
                Batman.point.setLocation(x-1,y);
            }
            else if (message.equals("Hit box and dest")){
                curMap[x-1][y] = '@';
                curMap[x-2][y] = '*';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';                
                Batman.standIn = false;
                Batman.point.setLocation(x-1,y);
                currentPoint++;
            }
            else if (message.equals("Hit blue box and space")){
                curMap[x-1][y] = '%';
                curMap[x-2][y] = '$';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = true;
                Batman.point.setLocation(x-1,y);
                currentPoint--;
            }
            else if (message.equals("Hit blue box and dest")){
                curMap[x-1][y] = '%';
                curMap[x-2][y] = '*';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = true;
                Batman.point.setLocation(x-1,y);
            }
        }
        
        //down
        else if (key == KeyEvent.VK_DOWN){            
            if (message.equals("space")){
                curMap[x+1][y] = '@';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = false;
                Batman.point.setLocation(x+1,y);
            }
            else if (message.equals("Hit dest")){
                curMap[x+1][y] = '%';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = true;
                Batman.point.setLocation(x+1,y);  
            }
            else if (message.equals("Hit box and space")){
                curMap[x+1][y] = '@';
                curMap[x+2][y] = '$';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = false;
                Batman.point.setLocation(x+1,y);
            }
            else if (message.equals("Hit box and dest")){
                curMap[x+1][y] = '@';
                curMap[x+2][y] = '*';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';                
                Batman.standIn = false;
                Batman.point.setLocation(x+1,y);
                currentPoint++;
            }
            else if (message.equals("Hit blue box and space")){
                curMap[x+1][y] = '%';
                curMap[x+2][y] = '$';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = true;
                Batman.point.setLocation(x+1,y);
                currentPoint--;
            }
            else if (message.equals("Hit blue box and dest")){
                curMap[x+1][y] = '%';
                curMap[x+2][y] = '*';
                if (!Batman.standIn)
                    curMap[x][y] = ' ';
                else
                    curMap[x][y] = '.';
                Batman.standIn = true;
                Batman.point.setLocation(x+1,y);
            }
        }
        
        /*test
        for (int i = 0; i < curMap.length; i++){
            for (int j = 0; j < curMap[0].length; j++)
                System.out.print(curMap[i][j]);
            System.out.println();
        }*/
    }
    
    private void checkWinning(){
        if (currentPoint == starNum)
            winningFlag = true;      
            
        
    }
    ////////////////////////////////////Override///////////////////////////////////////////////
    @Override
    public void paintComponent(Graphics gg){
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        
        if (curMap.length > 9)
            g.scale(0.5, 0.45);
        g.translate(70, 50);
        doDrawing(g);
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();
        //System.out.println(key);
        if (!winningFlag) {
            String message = checkCollision(key);
            move(message, key);
            checkWinning();
            repaint();
        }
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
