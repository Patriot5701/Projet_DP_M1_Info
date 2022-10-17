package outilsvues;

import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class EcouteurTerminaison implements WindowListener
{

public EcouteurTerminaison(Window window)
{
window.addWindowListener(this);
}

@Override
public void windowActivated(WindowEvent arg0)
{
// TODO Auto-generated method stub

}

@Override
public void windowClosed(WindowEvent arg0)
{
// TODO Auto-generated method stub

}

@Override
public void windowClosing(WindowEvent arg0)
{
System.exit(0);

}

@Override
public void windowDeactivated(WindowEvent arg0)
{
// TODO Auto-generated method stub

}

@Override
public void windowDeiconified(WindowEvent arg0)
{
// TODO Auto-generated method stub

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
