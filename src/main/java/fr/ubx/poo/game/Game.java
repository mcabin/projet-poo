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

    private World world;
    private final Player player;
    private final String worldPath;
    private ArrayList<Monster> monsterList=new ArrayList<Monster>();;
    public int initPlayerLives;
    public boolean update;
    private int currLevel=1;

    public int getCurrLevel() {
		return currLevel;
	}

	public void setCurrLevel(int currLevel) {
		this.currLevel = currLevel;
	}

	public Game(String worldPath) {
        this.worldPath = worldPath;
        world = new WorldReader(worldPath+"\\level"+this.currLevel+".txt");
        loadConfig(worldPath);
        Position positionPlayer = null;
        try {
            positionPlayer = world.findPlayer();
            player = new Player(this, positionPlayer);
        } catch (PositionNotFoundException e) {
            System.err.println("Position not found : " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
        initialiseMonster();
        
    }
	
	public void initialiseMonster() {
		ArrayList<Position> posList=world.findMonsters();
		monsterList.clear();
        for(Position i :posList) {
        	Monster nMonster=new Monster(this, i);
        	monsterList.add(nMonster);
        	
        }
	}
	
	

    public String getWorldPath() {
		return worldPath;
	}

	public void setWorld(World world) {
		this.world = world;
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
