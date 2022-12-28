package modele;

import java.util.Vector;

import mesmaths.geometrie.base.Vecteur;
import vue.VisitorBille;

/**
 * 
 * Classe abstraite représentant une Bille
 */
public abstract class Bille {

	/**
	 * Position du centre de la bille
	 */
	public Vecteur position;
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
	/**
	 * 
	 * @return the color
	 */
	public abstract String getColor();
	
	/**
	 * Gère l'accélération
	 * @param billes : Ensemble des billes existantes
	 */
	public abstract void gestionAcceleration(Vector<Bille> billes);
	/**
	 * 
	 * @param billes : Ensemble des billes existantes
	 * @return la possible collision
	 */
	public boolean gestionCollisionBilleBille(Vector<Bille> billes){
        return OutilsBilles.gestionCollisionBilleBille(this, billes);
    }
	/**
	 * Gère la collision avec les rebords
	 * @param abscisseCoinHautGauche
	 * @param ordonneeCoinHautGauche
	 * @param largeur
	 * @param hauteur
	 */
	public abstract void collisionContour(double abscisseCoinHautGauche, double ordonneeCoinHautGauche, double largeur, double hauteur);

	/**
	 * Charge le visiteur en paramètre de dessiner la bille
	 * @param visitor : le visiteur chargé de dessiner avec sa librairie graphique
	 */
	public void accepteDraw(VisitorBille visitor){
		visitor.visitDraw(this);
	}
	public abstract String toString();
	/**
	 * 
	 * @return la masse
	 */
	public abstract double getMasse();
	/**
	 * Gère le déplacement
	 * @param deltaT
	 */
	public abstract void deplacer(double deltaT);

}
