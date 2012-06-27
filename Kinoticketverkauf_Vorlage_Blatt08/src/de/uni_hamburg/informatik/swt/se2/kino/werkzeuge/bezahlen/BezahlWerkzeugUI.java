package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.bezahlen;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField; 
import javax.swing.text.NumberFormatter;

/**
 * 
 * Das UI des {@link BezahlWerkzeug} 
 * 
 * @author Markus Fasselt, Julia Foerster, Annika Meinecke, Konstantin Moellers
 * @version 24.06.2012
 *
 */

class BezahlWerkzeugUI 
{

	// Die Widgets, aus denen das UI sich zusammensetzt
	private int _gesammtbetrag;
	private JFrame _frame;
	private JButton _abbrechenButton;
	private JButton _okButton;
	private JPanel _gesammtbetragPanel;
	private JFormattedTextField _bezahltField;
	private JTextField _restbetragField;
	private JPanel _buttonPanel;
	
	/**
	 * Initialisiert die Oberfläche.
	 * 
	 * @param gesammtbetrag 
	 * 					Der Gesammtbetrag, der zu zahlen ist.
	 */
	public BezahlWerkzeugUI(int gesammtbetrag) 
	{
		_gesammtbetrag = gesammtbetrag;
		_bezahltField = new JFormattedTextField(
			       new NumberFormatter(NumberFormat.getIntegerInstance()));
		_restbetragField = new JTextField();
		_restbetragField.setEditable(false);
		
		_frame = new JFrame("Bezahlen"); 
		_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		_frame.setLayout(new BorderLayout()); 
		
		JComponent gesammtbetragPanel = erstelleGesammtbetragPanel();
		JComponent buttonPanel = erstelleButtonPanel();
		
		_frame.getContentPane().add(gesammtbetragPanel, BorderLayout.NORTH); 
		_frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
	}

	/**
	 * Erzeugt das Panel, in dem der Gesammtbetrag, der bezahlte Betrag und der Restbetrag angezeigt, bzw eingegeben werden.
	 * 
	 * @return _gesammtbetragPanel 
	 * 
	 */
	private JPanel erstelleGesammtbetragPanel() 
	{
		_gesammtbetragPanel = new JPanel(); 
		_gesammtbetragPanel.setLayout(new GridLayout(3, 2)); 
		_gesammtbetragPanel.add(new JLabel("Gesammtbetrag: ")); 
		_gesammtbetragPanel.add(new JLabel(""+_gesammtbetrag)); 
		_gesammtbetragPanel.add(new JLabel("Bezahlt: ")); 
		_gesammtbetragPanel.add(_bezahltField);
		_gesammtbetragPanel.add(new JLabel("Restbetrag: ")); 
		_gesammtbetragPanel.add(_restbetragField); 
		
		return _gesammtbetragPanel;
				
	}
	/**
	 * Erzeugt das Panel mit dem OK- und Abbrechen-Button.
	 * 
	 * @return _buttonPanel
	 */
	private JPanel erstelleButtonPanel()
	{

		_buttonPanel = new JPanel(); 
		_buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		_okButton = new JButton("OK");
		_okButton.setEnabled(false);
		_abbrechenButton = new JButton("Abbrechen"); 
		_buttonPanel.add(_okButton);
		_buttonPanel.add(_abbrechenButton); 
		
		
		return _buttonPanel;
	}

	/**
	 * Gibt den Abbrechen-Button zurück.
	 * 
	 * @return _abbrechenButton
	 */
	public JButton getAbbrechenButton() 
	{
		return _abbrechenButton;
	}

	/**
	 * Gibt den OK-Button zurück.
	 * 
	 * @return _okButton
	 */
	public JButton getOkButton() 
	{
		return _okButton;
	}

	/**
	 * Gibt das Bezahlt-TextField zurück.
	 * 
	 * @return _bezahlt
	 */
	public JFormattedTextField getBezahltTextField() 
	{
		return _bezahltField;
	}

	/**
	 * Setzt den Restbetrag
	 * 
	 */
	public void setRestbetrag(int restbetrag) 
	{
		_restbetragField.setText("" + restbetrag);
		
	}
	
	/**
	 * Zeigt das Fenster an.
	 */
	public void zeigeFenster() 
	{
		_frame.pack();
		_frame.setVisible(true);
		
	}

	/**
	 * Schließt das Fenster.
	 */
	public void schliesseFenster() 
	{
		_frame.dispose();
		
	}

	
}