package modele;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class BillePilotee extends DecorateurBille implements MouseListener {

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

	//inutile
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	//inutile
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	//inutile
	@Override
	public void mouseExited(MouseEvent e) {
	}



}
