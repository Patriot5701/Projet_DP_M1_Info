package modele;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

public class BillePilotee extends DecorateurBille implements MouseMotionListener {

	public BillePilotee(Bille billeDecorated) {
		super(billeDecorated);
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

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

}
