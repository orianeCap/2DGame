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
	//BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("0.00");
	
	Graphics2D g2;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		algerian40 = new Font("Algerian", Font.PLAIN, 40);
		arial40 = new Font("Arial", Font.PLAIN, 40);
		arial80B = new Font("Arial", Font.BOLD, 80);
		//OBJ_Key key = new OBJ_Key(gp);
		//keyImage = key.image;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw (Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(arial40);
		g2.setColor(Color.white);
		
		if(gp.gameState == gp.playState) {
			//à remplir
		}
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
	}
	
	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "PAUSE";
		int x = getXForCenteredText(text);
		int y = gp.screenHeight/2;
		
		
		g2.drawString(text, x, y);
	}
	
	public int getXForCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x;
		x = gp.screenWidth/2 - length/2;
		return x;
		
	}
}
