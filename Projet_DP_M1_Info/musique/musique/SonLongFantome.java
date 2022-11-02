package musique;

/**
 * classe "fant�me" instanci�e lorsque la cr�ation de l'objet son �choue. 
 * Cela sert � rendre l'algo de diffusion homog�ne et � �viter que l'application ne soit bloqu�e dans le cas o� le son n'est pas disponible
 * 
 * */
public class SonLongFantome extends SonLong
{

public SonLongFantome()
{
super(new SonBref[1]);
//this.sons = new SonBref[1];
this.sons[0] = new SonBrefFantome();
}



}
