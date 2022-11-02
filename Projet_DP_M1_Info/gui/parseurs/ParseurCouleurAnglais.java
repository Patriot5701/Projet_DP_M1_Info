package parseurs;

import java.awt.Color;

public class ParseurCouleurAnglais extends GParseurCOR<Color, String>
{
	static String [] tS = {    "black",    "blue",    "red",    "green",    "yellow",     "cyan"};
	static Color []  tC = {Color.black,Color.blue,Color.red,Color.green,Color.yellow, Color.cyan};

	public ParseurCouleurAnglais(GParseurCOR<Color, String> suivant) { super(suivant); }

	@Override
	protected Color parse1(String sCouleur)
	{

		String sCouleurCherchee = sCouleur.trim();

		int p = sCouleurCherchee.indexOf(' ');
		sCouleurCherchee = sCouleurCherchee.substring(p+1);

		return tC[Operations.index(sCouleurCherchee, tS)];
	}

}

/*String [] tS = {    "black",    "blue",    "red",    "green",    "yellow",     "cyan"};
Color []  tC = {Color.black,Color.blue,Color.red,Color.green,Color.yellow, Color.cyan};

String sCouleurCherch�e = sCouleur.trim();

int p = sCouleurCherch�e.indexOf(' ');
sCouleurCherch�e = sCouleurCherch�e.substring(p+1);
int i;

for (i = 0; i < tS.length; ++i)
    if (tS[i].equalsIgnoreCase(sCouleurCherch�e)) return tC[i];

return null;*/