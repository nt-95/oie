import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;

public abstract class Jeu implements ActionListener {
	Case[] plateau; 
	Joueur[] joueurs;
	String nomVersion;
	String descriptif;
	Vue vue;
	int type; //type de fin
	boolean nouveauTour = false; //des lances --> JButton des dans Vue met a sur True via ActionPerformed;
	JButton lancerDe;
	int enAttente; //nb de joueurs en attente
	boolean premierTour = true;
	boolean victoire = false;
	
	public Jeu(int nbJ, Vue vue, int fin) {
		this.vue = vue;
		this.type = fin;
		this.joueurs = new Joueur[nbJ];
		for (int j=0;j<nbJ;j++) {
			joueurs[j] = new Joueur(j+1);  //initialisation de chaque joueur avec son champ nb +1 pour que Joueur 1, Joueur 2... et pas Joueur 0
		}
	}
	
	public String toString() {
		return "Version : "+this.nomVersion+"\n"+this.descriptif;
	}
	
	//GETTERS & SETTERS
	public Case getCase(int i) {
		return this.plateau[i];
	}	
	
	public int getNombreJoueurs() {
		return joueurs.length;
	}
	
	public Joueur getJoueurNb(int nb) {
			for(Joueur j:joueurs) {
				if(nb == j.getNum()) return j;				
			}
			return null;
		}
	
	public LinkedList<Joueur> getJoueurAt(int pos) {
		//renvoie une linkedlist contenant tous les joueurs a la position passee en argument
		LinkedList<Joueur> joueursCase = new LinkedList();
		for(Joueur j : joueurs) {
			if(j.position == pos) joueursCase.add(j);
		}
		return joueursCase;
		}
	
	public void setNouveauTour(boolean b) {
		this.nouveauTour = true;
	}
	
	public boolean hasNouveauTour() {
		return this.nouveauTour;
	}
	
	public boolean estFini() {
		return this.victoire;
	}
		
	//POUR AFFICHAGE INTERFACE GRAPHIQUE
	public String printJoueurAt(int pos) {
		//renvoie une representation en String de la linkedlist contenant les joueurs a la position passee en arg
		LinkedList<Joueur> joueursCase = new LinkedList<Joueur>();
		String s = "";
		for(Joueur j : joueurs) {
			if(j.position == pos) joueursCase.add(j);
		}
		for(Joueur j : joueursCase) {
			s += j.toString()+" ";
		}
		return s;
	}
		
	
	//METHODES a redefinir dans les classes filles de Jeu pour obtenir des versions differentes du jeu
	
	public boolean victoire(){
		//TYPE = type de fin
		if(this.type == 1) {
			//VICTOIRE = JOUEUR EST ARRIVE EXACTEMENT SUR LA DERNIERE CASE DU PLATEAU
			for(Joueur j : joueurs){
				if(j.position == (plateau.length-1)){ //64-1 pour avoir 63
					vue.prompt("\nLe joueur "+j.nb+" a gagne !\n");
					this.victoire = true;
					return true;
				}			
			}return false;	
		  
		}
		else /*if(type == 2)*/ {
			//VICTOIRE = LE JOUEUR DEPASSE LA DERNIERE CASE
			for(Joueur j : joueurs){
				if(j.position >= (plateau.length-1)){ //64-1 pour avoir 63
					vue.prompt("\nLe joueur "+j.nb+" a gagne !\n");
					this.victoire = true;
					return true;
				}			
			}return false;	
		  
		}
	}	
		
	public void deplacer(Joueur j, int de) {
		if(this.type == 1) {
			//SI LE JOUEUR N'ARRIVE PAS PILE SUR LA DERNIERE CASE, IL RECULE EN FONCTION DES CASES EN TROP
			if((j.position + de)<plateau.length) j.position += de;  //de = le chiffre du de
			else if ((j.position + de)>plateau.length){
				int difference = (j.position + de)-plateau.length;
				j.position = plateau.length-1 - difference;
				vue.prompt("Case finale depassee... Vous retournez "+difference+" cases en arriere");
			}		
		}
		else if(this.type == 2) {
			j.position += de;	
			if((j.position + de)<plateau.length) { 
				vue.prompt("Le joueur "+j.getNum()+" se deplace jusqu'a la case "+j.getPosition());
			}
		}
		vue.affichePlateau(this);
		vue.setVisible(true);
	}
	
	
	public void appliqueEffet(Joueur j, Case c) {
		vue.prompt(c.getNom()+" !");
		if (c.effet == "repos") {
			j.repos();
			vue.prompt("Le joueur "+j.getNum()+" se repose pour 2 tours.");
		}
		else if(c.effet == "attente") {
			j.attente(true);
			vue.prompt("Le joueur "+j.getNum()+" doit attendre qu'on le sorte d'ici!");

		}		
		else if(c.effet == "rejouer") {
			j.rejoue(true);
			vue.prompt("Le joueur "+j.getNum()+" rejoue!");
		}
		
		else if(c.effet == null && c.getNom() != null) {
			//pour les cases qui n'ont pas d'effet mais une destination
			if(c.getNom().equals("Chemin")) { //un chemin ne fait rien
				vue.prompt("Le joueur "+j.getNum()+" marche tranquillement sur le chemin.");
			}
			else {
				while(c.hasDestination() && !c.getNom().equals("Cimetiere")) {
				vue.prompt("Le joueur "+j.getNum()+" est a/au "+c.getNom()+"! Il se deplace a la case "+c.getDestination());
				j.setPosition(c.getDestination());
				c = plateau[j.getPosition()];
				}
				if(c.getNom().equals("Cimetiere")) {
					vue.prompt("Le joueur "+j.getNum()+" est mort. Il retourne a la case 0.");
					j.setPosition(0);
				}
			}
			
		}
	}
	
	public void afficheNomPos(Joueur j) {
		System.out.println(j +" : "+plateau[j.getPosition()]); //on affiche le nom et la nouvelle position du joueur + le nom de la case
		System.out.println();
	}

	public void jouer() {
		vue.affichePlateau(this);
		vue.setVisible(true);
		vue.update(vue.getGraphics());
		boolean premierTour = true;
		boolean victoire = false;		
		while(!victoire()){						
					enAttente = 0; //nb de joueurs en etat d'attente par tour
					for(Joueur j : joueurs) {
						Scanner sc = new Scanner(System.in);			
						sc.nextLine(); //nextLine() permet d'appuyer sur entree pour continuer	
						
						j.setPlaying(true);//met le champ du joueur 'isPlaying' sur true, puis sur false a la fin de la boucle
						vue.updateTour(j);
						//a chaque tour, pour chaque joueur, on verifie s'il doit se reposer ou s'il doit attendre
						//REPOS: si le joueur doit se reposer, on soustrait un tour et on passe au joueur suivant
						if(j.toursRepos > 0) { 
							vue.prompt("Le joueur se repose encore "+j.toursRepos+" tours.");
							j.reposMoinsUn();					
							} 
						else if(j.attente) {
							enAttente += 1; 
							//ATTENTE : Si tous les joueurs sont en attente, le jeu est fini
							if(enAttente == joueurs.length) {
								vue.prompt("Tous les joueurs attendent la delivrance !");
								gameOver(); //message de game over
								return; //fin du jeu						
							}
							//s'il y a plus de 2 personnes sur la mm case, on delivre j de l'attente
							if(plateau[j.getPosition()].checkDelivrance()) {
								j.attente(false);
								vue.prompt("Le joueur peut etre delivre !");
								} 					
						}//sinon si pas de repos et pas d'attente pour j
						else if (j.toursRepos == 0 && !j.attente) { 
							int de1 = j.lancerDe(); //on lance les 2 des 
							int de2 = j.lancerDe();
							int des = de1 + de2;
							vue.prompt("Le joueur numero "+j.nb+" lance les des : "+des);
							//si c'est le premier tour, on regarde si quelqu'un a fait un 9
							if(premierTour){
								jouerPremierTour(j, de1, de2);
							}//sinon si pas premier tour
							else {
								if(j.getPosition() != 0) plateau[j.getPosition()].setOccup(-1);//l'ancienne case est marquee comme inoccupee sauf 0
								deplacer(j, des ); //on deplace le joueur en fonction des des
							}
							//on verifie s'il y a victoire
							if (victoire())	return; //si True la methode nouveauCoup() break ici, sinon la suite se lancera:
							plateau[j.getPosition()].setOccup(+1); //la nouvelle case est marquee comme occupee
							appliqueEffet(j, plateau[j.getPosition()]); //on applique l'effet contenu par la case (attente, repos, rejouer ou rien si null)	
							afficheNomPos(j);
		
							//on verifie si le joueur est tombe sur une case 'Oie' (relancer les des)
							while(j.rejoue()) { //si True, il doit rejouer
								de1 = j.lancerDe(); //on lance les 2 des 
								de2 = j.lancerDe();
								des = de1 + de2;
								deplacer(j, des ); //on deplace a nouveau le joueur
								vue.prompt("Le joueur numero "+j.nb+" lance les des : "+des);
								if (victoire())	return; //on verifie a nouveau s'il y a victoire
								j.rejoue(false); //on met le champ rejoue sur false
								appliqueEffet(j, plateau[j.getPosition()]); //on applique l'effet de la nouvelle case. Si c'est encore une oie, rejoue redeviendra true et la boucle while recommence
								afficheNomPos(j);
							}		
							
						}
						j.setPlaying(false);
					}
					premierTour = false;
					setNouveauTour(false);
					}
			}//while has nouveau tour
	//}
	
	//JOUER() POUR INTERFACE GRAPHIQUE
	public void jouer(Joueur j) {
		if(j.toursRepos > 0) { 
			vue.prompt("Le joueur se repose encore "+j.toursRepos+" tours.");
			j.reposMoinsUn();					
			} 
		else if(j.attente) {
			enAttente += 1; 
			//ATTENTE : Si tous les joueurs sont en attente, le jeu est fini
			if(enAttente == joueurs.length) {
				vue.prompt("Tous les joueurs attendent la delivrance !");
				gameOver(); //message de game over
				return; //fin du jeu						
			}
			//s'il y a plus de 2 personnes sur la mm case, on delivre j de l'attente
			if(plateau[j.getPosition()].checkDelivrance()) {
				j.attente(false);
				vue.prompt("Le joueur peut etre delivre !");
				} 					
		}//sinon si pas de repos et pas d'attente pour j
		else if (j.toursRepos == 0 && !j.attente) { 
			int de1 = j.lancerDe(); //on lance les 2 des 
			int de2 = j.lancerDe();
			int des = de1 + de2;
			vue.prompt("Le joueur numero "+j.nb+" lance les des : "+des);
			//si c'est le premier tour, on regarde si quelqu'un a fait un 9
			if(premierTour){
				jouerPremierTour(j, de1, de2);
				premierTour = false;
			}//sinon si pas premier tour
			else {
				if(j.getPosition() != 0) plateau[j.getPosition()].setOccup(-1);//l'ancienne case est marquee comme inoccupee sauf 0
				deplacer(j, des ); //on deplace le joueur en fonction des des
			}
			//on verifie s'il y a victoire
			if (victoire())	return; //si True la methode nouveauCoup() break ici, sinon la suite se lancera:
			plateau[j.getPosition()].setOccup(+1); //la nouvelle case est marquee comme occupee
			appliqueEffet(j, plateau[j.getPosition()]); //on applique l'effet contenu par la case (attente, repos, rejouer ou rien si null)	
			afficheNomPos(j);

			//on verifie si le joueur est tombe sur une case 'Oie' (relancer les des)
			while(j.rejoue()) { //si True, il doit rejouer
				de1 = j.lancerDe(); //on lance les 2 des 
				de2 = j.lancerDe();
				des = de1 + de2;
				deplacer(j, des ); //on deplace a nouveau le joueur
				vue.prompt("Le joueur numero "+j.nb+" lance les des : "+des);
				if (victoire())	return; //on verifie a nouveau s'il y a victoire
				j.rejoue(false); //on met le champ rejoue sur false
				appliqueEffet(j, plateau[j.getPosition()]); //on applique l'effet de la nouvelle case. Si c'est encore une oie, rejoue redeviendra true et la boucle while recommence
			}		
			
		}
	}
	
	
	public void jouerPremierTour(Joueur j, int de1, int de2) {
		int des = de1 + de2;
		if((de1 == 6 && de2 == 3) || (de1 == 3 && de2 == 6)) {
			j.setPosition(26); // "Qui fait 9 au premier jet, ira au 26 s'il l'a fait par 6 et 3"
		}else if ((de1 == 4 && de2 == 5) || (de1 == 5 && de2 == 4)) {
			j.setPosition(53); // "ou au 53 s'il l'a fait par 4 et 5"
		}else {
			deplacer(j, des ); //on deplace le joueur en fonction des des
		}				
	}
	
	public boolean recommencer(){
		String in;
		do {
			System.out.println("Souhaitez vous creer une nouvelle partie ? (O / N) ");
			Scanner sc = new Scanner(System.in);
			in = sc.next().toUpperCase();	
			if(in.equals("O")) return true;			
			if(!in.equals("O") & !in.equals("N")) System.out.println("Repondre uniquement par 'O' ou par 'N'.");
		}while(!in.equals("O") & !in.equals("N"));		
		return false;		
	}
	
	public void gameOver() {
		vue.prompt("GAME OVER. Tout le monde a perdu");
		victoire = true;
	}
	
 	 	
}
