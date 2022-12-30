package mesmaths.cinematique;


import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.SonBrefJavax;

import mesmaths.MesMaths;
import mesmaths.geometrie.base.Geop;
import mesmaths.geometrie.base.Vecteur;
import modele.Bille;
import modele.OutilsBilles;
import musique.SonBref;
import musique.SonBrefFantome;


public class Collisions
{

	static double EPSILON = 1.0E-6;
	private static final double COEFF_VOLUME = 1;  
	private static File file = new File(""); // la ou la JVM est lancee : racine du projet
	private static File repertoireSon = new File(file.getAbsoluteFile(),"src"+File.separatorChar+"bruits");
	static SonBref sonBref;

	/**
	 * gestion de la collision avec traversement de la paroi (et reapparition sur le cote oppose) de la bille definie par (position) 
	 *  avec un contour rectangulaire de l'ecran. 
	 *  Ce rectangle est defini par (abscisseCoinHautGauche, OrdonneeCoinHautGauche,largeur,hauteur)
	 *  si il n'y a pas collision, le vecteur position n'est pas modifie
	 * si il y a collision, le vecteur position est modifie : la bille "ressort" sur le bord oppose au bord qu'elle penetre.
	 * @param position : vecteur position de la bille
	 * 
	 * */
	public static void collisionBilleContourPasseMuraille(Vecteur position, 
			double abscisseCoinHautGauche, double ordonneeCoinHautGauche, double largeur, double hauteur)
	{
		position.x = Collisions.traverseBord(position.x,abscisseCoinHautGauche, largeur);
		position.y = Collisions.traverseBord(position.y,ordonneeCoinHautGauche, hauteur);
	}

	/*
	 * utile a la methode collisionBilleContourPasseMuraille
	 * */
	private static double traverseBord(double x, double xMin, double largeur)
	{
		double xMax = xMin + largeur;

		if (x > xMax)
		{
			double d = (x-xMin)/largeur;
			double d1 = Math.floor(d);
			int q = (int)d1;
			return x - q*largeur;
		}

		else
			if (x < xMin)
			{
				double d = (xMax-x)/largeur;
				double d1 = Math.floor(d);
				int q = (int)d1;
				return x + q*largeur;
			}
			else
				return x;
	}

	/**
	 * gestion de la collision avec arret suivant la direction horizontale  de la bille definie par (position,rayon,vitesse) avec un contour rectangulaire de l'ecran.
	 * 
	 *  Ce rectangle est defini partiellement par (abscisseCoinHautGauche,largeur)
	 * 
	 * si il n'y a pas de collision avec un bord vertical, position et vitesse sont inchanges par la methode
	 * 
	 *  si il y a collision avec un bord vertical
	 * alors vitesse est modifie et  position reste intact
	 * 
	 * Des qu'il y a collision avec un bord vertical, la composante horizontale du vecteur vitesse est annul�e, de sorte que la bille continue a glisser le long de
	 * la bande qui l'a arretee
	 * 
	 */
	public static void collisionBilleContourAvecArretHorizontal(Vecteur position, double rayon, Vecteur vitesse,
			double abscisseCoinHautGauche, double largeur)
	{
		//vitesse.x = Collisions.arretSurBord(vitesse.x, position.x, rayon, abscisseCoinHautGauche, largeur);
		double t[] = Collisions.arretSurBord(vitesse.x, position.x, rayon, abscisseCoinHautGauche, largeur);
		vitesse.x = t[0];
		position.x = t[1];
		try
		{
			sonBref = new SonBrefJavax(repertoireSon,"collision_bille_bille.wav",50,100);
		}
		catch (Exception e)
		{
			//System.err.println("son non créé");
			System.err.println(e);
			sonBref = new SonBrefFantome();
		}
		double n = vitesse.norme();
		double y = Math.exp(-COEFF_VOLUME*n);
		double volume = 1-y;
		double balance = 0;
		//sonBref.joue(volume, balance);
	}

	/**
	 * gestion de la collision avec arret suivant la direction verticale  de la bille definie par (position,rayon,vitesse) avec un contour rectangulaire de l'ecran.
	 * 
	 *  Ce rectangle est defini partiellement par (ordonneeCoinHautgauche,hauteur)
	 * 
	 * si il n'y a pas de collision avec un bord horizontal, position et vitesse sont inchanges par la methode
	 * 
	 *  si il y a collision avec un bord horizontal
	 * alors vitesse est modifie et  position reste intact
	 * 
	 * D�s qu'il y a collision avec un bord horizontal, la composante verticale du vecteur vitesse est annulee, de sorte que la bille continue a glisser le long de
	 * la bande qui l'a arretee
	 * 
	 */
	public static void collisionBilleContourAvecArretVertical(Vecteur position, double rayon, Vecteur vitesse,
			double ordonneeCoinHautGauche, double hauteur)
	{
		//vitesse.y = Collisions.arretSurBord(vitesse.y, position.y, rayon, ordonneeCoinHautGauche, hauteur);
		double t[] = Collisions.arretSurBord(vitesse.y, position.y, rayon, ordonneeCoinHautGauche, hauteur);
		vitesse.y = t[0];
		position.y = t[1];
		try
		{
			sonBref = new SonBrefJavax(repertoireSon,"collision_bille_bille.wav",50,100);
		}
		catch (Exception e)
		{
			//System.err.println("son non créé");
			System.err.println(e);
			sonBref = new SonBrefFantome();
		}
		double n = vitesse.norme();
		double y = Math.exp(-COEFF_VOLUME*n);
		double volume = 1-y;
		double balance = 0;
		//sonBref.joue(volume, balance);
	}



	/*
	 * utile a collisionBilleContourAvecArretHorizontal et a collisionBilleContourAvecArretVertical
	 * */
	private static double [] arretSurBord(double v, double x, double rayon, double xMin, double largeur)
	{
		double [] t = new double[2]; //  nouvelle composante_vitesse dans t[0] et nouvelle composante_position dans t[1]

		double xMax = xMin+largeur;


		if (x+rayon > xMax)
		{
			t[0] = 0;
			t[1] = xMax-rayon-Collisions.EPSILON;
		}
		else
			if (x-rayon < xMin)
			{
				t[0] = 0;
				t[1] = xMin + Collisions.EPSILON + rayon;
			} 
			else
			{
				t[0] = v; t[1] = x; 
			}

		return t;
	}

	/**
	 * gestion de la collision avec rebond de la bille definie par (position,rayon,vitesse) avec le segment orient� [P0P1]
	 * 
	 * @return false si il n'y a pas de collision
	 * 
	 * @return true si il y a collision et dans ce cas modifie vitesse
	 * 
	 * 
	 * c-a-d que en entree on considere que (position,vitesse) sont le vecteur position et le vecteur vitesse de la bille immediatement avant le choc
	 * et en sortie (position,vitesse) sont le vecteur position et le vecteur vitesse de la bille immediatement apres le choc
	 * 
	 * le vecteur vitesse est modifie par la collision (comme une boule de billard l'est par une bande)
	 * La methode laisse le vecteur position intact 
	 * @param position : vecteur position de la bille immediatement avant la collision avec le segment [P0P1]
	 * @param rayon : rayon de la bille
	 * @param vitesse : vecteur vitesse de la bille immediatement avant la collision avec le segment [P0P1]
	 * @param P0 : le debut du segment
	 * @param P1 : la fin du segment
	 * 
	 * */
	public static boolean collisionBilleSegmentAvecRebond( final Vecteur position,  double rayon, Vecteur vitesse, final Vecteur P0, final Vecteur P1)
	{
		Vecteur[] base = Geop.base( P0,  P1);
		//Vecteur I = base[0];
		Vecteur N = base[1];

		double distanceSignee = position.difference(P0).produitScalaire(N);

		if (distanceSignee >= rayon)  return false; // il n'y a pas collision entre la bille (position,rayon) et le segment [P0P1]

		double vN = vitesse.produitScalaire(N);

		if (vN >= 0) return false; // la bille est a l'exterieur et revient vers le cadre ou est a l'interieur et s'�loigne du bord

		// a present distanceSignee < rayon et vN < 0

		// -vN est l'intensit�e du choc !!!!

		Vecteur deltaV = N.produit(-2*vN);  // calcul du changement de trajectoire

		vitesse.ajoute(deltaV); // la bille rebondit
		try
		{
			sonBref = new SonBrefJavax(repertoireSon,"collision_bille_bille.wav",50,100);
		}
		catch (Exception e)
		{
			//System.err.println("son non créé");
			System.err.println(e);
			sonBref = new SonBrefFantome();
		}
		double n = vitesse.norme();
		double y = Math.exp(-COEFF_VOLUME*n);
		double volume = 1-y;
		double balance = 0;
		sonBref.joue(volume, balance);
		return true;

	}                           // collisionBilleSegmentAvecRebond

	/**
	 * gestion de la collision avec rebond de la bille definie par (position,rayon,vitesse) avec un contour rectangulaire de l'�cran.
	 * 
	 *  Ce rectangle est defini par (abscisseCoinHautGauche, OrdonneeCoinHautGauche,largeur,hauteur)
	 * 
	 * @return false si il n'y a pas de collision
	 * 
	 * @return true si il y a collision et dans ce cas modifie vitesse
	 * 
	 * c-a-d que en entree on considere que (position,vitesse) sont le vecteur position et le vecteur vitesse de la bille immediatement avant le choc
	 * et en sortie (position,vitesse) sont le vecteur position et le vecteur vitesse de la bille immediatement apres le choc
	 * 
	 * le vecteur vitesse est modifie par la collision (comme une boule de billard l'est par une bande)
	 * La methode laisse le vecteur position intact 
	 * 
	 * @param position : vecteur position de la bille immediatement avant la collision avec le contour
	 * @param rayon : rayon de la bille
	 * @param vitesse : vecteur vitesse de la bille immediatement avant la collision avec le contour
	 * @param abscisseCoinHautGauche : abscisse minimale du contour rectangulaire
	 * @param ordonn�eCoinHautGauche : ordonnee minimale du contour rectangulaire
	 * @param largeur : largeur du contour rectangulaire
	 * @param hauteur : hauteur du contour rectangulaire
	 * 
	 * 
	 * */
	public static boolean collisionBilleContourAvecRebond( final Vecteur position,  double rayon, Vecteur vitesse, 
			double abscisseCoinHautGauche, double ordonneeCoinHautGauche, double largeur, double hauteur)
	{
		Vecteur min = new Vecteur(abscisseCoinHautGauche,ordonneeCoinHautGauche);
		Vecteur diago = new Vecteur(largeur, hauteur);
		Vecteur max = min.somme(diago); 


		Vecteur coins[] = new Vecteur[5];

		coins[0] = min;         // le coin haut gauche du rectangle d�fini par le composant
		coins[1] = new Vecteur(max.x,min.y);
		coins[2] = max;
		coins[3] = new Vecteur(min.x, max.y);
		coins[4] = coins[0];                    // pour refermer le contour !
		try
		{
			//sonBref = new SonBrefJavax(repertoireSon,"collision_bille_bille.wav",0,100);
			sonBref = new SonBrefJavax(repertoireSon,"collision_bille_bille.wav",50,100);
		}
		catch (Exception e)
		{
			//System.err.println("son non créé");
			System.err.println(e);
			sonBref = new SonBrefFantome();
		}
		double n = vitesse.norme();
		double y = Math.exp(-COEFF_VOLUME*n);
		double volume = 1-y;
		double balance = 0;
		
		int i;
		for ( i = 1; i < coins.length; ++i)
			if (Collisions.collisionBilleSegmentAvecRebond(position, rayon, vitesse, coins[i-1], coins[i])) { 
				sonBref.joue(volume, balance);
				return true;
			}
		return false;
	}

	/**
	 * Effectue les calculs correspondants a la collision entre la bille b1(G1,rayon1,v1,m1) et la bille b2(G2,rayon2,v2,m2)
	 * 
	 * La methode laisse toujours G1 et G2 intacts.
	 *
	 * @param G1 : centre de la bille b1
	 * @param rayon1 : rayon de la bille b1
	 * @param v1 : vecteur vitesse de la bille b1
	 * @param m1 : masse de la bille b1
	 * 
	 * @param G2 : centre de la bille b2
	 * @param rayon2 : rayon de la bille b2
	 * @param v2 : vecteur vitesse de la bille b2
	 * @param m2 : masse de la bille b2
	 * 
	 * @return true si il y a collision et false si il n'y a pas collision
	 * 
	 * si retourne true, modifie les vecteurs vitesse v1 et v2
	 */
	public static boolean CollisionBilleBille( final Vecteur G1, double rayon1, Vecteur v1, double m1, 
			final Vecteur G2, double rayon2, Vecteur v2, double m2)   
	//modifie b1 et b2
	//donnees : b1 et b2 avant le choc
	//r�sultats : b1 et b2 apres le choc
	{
		Vecteur G1G2 = Vecteur.difference(G2, G1);

		double nG1G2_2 = G1G2.normeCarree();

		double r = rayon1+rayon2;
		double r2 = r*r;

		if ( nG1G2_2 >= r2 )  return false;   // il n'y a pas de collision entre les 2 billes car elles sont trop eloignees l'une de l'autre

		double  nG1G2 = Math.sqrt(nG1G2_2);

		Vecteur N = G1G2.produit(1 / nG1G2);    // vecteur unitaire sur le segment [G1 G2]

		double v1N, v2N;

		v1N = v1.produitScalaire(N);
		v2N = v2.produitScalaire(N);

		double a = v1N - v2N;

		if (a <= 0) return false;       // les billes s eloignent l'une de l'autre, la collision a donc deja ete traitee

		// a present a > 0. a represente l'intensit� du choc entre les 2 billes !!!!

		// calculons les nouveaux vecteurs vitesse immediatement apres le choc

		double masseTotale = m1+m2;
		double alfa = (m1-m2)/masseTotale;
		double deuxSurM = 2/masseTotale;

		double v1Np =          alfa * v1N + m2 * deuxSurM * v2N;
		double v2Np = m1 * deuxSurM * v1N -          alfa * v2N;

		Vecteur U = N.rotationQuartDeTour();

		double v1T = v1.produitScalaire(U);
		double v2T = v2.produitScalaire(U);

		v1.set(Vecteur.combinaisonLineaire(v1Np,  N , v1T, U));
		v2.set(Vecteur.combinaisonLineaire(v2Np,  N , v2T, U));

		return true;

	} // collisionBilleBille

} // Collisions
