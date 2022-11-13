package modele;

import java.util.Vector;

import mesmaths.cinematique.Collisions;

public class CollisionBordFranchissement extends DecorateurBille{

	public CollisionBordFranchissement(Bille billeDecorated) {
		super(billeDecorated);
	}

	@Override
	public void gestionAcceleration(Vector<Bille> billes) {
		billeDecoree.gestionAcceleration(billes);
	}

	@Override
	public boolean gestionCollisionBilleBille(Vector<Bille> billes) {
		return billeDecoree.gestionCollisionBilleBille(billes);
	}

	@Override
	public void collisionContour(double abscisseCoinHautGauche, double ordonneeCoinHautGauche, double largeur,
			double hauteur) {
		Collisions.collisionBilleContourPasseMuraille( this.getPosition(), abscisseCoinHautGauche, ordonneeCoinHautGauche, largeur, hauteur);
	}

	@Override
	public String toString() {
		String str = billeDecoree.toString();
		return str + ", Comportement : franchissement des bords";
	}

}
