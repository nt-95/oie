import java.awt.event.ActionEvent;
import java.util.Scanner;
import javax.swing.JTable;

public class JeuStandard extends Jeu {

	public JeuStandard(int nbJ, Vue vue, int fin) {
		super(nbJ, vue, fin); //l'array de joueurs est cree dans Jeu
		plateau = new Case[64];  //jeu standard a 63 cases, on rajoute +1 pour que la 63e compte
		
		nomVersion = "Jeu de l'oie standard";
		descriptif = "Plateau lineaire, 63 cases. 2 des. Effets classiques des cases : saut, passer son tour, attendre une liberation, rejouer. \r\n" + 
				"    Qui fait 9 au premier jet, ira au 26 s'il l'a fait par 6 et 3, ou au 53 s'il l'a fait par 4 et 5.\r\n" + 
				"    Qui tombe a 6, ou il y a un pont, ira a 12.\r\n" + 
				"    Qui tombe a 19, ou il y a un hotel, se repose quand chacun joue 2 fois.\r\n" + 
				"    Qui tombe a 31, ou il y a un puits attend qu'on le releve.\r\n" + 
				"    Qui tombe a 42, ou il y a un labyrinthe retourne a 30.\r\n" + 
				"    Qui tombe a 52, ou il y a une prison attend qu'on le releve.\r\n" + 
				"    Qui tombe a 58, ou il y a la mort, recommence.\r\n" + 
				"";		
		
		for(int i=0;i<plateau.length;i++) {
			plateau[i] = new Case(i);  //initialisation des cases			
		}
		//attribution de destinations et d'effets a certaines cases d'apres les regles 
		plateau[6].destination = 12;  //pont
		plateau[6].nom = "Pont";  
		plateau[42].destination = 30; //labyrinthe
		plateau[42].nom = "Labyrinthe";
		plateau[58].destination = 0;  //mort
		plateau[58].nom = "Cimetiere";		
		plateau[19].effet = "repos"; //  hotel  --> repos 2 tours
		plateau[19].nom = "Hotel ";
		plateau[31].effet = "attente"; // puits  --> attend passage de qqn d'autre	
		plateau[31].nom = "Puits";
		plateau[52].effet = "attente"; // prison --> attend passage de qqn d'autre	
		plateau[52].nom = "Prison";
		plateau[10].nom = "Oie";       // oie --> le joueur relance les des
		plateau[10].effet = "rejouer";
		plateau[20].nom = "Oie";
		plateau[20].effet = "rejouer";
		plateau[63].nom = "Fin";
		//les autres destinations et effets sont par defaut == null. 		
		
		for(int i=0;i<plateau.length;i++) { //toutes les cases 'null' deviennent des chemins
			if(plateau[i].getNom() == null) {
				plateau[i].setNom("Chemin"); 
			}
		}		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}		
	
}
