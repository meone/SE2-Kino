package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge;

import java.util.HashSet;
import java.util.Set;

/**
 * Baisklasse für Subwerkzeuge, welche Änderungen an verschiedenen Observern feststellt
 * und an diese entsprechend weiterleitet.
 * 
 * @author 1meineck,  1foerste, 1fasselt, 1kmoelle
 * @version 13.06.2012
 */
public abstract class Observable {
	
	/**
	 * Liste aller Beobachter.
	 */
	private Set<Observer> _observers;
	
	/**
	 * Erstellt die Basisklasse.
	 */
	public Observable()
	{
		// Erstellt eine neue Array list
		_observers = new HashSet<Observer>();
	}
	
	/**
	 * Fügt einen Beobachter zu diesem Subwerkzeug hinzu.
	 * 
	 * @param o
	 * 		Der Beobachter, der hinzugefügt werden soll.
	 * @return
	 * 		Gibt zurück, ob der Beobachter hinzugefügt werden konnte.
	 * 
	 * @require o != null
	 */
	public boolean addObserver(Observer o)
	{
		assert o != null : "Vorbedingung verletzt: o ist keine Referenz auf ein Objekt!";
		
		return _observers.add(o);
	}
	
	/**
	 * Entfernt einen Beobachter von diesem Subwerkzeug.
	 *
	 * @param o	
	 * 		Der zu entfernende Beobachter.
	 * 
	 * @require isObserver(o)
	 */
	public void removeObserver(Observer o)
	{
		assert isObserver(o) : "Vorbedingung verletzt: isObserver(o)";
		
		_observers.remove(o);
	}
	
	/**
	 * Gibt zurück, ob dies ein Beobachter von diesem Subwerkzeug ist.
	 * 
	 * @param o
	 * 		Der zu prüfende Beobachter.
	 * @return
	 * 		Der Erfolg der Prüfung.
	 * 
	 * @require o != null
	 */
	public boolean isObserver(Observer o)
	{
		assert o != null : "Vorbedingung verletzt: o ist keine Referenz auf ein Objekt!";
		
		return _observers.contains(o);
	}
	
	/**
	 * Gibt Änderungen an seine Beobachter weiter.
	 */
	protected void update()
	{
		for (Observer o: _observers)
		{
			// Informiere über Änderung
			o.handleChanges(this);
		}
	}
	
}
