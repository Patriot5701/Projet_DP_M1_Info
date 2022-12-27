package controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import vue.CadreAngryBalls;

public class EcouteurPosition implements MouseListener{
	
	CadreAngryBalls cadre;
	int xDepart, yDepart, xArrivee, yArrivee;
	
	public EcouteurPosition(CadreAngryBalls cadre) {
		super();
		this.cadre = cadre;
	}
	
	public void active() {
		this.cadre.billard.addMouseListener(this);
	}
	
	public void desactive() {
		this.cadre.billard.removeMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.xDepart = e.getX();
		this.yDepart = e.getY();
		
		System.out.println(xDepart);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.xArrivee = e.getX();
		this.yArrivee = e.getY();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
