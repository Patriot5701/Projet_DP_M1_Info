package modele;

import java.util.Vector;

import mesmaths.cinematique.Collisions;
/**
 * Comportement bloquant la bille lors d'une collision avec le bord
 * @author clement
 *
 */
public class CollisionBordBlocage extends DecorateurBille{
	/**
	 * Constructeur
	 * @param billeDecorated : la bille decoree
	 */
	public CollisionBordBlocage(Bille billeDecorated) {
		super(billeDecorated);
	}

	@Override
	public void collisionContour(double abscisseCoinHautGauche, double ordonneeCoinHautGauche, double largeur,
			double hauteur) {
		Collisions.collisionBilleContourAvecArretHorizontal(this.getPosition(), this.getRayon(), this.getVitesse(), abscisseCoinHautGauche, largeur);
		Collisions.collisionBilleContourAvecArretVertical(this.getPosition(), this.getRayon(), this.getVitesse(), ordonneeCoinHautGauche, hauteur);
	}


}
