package outilsvues.dessinateurs;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;


/**
 * Dessinateur simple sans options particuli�re de couleur, d'�paisseur de trait ou autre
 * 
 * 
 * */
public class DessinateurSimple implements Dessinateur
{
	Graphics2D graphics;

	/**
	 * @param graphics
	 */
	public DessinateurSimple(Graphics graphics)
	{
		this.graphics = (Graphics2D)graphics;
	}

	@Override
	public Graphics2D getGraphics2D()
	{
		return this.graphics;
	}

	@Override
	public void dessineSegment(Point debut, Point fin)
	{
		graphics.drawLine(debut.x, debut.y, fin.x, fin.y);
	}

	@Override
	public void dessineCercle(Point centre, int rayon)
	{

		int largeur, hauteur;

		largeur = hauteur = 2*rayon;

		graphics.drawOval(centre.x-rayon, centre.y-rayon, largeur, hauteur);

	}

	@Override
	public void dessineDisque(Point centre, int rayon)
	{

		int largeur, hauteur;

		largeur = hauteur = 2*rayon;

		graphics.fillOval(centre.x-rayon, centre.y-rayon, largeur, hauteur);

	}

	@Override
	public void dessineMessage(String message, Point position)
	{
		int d = 5;
		graphics.drawString(message, position.x+d, position.y+d);
	}

}
