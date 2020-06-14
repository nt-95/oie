import java.awt.EventQueue;
import java.util.Scanner;

public class Lanceur {
	Jeu jeu;
		
		
	//METHODE CREER UNE PARTIE VIA L'INTERFACE GRAPHIQUE
	public void creerJeu(int nbJ, int v, Vue vue, int fin) {
		if(v == 1) this.jeu = new JeuStandard(nbJ, vue, fin);		
		else if(v == 2) this.jeu = new JeuV2(nbJ, vue, fin);		
		System.out.println(jeu);
		//vue.prompt(jeu.toString());
		//for(int i=0;i<nbJ;i++)	System.out.println(jeu.joueurs[i]);

	}
	
	/* METHODES POUR CREER UNE PARTIE SUR CONSOLE : 
	
	public void creerJeu(Vue vue) {
		int nbJ = nb("joueurs");
		jeu = choixVersion(nbJ, vue);
		System.out.println(jeu);
		vue.prompt(jeu.toString());
		for(int i=0;i<nbJ;i++)	System.out.println(jeu.joueurs[i]);

	}
	
	public int nb(String joueursOuCases) {
		System.out.println("Veuillez indiquer le nombre de "+joueursOuCases+" souhaites pour cette partie : ");
		Scanner sc = new Scanner(System.in);
		String nb = sc.next();
		int n = Integer.parseInt(nb);
		return n;
	}
	
	public Jeu choixVersion(int nbJ) {
		System.out.println("Veuillez selectionner une version du jeu parmi les suivantes : ");
		System.out.println("1. Version Standard\n2. Version cases aleatoires");
		
		Scanner sc = new Scanner(System.in);
		int selection = Integer.parseInt(sc.next());
		
		if(selection == 1) {
			JeuStandard jeu = new JeuStandard(nbJ);
			return jeu;
		}
		else if(selection == 2) {
			JeuV2 jeu = new JeuV2(nbJ);
			return jeu;
		}
		
		return null;		
				
	}	
	
	
	public int choixType() {
		System.out.println("Veuillez selectionner un type de fin : ");
		System.out.println("1.Le joueur qui arrive pile sur la derniere case gagne\n2.Le joueur qui atteint ou depasse la derniere case gagne");
		Scanner sc = new Scanner(System.in);
		int selection = Integer.parseInt(sc.next());
		return selection;
	}
	
	*/
	
	public static void main(String[] args) {
		EventQueue.invokeLater( () -> 
		{
		
			Vue vue = new Vue();
			vue.initMenu();	  
			vue.pack();
			vue.setVisible(true);
			
		});	
		
		/*
		Lanceur partie = new Lanceur();
		boolean nouvellePartie = true;	
		while(nouvellePartie){
			partie.creerJeu(vue);
			int type = partie.choixType();			
			partie.jeu.jouer(type);	
			nouvellePartie = partie.jeu.recommencer();	
			
		}
		System.out.println("A bientot !");
		//vue.prompt("A bientot!");
		*/
	}

}
