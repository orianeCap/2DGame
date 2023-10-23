package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{
	
	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	//public int hasKey = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		
		
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		//gestion de la collision
		solidArea = new Rectangle(8, 16, 27, 27);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		
		getPlayerImage();
	}
	
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
		
		// PLAYER STATUS
		maxLife = 6;
		life = maxLife;
	}
	
	public void getPlayerImage() {
		
		up1 = setup("/player/girl_up1");
		up2 = setup("/player/girl_up2");
		down1 = setup("/player/girl_down1");
		down2 = setup("/player/girl_down2");
		right1 = setup("/player/girl_right1");
		right2 = setup("/player/girl_right2");
		left1 = setup("/player/girl_left1");
		left2 = setup("/player/girl_left2");
			
	}
	
	
	public void update() {
		
		if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			if (keyH.upPressed == true) {
				direction = "up";
			} else if (keyH.downPressed == true) {
				direction = "down";
			} else if (keyH.leftPressed == true) {
				direction = "left";
			} else if (keyH.rightPressed == true) {
				direction = "right";
			}
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// CHECK OBJECT COLLISION
			int objIndex =  gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			// CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// IF COLLISION IS FALSE? PLAYER CAN MOVE
			if (collisionOn == false) {
				switch(direction) {
				case "up" :
					worldY -= speed;
					break;
				case "down" :
					worldY += speed;
					break;
				case "left" :
					worldX -= speed;
					break;
				case "right" :
					worldX += speed;
					break;
				}
			}
			
			
			spriteCounter++;
			if (spriteCounter > 12) {
				if (spriteNum == 1) {
					spriteNum =2;
				} else if (spriteNum == 2){
					spriteNum = 1;
				}
				spriteCounter = 0;
			}	
		}
	}
	
	public void pickUpObject(int i) {
		if(i != 999) {
				
		}
	}
	
	public void interactNPC(int i) {
		if(i != 999) {
			if(gp.keyH.enterPressed == true) {				
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
		}
		gp.keyH.enterPressed = false;
	}
	
	public void draw(Graphics2D g2) {
		//g2.setColor(Color.white);
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up" :
			if(spriteNum == 1) {	
				image = up1;
			} else if (spriteNum == 2) {
				image = up2;
			}
			break;
		case "down" : 
			if(spriteNum == 1) {	
				image = down1;
			} else if (spriteNum == 2) {
				image = down2;
			}
			break;
		case "left" : 
			if(spriteNum == 1) {	
				image = left1;
			} else if (spriteNum == 2) {
				image = left2;
			}
			break;
		case "right" : 
			if(spriteNum == 1) {	
				image = right1;
			} else if (spriteNum == 2) {
				image = right2;
			}
			break;
		}
		
		g2.drawImage(image, screenX, screenY, null);
	}
}
