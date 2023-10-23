package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Heart extends SuperObject{
GamePanel gp;
	
	public OBJ_Heart(GamePanel gp) {
		
		// LES CLEFS SONT REMPLACEES PAR DES LIVRES? TOUTE REFERENCE A UNE CLEF DANS LE CODE PARLE D UN LIVRE
		
		name = "Heart";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_half.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_blank.png"));
			image = uTool.scaledImage(image, gp.tileSize, gp.tileSize);
			image2 = uTool.scaledImage(image2, gp.tileSize, gp.tileSize);
			image3 = uTool.scaledImage(image3, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
