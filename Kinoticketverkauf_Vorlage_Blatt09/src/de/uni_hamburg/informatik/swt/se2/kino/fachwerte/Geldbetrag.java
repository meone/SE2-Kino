package de.uni_hamburg.informatik.swt.se2.kino.fachwerte;

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
	 * Pattern zum Prüfen von Geldbeträgen.
	 */
	private static Pattern _pattern = Pattern.compile("^(€\\s*)?([+\\-]?\\s*\\d+)(,(\\d{2}))?(\\s*€)?$");
	
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
	 * 
	 * @require istGueltigerGeldbetrag(formatierterString)
	 * @ensure result != null
	 */
	public static Geldbetrag neuerGeldbetrag(String formatierterString)
	{
		assert istGueltigerGeldbetrag(formatierterString) : "Vorbedingung verletzt: istGueltigerGeldbetrag(formatierterString)";
		Matcher matcher = _pattern.matcher(formatierterString);
		matcher.find();
		
		int euro = Integer.parseInt(matcher.group(2));
		
		String stringCent = matcher.group(4);
		int cent = 0;
		if(stringCent != null)
		    cent = Integer.parseInt(stringCent);
		
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
	 * 
	 * @require istAddierenMoeglich(betrag)
	 */
	public Geldbetrag addiereBetrag(Geldbetrag betrag)
	{
		assert istAddierenMoeglich(betrag) : "Vorbedingung verletzt: istAddierenMoeglich(betrag)";
		 
		return Geldbetrag.neuerGeldbetrag(getEuroCent() + betrag.getEuroCent());
	}
	
	/**
	 * Prüft, ob das Addieren eines Betrags möglich ist.
	 * 
	 * @param betrag
	 * 		Der zu addierende Betrag.
	 * @return
	 * 		Das Ergebnis der Prüfung.
	 */
	public boolean istAddierenMoeglich(Geldbetrag betrag)
	{
		return ((long) getEuroCent() + betrag.getEuroCent() <= Integer.MAX_VALUE) 
				&& ((long) getEuroCent() + betrag.getEuroCent() >= Integer.MIN_VALUE); 
	}
	
	/**
	 * Subtrahiert von einem Geldbetrag einen anderen.
	 * 
	 * @param betrag
	 * 		Der abzuziehende Betrag.
	 * @return
	 * 		Das Ergebnis.
	 * 
	 * @require istSubtrahierenMoeglich(betrag)
	 * @ensure result != null
	 */
	public Geldbetrag subtrahiereBetrag(Geldbetrag betrag)
	{
		assert istSubtrahierenMoeglich(betrag) : "Vorbedingung verletzt: istSubtrahierenMoeglich(betrag)";
		
		return Geldbetrag.neuerGeldbetrag(getEuroCent() - betrag.getEuroCent());
	}

	/**
	 * Prüft, ob das Subtrahieren eines Betrags möglich ist.
	 * 
	 * @param betrag
	 * 		Der zu subtrahierende Betrag.
	 * @return
	 * 		Das Ergebnis der Prüfung.
	 */
	public boolean istSubtrahierenMoeglich(Geldbetrag betrag)
	{
		return ((long) getEuroCent() - betrag.getEuroCent() <= Integer.MAX_VALUE) 
				&& ((long) getEuroCent() - betrag.getEuroCent() >= Integer.MIN_VALUE); 
	}
	
	/**
	 * Multipliziert einen Geldbetrag mit einem Skalar.
	 * 
	 * @param skalar
	 * 		Der Skalar, mit dem Multipliziert wird.
	 * @return
	 * 		Das Ergebnis.
	 * 
	 * @require istSkalierenMoeglich(skalar)
	 * @ensure result != null
	 */
	public Geldbetrag skaliere(int skalar)
	{
		assert istSkalierenMoeglich(skalar) : "Vorbedingung verletzt: istSkalierenMoeglich(skalar)";
		
		return Geldbetrag.neuerGeldbetrag(skalar * getEuroCent());
	}

	/**
	 * Prüft, ob das Skalieren eines Betrags möglich ist.
	 * 
	 * @param skalar
	 * 		Der Faktor, um den skaliert wird.
	 * @return
	 * 		Das Ergebnis der Prüfung.
	 */
	public boolean istSkalierenMoeglich(int skalar)
	{
		return ((long) getEuroCent() * skalar <= Integer.MAX_VALUE) 
				&& ((long) getEuroCent() * skalar >= Integer.MIN_VALUE); 
	}
	
	/**
	 * Formatiert den Geldbetrag und gibt ihn als String EE,CC € zurück.
	 * 
	 * @return
	 * 		Formatierter String, der den Betrag repräsentiert.
	 */
	public String getFormatiertenString()
	{
		if (getCent() < 10)
			return (getSigned() ? "-" : "") + getEuro() + ",0" + getCent() + " €";
		else
			return (getSigned() ? "-" : "") + getEuro() + "," + getCent() + " €";
	}
	
	/**
	 * Prüft einen String darauf, ob er einen gültigen Geldbetrag repräsentiert.
	 * 
	 * @param subjekt
	 * 		Der zu prüfende String.
	 * @return
	 */
	public static boolean istGueltigerGeldbetrag(String subjekt)
	{
		Matcher matcher = _pattern.matcher(subjekt);
		if (matcher.find())
		{
			long euro = Long.parseLong(matcher.group(2));
			return (euro >= Integer.MIN_VALUE && euro <= Integer.MAX_VALUE); 
		}
		else return false;
	}
	
	/**
	 * Prüft, ob ein Geldbetrag kleiner ist als ein anderer.
	 * 
	 * @param subjekt
	 * @return
	 */
	public boolean istEchtKleinerAls(Geldbetrag subjekt)
	{
		Geldbetrag differenz = subtrahiereBetrag(subjekt);
		return differenz.getSigned() && differenz != Geldbetrag.neuerGeldbetrag(0);
	}
	
	/**
	 * Prüft, ob ein Geldbetrag größer ist als ein anderer.
	 * 
	 * @param subjekt
	 * @return
	 */
	public boolean istEchtGroesserAls(Geldbetrag subjekt)
	{
		Geldbetrag differenz = subtrahiereBetrag(subjekt);
		return !differenz.getSigned() && differenz != Geldbetrag.neuerGeldbetrag(0);
	}
	
	/**
	 * Prüft, ob ein Geldbetrag kleiner gleich ist als ein anderer.
	 * 
	 * @param subjekt
	 * @return
	 */
	public boolean istKleinerGleichAls(Geldbetrag subjekt)
	{
		Geldbetrag differenz = subtrahiereBetrag(subjekt);
		return differenz.getSigned();
	}
	
	/**
	 * Prüft, ob ein Geldbetrag größer gleich ist als ein anderer.
	 * 
	 * @param subjekt
	 * @return
	 */
	public boolean istGroesserGleichAls(Geldbetrag subjekt)
	{
		Geldbetrag differenz = subtrahiereBetrag(subjekt);
		return !differenz.getSigned();
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
        return getFormatiertenString();
    }
}
