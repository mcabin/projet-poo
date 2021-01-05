package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.WorldEntity;
import fr.ubx.poo.model.go.character.Player;

public class Heart extends Decor{

	public String toString() {
        return "Heart";
    }
	public void interact (Game game,Position nextPos) {
		game.getWorld().clear(nextPos);
    	game.update=true;
    	game.getPlayer().setLives(game.getPlayer().getLives()+1);
	}
	public WorldEntity toWorldEntity() {
		return WorldEntity.Heart;
	}
}

