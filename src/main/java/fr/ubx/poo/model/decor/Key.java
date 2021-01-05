package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.WorldEntity;

public class Key extends Decor{
	public String toString() {
        return "Key";
    }
	public void interact (Game game,Position nextPos) {
		game.getWorld().clear(nextPos);
    	game.update=true;
    	game.getPlayer().setKeys(game.getPlayer().getKeys()+1);
	}
	public WorldEntity toWorldEntity() {
		return WorldEntity.Key;
	}
}
