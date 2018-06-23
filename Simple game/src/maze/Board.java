/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author ns_red
 */
public class Board extends JPanel{
    private int WIDTH = 280;
    private int HEIGT = 200;
    private int offset = 70;
    private int[][] maps = {{1, 0, 0 ,0},
                            {1, 1, 1, 0},
                            {0, 0, 1, 0},
                            {0, 0, 1, 0}};
    
    public Board(){
        setSize(WIDTH, HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGT));
        
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.translate(110, 110);
        for (int i = 0; i < maps.length; i++)
            for (int j = 0; j <maps[0].length; j++){
                if (maps[i][j] == 0)
                    g.setColor(Color.WHITE);
                else
                    g.setColor(Color.GREEN);
                g.fillRect(i*offset, j*offset, offset, offset);
                g.setColor(Color.BLACK);
                g.drawRect(i*offset, j*offset, offset, offset);
            }
    }
}
