package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.WorldEntity;
import fr.ubx.poo.model.go.character.Player;

public class BombNumberDec extends Decor{
	public String toString() {
        return "BombNumberDec";
    }
	
	public void interact (Game game,Position nextPos) {
		game.getWorld().clear(nextPos);
		game.update=true;
		Player p=game.getPlayer();
		p.setBombValue(p.getBombValue()-1);;
		if(p.getBombValue()<0) {
			p.setBombValue(0);
		}
	}
	public WorldEntity toWorldEntity() {
		return WorldEntity.BombNumberDec;
	}
}
