package modele;

import java.util.Vector;
import mesmaths.cinematique.Collisions;
/**
 * Comportement franchissant le bord lors d'une collision
 * @author clement
 *
 */
public class CollisionBordFranchissement extends DecorateurBille{
	/**
	 * Constructeur
	 * @param billeDecorated : la bille decoree
	 */
	public CollisionBordFranchissement(Bille billeDecorated) {
		super(billeDecorated);
	}

	@Override
	public void gestionAcceleration(Vector<Bille> billes) {
		billeDecoree.gestionAcceleration(billes);
	}


	@Override
	public void collisionContour(double abscisseCoinHautGauche, double ordonneeCoinHautGauche, double largeur,
			double hauteur) {
		Collisions.collisionBilleContourPasseMuraille( this.getPosition(), abscisseCoinHautGauche, ordonneeCoinHautGauche, largeur, hauteur);
	}

	@Override
	public String toString() {
		return billeDecoree.toString();
	}



}
