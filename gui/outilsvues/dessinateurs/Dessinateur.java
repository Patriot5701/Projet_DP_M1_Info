package outilsvues.dessinateurs;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Op�rations �l�mentaires de dessin
 * 
 * 1) La classe DessinateurSimple qui en d�rive contient les op�rations basiques de dessin
 * 
 * 2) La classe DecorateurDessinateur (DP D�corateur) qui en d�rive g�re l'�paisseur du trait et la couleur et les autres propri�t�s
 * 
 * Mise en oeuvre du DP Decorator
 * 
 * 
 * */
public interface Dessinateur
{
public void dessineSegment( Point debut, Point fin);

public void dessineCercle(  Point centre, int  rayon);

public void dessineDisque( Point centre, int rayon);

public void dessineMessage(String message, Point position);

public Graphics2D getGraphics2D();

}
