package musique;

import java.util.Arrays;
import java.util.Vector;

/**
 * @author Dom
 *
 * succession de sons brefs  destin�s � �tre diffus�s de fa�on continue en boucle. cf classe SonLongJavax d�riv�e.
 * 
 * On peut param�trer un volume, une balance et un coeffPitch diff�rents pour chaque son bref du tableau
 * 
 * Sur ma machine, le pitch n'est pas disponible
 * 
 */
public class SonLong
{
public SonBref sons[];

public SonLong(SonBref[] sons)
{
super();
this.sons = sons;
}

/**
 * @param i : indique l'�l�ment du tableau � diffuser. on joue l'�l�ment n� i % sons.length 
 * @param volume : tel que 0 <= volume <= 1
 * @param balance : tel que -1 <= balance <= 1
 * @param coeffPitch : tel que 0 < coeffPitch. Pour coeffPitch == 1, le son jou� a la m�me hauteur que le son enregistr�
 * Pour coeffPitch > 1, le son jou� est plus aigu que le son enregistr�
 * Pour coeffPitch < 1, le son jou� est plus grave que le son enregistr�
 */
public void joue(int i, double volume, double balance, double coeffPitch)
{
int j = i%this.sons.length; 
sons[j].joue(volume,balance,coeffPitch);
}

@Override
public String toString()
{
return "SonLong [sons=" + Arrays.toString(this.sons) + "]";
}

public String getNom() { return sons[0].getNom(); }

public SonLong clone() 
{
SonBref t[] = new SonBref[this.sons.length];

int i;
for ( i = 0; i < sons.length; ++i) t[i] = sons[i].clone();

return new SonLong(t);
}

public static SonLong [] toTableau(Vector<SonLong> v)
{
SonLong [] t = new SonLong [v.size()];

int i;
for ( i = 0; i < t.length; ++i) { t[i] = v.elementAt(i); } 

return t;
}
}