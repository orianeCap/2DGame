package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Key extends SuperObject{
	
	public OBJ_Key() {
		
		// LES CLEFS SONT REMPLACEES PAR DES LIVRES? TOUTE REFERENCE A UNE CLEF DANS LE CODE PARLE D UN LIVRE
		
		name = "Key";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/book.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
