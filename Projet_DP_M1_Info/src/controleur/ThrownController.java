package controleur;

import java.awt.event.MouseEvent;

public class ThrownController extends ControllerState{

	public ThrownController(ControllerGeneral ctrlrGen, ControllerState follow, ControllerState prev) {
		super(ctrlrGen, follow, prev);
	}
	
	@Override
	public void treat() {
		
		//TODO
		System.out.println("thrown");
		
		super.treat();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("VAS-T-EN MEC !!!");
		treat();
	}
	

}
