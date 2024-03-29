package javax;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import musique.SonBref;
import musique.SonBrefFantome;

/*
 * Exemple de diffusion d'un son bref avec param�trage du volume, de la balance et de l'extrait � diffuser
 * 
 * 
 * */
public class TestSonBrefSabreLaser
{

	public static void main(String[] args)
	{
		try
		{

			File repertoire = new File("");             /* r�pertoire cible de la JVM : racine du projet  */

			repertoire = new File(repertoire.getAbsoluteFile(),"musique"+File.separatorChar+"bruits");      /* emplacement des bruitages */

			String nomFichier = "sabrelaser.wav";           /* nom du fichier son � utiliser. extension .wav obligatoire, les autres formats ne sont pas g�r�s */

			int debutExtrait = 0, finExtrait = 150;  // positions sur l'axe temporel du fichier son. en centi�mes de secondes

			/* extraction du passage � diffuser : chargement de l'extrait de son dans le tableau d'octets contenu dans l'objet sonBref */

			SonBref sonBref;
			try
			{
				sonBref = new SonBrefJavax(repertoire,nomFichier,debutExtrait,finExtrait);
			}
			catch (Exception e)
			{
				System.err.println("son non cr��");
				sonBref = new SonBrefFantome();
			}

			System.out.println("sonBref = " +"\n"+sonBref);

			//System.out.println("sonBref.getNomFichier() = " + sonBref.getNomFichier());
			System.out.println("sonBref.getNom() = " + sonBref.getNom());

			//System.in.read();
			/* � pr�sent, le son � diffuser est repr�sent� par l'objet sonBref */

			double volume = 0.5;  /* volume � moit�. On doit choisir 0 <= volume <= 1. Le son est � fond pour volume == 1 */ 

			double balance = 0;     /* le son est distribu� de fa�on �quitable entre les HP gauche et droite
                           pour balance == -1, tout le son est diffus" sur le haut-parleur gauche, 
                           pour balance == 1, tout  le son est diffus� sur le haut-parleur droit */


			System.out.println("on d�marre le son dans un thread s�par�");

			sonBref.joue(volume,balance);

			System.out.println("le son a �t� jou�");

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
		/*catch (UnsupportedAudioFileException e)
{
// TODO Auto-generated catch block
e.printStackTrace();
}*/
		/*catch (IOException e)
{
// TODO Auto-generated catch block
e.printStackTrace();
}
		 *//*catch (LineUnavailableException e)
{
// TODO Auto-generated catch block
e.printStackTrace();
}*/
		/*catch (InterruptedException e)
    {
    // TODO Auto-generated catch block
    e.printStackTrace();
    }*/


	}
}

