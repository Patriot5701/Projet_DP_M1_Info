package outilsvues.dessinateurs;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.image.BufferStrategy;

import outilsvues.Outils;

import java.awt.Frame;

public class FrameDessin extends Frame
{
	TextField resume1, resume2;
	Panel haut;
	CanvasDessinPR canvas;
	BufferStrategy strategie;
	Graphics graphics1, graphics2;


	FrameDessin() throws InterruptedException
	{
		super("essai de la hi�rachie des dessinateurs");
		Outils.place(this, 0.1, 0.1, 0.5, 0.4);

		this.setVisible(true);
		resume1 = new TextField("on essaie de faire de l'Active Rendering seulement sur le canvas");
		resume2 = new TextField("on essaie de faire de l'Active Rendering seulement sur le canvas");
		haut = new Panel();
		haut.setLayout(new GridLayout(2, 1));
		haut.add(resume1);
		haut.add(resume2);
		this.add(haut, BorderLayout.NORTH);

		canvas = new CanvasDessinPR();
		this.add(canvas, BorderLayout.CENTER);
		/*this.setIgnoreRepaint(true);

this.createBufferStrategy(1);

strat�gie = this.getBufferStrategy();

graphics1 = strat�gie.getDrawGraphics();
graphics2 = strat�gie.getDrawGraphics();

Thread.sleep(150);*/
	}
}
