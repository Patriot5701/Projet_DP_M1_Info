package musique;


/**
 * repr�sente un son bref (quelques secondes, moins d'une minute)
 * 
 * on peut faire varier le volume, la balance et le pitch lorsqu'on diffuse le son
 * 
 *  Sur ma machine, le pitch n'est pas disponible
 * 
 * */
public interface SonBref 
{

/**
 * @param volume : tel que 0.0 <= volume <= 1.0
 * @param balance : tel que -1.0 <= balance <= 1.0. -1.0 <---> tout sur l'enceinte gauche, 1.0 <---> tout sur l'enceinte droite
 * 
 * joue le son dans un thread s�par� 
 */
public abstract void joue(double volume, double balance);

/**
 * @param volume : tel que 0.0 <= volume <= 1.0
 * @param balance : tel que -1.0 <= balance <= 1.0. -1.0 <---> tout sur l'enceinte gauche, 1.0 <---> tout sur l'enceinte droite
 * @param coeffPitch : ratio hauteur du son diffus�/hauteur du son enregistr�. par d�faut peut �tre mis � 1.0
 * joue le son dans un thread s�par� 
 */
public abstract void joue(double volume, double balance, double coeffPitch);

/**
 * joue le son dans un thread s�par� avec volume = 1.0 et balance = 0.0
 */
//public void joue() {this.joue(1.0,0.0); /* le volume est maximum et la balance est au milieu */ }

public abstract SonBref clone();

/** @return le nom de ce son */
public abstract String getNom();
}