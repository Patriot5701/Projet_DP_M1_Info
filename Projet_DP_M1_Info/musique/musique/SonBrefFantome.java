package musique;

/**
 * classe "fant�me" instanci�e lorsque la cr�ation de l'objet son �choue. 
 * Cela sert � rendre l'algo de diffusion homog�ne et � �viter que l'application ne soit bloqu�e dans le cas o� le son n'est pas disponible
 * 
 * */
public class SonBrefFantome implements SonBref
{

  

    @Override
    public String getNom()
    {
    return "le son bref n'a pas pu �tre cr��";
    }

    @Override
    public void joue(double volume, double balance)
    {
    // TODO Auto-generated method stub
    
    }

    /*@Override
    public void joue()
    {
    // TODO Auto-generated method stub
    
    }*/

    
    public SonBref clone() 
    {
    return new SonBrefFantome();
    }

    @Override
    public void joue(double volume, double balance, double coeffPitch)
    {
    // TODO Auto-generated method stub
    
    }



}
