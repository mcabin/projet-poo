package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.PositionNotFoundException;
import fr.ubx.poo.game.World;
import fr.ubx.poo.game.WorldEntity;
import fr.ubx.poo.game.WorldReader;

public class DoorOpened extends Decor{
	private boolean next;
	public DoorOpened(boolean next) {
		this.next=next;
	}
    public boolean isNext() {
		return next;
	}
	public String toString() {
        return "DoorOpened";
    }
    public boolean fragile() {
    	return false;
    }
    
    public boolean isDoor() {
    	return true;
    }
    
    public void interact (Game game,Position nextPos) {
    	game.saveLevel(game.getWorld());
    	if(this.next) {
    		game.setCurrLevel(game.getCurrLevel()+1);
    		World newLev=new WorldReader(game.getWorldPath()+"\\level"+game.getCurrLevel()+".txt");
        	game.setWorld(newLev);
        	try {
        		Position posPlayer=game.getWorld().findDoor(next);
        		game.getPlayer().setPosition(posPlayer);
            } catch (PositionNotFoundException e) {
                System.err.println("Position not found : " + e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
    	}
    	else {
    		game.setCurrLevel(game.getCurrLevel()-1);
    		World newLev=new World(game.getLevelSaves().get(game.getCurrLevel()-1));
    		game.setWorld(newLev);
    		try {
        		Position posPlayer=game.getWorld().findDoor(next);
        		game.getPlayer().setPosition(posPlayer);
            } catch (PositionNotFoundException e) {
                System.err.println("Position not found : " + e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
    	}
        
    	game.initialiseMonster();
    	game.update=true;
	}
    public WorldEntity toWorldEntity() {
		if(next) {
			return WorldEntity.DoorNextOpened;
		}
		else {
			return WorldEntity.DoorPrevOpened;
		}
	}
    
}