package outilsvues.dessinateurs.brouillon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Stroke;

import mesmaths.geometrie.base.Vecteur;
import mesmaths.geometrie.base.awt.VecteurAWT;
import outilsvues.MonGraphics;



/**
 * 
 * classe utile au serveur de dessin
 * 
 * Cette classe s'engage � fournir les fonctionnalit�s suivantes (cf. classe C++ FenetreAWTJAVA) :
 * 
 * virtual void dessineSegment( const int x1, const int y1, const int x2, const int y2, 
 * const Couleur & couleur = (Couleur)"black", const int epaisseur = 1)
 * os << "segment " << x1 << " " << y1 << " " << x2 << " " << y2 << " " << couleur << " " << epaisseur;


virtual void dessineMessage( const string & message, const int x, const int y, const Couleur & couleur = (Couleur)"black")
os << "message " << x << " " << y << " " << couleur << " " << message;

virtual void dessineCercle( const Vecteur2D&  centre, const double & rayon, const Couleur & couleur = (Couleur)"black", const int epaisseur = 1);
int xCentre, yCentre;
os << "cercle " << xCentre << " " << yCentre << " " << (int)rayon << " " << couleur << " " << epaisseur;

virtual void dessineDisque( const Vecteur2D&  centre, const double & rayon,
                            const Couleur & couleurInterieur = (Couleur)"black", const Couleur & couleurBord = (Couleur)"black", const int epaisseurBord = 1);

int xCentre, yCentre;
os << "disque " << xCentre << " " << yCentre << " " << (int)rayon << " " << couleurInterieur << " " << couleurBord << " "<< epaisseurBord;

 * 
 * */

public class MonGraphicsDessinateur extends MonGraphics
{
	private int ancienneEpaisseurTrait = 1;
	private int nouvelleEpaisseurTrait = 1;
	private Color ancienneCouleur;
	private Color nouvelleCouleur;

	public MonGraphicsDessinateur(Graphics g)
	{
		super(g);
	}


	void setCouleurEtEpaisseur( Color couleur, int epaisseurTrait)
	{
		this.ancienneEpaisseurTrait = this.nouvelleEpaisseurTrait;
		this.ancienneCouleur = this.nouvelleCouleur;

		this.setEpaisseurTrait(epaisseurTrait);
		this.setColor(couleur);
	}

	void setAncienneCouleurEtEpaisseur()
	{
		this.setEpaisseurTrait(ancienneEpaisseurTrait);
		this.nouvelleCouleur = this.ancienneCouleur;
	}


	void dessineSegment( int x1, int y1, int x2, int y2, Color couleur, int epaisseurTrait)
	{
		Color ancienneCouleur = this.getColor();
		this.setColor(couleur);
		int ancienneEpaisseur = this.getEpaisseurTrait();
		this.setEpaisseurTrait(epaisseurTrait);
		this.g.drawLine(x1, y1, x2, y2);
		this.setEpaisseurTrait(ancienneEpaisseur);
		this.setColor(ancienneCouleur);
	}

	public void setEpaisseurTrait(int epaisseurTrait)
	{
		this.ancienneEpaisseurTrait = epaisseurTrait;

		this.g.setStroke(new BasicStroke(epaisseurTrait));
	}

	public void setAncienneEpaisseurTrait()
	{
		this.ancienneEpaisseurTrait = this.nouvelleEpaisseurTrait;

		this.g.setStroke(new BasicStroke(epaisseurTrait));
	}


	public int getEpaisseurTrait()
	{
		return this.ancienneEpaisseurTrait;
	}

	void dessineMessage(  String  message,  int x,  int y,  Color couleur)
	{
		Color ancienneCouleur = this.getColor();
		this.setColor(couleur);
		this.drawString( message, new Point(x,y));
		this.setColor(ancienneCouleur);
	}


	void dessineCercle(  Vecteur  centre,  double  rayon,  Color  couleur,  int epaisseurTrait)
	{
		Color ancienneCouleur = this.getColor();
		this.setColor(couleur);
		int ancienneEpaisseur = this.getEpaisseurTrait();
		this.setEpaisseurTrait(epaisseurTrait);

		int largeur, hauteur;

		largeur = hauteur = (int)(2*rayon);

		Vecteur coinHG1 = centre.difference(new Vecteur(rayon, rayon));
		VecteurAWT coinHG2 = new VecteurAWT(coinHG1);
		Point coinHG = coinHG2.toPoint();

		this.g.drawOval(coinHG.x, coinHG.y, largeur, hauteur);

		this.setEpaisseurTrait(ancienneEpaisseur);
		this.setColor(ancienneCouleur);
	}

	void dessineDisque( Vecteur centre, double rayon, Color couleur)
	{
		Color ancienneCouleur = this.getColor();
		this.setColor(couleur);

		VecteurAWT centreAWT = new VecteurAWT(centre);

		this.tracePoint( centreAWT.toPoint(), (int)rayon);

		this.setColor(ancienneCouleur);
	}

	void dessineDisque( Vecteur centre, double rayon, Color couleurInterieur, Color  couleurBord, int epaisseurBord)
	{
		dessineDisque(centre, rayon, couleurInterieur);
		dessineCercle( centre, rayon, couleurBord, epaisseurBord);
	}



}
