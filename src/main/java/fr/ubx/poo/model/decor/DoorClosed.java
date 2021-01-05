package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.WorldEntity;

public class DoorClosed extends Decor{
    public String toString() {
        return "DoorClosed";
    }
    public boolean fragile() {
    	return false;
    }
    public boolean openable() {
    	return true;
    }
    public boolean isDoor() {
    	return true;
    }
    public WorldEntity toWorldEntity() {
		return WorldEntity.DoorNextClosed;
	}
}
