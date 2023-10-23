package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

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
	public int commandNum = 0;
	public int titleScreenState = 0;
	BufferedImage heart_full, heart_half, heart_blank;
	
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
		
		// CREATE HUD OBJECT
		SuperObject heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw (Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(start);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		
		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		// PLAY STATE
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
		}
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
	}
	
	public void drawTitleScreen() {
		if (titleScreenState == 0) {
			// TITLE NAME
			//bg
			g2.setColor(new Color(16, 0, 43));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			g2.setFont(gothic.deriveFont(Font.BOLD, 75F));
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			String text = "Oriane Adventure";
			int x = getXForCenteredText(text);
			int y = gp.tileSize*3;
			
			//SHADOW
			g2.setColor(new Color(71, 18, 107, 180));
			g2.drawString(text, x+5, y+5);
			
			// MAIN COLOR
			g2.setColor(new Color(234, 105, 139));
			g2.drawString(text, x, y);
			
			// PERSO IMAGE
			x = gp.screenWidth/2 -(gp.tileSize*2) /2;
			y += gp.tileSize*2;
			g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
			
			// MENU
			g2.setFont(nokia.deriveFont(Font.BOLD, 30F));
			
			text = "NEW GAME";
			x = getXForCenteredText(text);
			y += gp.tileSize*3.5;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "LOAD GAME";
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "QUIT";
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-gp.tileSize, y);
			}	
		} else if (titleScreenState == 1) {
			
			g2.setColor(new Color(234, 105, 139));
			g2.setFont(g2.getFont().deriveFont(15F));
			
			String text = "Vous allez suivre les aventures \nde la reconversion d'Oriane.\nC'est la rentrée universitaire à la Sorbonne \net vous devez ABSOLUMENT obtenir votre diplôme.\nPour cela vous devrez trouver et étudier les"
					+ " \nlivres des plus grands philosophes !";
			int y = gp.tileSize*3;
			for(String line : text.split("\n")) {
				int x = getXForCenteredText(line);
				g2.drawString(line, x, y);
				y+= 40;
			}
			
			text = "PRESS SPACE TO PLAY";
			g2.setFont(g2.getFont().deriveFont(25F));
			y = gp.tileSize*10;
			int x = getXForCenteredText(text);
			g2.drawString(text, x, y);
			
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
	
	public void drawPlayerLife() {
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		// DRAW MAX LIFE
		while(i < gp.player.maxLife/2) {
			g2.drawImage(heart_full, x, y, null);
			i++;
			x+= gp.tileSize;
		}
		
		// RESET
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		
		// DRAW CURRENT LIFE
		while(i < gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i< gp.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}
	}
}
