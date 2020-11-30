/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.game;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import fr.ubx.poo.model.go.Monster;
import fr.ubx.poo.model.go.character.Player;

public class Game {

    private final World world;
    private final Player player;
    private final String worldPath;
    private ArrayList<Monster> monsterList=new ArrayList<Monster>();;
    public int initPlayerLives;
    public boolean update;

    public Game(String worldPath) {
        world = new WorldStatic();
        this.worldPath = worldPath;
        loadConfig(worldPath);
        Position positionPlayer = null;
        try {
            positionPlayer = world.findPlayer();
            player = new Player(this, positionPlayer);
        } catch (PositionNotFoundException e) {
            System.err.println("Position not found : " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
        ArrayList<Position> posList=world.findMonsters();
        for(Position i :posList) {
        	Monster nMonster=new Monster(this, i);
        	monsterList.add(nMonster);
        	
        }
        
    }

    public int getInitPlayerLives() {
        return initPlayerLives;
    }

    private void loadConfig(String path) {
        try (InputStream input = new FileInputStream(new File(path, "config.properties"))) {
            Properties prop = new Properties();
            // load the configuration file
            prop.load(input);
            initPlayerLives = Integer.parseInt(prop.getProperty("lives", "3"));
        } catch (IOException ex) {
            System.err.println("Error loading configuration");
        }
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return this.player;
    }

	public ArrayList<Monster> getMonsterList() {
		return this.monsterList;
	}
    

}
