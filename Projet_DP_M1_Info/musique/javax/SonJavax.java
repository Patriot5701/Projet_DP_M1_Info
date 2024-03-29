package javax;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import musique.SonBref;
import musique.SonBrefFantome;

/**
 * contient, sous forme de tableau, un fragment de fichier audio  pret � etre diffuse sur les hauts-parleurs
 * la m�thode run d�finit comment est diffus� le son gr�ce aux param�tres (volume, balance, pitch)
 * 
 * la m�thode run factorise toutes les initialisations n�cessaires � la diffusion du son.
 * la m�thode run1, abstraite � ce niveau, contient ce qui est sp�cifique � la diffusion du son
 * 
 *  Important : 
 *  
 *  1) on doit toujours traiter un nombre entier de frames (cf. d�finition du format d'un fichier audio)
 *  
 *  2) Le paquet d'octets envoy� sur les haut-parleurs (cf. m�thode SourceDataLine.write) doit toujours contenir un nombre de frames >= TAILLE_BUFFER_LIGNE
 *  sinon on entend des craquements ou des cliquetis d�sagr�ables 
 *  
 * */
public abstract class SonJavax implements Runnable
{
	public static final int TAILLE_BUFFER_LIGNE = /*2400;*/ 4800;    /* doit corresponde � un nombre entier de frames. 
                                                             si la fr�quence d'�chantillonnage est de 48000 Hz, cette constante correspond � 0.05 seconde
                                                             si la fr�quence d'�chantillonnage est de 44100 Hz cela correpond � un peu plus de 0.05 seconde */
	SourceDataLine ligne;           // ligne sur laquelle est diffus� le son
	byte tampon[];                  // contient le fragment de fichier audio � diffuser. contient un nombre entier de frames �  jouer
	AudioFormat audioFormat;        // d�tails du format audio du fichier audio dont on extrait le fragment 

	String nomFichier = "non renseign�";              /* nom du fichier avec l'extension .wav */
	int debutExtrait = -1, finExtrait = -1;   /* en centi�mes de secondes */

	public void initInfos( String nomFichier, int debutExtrait, int finExtrait)
	{
		this.nomFichier = nomFichier; this.debutExtrait = debutExtrait; this.finExtrait = finExtrait;
	}

	public String getNomFichier()
	{
		return this.nomFichier;
	}

	/**
	 * volume : tel que 0.0 <= volume <= 1.0
	 * balance : tel que -1.0 <= balance <= 1.0. -1.0 <---> tout sur l'enceinte gauche, 1.0 <---> tout sur l'enceinte droite
	 * 
	 * pich, en anglais, repr�sente le niveau d'aigu (ou la hauteur d'un son)
	 * 
	 * on doit avoir 0 < coeffPitch. 
	 * pour coeffPitch == 1, le son est jou� � la m�me hauteur que lors de l'enregistrement,
	 * si coeffPitch > 1, le son jou� est plus aigu que l'original 
	 * si 0 < coeffPitch < 1, le son jou� est plus grave que l'original,
	 * 
	 *  ===>>> Notez bien que ces 3 param�tres peuvent �tre chang�s avant chaque diffusion. cf. m�thode joue(,,) de la classe SonBrefJavax  
	 *  
	 *  */

	double volume, balance, coeffPitch;      

	/** petite fonction pourrie qui transforme le volume en gain.
	 * On transforme l'intervalle [0, 1/2, 1] en [gainMin, 0, gainMax]
	 * 
	 * on prend une fonction affine par morceaux
	 * 
	 * On garantit que : 
	 * 
	 * convertit(0) = gainMin
	 * convertit(0.5) = 0
	 * convertit(1) = gainMax
	 * 
	 * la fonction est affine sur [0,0.5] et sur [0.5, 1],
	 * 
	 * sur ]-inf,0] elle est constante et vaut gainMin
	 * 
	 * sur ]1,+inf[ elle est constante et vaut gainMax
	 * 
	 * */
	static double convertit(double volume, double gainMin, double gainMax)
	{
		if (volume < 0) return gainMin;

		double u = 2*volume-1;

		if (volume < 0.5) return -gainMin*u;

		if (volume < 1) return gainMax*u;

		else return gainMax;
	}

	/**
	 * extrait du fichier "nomFichier" un morceau de son que l'on va stocker dans le tableau tampon 
	 * @param r�pertoire : r�pertoire contenant le fichier audio nomFichier
	 * @param nomFichier : nom du fichier audio avec l'extension .wav
	 * @param debutExtrait en centi�mes de secondes : debut de l'extrait � charger
	 * @param finExtrait   en centi�mes  de secondes : fin de l'extrait � charger
	 * @throws LineUnavailableException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */
	public SonJavax( File repertoire, String nomFichier, int debutExtrait, int finExtrait) throws LineUnavailableException, IOException, UnsupportedAudioFileException 
	{
		this(AudioSystem.getAudioInputStream(new File(repertoire,nomFichier)),debutExtrait,finExtrait);
		this.initInfos(nomFichier, debutExtrait, finExtrait);
	}

	/**
	 * @param volume : tel que 0.0 <= volume <= 1.0
	 * @param balance : tel que -1.0 <= balance <= 1.0. -1.0 <---> tout sur l'enceinte gauche, 1.0 <---> tout sur l'enceinte droite
	 * @param coeffPitch : pich, en anglais, repr�sente le niveau d'aigu (ou la hauteur d'un son)
	 * 
	 *      on doit avoir 0 < coeffPitch. 
	 *      pour coeffPitch == 1, le son est jou� � la m�me hauteur que lors de l'enregistrement,
	 *      si coeffPitch > 1, le son jou� est plus aigu que l'original 
	 *      si 0 < coeffPitch < 1, le son jou� est plus grave que l'original
	 */
	public void init(AudioFormat format, byte[] tampon, double volume, double balance, double coeffPitch) throws LineUnavailableException
	{
		this.ligne = AudioSystem.getSourceDataLine(format);
		this.ligne.open(format,TAILLE_BUFFER_LIGNE);

		//System.err.println("ouverture de la ligne  r�ussie !!!");

		this.tampon = tampon;
		this.audioFormat = format;
		this.volume = volume;
		this.balance = balance;
		this.coeffPitch = coeffPitch;       
	}

	/** n�cessaire � la d�finition d'une m�thode clone(). cf. classe d�riv�e SonBrefJavax */
	protected SonJavax(AudioFormat format, byte [] tampon, double volume, double balance, double coeffPitch) throws LineUnavailableException
	{
		this.init(format, tampon, volume, balance, coeffPitch);
	}

	/**
	 * @param debutExtrait : en centi�mes de seconde
	 * @param finExtrait : en centi�mes de seconde
	 * @throws LineUnavailableException 
	 * 
	 * construit l'extrait du fichier audio compris entre les instants debut et fin.
	 * Il faut que debut < fin
	 * 
	 * 
	 * Rappelons que dans un fichier audio, il faut toujours lire un nombre entier de frames !
	 * @throws IOException 
	 * 
	 * */
	protected SonJavax(AudioInputStream fichierAudio, int debutExtrait, int finExtrait) throws LineUnavailableException, IOException
	{
		AudioFormat format;

		format = fichierAudio.getFormat();

		System.err.println(" format audio : "+ format);

		System.out.println(" format du fichier son : "+format);
		System.out.println(" nombre de canaux : "+format.getChannels());
		System.out.println(" nombre de frames par seconde : "+format.getFrameRate());
		System.out.println(" taille d'un frame en octets : "+format.getFrameSize());
		System.out.println(" frequence d'echantillonnage : "+format.getSampleRate());
		System.out.println(" taille d'un echantillon en bits : "+format.getSampleSizeInBits());
		System.out.println("format.isBigEndian() : " + format.isBigEndian());

		//-------------------- on calcule le nombre d'octets � extraire du fichier son ---------------------

		double debutSecondes = 0.01*debutExtrait;     // debut du passage � extraire exprim� en secondes
		double finSecondes = 0.01*finExtrait;         // fin du passage � extraire exprim� en secondes

		double dureeSecondes = finSecondes-debutSecondes; // dur�e du passage � extraire en secondes

		double frequence = format.getFrameRate(); // nombre de frames par seconde

		double nombreFrames = dureeSecondes*frequence; // dur�e du passage � extraire exprim�e en nombre de frames

		int m = format.getFrameSize();  // taille d'un frame en octets ou bien nombre d'octets par frame

		int nombreOctets = (int)nombreFrames*m; // dur�e du passage � extraire en octets

		byte [] tampon = new byte[nombreOctets];

		double positiondebut = debutSecondes*frequence;   // position debut exprim�e en frames

		int positiondebutOctets = (int)positiondebut*m;

		fichierAudio.skip(positiondebutOctets); // on place le curseur du fichier � l'endroit o� commence l'extrait

		fichierAudio.read(tampon);    // on copie l'extrait dans le tampon

		this.init(format, tampon, 0.5, 0, 1);      /* valeurs par d�faut : son : au mileu, balance : au milieu et pitch inchang� */
		int tailleBufferLigne = this.ligne.getBufferSize();

		System.err.println("tailleBufferLigne = " + tailleBufferLigne);
	}


	/**
	 * un son doit n�cessairement �te diffus� dans un thread s�par� sous peine de bloquer l'application jusqu'� la fin de la diffusion
	 * */
	@Override
	public void run()
	{
		FloatControl controleVolume = (FloatControl)ligne.getControl(FloatControl.Type.MASTER_GAIN);  // pour r�gler le volume de diffusion du bruit

		//System.out.println("controle Master Gain avant setValue= " + controle);

		double gainMin = controleVolume.getMinimum();
		double gainMax = controleVolume.getMaximum();

		//double [] t = conversionGain( gainMin, gainMax);


		//System.out.println("controle Master Gain apr�s setValue= " + controle);

		FloatControl controleBalance;
		try
		{
			controleBalance = (FloatControl)ligne.getControl(FloatControl.Type.BALANCE);  // pour r�gler la balance HP gauche - HP droit
		}

		catch (IllegalArgumentException e)
		{
			System.err.println("reglage \"balance\" gauche - droite non disponible pour le fichier son utilise : enregistrement non stereo ?");
			controleBalance = new FloatControlFantome();
		}

		FloatControl controlePitch;
		try
		{
			controlePitch = (FloatControl)ligne.getControl(FloatControl.Type.SAMPLE_RATE); /* pour r�gler la vitesse de diffusion et donc la hauteur*/
		}

		catch (IllegalArgumentException e)
		{
			//System.err.println("r�glage sample rate non disponible pour la ligne utilis�e");
			controlePitch = new FloatControlFantome();
		}
		//System.out.println("controle Balance apr�s setValue= " + controle);

		//System.out.println("la ligne a d�marr�");
		if (this.tampon == null) 
			throw new ArrayIndexOutOfBoundsException("le tampon n'a pas ete cree");

		this.ligne.start();

		run1(controleVolume,controleBalance,controlePitch,gainMin,gainMax);              

		this.ligne.stop();  
	}                               // run

	/**
	 * realise le detail de la diffusion. cf. classe SonBrefJavax par exemple 
	 * 
	 * Les parametres de type FloatControl servent � parametrer le volume, la balnce et le pitch
	 * 
	 * volume, balance et coeffPitch ne sont pas des attributs intrinseques de la classe. Ils peuvent etre mis � jour par les classes derivees lors de 
	 * chaque diffusion. cf. classe SonBrefJavax
	 * 
	 * */
	protected abstract void run1(FloatControl controleVolume, FloatControl controleBalance, FloatControl controlePitch, double gainMin, double gainMax);

	/**
	 * pour lancer, dans un thread separe, la diffusion du son
	 * 
	 * 
	 * */
	public void start()
	{
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public String toString()
	{
		return "SonJavax [\n taille tampon="
				+ this.tampon.length + ",\n audioFormat=" + this.audioFormat
				+ ",\n nomFichier=" + this.nomFichier + ", debutExtrait="
				+ this.debutExtrait + ", finExtrait=" + this.finExtrait + ", volume="
				+ this.volume + ", balance=" + this.balance + ", coeffPitch="
				+ this.coeffPitch + "\n]";
	}


}   // classe SonJavax
