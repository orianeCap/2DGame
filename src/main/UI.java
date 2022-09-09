package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
	
	GamePanel gp;
	Font algerian40;
	Font arial40, arial80B;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("0.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		algerian40 = new Font("Algerian", Font.PLAIN, 40);
		arial40 = new Font("Arial", Font.PLAIN, 40);
		arial80B = new Font("Arial", Font.BOLD, 80);
		OBJ_Key key = new OBJ_Key(gp);
		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw (Graphics2D g2) {
		if(gameFinished == true) {
			
			String text;
			int textLength;
			int x;
			int y ;
			
			g2.setFont(arial40);
			g2.setColor(Color.YELLOW);
			text = "Vous avez eu votre diplôme ! ";
			//récupérer taille du texte pour le placer au centre 
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 - (gp.tileSize*3);
			g2.drawString(text, x, y);
			
			text = "Temps de jeux : " + dFormat.format(playTime) + " !" ;
			//récupérer taille du texte pour le placer au centre 
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.tileSize*4);
			g2.drawString(text, x, y);
			
			g2.setFont(arial80B);
			g2.setColor(Color.RED);
			text = "Félicitations ! ";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.tileSize*2);
			g2.drawString(text, x, y);
			
			gp.gameThread = null;
			
		} else {
			
			// afficher nb keys (livres)
			g2.setFont(arial40);
			g2.setColor(Color.YELLOW);
			g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
			// dans le cas du texte le point central fait référence au bas du texte et non pas au point en haut à gauche
			g2.drawString(" x " + gp.player.hasKey, 74, 65);
			
			// time
			playTime += (double)1/60;
			g2.drawString("Time : " + dFormat.format(playTime), gp.tileSize*11, 65);
			
			// message
			if(messageOn == true) {
				
				g2.setFont(g2.getFont().deriveFont(30F));
				
				g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
				
				messageCounter++;
				
				if(messageCounter > 180) {
					messageCounter = 0;
					messageOn = false;
				}
			}
		}
		
	}
}
