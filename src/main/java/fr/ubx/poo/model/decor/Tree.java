/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.WorldEntity;

public class Tree extends Decor {
    @Override
    public String toString() {
        return "Tree";
    }
    public boolean canMove() {
    	return false;
    }
    public boolean fragile() {
    	return false;
    }
    public WorldEntity toWorldEntity() {
		return WorldEntity.Tree;
	}
}
