package controleur;

import java.awt.event.MouseEvent;
import mesmaths.geometrie.base.Vecteur;

/**
 * Controleur d'etat
 * @author clement
 *
 */
public abstract class ControllerState {
	protected double xMouse;
	protected double yMouse;
	
	protected ControllerGeneral controllerGeneral;
	
	protected ControllerState follower;
	protected ControllerState previous;
	/**
	 * Constructeur
	 * @param ctrlrGen : controleur general
	 * @param follow : controleur suivant
	 * @param prev : controleur precedent
	 */
	public ControllerState(ControllerGeneral ctrlrGen, ControllerState follow, ControllerState prev) {
		super();
		this.controllerGeneral = ctrlrGen;
		this.follower = follow;
		this.previous = prev;
	}
	/**
	 * Evenement : pression de la souris
	 * @param e : MouseEvent
	 */
	public void mousePressed(MouseEvent e) {}

	/**
	 * Evenement : relachement de la souris
	 * @param e : MouseEvent
	 */
	public void mouseReleased(MouseEvent e) {}
	/**
	 * Evenement : dragging de la souris
	 * @param e : MouseEvent
	 */
	public void mouseDragged(MouseEvent e) {
		this.xMouse = e.getX();
		this.yMouse = e.getY();
		System.out.println("("+this.xMouse+", "+this.yMouse+")");
		System.out.println("Mouse Moved !");
	}
	/**
	 * Ajoute le vecteur acceleration du au controle de l'utilisateur
	 * Ici, aucun controle par defaut donc ajoute un vecteur nul.
	 * @return Vecteur
	 */
	public Vecteur treat() {return Vecteur.VECTEURNUL;}
	
}
