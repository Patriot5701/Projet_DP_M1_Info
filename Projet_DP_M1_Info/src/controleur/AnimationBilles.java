package controleur;

import java.util.Vector;

import modele.Bille;
import vue.VueBillard;

/**
 * responsable de l'animation des billes, c-à-d responsable du mouvement de la liste des billes. met perpétuellement à jour les billes. 
 * gère le délai entre 2 mises à jour (deltaT) et prévient la vue responsable du dessin des billes qu'il faut mettre à jour la scène
 * 
 * ICI : IL N'Y A RIEN A CHANGER
 * */
public class AnimationBilles implements Runnable{


	Vector<Bille> billes;   // la liste de toutes les billes en mouvement 
	VueBillard vueBillard;    // la vue responsable du dessin des billes
	private Thread thread;    // pour lancer et arreter les billes


	private static final double COEFF = 0.5;

	/**
	 * @param billes
	 * @param vueBillard
	 */
	public AnimationBilles(Vector<Bille> billes, VueBillard vueBillard){
		this.billes = billes;
		this.vueBillard = vueBillard;
		this.thread = null;     //est-ce utile ?
	}

	@Override
	public void run(){
		try{
			double deltaT;  // delai entre 2 mises a jour de la liste des billes
			Bille billeCourante;
    
			double minRayons = AnimationBilles.minRayons(billes);   //necessaire au calcul de deltaT
			double minRayons2 = minRayons*minRayons;                //necessaire au calcul de deltaT
    
			while (!Thread.interrupted()){                           // gestion du mouvement

				//deltaT = COEFF*minRayons2/(1+maxVitessesCarr�es(billes));       // mise a jour deltaT. L'addition + 1 est une astuce pour eviter les divisions par zero
        
                                                                        //System.err.println("deltaT = " + deltaT);
				deltaT = 10;
        
				int i;
				for ( i = 0; i < billes.size(); ++i){    // mise a jour de la liste des billes
					billeCourante = billes.get(i);
					billeCourante.deplacer(deltaT);                 // mise a jour position et vitesse de cette bille
					billeCourante.gestionAcceleration(billes);      // calcul de l'acceleration subie par cette bille
					billeCourante.gestionCollisionBilleBille(billes);
					billeCourante.collisionContour( 0, 0, vueBillard.largeurBillard(), vueBillard.hauteurBillard());        //System.err.println("billes = " + billes);
				}
        
				vueBillard.miseAJour();                                // on previent la vue qu'il faut redessiner les billes
      
       
				Thread.sleep((int)deltaT);                          // deltaT peut etre considere comme le delai entre 2 flashes d'un stroboscope qui eclairerait la scene
			}
		}catch (InterruptedException e){
			/* arret normal, il n'y a rien e faire dans ce cas */
		}

	}

	/**
	 * calcule le maximum de de la norme carree (pour faire moins de calcul) des vecteurs vitesse de la liste de billes
	 * 
	 * */
	static double maxVitessesCarrees(Vector<Bille> billes){
		double vitesse2Max = 0;

		int i;
		double vitesse2Courante;

		for( i = 0; i < billes.size(); ++i)
			if ( (vitesse2Courante = billes.get(i).getVitesse().normeCarree()) > vitesse2Max)
				vitesse2Max = vitesse2Courante; 

		return vitesse2Max;
	}

	/**
	 * calcule le minimum  des rayons de a liste des billes
	 * 
	 * 
	 * */
	static double minRayons(Vector<Bille> billes){
		double rayonMin, rayonCourant;

		rayonMin = Double.MAX_VALUE;

		int i;
		for ( i = 0; i < billes.size(); ++i)
			if ( ( rayonCourant = billes.get(i).getRayon()) < rayonMin)
				rayonMin = rayonCourant;

		return rayonMin;
	}


	public void lancerAnimation(){
		if (this.thread == null){ 
			this.thread = new Thread(this);
			thread.start();
		}
	}

	public void arreterAnimation(){
		if (thread != null){
			this.thread.interrupt();
			this.thread = null;
		}
	}
}
