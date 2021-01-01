package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.go.character.Player;

public class Princess extends Decor{
	public String toString() {
        return "Princess";
    }
	public void interact (Game game,Position nextPos) {
		game.getWorld().clear(nextPos);
    	game.update=true;
    	game.getPlayer().setWinner(true);
	}
	public boolean fragile() {
    	return false;
    }
}
