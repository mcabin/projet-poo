package fr.ubx.poo.model.go;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;

public class Bomb extends GameObject{
	private int compt;
	public int getCompt() {
		return compt;
	}
	public void setCompt(int compt) {
		this.compt = compt;
	}
	public Bomb(Game game, Position position) {
		super(game, position);
		this.compt=4;
	}
	

}
