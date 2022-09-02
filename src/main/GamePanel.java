package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

	// SCREEN SETTINGS
	
	final int originalTileSize = 16; //16x16 taille standard pour les pixels
	final int scale = 3;
	final int tileSize = originalTileSize * scale;
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeight = tileSize * maxScreenRow;
	
	// FPS
	int fps = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	
	// position par défaut
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
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
		if (keyH.upPressed == true) {
			playerY -= playerSpeed;
		} else if (keyH.downPressed == true) {
			playerY += playerSpeed;
		} else if (keyH.leftPressed == true) {
			playerX -= playerSpeed;
		} else if (keyH.rightPressed == true) {
			playerX += playerSpeed;
		}
	}
	
	//paint component fait partie de JPanel
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		//Graphics2D offre un meilleur contrôle de la géométrie en 2D
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.white);
		g2.fillRect(playerX, playerY, tileSize, tileSize);
		// permet de libérer la mémoire utilisée (bonne pratique)
		g2.dispose();
		
		
	}

}
