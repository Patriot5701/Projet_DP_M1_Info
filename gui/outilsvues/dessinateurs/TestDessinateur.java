package outilsvues.dessinateurs;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Panel;
import java.awt.Point;
import java.awt.TextField;
import java.awt.image.BufferStrategy;

import outilsvues.Outils;

public class TestDessinateur
{


public static void main(String[] args)
{
try
    {
    FrameDessin frame = new FrameDessin();
    /*Frame frame = new Frame("essai de la hi�rachie des dessinateurs");
    Outils.place(frame, 0.1, 0.1, 0.5, 0.4);
    
    frame.setVisible(true);
    TextField resum�1 = new TextField("on essaie de faire de l'Active Rendering seulement sur le canvas");
    TextField resum�2 = new TextField("on essaie de faire de l'Active Rendering seulement sur le canvas");
    Panel haut = new Panel();
    haut.setLayout(new GridLayout(2, 1));
    haut.add(resum�1);
    haut.add(resum�2);
    frame.add(haut, BorderLayout.NORTH);
    
    Canvas canvas = new Canvas();
    frame.add(canvas, BorderLayout.CENTER);
    canvas.setIgnoreRepaint(true);
    
    canvas.createBufferStrategy(1);
    
    BufferStrategy strat�gie = canvas.getBufferStrategy();
    
    Graphics graphics1 = strat�gie.getDrawGraphics();
    Graphics graphics2 = strat�gie.getDrawGraphics();
    
    Thread.sleep(100);
    */
    /*Dessinateur dessinateur = new DessinateurSimple(graphics1);
    
    Point d�but = new Point(20,10);
    Point fin = new Point(90,40);
    dessinateur.dessineSegment(d�but, fin);
    int �paisseurTrait1 = 5; // 5 pixels d'�paisseur
    dessinateur = new DessinateurEpaisseur(dessinateur,�paisseurTrait1);
    Point centre1 = new Point(80,80);
    int rayon1 = 20;
    dessinateur.dessineCercle(centre1, rayon1);
    
    dessinateur = new DessinateurCouleur(dessinateur,"red");
    Point centre2 = new Point(150, 110);
    int rayon2 = 40;
    dessinateur.dessineDisque(centre2, rayon2);
    
    
    int �paisseurTrait2 = 8;
    Dessinateur dessinateur2 = new DessinateurCouleur(new DessinateurEpaisseur(new DessinateurSimple(graphics2),�paisseurTrait2),"green");
    dessinateur2.dessineCercle(centre2, rayon2);
    Point position = new Point(180,30);
    dessinateur.dessineMessage("Un �l�phant, �a trompe �norm�ment !", position);*/
    //strat�gie.show();
    
    //EcouteurDessin �couteur = new EcouteurDessin(frame);
    }
catch (HeadlessException e)
    {
    // TODO Auto-generated catch block
    e.printStackTrace();
    }
catch (InterruptedException e)
    {
    // TODO Auto-generated catch block
    e.printStackTrace();
    }


}

}
