package modele;

import java.util.Vector;

import mesmaths.mecanique.MecaniquePoint;

public class MvmtFrottements extends DecorateurBille{

	public MvmtFrottements(Bille billeDecorated) {
		super(billeDecorated);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see decorateur_angryballs.modele.Bille#gestionAcc�l�ration(java.util.Vector)
	 */
	public void gestionAcceleration(Vector<Bille> billes)
	{
		this.billeDecoree.gestionAcceleration(billes);
		this.getAcceleration().ajoute(MecaniquePoint.freinageFrottement(this.getMasse(), this.getVitesse()));     // contribution de l'acceleration due au frottement
	}

	//TODO
	@Override
	public String toString() {
		String str = billeDecoree.toString();
		return str + ", Comportement : freinage dû aux frottements dans l'air ";
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
