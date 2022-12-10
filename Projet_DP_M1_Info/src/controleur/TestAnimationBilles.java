package controleur;

import java.awt.Color;
import java.io.IOException;
import java.util.Vector;
import modele.Bille;
import modele.BilleSimple;
import modele.CollisionBordFranchissement;
import modele.Colors;
import mesmaths.geometrie.base.Vecteur;
import modele_maladroit.BilleMvtRUPasseMurailles;
import vue.VueBillard;

public class TestAnimationBilles
{

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{
		Vector<Bille> billes = new Vector<Bille>();

		Vecteur centreBille1 = new Vecteur(110,70);
		double rayonBille1 = 12;
		Vecteur vitesseBille1 = new Vecteur (10,-30);
		Bille bille1 = new BilleSimple(centreBille1, rayonBille1, vitesseBille1, Colors.CYAN);
		bille1 = new CollisionBordFranchissement(bille1);
		billes.add(bille1);

		Vecteur centreBille2 = new Vecteur(20,50);
		double rayonBille2 = 15;
		Vecteur vitesseBille2 = new Vecteur (5,-5);
		Bille bille2 = new BilleSimple(centreBille2, rayonBille2, vitesseBille2, Colors.CYAN);
		bille2 = new CollisionBordFranchissement(bille2);
		billes.add(bille2);


		double vmax2 = AnimationBilles.maxVitessesCarrees(billes);
		double rayonmin2 = AnimationBilles.minRayons(billes);

		System.out.println("vmax2 = " + vmax2 + "\nrayonMin2 = " + rayonmin2);
		int c = System.in.read();

		VueBillard vueBillardFactice = new VueBillardFactice();

		AnimationBilles animation = new AnimationBilles(billes, vueBillardFactice);
		animation.lancerAnimation();
	}

}
