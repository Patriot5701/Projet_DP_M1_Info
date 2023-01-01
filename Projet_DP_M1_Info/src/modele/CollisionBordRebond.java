package modele;

import java.util.Vector;

import mesmaths.cinematique.Collisions;

/**
 * Comportement rebondissant contre le rebord
 * @author clement
 *
 */
public class CollisionBordRebond extends DecorateurBille{
	/**
	 * Constructeur
	 * @param billeDecorated : la bille decoree
	 */
	public CollisionBordRebond(Bille billeDecorated) {
		super(billeDecorated);
	}

	@Override
	public void collisionContour(double abscisseCoinHautGauche, double ordonneeCoinHautGauche, double largeur,
			double hauteur) {
		Collisions.collisionBilleContourAvecRebond( this.getPosition(), this.getRayon(), this.getVitesse(), abscisseCoinHautGauche, ordonneeCoinHautGauche, largeur, hauteur);
	}
	
}
