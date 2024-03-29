package javax;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import musique.SonBref;
import musique.SonLong;

public class SonLongJavax extends SonLong
{
	//public SonBref sons[];

	/**
	 * @param r�pertoire : chemin du dossier o� est plac� le fichier audio
	 * @param nomFichier : nom du fichier audio se terminant par l'extension .wav
	 * @param d�butExtrait : en centi�mes de secondes
	 * @param finExtrait : en centi�mes de secondes tel que d�butExtrait < finExtrait
	 * @param effectif : le passage extrait est d�coup� en effectif passages cons�cutifs
	 * 
	 * il faut choisir d�butExtrait, finExtrait et effectif de telle sorte que finExtrait-d�butExtrait soit un multiple de effectif
	 * */
	public SonLongJavax( File repertoire, String nomFichier, int debutExtrait, int finExtrait, int effectif) throws LineUnavailableException, 
	IOException, 
	UnsupportedAudioFileException 
	{
		super(new SonBrefJavax[effectif]);
		//this.sons = new SonBrefJavax[effectif];
		int i;
		int deltaT = finExtrait-debutExtrait;

		if (deltaT % effectif != 0) throw new IllegalArgumentException("dans SonLong(...) : finExtrait - d�butExtrait n'est pas un multiple de effectif ");

		int pas = deltaT/effectif;
		int debut, fin;
		for ( i = 0, debut = debutExtrait, fin = debutExtrait + pas; i < effectif; ++i, fin +=pas)
		{
			sons[i] = new SonBrefJavax(repertoire,nomFichier,debut,fin);
			debut = fin;
		}
	}

	/*public void joue(int i, int d�lai, double volume, double balance, double coeffPitch) throws InterruptedException
{
int j = i%this.sons.length;

Thread.sleep(d�lai);

sons[j].joue(volume,balance,coeffPitch);

}*/

	/**
	 * @param r�pertoireBruits : chemin du dossier o� est stock� le fichiers audio .wav pour la cr�ation de l'objet sonLongJavax
	 * @param s : s est suppos�e respecter le format suivant : 
	 * nom du fichier audio (sans l'extension) d�but de l'extrait (en centi�mes de secondes) fin de l'extrait (en centi�mes de secondes) effectif
	 * 
	 * Exemple : s = "spitfire 1100 1700 30" 
	 * Le caract�re ' ' est le s�parateur
	 * 
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 * @throws LineUnavailableException 
	 * 
	 * Pour le d�tail des param�tres (d�butExtrait,finExtrait,effectif) extraits de s, cf. constructeur ci-avant
	 * 
	 * */
	public static SonLong cree(File repertoireBruits, String s) throws LineUnavailableException, IOException, UnsupportedAudioFileException
	{       
		String t[] = s.split(" ");
		String nom = t[0];
		nom +=".wav";
		int debutExtrait = Integer.parseInt(t[1]);
		int finExtrait = Integer.parseInt(t[2]);
		int effectif = Integer.parseInt(t[3]);

		return new SonLongJavax(repertoireBruits, nom, debutExtrait, finExtrait, effectif);
	}
}
