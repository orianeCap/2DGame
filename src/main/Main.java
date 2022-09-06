package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
	
		// créer fenêtre
		JFrame window = new JFrame();
		// permettre à l'utilisateur de bien fermer la fenêtre quand il clique sur "X"
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// empêcher de changer la taille de la fenêtre
		window.setResizable(false);
		// titre
		window.setTitle("2D Adventure");
		
		
		// on instancie GamePanel et on l'ajoute à notre fenêtre
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		// oblige la fenêtre à prendre la taille de ses sous-composants (ici GamePanel)
		window.pack();
		
		
		//la fenêtre ne s'ouvrira pas à un endroit spécifique de l'écran (elle sera donc au milieu)
		window.setLocationRelativeTo(null);
		//rendre la fenêtre visible
		window.setVisible(true);
		
		// on veut lancer les objets avant le jeux
		gamePanel.setupGame();
		// lancement du thread (temps dans je jeux)
		gamePanel.startGameThread();
		

	}

}
