package parseurs;

import general.OutilsParseurString;

import java.awt.Color;

import corgenerique.GParseurCOR;;

public class ParseurCouleurRGB extends GParseurCOR<Color, String>
{

public ParseurCouleurRGB(GParseurCOR<Color, String> suivant) { super(suivant); }

@Override
protected Color parse1(String sCouleur)
{
int [] rgb = parseCouleur(sCouleur);

return new Color(rgb[0], rgb[1], rgb[2]);
}


/**
 * extrait les valeurs enti�res � partir de s suppos�e respecter le format suivant :
 * "a = 203, r=210, g  =105, b=  30"
 * chaque paire nom = valeur est s�par�e de la suivante par ','
 * dans une paire, la partie "nom =" est facultative
 * il y a un nombre qque d'espaces comme s�parateur
 * la partie "valeur" est forc�ment un nombre entier
 *  
 * 
 * */
public static int [] parseCouleur(String s)
{char s�parateur='=';

String t[] = s.split(",");
int composanteCouleurs[] = new int[t.length];
int i;
for (i = 0; i < t.length; ++i)
    composanteCouleurs[i] = Integer.parseInt(OutilsParseurString.extraitValeur(t[i],s�parateur));
return composanteCouleurs;
}
}
