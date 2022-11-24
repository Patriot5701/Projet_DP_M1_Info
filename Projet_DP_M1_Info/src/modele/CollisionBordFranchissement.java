package modele;

import java.util.Vector;

import mesmaths.cinematique.Collisions;

public class CollisionBordFranchissement extends DecorateurBilleCollisionBord{

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
		billeDecoree.collisionContour(abscisseCoinHautGauche, ordonneeCoinHautGauche, largeur, hauteur);
		if(ComportementMemoire.getInstance().getComportementCollision().equals(this.getClass().getName())) {
			Collisions.collisionBilleContourPasseMuraille( this.getPosition(), abscisseCoinHautGauche, ordonneeCoinHautGauche, largeur, hauteur);
		}
	}

	@Override
	public String toStringCptmt() {
		if(ComportementMemoire.getInstance().getComportementCollision().equals(this.getClass().getName())) {
			return ", Comportement : franchit les bords";
		}
		return "";
	}

}
