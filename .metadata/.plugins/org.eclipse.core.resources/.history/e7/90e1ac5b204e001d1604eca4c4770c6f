package mesmaths.cinematique.brouillon;


import java.io.IOException;

import mesmaths.MesMaths;
import mesmaths.cinematique.Collisions;
import mesmaths.geometrie.base.Geop;
import mesmaths.geometrie.base.Vecteur;


public class Collisions
{
public static  double COEFF_ELASTICITE_BILLE = 50;
public static  double COEFF_ELASTICITE_PAROI = 120;
static double EPSILON = 1.0E-6;
static final double EPSILON_CHOC_BILLE = 1.0E-3;  // en deça de cette valeur pour |u.(v1-v2)| le choc entre 2 billes est considéré statique
static final double EPSILON_CHOC_PAROI = 1.0E-3;  // en deça de cette valeur pour |J.v| le choc entre une bille et la paroi est considéré statique 

/**
 * gestion de la collision dynamique (avec rebond) ou statique  de la bille définie par (position, rayon, vitesse, accélération) avec le segment orienté [P0P1]
 * 
 * @return false si il n'y a pas de collision
 * 
 * @return true si il y a collision et dans ce cas modifie soit vitesse soit accélération
 * 
 * 
 * c-à-d que en entrée on considère que (position,vitesse, accélération) sont le vecteur position, le vecteur vitesse et le vecteur accélération de la bille immédiatement avant le choc
 * et en sortie (position,vitesse, accélération) sont le vecteur position, le vecteur vitesse et le vecteur accélération de la bille immédiatement après le choc
 * 
 * si le choc est dynamique, le vecteur vitesse est modifié par la collision (comme une boule de billard l'est par une bande)
 * si le choc est statique, le vecteur accélération est modifié (on considère que le choc est élastique et une force de rappel est appliquée à la bille)
 * 
 * Dans tous les cas, le vecteur position est laissé intact par la collision,
 * 
 * @param position : vecteur position de la bille immédiatement avant la collision avec le segment [P0P1]
 * @param rayon : rayon de la bille
 * @param vitesse : vecteur vitesse de la bille immédiatement avant la collision avec le segment [P0P1]
 * @param accélération : vecteur vitesse de la bille immédiatement avant la collision avec le segment [P0P1]
 * @param P0 : le début du segment
 * @param P1 : la fin du segment
 * 
 * */
static boolean actionReactionBilleSegmentAvecRebond2( Vecteur position, double rayon, Vecteur vitesse, Vecteur accélération, double masse, Vecteur P0, Vecteur P1)
{
Vecteur[] base = Geop.base( P0,  P1);
Vecteur I = base[0];
Vecteur J = base[1];

double d = position.difference(P0).produitScalaire(J);

if (d <= rayon) // il y a collision entre la bille (position,rayon) et le segment [P0P1]
   {                                                            ////System.err.println("intersection avec : "+ P0 +" et "+ P1 );
   double vJ = vitesse.produitScalaire(J);
   
   if (vJ < -Collisions.EPSILON_CHOC_PAROI) // la bille sort du contour, le choc est dynamique
      { 
      // d'abord calcul du vecteur vitesse réfléchi
      
      Vecteur vS = Vecteur.combinaisonLinéaire(1, vitesse, -2*vJ, J);
      vitesse.set(vS);
      
      
      // à présent calcul de la nouvelle position : qui n'est plus utilisée
      
      double  d1 = rayon + EPSILON;
      
      double deltaT = (d1 - d) / vitesse.produitScalaire(J);
      //position.ajoute(vitesse.produit(deltaT));
      
                                                          //System.err.println("choc rapide avec la paroi" );
      return true;
      }
   else
      if (vJ > Collisions.EPSILON_CHOC_PAROI)               // la bille revient : on n'a rien à faire
         {                                                  //System.err.println("une bille rentre");
         return false;
         }
      else                                             // choc sans vitesse
         {
         double e = rayon - d;                    // profondeur de la prénétration de la bille dans le mur
      
         //e = Math.log(1+e);
      
         double forceRappel = Collisions.COEFF_ELASTICITE_PAROI*e;
      
         Vecteur a = J.produit(forceRappel/masse);
                                                          //System.err.println("choc mou avec la paroi" );
         accélération.ajoute(a);
         return true;
         }
  
   } // il y a collision 

else                // il n'y a pas de collision entre la bille et le bord
   return false;
}                           // actionReactionBilleSegmentAvecRebond1

/**
 * 
 * ESSAI : pas au point
 * 
 * gestion de la collision statique de la bille définie par (position,rayon,vitesse) avec le segment orienté [P0P1]
 * 
 * @return false si il n'y a pas de collision
 * 
 * @return true si il y a collision et dans ce cas modifie (position,vitesse)
 * 
 * 
 * c-à-d que en entrée on considère que (position,vitesse) sont le vecteur position et le vecteur vitesse de la bille immédiatement avant le choc
 * et en sortie (position,vitesse) sont le vecteur position et le vecteur vitesse de la bille immédiatement après le choc
 * 
 * le vecteur vitesse est modifié par la collision (comme une boule de billard l'est par une bande)
 * le vecteur position est modifié : la bille "ressort" du bord. En effet, en entrée si on détecté une collision, c'était parce que la bille avait légèrement pénétré
 * la paroi, il fallait donc la ressortir suivant sa nouvelle trajectoire pour un meilleur réalisme
 * 
 * @param position : vecteur position de la bille immédiatement avant la collision avec le segment [P0P1]
 * @param rayon : rayon de la bille
 * @param vitesse : vecteur vitesse de la bille immédiatement avant la collision avec le segment [P0P1]
 * @param P0 : le début du segment
 * @param P1 : la fin du segment
 * 
 * */
static boolean actionReactionBilleSegmentStatique( Vecteur position, double rayon, Vecteur accélération, double masse, Vecteur P0, Vecteur P1)
{
Vecteur[] base = Geop.base( P0,  P1);
Vecteur I = base[0];
Vecteur J = base[1];

double d = position.difference(P0).produitScalaire(J);

if (d <= rayon) // il y a collision entre la bille (position,rayon) et le segment [P0P1]
   {
   double e = rayon - d;                    // profondeur de la prénétration de la bille dans le mur
   
   e = Math.log(1+e);
   
   double forceRappel = Collisions.COEFF_ELASTICITE_PAROI*e;
   
   Vecteur a = J.produit(forceRappel/masse);
                                           ////System.err.println("intersection avec : "+ P0 +" et "+ P1 );
   accélération.ajoute(a);
   return true;
   } // il y a collision 

else                // il n'y a pas de collision entre la bille et le bord
   return false;
}                           // actionReactionBilleSegmentAvecRebond



/**
 * gestion de la collision avec rebond de la bille définie par (position,rayon,vitesse) avec le segment orienté [P0P1]
 * 
 * @return false si il n'y a pas de collision
 * 
 * @return true si il y a collision et dans ce cas modifie (position,vitesse)
 * 
 * 
 * c-à-d que en entrée on considère que (position,vitesse) sont le vecteur position et le vecteur vitesse de la bille immédiatement avant le choc
 * et en sortie (position,vitesse) sont le vecteur position et le vecteur vitesse de la bille immédiatement après le choc
 * 
 * le vecteur vitesse est modifié par la collision (comme une boule de billard l'est par une bande)
 * le vecteur position est modifié : la bille "ressort" du bord. En effet, en entrée si on détecté une collision, c'était parce que la bille avait légèrement pénétré
 * la paroi, il fallait donc la ressortir suivant sa nouvelle trajectoire pour un meilleur réalisme
 * 
 * @param position : vecteur position de la bille immédiatement avant la collision avec le segment [P0P1]
 * @param rayon : rayon de la bille
 * @param vitesse : vecteur vitesse de la bille immédiatement avant la collision avec le segment [P0P1]
 * @param P0 : le début du segment
 * @param P1 : la fin du segment
 * 
 * */
static boolean collisionBilleSegmentAvecRebond( Vecteur position, double rayon, Vecteur vitesse, Vecteur P0, Vecteur P1)
{
Vecteur[] base = Geop.base( P0,  P1);
Vecteur I = base[0];
Vecteur J = base[1];

double d = position.difference(P0).produitScalaire(J);

if (d <= rayon) // il y a collision entre la bille (position,rayon) et le segment [P0P1]
   {                                                            ////System.err.println("intersection avec : "+ P0 +" et "+ P1 );
   
   
   vitesse.set( Geop.reflechi( vitesse, I, J));                    ////System.err.println("v réfléchi = " + vitesse);
   
   double  d1;
   
  
   d1 = rayon + EPSILON;
   
   if (vitesse.norme() < Collisions.EPSILON) // choc mou
      {                                                                         ////System.err.println("choc mou au bord");
      double t = d1 - d;
      position.ajoute(J.produit(t));
      }
   else                                     // choc élastique
      {
      double deltaT = (d1 - d) / vitesse.produitScalaire(J);
      position.ajoute(vitesse.produit(deltaT));
      }
   return true;
   } // il y a collision 

else                // il n'y a pas de collision entre la bille et le bord
   return false;
}                           // collisionBilleSegmentAvecRebond






static boolean collisionBilleSegmentAvecRebond1( Vecteur position, double rayon, Vecteur vitesse, Vecteur P0, Vecteur P1)
{
double [] t = Geop.intersectionSegmentCercle( P0, P1, position, rayon);

if (t.length == 2)
   {                                                            ////System.err.println("intersection avec : "+ P0 +" et "+ P1 );
   Vecteur[] base = Geop.base( P0,  P1);
   Vecteur I = base[0];
   Vecteur J = base[1];
   
   vitesse.set( Geop.reflechi( vitesse, I, J));                    ////System.err.println("v réfléchi = " + vitesse);
   
   double d, d1;
   
   d = position.difference(P0).produitScalaire(J);
   d1 = rayon + EPSILON;
   
   
   
   double deltaT = (d1-d)/vitesse.produitScalaire(J);
   
   position.ajoute(vitesse.produit(deltaT));
   
   return true;
   } 

else                // il n'y a pas de collision entre la bille et le bord
   return false;
}

/**
 * gestion de la collision avec rebond de la bille définie par (position,rayon,vitesse) avec un contour rectangulaire de l'écran.
 * 
 *  Ce rectangle est défini par (abscisseCoinHautGauche, OrdonnéeCoinHautGauche,largeur,hauteur)
 * 
 * @return false si il n'y a pas de collision
 * 
 * @return true si il y a collision et dans ce cas modifie (position,vitesse)
 * 
 * c-à-d que en entrée on considère que (position,vitesse) sont le vecteur position et le vecteur vitesse de la bille immédiatement avant le choc
 * et en sortie (position,vitesse) sont le vecteur position et le vecteur vitesse de la bille immédiatement après le choc
 * 
 * le vecteur vitesse est modifié par la collision (comme une boule de billard l'est par une bande)
 * le vecteur position est modifié : la bille "ressort" du bord. En effet, en entrée si on détecté une collision, c'était parce que la bille avait légèrement pénétré
 * la paroi, il fallait donc la ressortir suivant sa nouvelle trajectoire pour un meilleur réalisme
 * 
 * @param position : vecteur position de la bille immédiatement avant la collision avec le contour
 * @param rayon : rayon de la bille
 * @param vitesse : vecteur vitesse de la bille immédiatement avant la collision avec le contour
 * @param abscisseCoinHautGauche : abscisse minimale du contour rectangulaire
 * @param ordonnéeCoinHautGauche : ordonnée minimale du contour rectangulaire
 * @param largeur : largeur du contour rectangulaire
 * @param hauteur : hauteur du contour rectangulaire
 * 
 * 
 * */
public static boolean collisionBilleContourAvecRebond( Vecteur position, double rayon, Vecteur vitesse, 
        double abscisseCoinHautGauche, double ordonnéeCoinHautGauche, double largeur, double hauteur)
{
Vecteur min = new Vecteur(abscisseCoinHautGauche,ordonnéeCoinHautGauche);
Vecteur diago = new Vecteur(largeur, hauteur);
Vecteur max = min.somme(diago); 


Vecteur coins[] = new Vecteur[5];

coins[0] = min;         // le coin haut gauche du rectangle défini par le composant
coins[1] = new Vecteur(max.x,min.y);
coins[2] = max;
coins[3] = new Vecteur(min.x, max.y);
coins[4] = coins[0];                    // pour refermer le contour !

int i;
for ( i = 1; i < coins.length; ++i)
    if (Collisions.collisionBilleSegmentAvecRebond(position, rayon, vitesse, coins[i-1], coins[i])) 
       return true;

return false;
}

/**
 * gestion de la collision dynamique (avec rebond) ou statique  de la bille définie par (position,rayon,vitesse,accélération) avec un contour rectangulaire de l'écran.
 * 
 *  Ce rectangle est défini par (abscisseCoinHautGauche, OrdonnéeCoinHautGauche,largeur,hauteur)
 * 
 * @return false si il n'y a pas de collision
 * 
 * @return true si il y a collision et dans ce cas modifie vitesse ou accélération
 * 
 * c-à-d que en entrée on considère que (position,vitesse,accélération) sont le vecteur position, le vecteur vitesse et le vecteur accélération de la bille immédiatement avant le choc
 * et en sortie (position,vitesse,accélération) sont le vecteur position, le vecteur vitesse et le vecteur accélération de la bille immédiatement après le choc
 * 
 * si la collision est dynamique, le vecteur vitesse est modifié par la collision (comme une boule de billard l'est par une bande)
 * si la collision est statique, le vecteur accélération est modifié par la collision (une force de rappel est appliquée à la bille)
 * 
 * @param position : vecteur position de la bille immédiatement avant la collision avec le contour
 * @param rayon : rayon de la bille
 * @param vitesse : vecteur vitesse de la bille immédiatement avant la collision avec le contour
 * @param accélération : vecteur accélération de la bille immédiatement avant la collision avec le contour
 * @param abscisseCoinHautGauche : abscisse minimale du contour rectangulaire
 * @param ordonnéeCoinHautGauche : ordonnée minimale du contour rectangulaire
 * @param largeur : largeur du contour rectangulaire
 * @param hauteur : hauteur du contour rectangulaire
 * 
 * 
 * */
public static boolean actionReactionBilleContourAvecRebond1( Vecteur position, double rayon, Vecteur vitesse, Vecteur accélération, double masse, 
        double abscisseCoinHautGauche, double ordonnéeCoinHautGauche, double largeur, double hauteur)
{
Vecteur min = new Vecteur(abscisseCoinHautGauche,ordonnéeCoinHautGauche);
Vecteur diago = new Vecteur(largeur, hauteur);
Vecteur max = min.somme(diago); 


Vecteur coins[] = new Vecteur[5];

coins[0] = min;         // le coin haut gauche du rectangle défini par le composant
coins[1] = new Vecteur(max.x,min.y);
coins[2] = max;
coins[3] = new Vecteur(min.x, max.y);
coins[4] = coins[0];                    // pour refermer le contour !

int i;

boolean collision;

for ( i = 1, collision = false; i < coins.length; ++i)
    if (Collisions.actionReactionBilleSegmentAvecRebond(position, rayon, vitesse, accélération, masse, coins[i-1], coins[i])) 
       collision = true;

return collision;
}                   // actionReactionBilleContourAvecRebond1

/**
 * gestion de la collision avec traversement de la paroi (et réapparition sur le coté opposé) de la bille définie par (position) 
 *  avec un contour rectangulaire de l'écran. 
 *  Ce rectangle est défini par (abscisseCoinHautGauche, OrdonnéeCoinHautGauche,largeur,hauteur)
 *  si il n'y a pas collision, le vecteur position n'est pas modifié
 * si il y a collision, le vecteur position est modifié : la bille "ressort" sur le bord opposé au bord qu'elle pénètre.
 * @param position : vecteur position de la bille
 * 
 * */
public static void collisionBilleContourPasseMuraille(Vecteur position, 
        double abscisseCoinHautGauche, double ordonnéeCoinHautGauche, double largeur, double hauteur)
{
position.x = Collisions.traverseBord(position.x,abscisseCoinHautGauche, largeur);
position.y = Collisions.traverseBord(position.y,ordonnéeCoinHautGauche, hauteur);
}

/*
 * utile à la méthode collisionBilleContourPasseMuraille
 * */
private static double traverseBord(double x, double xMin, double largeur)
{
double xMax = xMin + largeur;

if (x > xMax)
   {
   double d = (x-xMin)/largeur;
   double d1 = Math.floor(d);
   int q = (int)d1;
   return x - q*largeur;
   }
   
else
   if (x < xMin)
      {
      double d = (xMax-x)/largeur;
      double d1 = Math.floor(d);
      int q = (int)d1;
      return x + q*largeur;
      }
   else
      return x;
}

/**
 * gestion de la collision avec arrêt suivant la direction horizontale  de la bille définie par (position,rayon,vitesse) avec un contour rectangulaire de l'écran.
 * 
 *  Ce rectangle est défini partiellement par (abscisseCoinHautGauche,largeur)
 * 
 * si il n'y a pas de collision avec un bord vertical, position et vitesse sont inchangés par la méthode
 * 
 *  si il y a collision avec un bord vertical
 * alors vitesse est modifié et  position reste intact
 * 
 * Dès qu'il y a collision avec un bord vertical, la composante horizontale du vecteur vitesse est annulée, de sorte que la bille continue à glisser le long de
 * la bande qui l'a arrêtée
 * 
 */
public static void collisionBilleContourAvecArretHorizontal(Vecteur position, double rayon, Vecteur vitesse,
        double abscisseCoinHautGauche, double largeur)
{
//vitesse.x = Collisions.arretSurBord(vitesse.x, position.x, rayon, abscisseCoinHautGauche, largeur);
double t[] = Collisions.arretSurBord(vitesse.x, position.x, rayon, abscisseCoinHautGauche, largeur);
vitesse.x = t[0];
position.x = t[1];
}

/**
 * gestion de la collision avec arrêt suivant la direction verticale  de la bille définie par (position,rayon,vitesse) avec un contour rectangulaire de l'écran.
 * 
 *  Ce rectangle est défini partiellement par (ordonnéeCoinHautgauche,hauteur)
 * 
 * si il n'y a pas de collision avec un bord horizontal, position et vitesse sont inchangés par la méthode
 * 
 *  si il y a collision avec un bord horizontal
 * alors vitesse est modifié et  position reste intact
 * 
 * Dès qu'il y a collision avec un bord horizontal, la composante verticale du vecteur vitesse est annulée, de sorte que la bille continue à glisser le long de
 * la bande qui l'a arrêtée
 * 
 */
public static void collisionBilleContourAvecArretVertical(Vecteur position, double rayon, Vecteur vitesse,
        double ordonnéeCoinHautGauche, double hauteur)
{
//vitesse.y = Collisions.arretSurBord(vitesse.y, position.y, rayon, ordonnéeCoinHautGauche, hauteur);
double t[] = Collisions.arretSurBord(vitesse.y, position.y, rayon, ordonnéeCoinHautGauche, hauteur);
vitesse.y = t[0];
position.y = t[1];
}

/*
 * utile à collisionBilleContourAvecArretHorizontal et à collisionBilleContourAvecArretVertical
 * */
private static double arretSurBord1(double v, double x, double rayon, double xMin, double largeur)
{
double xMax = xMin+largeur;

if (x+rayon > xMax   || x-rayon < xMin) 
    return 0;
else
    return v;
}

/*
 * utile à collisionBilleContourAvecArretHorizontal et à collisionBilleContourAvecArretVertical
 * */
private static double [] arretSurBord(double v, double x, double rayon, double xMin, double largeur)
{
double [] t = new double[2]; //  nouvelle composante_vitesse dans t[0] et nouvelle composante_position dans t[1]

double xMax = xMin+largeur;


if (x+rayon > xMax)
    {
    t[0] = 0;
    t[1] = xMax-rayon-Collisions.EPSILON;
    }
else
    if (x-rayon < xMin)
        {
        t[0] = 0;
        t[1] = xMin + Collisions.EPSILON + rayon;
        } 
    else
        {
        t[0] = v; t[1] = x; 
        }

return t;
}





/**
 * gère la collision des 2 billes définies respectivement par 
 * ( position1, rayon1, vitesse1, masse1 ) et par ( position2, rayon2, vitesse2,  masse2)
 * 
 * si il n'y a pas de collision
 * renvoie false et ne modifie rien
 * si il y a collision renvoie true et 
 * modifie position1, vitesse1, position2, vitesse2.
 * Les nouvelles positions et nouveaux vecteurs vitesses sont calculés pour représenter l'état des billes immédiatement après le choc  
 * 
 * 
 * 
 * */
private static boolean CollisionBilleBille1( Vecteur position1, double rayon1, Vecteur vitesse1, double masse1, 
                                           Vecteur position2, double rayon2, Vecteur vitesse2, double masse2)   
// modifie b1 et b2
// données : b1 et b2 avant le choc
// résultats : b1 et b2 après le choc
{
Vecteur G1G2;
double nG1G2;
G1G2 = Vecteur.difference(position2, position1);
nG1G2 = G1G2.norme();
Vecteur u = G1G2.produit(1 / nG1G2);
Vecteur v1 = vitesse1;
Vecteur v2 = vitesse2;

Vecteur v1_v2 = Vecteur.difference(v1, v2);
double a = Vecteur.produitScalaire(u, v1_v2);

double r = rayon1+rayon2;

if ( ! (nG1G2 < r && a > Collisions.EPSILON) )     // il n'y a pas de collision entre les 2 billes
    return false;
else                // il y a collision entre les 2 billes
    {
    
    //d'abord calcul des vitesses après le choc
    
    Vecteur  vs1, vs2;
    double t, m1, m2;
    
    m1 = masse1;
    m2 = masse2;
    
    
    t = 2 * (m1 / (m1 + m2)) * a;
    vs1 = Vecteur.combinaisonLinéaire(1, v1, -(m2 / m1) * t, u);
    vs2 = Vecteur.combinaisonLinéaire(1, v2, t, u); 
    
    vitesse1.set(vs1);                  // vecteur vitesse de la bille 1 après le choc
    vitesse2.set(vs2);                  // vecteur vitesse de la bille 2 après le choc
    
    // à présent calcul des positions après le choc
    
    double l = r + Collisions.EPSILON;
    Vecteur vs2_vs1 = vs2.difference(vs1);
    
    double a0, b0, c0;
    
    a0 = vs2_vs1.normeCarrée();
    b0 = 2*Vecteur.produitScalaire(vs2_vs1, G1G2);
    c0 = nG1G2*nG1G2 - l*l;
    double deltaT[] = MesMaths.résoudre(a0, b0, c0);
    
    double dT = deltaT[1];
    
    position1.ajoute(vs1.produit(dT));
    position2.ajoute(vs2.produit(dT));
    
    return true;
    }
} // collisionBilleBille1

public static boolean CollisionBilleBille2( Vecteur position1, double rayon1, Vecteur vitesse1, double masse1, 
        Vecteur position2, double rayon2, Vecteur vitesse2, double masse2)   
//modifie b1 et b2
//données : b1 et b2 avant le choc
//résultats : b1 et b2 après le choc
{
Vecteur G1G2;
double nG1G2;
G1G2 = Vecteur.difference(position2, position1);
nG1G2 = G1G2.norme();

double r = rayon1+rayon2;

if ( nG1G2 >= r  || nG1G2 < Collisions.EPSILON)     // il n'y a pas de collision entre les 2 billes
   return false;

else
   {
   Vecteur u = G1G2.produit(1 / nG1G2);
   Vecteur v1 = vitesse1;
   Vecteur v2 = vitesse2;

   Vecteur v1_v2 = Vecteur.difference(v1, v2);
   double a = Vecteur.produitScalaire(u, v1_v2);
   
   double m1 = masse1;
   double m2 = masse2;
   double masseTotale = m1+m2;
   
   double l = r + Collisions.EPSILON;

   if ( a < Collisions.EPSILON ) // il y a un choc mou entre les 2 billes : on va simplement séparer les 2 billes sans modifier les vitesses
      {                                 // on déplace les billes sur (G1G2) proportionnellement aux inverses de leurs masses
      double k = m2/masseTotale;
      Vecteur M =  Vecteur.combinaisonLinéaire(1, position1, k, G1G2);  // M = bar(m1,G1,m2,G2)
      double lk = l*k;
      Vecteur G1s = Vecteur.combinaisonLinéaire(1, M, -lk, u);
      Vecteur G2s = Vecteur.combinaisonLinéaire(1, M, m1*lk/m2, u);
      
      position1.set(G1s);
      position2.set(G2s);
                                          ////System.err.println("choc mou");
      return true;
      }
     else                // il y a choc élastique entre les 2 billes
        {
        //d'abord calcul des vitesses après le choc
        
        Vecteur  vs1, vs2;
        double t;
        
        t = 2 * (m1 / masseTotale) * a;
        vs1 = Vecteur.combinaisonLinéaire(1, v1, -(m2 / m1) * t, u);
        vs2 = Vecteur.combinaisonLinéaire(1, v2, t, u); 
        
        vitesse1.set(vs1);                  // vecteur vitesse de la bille 1 après le choc
        vitesse2.set(vs2);                  // vecteur vitesse de la bille 2 après le choc
        
        // à présent calcul des positions après le choc
        
        
        Vecteur vs2_vs1 = vs2.difference(vs1);
        
        double a0, b0, c0;
        
        a0 = vs2_vs1.normeCarrée();
        b0 = 2*Vecteur.produitScalaire(vs2_vs1, G1G2);
        c0 = nG1G2*nG1G2 - l*l;
        double deltaT[] = MesMaths.résoudre(a0, b0, c0);
        
        //if (deltaT.length > 0)
            {
            double dT = deltaT[1];
            position1.ajoute(vs1.produit(dT));
            position2.ajoute(vs2.produit(dT));
            }
            
        return true;
        }
   }
} // collisionBilleBille2

public static boolean CollisionBilleBille( Vecteur position1, double rayon1, Vecteur vitesse1, double masse1, 
        Vecteur position2, double rayon2, Vecteur vitesse2, double masse2)   
//modifie b1 et b2
//données : b1 et b2 avant le choc
//résultats : b1 et b2 après le choc
{
Vecteur G1G2;
double nG1G2, nG1G2_2;
G1G2 = Vecteur.difference(position2, position1);
nG1G2_2 = G1G2.normeCarrée();
nG1G2 = Math.sqrt(nG1G2_2);
//nG1G2 = G1G2.norme();

double r = rayon1+rayon2;

if ( nG1G2 >= r  )     // il n'y a pas de collision entre les 2 billes
   return false;

else
   {
   Vecteur u = G1G2.produit(1 / nG1G2);
   Vecteur v1 = vitesse1;
   Vecteur v2 = vitesse2;

   Vecteur v1_v2 = Vecteur.difference(v1, v2);
   double a = Vecteur.produitScalaire(u, v1_v2);
   
   double m1 = masse1;
   double m2 = masse2;
   double masseTotale = m1+m2;
   
   double l = r + Collisions.EPSILON;

   if ( a < 0 ) // il y a un choc mou entre les 2 billes : on va simplement séparer les 2 billes sans modifier les vitesses
      {                                 // on déplace les billes sur (G1G2) proportionnellement aux inverses de leurs masses
      double k = m2/masseTotale;
      Vecteur M =  Vecteur.combinaisonLinéaire(1, position1, k, G1G2);  // M = bar(m1,G1,m2,G2)
      double lk = l*k;
      Vecteur G1s = Vecteur.combinaisonLinéaire(1, M, -lk, u);
      Vecteur G2s = Vecteur.combinaisonLinéaire(1, M, m1*lk/m2, u);
      
      position1.set(G1s);
      position2.set(G2s);
                                          //System.err.println("choc mou");
      return true;
      }
     else                // il y a choc élastique entre les 2 billes
        {
        //d'abord calcul des vitesses après le choc
        
        Vecteur  vs1, vs2;
        double t;
        
        t = 2 * (m1 / masseTotale) * a;
        vs1 = Vecteur.combinaisonLinéaire(1, v1, -(m2 / m1) * t, u);
        vs2 = Vecteur.combinaisonLinéaire(1, v2, t, u); 
        
        vitesse1.set(vs1);                  // vecteur vitesse de la bille 1 après le choc
        vitesse2.set(vs2);                  // vecteur vitesse de la bille 2 après le choc
        
        // à présent calcul des positions après le choc
        
        double v1_v2_2 = v1_v2.normeCarrée();
        
        double delta1 = a*a - v1_v2_2*(nG1G2_2 - l*l );
        
        
        double dT = ( -a + Math.sqrt(delta1) ) / v1_v2_2;
        
        
        position1.ajoute(vs1.produit(dT));
        position2.ajoute(vs2.produit(dT));
          
            
        return true;
        }
   }
} // collisionBilleBille


/**
 * gère la collision dynamique ou statique des 2 billes définies respectivement par 
 * ( position1, rayon1, vitesse1, accélération1, masse1 ) et par ( position2, rayon2, vitesse2, accélération1, masse2)
 * 
 * si il n'y a pas de collision
 * renvoie false et ne modifie rien
 * 
 * si il y a collision dynamique :
 * renvoie true et modifie  vitesse1 et vitesse2.
 * 
 * si il y a collision statique :
 * renvoie true et modifie  accélération1 et accélération2. (une force de rappel élastique est appliquée aux deux billes)
 * 
 * Les nouvelles accélérations ou les nouveaux vecteurs vitesses sont calculés pour représenter l'état des billes immédiatement après le choc  
 * 
 * 
 * 
 * */

public static boolean actionReactionBilleBille1( Vecteur position1, double rayon1, Vecteur vitesse1, Vecteur accélération1, double masse1, 
        Vecteur position2, double rayon2, Vecteur vitesse2, Vecteur accélération2, double masse2)   
//modifie b1 et b2
//données : b1 et b2 avant le choc
//résultats : b1 et b2 après le choc
{
Vecteur G1G2;
double nG1G2, nG1G2_2;
G1G2 = Vecteur.difference(position2, position1);
nG1G2_2 = G1G2.normeCarrée();
nG1G2 = Math.sqrt(nG1G2_2);
//nG1G2 = G1G2.norme();

double r = rayon1+rayon2;

if ( nG1G2 >= r  || nG1G2 < Collisions.EPSILON)     // il n'y a pas de collision entre les 2 billes
   return false;

else
   {
   Vecteur u = G1G2.produit(1 / nG1G2);
   Vecteur v1 = vitesse1;
   Vecteur v2 = vitesse2;

   Vecteur v1_v2 = Vecteur.difference(v1, v2);
   double a = Vecteur.produitScalaire(u, v1_v2);
   
   double m1 = masse1;
   double m2 = masse2;
   double masseTotale = m1+m2;
   
   double l = r + Collisions.EPSILON;

   if ( a < Collisions.EPSILON_CHOC_BILLE )
      {
            if (a > - Collisions.EPSILON_CHOC_BILLE)
                {                                           // choc sans vitesse

                double d = r - nG1G2;

                double forceRappel = Collisions.COEFF_ELASTICITE_BILLE * d;
                Vecteur a12, a21;
                a12 = u.produit(forceRappel / masse2);
                a21 = u.produit(-forceRappel / masse1);

                accélération1.ajoute(a21);
                accélération2.ajoute(a12);

                                                        //System.err.println("choc mou entre deux billes");
                return true;
                
                }
            else                                        // les 2 billes s'éloignent l'une de l'autre
                {                                       //System.err.println("fin de collision bille-bille");
                return false;
                }
        }
   else      /*  a >= 0 */          // il y a choc élastique entre les 2 billes
        {
        //d'abord calcul des vitesses après le choc
        
        Vecteur  vs1, vs2;
        double t;
        
        t = 2 * (m1 / masseTotale) * a;
        vs1 = Vecteur.combinaisonLinéaire(1, v1, -(m2 / m1) * t, u);
        vs2 = Vecteur.combinaisonLinéaire(1, v2, t, u); 
        
        vitesse1.set(vs1);                  // vecteur vitesse de la bille 1 après le choc
        vitesse2.set(vs2);                  // vecteur vitesse de la bille 2 après le choc
        
        // à présent calcul des positions après le choc qui n'est plus utilisée
        
        double v1_v2_2 = v1_v2.normeCarrée();
        
        double delta1 = a*a - v1_v2_2*(nG1G2_2 - l*l );
        
        
        double dT = ( -a + Math.sqrt(delta1) ) / v1_v2_2;
        
        
        //position1.ajoute(vs1.produit(dT));
        //position2.ajoute(vs2.produit(dT));
        
                                                //System.err.println("choc rapide entre deux billes");
            
        return true;
        }
   }
} // actionReactionBilleBille

public static boolean actionReactionStatiqueBilleBille( Vecteur position1, double rayon1, Vecteur accélération1, double masse1, 
        Vecteur position2, double rayon2, Vecteur accélération2, double masse2)   
{
Vecteur G1G2;
double nG1G2;
G1G2 = Vecteur.difference(position2, position1);
nG1G2 = G1G2.norme();

double r = rayon1+rayon2;

if ( nG1G2 >= r  )     // il n'y a pas de collision entre les 2 billes
   return false;

else
   {
   Vecteur u = G1G2.produit(1 / nG1G2);
   double d = r - nG1G2;
   
   double forceRappel = Collisions.COEFF_ELASTICITE_BILLE*d;
   Vecteur a12, a21;
   a12 = u.produit(forceRappel/masse2);
   a21 = u.produit(-forceRappel/masse1);
   
   accélération1.ajoute(a21);
   accélération2.ajoute(a12);
          
   return true;     
   }
} // actionReactionStatiqueBilleBille


/**
 * gestion de la collision dynamique (avec rebond) ou statique  de la bille définie par (position, rayon, vitesse, accélération) avec le segment orienté [P0P1]
 * 
 * @return l'intensité du choc. Si la valeur retournée est > 0 alors il y a choc, si la valeur retournée est <= 0 alors il n'y a pas de choc. 
 * Plus la valeur retournée (lorsqu'elle est > 0) est grande, plus le choc est violent 
 * 
 *  si la valeur retournée est <= 0, vitesse et accélération sont laissés intacts
 * 
 * si la valeur retournée est > 0 alors vitesse et accélération sont modifiés
 * 
 * 
 * c-à-d que en entrée on considère que (position,vitesse, accélération) sont le vecteur position, le vecteur vitesse et le vecteur accélération de la bille 
 * immédiatement avant le choc
 * et en sortie (position,vitesse, accélération) sont le vecteur position, le vecteur vitesse et le vecteur accélération de la bille immédiatement après le choc
 * 
 * si le choc est parfaitement dynamique, le vecteur vitesse est modifié par la collision (comme une boule de billard l'est par une bande)
 * si le choc est parfaitement statique, le vecteur accélération est modifié (on considère que le choc est élastique et une force de rappel est appliquée à la bille)
 * Dans toutes les situations intermédiaires, le vecteur vitesse et le vecteur accélération sont modifiés suivant une pondération qui cherche un compromis entre
 * situation dynamique et situation statique
 * 
 * Dans tous les cas, le vecteur position est laissé intact par la collision,
 * 
 * @param position : vecteur position de la bille immédiatement avant la collision avec le segment [P0P1]
 * @param rayon : rayon de la bille
 * @param vitesse : vecteur vitesse de la bille immédiatement avant la collision avec le segment [P0P1]
 * @param accélération : vecteur vitesse de la bille immédiatement avant la collision avec le segment [P0P1]
 * @param P0 : le début du segment
 * @param P1 : la fin du segment
 * 
 * */
static double actionReactionBilleSegmentAvecRebond( Vecteur position, double rayon, Vecteur vitesse, Vecteur accélération, double masse, Vecteur P0, Vecteur P1)
{
Vecteur[] base = Geop.base( P0,  P1);
Vecteur I = base[0];
Vecteur J = base[1];

double vJ = vitesse.produitScalaire(J);

if (vJ>=0) return -vJ; // la bille revient vers le bord depuis l'extérieur ou bien s'éloigne du bord depuis l'intérieur: il n'y a pas de collision

// à présent, on a forcément vJ < 0

double d = position.difference(P0).produitScalaire(J);  // d est la distance signée entre le segment et la bille

if (d >= rayon) return vJ; // il n'y a pas de collision

// à présent, on a forcément : vJ < 0 et d < rayon

double v = -vJ; // on a forcément v > 0

if (v >= EPSILON_CHOC_PAROI)            // le choc est dynamique
   {
   // d'abord calcul du vecteur vitesse réfléchi

   Vecteur deltaV = J.produit(-2*vJ);      // deltaV = vecteur vitesse réfléchi - vecteur vitesse incident
   Vecteur vS = vitesse.somme(deltaV);
   vitesse.set(vS);
                                                    //System.err.println("choc mou avec la paroi" );
   }

else                // le choc est mou
   {
   // calcul de la force de rappel

   double e = rayon - d;                    // profondeur de la prénétration de la bille dans le mur, on a e > 0

   double forceRappel = Collisions.COEFF_ELASTICITE_PAROI*e;

   Vecteur a = J.produit(forceRappel/masse);
   accélération.ajoute(a);
   }


//double alfa = 1; // v/(v+e); 
//alfa = Math.sqrt(alfa);
//double alfabar = e/(v+e);

//Vecteur vS = Vecteur.combinaisonLinéaire(1, vitesse, alfa, deltaV);

return v;
}                           // actionReactionBilleSegmentAvecRebond2


static double volumePartielSphère(double r, double e)
{
if (e <= 2*r)
    {
    double e2 = e*e;

    return  Math.PI*e2*(r-e/3);
    }
else
    return 4.0/3*Math.PI*r*r*r;

}

static boolean actionReactionBilleSegmentAvecRebond1( Vecteur position, double rayon, Vecteur vitesse, Vecteur accélération, double masse, 
        Vecteur P0, Vecteur P1)
{
Vecteur[] base = Geop.base( P0,  P1);
Vecteur I = base[0];
Vecteur J = base[1];

double d = position.difference(P0).produitScalaire(J);  // d est la distance signée entre le segment et la bille

if (d >= rayon) return false; // il n'y a pas de collision

//à présent, on a forcément : d < rayon

//calcul de la force de rappel

double e = rayon - d;                    // profondeur de la prénétration de la bille dans le mur, on a e > 0

double volumePénétrant = volumePartielSphère(rayon, e);        // volume de la bille qui s'est enfoncé dans la paroi

double alfa = 0.1;
double alfa2 = alfa*alfa;
double k = 4/(alfa2*(3-alfa));

double forceRappel = k*volumePénétrant;

Vecteur a = J.produit(forceRappel/masse);
accélération.ajoute(a);
                                            System.out.println("a = " + a +", volume = " + volumePénétrant +", k = " + k + ", e = " + e +", rayon = " + rayon +", d = " + d);
                                            System.out.println("position = " + position + ", P0 = " + P0 +", P1 = " + P1 + ", J = " + J); 
                                            try
                                                {
                                                System.in.read();
                                                }
                                            catch (IOException e1)
                                                {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                                }
double vJ = vitesse.produitScalaire(J);

return true ; // la bille revient vers le bord depuis l'extérieur ou bien s'éloigne du bord depuis l'intérieur: il n'y a pas de collision

// à présent, on a forcément vJ < 0





/*double v = -vJ; // on a forcément v > 0

if (v >= EPSILON_CHOC_PAROI)            // le choc est dynamique
   {
   // d'abord calcul du vecteur vitesse réfléchi

   Vecteur deltaV = J.produit(-2*vJ);      // deltaV = vecteur vitesse réfléchi - vecteur vitesse incident
   Vecteur vS = vitesse.somme(deltaV);
   vitesse.set(vS);
                                                    //System.err.println("choc mou avec la paroi" );
   }

else                // le choc est mou
   {
   
   }


//double alfa = 1; // v/(v+e); 
//alfa = Math.sqrt(alfa);
//double alfabar = e/(v+e);

//Vecteur vS = Vecteur.combinaisonLinéaire(1, vitesse, alfa, deltaV);

return v;
*/}                           // actionReactionBilleSegmentAvecRebond



/**
 * gestion de la collision dynamique (avec rebond) ou statique  de la bille définie par (position,rayon,vitesse,accélération) avec un contour rectangulaire de l'écran.
 * 
 *  Ce rectangle est défini par (abscisseCoinHautGauche, OrdonnéeCoinHautGauche,largeur,hauteur)
 * 
 * @return une valeur <= 0 si il n'y a pas de collision
 * 
 * @return une valeur > 0 si il y a collision et dans ce cas modifie vitesse et accélération. Dans ce cas aussi, la valeur retournée mesure l'intensité du choc
 * 
 * c-à-d que en entrée on considère que (position,vitesse,accélération) sont le vecteur position, le vecteur vitesse et le vecteur accélération de la bille immédiatement avant le choc
 * et en sortie (position,vitesse,accélération) sont le vecteur position, le vecteur vitesse et le vecteur accélération de la bille immédiatement après le choc
 * 
 * si le choc est parfaitement dynamique, le vecteur vitesse est modifié par la collision (comme une boule de billard l'est par une bande)
 * si le choc est parfaitement statique, le vecteur accélération est modifié (on considère que le choc est élastique et une force de rappel est appliquée à la bille)
 * Dans toutes les situations intermédiaires, le vecteur vitesse et le vecteur accélération sont modifiés suivant une pondération qui cherche un compromis entre
 * situation dynamique et situation statique
 * 
 * @param position : vecteur position de la bille immédiatement avant la collision avec le contour
 * @param rayon : rayon de la bille
 * @param vitesse : vecteur vitesse de la bille immédiatement avant la collision avec le contour
 * @param accélération : vecteur accélération de la bille immédiatement avant la collision avec le contour
 * @param abscisseCoinHautGauche : abscisse minimale du contour rectangulaire
 * @param ordonnéeCoinHautGauche : ordonnée minimale du contour rectangulaire
 * @param largeur : largeur du contour rectangulaire
 * @param hauteur : hauteur du contour rectangulaire
 * 
 * 
 * */
public static double actionReactionBilleContourAvecRebond( Vecteur position, double rayon, Vecteur vitesse, Vecteur accélération, double masse, 
        double abscisseCoinHautGauche, double ordonnéeCoinHautGauche, double largeur, double hauteur)
{
Vecteur min = new Vecteur(abscisseCoinHautGauche,ordonnéeCoinHautGauche);
Vecteur diago = new Vecteur(largeur, hauteur);
Vecteur max = min.somme(diago); 


Vecteur coins[] = new Vecteur[5];

coins[0] = min;         // le coin haut gauche du rectangle défini par le composant
coins[1] = new Vecteur(max.x,min.y);
coins[2] = max;
coins[3] = new Vecteur(min.x, max.y);
coins[4] = coins[0];                    // pour refermer le contour !

int i;

double intensitéChoc = -1;

for ( i = 1; i < coins.length; ++i)
    if (( intensitéChoc = Collisions.actionReactionBilleSegmentAvecRebond(position, rayon, vitesse, accélération, masse, coins[i-1], coins[i]))> 0 )
       return intensitéChoc;
    
return intensitéChoc;
}                   // actionReactionBilleContourAvecRebond


/**
 * gère la collision dynamique ou statique des 2 billes définies respectivement par 
 * ( position1, rayon1, vitesse1, accélération1, masse1 ) et par ( position2, rayon2, vitesse2, accélération1, masse2)
 * 
 * si il n'y a pas de collision
 * renvoie une valeur < = 0 et ne modifie rien
 * 
 * si il y a collision dynamique :
 * renvoie une valeur > 0 représentant l'intensité du choc  et modifie  vitesse1 et vitesse2.
 * 
 * si il y a collision statique :
 * renvoie ? et modifie  accélération1 et accélération2. (une force de rappel élastique est appliquée aux deux billes)
 * 
 * Les nouvelles accélérations ou les nouveaux vecteurs vitesses sont calculés pour représenter l'état des billes immédiatement après le choc  
 * 
 * 
 * 
 * */

public static double actionReactionBilleBille( Vecteur position1, double rayon1, Vecteur vitesse1, Vecteur accélération1, double masse1, 
        Vecteur position2, double rayon2, Vecteur vitesse2, Vecteur accélération2, double masse2)   
//modifie b1 et b2
//données : b1 et b2 avant le choc
//résultats : b1 et b2 après le choc
{
Vecteur G1G2;
double nG1G2, nG1G2_2;
G1G2 = Vecteur.difference(position2, position1);
nG1G2_2 = G1G2.normeCarrée();

double r = rayon1+rayon2;

double r2 = r*r;

if (nG1G2_2 >= r2) return -1;           // on a donc nG1G2 >= rayon1 + rayon2, il n'y a donc pas de choc

// à présent nG1G2_2 < r2

nG1G2 = Math.sqrt(nG1G2_2);

// on a donc nG1G2 < r

Vecteur u = G1G2.produit(1 / nG1G2);

Vecteur v1 = vitesse1;
Vecteur v2 = vitesse2;

Vecteur v1_v2 = Vecteur.difference(v1, v2);
double a = Vecteur.produitScalaire(u, v1_v2);


if (a <-EPSILON_CHOC_BILLE) return a;       // il n'y a pas de collision, les billes s'éloignent l'une de l'autre

// à présent, -EPSILON_CHOC_BILLE <= a 

if (a < EPSILON_CHOC_BILLE) // le choc est mou car a ~= 0
   {
   double e = r - nG1G2;       // e est la profondeur de la pénétration d'une bille dans l'autre

   double forceRappel = Collisions.COEFF_ELASTICITE_BILLE * e;

   Vecteur a12, a21;
   a12 = u.produit(forceRappel / masse2);
   a21 = u.produit(-forceRappel / masse1);

   accélération1.ajoute(a21);
   accélération2.ajoute(a12);
   
   return a;
   }

// à présent, a > EPSILON_CHOC_BILLE et nG1G2 < r, le choc est donc dynamique
 
double m1 = masse1;
double m2 = masse2;
double masseTotale = m1+m2;

double t;

t = 2 * (m1 / masseTotale) * a;

Vecteur  vs1, vs2;

vs1 = Vecteur.combinaisonLinéaire(1, v1, -(m2 / m1) * t, u);
vs2 = Vecteur.combinaisonLinéaire(1, v2, t, u); 

vitesse1.set(vs1);                  // vecteur vitesse de la bille 1 après le choc
vitesse2.set(vs2);                  // vecteur vitesse de la bille 2 après le choc

return a;


   
  /* double l = r + Collisions.EPSILON;

   if ( a < Collisions.EPSILON_CHOC_BILLE )
      {
            if (a > - Collisions.EPSILON_CHOC_BILLE)
                {                                           // choc sans vitesse

                double d = r - nG1G2;

                double forceRappel = Collisions.COEFF_ELASTICITE_BILLE * d;
                Vecteur a12, a21;
                a12 = u.produit(forceRappel / masse2);
                a21 = u.produit(-forceRappel / masse1);

                accélération1.ajoute(a21);
                accélération2.ajoute(a12);

                                                        //System.err.println("choc mou entre deux billes");
                return true;
                
                }
            else                                        // les 2 billes s'éloignent l'une de l'autre
                {                                       //System.err.println("fin de collision bille-bille");
                return false;
                }
        }
   else        a >= 0           // il y a choc élastique entre les 2 billes
        {
        //d'abord calcul des vitesses après le choc
        
        
        
        // à présent calcul des positions après le choc qui n'est plus utilisée
        
        double v1_v2_2 = v1_v2.normeCarrée();
        
        double delta1 = a*a - v1_v2_2*(nG1G2_2 - l*l );
        
        
        double dT = ( -a + Math.sqrt(delta1) ) / v1_v2_2;
        
        
        //position1.ajoute(vs1.produit(dT));
        //position2.ajoute(vs2.produit(dT));
        
                                                //System.err.println("choc rapide entre deux billes");
            
        return true;
        }
   }*/
} // actionReactionBilleBille2

public static double actionReactionBilleBille2( Vecteur position1, double rayon1, Vecteur vitesse1, Vecteur accélération1, double masse1, 
        Vecteur position2, double rayon2, Vecteur vitesse2, Vecteur accélération2, double masse2)   
//modifie b1 et b2
//données : b1 et b2 avant le choc
//résultats : b1 et b2 après le choc
{
Vecteur G1G2;
double nG1G2, nG1G2_2;
G1G2 = Vecteur.difference(position2, position1);
nG1G2_2 = G1G2.normeCarrée();

double r = rayon1+rayon2;

double r2 = r*r;

if (nG1G2_2 >= r2) return -1;           // on a donc nG1G2 >= rayon1 + rayon2, il n'y a donc pas de choc

// à présent nG1G2_2 < r2

nG1G2 = Math.sqrt(nG1G2_2);

// on a donc nG1G2 < r

Vecteur u = G1G2.produit(1 / nG1G2);

Vecteur v1 = vitesse1;
Vecteur v2 = vitesse2;

Vecteur v1_v2 = Vecteur.difference(v1, v2);
double a = Vecteur.produitScalaire(u, v1_v2); // a est l'intensité du choc

double e = r - nG1G2;       // e est la profondeur de la pénétration d'une bille dans l'autre

double forceRappel = Collisions.COEFF_ELASTICITE_BILLE * e;

Vecteur a12, a21;
a12 = u.produit(forceRappel / masse2);
a21 = u.produit(-forceRappel / masse1);

accélération1.ajoute(a21);
accélération2.ajoute(a12);

return a;

} // actionReactionBilleBille
public static boolean actionReactionBilleContourAvecRebond1( Vecteur position, double rayon, Vecteur vitesse, Vecteur accélération, double masse, 
        double abscisseCoinHautGauche, double ordonnéeCoinHautGauche, double largeur, double hauteur)
{
Vecteur min = new Vecteur(abscisseCoinHautGauche,ordonnéeCoinHautGauche);
Vecteur diago = new Vecteur(largeur, hauteur);
Vecteur max = min.somme(diago); 


Vecteur coins[] = new Vecteur[5];

coins[0] = min;         // le coin haut gauche du rectangle défini par le composant
coins[1] = new Vecteur(max.x,min.y);
coins[2] = max;
coins[3] = new Vecteur(min.x, max.y);
coins[4] = coins[0];                    // pour refermer le contour !

int i;

for ( i = 1; i < coins.length; ++i)
    if ( Collisions.actionReactionBilleSegmentAvecRebond(position, rayon, vitesse, accélération, masse, coins[i-1], coins[i]) )
       return true;
    
return false;
}                   // actionReactionBilleContourAvecRebond

}
