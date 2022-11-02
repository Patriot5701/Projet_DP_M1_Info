package outilsvues.dessinateurs;

import java.awt.Color;

import parseurs.ParseurCouleurAnglais;
import parseurs.ParseurCouleurRGB;

/**
 * permet de definir la couleur des dessins
 * 
 * */
public class DessinateurCouleur extends DecorateurDessinateur
{
	static GParseurCOR<Color,String> parseurCouleur = null;

	static 
	{
		parseurCouleur = new ParseurCouleurAnglais(parseurCouleur);
		parseurCouleur = new ParseurCouleurRGB(parseurCouleur);
	}

	private Color couleur, couleurAvant;


	/**
	 * Tous les dessins effectu�s par this seront effectu�s avec la couleur sp�cifi�e
	 * 
	 * 
	 * */
	public DessinateurCouleur(Dessinateur suivant, Color couleur)
	{
		super(suivant);
		this.couleur = couleur;
	}

	public DessinateurCouleur(Dessinateur suivant, String couleur)
	{
		this(suivant,parseurCouleur.parse(couleur));
	}

	@Override
	public void avant()
	{
		this.couleurAvant = this.getGraphics2D().getColor();
		this.getGraphics2D().setColor(this.couleur);
	}

	@Override
	public void apres()
	{
		this.getGraphics2D().setColor(this.couleurAvant);
	}




}
