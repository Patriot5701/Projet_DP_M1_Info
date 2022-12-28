package vue;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.Vector;

import modele.Bille;

import java.awt.image.BufferStrategy;

/**
 * responsable du dessin des billes
 * 
 * ICI : IL N'Y A RIEN A CHANGER
 * 
 * 
 */
public class Billard extends Canvas {

	Vector<Bille> billes;
	VisitorBille visitor = new VisitorBilleAwt(getGraphics());



	public Billard(Vector<Bille> billes) {
		this.billes = billes;

	}




	public void myRenderingLoop() {
		this.setVisible(true); // rend le frame visible sur l'écran
		this.setIgnoreRepaint(true); // désactive l'appel automatique de la fct paint(...) par repaint()
		int numBuffers = 2;
		this.createBufferStrategy(numBuffers); // crée une stratégie de tampon d'image à 1 tampon vidéo
		
		BufferStrategy stratégie = this.getBufferStrategy();
		Graphics graphics = stratégie.getDrawGraphics();
		// le graphics sert à dessiner sur le tampon
		visitor = new VisitorBilleAwt(graphics);
		for (int i = 0; i < this.billes.size(); ++i) {
			this.billes.get(i).accepteDraw(visitor);
		}

		stratégie.show(); // place le tampon sur l'écran : la technique utilisée dépend du type de
							// stratégie utilisé : blitting, pointeur vidéo, etc.

		graphics.dispose();
	}


	public void update() {
		myRenderingLoop();
	}
}