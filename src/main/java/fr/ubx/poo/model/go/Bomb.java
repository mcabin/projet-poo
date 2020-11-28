package fr.ubx.poo.model.go;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.go.character.Player;

public class Bomb extends GameObject{
	private int range;
	private int compt;
	public int getCompt() {
		return compt;
	}
	public void setCompt(int compt) {
		this.compt = compt;
	}
	public Bomb(Game game, Position position,int range) {
		super(game, position);
		this.compt=4;
		this.range=range;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public void exploseAt(Position pos) {
		for(int i=0;game.getMonsterList().size()>i;i++) {
			Position mPos=game.getMonsterList().get(i).getPosition();
			if(mPos.equals(pos)) {
				game.getMonsterList().remove(i);
			}
		}
		Position pPos=game.getPlayer().getPosition();
		if(pPos.equals(pos)) {
			System.out.println("Bomb");
			game.getPlayer().setLives(game.getPlayer().getLives()-1);
		}
		
	}
	public void explose() {
		exploseAt(getPosition());
		Position nextN=getPosition();
		Position nextS=getPosition();
		Position nextW=getPosition();
		Position nextE=getPosition();
		for(int i=0;i<range;i++) {
			nextN=Direction.N.nextPosition(nextN);
			nextS=Direction.S.nextPosition(nextS);
			nextW=Direction.W.nextPosition(nextW);
			nextE=Direction.E.nextPosition(nextE);
			exploseAt(nextN);
			exploseAt(nextS);
			exploseAt(nextW);
			exploseAt(nextE);
		}
	}
	

}
