package controleur;

import java.awt.event.ActionEvent;
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
	
	//TOEND
	public void actionPerformed(ActionEvent e) {
		//TouchePavé touche = (TouchePavé)(arg0.getSource());

		//char c = touche.getSymbole();
		//traite(c);
	}
	
	public void mousePressed(MouseEvent e) {
		//char c = arg0.getKeyChar();
		//traite(c);

	}


	public void mouseReleased(MouseEvent e) {
		//char c = arg0.getKeyChar();
		//traite(c);

	}
	
	/**
	 * Traitement de l'évènement : la frappe d'une touche (virtuelle ou physique) portant le symbole c
	 * 
	 * */
	//public  void traite(char c){
		//if (!this.traiteRetour(c))traiteCasGeneral(c); 
		//}


	/** on suppose que c ne correspond pas au cas "retour à l'état suivant" */
	//protected abstract void traiteCasGeneral(char c);

	 
	/**
	 * Traite le cas du retour à l'état précédent
	 * 
	 * */
	//public boolean traiteRetour(char c){
		//if (c == 'c' && this.retour != null){
			//this.controleurGeneral.calculetteState.effaceFinRésultat();
		//this.controleurGeneral.setContrôleurCourant(this.retour);
		//return true;
		//}else 
		//return false;
		//}

	//}


}
