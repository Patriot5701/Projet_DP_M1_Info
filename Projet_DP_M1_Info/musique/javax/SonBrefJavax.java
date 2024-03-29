package javax;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import musique.SonBref;
import musique.SonBrefFantome;


public class SonBrefJavax extends SonJavax implements SonBref
{

	/**
	 * pour le d�tail des param�tres cf. constructeur classe SonJavax
	 */
	public SonBrefJavax( File repertoire, String nomFichier, int debutExtrait, int finExtrait) throws LineUnavailableException, IOException, UnsupportedAudioFileException 
	{
		super(repertoire,nomFichier,debutExtrait,finExtrait);
	}

	public SonBrefJavax(AudioInputStream fichierAudio, int debutExtrait,
			int finExtrait) throws LineUnavailableException, IOException
	{
		super(fichierAudio, debutExtrait, finExtrait);
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructeur n�cessaire � la m�thode clone()
	 * */
	protected SonBrefJavax(AudioFormat format, byte [] tampon, double volume, double balance, double coeffPitch) throws LineUnavailableException
	{
		super(format, tampon, volume, balance, coeffPitch);
		this.initInfos(this.nomFichier, this.debutExtrait, this.finExtrait);
	}

	/**
	 * @return le nom du son (sans l'extension)
	 * 
	 * */
	@Override
	public String getNom()
	{
		String nomComplet = this.getNomFichier();
		int p = nomComplet.indexOf('.');
		return nomComplet.substring(0, p);
	}
	/*{
String nomComplet = this.getNomFichier();                           System.err.println("Dans SonBrefJavax.getNom() : nomComplet = " + nomComplet);
String t[] = nomComplet.split(".");                                 System.err.println("Dans SonBrefJavax.getNom() : t = " + Arrays.toString(t));
return t[0];                                                        Cet algo ne marche pas. t est vide ! pourquoi ??
}*/

	public SonBref clone()
	{
		try
		{
			return new SonBrefJavax(this.ligne.getFormat(), Arrays.copyOf(this.tampon, this.tampon.length), this.volume, this.balance, this.coeffPitch);
		}
		catch (LineUnavailableException e)
		{
			System.err.println("echec de clone() dans SonBrefJavax");
			return new SonBrefFantome();                                /* pour que l'application puisse fonctionner m�me si le son n'est pas disponible */
		}
	}

	/**
	 * @see musique.javax.SonBref#joue(double, double,double)
	 * diffuse dans sa totalit� le fragment de fichier audio stock� dans tampon avec le volume, la balance et le coeffPitch sp�cifi�s
	 * 
	 * */
	@Override
	public void joue(double volume, double balance, double coeffPitch)
	{
		this.volume = volume; this.balance = balance; this.coeffPitch = coeffPitch;

		this.start();
	}

	/** (non-Javadoc)
	 * @see musique.javax.SonBref#joue(double, double)
	 */
	@Override
	public void joue(double volume, double balance)
	{
		/* par d�faut, on met hauteur du son diffus� == hauteur du son enregistr� */
		this.joue(volume, balance, 1);
	}

	@Override
	protected void run1(FloatControl controleVolume, FloatControl controleBalance,
			FloatControl controlePitch, double gainMin, double gainMax)
	{

		double gain = convertit( volume, gainMin, gainMax);
		controleVolume.setValue((float)gain);                               // il faut que gainMin <= gain = gainMax  
		controleBalance.setValue((float)balance);                          // il faut que -1 <= valeur = +1. -1 <---> tout � gauche. +1 <---> tout � droite. par d�faut, balance = 0 
		controlePitch.setValue((float)(this.coeffPitch*this.audioFormat.getFrameRate()));           /* ceci n'est pas disponible sur ma machine */
		this.ligne.write(tampon, 0, tampon.length);             /* envoie le son sur les haut-parleurs. on doit avoir tampon.length > SonJavax.TAILLE_BUFFER_LIGNE 
                                                         sous peine d'entendre des clics d�sagr�ables et de perturber la fluidit� de la diffusion */
		this.ligne.drain();                                     /* �vite les craquements */
	}



}
