package outilsvues.dessinateurs;

import java.awt.Graphics2D;
import java.awt.Point;


/**
 * Pour g�rer les diff�rentes options des dessins : couleur, �paisseur du trait, etc.
 * Mise en oeuvre du DP D�corateur
 * 
 * */
public class DecorateurDessinateur implements Dessinateur
{
	protected Dessinateur suivant;

	/**
	 * @param suivant
	 */
	public DecorateurDessinateur(Dessinateur suivant)
	{
		this.suivant = suivant;
	}

	@Override
	public Graphics2D getGraphics2D()
	{
		return this.suivant.getGraphics2D();
	}

	public void avant() {}
	public void apres() {}

	@Override
	public void dessineSegment(Point debut, Point fin)
	{
		this.avant();
		this.suivant.dessineSegment(debut, fin);
		this.apres();
	}

	@Override
	public void dessineCercle(Point centre, int rayon)
	{
		this.avant();
		this.suivant.dessineCercle(centre, rayon);
		this.apres();
	}

	@Override
	public void dessineDisque(Point centre, int rayon)
	{
		this.avant();
		this.suivant.dessineDisque(centre, rayon);
		this.apres();
	}

	@Override
	public void dessineMessage(String message, Point position)
	{
		this.avant();
		this.suivant.dessineMessage(message, position);
		this.apres();
	}

}
