/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.WorldEntity;

public class Stone extends Decor {
    @Override
    public String toString() {
        return "Stone";
    }
    public boolean canMove() {
    	return false;
    }
    public boolean fragile() {
    	return false;
    }
    public WorldEntity toWorldEntity() {
		return WorldEntity.Stone;
	}
}
