package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.bezahlen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Set;


import de.uni_hamburg.informatik.swt.se2.kino.fachwerte.Platz;
import de.uni_hamburg.informatik.swt.se2.kino.materialien.Vorstellung;
import de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.BeobachtbaresSubWerkzeug;

/**
 * Dieses Werkzeug unterstützt das Bezahlen der Kinotickets an der Kasse, 
 * indem es den Gesammtpreis anzeigt und eine Möglichkeit gibt, 
 * den gezahlten Betrag mit dem Gesammtbetrag abzugleichen. 
 * Außerdem ist das Verkaufen nur möglich, wenn genug Geld gezahlt wurde.
 * 
 * Dieses Werkzeug ist ein Subwerkzeug. Es benachrichtigt sein Kontextwerkzeug, 
 * wenn ein Kartenverkauf stattgefunden hat. 
 * 
 * @author Markus Fasselt, Julia Foerster, Annika Meinecke, Konstantin Moellers
 *@version 24.06.2012
 */
public class BezahlWerkzeug extends BeobachtbaresSubWerkzeug
{

	private int _gesammtpreis;
	private Vorstellung _vorstellung;
	private Set<Platz> _plaetze;
	private BezahlWerkzeugUI _ui;
	private int _bezahlt;
	private int _restbetrag;
	

	/** 
	 * Initialisiert das BezahlWerkzeug
	 * 
	 * @param plaetze die Plaetze, die verkauft werden sollen.
	 * @param vorstellung die Vorstellung, um die es sich handelt. 
	 * 
	 * @require plaetze != null 
	 * @require vorstellung != null
	 */
	public BezahlWerkzeug(Set<Platz> plaetze, Vorstellung vorstellung) 
	{
		assert plaetze != null : "Vorbedingung verletzt: plaetze != null";
		assert vorstellung != null : "Vorbedingung verletzt: vorstellung != null";
		
		_plaetze = plaetze; 
		_vorstellung = vorstellung;
		_gesammtpreis = _vorstellung.getPreisFuerPlaetze(plaetze);
		_restbetrag = 0;
		_ui = new BezahlWerkzeugUI(_gesammtpreis); 
		registriereUIAktionen();
	}
	
	/**
	 * Zeigt den Dialog an.
	 */
	public void zeigeFenster()
	{
		_ui.zeigeFenster();
	}

	/**
	 * Fügt die Funktionalität zum OK-Button, Abbruch-Button und zum Bezahlt-TextField hinzu
	 */
	private void registriereUIAktionen()
    {
		_ui.getBezahltTextField().addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				reagiereAufPreisBezahlt();
			}
		});
        _ui.getOkButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                reagiereAufOkButton();
            }
        });

        _ui.getAbbrechenButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	reagiereAufAbbrechenButton();
            }
        });
        _ui.getDialog().addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				reagiereAufGeschlossenenDialog();
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
			}
		});
    }


	/**
	 * Reagiert darauf, dass bezahlt wurde.
	 */
	private void reagiereAufPreisBezahlt()
	{
		_bezahlt = Integer.parseInt(_ui.getBezahltTextField().getText());
		_restbetrag = _bezahlt - _gesammtpreis;
		_ui.setRestbetrag(_restbetrag);	
		_ui.getOkButton().setEnabled(_restbetrag >= 0);
	}
	
	/**
	 * Reagiert auf den Ok-Button und verkauft die Plaetze.
	 */
	private void reagiereAufOkButton()
	{
		_vorstellung.verkaufePlaetze(_plaetze);
        _ui.schliesseFenster();
	}
	
	/**
	 * Schließt das Fenster
	 */
	protected void reagiereAufAbbrechenButton() 
	{
		_ui.schliesseFenster();		
	}

	/**
	 * Wenn das Fenster geschlossen wurde
	 */
	protected void reagiereAufGeschlossenenDialog() 
	{
		informiereAlleBeobachter();
	}
}