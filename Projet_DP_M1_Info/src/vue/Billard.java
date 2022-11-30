package vue;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.Graphics;
import java.util.Vector;

import modele.Bille;

import java.awt.GraphicsEnvironment;
import java.awt.Window;



/**
 * responsable du dessin des billes
 * 
 *  ICI : IL N'Y A RIEN A CHANGER 
 *  
 * 
 * */
public class Billard extends Canvas
{
	Vector<Bille> billes;
	CadreAngryBalls cadre;
	public Billard(Vector<Bille> billes)
	{
		this.billes = billes;
	}
	
	//Pour l'active rendering
	public Billard(Vector<Bille> billes, CadreAngryBalls cadre)
	{
		this.billes = billes;
		this.cadre = cadre;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Canvas#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics graphics)
	{
		int i;
		//GraphicsDevice myDevice;
		//Window myWindow = new Window(cadre);
	
		
		//GraphicsEnvironment environnement = GraphicsEnvironment.getLocalGraphicsEnvironment();
		//myDevice = environnement.getDefaultScreenDevice();
		
		//if(myDevice.isFullScreenSupported()) {
			//try {
				//myDevice.setFullScreenWindow(myWindow);
				//for ( i = 0; i < this.billes.size(); ++i)
					//this.billes.get(i).dessine(graphics);
			//}finally {
				//myDevice.setFullScreenWindow(null);
			//}
		//}else {
			for ( i = 0; i < this.billes.size(); ++i)
				this.billes.get(i).dessine(graphics);
		//}
		
		

		

		//System.out.println("billes dans le billard = " + billes);
	}



}
