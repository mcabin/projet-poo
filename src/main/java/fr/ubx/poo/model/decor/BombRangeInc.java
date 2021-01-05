package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.WorldEntity;

public class BombRangeInc extends Decor{
	public String toString() {
        return "BombRangeInc";
    }
	public void interact (Game game,Position nextPos) {
		game.getWorld().clear(nextPos);
    	game.update=true;
    	game.getPlayer().setRangeValue(game.getPlayer().getRangeValue()+1);
	}
	public WorldEntity toWorldEntity() {
		return WorldEntity.BombRangeInc;
	}
}
