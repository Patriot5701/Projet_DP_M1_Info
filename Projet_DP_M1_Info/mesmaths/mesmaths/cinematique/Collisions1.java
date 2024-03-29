package mesmaths.cinematique;

import java.util.Observable;
import java.util.Vector;

import mesmaths.geometrie.ExceptionGeometrique;
import mesmaths.geometrie.base.Geop;
import mesmaths.geometrie.base.Vecteur;
import mesmaths.geometrie.formes.ArcCourbe;

public class Collisions1 extends Observable
{
	public static final Collisions1 collisions1;    // pour mettre en oeuvre Observable, il nous faut au moins une instance de Collisions1
	static
	{
		collisions1 = new Collisions1();
	}

	//static double EPSILON = 1.0E-6;
	static final double EPSILON_CHOC_BILLE = 1.0E-2;  // en deca de cette valeur pour |u.(v1-v2)| le choc entre 2 billes est considere statique
	static final double EPSILON_CHOC_PAROI = 1.0E-2;  // en deca de cette valeur pour |J.v| le choc entre une bille et la paroi est considere statique 
	public static final int PAS_DE_CHOC = 0;
	public static final int CHOC_DYNAMIQUE = 1;
	public static final int CHOC_MOU = 2;

	public static final double g = 0.005;
	public final static double ro = 500;        // masse volumique de la paroi : pour le calcul de la poussee d'Archimede

	public static  double COEFF_ELASTICITE_BILLE = 3;
	public static  double COEFF_ELASTICITE_PAROI = 1; // quantite >= 0. si egal a zero alors minimum de reaction de la part de la paroi
	public static double COEFF_PAROI = 2*(1+COEFF_ELASTICITE_PAROI)*g;
	public static double COEFF_BILLE_BILLE = 2*(1+COEFF_ELASTICITE_BILLE)*g;




	/**
	 * 
	 * pour gerer les chocs mous avec la paroi.
	 * hypotheses : rayon > distanceSignee > -rayon
	 * 
	 * 
	 * */
	public static Vecteur  pousseeArchimede(final Vecteur N, double distanceSignee, double rayon)
	{
		double e = rayon-distanceSignee; // profondeur de la penetration de la bille dans le mur

		double v = Geop.volumeSphereTronquee(rayon, e);
		double intensitePousseeArchimede = v*ro*g;
		return N.produit(intensitePousseeArchimede);
	}


	/**
	 * V�rifie si il y a collision entre la bille (position, rayon, vitesse) et arc
	 * 
	 * @return false si il n'y a pas de collision et true si il y a collision
	 * 
	 * Si il y a collision alors calcule aussi :
	 * t l'abscisse curviligne du projete orthogonal de position sur arc
	 * {i,j} la base orthonormale locale en t sur arc orient�e telle que i vecteur tangent dans le sens croissant sur arc et j oriente vers l'interieur 
	 * le projete orthogonal de position sur arc
	 * vN : composante normale a l'arc du vecteur vitesse telle que vN < 0
	 * d : distance sign�e de position � l'arc, telle que d < rayon
	 * 
	 * */
	public static boolean  toutesCollisionsBord( final Vecteur position, double rayon, final Vecteur vitesse, final ArcCourbe arc,
			double t[], Vecteur i, Vecteur j, Vecteur projete, double vN[], double d[])
	{
		try
		{
			t[0] = arc.projeteOrthogonal(position);

			Vecteur [] base = arc.base(t[0]);
			i.set(base[0]);
			j.set(base[1]);

			vN[0] = vitesse.produitScalaire(j);

			if (vN[0] >= 0) return false;            // la bille est a l'exterieur du contour et revient vers celui-ci ou bien 
			// la bille est a l'interieur du contour et s'eloigne du bord

			// A present vN < 0

			projete.set( arc.evalue(t[0]));
			d[0] = Vecteur.difference(position, projete).produitScalaire(j);      // d est la distance signee de position a l'arc

			if (d[0] >= rayon) return false;

			// A present vN < 0 et d < rayon, il y a donc collision avec le bord

			return true;

		}
		catch (ExceptionGeometrique e)      // le projete orthogonal de position n'existe pas, cela signifie qu'il existe un autre arc du contour qui est plus 
		{                       // proche de position que ne l'est this
			return false;
		}

	} // toutesCollisionsBord



	/**
	 * gestion de la collision avec traversee de la paroi (et reapparition sur le cote oppose) de la bille definie par (position, rayon, vitesse) 
	 *  avec un segment curviligne (arc) du billard (le billard est un contour ferme constitue d'arcs). 
	 *  
	 *  si il n'y a pas collision, le vecteur position n'est pas modifie
	 * si il y a collision, le vecteur position est modifie : la bille "ressort" sur le bord oppose au bord qu'elle penetre.
	 * @param position : vecteur position de la bille
	 * 
	 * */
	public static boolean actionReactionArcPasseMuraille(Vecteur position, double rayon, final Vecteur vitesse, final ArcDansContour arc)
	{
		double t[] = new double[1];
		Vecteur i = new Vecteur();
		Vecteur j = new Vecteur();
		Vecteur projete = new Vecteur();
		double vN[] = new double[1];
		double d[] = new double[1];

		if (toutesCollisionsBord(position, rayon, vitesse, arc, t, i, j, projete, vN, d))
		{
			// A present vN < 0 et d < rayon, il y a donc collision avec le bord

			position.set(arc.oppose(projete,j.oppose(),-d[0]));
			return true;
		} 

		else return false;
	}


	/**
	 * gestion de la collision avec arret suivant la direction normale  de la bille definie par (position,rayon,vitesse) avec un arc du billard 
	 * (le billard est un contour ferme constitue d'arcs).
	 * 
	 * si il n'y a pas de collision avec l'arc, position et vitesse sont inchanges par la methode
	 * 
	 *  si il y a collision avec l'arc
	 * alors vitesse est modifie et  position reste intact
	 * 
	 * Des qu'il y a collision avec l'arc, la composante normale  du vecteur vitesse est annulee, de sorte que la bille continue a glisser le long de
	 * la bande qui l'a arretee
	 * 
	 */
	public static boolean actionReactionArcArret( final Vecteur position, double rayon, Vecteur vitesse, final ArcCourbe arc) 
	{
		double t[] = new double[1];
		Vecteur i = new Vecteur();
		Vecteur j = new Vecteur();
		Vecteur projete = new Vecteur();
		double vN[] = new double[1];
		double d[] = new double[1];

		if(toutesCollisionsBord(position, rayon, vitesse, arc, t, i, j, projete, vN, d))
		{
			// A present vN < 0 et d < rayon, il y a donc collision avec le bord

			Vecteur vs1 = j.produit(vN[0]);
			vitesse.retire(vs1);
			return true;
		} 

		else return false;
	}

	/**
	 * Effectue une partie des calculs concernant la collision d'une bille contre un arc,
	 * 
	 * qu'il y ait effet ou non. Permet de factoriser les calcules des 2 methodes qui suivent.
	 * 
	 * DONNEES : la bille (position, rayon, vitesse, w, acceleration,  masse), arc
	 * 
	 * si retourne PAS_DE_CHOC, ne calcule rien, cela signifie qu'il n'y a pas de collision
	 * 
	 * si retourne CHOC_MOU alors met � jour le vecteur acceleration (ajoute une contribution) et calcule i, N, vT, vTplusrw
	 * 
	 * si retourne CHOC_DYNAMIQUE, calcule vNp la composante du vecteur vitesse normale a arc apres la collision et calcule aussi 
	 *  i, N, vT, vTplusrw et met a jour intensiteChoc en ajoutant (position du choc, intensite du choc). Cela sert a gerer les sons d'impact.
	 * 
	 * ou vT est la composante normale a l'arc du vecteur vitesse incident 
	 * et {i,N} est la base associee au projete orthogonal de position sur l'arc
	 * et vTplusrw = vT + r*w
	 * 
	 * */
	public static int actionReactionArcTousRebonds( final Vecteur position, final double rayon, final Vecteur vitesse, final double w [], 
			Vecteur acceleration, final double masse, 
			final ArcCourbe arc, Vector<Choc> intensiteChoc, Vecteur i, Vecteur N, double vNp[], double vT[], double vTplusrw[])
	{
		double t[] = new double[1];

		Vecteur projete = new Vecteur();
		double vN[] = new double[1];
		double d[] = new double[1];

		if (!toutesCollisionsBord(position, rayon, vitesse, arc, t, i, N, projete, vN, d)) return PAS_DE_CHOC;

		//A present, on a forcement : vN < 0 et d < rayon

		vT[0] = vitesse.produitScalaire(i);
		double rw = rayon*w[0];
		vTplusrw[0] = vT[0]+rw; 

		if (vN[0] >= -EPSILON_CHOC_PAROI && Math.abs(vTplusrw[0]) <= EPSILON_CHOC_PAROI)          // -EPSILON_CHOC_PAROI <= vN[0] < 0  : le choc est mou
		{
			// calcul de la force de rappel

			double e = rayon - d[0];                    // profondeur de la penetration de la bille dans le mur, on a e > 0

			//double forceRappel = COEFF_ELASTICITE_PAROI*e;

			Vecteur pousseeArchi = Collisions1.pousseeArchimede(N, d[0], rayon);

			//Vecteur a = N.produit(forceRappel/masse);
			Vecteur a = pousseeArchi.produit(1/masse);
			//Vecteur a = N.produit(2*g);
			//Vecteur a = N.produit(COEFF_PAROI);
			acceleration.ajoute(a);              
			//System.err.println("choc mou : N = " + N +", e = " + e);
			//position.ajoute(N.produit(e));   //  Avec une translation, on remet la bille a l'interieur du contour 
			return CHOC_MOU;
		}

		else                // le choc est dynamique
		{
			// d'abord calcul du vecteur vitesse reflechi
			vNp[0] = -vN[0];
			//intensiteChoc.add( vNp[0]);
			intensiteChoc.add( new Choc( projete,vNp[0]));
			return CHOC_DYNAMIQUE;
		}
	}               // actionReactionArcTousRebonds


	/**
	 * gestion de la collision dynamique (avec rebond) ou statique  de la bille definie par (position,rayon,vitesse,acceleration, masse) avec arc
	 * La bille ne tourne PAS sur elle-meme
	 * @return false si il n'y a pas de collision
	 * 
	 * @return true si il y a collision et dans ce cas modifie vitesse ou (xor) acceleration. 
	 * Si il y choc mou, met a jour le vecteur acceleration (ajoute une contribution)
	 * Si il y a choc dynamique, modifie le vecteur vitesse (ecrase les anciennes coordonnees), redirige le vecteur vitesse vers l'interieur du contour
	 * Si il y a choc dynamique, met � jour intensiteChoc. ajoute (position du choc, intensite du choc). Cela est necessaire pour la gestion 
	 * des sons d'impacts.
	 * 
	 * c-a-d que en entree on considere que (position,vitesse,acceleration) sont le vecteur position, le vecteur vitesse et le vecteur acceleration
	 *  de la bille immediatement avant le choc
	 * et en sortie (position,vitesse,acceleration) sont le vecteur position, le vecteur vitesse et le vecteur acceleration de 
	 * la bille immediatement apres le choc
	 * 
	 * si le choc est parfaitement dynamique, le vecteur vitesse est modifie par la collision (comme une boule de billard l'est par une bande)
	 * si le choc est parfaitement statique, le vecteur acceleration est modifie (on considere que le choc est elastique et une force de rappel 
	 * est appliquee a la bille)
	 * 
	 * @param position : vecteur position de la bille immediatement avant la collision avec le contour
	 * @param rayon : rayon de la bille
	 * @param vitesse : vecteur vitesse de la bille immediatement avant la collision avec le contour
	 * @param acceleration : vecteur acceleration de la bille immediatement avant la collision avec le contour
	 *
	 * */
	public static boolean actionReactionArcAvecRebond( final Vecteur position, double rayon, Vecteur vitesse, Vecteur acceleration, double masse, 
			final ArcCourbe arc, Vector<Choc> intensiteChoc)
	{

		double [] w = {0};
		Vecteur i = new Vecteur();
		Vecteur N = new Vecteur();
		double [] vNp = new double[1];
		double [] vT = new double[1];
		double [] vTplusrw = new double[1];

		int ok = actionReactionArcTousRebonds(position, rayon, vitesse, w, acceleration, masse, arc, intensiteChoc, i, N, vNp, vT, vTplusrw);

		if (ok == PAS_DE_CHOC) return false;
		else
			if (ok == CHOC_MOU) return true;
			else
			{
				Vecteur deltaV = N.produit(2*vNp[0]);      // deltaV = vecteur vitesse reflechi - vecteur vitesse incident
				vitesse.ajoute(deltaV);
				return true;
			}
	}                   // actionReactionBilleContourAvecRebond


	/**
	 * gestion de la collision dynamique (avec rebond) ou statique  de la bille definie par (position,rayon,vitesse,w,acceleration, masse, J, Jplusmr2)
	 *  avec arc
	 * La bille tourne sur elle-meme :
	 * w est la vitesse angulaire
	 * J est le moment d'inertie,
	 * Jplusmr2 est J + masse*rayon^2
	 * 
	 * 
	 * @return false si il n'y a pas de collision
	 * 
	 * @return true si il y a collision et dans ce cas modifie (vitesse et w)  ou (xor) acceleration. 
	 * Si il y choc mou, met a jour le vecteur acceleration (ajoute une contribution)
	 * Si il y a choc dynamique, calcule le nouveau vecteur vitesse {vT,vN} et calcule la nouvelle vitesse angulaire w.
	 *  
	 * Si il y a choc dynamique, met a jour intensiteChoc. ajoute (position du choc, intensite du choc). Cela est necessaire pour la gestion 
	 * des sons d'impacts.
	 * 
	 * c-a-d que en entree on considere que (position,vitesse,w, acceleration) sont le vecteur position, le vecteur vitesse, la vitesse angulaire
	 *  et le vecteur acceleration de la bille immediatement avant le choc
	 * et en sortie (position,vitesse,w, acceleration) sont le vecteur position, le vecteur vitesse, la vitesse angulaire et le vecteur acceleration de 
	 * la bille immediatement apres le choc
	 * 
	 * si le choc est parfaitement dynamique, le vecteur vitesse et la vitesse angulaire sont modifies par la collision
	 *  (comme une boule de billard l'est par une bande)
	 * si le choc est parfaitement statique, le vecteur acceleration est modifie (on considere que le choc est elastique et une force de rappel 
	 * est appliquee a la bille)
	 * 
	 * @param position : vecteur position de la bille immediatement avant la collision avec le contour
	 * @param rayon : rayon de la bille
	 * @param vitesse : vecteur vitesse de la bille immediatement avant la collision avec le contour
	 * @param w : vitesse angulaire de la bille immediatement avant le choc
	 * @param acceleration : vecteur acceleration de la bille immediatement avant la collision avec le contour
	 * @param coeffFrottement : frottement de Coulomb contre la paroi tel que coeffFrottement >= 0.
	 * Si coeffFrottement == 0, la bille glisse parfaitement sur l'arc, ni la vitesse tangentielle vT, ni la vitesse angulaire ne sont modifiees
	 * Plus coeffFrottement augmente, plus on s'approche des conditions de roulement sans glisement. 
	 * On conseille de fixer le coeff entre 0 et 1 pour des conditions r�alistes.
	 *
	 * */

	public static boolean actionReactionBilleArcAvecRebondEtEffet( final Vecteur position, final double rayon, Vecteur vitesse, double w [], 
			Vecteur acceleration, final double masse, final double J, final double JplusMr2,
			final ArcCourbe arc, final double coeffFrottement, Vector<Choc> intensiteChoc)
	{
		Vecteur i = new Vecteur();
		Vecteur N = new Vecteur();
		double [] vNp = new double[1];
		double [] vT = new double[1];
		double [] vTplusrw = new double[1];

		int ok = actionReactionArcTousRebonds(position, rayon, vitesse, w, acceleration, masse, arc, intensiteChoc, i, N, vNp, vT, vTplusrw);

		if (ok == PAS_DE_CHOC) return false;
		else
			if (ok == CHOC_MOU) return true;
			else                              // choc dynamique
			{                                 //Collisions1.collisions1.setChanged(); 

				if ( Math.abs(vTplusrw[0]) <= coeffFrottement * (JplusMr2/J) * vNp[0])    // condition A de frottement (cf. frottement de Coulomb)
				{
					vT[0] -= 2 *             (J/JplusMr2) * vTplusrw[0];
					w[0]  -= 2 * rayon * (masse/JplusMr2) * vTplusrw[0];
				}

				else   // condition non A , ou de glissement (cf. frottements de Coulomb)
				{
					double beta = ( vTplusrw[0] > 0 ? -coeffFrottement : coeffFrottement);
					double deuxBeta = 2*beta;

					vT[0] += deuxBeta*vNp[0];
					w[0]  += deuxBeta*(masse*rayon/J)*vNp[0];
				}

				Vecteur vS = Vecteur.combinaisonLineaire(vT[0], i, vNp[0], N);
				vitesse.set(vS);  

				return true;
			}

	}                           // actionReactionBilleArcAvecRebondEtEffet




	//-----------------------------------------------------------------------------------------------------------------
	//---------------------------- A present gestion des collisions bille - bille -------------------------------------
	//-----------------------------------------------------------------------------------------------------------------


	/**
	 * Factorise une partie des calculs impliques dans la collision bille-bille.
	 * 
	 *  DONNEES : 
	 *  bille1 (position1, rayon1, vitesse1, w1, acceleration1, masse1)
	 *  bille2 (position2, rayon2, vitesse2, w2, acceleration2, masse2)
	 *  
	 *  @return PAS_DE_CHOC si il n'y pas de collision
	 *  
	 *  @return CHOC_MOU si il y a un choc mou (sans vitesse). Dans ce cas met a jour acceleration1 et acceleration2 
	 *  (ajoute aux 2 vecteurs une contribution)
	 *  Dans ce cas calcule aussi nG1G2 (distance entre les centres des 2 billes G1 et G2), a = (v1N-v2N), base {N,u}, v1T, v2T, 
	 *  deltavT = (v1T + r1*w1) - (v2T +r2*w2)
	 *  
	 *  @return CHOC_DYNAMIQUE si il y a un choc dynamique (suppose parfaitement elastique).
	 *  Dans ce cas calcule aussi nG1G2 (distance entre les centres des 2 billes G1 et G2), a = (v1N-v2N), base {N,u}, v1T, v2T, 
	 *  deltavT = (v1T + r1*w1) - (v2T +r2*w2), M
	 *  v1N, v2N composantes de v1 et v2 normales immediatement apres le choc
	 *  Dans ce cas, envoie aussi a un eventuel Observer un objet Choc (position du choc, intensite du choc) pour g�rer les sons d'impact
	 *  
	 *  CONVENTIONS :
	 *  
	 *  {N,u} est la base orthonormee directe du segment orient� [G1,G2]
	 *  telle que v1 = v1T*u + v1N * N et v2 = v2T*u + v2N * N
	 *  M = m1+m2
	 * 
	 * */

	public /*static*/ int actionReactionBilleBilleTousRebonds(
			final Vecteur position1, final double rayon1, final Vecteur vitesse1, final double w1[], Vecteur acceleration1, final double masse1,
			final Vecteur position2, final double rayon2, final Vecteur vitesse2, final double w2[], Vecteur acceleration2, final double masse2,
			double [] nG1G2, double [] a, Vecteur [] base, double [] v1T, double v2T[], double [] deltavT, double [] M, double [] v1Np, double [] v2Np)
	{
		Vecteur G1G2;
		double nG1G2_2;
		G1G2 = Vecteur.difference(position2, position1);
		nG1G2_2 = G1G2.normeCarree();

		double r = rayon1+rayon2;

		double r2 = r*r;

		if (nG1G2_2 >= r2) return PAS_DE_CHOC;           // on a donc nG1G2 >= rayon1 + rayon2, il n'y a donc pas de choc

		// � pr�sent nG1G2_2 < r2

		nG1G2[0] = Math.sqrt(nG1G2_2);

		// on a donc nG1G2 < r

		Vecteur N = base[0] = G1G2.produit(1 / nG1G2[0]);

		double v1N, v2N;

		v1N = vitesse1.produitScalaire(N);
		v2N = vitesse2.produitScalaire(N);

		a[0] = v1N - v2N;

		if (a[0] <= 0) return PAS_DE_CHOC;       // il n'y a pas de collision, les billes s'eloignent l'une de l'autre

		// A present,  a[0] > 0 et nG1G2 < r 

		Vecteur u = base[1] = N.rotationQuartDeTour();

		v1T[0] = vitesse1.produitScalaire(u);
		v2T[0] = vitesse2.produitScalaire(u);

		deltavT[0] = (v1T[0] + rayon1*w1[0]) - ( v2T[0]+ rayon2*w2[0]); 


		if (a[0] < EPSILON_CHOC_BILLE && Math.abs(deltavT[0]) < EPSILON_CHOC_BILLE) // le choc est mou car a[0] ~= 0
		{
			double e = r - nG1G2[0];       // e est la profondeur de la penetration d'une bille dans l'autre

			//double forceRappel = COEFF_ELASTICITE_BILLE * e;

			Vecteur a12, a21;
			//a12 = N.produit(forceRappel / masse2);
			//a21 = N.produit(-forceRappel / masse1);


			//a12 = N.produit(2*g);
			//a21 = N.produit(-2*g);

			a12 = N.produit(COEFF_BILLE_BILLE);
			a21 = N.produit(-COEFF_BILLE_BILLE);

			acceleration1.ajoute(a21);
			acceleration2.ajoute(a12);

			//double kk = e*rayon1*rayon2/r;
			//double d1 = kk/rayon1, d2 = kk/rayon2;

			//position1.ajoute(N.produit(-d1));// on remet la bille n�1 � l'ext�rieur de la bille n�2 avec une translation propotionnelle � 1/r1
			//position2.ajoute(N.produit(d2)); // on remet la bille n�2 � l'ext�rieur de la bille n�1 avec une translation propotionnelle � 1/r2

			//System.err.println("choc mou entre 2 billes: N = " + N +", d1 = " + d1 +", d2 = " + d2);
			return CHOC_MOU;
		}
		else        // � pr�sent, a[0] >= EPSILON_CHOC_BILLE et nG1G2 < r, le choc est donc dynamique
		{ 
			double m1 = masse1;
			double m2 = masse2;
			M[0] = m1+m2;

			double alfa = (m1-m2)/M[0];
			double deuxSurM = 2/M[0];

			v1Np[0] =          alfa * v1N + deuxSurM *m2 * v2N; 
			v2Np[0] = deuxSurM * m1 * v1N -         alfa * v2N;

			//intensit�Choc.add( a[0]);
			//intensit�Choc.add( new Choc( position1.somme(N.produit(rayon1)), a[0]));
			this.setChanged();
			this.notifyObservers(new Choc( position1.somme(N.produit(rayon1)), a[0]));
			return CHOC_DYNAMIQUE;
		}

	} //actionReactionBilleBilleTousRebonds


	/**
	 * gere la collision dynamique ou statique des 2 billes d�finies respectivement par 
	 * ( position1, rayon1, vitesse1, acceleration1, masse1 ) et par ( position2, rayon2, vitesse2, acceleration1, masse2)
	 * 
	 * si il n'y a pas de collision
	 * renvoie false et ne modifie rien
	 *
	 * si il y a collision statique :
	 * renvoie true et modifie  acceleration1 et acceleration2. (une force de rappel elastique est appliquee aux deux billes). 
	 * Ajoute une contribution aux 2 vecteurs acceleration.
	 * 
	 * si il y a collision dynamique :
	 * renvoie true  et calcule les nouveaux vecteurs vitesse  vitesse1 et vitesse2 immediatement apres le choc
	 *  Dans ce cas, envoie aussi a un eventuel Observer un objet Choc (position du choc, intensite du choc) pour gerer les sons d'impact
	 * 
	 * Les nouvelles accelerations ou les nouveaux vecteurs vitesses sont calcules pour representer l'etat des billes immediatement apres le choc  
	 * 
	 * */

	public static boolean actionReactionBilleBille( Vecteur position1, double rayon1, Vecteur vitesse1, Vecteur acceleration1, double masse1, 
			Vecteur position2, double rayon2, Vecteur vitesse2, Vecteur acceleration2, double masse2)   
	//modifie b1 et b2
	//donn�es : b1 et b2 avant le choc
	//r�sultats : b1 et b2 apr�s le choc
	{
		double [] w1 = {0};
		double [] w2 = {0};
		double [] nG1G2 = new double[1];
		double [] a = new double[1];
		Vecteur [] base = new Vecteur[2];
		double [] v1T = new double[1];
		double [] v2T = new double[1];
		double [] deltavT = new double[1];
		double [] M = new double[1];
		double [] v1Np = new double[1];
		double [] v2Np = new double[1];
		int ok = Collisions1.collisions1.actionReactionBilleBilleTousRebonds( position1, rayon1, vitesse1, w1, acceleration1, masse1, 
				position2, rayon2, vitesse2, w2, acceleration2, masse2, 
				nG1G2, a, base, v1T, v2T, deltavT, M, v1Np, v2Np);

		if (ok == PAS_DE_CHOC) return false;
		else
			if (ok == CHOC_MOU) return true;
			else
			{ 
				Vecteur v1S = Vecteur.combinaisonLineaire(v1Np[0], base[0], v1T[0], base[1]);
				Vecteur v2S = Vecteur.combinaisonLineaire(v2Np[0], base[0], v2T[0], base[1]);

				vitesse1.set(v1S);                  // vecteur vitesse de la bille 1 apres le choc
				vitesse2.set(v2S);                  // vecteur vitesse de la bille 2 apres le choc

				return true;
			}

	} // actionReactionBilleBille



	/**
	 * gere la collision dynamique ou statique des 2 billes b1 et b 2 definies respectivement par 
	 * ( position1, rayon1, vitesse1, w1, acceleration1, m1, J1, J1plusM1r12 ) et par 
	 * ( position2, rayon2, vitesse2, w2, acceleration2, m2, J2, J2plusM2r22)
	 * 
	 * Les billes tournent sur elles-memes !
	 * w1 est la vitesse angulaire
	 * J1 est le moment d'inertie
	 * J1plusM1r12 = J1 + m1*r1^2
	 * 
	 * meme chose pour b2
	 * 
	 * si il n'y a pas de collision
	 * renvoie false et ne modifie rien
	 *
	 * si il y a collision statique :
	 * renvoie true et modifie  acceleration1 et acceleration2. (une force de rappel elastique est appliquee aux deux billes). 
	 * Ajoute une contribution aux 2 vecteurs acceleration.
	 * 
	 * si il y a collision dynamique :
	 * renvoie true  et calcule les nouveaux vecteurs vitesse  vitesse1 et vitesse2 et les nouvelles vitesses angulaires w1 et w2 
	 * imm�diatement apres le choc
	 *  Dans ce cas, envoie aussi a un eventuel Observer un objet Choc (position du choc, intensite du choc) pour gerer les sons d'impact
	 * 
	 * Les nouvelles accelerations ou les nouveaux vecteurs vitesses et vitesses angulaires w1 et w2 sont calcules
	 *  pour representer l'etat des billes imm�diatement apres le choc
	 *  
	 * @param coeffFrottement : frottement de Coulomb entre 2 billes  tel que coeffFrottement >= 0.
	 * Si coeffFrottement == 0, les billes glissent parfaitement l'une sur l'autre, ni les vitesses tangentielles v1T, v2T,
	 *  ni les vitesses angulaires w1 et w2  ne sont modifi�es
	 * Plus coeffFrottement augmente, plus on s'approche des conditions de roulement sans glisement. 
	 * On conseille de fixer le coeff entre 0 et 1 pour des conditions r�alistes.
	 * 
	 * */

	public static boolean actionReactionBilleBilleAvecEffet( 
			Vecteur position1, double rayon1, Vecteur vitesse1, double w1[], Vecteur acceleration1, double m1, final double J1, final double J1plusM1r12,
			Vecteur position2, double rayon2, Vecteur vitesse2, double w2[], Vecteur acceleration2, double m2, final double J2, final double J2plusM2r22,
			final double coeffFrottement)   
	//modifie b1 et b2
	//donnees : b1 et b2 avant le choc
	//resultats : b1 et b2 apres le choc
	{
		////System.err.println("actionReactionBilleBilleAvecEffet, debut ");

		double [] nG1G2 = new double[1];
		double [] a = new double[1];
		Vecteur [] base = new Vecteur[2];
		double [] v1T = new double[1];
		double [] v2T = new double[1];
		double [] deltavT = new double[1];
		double [] M = new double[1];
		double [] v1Np = new double[1];
		double [] v2Np = new double[1];
		int ok = Collisions1.collisions1.actionReactionBilleBilleTousRebonds( position1, rayon1, vitesse1, w1, acceleration1, m1, 
				position2, rayon2, vitesse2, w2, acceleration2, m2, 
				nG1G2, a, base, v1T, v2T, deltavT, M, v1Np, v2Np);

		if (ok == PAS_DE_CHOC) return false;
		else
			if (ok == CHOC_MOU) return true;
			else
			{                                     // choc dynamique
				double ki = J1plusM1r12/(J1*m1) + J2plusM2r22/(J2*m2);
				double mu = 1/ki;
				double m1m2 = m1*m2;

				double r1SurJ1 = rayon1/J1;
				double r2SurJ2 = rayon2/J2;

				if ( Math.abs(deltavT[0]) <= coeffFrottement * m1m2 /(M[0]*mu) * Math.abs(a[0]) ) // il y a frottement (cf. frottement de coulomb)
				{
					double deuxmuvT = 2*mu*deltavT[0];
					v1T[0] -= deuxmuvT/m1;
					w1[0]  -= deuxmuvT*r1SurJ1;
					v2T[0] += deuxmuvT/m2;
					w2[0]  += deuxmuvT*r2SurJ2;
				}


				else                                          // il y a glissement (cf. frottement de Coulomb)
				{


					double beta = ( deltavT[0] > 0 ? coeffFrottement : -coeffFrottement );

					double x = 2*beta*(-a[0])/M[0];        // x = 2 * beta * (vN2 - vN1)/m    o� m = m1+m2

					double xm1m2 = x*m1m2;

					v1T[0] += x*m2;
					w1[0]  += xm1m2*r1SurJ1;
					v2T[0] -= x*m1;
					w2[0]  -= xm1m2*r2SurJ2;
				}                          // roulement pur

				Vecteur v1S = Vecteur.combinaisonLineaire(v1Np[0], base[0], v1T[0], base[1]);
				Vecteur v2S = Vecteur.combinaisonLineaire(v2Np[0], base[0], v2T[0], base[1]);

				vitesse1.set(v1S);                  // vecteur vitesse de la bille 1 apr�s le choc
				vitesse2.set(v2S);                  // vecteur vitesse de la bille 2 apr�s le choc

				return true;
			}                     // choc dynamique

	} // actionReactionBilleBilleAvecEffet

} // Collisions1


