package modele;

import java.util.Vector;
import mesmaths.cinematique.Collisions;

public class CollisionBordRebond extends DecorateurBille{

	//TODO create abstract class decoCollision pour add cette condition
	public CollisionBordRebond(Bille billeDecorated) {
		super(billeDecorated);
		if(!ComportementMemoire.getInstance().hasAComportment()) {
			ComportementMemoire.getInstance().setComportementCollision(this);
		}
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
			Collisions.collisionBilleContourAvecRebond( billeDecoree.getPosition(), billeDecoree.getRayon(), billeDecoree.getVitesse(), abscisseCoinHautGauche, ordonneeCoinHautGauche, largeur, hauteur);
		}
	}

	@Override
	public String toString() {
		String str = billeDecoree.toString();
		return str + ", Comportement : rebondit sur les bords";
	}

}
