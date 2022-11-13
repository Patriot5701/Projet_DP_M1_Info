package modele;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import mesmaths.geometrie.base.Vecteur;

public abstract class Bille {

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
	
	public abstract Color getColor();//TODO attention lib
	
	public abstract void gestionAcceleration(Vector<Bille> billes);
	
	public abstract boolean gestionCollisionBilleBille(Vector<Bille> billes);
	
	public abstract void collisionContour(double abscisseCoinHautGauche, double ordonneeCoinHautGauche, double largeur, double hauteur);

	public void dessine (Graphics g) {    // reference awt : mauvais
	    
		int width, height;
		int xMin, yMin;
    
		xMin = (int)Math.round(getPosition().x-getRayon());
		yMin = (int)Math.round(getPosition().y-getRayon());

		width = height = 2*(int)Math.round(getRayon()); 

		g.setColor(getColor());
		g.fillOval( xMin, yMin, width, height);
		g.setColor(Color.CYAN);
		g.drawOval(xMin, yMin, width, height);
    }
	//TODO awt
	
	public abstract String toString();
	
	public abstract double getMasse();


}
