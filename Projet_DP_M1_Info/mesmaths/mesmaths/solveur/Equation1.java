package mesmaths.solveur;

/**
 * 
 * Permet de r�soudre l'�quation (x-1)*(x-2)*(x-3) = 0
 * 
 * */
public class Equation1 extends Courbe
{

	@Override
	public double evalue(double x)
	{
		return (x-1)*(x-2)*(x-3);
	}

	@Override
	public double evalueDerivee(double x)
	{
		//return super.�valueD�riv�e(x);
		return 11 + x*(-12 + 3*x);                           // l'algo de Solveur fonctionne aussi si cette classe ne fournit pas ce calcul mais il sera moins pr�cis et plus lent
	}



}
