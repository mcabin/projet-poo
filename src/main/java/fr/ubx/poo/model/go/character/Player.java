/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.go.character;

import java.util.ArrayList;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.PositionNotFoundException;
import fr.ubx.poo.game.World;
import fr.ubx.poo.game.WorldReader;
import fr.ubx.poo.model.Movable;
import fr.ubx.poo.model.decor.*;
import fr.ubx.poo.model.go.Bomb;
import fr.ubx.poo.model.go.GameObject;
import fr.ubx.poo.model.go.Monster;
import fr.ubx.poo.game.Game;

public class Player extends GameObject implements Movable {

    private final boolean alive = true;
    Direction direction;
    private boolean moveRequested = false;
    private int lives = 1;
    private int keys=0;
    private boolean winner;
    private int rangeValue=1;
    private int bombValue=0;
    private int hitCooldown=0;
    private ArrayList<Bomb> bombList=new ArrayList<Bomb>();
    
    public ArrayList<Bomb> getBombList() {
		return bombList;
	}

	

    public Player(Game game, Position position) {
        super(game, position);
        this.direction = Direction.S;
        this.lives = game.getInitPlayerLives();
    }

    public int getLives() {
        return lives;
    }
    
    public void setLives(int lives) {
    	this.lives=lives;
    }
    public int getKeys() {
        return keys;
    }
    public int getBombValue() {
        return bombValue;
    }
    public int getRangeValue() {
        return rangeValue;
    }

    public Direction getDirection() {
        return direction;
    }
    public void createBomb() {
    	if(this.bombValue>0) {
    		Position pos=getPosition();
        	Bomb newBomb=new Bomb(game,pos,rangeValue);
        	bombList.add(newBomb);
        	this.bombValue--;
        	game.update=true;
    	}
    }
    public void useKey () {  //open the door where at the player position
        if (this.keys > 0) {
            Position pos = getPosition();
            if (game.getWorld().get(pos)!=null){
            	if(game.getWorld().get(pos).openable()) {
            		game.getWorld().clear(pos);
                    DoorOpened newDoor=new DoorOpened(true);
                    game.getWorld().set(pos, newDoor);
                    this.keys --;
                    game.getWorld().get(pos).interact(game, pos);
                    game.update=true;
            	}
            }
        }
    }
    public void takeDamage() { 			//lower of one the live of the player if the Hitcooldown is at 
    	if(this.getHitCooldown()==0) {
    		this.lives--;
    		setHitCooldown(2
    				);
    	}
    }
    public int getHitCooldown() {
		return hitCooldown;
	}



	public void setHitCooldown(int hitCooldown) {
		this.hitCooldown = hitCooldown;
	}



	public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
        }
        moveRequested = true;
    }
    
    @Override
    public boolean canMove(Direction direction) {	//check if the move of the player is valid
        Position nextPos = direction.nextPosition(getPosition());
        if(!nextPos.inside(game.getWorld().dimension)) {
        	return false;
        }
        if(game.getWorld().get(nextPos)==null) {	
        	return true;
        }
        if(!game.getWorld().get(nextPos).canMove()) {	//check if we can move on this decor
        	return false;
        }
        
        if(game.getWorld().get(nextPos).movable()) {			//check if the box can move in a empty position if not return false
        	Position nextPos2 = direction.nextPosition(nextPos);
            return nextPos2.inside(game.getWorld().dimension) && game.getWorld().get(nextPos2) == null;
        }
        return true;
    }

    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        setPosition(nextPos);
        if(game.getWorld().get(nextPos)!=null) {
        	game.getWorld().get(nextPos).interact(game,nextPos);
        }
        hitMonster(nextPos);
    }
    
    public void setDirection(Direction direction) {
		this.direction = direction;
	}



	public void setWinner(boolean winner) {
		this.winner = winner;
	}



	public void hitMonster(Position p) { 	//check if the position where the player is is share with monster and give him a damage in this case
    	for(Monster i : game.getMonsterList()) {
    		if(i.getPosition().equals(p)) {
    			takeDamage();
    		}
    	}
    }

    public void setKeys(int keys) {
		this.keys = keys;
	}



	public void setRangeValue(int rangeValue) {
		this.rangeValue = rangeValue;
	}



	public void setBombValue(int bombValue) {
		this.bombValue = bombValue;
	}



	public void setBombList(ArrayList<Bomb> bombList) {
		this.bombList = bombList;
	}



	public void update(long now) {
        if (moveRequested) {
            if (canMove(direction)) {
                doMove(direction);
            }
        }
        moveRequested = false;
    }

    public boolean isWinner() {
        return winner;
    }

    public boolean isAlive() {
    	if(lives<=0) {
    		return false;
    	}
        return alive;
    }

}
