package mesmaths.cinematique;

import mesmaths.geometrie.ExceptionGeometrique;
import mesmaths.geometrie.base.Vecteur;
import mesmaths.geometrie.formes.ArcCourbe;

/**
 * ArcDansContour au sens large : rectiligne ou curviligne
 * Represente une partie orientee d'un contour ferme.
 * Un arc dispose d'un sens de parcours indique par la base. (cf. methode base())
 * 
 * A revoir
 * 
 * 
 * */
public abstract class ArcDansContour extends ArcCourbe
{
	public ArcCourbe arcCourbe;

	public ArcDansContour(ArcCourbe arcCourbe)
	{
		super();
		this.arcCourbe = arcCourbe;
	}

	/**
	 * Calcule l'abscisse curviligne t du projet� orthogonal de p sur this
	 * l'abscisse curviligne t d�crit l'intervalle [0,1].
	 * t == 0 <---> d�but de l' arc et t == 1 <---> fin de l' arc 
	 * @throws une ExceptionGeometrique pour indiquer que le projet� orthogonal de p n'est pas d�fini
	 * 
	 * */
	@Override
	public double projeteOrthogonal(Vecteur p) throws ExceptionGeometrique
	{
		return this.arcCourbe.projeteOrthogonal(p);
	}

	/**
	 * Calcule, sur l'arc this, le point de passage  correspondant a l'abscisse curviligne t.
	 * 
	 * */
	@Override
	public Vecteur evalue(double t)
	{
		return this.arcCourbe.evalue(t);
	}


	/**
	 * Calcule un vecteur tangent (pas forcement unitaire) dans le sens positif en p(t)
	 * 
	 * t doit etre tel que 0 <= t <= 1
	 * 
	 * */
	@Override
	public Vecteur vecteurTangent(double t)
	{
		return this.arcCourbe.vecteurTangent(t);
	}


	/**
	 * Calcule une base orthonormale de 2 vecteurs {i,j} au point d'abscisse curviligne t.
	 * On doit avoir 0 <= t <= 1. t == 0 <---> d�but de l' arc et t == 1 <---> fin de l' arc 
	 * Cette base est telle que i soit un vecteur tangent � l'arc et j soit un vecteur rentrant dans le contour.
	 * j indique ou se trouve l'interieur du contour ferme. 
	 * |i| == |j| == 1 et i.j == 0
	 * @return un tableau t de 2 elements tel que t[0] = i et tel que t[1] = j
	 * */
	@Override
	public Vecteur[] base(double t)
	{
		return this.arcCourbe.base(t);
	}

	@Override
	public double[] intersectionDroite(Vecteur A, Vecteur n)
	{
		return this.arcCourbe.intersectionDroite(A, n);
	}



	/**
	 * fait la meme chose que la 2eme methode oppose() 
	 * N est un point quelconque de this. 
	 * u est le vecteur unitaire sur une droite D passant par N et perpendiculaire au contour. u est oriente vers l'exterieur.
	 * D = {N,u}
	 * On suppose qu'il existe un point p sur D a l'interieur du contour tel que l'abscisse de p sur {N,u} soit d (donc d < 0)
	 * 
	 * */
	public abstract Vecteur oppose(Vecteur N, Vecteur u, double d);

	/**
	 * pour factoriser du code dans les 2 methodes qui suivent
	 * 
	 * calcule l'abscisse curviligne t, le vecteur normal rentrant j, le projete orthogonal et d, la distance signee de projete orthogonal a p
	 * 
	 * */
	public void f(final Vecteur p, double t[], Vecteur j, Vecteur projete, double d[])
	{
		t[0] = this.projeteOrthogonal(p);
		j.set( this.base(t[0]) [1]);
		projete.set( this.evalue(t[0]) );
		d[0] = Vecteur.difference(p, projete).produitScalaire(j);
	}

	/**
	 * Calcule la distance sign�e entre le point p et l'arc this. 
	 * Si cette distance est > 0, p est a l'interieur du contour
	 * Si cette distance est < 0, p est a l'exterieur du contour
	 * Si cette distance est == 0, p est a sur l'arc
	 * 
	 * @throws une ExceptionGeometrique pour indiquer que la distance signee n'est pas definie (se produit lorsque le projete orthogonal de p n'est pas defini)
	 * 
	 * */
	public double distanceSignee(Vecteur p) throws ExceptionGeometrique
	{
		double t[] = new double[1];
		Vecteur j = new Vecteur();
		Vecteur projete = new Vecteur();
		double d[] = new double[1];

		this.f(p, t, j, projete, d);
		return d[0];
	}


	/**
	 * calcule les eventuels points d'intersection avec la droite definie par {A,n}
	 * ou A est un point de passage et n un vecteur normal � la droite
	 * 
	 * @return le tableau des points d'intersection entre this et {A,n}
	 * 
	 * */
	public  Vecteur [] intersectionArcDroite(final Vecteur A, final Vecteur n)
	{
		double t[] = this.intersectionDroite(A, n);

		Vecteur [] r = new Vecteur[t.length];
		int i;
		for (i = 0; i < t.length; ++i) r[i] = evalue(t[i]);

		return r;
	}

	/**
	 * Calcule l'oppos� de p suivant la direction et le sens defini par le projete orthogonal N au contour C dont this fait partie.
	 * Explication : Le point trouve est unique. 
	 * Il est defini comme suit :
	 * 
	 * Notons d la droite munie du repere oriente {p,n} avec p comme point de passage et n vecteur directeur de la droite orthogonale a this passant par p
	 * On suppose que p est dans le contour. n orient� de p vers N.
	 * La droite coupe le contour C en 2 points A et B d'eloignement maximal puisque C est ferme et que p est aa l'interieur de C.
	 * Supposons que les points A, p et B soient orientes dans le sens A < p < B (suivant n).
	 * Notons xA, xp,xB les abscisses respectifs de A, p et B suivant le repere {p,n}
	 * 
	 * notons deltaX = xB - xp.
	 * 
	 * Alors retourne le point M tel que vecteur(M ->A) == deltaX * n
	 * 
	 * 
	 * * @throws une ExceptionGeometrique pour indiquer que l'oppose n'est pas defini (se produit lorsque le projete orthogonal de p n'est pas defini)
	 * 
	 * */
	public Vecteur oppose(Vecteur p) throws ExceptionGeometrique
	{
		double t[] = new double[1];
		Vecteur j = new Vecteur();
		Vecteur projete = new Vecteur();
		double d[] = new double[1];

		this.f(p, t, j, projete, d);

		return this.oppose(projete, j.oppose(), -d[0]);
	}







}
