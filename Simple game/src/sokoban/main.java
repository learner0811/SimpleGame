/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author ns_red
 */
public class main extends JFrame {
    private Dimension expected;
    private Board b;    

    public main() {        
        this.init();        
        this.setPreferredSize(expected);
        this.setSize(expected);                
        this.setMenu();
        this.add(b);
        this.setTitle("Sokoban");
        this.setLocationRelativeTo(this);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);        
    }

    private void init() {
        expected = new Dimension(600, 600);        
        b = new Board();

    }
    private void setMenu(){
        JMenuBar mb = new JMenuBar();
        JMenu menu = new JMenu("FIle");
        JMenuItem newgame = new JMenuItem("New Game");
        newgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                b.reset();
            }
        });
        menu.add(newgame);
        mb.add(menu);
        this.setJMenuBar(mb);
    }
    

    public static void main(String[] args) {
        main view = new main();

    }
}
