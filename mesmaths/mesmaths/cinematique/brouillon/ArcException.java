package mesmaths.cinematique.brouillon;

/**
 * 
 * Lancee pour indiquer que pour un arc, une notion n'est pas d�finie.
 * 
 * Par exemple le projete orthogonal d'un point quelconque sur un arc n'est pas toujours defini.
 * */
public class ArcException extends RuntimeException
{

	public ArcException()
	{
		super();
	}

	public ArcException(String arg0)
	{
		super(arg0);
	}

}
