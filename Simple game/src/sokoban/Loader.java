/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


/**
 *
 * @author ns_red
 */
public class Loader {
    public static ArrayList<Level> mapLoader(){
        ArrayList<Level> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("maps.txt"));
            
            int space = 0;
            String line = null;
            ArrayList<String> maps = new ArrayList<>();
            Level level = new Level();
            
            while ((line = br.readLine()) != null){
                if (space < 2){
                    if (line.isEmpty())
                        space++;
                    continue;
                }
                //end of information
                if(line.isEmpty())
                    continue;
                if (line.startsWith(";")){
                    level.setName(line.replace(";", "").trim());
                    
                    int size = maps.size();
                    char[][] temp = new char[size][];
                    for (int i = 0; i < size; i++)
                        temp[i] = maps.get(i).toCharArray();
                    
                    level.setOrigin(temp);                    
                    level.process();
                    list.add(level);
                    
                    //erase
                    maps = new ArrayList<>();
                    level = new Level();
                }
                else{
                    maps.add(line);
                }       
            }
        } catch (Exception ex) {
            System.out.println("Fail in loader");
        }        
        return list;
    }
}
