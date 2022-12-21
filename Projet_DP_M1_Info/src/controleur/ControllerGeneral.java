package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import modele.BillePilotee;
import vue.CadreAngryBalls;

public class ControllerGeneral implements MouseListener{
	BillePilotee billePilotee;	//Reference sur modele
	CadreAngryBalls cadre;		//Reference sur vue
	
	EcouteurPosition ecouteurPosition;
	
	ControllerState currentController;	//Le controleur qui voyage à travers le graphe orienté
	CaughtController caughtController;	//Associé à l'état "Bille Attrapée"
	ThrownController thrownController;	//Associé à l'état "Bille Lancée"
	
	public ControllerGeneral(BillePilotee billePilotee, CadreAngryBalls cadre) {
		this.cadre = cadre;
		this.billePilotee = billePilotee;
		this.installControllers();	//Permet d'écouter les évènements Souris
		
		this.cadre.billard.addMouseListener(this);
	}
	
	private void installControllers() {
		this.ecouteurPosition = new EcouteurPosition(cadre);
		
		//Instanciation des controleurs
		this.caughtController = new CaughtController(this, null, null);
		this.thrownController = new ThrownController(this, null, this.caughtController);
		
		//On rajoute les liens manquants entre les controleurs
		this.caughtController.follower = this.thrownController;
		
		//On place le contrôleur courant au premier état du graphe
		this.currentController = this.caughtController;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.currentController.mousePressed(e); //Transmet gestion evenement au controlleur courant
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.currentController.mouseReleased(e); //Transmet gestion evenement au controlleur courant
	}
	
	//Inutile
	@Override
	public void mouseClicked(MouseEvent e) {}
	

	//Inutile
	@Override
	public void mouseEntered(MouseEvent e) {}

	//Inutile
	@Override
	public void mouseExited(MouseEvent e) {}

}
