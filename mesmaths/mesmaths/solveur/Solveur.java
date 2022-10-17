package mesmaths.solveur;
import java.util.*;
/**
 * d�di�e � la r�solution des �quations de la forme f(x)=0 avec x r�el et f � valeurs r�elles
 * 
 * f est suppos�e �tre une instance d'une classe d�riv�e de Courbe
 * 
 * On suppose que f est une fonction continue, mais c'est mieux si elle d�rivable (c'est-�-dire si elle fournit un calcul de d�riv�e. cf. classe Courbe)
 * */
public class Solveur
{

	private static final double PRECISION = 1.0e-6; // pr�cision des r�sultats

	/**
	 * r�sout l'�quation f(x) = 0 sur l'intervalle fini [a,b]
	 * 
	 * On suppose que f(a)*f(b) < 0 et que l'�quation admet une unique solution sur [a,b]
	 * 
	 * @param f : telle que f(x) = 0 est l'�quation � r�soudre
	 * @param a : borne inf�rieure de l'intervalle de recherche
	 * @param b : borne sup�rieure de l'intervalle de recherche
	 * 
	 * @return la valeur de x telle que f(x) = 0 et x est dans [a,b]
	 * 
	 * 
	 * */
	public static double resout( Courbe f, double a, double b)
	{
		double fa, f1a, fb, f1b, u, fu, x, fx, f1x, v;

		fa = f.evalue(a);
		fb = f.evalue(b);

		if (fa*fb > 0) throw new IllegalArgumentException("l'�quation � r�soudre n'a peut-�tre pas de solution sur [ " + a + ", " + b + "]");

		u = a; fu = fa; v = b;
		f1a = f.evalueDerivee(a); 
		f1b = f.evalueDerivee(b);

		if (Math.abs(f1a) < Math.abs(f1b))              // on part toujours de la borne de plus grande pente
		{ x = b; fx = fb; f1x = f1b;}
		else
		{x = a; fx = fa; f1x = f1a;}


		// la boucle g�re les suites Un, f(Un), Xn, f(Xn), f'(Xn) et Vn
		while (true)
		{
			double r = fx/f1x;
			x -= r;                                     // it�ration Newton
			if (Math.abs(r)<PRECISION) return x;

			if (!(u <= x && x <= v)) x = 0.5*(u+v);     // dichotomie si �chec de Newton

			fx = f.evalue(x);
			f1x = f.evalueDerivee(x);

			if (fu*fx<0)
				v = x;
			else
			{u=x; fu = fx;}

		}    
	}

}
