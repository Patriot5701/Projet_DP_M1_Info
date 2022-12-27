package mesmaths.mecanique;

import mesmaths.geometrie.base.Geop;
import mesmaths.geometrie.base.Vecteur;



/**
 * gestion des op�rations de m�canique du point mat�riel
 * 
 * */
public class MecaniquePoint
{
	/**
	 * constante G de la gravitation universelle
	 * 
	 * */
	public static double G = 0.0002;     // constante G de la gravitation universelle

	/**
	 * coefficient de frottement visqueux (dans l'air)
	 * 
	 * */
	public static double COEFFICIENTFROTTEMENT = 4.5; // coefficient de frottement visqueux (dans l'air)

	/**
	 * 
	 * calcule la force de frottement visqueux proportionnelle au vecteur vitesse v et au coefficient de frottement coefficientFrottement >= 0
	 * 
	 * Un frottement visqueux est un frottement proportionnel � la vitesse comme le frottement dans l'air par exemple.
	 * 
	 * @return la force de frottement
	 * */
	public static Vecteur frottementVisqueux(Vecteur v, double coefficientFrottement)
	{
		return v.produit(-coefficientFrottement);
	}

	/**
	 * 
	 * calcule la force de frottement visqueux proportionnelle au vecteur vitesse v et au coefficient de frottement COEFFICIENTFROTTEMENT
	 * 
	 * Un frottement visqueux est un frottement proportionnel � la vitesse comme le frottement dans l'air par exemple.
	 * 
	 * @return la force de frottement
	 * */
	public static Vecteur frottementVisqueux(Vecteur v)
	{
		return MecaniquePoint.frottementVisqueux(v, COEFFICIENTFROTTEMENT);
	}

	/**
	 * 
	 * calcule l'acceleration (en fait freinage dans le langage courant) due a la force de frottement visqueux proportionnelle au vecteur vitesse v et au coefficient de frottement COEFFICIENTFROTTEMENT
	 * 
	 * Un frottement visqueux est un frottement proportionnel � la vitesse comme le frottement dans l'air par exemple (et cela freine l'objet en mouvement)
	 * 
	 * Ce freinage est calcul� pour un objet de masse "masse" et de vecteur vitesse "vitesse"
	 * @param masse : masse de l'objet subissant le frottement
	 * @param vitesse : vecteur vitesse de l'objet subissant le frottement
	 * @return l'acc�l�ration due au  frottement
	 * */
	public  static Vecteur freinageFrottement( double masse, Vecteur vitesse)
	{
		Vecteur forceFrottement = MecaniquePoint.frottementVisqueux(vitesse);
		return forceFrottement.produit(1/masse);
	}

	/**
	 * 
	 * calcule l'acc�l�ration (en fait freinage dans le langage courant) due � la force de frottement visqueux proportionnelle 
	 * au vecteur vitesse v et au coefficient de frottement "coefficientFrottement"
	 * 
	 * Un frottement visqueux est un frottement proportionnel � la vitesse comme le frottement dans l'air par exemple (et cela freine l'objet en mouvement)
	 * 
	 * Ce freinage est calcul� pour un objet de masse "masse" et de vecteur vitesse "vitesse"
	 * @param masse : masse de l'objet subissant le frottement
	 * @param vitesse : vecteur vitesse de l'objet subissant le frottement
	 * @param coefficientFrottement : tel que coefficientFrottement >= 0, si coefficientFrottement == 0, il n'y a pas de frottement
	 * @return l'acc�l�ration due au  frottement
	 * */
	public  static Vecteur freinageFrottement( double masse, Vecteur vitesse, double coefficientFrottement)
	{
		Vecteur forceFrottement = MecaniquePoint.frottementVisqueux(vitesse, coefficientFrottement);
		return forceFrottement.produit(1/masse);
	}

	/**
	 *  calcule et renvoie le champ de gravite au point P du � la masse m plac�e en C 
ne tient pas compte de la constante de gravitation G 
	 */
	private static Vecteur champGravite(Vecteur P, double m, Vecteur C) 
	{
		Vecteur PC;  /* le vecteur P-->C */
		double PC2; /* |PC|� */
		double coef;

		PC = Vecteur.difference( C, P);
		PC2 = Vecteur.produitScalaire(PC,PC);

		coef = m/(PC2*Math.sqrt(PC2));
		return PC.produit(coef);
	} // champGravit�


	/**
	 * calcule et renvoie le champ de gravit� au point P dues aux masses masses[0]...masses[d-1] plac�es respectivement aux points C[0]...C[d-1]
	 * 
	 * constanteGravitationUniverselle est la constante de gravitation universelle  pr�vue par la loi de gravitation universelle de Newton
	 * 
	 * constanteGravitationUniverselle > 0
	 * 
	 * On suppose que taille de masses[] == taille de C[]
	 * */
	public static  Vecteur champGraviteGlobal(Vecteur P, double masses[], Vecteur C[], double constanteGravitationUniverselle)
	{
		int d = masses.length;

		int i;

		Vecteur g = new Vecteur();

		for( i = 0; i <d; ++i)
			g.ajoute(champGravite(P, masses[i], C[i])); 

		return g.produit(constanteGravitationUniverselle);
	}

	/**
	 * calcule et renvoie le champ de gravite au point P dues aux masses masses[0]...masses[d-1] placees respectivement aux points C[0]...C[d-1]
	 * 
	 * utilise la constante de gravitation universelle  (prevue par la loi de gravitation universelle de Newton) definie dans cette classe
	 * 
	 * On suppose que taille de masses[] == taille de C[] == d
	 * 
	 * @return le vecteur acceleration en P  due a l'attraction gravitationnelle de toutes les masses masses[i] placees en C[i] pour i = 0...d-1   
	 * */
	public static  Vecteur champGraviteGlobal(Vecteur P, double masses[], Vecteur C[])
	{
		return champGraviteGlobal( P,  masses,  C, MecaniquePoint.G);
	}
}
