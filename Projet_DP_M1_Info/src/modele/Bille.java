package modele;

import java.util.Vector;
import mesmaths.geometrie.base.Vecteur;
import vue.VisitorBille;

public abstract class Bille {

	public Vecteur position;   // centre de la bille
	/**
	 * @return the position
	 */
	public abstract Vecteur getPosition();
	/**
	 * @return the rayon
	 */
	public abstract double getRayon();
	/**
	 * @return the vitesse
	 */
	public abstract Vecteur getVitesse();
	/**
	 * @return the acceleration
	 */
	public abstract Vecteur getAcceleration();
	/**
	 * @return the clef
	 */
	public abstract int getClef();
	
	public abstract String getColor();
	
	public abstract void gestionAcceleration(Vector<Bille> billes);
	
	public boolean gestionCollisionBilleBille(Vector<Bille> billes){
        return OutilsBilles.gestionCollisionBilleBille(this, billes);
    }
	
	public abstract void collisionContour(double abscisseCoinHautGauche, double ordonneeCoinHautGauche, double largeur, double hauteur);

	
	public void accepteDraw(VisitorBille visitor) {
		visitor.visitDraw(this);
	}
	
	public abstract String toString();
	
	public abstract double getMasse();
	
	public abstract void deplacer(double deltaT);

}
