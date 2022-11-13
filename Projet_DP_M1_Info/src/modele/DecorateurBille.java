package modele;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import mesmaths.geometrie.base.Geop;
import mesmaths.geometrie.base.Vecteur;

public abstract class DecorateurBille extends Bille{
	protected Bille billeDecoree;
	
	public DecorateurBille(Bille billeDecorated) {
		billeDecoree = billeDecorated;
	}

	public Vecteur getPosition(){
		return this.billeDecoree.getPosition();
	}
	/**
	 * @return the rayon
	 */
	public double getRayon(){
		return this.billeDecoree.getRayon();
	}
	/**
	 * @return the vitesse
	 */
	public Vecteur getVitesse(){
		return this.billeDecoree.getVitesse();
	}
	/**
	 * @return the acceleration
	 */
	public Vecteur getAcceleration(){
		return this.billeDecoree.getAcceleration();
	}
	/**
	 * @return the clef
	 */
	public int getClef(){
		return this.billeDecoree.getClef();
	}
	
	public Color getColor() {
		return this.billeDecoree.getColor();
	}
	
	public double getMasse() {
		return this.billeDecoree.getMasse();
	}
	
}
