package javax;

import javax.sound.sampled.FloatControl;


/**
 * classe Bidon qui est innstanci�e lorsque le contr�le n'est pas disponible
 * 
 * sert uniquement � simplifier l'algo g�n�ral dans les cas particuliers 
 * */
public class FloatControlFantome extends FloatControl
{
	public FloatControlFantome()
	{
		super(null, Float.NaN, Float.NaN, Float.NaN, -1, Float.NaN, "");
	}

	@Override
	public void setValue(float newValue)
	{
		/* on ne fait rien */
	}

}
