package outilsvues.dessinateurs.brouillon;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import outilsvues.dessinateurs.FrameDessin;

public class EcouteurDessin implements WindowListener
{
	FrameDessin frameDessin;


	/**
	 * @param frameDessin
	 */
	public EcouteurDessin(FrameDessin frameDessin)
	{
		this.frameDessin = frameDessin;
		this.frameDessin.addWindowListener(this);
	}

	@Override
	public void windowActivated(WindowEvent arg0)
	{
		this.frameDessin.strategie.show();

	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0)
	{
		this.frameDessin.strategie.show();

	}

	@Override
	public void windowIconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

}
