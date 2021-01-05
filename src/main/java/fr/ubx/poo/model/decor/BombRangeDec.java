package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.WorldEntity;
import fr.ubx.poo.model.go.character.Player;

public class BombRangeDec extends Decor{
	public String toString() {
        return "BombRangeDec";
    }
	public void interact (Game game,Position nextPos) {
		game.getWorld().clear(nextPos);
		game.update=true;
		Player p=game.getPlayer();
		p.setRangeValue(p.getRangeValue()-1);;
		if(p.getRangeValue()<1) {
			p.setRangeValue(1);
		}
	}
	public WorldEntity toWorldEntity() {
		return WorldEntity.BombRangeDec;
	}
}
