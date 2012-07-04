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
	 * @return
	 * 		Ein Geldbetrag-Fachwert.
	 * @ensure result != null
	 */
	public static Geldbetrag neuerGeldbetrag(int euro, int cent)
	{
		int gesamtCent;
		if (euro < 0)
			gesamtCent = euro * 100 - Math.abs(cent);
		else
			gesamtCent = euro * 100 + Math.abs(cent);
			
		if (!_werteListe.containsKey(gesamtCent))
			_werteListe.put(gesamtCent, new Geldbetrag(gesamtCent));
		return _werteListe.get(gesamtCent);
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
		
		return neuerGeldbetrag(euro, cent);
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
		return Math.abs(_centbetrag % 100);
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
		return Geldbetrag.neuerGeldbetrag(getEuro() + betrag.getEuro(), getCent() + betrag.getCent());
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
		return Geldbetrag.neuerGeldbetrag(getEuro() - betrag.getEuro(), getCent() - betrag.getCent());
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
		if (getCent() < 10)
			return getEuro() + ",0" + getCent() + " €";
		else
			return getEuro() + "," + getCent() + " €";
	}
	
	@Override
    public boolean equals(Object o)
    {
        boolean ergebnis = false;
        if (o instanceof Geldbetrag)
        {
        	Geldbetrag betrag = (Geldbetrag) o;
            ergebnis = ((betrag.getEuro() == this.getEuro()) 
            		&& (betrag.getCent() == this.getCent()));
        }
        return ergebnis;
    }

    @Override
    public int hashCode()
    {
        return _centbetrag;
    }

    @Override
    public String toString()
    {
        return formatiere();
    }
}
