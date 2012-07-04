package de.uni_hamburg.informatik.swt.se2.kino.fachwerte;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Fachwert zur Speicherung von Geldbeträgen. Die Klasse verfügt
 * über weitreichende arithmetische Operationen, welche die 
 * Handhabung von finanziellen Aktionen im Programmkontext simuliert.
 * 
 * @author 1fasselt, 1foerste, 1meineck, 1kmoelle
 */
public final class Geldbetrag {
	
	/**
	 * Intern gespeicerter Centbetrag.
	 */
	private final int _centbetrag;
	
	/**
	 * Liste aller auftretenden Geldbeträge.
	 */
	private static HashMap<Integer, Geldbetrag> _werteListe = new HashMap<Integer, Geldbetrag>();
	
	/**
	 * Erstellt einen Fachwert.
	 */
	private Geldbetrag(int gesamtCent)
	{
		_centbetrag = gesamtCent;
	}
	
	/**
	 * Erstellt einen neuen Geldbetrag.
	 * 
	 * @param euro
	 * 		Der Euro-Anteil.
	 * @param cent
	 * 		Der Cent-Anteil.
	 * @return
	 * 		Ein Geldbetrag-Fachwert.
	 */
	public static Geldbetrag neuerGeldbetrag(int euro, int cent)
	{
		int gesamtCent = cent + euro * 100;
		if (_werteListe.containsKey(gesamtCent))
			return _werteListe.get(gesamtCent);
		else
			return _werteListe.put(gesamtCent, new Geldbetrag(gesamtCent));
	}
	
	/**
	 * Gibt vollwertige Euro an. 
	 */
	public int getEuro()
	{
		return _centbetrag / 100;
	}
	
	/**
	 * Gibt den reinen Centbetrag an. 
	 */
	public int getCent()
	{
		return _centbetrag % 100;
	}
	
	/**
	 * Addiert zu einem Geldbetrag einen anderen.
	 * 
	 * @param betrag
	 * 		Der zu addierende Betrag.
	 * @return
	 * 		Das Ergebnis.
	 */
	public Geldbetrag addiereBetrag(Geldbetrag betrag)
	{
		return Geldbetrag.neuerGeldbetrag(getEuro() + betrag.getEuro(), getCent() + betrag.getCent());
	}
	
	/**
	 * Subtrahiert von einem Geldbetrag einen anderen.
	 * 
	 * @param betrag
	 * 		Der abzuziehende Betrag.
	 * @return
	 * 		Das Ergebnis.
	 */
	public Geldbetrag subtrahiereBetrag(Geldbetrag betrag)
	{
		return Geldbetrag.neuerGeldbetrag(getEuro() - betrag.getEuro(), getCent() - betrag.getCent());
	}
	
	/**
	 * Multipliziert einen Geldbetrag mit einem Skalar.
	 * 
	 * @param skalar
	 * 		Der Skalar, mit dem Multipliziert wird.
	 * @return
	 * 		Das Ergebnis.
	 */
	public Geldbetrag skaliere(int skalar)
	{
		return Geldbetrag.neuerGeldbetrag(getEuro() * skalar, getCent() * skalar);
	}
	
	/**
	 * Formatiert den Geldbetrag und gibt ihn als String EE,CC € zurück.
	 * 
	 * @return
	 * 		Formatierter String, der den Betrag repräsentiert.
	 */
	public String formatiere()
	{
		return getEuro() + "," + getCent() + " €";
	}
	
	public static Geldbetrag neuerGeldbetrag(String formatierterString)
	{
		Pattern rePattern = Pattern.compile("/^(€\\w*)?(\\d+)(,(\\d+))?(\\w*€)?$/");
		Matcher matcher = rePattern.matcher(formatierterString);
		
		if (!matcher.find())
			throw new InvalidParameterException("Es wurde kein gültiger Geldbetrag eingegeben");
		
		int euro = Integer.parseInt(matcher.group(1));
		int cent = Integer.parseInt(matcher.group(3));
		
		return neuerGeldbetrag(euro, cent);
	}
	
}
