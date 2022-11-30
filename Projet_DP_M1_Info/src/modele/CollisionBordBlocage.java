package modele;

import java.util.Vector;

import mesmaths.cinematique.Collisions;

public class CollisionBordBlocage extends DecorateurBilleCollisionBord{

	public CollisionBordBlocage(Bille billeDecorated) {
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
			Collisions.collisionBilleContourAvecArretHorizontal(billeDecoree.getPosition(), billeDecoree.getRayon(), billeDecoree.getVitesse(), abscisseCoinHautGauche, largeur);
			Collisions.collisionBilleContourAvecArretVertical(billeDecoree.getPosition(), billeDecoree.getRayon(), billeDecoree.getVitesse(), ordonneeCoinHautGauche, hauteur);
		}
	}

	@Override
	public String toStringCptmt() {
		if(ComportementMemoire.getInstance().getComportementCollision().equals(this.getClass().getName())) {
			return ", Comportement : bloquée par les bords";
		}
		return "";
	}


}
