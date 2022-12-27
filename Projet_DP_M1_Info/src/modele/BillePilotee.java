package modele;

import java.util.Vector;
import controleur.ControllerGeneral;
import vue.CadreAngryBalls;

public class BillePilotee extends DecorateurBille {
	ControllerGeneral controllerGen;

	public BillePilotee(Bille billeDecorated, CadreAngryBalls cadre) {
		super(billeDecorated);
		
		controllerGen = new ControllerGeneral(this, cadre);
	}

	@Override
	public void gestionAcceleration(Vector<Bille> billes) {
		this.billeDecoree.gestionAcceleration(billes);
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

	@Override
	public String toString() {
		String str = billeDecoree.toString();
		return str + ", Comportement : attrapable";
	}

}
