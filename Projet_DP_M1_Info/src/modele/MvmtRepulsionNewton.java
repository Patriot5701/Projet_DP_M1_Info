package modele;

import java.util.Vector;

/**
 * Comportement repoussant la bille face aux autres billes
 * @author alexis
 *
 */
public class MvmtRepulsionNewton extends DecorateurBille {
	/**
	 * Constructeur
	 * @param billeDecorated : la bille decoree
	 */
	public MvmtRepulsionNewton(Bille billeDecorated) {
		super(billeDecorated);
	}


	public void gestionAcceleration(Vector<Bille> billes)
	{
		this.billeDecoree.gestionAcceleration(billes);
		this.getAcceleration().ajoute(OutilsBilles.gestionAccelerationNewton(billeDecoree, billes).oppose());     // contribution de l'acceleration due a la repulsion des autres billes
	}

	@Override
	public String toString() {
		String str = billeDecoree.toString();
		return str + ", Comportement : repousée par les autres billes";
	}



	@Override
	public void collisionContour(double abscisseCoinHautGauche, double ordonneeCoinHautGauche, double largeur,
			double hauteur) {
		billeDecoree.collisionContour(abscisseCoinHautGauche, ordonneeCoinHautGauche, largeur, hauteur);
		
	}

}
