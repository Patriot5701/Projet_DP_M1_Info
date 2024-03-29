package javax;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import mesmaths.bsplines.OutilsNaiveBSpline;
import musique.SonBref;
import mesmaths.bsplines.FoncteurNaiveNo4;
/*
 * 
 * teste la possibilit� de diffuser plusieurs sons simultan�ment, voire beaucoup de sons simultan�ment : 
 * comme les applaudissements de spectateurs, une chorale, etc.
 * Pour tester une cacophonie, un brouhaha
 * 
 * ici les sons sont individuellement modulables en volume et en "balance"
 * 
 * */
public class TestBrouhahaSonBrefHuey
{



	public static void main(String[] args)
	{
		try
		{
			File repertoire = new File("");
			repertoire = new File(repertoire.getAbsoluteFile(),"musique"+File.separatorChar+"bruits");
			String nomFichier = "huey2.wav";

			int effectif = 15;                          // pour huey
			SonBref sons[] = new SonBref[effectif];

			int debutExtrait, finExtrait;           /* en centi�mes de secondes */
			int pasTemporel = 20;                   /* en centi�mes de secondes*/ 
			int i;
			int debutAbsolu = 3000;                 /* en centi�mes de secondes*/ 
			for ( i = 0, debutExtrait = debutAbsolu, finExtrait = debutAbsolu + pasTemporel; i < effectif; ++i, finExtrait += pasTemporel)
			{ 
				sons[i] = new SonBrefJavax(repertoire,nomFichier,debutExtrait,finExtrait);
				debutExtrait = finExtrait;
			}

			/* on a d�coup� l'intervalle temporel [3000,3300] en 15 intervalles de taille identique */
			/* on va diffuser le son en bouclant sur le tableau. 
			 * Plusieurs sons vont �tre jou�s en parall�le, le volume et la balance varient d'un passage au suivant */

			System.out.println("les sons sont pr�ts");

			double milieu = 0.5*effectif;
			double rayon = milieu;
			FoncteurNaiveNo4 foncteurNaiveNo4 = new FoncteurNaiveNo4(milieu, rayon);
			/* foncteurNaiveo4 est une fct "cloche", un peu comme une gaussienne, qui donne toujours des valeurs sur [0,1],
			 * cette cloche est centr�e au milieu de l'extrait et son rayon est la moit� de la dur�e totale de l'extrait,
			 * cela sert � faire varier de fa�on lisse et p�riodique le volume, la balance et la fr�quence des diffusions */
			/* le son produit doit rester harmonieux, sans coupure et sans craquements ou cliquetis */


			int nombreRepetitions = 180; /* arbitraire, on met ce qu'on veut. ici on va boucler 12 fois sur le tableau,
                                     on obtient donc 12 p�riodes cons�cutives 
                                     Rappel math�matique : 12 p�riodes signifie que la diffusion va se Repeter de fa�on strictement identique 12 fois */

			for ( i = 0; i < nombreRepetitions; ++i) // on boucle sur le tableau
			{
				int j = i%effectif;                                 // ainsi on acc�de toujours � un �l�ment du tableau 
				double y = foncteurNaiveNo4.calcule(j);             /* calcule l'intensit� y entre 0 et 1 en fct de i, l'intensit� est maximale et vaut 1 lorsque i 
                                                             correspond au milieu de l'extrait, l'intensit� y s'approche de 0 lorsque i est proche du debut 
                                                             du tableau ou proche de la fin du tableau  */
				int delai = (int)(200*(1-y));                       /* delai entre 2 diffusions cons�cutives. en millisecondes. varie entre 0.2 s et 0 s */

				Thread.sleep(delai);                                // main "attend" et est donc bloqu�e pour une dur�e de delai millisecondes
				double volume = 0.6*y+0.4;                          // le volume varie p�riodiquement. toujours sur l'intervalle [0,1]
				double balance = -1+2*y;                            // la balance varie p�riodiquement. toujours sur l'intervalle [-1,1]
				sons[j].joue(volume,balance);                       // on diffuse le son sur les hauts-parleurs

				//System.out.println("i = " + i);
				//System.out.println("y = " + y);
			}
			System.out.println("1. les sons ont �t� jou�s");
			int c = System.in.read();

			/*apr�s avoir �cout� cette diffusion, r��couter le fichier son initial et comparer pour noter les effets sonores obtenus */

			/* la diffusion du son est mauvaise sur la boucle qui suit car le m�me objet son (et donc la m�me ligne) est utilis� simultan�ment par plusieurs threads
			 * exemple de ce qu'il ne faut pas faire  !!! */

			for (i = 0; i < 100; ++i) 
			{
				int duree = (int)(200*Math.random());  //dur�e d�finie al�atoirement entre 0 et 200 millisecondes. en millisecondes 
				Thread.sleep(duree);

				sons[0].joue(1,0);
			}
			System.out.println("2. les sons ont �t� jou�s");

			c = System.in.read();

		}
		catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnsupportedAudioFileException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (LineUnavailableException e)
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
