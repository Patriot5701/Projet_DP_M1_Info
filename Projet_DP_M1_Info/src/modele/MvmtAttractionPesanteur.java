package modele;

import java.util.Vector;

import mesmaths.geometrie.base.Vecteur;
/**
 * Comportement ajoutant la pesanteur
 * @author alexis
 *
 */
public class MvmtAttractionPesanteur extends DecorateurBille{
	Vecteur pesanteur = new Vecteur(0,0.001); // Vecteur utilisé dans TestAngryBalls
	/**
	 * Constructeur
	 * @param billeDecorated : la bille decoree
	 */
	public MvmtAttractionPesanteur(Bille billeDecorated) {
		super(billeDecorated);
	}

	public void gestionAcceleration(Vector<Bille> billes)
	{
		this.billeDecoree.gestionAcceleration(billes);
		this.getAcceleration().ajoute(this.pesanteur);     // contribution de l'acceleration due a la pesanteur
	}

	@Override
	public String toString() {
		String str = billeDecoree.toString();
		return str + ", Comportement : attirée vers le bas";
	}

	@Override
	public boolean gestionCollisionBilleBille(Vector<Bille> billes) {
		return billeDecoree.gestionCollisionBilleBille(billes);
	}

	@Override
	public void collisionContour(double abscisseCoinHautGauche, double ordonneeCoinHautGauche, double largeur,
			double hauteur) {
		billeDecoree.collisionContour(abscisseCoinHautGauche, ordonneeCoinHautGauche, largeur, hauteur);
		
	}
}
