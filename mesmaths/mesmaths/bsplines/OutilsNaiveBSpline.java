package mesmaths.bsplines;

/**
 * NaiveBSpline.h
 *
 *  Created on: 10 mars 2022
 *      Author: Dom
 *
 *      représente la fct BSpline t |---> No,4(t) directement écrite en développant la formule de récurrence.
 */
public class OutilsNaiveBSpline
{

/**
 * représente la fct BSpline t |---> No,4(t) directement écrite en développant la formule de récurrence.
 *
 * */
public static double NaiveNo4(final double t)
{
if (t < 0) return 0;

if ( t < 1)
   return t*t*t/6;
if (t < 2)
   {
   double t_1 = t-1;

   double a = (2-t)*t*t;
   double b = (3-t)*t*t_1;
   double c = (4-t)*t_1*t_1;

   return (a+b+c)/6;
   }
if (t <3)
   {
   double t3 = 3-t;
   double t4 = 4-t;
   double a = t3*t3*t;
   double b = t4*(t-1)*t3;
   double c = t4*t4*(t-2);
   return (a+b+c)/6;
   }
if ( t < 4 )
   {
   double t4 = 4-t;
   return t4*t4*t4/6;
   }
else
   return 0;
}

/**
 * BSpline No4 centrée en 0 et de rayon 1, vaut 1 au sommet. Le support est [-1,1[
 * */
public static double NaiveNo4Normalisee(final double u)
{
return 1.5*NaiveNo4(2*u+2);
}

/**
 * BSpline No4 centrée en c et de rayon r, vaut 1 en c. Le support est [c-r,c+r[
 * */
public static double gNaiveNo4(final double u, final double c, final double r)
{
return NaiveNo4Normalisee((u-c)/r);
}


//----------------------------------------------------------------------------------

/**
 * Soit t |--->c(t) une courbe BSpline d'ordre 4 clampée au début
 *
 * TACHE : calcule la valeur de t pour laquelle la fct t [--->N1,4(t) atteint som max lorque la courbe c est clampée au début
 *
 * On suppose donc que Uo = U1 = U2 = U3 < U4 < U5
 *
 * DONNEES : Uo, U4, U5
 *
 * RESULTATS : t tel que t |---> N1,4(t) atteint son max en t
 * */
public static double positionMaxN14Clampee( final double Uo, final double U4, final double U5)
{
double a = U4-Uo;
double b = U5-Uo;

double s = a+b;
double p = a*b;

double x1 = p/(s+Math.sqrt(p));

return x1 + Uo;
}

}



