package modele;

import java.util.Vector;

/**
 * Comportement attirant la bille par les autres billes
 * @author alexis
 *
 */
public class MvmtAttractionNewton extends DecorateurBille{

	/**
	 * Constructeur
	 * @param billeDecorated : la bille decoree
	 */
	public MvmtAttractionNewton(modele.Bille billeDecorated) {
		super(billeDecorated);
	}

	@Override
	public void gestionAcceleration(Vector<Bille> billes)
	{
		this.billeDecoree.gestionAcceleration(billes);
		this.getAcceleration().ajoute(OutilsBilles.gestionAccelerationNewton(billeDecoree, billes));     // contribution de l'acceleration due a l'attraction des autres billes
	}

	@Override
	public void collisionContour(double abscisseCoinHautGauche, double ordonneeCoinHautGauche, double largeur,
			double hauteur) {
		billeDecoree.collisionContour(abscisseCoinHautGauche, ordonneeCoinHautGauche, largeur, hauteur);
		
	}
}
