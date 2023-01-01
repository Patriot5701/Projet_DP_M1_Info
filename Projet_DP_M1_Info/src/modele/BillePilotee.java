package modele;

import java.util.Vector;
import controleur.ControllerGeneral;
import vue.CadreAngryBalls;

/**
 * Comportement gérant le pilotage de la bille par l'utilisateur
 * @author clement
 *
 */
public class BillePilotee extends DecorateurBille {
	ControllerGeneral controllerGen;

	/**
	 * Constructeur
	 * @param billeDecorated : la bille a decorer
	 * @param cadre : la vue
	 */
	public BillePilotee(Bille billeDecorated, CadreAngryBalls cadre) {
		super(billeDecorated);
		
		controllerGen = new ControllerGeneral(this, cadre);
	}
	
	@Override
	public void gestionAcceleration(Vector<Bille> billes) {
		this.billeDecoree.gestionAcceleration(billes);
		this.getAcceleration().ajoute(this.controllerGen.currentController.treat());
	}


	@Override
	public void collisionContour(double abscisseCoinHautGauche, double ordonneeCoinHautGauche, double largeur,
			double hauteur) {
		billeDecoree.collisionContour(abscisseCoinHautGauche, ordonneeCoinHautGauche, largeur, hauteur);
	}



}
