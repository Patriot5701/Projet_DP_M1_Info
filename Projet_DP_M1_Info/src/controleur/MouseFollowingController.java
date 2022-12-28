package controleur;

import java.awt.event.MouseEvent;

import mesmaths.geometrie.base.Vecteur;

public class MouseFollowingController extends ControllerState{

	public MouseFollowingController(ControllerGeneral ctrlrGen, ControllerState follow, ControllerState prev) {
		super(ctrlrGen, follow, prev);
	}

	@Override
	public Vecteur treat() {
		System.out.println("pos = ");
		System.out.println(this.controllerGeneral.billePilotee.getPosition());
		Vecteur mousePosition = new Vecteur(this.xMouse, this.yMouse);
		System.out.println("mousePosition = ("+this.xMouse+", "+this.yMouse+")");
		//Step 1 - Direction du vecteur
		Vecteur acceleration = mousePosition.difference(this.controllerGeneral.billePilotee.getPosition());
		//Step 2 - Normalisation du vecteur a 1
		acceleration = acceleration.produit(1/Math.sqrt(Math.pow(acceleration.x, 2)+Math.pow(acceleration.y, 2)));
		//Step 3 - Constante d'acceleration : La bille suit la souris plus ou moins rapidement
		acceleration = acceleration.produit(0.01);
		System.out.println("acc = "+acceleration);
		
		return acceleration;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("relâchée!");
		this.controllerGeneral.setCurrentController(follower);
	}

}
