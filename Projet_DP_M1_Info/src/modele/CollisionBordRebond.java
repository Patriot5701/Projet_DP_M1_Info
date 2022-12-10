package modele;

import java.util.Vector;
import mesmaths.cinematique.Collisions;

public class CollisionBordRebond extends DecorateurBille{

	public CollisionBordRebond(Bille billeDecorated) {
		super(billeDecorated);
	}

	@Override
	public void collisionContour(double abscisseCoinHautGauche, double ordonneeCoinHautGauche, double largeur,
			double hauteur) {
		Collisions.collisionBilleContourAvecRebond( this.getPosition(), this.getRayon(), this.getVitesse(), abscisseCoinHautGauche, ordonneeCoinHautGauche, largeur, hauteur);
	}
	
	@Override
	public String toString() {
		String message;
		message = billeDecoree.toString();
		return message;
	}

}
