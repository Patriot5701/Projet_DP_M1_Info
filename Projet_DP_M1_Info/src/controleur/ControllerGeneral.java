package controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import modele.BillePilotee;
import vue.CadreAngryBalls;

/**
 * Controleur general mettant en place le DP State : le controle de la bille par l'utilisateur
 * @author clement
 *
 */
public class ControllerGeneral implements MouseListener, MouseMotionListener{
	BillePilotee billePilotee;	//Reference sur modele
	private CadreAngryBalls cadre;		//Reference sur vue
	
	public ControllerState currentController;	//Le controleur qui voyage à travers le graphe orienté
	private MouseFollowingController mouseFollowingController;	//Associé à l'état "Controle"
	private AutoMovingController autoMovingController;	//Associé à l'état "Non controle"
	
	/**
	 * Constructeur
	 * @param billePilotee
	 * @param cadre
	 */
	public ControllerGeneral(BillePilotee billePilotee, CadreAngryBalls cadre) {
		this.cadre = cadre;
		this.billePilotee = billePilotee;
		this.installControllers();	//Permet d'écouter les évènements Souris
		
		this.cadre.billard.addMouseListener(this);
		this.cadre.billard.addMouseMotionListener(this);
	}
	/**
	 * Installe les controleurs 
	 */
	private void installControllers() {
		
		//Instanciation des controleurs
		this.mouseFollowingController = new MouseFollowingController(this, null, null);
		this.autoMovingController = new AutoMovingController(this, mouseFollowingController, null); //On met caught en follower car ils forment une boucle
		
		//On rajoute les liens manquants entre les controleurs
		this.mouseFollowingController.follower = this.autoMovingController;
		
		//On place le contrôleur courant au premier état du graphe
		this.currentController = this.autoMovingController;
	}
	/**
	 * Change de controleur actuel
	 * @param controller
	 */
	public void setCurrentController(ControllerState controller) {
		this.currentController = controller;
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

	//Inutile
	@Override
	public void mouseDragged(MouseEvent e) {this.currentController.mouseDragged(e);}

	//Inutile
	@Override
	public void mouseMoved(MouseEvent e) {}


}
