package mesmaths.geometrie.base;

import java.io.Serializable;

import mesmaths.MesMaths;

/**
 * Vecteur du plan d�fini par un couple (x,y) o� x et y sont des r�els quelconques
 * 
 * 
 * */
public class Vecteur implements Serializable{
	/**
	 * Le vecteur nul (0,0)
	 * 
	 * */
	public static final Vecteur VECTEURNUL = new Vecteur(0,0);
	public double x,y;

	/**
	 * @param x
	 * @param y
	 */
	public Vecteur(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * cree le vecteur nul (0,0)
	 */
	public Vecteur(){this.x = this.y = 0;}

	/**
	 * 
	 * classique constructeur de copie
	 * 
	 * */
	public Vecteur(Vecteur v){this(v.x,v.y);}

	/**
	 * @return une copie de this, physiquement independante de this (c'est-a-dire que la copie ne partage pas d'espace memoire avec this) 
	 * */
	public Vecteur copie() { return new Vecteur(this);}

	public void set(Vecteur v) 
	{
		x = v.x; 
		y = v.y;
	}

	/**
	 * cree et renvoie un vecteur a coordonnees (x,y) aleatoires telles que 
	 * 
	 * xMin <= x <= xMax et yMin <= y <= yMax
	 * 
	 * 
	 * */
	public static Vecteur creationAleatoire(double xMin, double yMin, double xMax, double yMax)
	{
		return new Vecteur(MesMaths.creationAleatoire(xMin, xMax),MesMaths.creationAleatoire(yMin, yMax));
	}

	/**
	 * @param string respectant le format " ( nombre reel , nombre reel )"
	 * c'est le meme format que celui du resultat de toString() 
	 * Accepte un nombre quelconque d'espaces de part et d'autre des symboles '('  et ',' et  ')'
	 * 
	 * efface dans string tout caractere analyse avec succes
	 * 
	 * */
	public Vecteur(StringBuffer string)
	{
		int p0 = string.indexOf("(");

		if (p0 < 0) throw new NumberFormatException("dans Vecteur(StringBuffer) : \"(\" attendue");

		int p1 = string.indexOf(",");
		int p2 = string.indexOf(")");
		String sX, sY;

		sX = string.substring(1+p0, p1).trim();
		sY = string.substring(1+p1, p2).trim();

		this.x = Double.parseDouble(sX);
		this.y = Double.parseDouble(sY);
		string.delete(0, p2+1);
	}


	/**
	 * @param string respectant le format " ( nombre reel, nombre reel)"
	 * c'est le meme format que celui du resultat de toString() 
	 * Accepte un nombre quelconque d'espaces de part et d'autre des symboles '('  et ',' et  ')'
	 * 
	 * */
	public Vecteur(String string) { this(new StringBuffer(string)); }


	/**
	 * 
	 * @return  this + u
	 * 
	 * */

	public Vecteur somme(Vecteur u) {return new Vecteur(this.x+u.x,this.y+u.y);}


	/**
	 * 
	 * @return  this - u
	 * 
	 * */

	public Vecteur difference(Vecteur u) {return this.somme(u.oppose());}



	/**
	 * 
	 * @return  u - v
	 * 
	 * */

	public static Vecteur difference(Vecteur u, Vecteur v){return u.difference(v);}



	/**
	 * 
	 * @return a * u + b * v
	 * 
	 * */
	public static Vecteur combinaisonLineaire(double a, Vecteur u, double b, Vecteur v)
	{
		return new Vecteur(a * u.x + b * v.x,
				a * u.y + b * v.y);
	}

	/**
	 * realise this = this + u
	 * 
	 * */
	public void ajoute(Vecteur u)   { this.x += u.x; this.y += u.y;}

	/**
	 * realise this = this - u
	 * 
	 * */
	public void retire(Vecteur u)   { this.x -= u.x; this.y -= u.y;}

	/**
	 * realise this = r * this
	 * 
	 * */
	public void multiplie(double r) { this.x *= r;   this.y *=r;}

	/**
	 * 
	 * @return -this
	 * 
	 * */
	public Vecteur oppose()
	{
		return new Vecteur(-x,-y);
	}

	/**
	 * 
	 * @return r * this
	 * 
	 * */
	public Vecteur produit(double r)
	{
		return new Vecteur(r*this.x,r*this.y);
	}

	public double produitScalaire(Vecteur v)
	{
		return this.x*v.x + this.y*v.y;
	}

	public static double produitScalaire(Vecteur u, Vecteur v) {return u.produitScalaire(v);}



	/**
	 * calcule et renvoie le carre de la norme euclidienne de this, c-a-d (x*x + y*y)
	 * 
	 * */

	public double normeCarree()
	{
		return this.produitScalaire(this);
	}

	/**
	 * calcule et renvoie la norme euclidienne de this, c-�-d (x*x + y*y)^(1/2)
	 * 
	 * */
	public double norme()
	{
		return Math.sqrt(this.normeCarree());
	}

	/**
	 * calcule l'argument de *this au sens des nombres complexes
	 * */
	public double arg() { return Math.atan2(y, x); }

	public double determinant(Vecteur v)
	{
		return this.x * v.y - this.y * v.x;
	}

	public static double determinant(Vecteur u, Vecteur v) {return u.determinant(v);}

	/**
	 * calcule et renvoie la valeur de l'angle orient� (u,v) en rad
	 * 
	 * */
	public static double angleOriente(Vecteur u, Vecteur v)
	{
		double normeU, normeV;
		normeU = u.norme(); normeV = v.norme();
		double sinAngle = Vecteur.determinant(u,v)/(normeU*normeV);
		double cosAngle = Vecteur.produitScalaire(u,v)/(normeU*normeV);

		return Math.atan2(sinAngle,cosAngle);
	}



	/**
	 * @return le resultat par la rotation de +PI/2 de this
	 * 
	 * */
	public Vecteur rotationQuartDeTour()
	{
		return new Vecteur(-y,x);
	}

	/**
	 * fournit une base orthornormee directe associee a this
	 * base[0] est un vecteur unitaire colineaire et  de meme sens que this
	 * et base[1] est tel que base[1] soit le resultat de la rotation d'angle de +PI/2 par rapport a base[0]
	 *  
	 * */
	public Vecteur[] base()
	{
		Vecteur [] t= new Vecteur[2];

		double d = this.norme();
		t[0] = this.produit(1/d);
		t[1] = t[0].rotationQuartDeTour();
		return t;
	}

	/**
	 * @return this sous forme textuelle : "( abscisse, ordonnee)"
	 * */
	public String toString()
	{
		return "( " + this.x + ", " + this.y + ")";
	}

	/**
	 * calcule et renvoie le resultat de la multiplication au sens des nombres complexes de this par v
	 * 
	 * */
	public Vecteur produitComplexe(Vecteur v)
	{
		return new Vecteur(x*v.x - y*v.y,x*v.y + y*v.x); 
	}

	public Vecteur produitSpecial(Vecteur v)
	{
		return new Vecteur(x*v.x, y*v.y);
	}

	/**
	 * cree le vecteur unitaire d'argument alfa
	 * 
	 * */
	public static Vecteur creationPolaireUnitaire( double alfa)
	{
		return new Vecteur( Math.cos(alfa), Math.sin(alfa));
	}

	/**
	 * cree le vecteur de norme rayon et d'argument alfa
	 * 
	 * */
	public static Vecteur creationPolaire( double rayon, double alfa)
	{
		return Vecteur.creationPolaireUnitaire(alfa).produit(rayon);
	}

	/**
	 * @return le vecteur (|x|,|y|)
	 * */
	public Vecteur vaSpeciale() { return new Vecteur (Math.abs(x),Math.abs(y));}


}
