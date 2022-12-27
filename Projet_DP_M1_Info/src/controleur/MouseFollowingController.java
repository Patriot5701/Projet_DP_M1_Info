package controleur;

import java.awt.event.MouseEvent;

import mesmaths.geometrie.base.Vecteur;

public class MouseFollowingController extends ControllerState{

	public MouseFollowingController(ControllerGeneral ctrlrGen, ControllerState follow, ControllerState prev) {
		super(ctrlrGen, follow, prev);
	}

	@Override
	public Vecteur treat() {
		System.out.println("setPos");
		//C'est ici que la bille doit nous suivre
		//this.controllerGeneral.billePilotee.setPosition((int) this.xMouse, (int) this.yMouse);
		Vecteur mousePosition = new Vecteur(this.xMouse, this.yMouse);
		//Step 1
		Vecteur acceleration = mousePosition.difference(this.controllerGeneral.billePilotee.getPosition());
		//Step 2
		acceleration = acceleration.produit(1/Math.sqrt(Math.pow(acceleration.x, 2)+Math.pow(acceleration.y, 2)));
		//Step 3
		acceleration = acceleration.produit(0.005);
		
		
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
