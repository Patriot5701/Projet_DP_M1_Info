package mesmaths.cinematique.brouillon;


import java.io.IOException;

import mesmaths.MesMaths;
import mesmaths.geometrie.base.Geop;
import mesmaths.geometrie.base.Vecteur;


public class Collisions
{
	public static double COEFF_ELASTICITE_BILLE = 50;
	public static double COEFF_ELASTICITE_PAROI = 120;
	static double EPSILON = 1.0E-6;
	static final double EPSILON_CHOC_BILLE = 1.0E-3;  // en deca de cette valeur pour |u.(v1-v2)| le choc entre 2 billes est consid�r� statique
	static final double EPSILON_CHOC_PAROI = 1.0E-3;  // en deca de cette valeur pour |J.v| le choc entre une bille et la paroi est consid�r� statique 

	/**
	 * gestion de la collision dynamique (avec rebond) ou statique  de la bille d�finie par (position, rayon, vitesse, acceleration) avec le segment orient� [P0P1]
	 * 
	 * @return false si il n'y a pas de collision
	 * 
	 * @return true si il y a collision et dans ce cas modifie soit vitesse soit acceleration
	 * 
	 * 
	 * c-�-d que en entr�e on consid�re que (position,vitesse, acceleration) sont le vecteur position, le vecteur vitesse et le vecteur acceleration de la bille imm�diatement avant le choc
	 * et en sortie (position,vitesse, acceleration) sont le vecteur position, le vecteur vitesse et le vecteur acceleration de la bille imm�diatement apr�s le choc
	 * 
	 * si le choc est dynamique, le vecteur vitesse est modifi� par la collision (comme une boule de billard l'est par une bande)
	 * si le choc est statique, le vecteur acceleration est modifi� (on consid�re que le choc est �lastique et une force de rappel est appliqu�e � la bille)
	 * 
	 * Dans tous les cas, le vecteur position est laiss� intact par la collision,
	 * 
	 * @param position : vecteur position de la bille imm�diatement avant la collision avec le segment [P0P1]
	 * @param rayon : rayon de la bille
	 * @param vitesse : vecteur vitesse de la bille imm�diatement avant la collision avec le segment [P0P1]
	 * @param acceleration : vecteur vitesse de la bille imm�diatement avant la collision avec le segment [P0P1]
	 * @param P0 : le d�but du segment
	 * @param P1 : la fin du segment
	 * 
	 * */
	static boolean actionReactionBilleSegmentAvecRebond2( Vecteur position, double rayon, Vecteur vitesse, Vecteur acceleration, double masse, Vecteur P0, Vecteur P1)
	{
		Vecteur[] base = Geop.base( P0,  P1);
		Vecteur I = base[0];
		Vecteur J = base[1];

		double d = position.difference(P0).produitScalaire(J);

		if (d <= rayon) // il y a collision entre la bille (position,rayon) et le segment [P0P1]
		{                                                            ////System.err.println("intersection avec : "+ P0 +" et "+ P1 );
			double vJ = vitesse.produitScalaire(J);

			if (vJ < -Collisions.EPSILON_CHOC_PAROI) // la bille sort du contour, le choc est dynamique
			{ 
				// d'abord calcul du vecteur vitesse r�fl�chi

				Vecteur vS = Vecteur.combinaisonLineaire(1, vitesse, -2*vJ, J);
				vitesse.set(vS);


				// � pr�sent calcul de la nouvelle position : qui n'est plus utilis�e

				double  d1 = rayon + EPSILON;

				double deltaT = (d1 - d) / vitesse.produitScalaire(J);
				//position.ajoute(vitesse.produit(deltaT));

				//System.err.println("choc rapide avec la paroi" );
				return true;
			}
			else
				if (vJ > Collisions.EPSILON_CHOC_PAROI)               // la bille revient : on n'a rien � faire
				{                                                  //System.err.println("une bille rentre");
					return false;
				}
				else                                             // choc sans vitesse
				{
					double e = rayon - d;                    // profondeur de la pr�n�tration de la bille dans le mur

					//e = Math.log(1+e);

					double forceRappel = Collisions.COEFF_ELASTICITE_PAROI*e;

					Vecteur a = J.produit(forceRappel/masse);
					//System.err.println("choc mou avec la paroi" );
					acceleration.ajoute(a);
					return true;
				}

		} // il y a collision 

		else                // il n'y a pas de collision entre la bille et le bord
			return false;
	}                           // actionReactionBilleSegmentAvecRebond1

	/**
	 * 
	 * ESSAI : pas au point
	 * 
	 * gestion de la collision statique de la bille d�finie par (position,rayon,vitesse) avec le segment orient� [P0P1]
	 * 
	 * @return false si il n'y a pas de collision
	 * 
	 * @return true si il y a collision et dans ce cas modifie (position,vitesse)
	 * 
	 * 
	 * c-�-d que en entr�e on consid�re que (position,vitesse) sont le vecteur position et le vecteur vitesse de la bille imm�diatement avant le choc
	 * et en sortie (position,vitesse) sont le vecteur position et le vecteur vitesse de la bille imm�diatement apr�s le choc
	 * 
	 * le vecteur vitesse est modifi� par la collision (comme une boule de billard l'est par une bande)
	 * le vecteur position est modifi� : la bille "ressort" du bord. En effet, en entr�e si on d�tect� une collision, c'�tait parce que la bille avait l�g�rement p�n�tr�
	 * la paroi, il fallait donc la ressortir suivant sa nouvelle trajectoire pour un meilleur r�alisme
	 * 
	 * @param position : vecteur position de la bille imm�diatement avant la collision avec le segment [P0P1]
	 * @param rayon : rayon de la bille
	 * @param vitesse : vecteur vitesse de la bille imm�diatement avant la collision avec le segment [P0P1]
	 * @param P0 : le d�but du segment
	 * @param P1 : la fin du segment
	 * 
	 * */
	static boolean actionReactionBilleSegmentStatique( Vecteur position, double rayon, Vecteur acceleration, double masse, Vecteur P0, Vecteur P1)
	{
		Vecteur[] base = Geop.base( P0,  P1);
		Vecteur I = base[0];
		Vecteur J = base[1];

		double d = position.difference(P0).produitScalaire(J);

		if (d <= rayon) // il y a collision entre la bille (position,rayon) et le segment [P0P1]
		{
			double e = rayon - d;                    // profondeur de la pr�n�tration de la bille dans le mur

			e = Math.log(1+e);

			double forceRappel = Collisions.COEFF_ELASTICITE_PAROI*e;

			Vecteur a = J.produit(forceRappel/masse);
			////System.err.println("intersection avec : "+ P0 +" et "+ P1 );
			acceleration.ajoute(a);
			return true;
		} // il y a collision 

		else                // il n'y a pas de collision entre la bille et le bord
			return false;
	}                           // actionReactionBilleSegmentAvecRebond



	/**
	 * gestion de la collision avec rebond de la bille d�finie par (position,rayon,vitesse) avec le segment orient� [P0P1]
	 * 
	 * @return false si il n'y a pas de collision
	 * 
	 * @return true si il y a collision et dans ce cas modifie (position,vitesse)
	 * 
	 * 
	 * c-�-d que en entr�e on consid�re que (position,vitesse) sont le vecteur position et le vecteur vitesse de la bille imm�diatement avant le choc
	 * et en sortie (position,vitesse) sont le vecteur position et le vecteur vitesse de la bille imm�diatement apr�s le choc
	 * 
	 * le vecteur vitesse est modifi� par la collision (comme une boule de billard l'est par une bande)
	 * le vecteur position est modifi� : la bille "ressort" du bord. En effet, en entr�e si on d�tect� une collision, c'�tait parce que la bille avait l�g�rement p�n�tr�
	 * la paroi, il fallait donc la ressortir suivant sa nouvelle trajectoire pour un meilleur r�alisme
	 * 
	 * @param position : vecteur position de la bille imm�diatement avant la collision avec le segment [P0P1]
	 * @param rayon : rayon de la bille
	 * @param vitesse : vecteur vitesse de la bille imm�diatement avant la collision avec le segment [P0P1]
	 * @param P0 : le d�but du segment
	 * @param P1 : la fin du segment
	 * 
	 * */
	static boolean collisionBilleSegmentAvecRebond( Vecteur position, double rayon, Vecteur vitesse, Vecteur P0, Vecteur P1)
	{
		Vecteur[] base = Geop.base( P0,  P1);
		Vecteur I = base[0];
		Vecteur J = base[1];

		double d = position.difference(P0).produitScalaire(J);

		if (d <= rayon) // il y a collision entre la bille (position,rayon) et le segment [P0P1]
		{                                                            ////System.err.println("intersection avec : "+ P0 +" et "+ P1 );


			vitesse.set( Geop.reflechi( vitesse, I, J));                    ////System.err.println("v r�fl�chi = " + vitesse);

			double  d1;


			d1 = rayon + EPSILON;

			if (vitesse.norme() < Collisions.EPSILON) // choc mou
			{                                                                         ////System.err.println("choc mou au bord");
				double t = d1 - d;
				position.ajoute(J.produit(t));
			}
			else                                     // choc �lastique
			{
				double deltaT = (d1 - d) / vitesse.produitScalaire(J);
				position.ajoute(vitesse.produit(deltaT));
			}
			return true;
		} // il y a collision 

		else                // il n'y a pas de collision entre la bille et le bord
			return false;
	}                           // collisionBilleSegmentAvecRebond






	static boolean collisionBilleSegmentAvecRebond1( Vecteur position, double rayon, Vecteur vitesse, Vecteur P0, Vecteur P1)
	{
		double [] t = Geop.intersectionSegmentCercle( P0, P1, position, rayon);

		if (t.length == 2)
		{                                                            ////System.err.println("intersection avec : "+ P0 +" et "+ P1 );
			Vecteur[] base = Geop.base( P0,  P1);
			Vecteur I = base[0];
			Vecteur J = base[1];

			vitesse.set( Geop.reflechi( vitesse, I, J));                    ////System.err.println("v r�fl�chi = " + vitesse);

			double d, d1;

			d = position.difference(P0).produitScalaire(J);
			d1 = rayon + EPSILON;



			double deltaT = (d1-d)/vitesse.produitScalaire(J);

			position.ajoute(vitesse.produit(deltaT));

			return true;
		} 

		else                // il n'y a pas de collision entre la bille et le bord
			return false;
	}

	/**
	 * gestion de la collision avec rebond de la bille d�finie par (position,rayon,vitesse) avec un contour rectangulaire de l'�cran.
	 * 
	 *  Ce rectangle est d�fini par (abscisseCoinHautGauche, Ordonn�eCoinHautGauche,largeur,hauteur)
	 * 
	 * @return false si il n'y a pas de collision
	 * 
	 * @return true si il y a collision et dans ce cas modifie (position,vitesse)
	 * 
	 * c-�-d que en entr�e on consid�re que (position,vitesse) sont le vecteur position et le vecteur vitesse de la bille imm�diatement avant le choc
	 * et en sortie (position,vitesse) sont le vecteur position et le vecteur vitesse de la bille imm�diatement apr�s le choc
	 * 
	 * le vecteur vitesse est modifi� par la collision (comme une boule de billard l'est par une bande)
	 * le vecteur position est modifi� : la bille "ressort" du bord. En effet, en entr�e si on d�tect� une collision, c'�tait parce que la bille avait l�g�rement p�n�tr�
	 * la paroi, il fallait donc la ressortir suivant sa nouvelle trajectoire pour un meilleur r�alisme
	 * 
	 * @param position : vecteur position de la bille imm�diatement avant la collision avec le contour
	 * @param rayon : rayon de la bille
	 * @param vitesse : vecteur vitesse de la bille imm�diatement avant la collision avec le contour
	 * @param abscisseCoinHautGauche : abscisse minimale du contour rectangulaire
	 * @param ordonn�eCoinHautGauche : ordonn�e minimale du contour rectangulaire
	 * @param largeur : largeur du contour rectangulaire
	 * @param hauteur : hauteur du contour rectangulaire
	 * 
	 * 
	 * */
	public static boolean collisionBilleContourAvecRebond( Vecteur position, double rayon, Vecteur vitesse, 
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

		int i;
		for ( i = 1; i < coins.length; ++i)
			if (Collisions.collisionBilleSegmentAvecRebond(position, rayon, vitesse, coins[i-1], coins[i])) 
				return true;

		return false;
	}

	/**
	 * gestion de la collision dynamique (avec rebond) ou statique  de la bille d�finie par (position,rayon,vitesse,acceleration) avec un contour rectangulaire de l'�cran.
	 * 
	 *  Ce rectangle est d�fini par (abscisseCoinHautGauche, Ordonn�eCoinHautGauche,largeur,hauteur)
	 * 
	 * @return false si il n'y a pas de collision
	 * 
	 * @return true si il y a collision et dans ce cas modifie vitesse ou acceleration
	 * 
	 * c-�-d que en entr�e on consid�re que (position,vitesse,acceleration) sont le vecteur position, le vecteur vitesse et le vecteur acceleration de la bille imm�diatement avant le choc
	 * et en sortie (position,vitesse,acceleration) sont le vecteur position, le vecteur vitesse et le vecteur acceleration de la bille imm�diatement apr�s le choc
	 * 
	 * si la collision est dynamique, le vecteur vitesse est modifi� par la collision (comme une boule de billard l'est par une bande)
	 * si la collision est statique, le vecteur acceleration est modifi� par la collision (une force de rappel est appliqu�e � la bille)
	 * 
	 * @param position : vecteur position de la bille imm�diatement avant la collision avec le contour
	 * @param rayon : rayon de la bille
	 * @param vitesse : vecteur vitesse de la bille imm�diatement avant la collision avec le contour
	 * @param acceleration : vecteur acceleration de la bille imm�diatement avant la collision avec le contour
	 * @param abscisseCoinHautGauche : abscisse minimale du contour rectangulaire
	 * @param ordonn�eCoinHautGauche : ordonn�e minimale du contour rectangulaire
	 * @param largeur : largeur du contour rectangulaire
	 * @param hauteur : hauteur du contour rectangulaire
	 * 
	 * 
	 * */
	public static boolean actionReactionBilleContourAvecRebond1( Vecteur position, double rayon, Vecteur vitesse, Vecteur acceleration, double masse, 
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

		int i;

		boolean collision;

		for ( i = 1, collision = false; i < coins.length; ++i)
			if (Collisions.actionReactionBilleSegmentAvecRebond1(position, rayon, vitesse, acceleration, masse, coins[i-1], coins[i])) 
				collision = true;

		return collision;
	}                   // actionReactionBilleContourAvecRebond1

	/**
	 * gestion de la collision avec traversement de la paroi (et r�apparition sur le cot� oppos�) de la bille d�finie par (position) 
	 *  avec un contour rectangulaire de l'�cran. 
	 *  Ce rectangle est d�fini par (abscisseCoinHautGauche, Ordonn�eCoinHautGauche,largeur,hauteur)
	 *  si il n'y a pas collision, le vecteur position n'est pas modifi�
	 * si il y a collision, le vecteur position est modifi� : la bille "ressort" sur le bord oppos� au bord qu'elle p�n�tre.
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
	 * utile � la m�thode collisionBilleContourPasseMuraille
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
	 * gestion de la collision avec arr�t suivant la direction horizontale  de la bille d�finie par (position,rayon,vitesse) avec un contour rectangulaire de l'�cran.
	 * 
	 *  Ce rectangle est d�fini partiellement par (abscisseCoinHautGauche,largeur)
	 * 
	 * si il n'y a pas de collision avec un bord vertical, position et vitesse sont inchang�s par la m�thode
	 * 
	 *  si il y a collision avec un bord vertical
	 * alors vitesse est modifi� et  position reste intact
	 * 
	 * D�s qu'il y a collision avec un bord vertical, la composante horizontale du vecteur vitesse est annul�e, de sorte que la bille continue � glisser le long de
	 * la bande qui l'a arr�t�e
	 * 
	 */
	public static void collisionBilleContourAvecArretHorizontal(Vecteur position, double rayon, Vecteur vitesse,
			double abscisseCoinHautGauche, double largeur)
	{
		//vitesse.x = Collisions.arretSurBord(vitesse.x, position.x, rayon, abscisseCoinHautGauche, largeur);
		double t[] = Collisions.arretSurBord(vitesse.x, position.x, rayon, abscisseCoinHautGauche, largeur);
		vitesse.x = t[0];
		position.x = t[1];
	}

	/**
	 * gestion de la collision avec arr�t suivant la direction verticale  de la bille d�finie par (position,rayon,vitesse) avec un contour rectangulaire de l'�cran.
	 * 
	 *  Ce rectangle est d�fini partiellement par (ordonn�eCoinHautgauche,hauteur)
	 * 
	 * si il n'y a pas de collision avec un bord horizontal, position et vitesse sont inchang�s par la m�thode
	 * 
	 *  si il y a collision avec un bord horizontal
	 * alors vitesse est modifi� et  position reste intact
	 * 
	 * D�s qu'il y a collision avec un bord horizontal, la composante verticale du vecteur vitesse est annul�e, de sorte que la bille continue � glisser le long de
	 * la bande qui l'a arr�t�e
	 * 
	 */
	public static void collisionBilleContourAvecArretVertical(Vecteur position, double rayon, Vecteur vitesse,
			double ordonneeCoinHautGauche, double hauteur)
	{
		//vitesse.y = Collisions.arretSurBord(vitesse.y, position.y, rayon, ordonn�eCoinHautGauche, hauteur);
		double t[] = Collisions.arretSurBord(vitesse.y, position.y, rayon, ordonneeCoinHautGauche, hauteur);
		vitesse.y = t[0];
		position.y = t[1];
	}

	/*
	 * utile � collisionBilleContourAvecArretHorizontal et � collisionBilleContourAvecArretVertical
	 * */
	private static double arretSurBord1(double v, double x, double rayon, double xMin, double largeur)
	{
		double xMax = xMin+largeur;

		if (x+rayon > xMax   || x-rayon < xMin) 
			return 0;
		else
			return v;
	}

	/*
	 * utile � collisionBilleContourAvecArretHorizontal et � collisionBilleContourAvecArretVertical
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
	 * g�re la collision des 2 billes d�finies respectivement par 
	 * ( position1, rayon1, vitesse1, masse1 ) et par ( position2, rayon2, vitesse2,  masse2)
	 * 
	 * si il n'y a pas de collision
	 * renvoie false et ne modifie rien
	 * si il y a collision renvoie true et 
	 * modifie position1, vitesse1, position2, vitesse2.
	 * Les nouvelles positions et nouveaux vecteurs vitesses sont calcul�s pour repr�senter l'�tat des billes imm�diatement apr�s le choc  
	 * 
	 * 
	 * 
	 * */
	private static boolean CollisionBilleBille1( Vecteur position1, double rayon1, Vecteur vitesse1, double masse1, 
			Vecteur position2, double rayon2, Vecteur vitesse2, double masse2)   
	// modifie b1 et b2
	// donn�es : b1 et b2 avant le choc
	// r�sultats : b1 et b2 apr�s le choc
	{
		Vecteur G1G2;
		double nG1G2;
		G1G2 = Vecteur.difference(position2, position1);
		nG1G2 = G1G2.norme();
		Vecteur u = G1G2.produit(1 / nG1G2);
		Vecteur v1 = vitesse1;
		Vecteur v2 = vitesse2;

		Vecteur v1_v2 = Vecteur.difference(v1, v2);
		double a = Vecteur.produitScalaire(u, v1_v2);

		double r = rayon1+rayon2;

		if ( ! (nG1G2 < r && a > Collisions.EPSILON) )     // il n'y a pas de collision entre les 2 billes
			return false;
		else                // il y a collision entre les 2 billes
		{

			//d'abord calcul des vitesses apr�s le choc

			Vecteur  vs1, vs2;
			double t, m1, m2;

			m1 = masse1;
			m2 = masse2;


			t = 2 * (m1 / (m1 + m2)) * a;
			vs1 = Vecteur.combinaisonLineaire(1, v1, -(m2 / m1) * t, u);
			vs2 = Vecteur.combinaisonLineaire(1, v2, t, u); 

			vitesse1.set(vs1);                  // vecteur vitesse de la bille 1 apr�s le choc
			vitesse2.set(vs2);                  // vecteur vitesse de la bille 2 apr�s le choc

			// � pr�sent calcul des positions apr�s le choc

			double l = r + Collisions.EPSILON;
			Vecteur vs2_vs1 = vs2.difference(vs1);

			double a0, b0, c0;

			a0 = vs2_vs1.normeCarree();
			b0 = 2*Vecteur.produitScalaire(vs2_vs1, G1G2);
			c0 = nG1G2*nG1G2 - l*l;
			double deltaT[] = MesMaths.resoudre(a0, b0, c0);

			double dT = deltaT[1];

			position1.ajoute(vs1.produit(dT));
			position2.ajoute(vs2.produit(dT));

			return true;
		}
	} // collisionBilleBille1

	public static boolean CollisionBilleBille2( Vecteur position1, double rayon1, Vecteur vitesse1, double masse1, 
			Vecteur position2, double rayon2, Vecteur vitesse2, double masse2)   
	//modifie b1 et b2
	//donn�es : b1 et b2 avant le choc
	//r�sultats : b1 et b2 apr�s le choc
	{
		Vecteur G1G2;
		double nG1G2;
		G1G2 = Vecteur.difference(position2, position1);
		nG1G2 = G1G2.norme();

		double r = rayon1+rayon2;

		if ( nG1G2 >= r  || nG1G2 < Collisions.EPSILON)     // il n'y a pas de collision entre les 2 billes
			return false;

		else
		{
			Vecteur u = G1G2.produit(1 / nG1G2);
			Vecteur v1 = vitesse1;
			Vecteur v2 = vitesse2;

			Vecteur v1_v2 = Vecteur.difference(v1, v2);
			double a = Vecteur.produitScalaire(u, v1_v2);

			double m1 = masse1;
			double m2 = masse2;
			double masseTotale = m1+m2;

			double l = r + Collisions.EPSILON;

			if ( a < Collisions.EPSILON ) // il y a un choc mou entre les 2 billes : on va simplement s�parer les 2 billes sans modifier les vitesses
			{                                 // on d�place les billes sur (G1G2) proportionnellement aux inverses de leurs masses
				double k = m2/masseTotale;
				Vecteur M =  Vecteur.combinaisonLineaire(1, position1, k, G1G2);  // M = bar(m1,G1,m2,G2)
				double lk = l*k;
				Vecteur G1s = Vecteur.combinaisonLineaire(1, M, -lk, u);
				Vecteur G2s = Vecteur.combinaisonLineaire(1, M, m1*lk/m2, u);

				position1.set(G1s);
				position2.set(G2s);
				////System.err.println("choc mou");
				return true;
			}
			else                // il y a choc �lastique entre les 2 billes
			{
				//d'abord calcul des vitesses apr�s le choc

				Vecteur  vs1, vs2;
				double t;

				t = 2 * (m1 / masseTotale) * a;
				vs1 = Vecteur.combinaisonLineaire(1, v1, -(m2 / m1) * t, u);
				vs2 = Vecteur.combinaisonLineaire(1, v2, t, u); 

				vitesse1.set(vs1);                  // vecteur vitesse de la bille 1 apr�s le choc
				vitesse2.set(vs2);                  // vecteur vitesse de la bille 2 apr�s le choc

				// � pr�sent calcul des positions apr�s le choc


				Vecteur vs2_vs1 = vs2.difference(vs1);

				double a0, b0, c0;

				a0 = vs2_vs1.normeCarree();
				b0 = 2*Vecteur.produitScalaire(vs2_vs1, G1G2);
				c0 = nG1G2*nG1G2 - l*l;
				double deltaT[] = MesMaths.resoudre(a0, b0, c0);

				//if (deltaT.length > 0)
				{
					double dT = deltaT[1];
					position1.ajoute(vs1.produit(dT));
					position2.ajoute(vs2.produit(dT));
				}

				return true;
			}
		}
	} // collisionBilleBille2

	public static boolean CollisionBilleBille( Vecteur position1, double rayon1, Vecteur vitesse1, double masse1, 
			Vecteur position2, double rayon2, Vecteur vitesse2, double masse2)   
	//modifie b1 et b2
	//donn�es : b1 et b2 avant le choc
	//r�sultats : b1 et b2 apr�s le choc
	{
		Vecteur G1G2;
		double nG1G2, nG1G2_2;
		G1G2 = Vecteur.difference(position2, position1);
		nG1G2_2 = G1G2.normeCarree();
		nG1G2 = Math.sqrt(nG1G2_2);
		//nG1G2 = G1G2.norme();

		double r = rayon1+rayon2;

		if ( nG1G2 >= r  )     // il n'y a pas de collision entre les 2 billes
			return false;

		else
		{
			Vecteur u = G1G2.produit(1 / nG1G2);
			Vecteur v1 = vitesse1;
			Vecteur v2 = vitesse2;

			Vecteur v1_v2 = Vecteur.difference(v1, v2);
			double a = Vecteur.produitScalaire(u, v1_v2);

			double m1 = masse1;
			double m2 = masse2;
			double masseTotale = m1+m2;

			double l = r + Collisions.EPSILON;

			if ( a < 0 ) // il y a un choc mou entre les 2 billes : on va simplement s�parer les 2 billes sans modifier les vitesses
			{                                 // on d�place les billes sur (G1G2) proportionnellement aux inverses de leurs masses
				double k = m2/masseTotale;
				Vecteur M =  Vecteur.combinaisonLineaire(1, position1, k, G1G2);  // M = bar(m1,G1,m2,G2)
				double lk = l*k;
				Vecteur G1s = Vecteur.combinaisonLineaire(1, M, -lk, u);
				Vecteur G2s = Vecteur.combinaisonLineaire(1, M, m1*lk/m2, u);

				position1.set(G1s);
				position2.set(G2s);
				//System.err.println("choc mou");
				return true;
			}
			else                // il y a choc �lastique entre les 2 billes
			{
				//d'abord calcul des vitesses apr�s le choc

				Vecteur  vs1, vs2;
				double t;

				t = 2 * (m1 / masseTotale) * a;
				vs1 = Vecteur.combinaisonLineaire(1, v1, -(m2 / m1) * t, u);
				vs2 = Vecteur.combinaisonLineaire(1, v2, t, u); 

				vitesse1.set(vs1);                  // vecteur vitesse de la bille 1 apr�s le choc
				vitesse2.set(vs2);                  // vecteur vitesse de la bille 2 apr�s le choc

				// � pr�sent calcul des positions apr�s le choc

				double v1_v2_2 = v1_v2.normeCarree();

				double delta1 = a*a - v1_v2_2*(nG1G2_2 - l*l );


				double dT = ( -a + Math.sqrt(delta1) ) / v1_v2_2;


				position1.ajoute(vs1.produit(dT));
				position2.ajoute(vs2.produit(dT));


				return true;
			}
		}
	} // collisionBilleBille


	/**
	 * g�re la collision dynamique ou statique des 2 billes d�finies respectivement par 
	 * ( position1, rayon1, vitesse1, acceleration1, masse1 ) et par ( position2, rayon2, vitesse2, acceleration1, masse2)
	 * 
	 * si il n'y a pas de collision
	 * renvoie false et ne modifie rien
	 * 
	 * si il y a collision dynamique :
	 * renvoie true et modifie  vitesse1 et vitesse2.
	 * 
	 * si il y a collision statique :
	 * renvoie true et modifie  acceleration1 et acceleration2. (une force de rappel �lastique est appliqu�e aux deux billes)
	 * 
	 * Les nouvelles accelerations ou les nouveaux vecteurs vitesses sont calcul�s pour repr�senter l'�tat des billes imm�diatement apr�s le choc  
	 * 
	 * 
	 * 
	 * */

	public static boolean actionReactionBilleBille1( Vecteur position1, double rayon1, Vecteur vitesse1, Vecteur acceleration1, double masse1, 
			Vecteur position2, double rayon2, Vecteur vitesse2, Vecteur acceleration2, double masse2)   
	//modifie b1 et b2
	//donn�es : b1 et b2 avant le choc
	//r�sultats : b1 et b2 apr�s le choc
	{
		Vecteur G1G2;
		double nG1G2, nG1G2_2;
		G1G2 = Vecteur.difference(position2, position1);
		nG1G2_2 = G1G2.normeCarree();
		nG1G2 = Math.sqrt(nG1G2_2);
		//nG1G2 = G1G2.norme();

		double r = rayon1+rayon2;

		if ( nG1G2 >= r  || nG1G2 < Collisions.EPSILON)     // il n'y a pas de collision entre les 2 billes
			return false;

		else
		{
			Vecteur u = G1G2.produit(1 / nG1G2);
			Vecteur v1 = vitesse1;
			Vecteur v2 = vitesse2;

			Vecteur v1_v2 = Vecteur.difference(v1, v2);
			double a = Vecteur.produitScalaire(u, v1_v2);

			double m1 = masse1;
			double m2 = masse2;
			double masseTotale = m1+m2;

			double l = r + Collisions.EPSILON;

			if ( a < Collisions.EPSILON_CHOC_BILLE )
			{
				if (a > - Collisions.EPSILON_CHOC_BILLE)
				{                                           // choc sans vitesse

					double d = r - nG1G2;

					double forceRappel = Collisions.COEFF_ELASTICITE_BILLE * d;
					Vecteur a12, a21;
					a12 = u.produit(forceRappel / masse2);
					a21 = u.produit(-forceRappel / masse1);

					acceleration1.ajoute(a21);
					acceleration2.ajoute(a12);

					//System.err.println("choc mou entre deux billes");
					return true;

				}
				else                                        // les 2 billes s'�loignent l'une de l'autre
				{                                       //System.err.println("fin de collision bille-bille");
					return false;
				}
			}
			else      /*  a >= 0 */          // il y a choc �lastique entre les 2 billes
			{
				//d'abord calcul des vitesses apr�s le choc

				Vecteur  vs1, vs2;
				double t;

				t = 2 * (m1 / masseTotale) * a;
				vs1 = Vecteur.combinaisonLineaire(1, v1, -(m2 / m1) * t, u);
				vs2 = Vecteur.combinaisonLineaire(1, v2, t, u); 

				vitesse1.set(vs1);                  // vecteur vitesse de la bille 1 apr�s le choc
				vitesse2.set(vs2);                  // vecteur vitesse de la bille 2 apr�s le choc

				// � pr�sent calcul des positions apr�s le choc qui n'est plus utilis�e

				double v1_v2_2 = v1_v2.normeCarree();

				double delta1 = a*a - v1_v2_2*(nG1G2_2 - l*l );


				double dT = ( -a + Math.sqrt(delta1) ) / v1_v2_2;


				//position1.ajoute(vs1.produit(dT));
				//position2.ajoute(vs2.produit(dT));

				//System.err.println("choc rapide entre deux billes");

				return true;
			}
		}
	} // actionReactionBilleBille

	public static boolean actionReactionStatiqueBilleBille( Vecteur position1, double rayon1, Vecteur acceleration1, double masse1, 
			Vecteur position2, double rayon2, Vecteur acceleration2, double masse2)   
	{
		Vecteur G1G2;
		double nG1G2;
		G1G2 = Vecteur.difference(position2, position1);
		nG1G2 = G1G2.norme();

		double r = rayon1+rayon2;

		if ( nG1G2 >= r  )     // il n'y a pas de collision entre les 2 billes
			return false;

		else
		{
			Vecteur u = G1G2.produit(1 / nG1G2);
			double d = r - nG1G2;

			double forceRappel = Collisions.COEFF_ELASTICITE_BILLE*d;
			Vecteur a12, a21;
			a12 = u.produit(forceRappel/masse2);
			a21 = u.produit(-forceRappel/masse1);

			acceleration1.ajoute(a21);
			acceleration2.ajoute(a12);

			return true;     
		}
	} // actionReactionStatiqueBilleBille


	/**
	 * gestion de la collision dynamique (avec rebond) ou statique  de la bille d�finie par (position, rayon, vitesse, acceleration) avec le segment orient� [P0P1]
	 * 
	 * @return l'intensit� du choc. Si la valeur retourn�e est > 0 alors il y a choc, si la valeur retourn�e est <= 0 alors il n'y a pas de choc. 
	 * Plus la valeur retourn�e (lorsqu'elle est > 0) est grande, plus le choc est violent 
	 * 
	 *  si la valeur retourn�e est <= 0, vitesse et acceleration sont laiss�s intacts
	 * 
	 * si la valeur retourn�e est > 0 alors vitesse et acceleration sont modifi�s
	 * 
	 * 
	 * c-�-d que en entr�e on consid�re que (position,vitesse, acceleration) sont le vecteur position, le vecteur vitesse et le vecteur acceleration de la bille 
	 * imm�diatement avant le choc
	 * et en sortie (position,vitesse, acceleration) sont le vecteur position, le vecteur vitesse et le vecteur acceleration de la bille imm�diatement apr�s le choc
	 * 
	 * si le choc est parfaitement dynamique, le vecteur vitesse est modifi� par la collision (comme une boule de billard l'est par une bande)
	 * si le choc est parfaitement statique, le vecteur acceleration est modifi� (on consid�re que le choc est �lastique et une force de rappel est appliqu�e � la bille)
	 * Dans toutes les situations interm�diaires, le vecteur vitesse et le vecteur acceleration sont modifi�s suivant une pond�ration qui cherche un compromis entre
	 * situation dynamique et situation statique
	 * 
	 * Dans tous les cas, le vecteur position est laiss� intact par la collision,
	 * 
	 * @param position : vecteur position de la bille imm�diatement avant la collision avec le segment [P0P1]
	 * @param rayon : rayon de la bille
	 * @param vitesse : vecteur vitesse de la bille imm�diatement avant la collision avec le segment [P0P1]
	 * @param acceleration : vecteur vitesse de la bille imm�diatement avant la collision avec le segment [P0P1]
	 * @param P0 : le d�but du segment
	 * @param P1 : la fin du segment
	 * 
	 * */
	static double actionReactionBilleSegmentAvecRebond( Vecteur position, double rayon, Vecteur vitesse, Vecteur acceleration, double masse, Vecteur P0, Vecteur P1)
	{
		Vecteur[] base = Geop.base( P0,  P1);
		Vecteur I = base[0];
		Vecteur J = base[1];

		double vJ = vitesse.produitScalaire(J);

		if (vJ>=0) return -vJ; // la bille revient vers le bord depuis l'ext�rieur ou bien s'�loigne du bord depuis l'int�rieur: il n'y a pas de collision

		// � pr�sent, on a forc�ment vJ < 0

		double d = position.difference(P0).produitScalaire(J);  // d est la distance sign�e entre le segment et la bille

		if (d >= rayon) return vJ; // il n'y a pas de collision

		// � pr�sent, on a forc�ment : vJ < 0 et d < rayon

		double v = -vJ; // on a forc�ment v > 0

		if (v >= EPSILON_CHOC_PAROI)            // le choc est dynamique
		{
			// d'abord calcul du vecteur vitesse r�fl�chi

			Vecteur deltaV = J.produit(-2*vJ);      // deltaV = vecteur vitesse r�fl�chi - vecteur vitesse incident
			Vecteur vS = vitesse.somme(deltaV);
			vitesse.set(vS);
			//System.err.println("choc mou avec la paroi" );
		}

		else                // le choc est mou
		{
			// calcul de la force de rappel

			double e = rayon - d;                    // profondeur de la pr�n�tration de la bille dans le mur, on a e > 0

			double forceRappel = Collisions.COEFF_ELASTICITE_PAROI*e;

			Vecteur a = J.produit(forceRappel/masse);
			acceleration.ajoute(a);
		}


		//double alfa = 1; // v/(v+e); 
		//alfa = Math.sqrt(alfa);
		//double alfabar = e/(v+e);

		//Vecteur vS = Vecteur.combinaisonLineaire(1, vitesse, alfa, deltaV);

		return v;
	}                           // actionReactionBilleSegmentAvecRebond2


	static double volumePartielSphere(double r, double e)
	{
		if (e <= 2*r)
		{
			double e2 = e*e;

			return  Math.PI*e2*(r-e/3);
		}
		else
			return 4.0/3*Math.PI*r*r*r;

	}

	static boolean actionReactionBilleSegmentAvecRebond1( Vecteur position, double rayon, Vecteur vitesse, Vecteur acceleration, double masse, 
			Vecteur P0, Vecteur P1)
	{
		Vecteur[] base = Geop.base( P0,  P1);
		Vecteur I = base[0];
		Vecteur J = base[1];

		double d = position.difference(P0).produitScalaire(J);  // d est la distance sign�e entre le segment et la bille

		if (d >= rayon) return false; // il n'y a pas de collision

		//� pr�sent, on a forc�ment : d < rayon

		//calcul de la force de rappel

		double e = rayon - d;                    // profondeur de la pr�n�tration de la bille dans le mur, on a e > 0

		double volumePenetrant = volumePartielSphere(rayon, e);        // volume de la bille qui s'est enfonc� dans la paroi

		double alfa = 0.1;
		double alfa2 = alfa*alfa;
		double k = 4/(alfa2*(3-alfa));

		double forceRappel = k*volumePenetrant;

		Vecteur a = J.produit(forceRappel/masse);
		acceleration.ajoute(a);
		System.out.println("a = " + a +", volume = " + volumePenetrant +", k = " + k + ", e = " + e +", rayon = " + rayon +", d = " + d);
		System.out.println("position = " + position + ", P0 = " + P0 +", P1 = " + P1 + ", J = " + J); 
		try
		{
			System.in.read();
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		double vJ = vitesse.produitScalaire(J);

		return true ; // la bille revient vers le bord depuis l'ext�rieur ou bien s'�loigne du bord depuis l'int�rieur: il n'y a pas de collision

		// � pr�sent, on a forc�ment vJ < 0





		/*double v = -vJ; // on a forc�ment v > 0

if (v >= EPSILON_CHOC_PAROI)            // le choc est dynamique
   {
   // d'abord calcul du vecteur vitesse r�fl�chi

   Vecteur deltaV = J.produit(-2*vJ);      // deltaV = vecteur vitesse r�fl�chi - vecteur vitesse incident
   Vecteur vS = vitesse.somme(deltaV);
   vitesse.set(vS);
                                                    //System.err.println("choc mou avec la paroi" );
   }

else                // le choc est mou
   {

   }


//double alfa = 1; // v/(v+e); 
//alfa = Math.sqrt(alfa);
//double alfabar = e/(v+e);

//Vecteur vS = Vecteur.combinaisonLineaire(1, vitesse, alfa, deltaV);

return v;
		 */}                           // actionReactionBilleSegmentAvecRebond



	/**
	 * gestion de la collision dynamique (avec rebond) ou statique  de la bille d�finie par (position,rayon,vitesse,acceleration) avec un contour rectangulaire de l'�cran.
	 * 
	 *  Ce rectangle est d�fini par (abscisseCoinHautGauche, Ordonn�eCoinHautGauche,largeur,hauteur)
	 * 
	 * @return une valeur <= 0 si il n'y a pas de collision
	 * 
	 * @return une valeur > 0 si il y a collision et dans ce cas modifie vitesse et acceleration. Dans ce cas aussi, la valeur retourn�e mesure l'intensit� du choc
	 * 
	 * c-�-d que en entr�e on consid�re que (position,vitesse,acceleration) sont le vecteur position, le vecteur vitesse et le vecteur acceleration de la bille imm�diatement avant le choc
	 * et en sortie (position,vitesse,acceleration) sont le vecteur position, le vecteur vitesse et le vecteur acceleration de la bille imm�diatement apr�s le choc
	 * 
	 * si le choc est parfaitement dynamique, le vecteur vitesse est modifi� par la collision (comme une boule de billard l'est par une bande)
	 * si le choc est parfaitement statique, le vecteur acceleration est modifi� (on consid�re que le choc est �lastique et une force de rappel est appliqu�e � la bille)
	 * Dans toutes les situations interm�diaires, le vecteur vitesse et le vecteur acceleration sont modifi�s suivant une pond�ration qui cherche un compromis entre
	 * situation dynamique et situation statique
	 * 
	 * @param position : vecteur position de la bille imm�diatement avant la collision avec le contour
	 * @param rayon : rayon de la bille
	 * @param vitesse : vecteur vitesse de la bille imm�diatement avant la collision avec le contour
	 * @param acceleration : vecteur acceleration de la bille imm�diatement avant la collision avec le contour
	 * @param abscisseCoinHautGauche : abscisse minimale du contour rectangulaire
	 * @param ordonn�eCoinHautGauche : ordonn�e minimale du contour rectangulaire
	 * @param largeur : largeur du contour rectangulaire
	 * @param hauteur : hauteur du contour rectangulaire
	 * 
	 * 
	 * */
	public static double actionReactionBilleContourAvecRebond( Vecteur position, double rayon, Vecteur vitesse, Vecteur acceleration, double masse, 
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

		int i;

		double intensiteChoc = -1;

		for ( i = 1; i < coins.length; ++i)
			if (( intensiteChoc = Collisions.actionReactionBilleSegmentAvecRebond(position, rayon, vitesse, acceleration, masse, coins[i-1], coins[i]))> 0 )
				return intensiteChoc;

		return intensiteChoc;
	}                   // actionReactionBilleContourAvecRebond


	/**
	 * g�re la collision dynamique ou statique des 2 billes d�finies respectivement par 
	 * ( position1, rayon1, vitesse1, acceleration1, masse1 ) et par ( position2, rayon2, vitesse2, acceleration1, masse2)
	 * 
	 * si il n'y a pas de collision
	 * renvoie une valeur < = 0 et ne modifie rien
	 * 
	 * si il y a collision dynamique :
	 * renvoie une valeur > 0 repr�sentant l'intensit� du choc  et modifie  vitesse1 et vitesse2.
	 * 
	 * si il y a collision statique :
	 * renvoie ? et modifie  acceleration1 et acceleration2. (une force de rappel �lastique est appliqu�e aux deux billes)
	 * 
	 * Les nouvelles accelerations ou les nouveaux vecteurs vitesses sont calcul�s pour repr�senter l'�tat des billes imm�diatement apr�s le choc  
	 * 
	 * 
	 * 
	 * */

	public static double actionReactionBilleBille( Vecteur position1, double rayon1, Vecteur vitesse1, Vecteur acceleration1, double masse1, 
			Vecteur position2, double rayon2, Vecteur vitesse2, Vecteur acceleration2, double masse2)   
	//modifie b1 et b2
	//donn�es : b1 et b2 avant le choc
	//r�sultats : b1 et b2 apr�s le choc
	{
		Vecteur G1G2;
		double nG1G2, nG1G2_2;
		G1G2 = Vecteur.difference(position2, position1);
		nG1G2_2 = G1G2.normeCarree();

		double r = rayon1+rayon2;

		double r2 = r*r;

		if (nG1G2_2 >= r2) return -1;           // on a donc nG1G2 >= rayon1 + rayon2, il n'y a donc pas de choc

		// � pr�sent nG1G2_2 < r2

		nG1G2 = Math.sqrt(nG1G2_2);

		// on a donc nG1G2 < r

		Vecteur u = G1G2.produit(1 / nG1G2);

		Vecteur v1 = vitesse1;
		Vecteur v2 = vitesse2;

		Vecteur v1_v2 = Vecteur.difference(v1, v2);
		double a = Vecteur.produitScalaire(u, v1_v2);


		if (a <-EPSILON_CHOC_BILLE) return a;       // il n'y a pas de collision, les billes s'�loignent l'une de l'autre

		// � pr�sent, -EPSILON_CHOC_BILLE <= a 

		if (a < EPSILON_CHOC_BILLE) // le choc est mou car a ~= 0
		{
			double e = r - nG1G2;       // e est la profondeur de la p�n�tration d'une bille dans l'autre

			double forceRappel = Collisions.COEFF_ELASTICITE_BILLE * e;

			Vecteur a12, a21;
			a12 = u.produit(forceRappel / masse2);
			a21 = u.produit(-forceRappel / masse1);

			acceleration1.ajoute(a21);
			acceleration2.ajoute(a12);

			return a;
		}

		// � pr�sent, a > EPSILON_CHOC_BILLE et nG1G2 < r, le choc est donc dynamique

		double m1 = masse1;
		double m2 = masse2;
		double masseTotale = m1+m2;

		double t;

		t = 2 * (m1 / masseTotale) * a;

		Vecteur  vs1, vs2;

		vs1 = Vecteur.combinaisonLineaire(1, v1, -(m2 / m1) * t, u);
		vs2 = Vecteur.combinaisonLineaire(1, v2, t, u); 

		vitesse1.set(vs1);                  // vecteur vitesse de la bille 1 apr�s le choc
		vitesse2.set(vs2);                  // vecteur vitesse de la bille 2 apr�s le choc

		return a;



		/* double l = r + Collisions.EPSILON;

   if ( a < Collisions.EPSILON_CHOC_BILLE )
      {
            if (a > - Collisions.EPSILON_CHOC_BILLE)
                {                                           // choc sans vitesse

                double d = r - nG1G2;

                double forceRappel = Collisions.COEFF_ELASTICITE_BILLE * d;
                Vecteur a12, a21;
                a12 = u.produit(forceRappel / masse2);
                a21 = u.produit(-forceRappel / masse1);

                acceleration1.ajoute(a21);
                acceleration2.ajoute(a12);

                                                        //System.err.println("choc mou entre deux billes");
                return true;

                }
            else                                        // les 2 billes s'�loignent l'une de l'autre
                {                                       //System.err.println("fin de collision bille-bille");
                return false;
                }
        }
   else        a >= 0           // il y a choc �lastique entre les 2 billes
        {
        //d'abord calcul des vitesses apr�s le choc



        // � pr�sent calcul des positions apr�s le choc qui n'est plus utilis�e

        double v1_v2_2 = v1_v2.normeCarree();

        double delta1 = a*a - v1_v2_2*(nG1G2_2 - l*l );


        double dT = ( -a + Math.sqrt(delta1) ) / v1_v2_2;


        //position1.ajoute(vs1.produit(dT));
        //position2.ajoute(vs2.produit(dT));

                                                //System.err.println("choc rapide entre deux billes");

        return true;
        }
   }*/
	} // actionReactionBilleBille2

	public static double actionReactionBilleBille2( Vecteur position1, double rayon1, Vecteur vitesse1, Vecteur acceleration1, double masse1, 
			Vecteur position2, double rayon2, Vecteur vitesse2, Vecteur acceleration2, double masse2)   
	//modifie b1 et b2
	//donn�es : b1 et b2 avant le choc
	//r�sultats : b1 et b2 apr�s le choc
	{
		Vecteur G1G2;
		double nG1G2, nG1G2_2;
		G1G2 = Vecteur.difference(position2, position1);
		nG1G2_2 = G1G2.normeCarree();

		double r = rayon1+rayon2;

		double r2 = r*r;

		if (nG1G2_2 >= r2) return -1;           // on a donc nG1G2 >= rayon1 + rayon2, il n'y a donc pas de choc

		// � pr�sent nG1G2_2 < r2

		nG1G2 = Math.sqrt(nG1G2_2);

		// on a donc nG1G2 < r

		Vecteur u = G1G2.produit(1 / nG1G2);

		Vecteur v1 = vitesse1;
		Vecteur v2 = vitesse2;

		Vecteur v1_v2 = Vecteur.difference(v1, v2);
		double a = Vecteur.produitScalaire(u, v1_v2); // a est l'intensit� du choc

		double e = r - nG1G2;       // e est la profondeur de la p�n�tration d'une bille dans l'autre

		double forceRappel = Collisions.COEFF_ELASTICITE_BILLE * e;

		Vecteur a12, a21;
		a12 = u.produit(forceRappel / masse2);
		a21 = u.produit(-forceRappel / masse1);

		acceleration1.ajoute(a21);
		acceleration2.ajoute(a12);

		return a;

	} // actionReactionBilleBille
	public static boolean actionReactionBilleContourAvecRebond2( Vecteur position, double rayon, Vecteur vitesse, Vecteur acceleration, double masse, 
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

		int i;

		boolean collision;

		for ( i = 1, collision = false; i < coins.length; ++i)
			if (Collisions.actionReactionBilleSegmentAvecRebond2(position, rayon, vitesse, acceleration, masse, coins[i-1], coins[i])) 
				collision = true;

		return collision;
	}                   // actionReactionBilleContourAvecRebond

}
