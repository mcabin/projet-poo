package fr.ubx.poo.model.go;

import java.util.Iterator;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.decor.Box;
import fr.ubx.poo.model.decor.Explosion;
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
	public void exploseAt(Position pos) {			 //apply the effect of a explosion for a specific position
		//check if a monster is at the position and destroy if its the case
		Iterator<Monster> mIt=game.getMonsterList().iterator();
		while(mIt.hasNext()) {
			Monster m=mIt.next();
			Position mPos=m.getPosition();
			if(mPos.equals(pos)) {
				mIt.remove();
			}
		}
		//check if player is in position and give him one damage if its the case
		Position pPos=game.getPlayer().getPosition();
		if(pPos.equals(pos)) {
			game.getPlayer().takeDamage();
		}
		//check if a decor is present and if the decor can be destroy
		if(game.getWorld().get(pos)!=null) {
			if(game.getWorld().get(pos).fragile()) {
				game.getWorld().clear(pos);
				game.update=true;
			}
		}
	}
	
	//apply the effect of a explosion for the position reach by the range of the bomb 
	public void explose() { 
		exploseAt(getPosition());
		Position nextN=getPosition();
		Position nextS=getPosition();
		Position nextW=getPosition();
		Position nextE=getPosition();
		for(int i=0;i<range;i++) { 					//use range to obtain every position reach by the bomb for each direction
			nextN=Direction.N.nextPosition(nextN);
			nextS=Direction.S.nextPosition(nextS);
			nextW=Direction.W.nextPosition(nextW);
			nextE=Direction.E.nextPosition(nextE);
			exploseAt(nextN);  						//for each position use exploseAt
			exploseAt(nextS);
			exploseAt(nextW);
			exploseAt(nextE);
		}
	}
	
	public void localExplosion(Position pos) { //create a explosion decor for a specific position
		
		if(pos.inside(game.getWorld().dimension)) { 
			if(game.getWorld().get(pos)==null) {		//check if the position as already a decor if not create a explosion
				Explosion explosion=new Explosion();
				game.getWorld().set(pos, explosion);
			}
			else if(game.getWorld().get(pos).fragile()) { //check if the position as a destructible decor if yes replace the decor by the explosion
				Explosion explosion=new Explosion();
				game.getWorld().set(pos, explosion);
			}
		}
	}
	
	//create a explosion decor for the position reach by the range of the bomb 
	public void createExplosion() {
		Position nextN=getPosition();
		Position nextS=getPosition();
		Position nextW=getPosition();
		Position nextE=getPosition();
		for(int i=0;i<range;i++) {
			nextN=Direction.N.nextPosition(nextN);
			nextS=Direction.S.nextPosition(nextS);
			nextW=Direction.W.nextPosition(nextW);
			nextE=Direction.E.nextPosition(nextE);
			localExplosion(nextN);
			localExplosion(nextS);
			localExplosion(nextW);
			localExplosion(nextE);
		}
		game.update=true;
	}
	

}
