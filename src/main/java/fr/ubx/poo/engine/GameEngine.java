/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.engine;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.view.sprite.Sprite;
import fr.ubx.poo.view.sprite.SpriteFactory;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.model.go.Bomb;
import fr.ubx.poo.model.go.Monster;
import fr.ubx.poo.model.go.character.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public final class GameEngine {

    private static AnimationTimer gameLoop;
    private final String windowTitle;
    private final Game game;
    private final Player player;
    private final List<Sprite> sprites = new ArrayList<>();
    private StatusBar statusBar;
    private ArrayList<Monster> monstersList;
    private Pane layer;
    private Input input;
    private Stage stage;
    private Sprite spritePlayer;
    private ArrayList<Sprite> spriteMonster=new ArrayList<Sprite>();
    private ArrayList<Sprite> spriteBomb=new ArrayList<Sprite>();

    public GameEngine(final String windowTitle, Game game, final Stage stage) {
        this.windowTitle = windowTitle;
        this.game = game;
        this.player = game.getPlayer();
        this.monstersList=game.getMonsterList();
        initialize(stage, game);
        buildAndSetGameLoop();
    }

    private void initialize(Stage stage, Game game) {
        this.stage = stage;
        Group root = new Group();
        layer = new Pane();

        int height = game.getWorld().dimension.height;
        int width = game.getWorld().dimension.width;
        int sceneWidth = width * Sprite.size;
        int sceneHeight = height * Sprite.size;
        Scene scene = new Scene(root, sceneWidth, sceneHeight + StatusBar.height);
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

        stage.setTitle(windowTitle);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        input = new Input(scene);
        root.getChildren().add(layer);
        statusBar = new StatusBar(root, sceneWidth, sceneHeight, game);
        // Create decor sprites
        sprites.clear();
        game.getWorld().forEach( (pos,d) -> {
        			sprites.add(SpriteFactory.createDecor(layer, pos, d));
        	});
        spritePlayer = SpriteFactory.createPlayer(layer, player);
        for(Monster i :monstersList) {
        	spriteMonster.add(SpriteFactory.createMonster(layer, i));
        }
        for(Bomb i : player.getBombList()) {
        	spriteBomb.add(SpriteFactory.createBomb(layer, i));
        }
        
    }
    
    //try to move the monster in parameter at a random direction
    private void randomMove(Monster monster) {
    	int nb=(int) (Math.random()*(4));
		if (nb==0) {
			monster.requestMove(Direction.S);
    	}
    	if (nb==1) {
        	monster.requestMove(Direction.W);
    	}
    	if (nb==2) {
        	monster.requestMove(Direction.E);
    	}
    	if (nb==3) {
    		monster.requestMove(Direction.N);
    	}
    }
    
    //this try to give to a monster the good direction to take to follow the player
    private void followPlayer(Monster m) {
    	//compare the position of the monster and the player and try to approach the player
    	if(game.getPlayer().getPosition().x>m.getPosition().x && m.canMove(Direction.E)) {
			m.requestMove(Direction.E);
		}
    	else if(game.getPlayer().getPosition().x<m.getPosition().x && m.canMove(Direction.W)) {
			m.requestMove(Direction.W);
		}
    	else if(game.getPlayer().getPosition().y>m.getPosition().y && m.canMove(Direction.S)) {
			m.requestMove(Direction.S);
		}
    	else if(game.getPlayer().getPosition().y<m.getPosition().y && m.canMove(Direction.N)) {
			m.requestMove(Direction.N);
		}
    	//if the monster no direction is good the monster try a random Direction
    	else {
    		randomMove(m);
    	}
    	
    }
    protected final void buildAndSetGameLoop() {
        gameLoop = new AnimationTimer() {
        	private long timenowS;
            public void handle(long now) {
                // Check keyboard actions
                processInput(now);

                // Do actions
                update(now);
                long timeinS=now/1000000000; 	//transform the time from nanosecond to second
                if(timeinS!=this.timenowS) { 	//check if one second have passed
                	this.timenowS=timeinS;
                	int monsterSpeed;			//use monster speed to change the reaction speed of monster 
                	if(game.getCurrLevel()>1) {	
                		monsterSpeed=1; 		//if level is at 1 monster move every 2 seconds
            		}
                	else {
                		monsterSpeed=2;			//if level is at 2 or more monster move every 1 second
                	}
                	
                	if(timenowS%monsterSpeed==0) {
                		Iterator<Monster> itMonster=monstersList.iterator();
                		while(itMonster.hasNext()) { 
                			Monster monst=itMonster.next();
                			if(game.getCurrLevel()>=3) { //monster try to follow player if current level is 3 or more monster try to follow the player
                				followPlayer(monst);
                			}
                			else {
                				randomMove(monst); //monster try just random move
                			}
                			
                		}
                	}
                	Iterator<Bomb> itBomb=player.getBombList().iterator();
                	while(itBomb.hasNext()) {
                		Bomb bomb=itBomb.next();
                		if(bomb.getCompt()==0) {		//check if the bomb compt is a zero to explose and get remove the bomb
                			bomb.explose();
                			itBomb.remove();
                			game.update=true;
                		}
                		else {
                			if(bomb.getCompt()==1) {	//the explosion effect appear just before the real explosion
                				bomb.createExplosion(); 
                				
                			}
                			bomb.setCompt(bomb.getCompt()-1);  //if the bomb compt is >0 then he diminish of one
                		}
                	}
                	
                	if(player.getHitCooldown()>0) {				//count down the player hitcooldown if he is superior at 0
                		player.setHitCooldown(player.getHitCooldown()-1);
                	}
                }
                
                // Graphic update
                render();
                
                statusBar.update(game);
            }
        };
    }
    private void processInput(long now) {
        if (input.isExit()) {
            gameLoop.stop();
            Platform.exit();
            System.exit(0);
        }
        if(input.isBomb()) {
        	player.createBomb();
        }
        if (input.isMoveDown()) {
            player.requestMove(Direction.S);
        }
        if (input.isMoveLeft()) {
            player.requestMove(Direction.W);
        }
        if (input.isMoveRight()) {
            player.requestMove(Direction.E);
        }
        if (input.isMoveUp()) {
            player.requestMove(Direction.N);
        }
        if (input.isKey()){
            player.useKey();
        }
        input.clear();
    }

    private void showMessage(String msg, Color color) {
        Text waitingForKey = new Text(msg);
        waitingForKey.setTextAlignment(TextAlignment.CENTER);
        waitingForKey.setFont(new Font(60));
        waitingForKey.setFill(color);
        StackPane root = new StackPane();
        root.getChildren().add(waitingForKey);
        Scene scene = new Scene(root, 400, 200, Color.WHITE);
        stage.setTitle(windowTitle);
        stage.setScene(scene);
        input = new Input(scene);
        stage.show();
        new AnimationTimer() {
            public void handle(long now) {
                processInput(now);
            }
        }.start();
    }


    private void update(long now) {
        player.update(now);
        for(Monster i : monstersList ) {
        	i.update(now);
        }
        if(game.update) {
        	game.update=false;
        	this.initialize(stage, game);
        }
        if (player.isAlive() == false) {
            gameLoop.stop();
            showMessage("Perdu!", Color.RED);
        }
        if (player.isWinner()) {
            gameLoop.stop();
            showMessage("Gagné", Color.BLUE);
        }
    }

    private void render() {
        sprites.forEach(Sprite::render);
        // last rendering to have player in the foreground
        spritePlayer.render();
        spriteMonster.forEach(Sprite::render);
        spriteBomb.forEach(Sprite::render);
    }

    public void start() {
        gameLoop.start();
    }
}
