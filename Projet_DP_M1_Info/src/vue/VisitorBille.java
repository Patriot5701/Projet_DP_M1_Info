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
	
	/**
	 * Chargé de renvoyer la largeur max du cadre
	 * @param bille
	 * @return double
	 */
	public double visitMaxWidth();
	
	

}
