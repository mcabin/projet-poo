package fr.ubx.poo.view.sprite;

import fr.ubx.poo.model.go.Bomb;
import fr.ubx.poo.model.go.GameObject;
import fr.ubx.poo.model.go.character.Player;
import fr.ubx.poo.view.image.ImageFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class SpriteBomb extends SpriteGameObject{

	public SpriteBomb(Pane layer, GameObject go) {
		super(layer, null, go);
		// TODO Auto-generated constructor stub
	}
	public void updateImage() {
        Bomb bomb = (Bomb) go;
        setImage(ImageFactory.getInstance().getBomb(bomb.getCompt()));
    }
}
