/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.WorldEntity;
import fr.ubx.poo.model.Entity;
import fr.ubx.poo.model.go.character.Player;

/***
 * A decor is an element that does not know its own position in the grid.
 */
public class Decor extends Entity {
	public boolean canMove() {  //if the player can pass on the decor
		return true;
	}
	public boolean movable() { //if the player can move the decor
		return false;
	}
	public boolean isDoor() {  //check if the decor is a door
		return false;
	}
	public boolean fragile() {  //check if the decor can be destroy by a bomb
		return true;
	}
	public void interact (Game g,Position nextPos) {  //trigger a action when the player pass on it who can change for each decor
		
	}
	public boolean openable() {    //check if the decor is can be open (in the case of a door)
    	return false;
    }
	public WorldEntity toWorldEntity() {  //return the worldEndity of a decor and null if the decor don't have a associate worldEntity
		return null;
	}
	
}
