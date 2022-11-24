package modele;

import java.util.Vector;

import mesmaths.geometrie.base.Vecteur;

public class MvmtAttractionPesanteur extends DecorateurBille{
	Vecteur pesanteur = new Vecteur(0,0.001); // Vecteur utilisé dans TestAngryBalls

	public MvmtAttractionPesanteur(Bille billeDecorated) {
		super(billeDecorated);
	}

	/* (non-Javadoc)
	 * @see decorateur_angryballs.modele.Bille#gestionAcc�l�ration(java.util.Vector)
	 */
	public void gestionAcceleration(Vector<Bille> billes)
	{
		this.billeDecoree.gestionAcceleration(billes);
		this.getAcceleration().ajoute(this.pesanteur);     // contribution de l'acceleration due a l'attraction des autres billes
	}

	//TODO
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
