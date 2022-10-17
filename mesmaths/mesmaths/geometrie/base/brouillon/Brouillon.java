package mesmaths.geometrie.base.brouillon;

public class Brouillon
{
	private double x;
	private double y;

	/**
	 * @param string respectant le format " ( nombre r�el , nombre r�el )"
	 * c'est le m�me format que celui du r�sultat de toString() 
	 * Accepte un nombre quelconque d'espaces de part et d'autre des symboles '('  et ',' et  ')'
	 * 
	 * efface dans string tout caract�re analys� avec succ�s
	 * @return 
	 * 
	 * */
	public void Vecteur(StringBuffer string)
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





}
