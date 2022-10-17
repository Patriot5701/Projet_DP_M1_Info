package mesmaths.cinematique.debugage;


public class RapportErreurApres implements RapportErreur
{
	double mvT2; double jw2; double[] vNp;
	double vTp; double w;

	public RapportErreurApres(double mvT2, double jw2, double[] vNp, double vTp,
			double w)
	{
		super();
		this.mvT2 = mvT2;
		this.jw2 = jw2;
		this.vNp = vNp;
		this.vTp = vTp;
		this.w = w;
	}

	/* (non-Javadoc)
	 * @see mesmaths.cinematique.debugage.RapportErreur#toString()
	 */

	@Override
	public String toString()
	{
		return "RapportErreurApr�s [mvT2 = " + this.mvT2 + ", jw2 = " + this.jw2 + ", vNp = "
				+ this.vNp[0] + ", vTp = " + this.vTp + ", w = "
				+ this.w + "]";
	}

}
