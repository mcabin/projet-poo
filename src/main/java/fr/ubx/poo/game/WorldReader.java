package fr.ubx.poo.game;

import fr.ubx.poo.game.WorldEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class WorldReader extends World{

    public static WorldEntity[][] readFromFile(String fileName) {
    	File file = new File(fileName);
        BufferedReader br = null;
        BufferedReader br2 = null;
		try {
			br = new BufferedReader(new FileReader(file));
			br2 = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
        String string;
        int line=0;
        int lineLenght=0;
        String first = null;
		try {
			first = br2.readLine();
			if(first!=null) {
	        	line++;
	        	lineLenght=first.length();
	        }
			while (br2.readLine() != null) {
	        	line++;
	        }
	        br2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        WorldEntity[][] map = new WorldEntity[line][lineLenght]; // TROP CHEUM
        int y = 0;
        try {
			while ((string = br.readLine()) != null) {
			    int x = 0;
			    char[] characters = string.toCharArray();
			    for (char ch : characters) {
			        if (WorldEntity.fromCode(ch).isPresent())
			            map[y][x] = WorldEntity.fromCode(ch).get();
			            x++;
			        }
			    y++;
			}
			 br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
       
        return map;
    }
    public WorldReader(String fileName) {
    	super(readFromFile(fileName));
    }
}
