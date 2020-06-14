import java.util.Random;

public class Joueur {
 		int nb;
 		int position;
 		int toursRepos;
 		boolean attente = false;
 		boolean rejoue = false;
 		boolean isPlaying = false;
 		Pion p;
 		
 		public Joueur(int nb) {
 			this.nb = nb;
 			this.position = 0; // = position initiale(case 0). Car toString affiche la position+1
 		} 		
 		
 		public int getNum() {
 			return this.nb;
 		}
 		
 		
 		
 		public int getPosition() {
 			return this.position;
 		}
 		
 		public void setPlaying(boolean bool) {
 			this.isPlaying = bool;
 		}
 		
 		public boolean isPlaying() {
 			return this.isPlaying;
 		}
 		
 		public int lancerDe() {
 			Random rand = new Random();
 			int de = rand.nextInt(6)+1; //pour eviter 0
 			return de;
 		}
 		
 		public void repos() {
 			this.toursRepos = 2;
 		}
 		
 		public void reposMoinsUn() {
 			this.toursRepos -= 1;
 		}
 		
 		public void attente(boolean b) {
 			this.attente = b;
 		}
 		
 		public void rejoue(boolean b) {
 			this.rejoue = b;
 		}
 		
 		public boolean rejoue() {
 			return this.rejoue;
 		}
 		
 		public void setPosition(int pos){
 			this.position = pos;
 		}
 		
 		 		
 		/*TO STRING POUR CONSOLE
 		public String toString() {
 			return "Joueur numero "+nb+" | Case "+(position);
 		}*/
 		
 		//toString pour interface graphique
 		public String toString() {
 			return "J"+nb;
 		}
 		
 		
 		
 		public class Pion{} 		
 		public class De{}
		
 		
 	}

