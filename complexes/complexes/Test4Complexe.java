package complexes;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Test4Complexe
{

@Before
public void setUp() throws Exception
{}

@After
public void tearDown() throws Exception
{}

@Test
public final void testSomme()
{
Complexe z1, z2;
z1 = new Complexe(12,57);
z2 = new Complexe(88,43);
Complexe z3 = z1.somme(z2);
Assert.assertEquals(100, z3.getX(), Double.MIN_NORMAL);
Assert.assertEquals(100, z3.getY(), Double.MIN_NORMAL);
}

@Test
public final void testDiff�rence()
{
fail("Not yet implemented"); // TODO
}

@Test
public final void testProduitComplexe()
{
fail("Not yet implemented"); // TODO
}

@Test
public final void testProduitDouble()
{
fail("Not yet implemented"); // TODO
}

@Test
public final void testInverse()
{
fail("Not yet implemented"); // TODO
}

}
