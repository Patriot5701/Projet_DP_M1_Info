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
    /*Frame frame = new Frame("essai de la hiérachie des dessinateurs");
    Outils.place(frame, 0.1, 0.1, 0.5, 0.4);
    
    frame.setVisible(true);
    TextField resumé1 = new TextField("on essaie de faire de l'Active Rendering seulement sur le canvas");
    TextField resumé2 = new TextField("on essaie de faire de l'Active Rendering seulement sur le canvas");
    Panel haut = new Panel();
    haut.setLayout(new GridLayout(2, 1));
    haut.add(resumé1);
    haut.add(resumé2);
    frame.add(haut, BorderLayout.NORTH);
    
    Canvas canvas = new Canvas();
    frame.add(canvas, BorderLayout.CENTER);
    canvas.setIgnoreRepaint(true);
    
    canvas.createBufferStrategy(1);
    
    BufferStrategy stratégie = canvas.getBufferStrategy();
    
    Graphics graphics1 = stratégie.getDrawGraphics();
    Graphics graphics2 = stratégie.getDrawGraphics();
    
    Thread.sleep(100);
    */
    /*Dessinateur dessinateur = new DessinateurSimple(graphics1);
    
    Point début = new Point(20,10);
    Point fin = new Point(90,40);
    dessinateur.dessineSegment(début, fin);
    int épaisseurTrait1 = 5; // 5 pixels d'épaisseur
    dessinateur = new DessinateurEpaisseur(dessinateur,épaisseurTrait1);
    Point centre1 = new Point(80,80);
    int rayon1 = 20;
    dessinateur.dessineCercle(centre1, rayon1);
    
    dessinateur = new DessinateurCouleur(dessinateur,"red");
    Point centre2 = new Point(150, 110);
    int rayon2 = 40;
    dessinateur.dessineDisque(centre2, rayon2);
    
    
    int épaisseurTrait2 = 8;
    Dessinateur dessinateur2 = new DessinateurCouleur(new DessinateurEpaisseur(new DessinateurSimple(graphics2),épaisseurTrait2),"green");
    dessinateur2.dessineCercle(centre2, rayon2);
    Point position = new Point(180,30);
    dessinateur.dessineMessage("Un éléphant, ça trompe énormément !", position);*/
    //stratégie.show();
    
    //EcouteurDessin écouteur = new EcouteurDessin(frame);
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
