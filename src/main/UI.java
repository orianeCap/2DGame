package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
	
	GamePanel gp;
	Font algerian40;
	Font gothic, nokia, start;
	//BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("0.00");
	public String currentDialogue;
	
	Graphics2D g2;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			// font 1
			InputStream is = getClass().getResourceAsStream("/font/GothicPixels.ttf");
			gothic = Font.createFont(Font.TRUETYPE_FONT, is);
			
			// font 2
			is = getClass().getResourceAsStream("/font/nokiafc22.ttf");
			nokia = Font.createFont(Font.TRUETYPE_FONT, is);
			
			// font 3
			is = getClass().getResourceAsStream("/font/PrStart.ttf");
			start = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// font 2
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw (Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(start);
		g2.setColor(Color.white);
		
		// PLAY STATE
		if(gp.gameState == gp.playState) {
			//à remplir
		}
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
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
	
	public void drawDialogueScreen() {
		// WINDOW 
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*4;
		
		drawSubWindow(x, y, width, height);
		
		// TEXT
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y+= 40;
		}
		
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0,0,0, 200);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255, 200);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
}
