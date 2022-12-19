package controleur;

public class CaughtController extends ControllerState{

	public CaughtController(ControllerGeneral ctrlrGen, ControllerState follow, ControllerState prev) {
		super(ctrlrGen, follow, prev);
	}
	
	//IMPORTANT
	/*
	public void traiteCasGeneral(char c)
	{
	Operation op;
	op = this.résout(c); // reconnaissance de l'opération à effectuer
	    
	if (op != null) // l'opération a été reconnue
	   {
	   this.controllerGeneral.drivenState.setOpération(op);
	   this.controllerGeneral.drivenState.complèteRésultat(c);
	   this.controllerGeneral.setContrôleurCourant(this.follower);
	   }
	}
	
	/**
	 * Reconnaissance de l'opération à effectuer
	 * 
	 * @param c : le symbole de l'opération, un opérateur parmi + et *
	 * @return l'opération à effectuer ou null si l'opération n'est pas reconnue
	 * 
	 * Remarque : l'algo de la méthode est TRES maladroit. Il doit être remplacé par le DP COR dans une version finale
	 * */
	/*
	private Operation résout(char c)
	{
	if (c == '+')
	    return new Plus();
	if (c == '*')
	    return new Fois();

	return null;
	}

	}

	//Autre exemple

/*
 * public void traiteCasGeneral(char c)
{
int x = c - '0';
if (0 <= x && x <= 9)
    {
    this.traiteOpérande(x);
    this.controleurGeneral.setContrôleurCourant(this.suivant);
    }
}

/**
 * Traite le cas de la frappe d'une touche représentant un chiffre de 0 à 9
 * 
 * */
/*
protected abstract void traiteOpérande(int x);
}

 */

//3e exemple

/*
@Override
protected void traiteOpérande(int x)
{
this.controleurGeneral.calculetteState.setX(x);
this.controleurGeneral.calculetteState.setRésultat(Integer.toString(x));
}
*/

	
	

}
