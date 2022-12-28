package vue;

import java.awt.Color;
import java.awt.Graphics;

import modele.Bille;

/**
 * Visteur AWT de la bille
 * @author cleme
 *
 */
public class VisitorBilleAwt implements VisitorBille{
	Graphics graphics;
	/**
	 * Constructeur
	 * @param graphics
	 */
	public VisitorBilleAwt(Graphics graphics) {
		this.graphics = graphics;
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
		graphics.setColor(Color.CYAN);
		graphics.drawOval(xMin, yMin, width, height);
	}

}
