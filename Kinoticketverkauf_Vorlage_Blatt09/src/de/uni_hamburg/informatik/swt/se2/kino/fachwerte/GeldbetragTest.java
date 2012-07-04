package de.uni_hamburg.informatik.swt.se2.kino.fachwerte;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GeldbetragTest {
	
	@Test
	public void testeCentUndEuro()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(1337, 42);
		
		assertEquals(1337, foo.getEuro());
		assertEquals(42, foo.getCent());
	}
	
	@Test
	public void testeCentUeberlauf()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(42, 1337);
		
		assertEquals(55, foo.getEuro());
		assertEquals(37, foo.getCent());
	}
	
	@Test
	public void testeAddition()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(1337, 42);
		Geldbetrag bar = Geldbetrag.neuerGeldbetrag(662, 58);
		
		Geldbetrag baz = foo.addiereBetrag(bar);
		
		assertEquals(2000, baz.getEuro());
		assertEquals(0, baz.getCent());
	}
	
	@Test
	public void testeSubtraktion()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(1337, 42);
		Geldbetrag bar = Geldbetrag.neuerGeldbetrag(37, 21);
		
		Geldbetrag baz = foo.subtrahiereBetrag(bar);
		
		assertEquals(1300, baz.getEuro());
		assertEquals(21, baz.getCent());
	}
	
	@Test
	public void testeNegativeSubtraktion()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(0, 0);
		Geldbetrag bar = Geldbetrag.neuerGeldbetrag(1337, 42);
		
		Geldbetrag baz = foo.subtrahiereBetrag(bar);
		
		assertEquals(-1337, baz.getEuro());
		assertEquals(42, baz.getCent());
	}
	
	@Test
	public void testeNegativeWerte()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(-1337, 42);
		
		assertEquals(-1337, foo.getEuro());
		assertEquals(42, foo.getCent());
	}
	
	@Test
	public void testeSkalierung()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(1234, 12);
		
		Geldbetrag baz = foo.skaliere(2);
		
		assertEquals(2468, baz.getEuro());
		assertEquals(24, baz.getCent());
	}
	
	@Test
	public void testeNegativeSkalierung()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(1234, 12);
		
		Geldbetrag baz = foo.skaliere(-2);
		
		assertEquals(-2468, baz.getEuro());
		assertEquals(24, baz.getCent());
	}
	
	@Test
	public void testeFormatierung()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(1234, 12);
		assertEquals("1234,12 €", foo.formatiere());
		
		foo = Geldbetrag.neuerGeldbetrag(1234, 2);
		assertEquals("1234,02 €", foo.formatiere());
	}
	
	@Test
	public void testeStringInterpretation()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag("€ 2400,42");
		
		assertEquals(2400, foo.getEuro());
		assertEquals(42, foo.getCent());
		
		foo = Geldbetrag.neuerGeldbetrag("+13,37");
		
		assertEquals(13, foo.getEuro());
		assertEquals(37, foo.getCent());
		
		foo = Geldbetrag.neuerGeldbetrag("-13,37");

		assertEquals(-13, foo.getEuro());
		assertEquals(37, foo.getCent());
	}

}
