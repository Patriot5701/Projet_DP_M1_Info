package modele;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import controleur.EcouteurBoutonLancer;
import controleur.EcouteurHurlement;
import musique.SonLong;
import vue.BoutonChoixHurlement;
import vue.CadreAngryBalls;
import vue.VueBillard;

/**
 * Comportement ajoutant le hurlement
 * @author alexis
 *
 */
public class Hurlement extends DecorateurBille{
	private static final int DELAI_MIN = 10;    /* delai minimum de rafraichissement du son. en millisecondes */
	public static final int DELAI_MAX = 150;    /* delai maximum de rafraichissement du son. en millisecondes */
	private static final double COEFF_VOLUME = 10;      // plus la valeur est grande, plus le son augmente rapidement en fct de la vitesse de la boule 
	CadreAngryBalls cadre;
	public SonLong sonLong;
	int i;
	long dernierInstant;  
	
	/**
	 * Constructeur
	 * @param billeDecorated : la bille decoree
	 * @param cadre : le cadre
	 * @param sonLong : le son du hurlement
	 */
	public Hurlement(Bille billeDecorated,CadreAngryBalls cadre, SonLong sonLong) {
		super(billeDecorated);
		i = 0;
		this.sonLong = sonLong;
		this.cadre = cadre;
		dernierInstant = System.currentTimeMillis();
	}
	
	@Override
	public boolean gestionCollisionBilleBille(Vector<Bille> billes) {
		double n = this.getVitesse().norme();
		double y = Math.exp(-COEFF_VOLUME*n);
		double xMax = cadre.largeurBillard();
		double x1 = Math.abs(this.getPosition().x/xMax);                   /* on obtient 0 <= x1 <= 1 */ ////System.err.println("dans BilleHurlante.deplacer() : x1 =  "+ x1);
		double balance = 2*x1 - 1; 
		double volume = 1-y;
		int delai = (int)(DELAI_MIN*volume + DELAI_MAX*y);              /* le delai entre 2 diffusions diminue lorsque la vitesse augmente */
		long instant = System.currentTimeMillis();
		if (instant - this.dernierInstant >=delai)                      /* la frequence de diffusion augmente donc avec la vitesse de la bille */
		{
			double coeffPitch = 1; 
			this.sonLong.joue(i++, volume, balance, coeffPitch);            /* le son est diffuse dans un thread s�par� */
			this.dernierInstant= instant;
		}
		return billeDecoree.gestionCollisionBilleBille(billes);
	}
	
	/**
	 * Change le son de la bille
	 * @param bouton : le bouton ayant été choisis
	 */
	public void changeSon(BoutonChoixHurlement bouton) {
		this.sonLong = bouton.sonLong;
	}

	@Override
	public String toString() {
		String str = billeDecoree.toString();
		return str + ", Comportement : bille hurlante";
	}

}
