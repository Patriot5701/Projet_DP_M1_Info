package mesmaths.solveur;


/**
 * Permet de r�soudre l'�quation ln(x) - (x-5)^2 = 0.
 * Nous savons qu'il y a exactement 2 solutions x1 et x2 telles que 0 < x1 < 5 et x2 > 5 
 * 
 * 
 * */
public class Equation2 extends Courbe
{

	@Override
	public double evalue(double x)
	{
		double r = x-5;
		return Math.log(x)-r*r;
	}

	@Override
	public double evalueDerivee(double x)
	{
		//return super.�valueD�riv�e(x);
		return 1/x - 2*(x-5);                   // l'algo de Solveur fonctionne aussi si cette classe ne fournit pas ce calcul mais il sera moins pr�cis et plus lent
	}

}
