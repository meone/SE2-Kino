package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge;

/**
 * Interface für Kontextwerkzeuge, welches Änderungen von Observables feststellt und entsprechende
 * Aktionen durchführt.
 * 
 * @author 1meineck,  1foerste, 1fasselt, 1kmoelle
 * @version 13.06.2012
 */
public interface Observer {
	/**
	 * Stellt Änderungen eines bestimmten Senders fest.
	 * 
	 * @param sender
	 * 		Ein beobachtbares Objekt, welches eine Änderung durchgeführt hat.
	 * @require sender != null
	 */
	void handleChanges(Observable sender);
}
