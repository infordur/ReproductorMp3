package reproductor;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextArea;

import javax.swing.JLabel;
import javax.swing.JScrollBar;

import java.awt.Scrollbar;

import javax.swing.JPanel;
import javax.swing.JTextPane;

import java.awt.Point;

/**
 * Muestra información sobre el reproductor
 * @author Pablo Durán
 *
 */
public class VerAyuda extends JDialog {

	/**
	 * Create the dialog.
	 */
	public VerAyuda() {
		setResizable(false);
		setTitle("Ver Ayuda");
		setBounds(100, 100, 617, 493);
		getContentPane().setLayout(null);
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(0, 0, 611, 465);

		scroll.setPreferredSize(new Dimension(200,300));
		getContentPane().add(scroll);
		
		JTextPane txtpReproductor = new JTextPane();
		txtpReproductor.setEditable(false);
		txtpReproductor.setContentType("text/html");
		txtpReproductor.setText("<html><body>\r\n\r\n<h1>Abrir Canci\u00F3n</h1>\r\n<p>Permite abrir un solo archivo a la vez</p>\r\n\r\n<h1>Abrir Carpeta</h1>\r\n\r\n<p>Abre una carpeta que contenga archivos .mp3 dentro</p>\r\n\r\n<h1>A\u00F1adir Cancion</h1>\r\n<p>A\u00F1ade una cancion a la lista de reproducci\u00F3n</p>\r\n\r\n<h1>A\u00F1adir Carpeta</h1>\r\n<p>A\u00F1ade una carpeta a la lista de reproducci\u00F3n</p>\r\n\r\n<h1>Controles</h1>\r\n<ul>\r\n<li>Play</li>\r\n<p>El bot\u00F3n Play permitir\u00E1 reproducir la canci\u00F3n seleccionada o reanudar la reproduci\u00F3n de una canci\u00F3n que haya sido pausada</p>\r\n<li>Pausa</li>\r\n<p>El bot\u00F3n Pausa permitir\u00E1 detener la canci\u00F3n que se est\u00E9 reproduciendo en ese momento</p>\r\n<li>Stop</li>\r\n<p>El bot\u00F3n Stop permitir\u00E1 detener la canci\u00F3n que se estuviese reproduciendo en ese momento</p>\r\n<li>Siguiente</li>\r\n<p>El bot\u00F3n Siguiente permitir\u00E1 avanzar a la siguiente canci\u00F3n de la lista</p>\r\n<li>Anterior</li>\r\n<p>El bot\u00F3n Siguiente permitir\u00E1 avanzar a la anterior canci\u00F3n de la lista</p>\r\n</ul>\r\n<p>Si hacemos click derecho sobre una canci\u00F3n nos perimitir\u00E1 realizar 2 opciones: </p>\r\n<ul>\r\n<li>Eliminar canci\u00F3n</li>\r\n<p>Est\u00E1 opci\u00F3n eliminar\u00E1 la canci\u00F3n seleccionada</p>\r\n<li>Propiedades</li>\r\n<p>Esta opci\u00F3n nos permitir\u00E1 ver m\u00E1s detalles sobre la canci\u00F3n</p></ul>\r\n</body></html>");
		scroll.setViewportView(txtpReproductor);
		
		
		
		

	}
}
