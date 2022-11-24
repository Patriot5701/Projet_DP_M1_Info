package modele;

public abstract class DecorateurBilleCollisionBord extends DecorateurBille{

	public DecorateurBilleCollisionBord(Bille billeDecorated) {
		super(billeDecorated);
		if(!ComportementMemoire.getInstance().hasAComportment()) {
			ComportementMemoire.getInstance().setComportementCollision(this);
		}
	}
	
	public abstract String toStringCptmt();
	
	@Override
	public String toString() {
		String str = billeDecoree.toString();
		return str + toStringCptmt();
	}

}
