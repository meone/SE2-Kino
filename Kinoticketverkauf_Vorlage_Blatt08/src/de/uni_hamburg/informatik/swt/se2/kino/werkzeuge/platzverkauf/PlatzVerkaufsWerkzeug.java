package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.platzverkauf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JPanel;

import de.uni_hamburg.informatik.swt.se2.kino.fachwerte.Platz;
import de.uni_hamburg.informatik.swt.se2.kino.materialien.Kinosaal;
import de.uni_hamburg.informatik.swt.se2.kino.materialien.Vorstellung;
import de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.SubwerkzeugBeobachter;
import de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.bezahlen.BezahlWerkzeug;

/**
 * Mit diesem Werkzeug können Plätze verkauft und storniert werden. Es arbeitet
 * auf einer Vorstellung als Material. Mit ihm kann angezeigt werden, welche
 * Plätze schon verkauft und welche noch frei sind.
 * 
 * Dieses Werkzeug ist ein eingebettetes Subwerkzeug. Es kann nicht beobachtet
 * werden.
 * 
 * @author SE2-Team
 * @version SoSe 2012
 */
public class PlatzVerkaufsWerkzeug
{
	
    private PlatzVerkaufsWerkzeugUI _ui;

    // Die aktuelle Vorstellung, deren Plätze angezeigt werden. Kann null sein.
    private Vorstellung _vorstellung;

    // Das Subwerkzeug
	private BezahlWerkzeug _bezahlwerkzeug;

    /**
     * Initialisiert das PlatzVerkaufsWerkzeug.
     */
    public PlatzVerkaufsWerkzeug()
    {
        _ui = new PlatzVerkaufsWerkzeugUI();
        registriereUIAktionen();
        // Am Anfang wird keine Vorstellung angezeigt:
        setVorstellung(null);
    }

    /**
     * Gibt das Panel dieses Subwerkzeugs zurück. Das Panel sollte von einem
     * Kontextwerkzeug eingebettet werden.
     * 
     * @ensure result != null
     */
    public JPanel getUIPanel()
    {
        return _ui.getUIPanel();
    }

    /**
     * Fügt der UI die Funktionalität hinzu mit entsprechenden Listenern.
     */
    private void registriereUIAktionen()
    {
        _ui.getVerkaufenButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                verkaufePlaetze();
            }
        });

        _ui.getStornierenButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                stornierePlaetze();
            }
        });

        _ui.getPlatzplan().addPlatzSelectionListener(
                new PlatzSelectionListener()
                {
                    @Override
                    public void auswahlGeaendert(PlatzSelectionEvent event)
                    {
                        reagiereAufNeuePlatzAuswahl(event
                                .getAusgewaehltePlaetze());
                    }
                });
    }

    /**
     * Reagiert darauf, dass sich die Menge der ausgewählten Plätze geändert
     * hat.
     * 
     * @param plaetze die jetzt ausgewählten Plätze.
     */
    private void reagiereAufNeuePlatzAuswahl(Set<Platz> plaetze)
    {
        _ui.getVerkaufenButton().setEnabled(istVerkaufenMoeglich(plaetze));
        _ui.getStornierenButton().setEnabled(istStornierenMoeglich(plaetze));
        aktualisierePreisanzeige(plaetze);
    }

    /**
     * Aktualisiert den anzuzeigenden Gesamtpreis
     */
    private void aktualisierePreisanzeige(Set<Platz> plaetze)
    {

        if (istVerkaufenMoeglich(plaetze))
        {
            int preis = _vorstellung.getPreisFuerPlaetze(plaetze);
            _ui.getPreisLabel().setText("Gesamtpreis: " + preis + " Eurocent");
        }
        else
        {
            _ui.getPreisLabel().setText("Gesamtpreis:");
        }
    }

    /**
     * Prüft, ob die angegebenen Plätze alle storniert werden können.
     */
    private boolean istStornierenMoeglich(Set<Platz> plaetze)
    {
        return !plaetze.isEmpty() && _vorstellung.sindStornierbar(plaetze);
    }

    /**
     * Prüft, ob die angegebenen Plätze alle verkauft werden können.
     */
    private boolean istVerkaufenMoeglich(Set<Platz> plaetze)
    {
        return !plaetze.isEmpty() && _vorstellung.sindVerkaufbar(plaetze);
    }

    /**
     * Setzt die Vorstellung. Sie ist das Material dieses Werkzeugs. Wenn die
     * Vorstellung gesetzt wird, muss die Anzeige aktualisiert werden. Die
     * Vorstellung darf auch null sein.
     */
    public void setVorstellung(Vorstellung vorstellung)
    {
        _vorstellung = vorstellung;
        aktualisierePlatzplan();
    }

    /**
     * Aktualisiert den Platzplan basierend auf der ausgwählten Vorstellung.
     */
    private void aktualisierePlatzplan()
    {
        if (_vorstellung != null)
        {
            Kinosaal saal = _vorstellung.getKinosaal();
            _ui.getPlatzplan().setAnzahlPlaetze(saal.getAnzahlReihen(),
                    saal.getAnzahlSitzeProReihe());

            for (Platz platz : saal.getPlaetze())
            {
                if (_vorstellung.istPlatzVerkauft(platz))
                {
                    _ui.getPlatzplan().markierePlatzAlsVerkauft(platz);
                }
            }
        }
        else
        {
            _ui.getPlatzplan().setAnzahlPlaetze(0, 0);
        }
    }

    /**
     * Erstellt das Subwerkzeug, durch das die Plaetze verkauft werden.
     */
    private void verkaufePlaetze()
    {
        Set<Platz> plaetze = _ui.getPlatzplan().getAusgewaehltePlaetze();
        _bezahlwerkzeug = new BezahlWerkzeug(plaetze, _vorstellung); 
        erzeugeListenerFuerSubwerkzeug();
        _bezahlwerkzeug.zeigeFenster();
    }

    /**
     * Erzeugt und registriert die Beobachter, die die Subwerkzeuge beobachten.
     */
    private void erzeugeListenerFuerSubwerkzeug() 
    {
    	_bezahlwerkzeug
        	.registriereBeobachter(new SubwerkzeugBeobachter()
        {
            @Override
            public void informiereUeberAenderung()
            {
                aktualisierePlatzplan();
            }
        });
    	
	}

	/**
     * Storniert die ausgewählten Plaetze.
     */
    private void stornierePlaetze()
    {
        Set<Platz> plaetze = _ui.getPlatzplan().getAusgewaehltePlaetze();
        _vorstellung.stornierePlaetze(plaetze);
        aktualisierePlatzplan();
    }
}