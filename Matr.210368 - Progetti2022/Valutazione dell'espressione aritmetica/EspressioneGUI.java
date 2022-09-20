package expr;

import java.awt.*;
import java.awt.BorderLayout; 
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

//import poo.polinomi.PolinomioGUI;

public class EspressioneGUI extends JFrame implements ActionListener {
	private JPanel contenitoreExpr; JTextField expr; JButton valuta;
	
public EspressioneGUI(){
	contenitoreExpr = new JPanel();	
	valuta = new JButton("Valuta");
	expr  = new JTextField("Inserisci l'espressione...");
	expr.addFocusListener(new FocusListener() {
	@Override 
		public void focusGained(FocusEvent e) {
			if (expr.getText().equals("Inserisci l'espressione...")) {
		            	expr.setText("");
		    }//if
		}//focusGained
		@Override
		public void focusLost(FocusEvent e) {
			if (expr.getText().isEmpty()) {
				expr.setText("Inserisci l'espressione...");
			}//if
		}//focusLost
	});//FocusListener (32)
	    
	    contenitoreExpr.add(expr);
	    contenitoreExpr.add(valuta);
	    
	    valuta.addActionListener(this);
	    
    this.setLayout(new GridBagLayout());
    this.add(contenitoreExpr);
	this.pack();
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setMinimumSize(new Dimension(300, 100));
	this.setTitle("Valuta Espressione");
	this.setVisible(true);
    
}//EspressioneGUI

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				EspressioneGUI g = new EspressioneGUI();
			}
		});
	}//main

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == valuta){
			String expr = src.toString();
			if(!expr.equals("")){
				try { 
					EspressioneAritmetica ea = new EspressioneAritmetica(expr);
					JOptionPane.showMessageDialog(this,"Risultato: "+ ea.valutaEspressione());
				}
				catch(Exception exptn){
					 JOptionPane.showMessageDialog(this,"Inserisci una espressione valida!");
				}
			}//if interno
		}//if esterno
	}//action Performed

}//end
