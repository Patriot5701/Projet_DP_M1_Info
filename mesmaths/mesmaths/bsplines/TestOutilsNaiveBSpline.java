package mesmaths.bsplines;

public class TestOutilsNaiveBSpline
{

public static void main(String[] args)
{
// TODO Auto-generated method stub
double t1 = 0.5;

double y1 = OutilsNaiveBSpline.NaiveNo4Normalisee(t1);

System.out.println("y1 = " + y1);

FoncteurNaiveNo4 foncteurNaiveNo4 = new FoncteurNaiveNo4(5,  3);

double t2 = 2.5;
double y2 = foncteurNaiveNo4.calcule(t2);
System.out.println("y2 = " + y2);


}

}
