package outilsvues.dessinateurs;

import java.awt.BasicStroke;
import java.awt.Stroke;


/**
 * permet de d�finir l'�paisseur du trac� des traits
 * 
 * */
public class DessinateurEpaisseur extends DecorateurDessinateur
{
	private Stroke stroke, strokeAvant;

	/**
	 * Tous les traits dessin�s par this seront dessin�s avec l'�paisseur sp�cifi�e
	 * 
	 * 
	 * */
	public DessinateurEpaisseur(Dessinateur suivant, int epaisseurTrait)
	{
		super(suivant);
		this.stroke = new BasicStroke(epaisseurTrait);
	}

	@Override
	public void avant()
	{
		this.strokeAvant = this.getGraphics2D().getStroke();
		this.getGraphics2D().setStroke( this.stroke);
	}

	@Override
	public void apres()
	{
		this.getGraphics2D().setStroke(strokeAvant);
	}





}
