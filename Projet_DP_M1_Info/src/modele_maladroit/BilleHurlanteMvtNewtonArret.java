package modele_maladroit;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import vue.BoutonChoixHurlement;
import vue.VueBillard;
import mesmaths.cinematique.Collisions;
import mesmaths.geometrie.base.Vecteur;
import mesmaths.mecanique.MecaniquePoint;
import musique.SonLong;

/**
 * 
 * Bille s'arretant sur les bords et subissant l'attraction des autres billes.
 *
 * Cette bille "hurle" aussi. Elle emet un son continu qui varie en fct de sa vitesse et de sa position.
 * 
 * Le son est stereo. la balance est determinee par la position (abscisse) de la bille
 * Le volume du son et la frequence d'�missions augmentent avec la vitesse de la bille
 * 
 * 
 * 
 *  A MODIFIER
 *  
 *  */

public class BilleHurlanteMvtNewtonArret extends Bille implements ItemListener // mauvais : depend de awt
{
	private static final int DELAI_MIN = 10;    /* delai minimum de rafraichissement du son. en millisecondes */
	public static final int DELAI_MAX = 150;    /* delai maximum de rafraichissement du son. en millisecondes */
	public SonLong sonLong;                            /* bande son a diffuser */
	int i;              /* ne de l'element de sonLong a jouer. on doit avoir i >= 0. 
                    sonLong se charge de faire le modulo pour obtenir un indice correct
                    et pour boucler ainsi sur le tableau inscrit dans sonLong */
	long dernierInstant;        /* dernier instant ou le son a ete diffuse */
	VueBillard vueBillard;

	private static final double COEFF_VOLUME = 6;      // plus la valeur est grande, plus le son augmente rapidement en fct de la vitesse de la boule 

	/*public BilleHurlante(Bille suivant, SonLong sonLong, VueBillard vueBillard)
{
super(suivant);
this.sonLong = sonLong;
i = 0;
dernierInstant = System.currentTimeMillis();
this.vueBillard = vueBillard;
}*/

	public BilleHurlanteMvtNewtonArret(Vecteur position, double rayon, Vecteur vitesse,
			Color couleur, SonLong sonLong, VueBillard vueBillard)
	{
		super(position, rayon, vitesse, couleur);
		this.sonLong = sonLong;
		i = 0;
		dernierInstant = System.currentTimeMillis();
		this.vueBillard = vueBillard;
	}

	@Override
	public void deplacer(double deltaT)
	{
		super.deplacer(deltaT);
		Vecteur p = this.getPosition();
		Vecteur v = this.getVitesse();
		double xMax;

		xMax = vueBillard.largeurBillard();

		double n = v.norme();
		//double n2 = v.normeCarr�e();
		double y = Math.exp(-COEFF_VOLUME*n);                // y = e^(-COEFF*n). on obtient donc 0 < y <= 1
		double volume = 1-y;                                 // on obtient 0 <= volume < 1 avec volume == 0 si n == 0  et volume proche de 1 si n est grand
		double x1 = p.x/xMax;                   /* on obtient 0 <= x1 <= 1 */ ////System.err.println("dans BilleHurlante.deplacer() : x1 =  "+ x1);
		double balance = 2*x1 - 1;              // on obtient -1 <= balance <= 1
		////System.err.println("volume = " + volume);
		//double v2 = volume*volume;
		int delai = (int)(DELAI_MIN*volume + DELAI_MAX*y);              /* le delai entre 2 diffusions diminue lorsque la vitesse augmente */
		long instant = System.currentTimeMillis();
		if (instant - this.dernierInstant >=delai)                      /* la frequence de diffusion augmente donc avec la vitesse de la bille */
		{
			double coeffPitch = 1; 
			this.sonLong.joue(i++, volume, balance, coeffPitch);            /* le son est diffuse dans un thread s�par� */
			this.dernierInstant= instant;
		}
	}


	/* (non-Javadoc)
	 * @see decorateur_angryballs.modele.Bille#gestionAcc�l�ration(java.util.Vector)
	 */
	@Override
	public void gestionAcceleration(Vector<Bille> billes)
	{
		super.gestionAcceleration(billes);                              // remise a zero du vecteur acceleration
		this.getAcceleration().ajoute(OutilsBille.gestionAccelerationNewton(this, billes));     // contribution de l'acceleration due a l'attraction des autres billes
	}

	@Override
	public void collisionContour(double abscisseCoinHautGauche, double ordonneeCoinHautGauche, double largeur, double hauteur)
	{
		Collisions.collisionBilleContourAvecArretHorizontal(this.getPosition(), this.getRayon(), this.getVitesse(), abscisseCoinHautGauche, largeur);
		Collisions.collisionBilleContourAvecArretVertical(this.getPosition(), this.getRayon(), this.getVitesse(), ordonneeCoinHautGauche, hauteur);
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{                                                                               //System.err.println("dans BilleHurlanteMvtNewtonArret.itemStateChanged au debut");
		if (e.getSource() instanceof BoutonChoixHurlement)
		{
			BoutonChoixHurlement boutonChoixHurlement = (BoutonChoixHurlement)(e.getSource());
			this.sonLong = boutonChoixHurlement.sonLong;                                //System.err.println("dans BilleHurlanteMvtNewtonArret.itemStateChanged");
		}
	}



} //classe BilleHurlanteMvtNewtonArret
