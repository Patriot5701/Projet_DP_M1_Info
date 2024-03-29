package vue;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import controleur.OutilsConfigurationBilleHurlante;
import modele.Bille;
import musique.SonBref;
import musique.SonBrefFantome;
import musique.SonLong;


public class TestCadreAngryBallsSeul
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		//---------------------- gestion des bruitages : parametrage du chemin du dossier contenant les fichiers audio --------------------------

		File file = new File(""); // la ou la JVM est lancee : racine du projet

		File repertoireSon = new File(file.getAbsoluteFile(),
				"src"+File.separatorChar+"bruits");
		System.out.println("repertoire son = " + repertoireSon);

		//-------------------- chargement des sons pour les hurlements --------------------------------------

		Vector<SonLong> sonsLongs = OutilsConfigurationBilleHurlante.chargeSons(repertoireSon, "config_audio_bille_hurlante.txt");

		SonLong hurlements[] = SonLong.toTableau(sonsLongs); // on obtient un tableau de SonLong

		Vector<Bille> billes = new Vector<Bille>();
		int choixHurlementInitial = 0;
		CadreAngryBalls cadre =new CadreAngryBalls("angry balls", "animation de billes marrantes", billes, hurlements, choixHurlementInitial);
		cadre.montrer();
	}

}
