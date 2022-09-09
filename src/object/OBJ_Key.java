package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Key extends SuperObject{
	
	GamePanel gp;
	
	public OBJ_Key(GamePanel gp) {
		
		// LES CLEFS SONT REMPLACEES PAR DES LIVRES? TOUTE REFERENCE A UNE CLEF DANS LE CODE PARLE D UN LIVRE
		
		name = "Key";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/book.png"));
			uTool.scaledImage(image, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
