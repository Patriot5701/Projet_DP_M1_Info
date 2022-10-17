package mesmaths.solveur;


/**
 * fonction de la forme : x |---> f(x) dont on va chercher les z�ros, c'est-�-dire les valeurs de x pour les quelles on a f(x) = 0
 * 
 * On suppose que x est r�el et que f(x) est r�el
 * 
 * Les �quations sp�cifiques � r�soudre sont d�finies par les classes d�riv�es
 * 
 * 
 * 
 * */
public abstract class Courbe
{

	private static final double EPSILON = 0.001;        // valeur petite d�finie arbitrairement

	/**
	 * @return la valeur de f(x) 
	 * 
	 * 
	 * */
	public abstract double evalue( double x);

	/**
	 * @return la valeur de f'(x) 
	 * 
	 * Les classes d�riv�es sont invit�es (mais ce n'est pas obligatoire) � fournir un calcul plus pr�cis 
	 * 
	 * car plus les calculs sont pr�cis, plus l'algo du solveur est rapide
	 * 
	 * A ce niveau, on ne peut faire qu'un calcul approch�.
	 * 
	 * */
	public  double evalueDerivee( double x)
	{
		double x1 = x + EPSILON;

		return (evalue(x1)-evalue(x))/EPSILON;  // approximation du calcul de la derivee
	}


}
