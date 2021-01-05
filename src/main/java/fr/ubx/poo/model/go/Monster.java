package fr.ubx.poo.model.go;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.decor.Box;
import fr.ubx.poo.model.decor.Stone;
import fr.ubx.poo.model.decor.Tree;

public class Monster extends GameObject{
	Direction direction;
	private boolean moveRequested;

	public Monster(Game game, Position position) {
		super(game, position);
		this.direction=Direction.S;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public void requestMove(Direction direction) {
		if (direction != this.direction) {
            this.direction = direction;
        }
        moveRequested = true;
	}
	
	public boolean canMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        if(!nextPos.inside(game.getWorld().dimension)) {
        	return false;
        }
        if(game.getWorld().get(nextPos)==null) {
        	return true;
        }
        if(game.getWorld().get(nextPos).movable() || !game.getWorld().get(nextPos).canMove() || game.getWorld().get(nextPos).isDoor()) {
        	return false;
        }
        return true;
    }
	
	public void doMove(Direction direction) {
		Position nextPos = direction.nextPosition(getPosition());
        setPosition(nextPos);
        
        if(nextPos.equals(game.getPlayer().getPosition())) {
        	game.getPlayer().takeDamage();
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
		
}
