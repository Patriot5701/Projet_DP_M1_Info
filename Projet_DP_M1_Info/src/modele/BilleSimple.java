package modele;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import mesmaths.cinematique.Cinematique;
import mesmaths.geometrie.base.Geop;
import mesmaths.geometrie.base.Vecteur;

public class BilleSimple extends Bille{
	

	public double rayon;            // rayon > 0
	public Vecteur vitesse;
	public Vecteur acceleration;
	public int clef;                // identifiant unique de cette bille

	private String couleur;

	private static int prochaineClef = 0;

	public static double ro = 1;

	
	public BilleSimple(Vecteur centre, double rayon, Vecteur vitesse, String couleur) {
		this.position = centre;
		this.rayon = rayon;
		this.vitesse = vitesse;
		this.acceleration = new Vecteur();
		this.couleur = couleur;
		this.clef = BilleSimple.prochaineClef ++;
	}
	
	/**
	 * @return the position
	 */
	public Vecteur getPosition(){
		return this.position;
	}
	/**
	 * @return the rayon
	 */
	public double getRayon(){
		return this.rayon;
	}
	/**
	 * @return the vitesse
	 */
	public Vecteur getVitesse(){
		return this.vitesse;
	}
	/**
	 * @return the acceleration
	 */
	public Vecteur getAcceleration(){
		return this.acceleration;
	}
	/**
	 * @return the clef
	 */
	public int getClef(){
		return this.clef;
	}
	
	public String getColor() {
		return this.couleur;
	}
	public double masse() {return ro*Geop.volumeSphere(rayon);}
	
	public double getMasse() {
		return masse();
	}
	
	/**
	 * mise a jour de position et vitesse et+deltaT a partir de position et vitesse a l'instant t
	 * 
	 * modifie le vecteur position et le vecteur vitesse
	 * 
	 * laisse le vecteur acceleration intact
	 *
	 * La bille subit par defaut un mouvement uniformement accelere
	 **/
	public void deplacer(double deltaT){
		Cinematique.mouvementUniformementAccelere( this.getPosition(), this.getVitesse(), this.getAcceleration(), deltaT);
		this.acceleration = new Vecteur();
	}
	
	/**
	 * calcul (c-a-d mise a jour) eventuel  du vecteur acceleration. 
	 * billes est la liste de toutes les billes en mouvement
	 * Cette methode peut avoir besoin de "billes" si this subit l'attraction gravitationnelle des autres billes
	 * La nature du calcul du vecteur acceleration de la bille  est definie dans les classes derivees
	 * A ce niveau le vecteur acceleration est mise zaro (c'est a dire pas d'acceleration)
	 **/
	public void gestionAcceleration(Vector<Bille> billes){}
	
	/**
	 * gestion de l'eventuelle  collision de cette bille avec les autres billes
	 *
	 * billes est la liste de toutes les billes en mouvement
	 * 
	 * Le comportement par defaut est le choc parfaitement elastique (c-a-d rebond sans amortissement)
	 * 
	 * @return true si il y a collision et dans ce cas les positions et vecteurs vitesses des 2 billes impliquees dans le choc sont modifiees
	 * si renvoie false, il n'y a pas de collision et les billes sont laissees intactes 
	 **/
	public boolean gestionCollisionBilleBille(Vector<Bille> billes){
		return OutilsBilles.gestionCollisionBilleBille(this, billes);
	}
	
	/**
	 * gestion de l'eventuelle collision de la bille (this) avec le contour rectangulaire de l'ecran defini par (abscisseCoinHautGauche, ordonneeCoinHautGauche, largeur, hauteur)
	 * 
	 * detecte si il y a collision et le cas echeant met a jour position et vitesse
	 * 
	 * La nature du comportement de la bille en reponse a cette collision est definie dans les classes derivees
	 **/
	public void collisionContour(double abscisseCoinHautGauche, double ordonneeCoinHautGauche, double largeur, double hauteur) {}
	
	
	public String toString(){
		String str =  "\n" + "	centre = " + this.getPosition() + "\n" + "	rayon = " + this.getRayon() + "\n" +  "	vitesse = " + this.getVitesse() + "\n" + "	acceleration = " + this.getAcceleration() + "\n" + "	couleur = " + this.getColor() + "\n" + "	clef = " + this.getClef() + "\n";
		
		return str;
    }


}
