import java.util.Random;

public class Case {
	String nom;
	int nbCase;
	Integer destination;
	String effet; 	//effets : se reposer 2 tours / attendre d'etre sauve
	boolean occupe = false;
	int nbOccup = 0; //nb de joueurs presents sur la meme case
	
	//CONSTRUCTEUR : on attribue un chiffre a la case dans l'ordre de creation
	public Case(int i) {
		this.nbCase = i;
	}
	
	//toString
	@Override
	public String toString() {
		return "<html>"+this.nbCase+"<br>"+this.nom+"<html>";
	}
	
	//GETTERS	
	
	
	public String getNom() {
		return this.nom;
	}
	
	public String getEffet() {
		return this.effet;
	}
	
	public int getDestination() {
		return this.destination;
	}
	
	public boolean hasDestination() {
		if (this.destination != null) return true;
		return false;
	}
		
	//SETTERS
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public void setOccup(boolean bool) {
		this.occupe = bool;
	}
	
	//surcharge
	public void setOccup(int n) {
		this.nbOccup += n;
	}
	
	public void setDestination() {
		
	}
	
	public boolean checkDelivrance() {
		//methode pour delivrer qqn de l'attente s'il y a 2+ personnes sur la meme case
		if(this.nbOccup >= 2) return true;
		return false;
	}
	
	public boolean hasEffet() {
		if (this.effet != null) return true;
		return false;
	}
	
	
	//AUTRES METHODES
	
	public void genNom(int tailleplateau) {
		//attribue de maniere aleatoire un nom pour la case parmi une liste de noms (hotel, bar, puits, pont, prison...)
		//pas utilisee pour vStandard. utile pour V2
		String[]lieux = new String[20];
		lieux[0] = "Prison";
		lieux[1] = "Puits";
		lieux[2] = "Labyrinthe";
		lieux[3] = "Hotel";
		lieux[4] = "Bar";
		lieux[5] = "Cimetiere";
		lieux[7] = "Oie";
		lieux[8] = "Oie";
		lieux[9] = "Oie";
		lieux[10] = "Oie";
		lieux[11] = "Oie";
		lieux[12] = "Jardin";
		lieux[13] = "Maison";
		lieux[14] = "Chemin";
		lieux[15] = "Chemin";
		lieux[16] = "Chemin";
		lieux[17] = "Chemin";
		lieux[18] = "Chemin";
		lieux[19] = "Chemin";
		//lieux[10] = "Foret";
		Random r = new Random();
		int rand = r.nextInt(lieux.length);
		this.nom = lieux[rand];  //atribution d'un nom aleatoire a la case 
		if(this.nom == "Cimetiere") this.destination = 0;  //si le nom est cimetiere, on attribue a la case la destination 0 (case depart)
		else if(this.nom == "Bar" || this.nom == "Labyrinthe") {
			//si c'est bar ou labyrinthe, la destination est aleatoire
			int rand2 = r.nextInt(tailleplateau);
			this.destination = rand2;
		}
		else if(this.nom == "Pont") {
			//si c'est pont, destination aleatoire mais superiere a la case (le pont ne mene que vers des cases superieures)
			int rand2 = r.nextInt((tailleplateau - this.nbCase) + 1) + this.nbCase;
			this.destination = rand2;
		}	
		
	}
	
	public void genEffet() {
		//attribue un effet a la case d'apres son nom 
		if(this.nom == "Prison" || this.nom == "Puits") this.effet = "attente";
		else if(this.nom == "Hotel"|| this.nom == "Maison") this.effet = "repos";
		else if(this.nom == "Oie") this.effet = "rejouer";
	}
	
	//Affichage du nom de la case dans la console
	/*
	public String toString() {
		return this.nom + " (Effet : "+ this.effet+") (Destination :"+this.destination+")";
	}*/

}
;