package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	// SCREEN SETTINGS
	
	final int originalTileSize = 16; //16x16 taille standard pour les pixels
	final int scale = 3;
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	// WORLD MAP SETTINGS
	
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int maxWorldWidth = tileSize * maxWorldCol;
	public final int maxWorldHeight = tileSize * maxWorldRow;
	
	// FPS
	int fps = 60;
	
	// SYSTEM
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread;
	
	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	// on peut afficher jusqu'à 10 objets en même temps
	public SuperObject obj[] = new SuperObject[10];
	public Entity npc[] = new Entity[10];
	
	// GAME STATE
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int titleState = 0;
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		
		gameState = titleState;
		aSetter.setObject();
		aSetter.setNPC();
		//playMusic(0);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	
	/*
	@Override
	public void run() {
		
		// permet de limiter la vitesse de dessin. ici 100000000(nanosecondes) divisé par 60 fps.
		double drawInterval = 1000000000/fps;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(gameThread != null) {
			
			//long currentTime = System.nanoTime();
			
			// 1 UPDATE : update des informations comme les positions
			update();
			
			
			// 2 DRAW : dessin de l'écran avec les nouvelles informations
			// repaint est la façon dont on appelle paintComponent
			repaint();
			
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long)remainingTime);
				nextDrawTime += drawInterval; 
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	*/
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if (delta >= 1 ) {
				update();
				repaint();
				delta--;
			}
		}
	}
	
	
	public void update() {
		
		if(gameState == playState) {
			// PLAYER
			player.update();
			// NPC
			for(int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					npc[i].update();
				}
			}
			
		}
		if (gameState == pauseState) {
			
		}
	}
	
	//paint component fait partie de JPanel
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		//Graphics2D offre un meilleur contrôle de la géométrie en 2D
		Graphics2D g2 = (Graphics2D) g;
		
		// DEBUG
		long drawStart = 0;
		if(keyH.checkDrawTime == true) {		
			drawStart = System.nanoTime();
		}
		
		// TITLE SCREEN
		if (gameState == titleState) {
			ui.draw(g2);
		} else {
			
			// TILE
			tileM.draw(g2);
			
			// OBJECT
			for (int i = 0;  i < obj.length; i++) {
				if (obj[i] != null) {
					obj[i].draw(g2, this);
				}
			}
			
			//NPC
			for(int i =0; i < npc.length; i++) {
				if(npc[i] != null) {
					npc[i].draw(g2);
				}
			}
			
			// PLAYER
			player.draw(g2);
			
			//UI
			ui.draw(g2);
			
			// DEBUG
			if(keyH.checkDrawTime == true) {		
				
				long drawEnd = System.nanoTime();
				long passed = drawEnd - drawStart;
				g2.setColor(Color.white);
				g2.drawString("Draw time : "+ passed, 10, 400);
				System.out.println("Draw time : " + passed);
				
			}
			
			// permet de libérer la mémoire utilisée (bonne pratique)
			g2.dispose();	
		}
		
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}

}
