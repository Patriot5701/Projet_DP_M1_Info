package modele;

public class ComportementMemoire {
	private Class<?> comportement;
	
	
	private static ComportementMemoire instance = null;

    private ComportementMemoire() {
    }

    public static ComportementMemoire getInstance() {
        if ( instance == null ) {
            instance = new ComportementMemoire();
        }
        return instance;
    }
    
    public String getComportementCollision(){
    	return comportement.getName();
    }
    
    public void setComportementCollision(Object objet) {
    	comportement = objet.getClass();
    }
    
    public boolean hasAComportment() {
    	if(comportement!=null) {
    		return true;
    	}
    	return false;
    }

}
