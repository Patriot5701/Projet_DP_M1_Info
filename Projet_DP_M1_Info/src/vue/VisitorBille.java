package vue;

import modele.Bille;
/**
 * Interface du DP Visiteur de la bille
 * @author clement
 *
 */
public interface VisitorBille {
	/**
	 * Chargé de dessiner la bille 
	 * @param bille
	 */
	public void visitDraw(Bille bille);

}
