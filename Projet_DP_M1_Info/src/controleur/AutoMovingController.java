package controleur;

import java.awt.event.MouseEvent;

public class AutoMovingController extends ControllerState{

	public AutoMovingController(ControllerGeneral ctrlrGen, ControllerState follow, ControllerState prev) {
		super(ctrlrGen, follow, prev);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {
		double xMouse = (double) e.getX();
		double yMouse = (double) e.getY();
		
		double xBille = controllerGeneral.billePilotee.getPosition().x;
		double yBille = controllerGeneral.billePilotee.getPosition().y;
		double rBille = controllerGeneral.billePilotee.getRayon();
		
		if(xMouse <= xBille+rBille && xMouse >= xBille-rBille && yMouse <= yBille+rBille && yMouse >= yBille-rBille) {
			this.controllerGeneral.setCurrentController(follower);
			System.out.println("attrapée !");
		}
	}

}
