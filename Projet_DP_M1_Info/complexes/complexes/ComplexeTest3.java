package complexes;

import junit.framework.Assert;
import junit.framework.TestCase;
import reels.*;

/**
 * 
 * d�di�e au test des m�thodes de la classe Complexe
 * 
 * */
public class ComplexeTest3 extends TestCase
{
Complexe x1, x2, x3, x4;
double eps;


public ComplexeTest3(String name)
{
super(name);

}

protected void setUp() throws Exception
{
super.setUp();
x1 = new Complexe(5,7);
x2 = new Complexe(-2,3);
x3 = new Complexe(3,10);
x4 = new Complexe(-31,1);
eps = 1.E-6;

}

protected void tearDown() throws Exception
{
super.tearDown();
}

public final void testComplexe()
{
Assert.assertTrue(0 == new Complexe().getX());
Assert.assertTrue(0 == new Complexe().getY());
}

public final void testComplexeDouble()
{
double x = Reel.random();
Assert.assertTrue(x == new Complexe(x).getX());
Assert.assertTrue(0 == new Complexe(x).getY());
}

public final void testComplexeDoubleDouble()
{
double x, y;
x = Reel.random();
y = Reel.random();

Assert.assertTrue(x == new Complexe(x,y).getX());
Assert.assertTrue(y == new Complexe(x,y).getY());

}

public final void testGetX()
{
double x, y;
x = Reel.random();
y = Reel.random();

Assert.assertTrue(x == new Complexe(x,y).getX());
}

public final void testGetY()
{
double x, y;
x = Reel.random();
y = Reel.random();

Assert.assertTrue(y == new Complexe(x,y).getY());
}

public final void testSomme()
{
Complexe z1 = new Complexe(Reel.random(),Reel.random()); // z1 est quelconque
Complexe z2 = new Complexe(Reel.random(),Reel.random()); // z2 est quelconque

System.out.println(" \n testSomme() : ");


System.out.println("Re(z1+z2) = "+z1.somme(z2).getX());
System.out.println("Re(z1)+Re(z2) = "+(z1.getX()+z2.getX()));
System.out.println("Im(z1+z2) = "+z1.somme(z2).getY());
System.out.println("Im(z1)+Im(z2) = "+(z1.getY()+z2.getY()));

Assert.assertTrue( z1.somme(z2).getX()==z1.getX()+z2.getX()&& 
                   z1.somme(z2).getY()== z1.getY()+z2.getY());


}

public final void testProduit()
{
Complexe z1 = new Complexe(Reel.random(),Reel.random()); // z1 est quelconque
Complexe z2 = new Complexe(Reel.random(),Reel.random()); // z2 est quelconque


System.out.println("\n testProduit() : ");

System.out.println(" |z1*z2| == "+z1.produit(z2).module());
System.out.println(" |z1|*|z2| == "+z1.module()*z2.module());

System.out.println(" arg(z1*z2) == "+z1.produit(z2).arg());
System.out.println(" arg(z1)+arg(z2) == "+(z1.arg()+z2.arg()));

double n = ( z1.arg()+z2.arg() -  z1.produit(z2).arg())/(2*Math.PI);

double ct1,ct2,st1,st2;

ct1 = Math.cos(z1.arg()+z2.arg());
st1 = Math.sin(z1.arg()+z2.arg());

ct2 = Math.cos(z1.produit(z2).arg());
st2 = Math.sin(z1.produit(z2).arg());

System.out.println(" n == "+n);


Assert.assertEquals( ct1,ct2,eps);

Assert.assertEquals( st1,st2,eps);


}

public final void testConjugu�()
{

Complexe z = new Complexe(Reel.random(), Reel.random()); // z1 est quelconque

Assert.assertTrue(z.getX() ==  z.conjugu�().getX() &&
                  z.getY() == -z.conjugu�().getY());
}

public final void testOppos�()
{
Complexe z1 = new Complexe(Reel.random(), Reel.random()); // z1 est quelconque

Assert.assertTrue(z1.oppos�().getX() == -z1.getX() && z1.oppos�().getY() == -z1.getY());

}

public final void testModule()
{
Complexe z1 = new Complexe(Reel.random(), Reel.random()); // z1 est quelconque
double mz, mz21, mz22;
mz = z1.module();
mz21 = z1.module2();
mz22 = mz*mz;

System.out.println("\n testModule() : ");


System.out.println(" |z1| == "+mz);
System.out.println(" |z1|^2 == "+mz21);
System.out.println(" |z1|*|z1| == "+mz22);

Assert.assertEquals(mz22,mz21,eps*mz21);

}

public final void testArg()
{
double r, theta;

r = 1/Math.random(); // r > 0
theta = 2*Math.PI*(Math.random()-0.5); // -PI <= theta < PI
Complexe z;

z = new Complexe(r*Math.cos(theta),r*Math.sin(theta));


System.out.println("\n testArg(): ");

System.out.println(" r == "+r);
System.out.println(" |z1| == "+z.module());
System.out.println(" theta == "+theta);
System.out.println(" arg z1 == "+z.arg());

Assert.assertEquals(r,z.module(),eps*r);
//Assert.assertEquals(theta, z.arg(), eps*theta);
Assert.assertEquals(Math.cos(theta), Math.cos(z.arg()), eps);
Assert.assertEquals(Math.sin(theta), Math.sin(z.arg()), eps);

/*
Assert.assertTrue(r == z.module());
Assert.assertTrue(theta == z.arg());
*/

}


public final void testModule2()
{
Complexe z1 = new Complexe(Reel.random(), Reel.random()); // z1 est quelconque

Assert.assertTrue(z1.module2() == z1.getX()*z1.getX() + z1.getY()*z1.getY());
}

public final void testEqualsObject()
{
Complexe z = new Complexe(Reel.random(), Reel.random()); // z est quelconque
Complexe zbar = z.conjugu�();
Complexe zzbar = z.produit(zbar);
double m = z.module2();
Complexe mz2 = new Complexe(m);
Complexe s = z.somme(zbar);
Complexe d = z.diff�rence(zbar);
Complexe x2, yi2;

x2 = Complexe.un.produit(2*z.getX());
yi2  = Complexe.i.produit(2*z.getY());

System.out.println("\n testEqualsObject() : ");

System.out.println(" z*zbar = "+ zzbar);
System.out.println(" |z|^2 = " + mz2);
System.out.println(" z + zbar =  "+ s);
System.out.println(" 2x =  "+ x2);
System.out.println(" z - zbar =  "+ d);
System.out.println(" 2iy =  "+ yi2);




//Assert.assertEquals(new Complexe(3,2), new Complexe(3,2));

System.out.println("new Complexe(3,2).equals(new Complexe(3,2)) : "+new Complexe(3,2).equals(new Complexe(3,2)));
Assert.assertTrue(new Complexe(3,2).equals(new Complexe(3,2)));
Assert.assertEquals(s, x2);
Assert.assertEquals(d, yi2);



}

public final void testToString()
{
System.out.println(" testToString(): ");

System.out.println(" 3+2*i == "+new Complexe(3,2));

Assert.assertEquals("3.0+2.0*i", new Complexe(3,2).toString());
}

}
