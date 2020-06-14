	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;

import javax.swing.*;
	import javax.swing.event.AncestorEvent;
	import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.Document;



	public class Vue extends JFrame {
		private static final int largeur = 900;
		private static final int hauteur = 600;
		JPanel panneauPlateau, panneauTexte;
		JSlider[] curseurs;
		JLabel log;
		String nbJ; //nombre de joueurs a recuperer via un JtextField / FocusListener, donc besoin de le mettre en champ de Vue
		JTable vuePlateau;
		JTextArea msgArea;
		JLabel tour;
		JScrollPane msgAreaCont;
		Joueur dernierJoueur; 

		
		
		public Vue(){
			//creation de la fenetre 
			setTitle("Jeu de l'oie");
			setSize(largeur, hauteur);
	        //setLayout(new GridLayout(2, 0)); //2 rows, 1 column
			setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//creation des jpanel
	        this.panneauPlateau = new JPanel(); //panneau ou sera affiche le plateau du jeu
	        this.panneauTexte = new JPanel(); //panneau contenant le menu pour creer une partie puis les messages du jeu
	        panneauPlateau.setBackground(new Color(254, 191, 210));
	        panneauPlateau.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //marges invisibles de 10px
	        
	        panneauTexte.setBackground(new Color(254, 191, 210));
	        panneauTexte.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        panneauTexte.setLayout(new BorderLayout()); //layout BorderLayout afin de pouvoir inserer plus tard le JPanel du menu avec l'option BorderLayout.CENTER
	        
	        //on cree des constraints pour pouvoir ajouter les panneaux a la frame avec des tailles differentes
	        c.fill = GridBagConstraints.HORIZONTAL;
	        c.anchor = GridBagConstraints.WEST;
	        c.gridx = 0;
	        c.gridy = 0;
	        c.ipady = hauteur-100;
	        c.ipadx = largeur;
	        add(panneauPlateau, c);
	        c.gridy = 1; //agit comme GridLayout(2,0) : 2 rangees, 1 colonne
	        c.ipady = 100;
	        add(panneauTexte, c); //les valeurs de c qui n'ont pas ete modifiees restent les memes
	        
	        
		} 
		
		
		
		public JTable getPlateau() {
			return this.vuePlateau;
		}
				
		
			
		public void updateTour(Joueur j) {
			//met a jour le JLabel tour pour afficher quel joueur est en train de jouer
			this.tour.setText("Tour : Joueur "+j.getNum());			
			panneauTexte.updateUI();
			tour.updateUI();
		}
		
		
		
		
		public void initMenu() {
			//BARRE DE MENU ET BOUTONS		
			//on rajoute un jPanel dans panneauTexte qui contiendra le menu
	        JPanel menu = new JPanel();	        
	        menu.setPreferredSize(new Dimension(20,20));	 
	        menu.setLayout(new GridBagLayout()); // similaire a gridLayout mais avec possibilites d'avoir des cases de tailles differentes
	        menu.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
	        panneauTexte.add(menu, BorderLayout.CENTER);	        
	        
	        GridBagConstraints c = new GridBagConstraints(); //nouvelles constraints pour le menu
	        c.insets = new Insets(3,3,3,3); //distance entre les cases du GridBag

	        log = new JLabel("Bienvenue au Jeu de l'Oie");
	        log.setFont(new Font("Default",Font.BOLD,17));
			panneauPlateau.add(log, c);			
	        
	       	//NOMBRE DE JOUEURS	
			log = new JLabel("Nombre de joueurs :");
			c.fill = GridBagConstraints.HORIZONTAL; 
	        c.anchor = GridBagConstraints.WEST;
	        c.weighty =0.50;
	        c.weightx = 0.50;
			c.gridy = 0;
			menu.add(log, c);
			
			//aire pour taper le nb de joueurs
			JTextField nb = new JTextField(5);	//5 colonnes
			c.fill = GridBagConstraints.NONE; //pour ne pas que le Jtextfield s'etire trop
			c.anchor = GridBagConstraints.WEST; //pour qu'il soit contre le bord gauche de la case, car sinon par defaut est au centre
			nb.addFocusListener(new FocusListener() {	//quand on quitte le champ, l'action se declenche:		
				@Override
				public void focusLost(FocusEvent e) {
					nbJ = nb.getText().trim(); //on recup le nb de joueurs et on le stocke dans nbJ(champ de Vue)
				}
				@Override
				public void focusGained(FocusEvent e) {}				

			});			
			menu.add(nb, c);			
			
			log = new JLabel("Version du jeu :");
			c.gridy = 1;
	        c.fill = GridBagConstraints.HORIZONTAL; //on retablit la valeur de fill a horizontal
	        //on garde anchor = WEST car pas de difference 
			menu.add(log, c);
			CheckboxGroup version = new CheckboxGroup(); //creation des boutons a cocher
			Checkbox v1 = new Checkbox("Standard",version,true);
			Checkbox v2 = new Checkbox("Aleatoire",version,false);
			/*v1.addMouseListener(new MouseAdapter() {
				JLabel info = new JLabel("msg");
				JTextArea inf = new JTextArea();

				@Override
				public void mouseEntered(MouseEvent e) {
					//System.out.println("msg");					
					//panneauPlateau.add(info);
					inf.append("msg");
					inf.setEditable(false);
					panneauPlateau.add(inf);
					update(getGraphics());
				}
				@Override
				public void mouseExited(MouseEvent e) {
					//panneauPlateau.remove(info);						
					panneauPlateau.remove(inf);	
					update(getGraphics());
				}
				
			});
			v2.addMouseListener(new MouseAdapter() {
				JLabel info = new JLabel("msg2");
				@Override
				public void mouseEntered(MouseEvent e) {
					System.out.println("msg2");					
					panneauPlateau.add(info);
					update(getGraphics());
				}
				@Override
				public void mouseExited(MouseEvent e) {
					panneauPlateau.remove(info);
					update(getGraphics());
				}
				
			});*/
			menu.add(v1, c);
			menu.add(v2, c);
			
			
			log = new JLabel("Type de fin :");
			c.gridy = 2;
			menu.add(log, c);
			CheckboxGroup fin = new CheckboxGroup();
			Checkbox f1 = new Checkbox("Le joueur qui arrive pile sur la derniere case gagne",fin,true);
			Checkbox f2 = new Checkbox("Le joueur qui atteint ou depasse la derniere case gagne",fin,false);
			menu.add(f1, c);
			menu.add(f2, c);
			
			JButton ok = new JButton("Creer une nouvelle partie");
			c.gridy = 3;
			menu.add(ok, c);
			ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//QUAND ON APPUIE SUR LE BOUTON : LA BOUCLE DU JEU EST LANCEE
					//on enregistre les parametres saisis via l'interface dans 3 int : nb, version, fin --> ils vont permettre d'initialiser une partie via la fonction initgame(int, int, int)
					int nb = Integer.parseInt(nbJ); //on a deja enregistre le nb de joueurs via le FocusListener du JTextField nb, il suffit de convertir le string nbJ en int
					int version;
					int fin;
					if(f1.getState())fin = 1; //si le checkbox f1 (fin de type 1) est sur true(est coche), int fin = 1				
					else fin = 2; //sinon ca veut dire que c'est f2 qui est coche 
					if(v1.getState()) version = 1;  //pareil pour la version Standard ou Aleatoire
					else version = 2;
										
					panneauPlateau.removeAll(); //Suppression des elements du menu principal ajoutes avev initmenu()
					panneauTexte.remove(menu);
					update(getGraphics()); //mise a jour de la frame
					initgame(nb, version, fin, Vue.this); // CONTIENT LA BOUCLE DU JEU						
				}
			}); 
			
		}
		
		
		
		public void initgame(int nb, int version, int fin, Vue vue) {
			//BOUCLE DU JEU			
			Lanceur partie = new Lanceur();
			boolean nouvellePartie = true;	
			//while(nouvellePartie) {  //erreur
			partie.creerJeu(nb, version, vue, fin);
			initplateau(partie.jeu); //creation du plateau	
			//partie.jeu.jouer(); //POUR JOUER VIA LA CONSOLE -- cette fonction appelle partie.jeu.deplacer(), qui contient un appel a vue.afficherPlateau() pour que la vue soit mise a jour a chaque nouveau deplacement des joueurs
			//recommencer(); //ouverture de la boite de dialogue demandant si on recommence
			//} //fin while
			
		}
		
		
		
		
		public void initplateau(Jeu jeu) {
			//PLATEAU DU JEU
			//construction de la JTable vuePlateau qui represente les cases du plateau
			this.vuePlateau = new JTable(7,9); //a modifier pour que la JTable soit egale aux nombres de cases de jeu.plateau si != de 63 cases
			vuePlateau.getTableHeader().setUI(null);
			vuePlateau.setRowHeight(73);
			vuePlateau.setDefaultRenderer(Object.class, new JeuCellRenderer()); //affichage personnalise des cellules du JTable
					
			affichePlateau(jeu);//remplissage de la JTable vuePlateau avec le contenu des cases du jeu en cours
			
			//creation d'un jScrollPane pour contenir le plateau
			JScrollPane pcont = new JScrollPane(vuePlateau);
			pcont.setPreferredSize(new Dimension(largeur-5, hauteur-85));
			panneauPlateau.add(pcont, BorderLayout.CENTER); 
			pcont.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
			
			//CADRE AVEC INFORMATIONS ET DeS			
			//creation d'une aire pour afficher les messages du jeu et infos sur le tour en cours
			this.msgArea = new JTextArea(7, 20); //7 lignes, 20 colonnes	
			msgArea.setEditable(false);
			msgArea.setBackground(new Color(239, 239, 239));
			msgArea.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));			
			this.msgAreaCont = new JScrollPane(msgArea);	//jScrollPane qui contient la JTextArea	
			
			//creation d'un JLabel informant sur le tour et bouton pour lancer les des
			this.tour = new JLabel("Le joueur 1 commence");
			JButton de = new JButton("Lancer les des");
			de.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {					
					Joueur j;
					//on memorise dans un champ de Vue le dernier joueur qui a lance les des
					if(dernierJoueur == null) {
						j = jeu.getJoueurNb(1);	//s'il est null c'est que la partie vient de commencer, c'est au joueur 1 de lancer les des
						//possibilite de mettre random int a la place de 1 pour que le premier joueur a lancer les des soit designe aleatoirement
					}
					else if(dernierJoueur.getNum()+1 <= jeu.getNombreJoueurs()) {
						j = jeu.getJoueurNb(dernierJoueur.getNum()+1);
						}
					else {
						j = jeu.getJoueurNb(1);	
					}
						dernierJoueur = j;		
						updateTour(j);
						jeu.jouer(j);
						if(jeu.estFini()) recommencer();
						
						/*int de1 = j.lancerDe();
						int de2 = j.lancerDe();
						int des = de1 + de2;
						jeu.deplacer(j, des);
						jeu.appliqueEffet(j, jeu.getCase(j.getPosition()));*/
						//prompt(j+" se deplace de "+des+" cases");
					
					/*for(Joueur j: jeu.joueurs) {
						lastPlay = j;
						j.setPlaying(true);
						if(j.isPlaying()) {
							int de1 = j.lancerDe();
							int de2 = j.lancerDe();
							int des = de1 + de2;
							jeu.deplacer(j, des);
							prompt(j+" se deplace de "+des+" cases");
							}
						j.setPlaying(false);						
					}		*/
				}				
			});
			
			//Ajout des elements crees dans panneauTexte
			panneauTexte.setLayout(new GridBagLayout()); //On donne a panneauTexte une disposition de GridBagLayout
			//panneauTexte.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));			
			GridBagConstraints c = new GridBagConstraints(); //constraints pour le GridBagLayout
			c.insets = new Insets(3,3,3,3);	//tous les composants auront une marge de 3			
			//Positionnement du JScrollPane contenant le msgArea a gauche du JPanel panneauTexte:
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 0.95;
			c.weighty = 1;
			c.gridheight = 2;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.PAGE_START;
			panneauTexte.add(msgAreaCont, c);
			//Positionnement du JLabel indiquant quel joueur lance les des:
			c.gridx = 1;
			c.gridy = 0;
			c.weightx = 0.05;
			c.weighty = 0.90;
			c.gridheight = 0;			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.PAGE_START;
			panneauTexte.add(tour, c);
			//Positionnement du JButton "Lancer les des":
			c.gridx = 1;
			c.gridy = 1;
			c.weightx = 0.10;
			c.weighty = 0.10;
			c.gridheight = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.CENTER;
			panneauTexte.add(de, c);

			//Mise a jour du l'affichage
			panneauTexte.updateUI();
			msgArea.updateUI();
			msgAreaCont.updateUI();
			update(getGraphics());
									
		}
		
		
		
		public void affichePlateau(Jeu jeu) {
			int count = 0;			
			//plateau en zig-zag (i = lignes  / j = colonnes)
			for(int i = vuePlateau.getRowCount()-1;i>=0;i-= 2) {				
				for(int j = 0; j<vuePlateau.getColumnCount();j++) {
					count += 1;
					vuePlateau.setValueAt(jeu.getCase(count).toString()+"<html>"+"<br><b><font color=#8000FF>"+jeu.printJoueurAt(count)+"</font></b><br>"+"</html>", i, j);

				}
				count += 9;
			}
			count = 9;
			for(int i = vuePlateau.getRowCount()-2;i>=1;i-= 2) {
				for(int j = vuePlateau.getColumnCount()-1; j>=0;j--) {
					count += 1;
					JLabel contCase = new JLabel(jeu.getCase(count).toString()+"<html>"+"<br><b><font color=#8000FF>"+jeu.printJoueurAt(count)+"</font></b><br>"+"</html>");
					vuePlateau.setValueAt(contCase, i, j);
				}
				count += 9;
			}
			
			panneauPlateau.updateUI(); //mise a jour du JPanel
			panneauTexte.updateUI();
			
		}
		
		public boolean recommencer() {
			//Boite de dialogue qui s'ouvre a la fin de la partie pour demander si on veut rejouer
			String infoMessage = "Souhaitez vous recommencer ?";
			String titleBar = "La partie est terminee";
			Object[] options = {"Oui", "Non"};
			int reponse = JOptionPane.showOptionDialog(null, infoMessage, titleBar, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0] );
			if(reponse == JOptionPane.OK_OPTION) return true;
			return false;
		}
		
		public void prompt(String str) {
			int lines = this.msgArea.getLineCount();
			if(lines > 3) this.msgArea.replaceRange("", 0, msgArea.getText().length());
			this.msgArea.append("\n"+str);
			this.msgArea.updateUI();
			this.msgAreaCont.updateUI();
		}
		
		public class JeuCellRenderer extends DefaultTableCellRenderer{
			//pour personnaliser l'affichage des cellules du JTable
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        setBackground(new Color(254, 231, 240)); 
		        setHorizontalAlignment(CENTER);
	            setVerticalAlignment(TOP);
	            if(value instanceof JLabel) {
	            	setText( (((JLabel) value).getText()) );
	            }	            
	            
	            /*if(Arrays.asList(value).contains("o")) {
	            	System.out.println("TRUE");
	            	setBackground(Color.black);
	            }*/
	            
		        return this;
		}
			
			
		
		
			
	}
		
		
}



