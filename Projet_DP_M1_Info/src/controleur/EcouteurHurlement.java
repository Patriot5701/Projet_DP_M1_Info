package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import modele.Hurlement;
import musique.SonLong;
import vue.BoutonChoixHurlement;
import vue.CadreAngryBalls;

/**
 * Ecouteur de changements survenus avec la bille hurlante
 * @author alexis
 *
 */
public class EcouteurHurlement implements ItemListener{
	Hurlement billeHurlante;

	/**
	 * Constructeur
	 * @param hurlement
	 */
	public EcouteurHurlement(Hurlement hurlement)
	{
	this.billeHurlante = hurlement;
	}

	/**
	 * Evenement : changement de son
	 * @param e : ItemEvent
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() instanceof BoutonChoixHurlement)
			{
				this.billeHurlante.changeSon((BoutonChoixHurlement)(e.getSource()));            
			}
	}

}
