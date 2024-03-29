package vue;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.HeadlessException;

import musique.SonLong;

/**
 * represente une case a cocher (ou bouton de radio) pour choisir un son pour la bille hurlante
 * 
 * ICI : IL N'Y A RIEN A CHANGER
 * 
 * */
public class BoutonChoixHurlement extends Checkbox
{
	public SonLong sonLong;

	/**
	 * @param sonLong : son associe a ce bouton
	 * */
	public BoutonChoixHurlement( CheckboxGroup group, boolean state,
			SonLong sonLong) throws HeadlessException
	{
		super(sonLong.getNom(), group, state);
		this.sonLong = sonLong;
	}

}
