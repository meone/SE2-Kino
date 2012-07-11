package de.uni_hamburg.informatik.swt.se2.kino.fachwerte;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class GeldbetragTest {
	
	@Test
	public void testeEuroCent()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(-133742);
		
		assertEquals(1337, foo.getEuro());
		assertEquals(42, foo.getCent());
		assertEquals(true, foo.getSigned());
	}
	
	@Test
	public void testeCentUndEuro()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(1337, 42, false);
		
		assertEquals(1337, foo.getEuro());
		assertEquals(42, foo.getCent());
		assertEquals(false, foo.getSigned());
	}
	
	@Test
	public void testeCentUeberlauf()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(42, 1337, false);
		
		assertEquals(55, foo.getEuro());
		assertEquals(37, foo.getCent());
		assertEquals(false, foo.getSigned());
	}
	
	@Test
	public void testeAddition()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(1337, 42, false);
		Geldbetrag bar = Geldbetrag.neuerGeldbetrag(662, 58, false);
		
		Geldbetrag baz = foo.addiereBetrag(bar);
		
		assertEquals(2000, baz.getEuro());
		assertEquals(0, baz.getCent());
		assertEquals(false, baz.getSigned());
	}
	
	@Test
	public void testeSubtraktion()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(1337, 42, false);
		Geldbetrag bar = Geldbetrag.neuerGeldbetrag(37, 21, false);
		
		Geldbetrag baz = foo.subtrahiereBetrag(bar);
		
		assertEquals(1300, baz.getEuro());
		assertEquals(21, baz.getCent());
		assertEquals(false, baz.getSigned());
	}
	
	@Test
	public void testeNegativeSubtraktion()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(0, 0, false);
		Geldbetrag bar = Geldbetrag.neuerGeldbetrag(1337, 42, false);
		
		Geldbetrag baz = foo.subtrahiereBetrag(bar);
		
		assertEquals(1337, baz.getEuro());
		assertEquals(42, baz.getCent());
		assertEquals(true, baz.getSigned());
	}
	
	@Test
	public void testeNegativeWerte()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(-1337, 42, true);
		
		assertEquals(1337, foo.getEuro());
		assertEquals(42, foo.getCent());
		assertEquals(true, foo.getSigned());
	}
	
	@Test
	public void testeSkalierung()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(1234, 12, false);
		
		Geldbetrag baz = foo.skaliere(2);
		
		assertEquals(2468, baz.getEuro());
		assertEquals(24, baz.getCent());
		assertEquals(false, foo.getSigned());
	}
	
	@Test
	public void testeNegativeSkalierung()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(1234, 12, false);
		
		Geldbetrag baz = foo.skaliere(-2);
		
		assertEquals(2468, baz.getEuro());
		assertEquals(24, baz.getCent());
		assertEquals(true, baz.getSigned());
	}
	
	@Test
	public void testeFormatierung()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(-123412);
		assertEquals("-1234,12 €", foo.getFormatiertenString());
		
		foo = Geldbetrag.neuerGeldbetrag(1234, 2, false);
		assertEquals("1234,02 €", foo.getFormatiertenString());
	}
	
	@Test
	public void testeStringInterpretation()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag("€ 2400,42");
		
		assertEquals(2400, foo.getEuro());
		assertEquals(42, foo.getCent());
		assertEquals(false, foo.getSigned());
		
		assertFalse(Geldbetrag.istGueltigerGeldbetrag("€ 200,1"));
		
		foo = Geldbetrag.neuerGeldbetrag("+13,37");
		
		assertEquals(13, foo.getEuro());
		assertEquals(37, foo.getCent());
		assertEquals(false, foo.getSigned());
		
		foo = Geldbetrag.neuerGeldbetrag("-13,37");

		assertEquals(13, foo.getEuro());
		assertEquals(37, foo.getCent());
		assertEquals(true, foo.getSigned());
	}
	
	@Test
	public void testeVergleiche()
	{
		Geldbetrag foo = Geldbetrag.neuerGeldbetrag(1337);
		Geldbetrag bar = Geldbetrag.neuerGeldbetrag(-42);
		
		assertEquals(true, foo.istEchtGroesserAls(bar));
		assertEquals(false, foo.istEchtGroesserAls(foo));
		assertEquals(true, foo.istGroesserGleichAls(bar));
		assertEquals(true, foo.istGroesserGleichAls(foo));
	}

}
