package mesmaths.bsplines;

/**
 * BSpline No4 centrée en c et de rayon r. vaut 1 en c. Le support est [c-r,c+r[
 * */
public class FoncteurNaiveNo4
{
public double c, r;


public FoncteurNaiveNo4(double c, double r)
{
super();
this.c = c;
this.r = r;
}

public double calcule ( final double u) { return OutilsNaiveBSpline.gNaiveNo4(u,c,r);}
}