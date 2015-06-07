package reproductor;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.Label;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Color;
/**
 * Muestra información sobre el programa
 * @author Pablo Durán
 *
 */
public class SobreReproductor extends JDialog {

	/**
	 * Create the dialog.
	 */
	public SobreReproductor() {
		setResizable(false);
		setTitle("Acerca de...");
		setBounds(100, 100, 512, 220);
		getContentPane().setLayout(null);
		
		JLabel lblDesarrolladoPorPablo = new JLabel("<html><body>Desarrollado por: Pablo Dur\u00E1n Camacho.<br/>\r\nVersion: 2.1\r\n</body></html>");
		lblDesarrolladoPorPablo.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesarrolladoPorPablo.setForeground(Color.WHITE);
		lblDesarrolladoPorPablo.setBounds(22, 53, 457, 88);
		getContentPane().add(lblDesarrolladoPorPablo);
		
		JLabel lbFondoMadera = new JLabel("");
		lbFondoMadera.setIcon(new ImageIcon("src\\img\\musica.jpg"));
		lbFondoMadera.setBounds(0, 0, 519, 192);
		getContentPane().add(lbFondoMadera);

	}
}
