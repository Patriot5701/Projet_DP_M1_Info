package controleur;

import java.awt.event.ItemListener;
import java.io.File;
import java.util.Vector;
import mesmaths.geometrie.base.Vecteur;
import modele.Bille;
import modele.BillePilotee;
import modele.BilleSimple;
import modele.CollisionBordFranchissement;
import modele.CollisionBordRebond;
import modele.Colors;
import modele.Hurlement;
import modele.MvmtAttractionNewton;
import modele.MvmtAttractionPesanteur;
import modele.MvmtFrottements;
import modele.MvmtRepulsionNewton;
import musique.SonLong;
import vue.CadreAngryBalls;


/**
 * Gestion d'une liste de billes en mouvement ayant toutes un comportement different
 * 
 * Ideal pour mettre en place le DP decorator
 * */
public class TestAngryBalls
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		//---------------------- gestion des bruitages : parametrage du chemin du dossier contenant les fichiers audio --------------------------

		File file = new File(""); // la ou la JVM est lancee : racine du projet

		//TODO Changer le path
		File repertoireSon = new File(file.getAbsoluteFile(),"src"+File.separatorChar+"bruits");
		System.out.println("repertoire son = " + repertoireSon);

		//-------------------- chargement des sons pour les hurlements --------------------------------------

		Vector<SonLong> sonsLongs = OutilsConfigurationBilleHurlante.chargeSons(repertoireSon, "config_audio_bille_hurlante.txt");

		SonLong hurlements[] = SonLong.toTableau(sonsLongs);                // on obtient un tableau de SonLong

		//------------------- creation de la liste (pour l'instant vide) des billes -----------------------

		Vector<Bille> billes = new Vector<Bille>();

		//---------------- creation de la vue responsable du dessin des billes -------------------------

		int choixHurlementInitial = 0;
		CadreAngryBalls cadre = new CadreAngryBalls("Angry balls",
				"Animation de billes ayant des comportements differents. Situation ideale pour mettre en place le DP Decorator",
				billes,hurlements, choixHurlementInitial);

		cadre.montrer(); // on rend visible la vue

		//------------- remplissage de la liste avec 5 billes -------------------------------



		double xMax, yMax;
		double vMax = 0.1;
		xMax = cadre.largeurBillard();      // abscisse maximal
		yMax = cadre.hauteurBillard();      // ordonnee maximale

		//double rayon = 0.05*Math.min(xMax, yMax); // rayon des billes : ici toutes les billes ont le meme rayon, mais ce n'est pas obligatoire
		double rayon = 20;
		Vecteur p0, p1, p2, p3, p4, p5,  v0, v1, v2, v3, v4;    // les positions des centres des billes et les vecteurs vitesse au demarrage. 
		// Elles vont etre choisies aleatoirement

		//------------------- creation des vecteurs position des billes ---------------------------------

		p0 = Vecteur.creationAleatoire(0, 0, xMax, yMax);
		p1 = Vecteur.creationAleatoire(0, 0, xMax, yMax);
		p2 = Vecteur.creationAleatoire(0, 0, xMax, yMax);
		p3 = Vecteur.creationAleatoire(0, 0, xMax, yMax);
		p4 = Vecteur.creationAleatoire(0, 0, xMax, yMax);

		//------------------- creation des vecteurs vitesse des billes ---------------------------------

		v0 = Vecteur.creationAleatoire(-vMax, -vMax, vMax, vMax);
		v1 = Vecteur.creationAleatoire(-vMax, -vMax, vMax, 0);
		v2 = Vecteur.creationAleatoire(-vMax, -vMax, vMax, vMax);
		v3 = Vecteur.creationAleatoire(-vMax, -vMax, vMax, vMax);
		v4 = Vecteur.creationAleatoire(-vMax, -vMax, vMax, vMax);
		
		Vecteur centreBillepilotee = new Vecteur(300,140);
		Vecteur centreBille1 = new Vecteur(20,100);
		Vecteur vitesseBille = new Vecteur (0,0);
		Vecteur centreBille2 = new Vecteur(20,140);
		Vecteur centreBille3 = new Vecteur(20,180);
		Vecteur centreBille4 = new Vecteur(60,120);
		Vecteur centreBille5 = new Vecteur(60,160);
		Vecteur centreBille6 = new Vecteur(100,140);

		//--------------- ici commence la partie a changer ---------------------------------


		//Bille billePilotee = new BilleSimple(centreBillepilotee, rayon, vitesseBille, Colors.BLACK,xMax);
		Bille billePilotee = new BilleSimple(p0, rayon, v0, Colors.BLACK,xMax);
		billePilotee = new BillePilotee(billePilotee, cadre);
		billePilotee = new CollisionBordRebond(billePilotee);
		billePilotee = new Hurlement(billePilotee,cadre,hurlements[choixHurlementInitial]);

//		Bille bille1 = new BilleSimple(centreBille1, rayon, vitesseBille, Colors.RED,xMax);
//		Bille bille2 = new BilleSimple(centreBille2, rayon, vitesseBille, Colors.BLUE,xMax);
//		Bille bille3 = new BilleSimple(centreBille3, rayon, vitesseBille, Colors.GREEN,xMax);
//		Bille bille4 = new BilleSimple(centreBille4, rayon, vitesseBille, Colors.YELLOW,xMax);
//		Bille bille5 = new BilleSimple(centreBille5, rayon, vitesseBille, Colors.ROSE,xMax);
//		Bille bille6 = new BilleSimple(centreBille6, rayon, vitesseBille, Colors.CYAN,xMax);
		
		
		Bille billeRepulsionRebond = new BilleSimple(p4, rayon, v4, Colors.ORANGE,xMax);
		billeRepulsionRebond = new MvmtRepulsionNewton(billeRepulsionRebond);
		billeRepulsionRebond = new CollisionBordRebond(billeRepulsionRebond);
		billeRepulsionRebond = new Hurlement(billeRepulsionRebond,cadre,hurlements[choixHurlementInitial]);
		
		Bille billePesanteurFrottementRebond = new BilleSimple(p1, rayon, v1, Colors.YELLOW,xMax);
		billePesanteurFrottementRebond = new MvmtAttractionPesanteur(billePesanteurFrottementRebond);
		billePesanteurFrottementRebond = new MvmtFrottements(billePesanteurFrottementRebond);
		billePesanteurFrottementRebond = new CollisionBordRebond(billePesanteurFrottementRebond);
		
		Bille billeNewtonFrottementRebond = new BilleSimple(p2, rayon, v2, Colors.GREEN,xMax);
		billeNewtonFrottementRebond = new MvmtAttractionNewton(billeNewtonFrottementRebond);
		billeNewtonFrottementRebond = new MvmtFrottements(billeNewtonFrottementRebond);
		billeNewtonFrottementRebond = new CollisionBordRebond(billeNewtonFrottementRebond);
		
		Bille billePasseMurailles = new BilleSimple(p3, rayon, v3, Colors.CYAN,xMax);
		billePasseMurailles = new CollisionBordFranchissement(billePasseMurailles);
		billePasseMurailles = new Hurlement(billePasseMurailles,cadre,hurlements[choixHurlementInitial]);
		
		billes.add(billePilotee);
//		billes.add(bille1);
//		billes.add(bille2);
//		billes.add(bille3);
//		billes.add(bille4);
//		billes.add(bille5);
//		billes.add(bille6);
		//billes.add(billeRepulsionRebond);
		

		
		//billes.add(new BilleMvtRURebond(p0, rayon, v0, Color.red));
		//billes.add(new BilleMvtPesanteurFrottementRebond(p1, rayon, v1, new Vecteur(0,0.001), Color.yellow));
		//billes.add(new BilleMvtNewtonFrottementRebond(p2, rayon, v2, Color.green));
		//billes.add(new BilleMvtRUPasseMurailles(p3, rayon, v3, Color.cyan));

		//BilleHurlanteMvtNewtonArret billeNoire;         // cas particulier de la bille qui hurle
		//billes.add(billeNoire = new BilleHurlanteMvtNewtonArret(p4, rayon, v4,  Color.black,hurlements[choixHurlementInitial], cadre));

		

		//---------------------- ici finit la partie a changer -------------------------------------------------------------

		System.out.println("billes = " + billes);


		//-------------------- creation de l'objet responsable de l'animation (c'est un thread separe) -----------------------

		AnimationBilles animationBilles = new AnimationBilles(billes, cadre);

		//----------------------- mise en place des ecouteurs de boutons qui permettent de controler (un peu...) l'application -----------------

		EcouteurBoutonLancer ecouteurBoutonLancer = new EcouteurBoutonLancer(animationBilles);
		EcouteurBoutonArreter ecouteurBoutonArreter = new EcouteurBoutonArreter(animationBilles); 
		EcouteurHurlement ecouteurHurlement = new EcouteurHurlement((Hurlement) billePilotee);

		//------------------------- activation des ecouteurs des boutons et ca tourne tout seul ------------------------------


		cadre.lancerBilles.addActionListener(ecouteurBoutonLancer);             // pourrait etre remplace par Observable - Observer 
		cadre.arreterBilles.addActionListener(ecouteurBoutonArreter);           // pourrait etre remplace par Observable - Observer
		cadre.addChoixHurlementListener(ecouteurHurlement);  // A present on peut changer le son de la bille qui hurle


	}

}
