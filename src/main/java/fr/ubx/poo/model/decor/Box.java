package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;

public class Box extends Decor{
	public String toString() {
        return "Box";
    }
	public boolean movable() {
		return true;
	}
	public void interact (Game game,Position nextPos) {
		Position nextPos2 = game.getPlayer().getDirection().nextPosition(nextPos);
    	game.getWorld().clear(nextPos);
    	Box newBox=new Box();
    	game.getWorld().set(nextPos2, newBox);
    	game.update=true;
	}
}
