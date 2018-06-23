/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author ns_red
 */
public class main extends JFrame{
    private Dimension expected;
    private Board b;
    public main(){
        init();
        
        this.add(b);
        this.setSize(expected);
        this.setPreferredSize(expected); 
        this.setTitle("Sokoban");
        this.setLocationRelativeTo(this);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    private void init(){
        expected = new Dimension(500, 500);
        b = new Board();
        
    }
    
    public static void main(String[] args) {
        main view = new main();
        
    }
}
