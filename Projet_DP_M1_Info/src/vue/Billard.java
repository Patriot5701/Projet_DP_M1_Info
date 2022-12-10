package vue;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JFrame;

import modele.Bille;

import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.image.BufferStrategy;



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
	VisitorBille visitor = new VisitorBilleAwt(getGraphics());
		
	GraphicsDevice myDevice;
	Window myWindow = new Window(cadre);
	
	public Billard(Vector<Bille> billes)
	{
		this.billes = billes;
	}
	
	//Pour l'active rendering
	public Billard(Vector<Bille> billes, CadreAngryBalls cadre)
	{
		this.billes = billes;
		this.cadre = cadre;
		//myRenderingLoop();
	}
	
	public void myRenderingLoop() {
	    boolean done = false;
	    BufferStrategy myStrategy = null;

	    while (!done) {
	        Graphics g = null;
	        try {
	            g = myStrategy.getDrawGraphics();
	            visitor = new VisitorBilleAwt(g);
	            paint(g);
	            for (int i = 0; i < this.billes.size(); ++i) {
					//this.billes.get(i).dessine(graphics);
					this.billes.get(i).accepteDraw(visitor);
				}
	            render(g);
	        } finally {
	            g.dispose();
	        }
	        myStrategy.show();
	    }
	}
	
	private void render(Graphics graphics) {

	    BufferStrategy bs = this.getBufferStrategy();

	    if (bs == null){
	        createBufferStrategy(3);
	        return;
	    }

	    
	    bs.show();

	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.Canvas#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics graphics)
	{
		int i;
		visitor = new VisitorBilleAwt(graphics);
//		JFrame f = new JFrame();
//		GraphicsEnvironment environnement = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		myDevice = environnement.getDefaultScreenDevice();
//		DisplayMode newDisplayMode = new DisplayMode(this.getWidth(),this.getHeight(),DisplayMode.BIT_DEPTH_MULTI , DisplayMode.REFRESH_RATE_UNKNOWN );
//		DisplayMode oldDisplayMode = myDevice.getDisplayMode();
//		if(myDevice.isFullScreenSupported()) {
//			try {
//				myDevice.setFullScreenWindow(myWindow);
//				//myDevice.setDisplayMode(newDisplayMode);
//				f.setUndecorated(true);
//				
//				for ( i = 0; i < this.billes.size(); ++i)
//					//this.billes.get(i).dessine(graphics);
//					this.billes.get(i).accepteDraw(visitor);
//				myDevice.setFullScreenWindow(null);
//			}finally {
//				//myDevice.setDisplayMode(oldDisplayMode);
//				myDevice.setFullScreenWindow(null);
//			}
//		}else {
			for ( i = 0; i < this.billes.size(); ++i) {
				this.billes.get(i).accepteDraw(visitor);
			}
		//}

		

		//System.out.println("billes dans le billard = " + billes);
	}



}
