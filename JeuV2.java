import java.awt.event.ActionEvent;

public class JeuV2 extends Jeu {

	public JeuV2(int nbJ, Vue vue, int fin) {
		super(nbJ, vue, fin);
		nomVersion = "Jeu de l'oie V2";
		descriptif = "- plateau lineaire, 63 cases. 2 des.  \r\n" + 
				"- les effets des cases sont distribues de maniere aleatoire \r\n" + 
				"- effets classiques des cases : saut, passer son tour, attendre une liberation, rejouer.\r\n" + 
				"- presence de questions ?\r\n" + 
				"";		
		
		plateau = new Case[64];
		for(int i=1;i<plateau.length;i++) {
			plateau[i] = new Case(i);  //initialisation des cases
			while(plateau[i].getNom() == null) {
				plateau[i].genNom(plateau.length);
				plateau[i].genEffet();
			}

		}	
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
