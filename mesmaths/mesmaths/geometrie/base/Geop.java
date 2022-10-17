package mesmaths.geometrie.base;


import mesmaths.MesMaths;


/**
 * Quelques operations geometriques utiles
 * 
 * */
public class Geop    // operations utiles
{
	//----------------- classe Geop-------------------------------------


	/**
	 * indique si le point P est situ� � l'int�rieur du disque (centre, rayon)
	 * 
	 * @return true si P appartient au disque (centre, rayon), false sinon
	 * */
	public static boolean appartientDisque(Vecteur P, Vecteur centre, double rayon)
	{
		Vecteur CP;
		double CP2, r2;
		CP = Vecteur.difference(P, centre);
		CP2 = Vecteur.produitScalaire(CP, CP);
		r2 = rayon*rayon;
		return CP2 <= r2;
	}

	/**
	 * @return true si il y a intersection entre les cercles (centre1,rayon1) et (centre2,rayon2), false sinon
	 * 
	 * */
	public static boolean intersectionCercleCercle(Vecteur centre1, double rayon1, Vecteur centre2, double rayon2)
	{
		double d = rayon1+rayon2;
		Vecteur C1C2 = centre2.difference(centre1);

		return C1C2.normeCarree() < d*d;
	}

	/**
	 * indique si la droite (P0 P1) et le cercle (centre,rayon) se coupent
	 * si il y a intersection, renvoit les coordonne�s param�triques 
	 * t1 et t2 des points d'intersection M1 et M2
	 * tels que P0M1 = t1* P0P1 et P0M2 = t2*P0P1
	 * 
	 * t1 et t2 sont plac�es dans le tableau en sortie
	 * 
	 * si il n'y a pas d'intersection, renvoie un tableau de longueur == 0
	 *  
	 * */
	public static double[] intersectionDroiteCercle(Vecteur P0, Vecteur P1, Vecteur  centre, double rayon) 

	{
		Vecteur P0P1, CP0;
		double a, b, c, r2;

		P0P1 = Vecteur.difference(P1, P0);
		CP0 = Vecteur.difference(P0, centre); 

		r2 = rayon*rayon;                               
		a = Vecteur.produitScalaire(P0P1, P0P1);
		b = 2*Vecteur.produitScalaire(CP0, P0P1);
		c = Vecteur.produitScalaire(CP0, CP0)-r2;

		return MesMaths.resoudre(a, b, c);   
	}

	/**
	 * indique si le segment[P0 P1] et le cercle (centre,rayon) se coupent
	 * si il y a intersection, renvoit les coordonne�s param�triques 
	 * t1 et t2 des points d'intersection M1 et M2
	 * tels que P0M1 = t1* P0P1 et P0M2 = t2*P0P1
	 *
	 * t1 et t2 sont plac�es dans le tableau en sortie
	 * 
	 * si il n'y a pas d'intersection, renvoie un tableau de longueur == 0

	 * */
	public static double [] intersectionSegmentCercle(Vecteur P0, Vecteur P1, Vecteur centre, double rayon)
	{
		double []t;

		t = intersectionDroiteCercle( P0, P1, centre, rayon);

		if (t.length ==  2 && (MesMaths.appartient( t[0], 0, 1) || MesMaths.appartient( t[1], 0, 1)))
			return t;
		else 
			return new double[0];
	}

	/**
	 * fournit une base orthornorm�e directe associ�e au segment orient� [P0, P1]
	 * base[0] est un vecteur unitaire colineaire et  de meme sens que le vecteur P0--->P1
	 * et base[1] est tel que base[1] soit le r�sultat de la rotation d'angle de +PI/2 par rapport � base[0]
	 *  @return un tableau t � deux �l�ments tel que t[0] == base[0] et que t[1] == base[1]
	 * */
	public static Vecteur[] base(Vecteur P0, Vecteur P1)
	{
		return P1.difference(P0).base();
	}

	/** calcule le vecteur symetrique de v dans la symetrie d'axe orient� I 
o� (I,J) est une base orthonorm�e  */
	public static Vecteur reflechi(Vecteur v, Vecteur I, Vecteur J) 

	{
		double x, y;

		x = Vecteur.produitScalaire(v, I);
		y = Vecteur.produitScalaire(v, J);
		return Vecteur.combinaisonLineaire( x, I, -y, J);
	}

	public static double volumeSphere(double rayon)
	{
		return 4.0/3.0 * Math.PI*rayon*rayon*rayon;
	}

	/**
	 * calcule le volume d'une demi sph�re de rayon "rayon" tronqu�e � la hauteur "hauteur".
	 * 
	 * On doit avoir 0 <= hauteur <= rayon.
	 * 
	 * Pour h = 0. Le volume = 0
	 * Pour h = rayon, on a une demi-sph�re. Donc le volume = 2/3 * PI * rayon^3
	 * 
	 * Attention !!! h est la distance mesur�e � partir du centre de la sph�re en direction du p�le nord de la sph�re
	 * 
	 * */
	public static double volumeDemiSphereTronquee(double rayon, double hauteur)
	{
		return Math.PI*hauteur*(rayon*rayon - hauteur*hauteur/3);
	}


	/**
	 * calcule le volume d'une sph�re de rayon "rayon" tronqu�e � la hauteur "hauteur".
	 * 
	 * On doit avoir 0 <= hauteur <= 2*rayon.
	 * 
	 * Pour h = 0. Le volume = 0
	 * Pour h = rayon, on a une demi-sph�re. Donc le volume = 2/3 * PI * rayon^3
	 * Pour h = 2*rayon, on a une sph�re compl�te. Donc le volume = 4/3 * PI * rayon^3
	 * 
	 * Attention !!! h est la distance mesur�e � partir du p�le sud  de la sph�re en direction du p�le nord de la sph�re
	 * 
	 * */
	public static double volumeSphereTronquee(double rayon, double hauteur)
	{
		if (hauteur <= 0) return 0;

		double volumeDemiSphere = (2.0/3.0) *Math.PI*rayon*rayon*rayon;

		if (hauteur < rayon) 
		{
			double d = rayon-hauteur;
			double v = volumeDemiSphereTronquee(rayon, d);

			return volumeDemiSphere - v;
		}
		else
			if (hauteur < 2*rayon)
			{
				double d = hauteur-rayon;
				double v = volumeDemiSphereTronquee(rayon, d);
				return volumeDemiSphere + v;
			}
			else 
				return 2*volumeDemiSphere;

	}

	//----------------- classe Geop-------------------------------------
}
