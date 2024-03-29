package outilsvues.dessinateurs;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

public class CanvasDessinPR extends Canvas
{
	Vector<String> instructionsDeDessin;

	/**
	 * @param instructionsDeDessin
	 */
	public CanvasDessinPR()
	{
		this.instructionsDeDessin = new Vector<String>();
	}

	@Override
	public void paint(Graphics graphics)
	{
		Dessinateur dessinateur = new DessinateurSimple(graphics);

		Point debut = new Point(20,10);
		Point fin = new Point(90,40);
		dessinateur.dessineSegment(debut, fin);
		int epaisseurTrait1 = 5; // 5 pixels d'�paisseur
		dessinateur = new DessinateurEpaisseur(dessinateur,epaisseurTrait1);
		Point centre1 = new Point(80,80);
		int rayon1 = 20;
		dessinateur.dessineCercle(centre1, rayon1);

		dessinateur = new DessinateurCouleur(dessinateur,"red");
		Point centre2 = new Point(150, 110);
		int rayon2 = 40;
		dessinateur.dessineDisque(centre2, rayon2);


		int epaisseurTrait2 = 8;
		// Graphics graphics1 = (Graphics)graphics.clone();
		Dessinateur dessinateur2 = new DessinateurCouleur(new DessinateurEpaisseur(new DessinateurSimple(graphics),epaisseurTrait2),"green");
		dessinateur2.dessineCercle(centre2, rayon2);
		Point position = new Point(180,30);
		dessinateur.dessineMessage("Un �l�phant, �a trompe �norm�ment !", position);
		dessinateur.dessineSegment(new Point(150,20), new Point(110,60));
	}



}
