package mesmaths.cinematique;

import mesmaths.geometrie.base.Vecteur;

public class Cinematique
{
/**
 * suppose qu'un objet mobile subit un mouvement rectiligne uniforme (c'�-d un d�placement en ligne droite � vitesse constante)
 * 
 * le mouvement de l'objet est � tout instant d�fini par le vecteur position et le vecteur vitesse.
 * 
 *  DONNEES : position et vitesse � l'instant t, deltaT (deltaT est le d�lai entre 2 mises � jour, deltaT est suppos� petit) 
 * 
 * RESULTATS : position � l'instant t + deltaT
 * 
 * vitesse est laiss� inchang� par la m�thode
 * 
 * 
 * Le calcul peut �tre not� comme suit :
 * 
 * p(t+deltaT) = p(t) + deltaT * v(t)
 * v(t+deltaT) = v(t)
 * 
 * */
private static void mouvementRectiligneUniforme(Vecteur position, Vecteur vitesse, double deltaT)
{
position.ajoute(vitesse.produit(deltaT));   
}

/**
 * suppose qu'un objet mobile subit un mouvement uniform�ment acc�l�r� entre t et t+deltaT (c'�-d un d�placement comme la chute libre)
 * 
 * le mouvement de l'objet est � tout instant d�fini par le vecteur position, le vecteur vitesse et le vecteur acc�l�ration
 * 
 *  DONNEES : position, vitesse et acc�l�ration � l'instant t, deltaT (deltaT est le d�lai entre 2 mises � jour, deltaT est suppos� petit) 
 * 
 * RESULTATS : position et  vitesse  � l'instant t + deltaT. 
 * 
 * acc�l�ration reste inchang� par la m�thode. Il faudra le recalculer  l'instant t+deltaT avec une autre m�thode 
 * 
 * 
 * * Les calculs peuvent �tre not�s comme suit :
 * 
 * p(t+deltaT) = p(t) + deltaT * v(t) + (1/2) * deltaT^2 * a(t)
 * v(t+deltaT) = v(t) + deltaT * a(t)
 * a(t + deltaT) = a(t)
 * 
 * @param position : vecteur position du mobile � l'instant t. ce param�tre est modifi� par la m�thode : en sortie il repr�sente la position � l'instant t+deltaT 
 * @param vitesse : vecteur vitesse du mobile � l'instant t. ce param�tre est modifi� par la m�thode : en sortie il repr�sente la vitesse � l'instant t+deltaT
 * @param acc�l�ration : vecteur acc�l�ration du mobile � l'instant t
 * @param deltaT : une dur�e tr�s courte
 * 
 * */
public static void mouvementUniformementAccelere(Vecteur position, Vecteur vitesse, Vecteur acceleration, double deltaT)
{
mouvementRectiligneUniforme( position, vitesse, deltaT);
position.ajoute(acceleration.produit(0.5*deltaT*deltaT));
mouvementRectiligneUniforme(vitesse, acceleration, deltaT);
//acc�l�ration.set(Vecteur.VECTEURNUL);
}
}
