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
	 * 		Der Euro-Anteil. Er gibt auch das Vorzeichen vor.
	 * @param cent
	 * 		Der Cent-Anteil. Er ist positiv.
	 * @param signed
	 * 		Vorzeichen des Betrags. True, falls Geldbetrag negativ.
	 * @return
	 * 		Ein Geldbetrag-Fachwert.
	 * @ensure result != null
	 */
	public static Geldbetrag neuerGeldbetrag(int euro, int cent, boolean signed)
	{
		int gesamtCent;
		gesamtCent = Math.abs(euro) * 100 + Math.abs(cent);
		if (signed)
			gesamtCent *= -1;
			
		return neuerGeldbetrag(gesamtCent);
	}
	
	/**
	 * Erstellt einen neuen Geldbetrag.
	 * 
	 * @param eurocent
	 * 		Der Gesamtbetrag in Cent.
	 * @ensure result != null
	 */
	public static Geldbetrag neuerGeldbetrag(int eurocent)
	{
		if (!_werteListe.containsKey(eurocent))
			_werteListe.put(eurocent, new Geldbetrag(eurocent));
		return _werteListe.get(eurocent);
	}
	
	/**
	 * Erstellt einen neuen Fachwert für einen Geldbetrag anhand eines Strings.
	 * 
	 * @param formatierterString
	 * 		Ein währungsformatierter Text. Ein Euro-Zeichen kann am Anfang oder 
	 * 		Ende stehen. Ein Vorzeichen ist möglich.
	 * @return
	 * 		Ein Fachwert für den Betrag.
	 * @ensure result != null
	 */
	public static Geldbetrag neuerGeldbetrag(String formatierterString)
	{
		Pattern rePattern = Pattern.compile("^(€\\s*)?([+\\-]?\\s*\\d+)(,(\\d+))?(\\s*€)?$");
		Matcher matcher = rePattern.matcher(formatierterString);
		
		if (!matcher.find())
			throw new InvalidParameterException("Es wurde kein gültiger Geldbetrag eingegeben");
		
		int euro = Integer.parseInt(matcher.group(2));
		int cent = Integer.parseInt(matcher.group(4));
		
		return neuerGeldbetrag(euro, cent, euro < 0);
	}
	
	/**
	 * Gibt den vollwertigen Eurobetrag an. 
	 */
	int getEuro()
	{
		return Math.abs(_centbetrag / 100);
	}
	
	/**
	 * Gibt den reinen Centbetrag an. 
	 */
	int getCent()
	{
		return Math.abs(_centbetrag % 100);
	}
	
	/**
	 * Gibt zurück, ob der Betrag negativ ist.
	 */
	boolean getSigned()
	{
		return _centbetrag < 0;
	}
	
	/**
	 * Gibt den Betrag in Eurocent zurück.
	 */
	int getEuroCent()
	{
		return _centbetrag;
	}
	
	/**
	 * Addiert zu einem Geldbetrag einen anderen.
	 * 
	 * @param betrag
	 * 		Der zu addierende Betrag.
	 * @return
	 * 		Das Ergebnis.
	 * @ensure result != null
	 */
	public Geldbetrag addiereBetrag(Geldbetrag betrag)
	{
		return Geldbetrag.neuerGeldbetrag(getEuroCent() + betrag.getEuroCent());
	}
	
	/**
	 * Subtrahiert von einem Geldbetrag einen anderen.
	 * 
	 * @param betrag
	 * 		Der abzuziehende Betrag.
	 * @return
	 * 		Das Ergebnis.
	 * @ensure result != null
	 */
	public Geldbetrag subtrahiereBetrag(Geldbetrag betrag)
	{
		return Geldbetrag.neuerGeldbetrag(getEuroCent() - betrag.getEuroCent());
	}
	
	/**
	 * Multipliziert einen Geldbetrag mit einem Skalar.
	 * 
	 * @param skalar
	 * 		Der Skalar, mit dem Multipliziert wird.
	 * @return
	 * 		Das Ergebnis.
	 * @ensure result != null
	 */
	public Geldbetrag skaliere(int skalar)
	{
		return Geldbetrag.neuerGeldbetrag(skalar * getEuroCent());
	}
	
	/**
	 * Formatiert den Geldbetrag und gibt ihn als String EE,CC € zurück.
	 * 
	 * @return
	 * 		Formatierter String, der den Betrag repräsentiert.
	 */
	public String getFormatiertenString()
	{
		return getEuro() + "," + getCent() + " €";
	}
}
