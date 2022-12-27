package controleur;

import java.awt.event.MouseEvent;

import mesmaths.geometrie.base.Vecteur;

public abstract class ControllerState {
	double xMouse;
	double yMouse;
	
	ControllerGeneral controllerGeneral;
	
	public ControllerState follower, previous;
	
	public ControllerState(ControllerGeneral ctrlrGen, ControllerState follow, ControllerState prev) {
		super();
		this.controllerGeneral = ctrlrGen;
		this.follower = follow;
		this.previous = prev;
	}
	
	public void mousePressed(MouseEvent e) {
		System.out.println("SALUT MEC !!!");
	}


	public void mouseReleased(MouseEvent e) {
		System.out.println("DEGAGE MEC !!!");

	}
	
	public void mouseMoved(MouseEvent e) {
		this.xMouse = e.getX();
		this.yMouse = e.getY();
	}
	
	public Vecteur treat() {return Vecteur.VECTEURNUL;}
	
}
