package modele;

import java.util.Vector;

public class MvmtAttractionNewton extends DecorateurBille{

	
	public MvmtAttractionNewton(modele.Bille billeDecorated) {
		super(billeDecorated);
	}

	/* (non-Javadoc)
	 * @see decorateur_angryballs.modele.Bille#gestionAcc�l�ration(java.util.Vector)
	 */
	public void gestionAcceleration(Vector<Bille> billes)
	{
		this.billeDecoree.gestionAcceleration(billes);
		this.getAcceleration().ajoute(OutilsBilles.gestionAccelerationNewton(this, billes));     // contribution de l'acceleration due a l'attraction des autres billes
	}

	//TODO
	@Override
	public String toStringInfosSupps() {
		// TODO Auto-generated method stub
		return "";
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
