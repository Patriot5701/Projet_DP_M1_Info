package modele;

import java.util.Vector;

public class MvmtRectUnif extends DecorateurBille{

	public MvmtRectUnif(Bille billeDecorated) {
		super(billeDecorated);
	}

	@Override
	public double masse() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deplacer(double deltaT) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gestionAcceleration(Vector<Bille> billes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean gestionCollisionBilleBille(Vector<Bille> billes) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void collisionContour(double abscisseCoinHautGauche, double ordonneeCoinHautGauche, double largeur,
			double hauteur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		String str = billeDecoree.toString();
		return str + ", Comportement : mouvement rectiligne uniforme";
	}

}
