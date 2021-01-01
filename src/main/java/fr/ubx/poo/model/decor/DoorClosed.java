package fr.ubx.poo.model.decor;

public class DoorClosed extends Decor{
    public String toString() {
        return "DoorNextClosed";
    }
    public boolean fragile() {
    	return false;
    }
    public boolean openable() {
    	return true;
    }
}
