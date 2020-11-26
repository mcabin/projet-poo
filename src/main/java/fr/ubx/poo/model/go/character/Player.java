/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.go.character;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.Movable;
import fr.ubx.poo.model.decor.*;
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

    public Player(Game game, Position position) {
        super(game, position);
        this.direction = Direction.S;
        this.lives = game.getInitPlayerLives();
    }

    public int getLives() {
        return lives;
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

    public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
        }
        moveRequested = true;
    }

    @Override
    public boolean canMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        if(!nextPos.inside(game.getWorld().dimension) || game.getWorld().get(nextPos) instanceof Tree || game.getWorld().get(nextPos) instanceof Stone ) {
        	return false;
        }
        
        if(game.getWorld().get(nextPos) instanceof Box) {
        	Position nextPos2 = direction.nextPosition(nextPos);
        	if(!nextPos2.inside(game.getWorld().dimension) || game.getWorld().get(nextPos2)!=null) {
            	return false;
            }
        }
        return true;
    }

    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        setPosition(nextPos);
        if(game.getWorld().get(nextPos) instanceof Heart) {
        	game.getWorld().clear(nextPos);
        	game.update=true;
        	this.lives++;
        }
        if(game.getWorld().get(nextPos) instanceof Key) {
        	game.getWorld().clear(nextPos);
        	game.update=true;
        	this.keys++;
        	
        }
        if(game.getWorld().get(nextPos) instanceof BombRangeDec) {
        	game.getWorld().clear(nextPos);
        	game.update=true;
        	this.rangeValue--;
        	if(rangeValue<1) {
        		rangeValue=1;
        	}
        }
        if(game.getWorld().get(nextPos) instanceof BombRangeInc) {
        	game.getWorld().clear(nextPos);
        	game.update=true;
        	this.rangeValue++;
        }
        if(game.getWorld().get(nextPos) instanceof BombNumberDec) {
        	game.getWorld().clear(nextPos);
        	game.update=true;
        	this.bombValue--;
        	if(rangeValue<0) {
        		rangeValue=0;
        	}
        }
        if(game.getWorld().get(nextPos) instanceof BombNumberInc) {
        	game.getWorld().clear(nextPos);
        	game.update=true;
        	this.bombValue++;
        }
        if(game.getWorld().get(nextPos) instanceof Box) {
        	Position nextPos2 = direction.nextPosition(nextPos);
        	game.getWorld().clear(nextPos);
        	Box newBox=new Box();
        	game.getWorld().set(nextPos2, newBox);
        	game.update=true;
        	
        }
        if(game.getWorld().get(nextPos) instanceof Princess) {
        	game.getWorld().clear(nextPos);
        	game.update=true;
        	this.winner=true;
        }
        hitMonster(nextPos);
    }
    
    public void hitMonster(Position p) {
    	for(Monster i : game.getMonsterList()) {
    		if(i.getPosition().equals(p)) {
    			this.lives--;
    		}
    	}
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
