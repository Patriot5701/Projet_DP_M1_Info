package vue;

import java.awt.Color;
import java.awt.Graphics;

import modele.Bille;

/**
 * Visteur AWT de la bille
 * @author clement
 *
 */
public class VisitorBilleAwt implements VisitorBille{
	private Graphics graphics;
	private CadreAngryBalls cadre;
	/**
	 * Constructeur
	 * @param graphics
	 */
	public VisitorBilleAwt(Graphics graphics, CadreAngryBalls cadre) {
		this.graphics = graphics;
		this.cadre = cadre;
	}

	@Override
	public void visitDraw(Bille bille) {
		
		int width, height;
		int xMin, yMin;
    
		xMin = (int)Math.round(bille.getPosition().x-bille.getRayon());
		yMin = (int)Math.round(bille.getPosition().y-bille.getRayon());

		width = height = 2*(int)Math.round(bille.getRayon());

		graphics.setColor(Color.decode(bille.getColor()));
		graphics.fillOval( xMin, yMin, width, height);
		graphics.setColor(Color.BLACK);
		graphics.drawOval(xMin, yMin, width, height);
	}

	@Override
	public double visitMaxWidth() {
		return this.cadre.largeurBillard();
	}

}
