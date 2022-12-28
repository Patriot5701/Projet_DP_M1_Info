package modele;

import java.io.File;
import java.util.Vector;

import javax.SonBrefJavax;

import controleur.OutilsConfigurationBilleHurlante;
import mesmaths.cinematique.Collisions;
import mesmaths.geometrie.base.Vecteur;
import mesmaths.mecanique.MecaniquePoint;
import musique.SonBref;
import musique.SonBrefFantome;
import musique.SonLong;

/**
 * 
 * 
 * Operations utiles sur les billes
 * 
 *  ICI : IL N'Y A RIEN A CHANGER 
 *  
 *  */

public class OutilsBilles
{
	private static final double COEFF_VOLUME = 10;  
	private static File file = new File(""); // la ou la JVM est lancee : racine du projet
	private static File repertoireSon = new File(file.getAbsoluteFile(),"src"+File.separatorChar+"bruits");
	private Vector<SonLong> sonsLongs = OutilsConfigurationBilleHurlante.chargeSons(repertoireSon, "config_audio_bille_hurlante.txt");
	static SonBref sonBref;
	/**
	 * @param billes est la liste de TOUTES les billes en mouvement
	 * @param cetteBille est l'une d'entre elles.
	 * @return la liste des autres billes que cetteBille, c'est-a-dire la liste de toutes les billes sauf cetteBille 
	 * 
	 * */
	public static Vector<Bille> autresBilles(Bille cetteBille, Vector<Bille> billes)
	{
		Vector<Bille> autresBilles = new Vector<Bille>();

		Bille billeCourante;

		int i;

		for( i = 0; i < billes.size(); ++i)
		{
			billeCourante = billes.get(i);
			if ( billeCourante.getClef() != cetteBille.getClef())
				autresBilles.add(billeCourante);
		}

		return autresBilles;
	}


	/**
	 * @param cetteBille : une bille particuliere
	 * @param billes : une liste de billes, cette liste peut contenir cettebille
	 *
	 * gestion de l'eventuelle  collision de cette bille avec les autres billes
	 *
	 * billes est la liste de toutes les billes en mouvement
	 * 
	 * Le comportement par defaut est le choc parfaitement elastique (c-a-d rebond sans amortissement)
	 * 
	 * @return true si il y a collision et dans ce cas les positions et vecteurs vitesses des 2 billes impliquees dans le choc sont modifiees
	 * si renvoie false, il n'y a pas de collision et les billes sont laissees intactes 
	 * */
	public static  boolean gestionCollisionBilleBille(Bille cetteBille, Vector<Bille> billes)
	{
		//--- on r�cup�re d'abord dans autresBilles toutes les billes sauf cetteBille ----

		Vector<Bille> autresBilles = OutilsBilles.autresBilles(cetteBille, billes);

		//--- on cherche a present la 1ere des autres billes avec laquelle cetteBille est en collision ---------------------
		//-------------- on suppose qu'il ne peut y avoir de collision qui implique plus de deux billes a la fois ---------------
		try
		{
			sonBref = new SonBrefJavax(repertoireSon,"impact.wav",0,15);
		}
		catch (Exception e)
		{
			//System.err.println("son non créé");
			System.err.println(e);
			sonBref = new SonBrefFantome();
		}
		 
		Bille billeCourante;
		double n = cetteBille.getVitesse().norme();
		double y = Math.exp(-COEFF_VOLUME*n);
		double xMax = cetteBille.getXMax();
		double x1 = Math.abs(cetteBille.getPosition().x/xMax);                   /* on obtient 0 <= x1 <= 1 */ ////System.err.println("dans BilleHurlante.deplacer() : x1 =  "+ x1);
		double balance = 2*x1 - 1; 
		double volume = 1-y;
		//double balance = 0;
		int i;

		for ( i = 0 ; i < autresBilles.size(); ++i)
		{
			billeCourante = autresBilles.get(i);
			if (Collisions.CollisionBilleBille(    cetteBille.getPosition(),    cetteBille.getRayon(),    cetteBille.getVitesse(),    cetteBille.getMasse(), 
					billeCourante.getPosition(), billeCourante.getRayon(), billeCourante.getVitesse(), billeCourante.getMasse())) {
				OutilsBilles.sonBref.joue(volume, balance);
				return true; 
			}
		}
		return false;
	}


	/**
	 * @param cetteBille : une bille particuliere
	 * @param billes : une liste de billes, cette liste peut contenir cettebille
	 * 
	 * On suppose que cetteBille subit l'attraction gravitationnelle due aux billes contenues dans "billes" autres que cetteBille.
	 * 
	 * tache : calcule a, le vecteur acceleration subi par cetteBille resultant de l'attraction par les autres billes de la liste.
	 * 
	 * @return a : le vecteur acceleration r�sultant
	 * 
	 * */
	public static Vecteur gestionAccelerationNewton(Bille cetteBille, Vector<Bille> billes)
	{

		//--- on r�cup�re d'abord dans autresBilles toutes les billes sauf celle-ci ----

		Vector<Bille> autresBilles = OutilsBilles.autresBilles(cetteBille, billes);

		//-------------- � present on recupere les masses et les positions des autres billes ------------------
		int i;
		Bille billeCourante;

		int d = autresBilles.size();

		double masses [] = new double[d];   // les masses des autres billes
		Vecteur C [] = new Vecteur[d];      // les positions des autres billes

		for ( i = 0; i < d; ++i)
		{
			billeCourante = autresBilles.get(i);
			masses[i] = billeCourante.getMasse();
			C[i] = billeCourante.getPosition();
		}

		//------------------ � pr�sent on calcule le champ de gravite exerce par les autres billes sur cette bille ------------------

		return  MecaniquePoint.champGraviteGlobal( cetteBille.getPosition(),  masses, C);
	}
}
