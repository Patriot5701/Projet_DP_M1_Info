package controleur;

import java.awt.event.MouseEvent;
import mesmaths.geometrie.base.Vecteur;

public abstract class ControllerState {
	protected double xMouse;
	protected double yMouse;
	
	protected ControllerGeneral controllerGeneral;
	
	protected ControllerState follower;
	protected ControllerState previous;
	
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
	
	public void mouseDragged(MouseEvent e) {
		this.xMouse = e.getX();
		this.yMouse = e.getY();
		System.out.println("("+this.xMouse+", "+this.yMouse+")");
		System.out.println("Mouse Moved !");
	}
	
	public Vecteur treat() {return Vecteur.VECTEURNUL;}
	
}
