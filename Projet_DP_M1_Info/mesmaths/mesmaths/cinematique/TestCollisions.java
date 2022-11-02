package mesmaths.cinematique;

import java.util.Arrays;
import java.util.Vector;

import mesmaths.geometrie.base.Vecteur;

public class TestCollisions
{

	public TestCollisions()
	{
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args)
	{
		Vecteur position1;
		double rayon1 = 1;
		Vecteur vitesse1;
		double[] w1 = {10};
		Vecteur acceleration1;
		double masse1 = 1;
		double J1 = 0.5;
		Vecteur position2;
		double rayon2 = 1;
		Vecteur vitesse2;
		double[] w2 = {0};
		Vecteur acceleration2;
		double masse2 = 1;
		double J2 = 0.5;
		double G1G2 = 1.9;
		Vector<Double> intensiteChoc;
		double coeffFrottement = 1;

		/*double  v1N( 2), v1T(2), w1bar(0), w1(10),  rayon1(1),  masse1(1),  J1(0.5),
        v2N(-2), v2T(2), w2bar(0), w2(0),  rayon2(1),  masse2(1),  J2(0.5),
       G1G2(1.9),  coeffFrottement(1);  */

		double[] vitesses = {2,2,w1[0], -2, 2, w2[0]}; // vitesses contient, dans l'ordre, v1N', v1T', w1', v2N', v2T', w2'

		System.out.println("vitesses avant le choc : " + Arrays.toString(vitesses));
		Collisions.actionReactionBilleBilleAvecEffet(vitesses, rayon1, masse1, J1, rayon2, masse2, J2, G1G2, coeffFrottement);

		System.out.println("vitesses apres le choc : " + Arrays.toString(vitesses));

	}

}
