package controleur;

import java.awt.event.MouseEvent;

public abstract class ControllerState {
	ControllerGeneral controllerGeneral;
	
	public ControllerState follower, previous;
	
	public ControllerState(ControllerGeneral ctrlrGen, ControllerState follow, ControllerState prev) {
		super();
		this.controllerGeneral = ctrlrGen;
		this.follower = follow;
		this.previous = prev;
	}
	
	public void mousePressed(MouseEvent e) {
		//CaughtController va faire le taff
		//Sinon affiche le msg suivant car est dans ThrownController
		System.out.println("SALUT MEC !!!");
	}


	public void mouseReleased(MouseEvent e) {
		//ThrownController va faire le taff
		//Sinon affiche le msg suivant car est dans CaughtController
		System.out.println("DEGAGE MEC !!!");

	}
	
	public void treat() {
		this.controllerGeneral.setCurrentController(this.follower);
	}
	
}
