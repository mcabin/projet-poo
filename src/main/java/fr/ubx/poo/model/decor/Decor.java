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
	public boolean canMove() {
		return true;
	}
	public boolean movable() {
		return false;
	}
	public boolean isDoor() {
		return false;
	}
	public boolean fragile() {
		return true;
	}
	public void interact (Game g,Position nextPos) {
		
	}
	public boolean openable() {
    	return false;
    }
	public WorldEntity toWorldEntity() {
		return null;
	}
	
}
