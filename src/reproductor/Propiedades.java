package reproductor;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.tritonus.share.sampled.file.TAudioFileFormat;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingConstants;

/**
 * Ventana en la que se muestra la información de una canción
 * @author Pablo Durán
 *
 */
public class Propiedades extends JFrame {

	private JPanel contentPane;
	private JTextField tfTitulo;
	private JTextField tfArtista;
	private JTextField tfAlbum;
	private JTextField tfAnno;
	private JTextField tfCopyright;
	private JTextField tfUbicacion;
	private JTextField tfTamanno;
	private JTextField tfDuracion;
	private String rutaAbsoluta;
	private JTextArea tfComentario;
	private JLabel lblMb;

	/**
	 * Create the frame.
	 * 
	 * @param string
	 * @param actionListener
	 */
	public Propiedades(ActionListener actionListener, String ruta) {
		setTitle("Propiedades");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 495, 329);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("Titulo:");
		lblTitulo.setForeground(Color.LIGHT_GRAY);
		lblTitulo.setBounds(10, 11, 71, 14);
		contentPane.add(lblTitulo);

		JLabel lblArtista = new JLabel("Artista:");
		lblArtista.setForeground(Color.LIGHT_GRAY);
		lblArtista.setBounds(10, 36, 71, 14);
		contentPane.add(lblArtista);

		JLabel lblAlbum = new JLabel("Album:");
		lblAlbum.setForeground(Color.LIGHT_GRAY);
		lblAlbum.setBounds(10, 61, 71, 14);
		contentPane.add(lblAlbum);

		JLabel lblAo = new JLabel("A\u00F1o:");
		lblAo.setForeground(Color.LIGHT_GRAY);
		lblAo.setBounds(10, 86, 71, 14);
		contentPane.add(lblAo);

		JLabel lblCopyright = new JLabel("Copyright:");
		lblCopyright.setForeground(Color.LIGHT_GRAY);
		lblCopyright.setBounds(10, 111, 71, 14);
		contentPane.add(lblCopyright);

		JLabel lblUbicacion = new JLabel("Ubicaci\u00F3n:");
		lblUbicacion.setForeground(Color.LIGHT_GRAY);
		lblUbicacion.setBounds(10, 136, 71, 14);
		contentPane.add(lblUbicacion);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Comentario", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		panel.setBounds(10, 162, 271, 118);
		contentPane.add(panel);
		panel.setLayout(null);

		tfComentario = new JTextArea();
		tfComentario.setEditable(false);
		tfComentario.setBounds(6, 16, 259, 95);
		panel.add(tfComentario);

		JLabel lblTamao = new JLabel("Tama\u00F1o:");
		lblTamao.setForeground(Color.LIGHT_GRAY);
		lblTamao.setBounds(291, 162, 46, 14);
		contentPane.add(lblTamao);

		JLabel lblDuracin = new JLabel("Duraci\u00F3n:");
		lblDuracin.setForeground(Color.LIGHT_GRAY);
		lblDuracin.setBounds(291, 187, 56, 14);
		contentPane.add(lblDuracin);

		tfTitulo = new JTextField();
		tfTitulo.setBorder(null);
		tfTitulo.setForeground(Color.WHITE);
		tfTitulo.setOpaque(false);
		tfTitulo.setEditable(false);
		tfTitulo.setBounds(91, 11, 378, 20);
		contentPane.add(tfTitulo);
		tfTitulo.setColumns(10);

		tfArtista = new JTextField();
		tfArtista.setBorder(null);
		tfArtista.setForeground(Color.WHITE);
		tfArtista.setOpaque(false);
		tfArtista.setEditable(false);
		tfArtista.setColumns(10);
		tfArtista.setBounds(91, 33, 378, 20);
		contentPane.add(tfArtista);

		tfAlbum = new JTextField();
		tfAlbum.setBorder(null);
		tfAlbum.setForeground(Color.WHITE);
		tfAlbum.setOpaque(false);
		tfAlbum.setEditable(false);
		tfAlbum.setColumns(10);
		tfAlbum.setBounds(91, 58, 378, 20);
		contentPane.add(tfAlbum);

		tfAnno = new JTextField();
		tfAnno.setBorder(null);
		tfAnno.setForeground(Color.WHITE);
		tfAnno.setOpaque(false);
		tfAnno.setEditable(false);
		tfAnno.setColumns(10);
		tfAnno.setBounds(91, 83, 378, 20);
		contentPane.add(tfAnno);

		tfCopyright = new JTextField();
		tfCopyright.setBorder(null);
		tfCopyright.setForeground(Color.WHITE);
		tfCopyright.setOpaque(false);
		tfCopyright.setEditable(false);
		tfCopyright.setColumns(10);
		tfCopyright.setBounds(91, 108, 378, 20);
		contentPane.add(tfCopyright);

		tfUbicacion = new JTextField();
		tfUbicacion.setBorder(null);
		tfUbicacion.setForeground(Color.WHITE);
		tfUbicacion.setOpaque(false);
		tfUbicacion.setEditable(false);
		tfUbicacion.setColumns(10);
		tfUbicacion.setBounds(91, 134, 378, 20);
		contentPane.add(tfUbicacion);

		tfTamanno = new JTextField();
		tfTamanno.setHorizontalAlignment(SwingConstants.CENTER);
		tfTamanno.setBorder(null);
		tfTamanno.setForeground(Color.WHITE);
		tfTamanno.setOpaque(false);
		tfTamanno.setEditable(false);
		tfTamanno.setBounds(349, 159, 56, 20);
		contentPane.add(tfTamanno);
		tfTamanno.setColumns(10);

		tfDuracion = new JTextField();
		tfDuracion.setBorder(null);
		tfDuracion.setForeground(Color.WHITE);
		tfDuracion.setOpaque(false);
		tfDuracion.setEditable(false);
		tfDuracion.setColumns(10);
		tfDuracion.setBounds(349, 182, 130, 20);
		contentPane.add(tfDuracion);
		
		lblMb = new JLabel("Mb");
		lblMb.setHorizontalAlignment(SwingConstants.CENTER);
		lblMb.setForeground(Color.WHITE);
		lblMb.setBounds(408, 162, 46, 14);
		contentPane.add(lblMb);

		rutaAbsoluta = ruta;
		obternerDatos();
	}

	/**
	 * Devuelve los datos de la canción seleccionada
	 */
	public void obternerDatos() {
		File mp3Archivo = new File(rutaAbsoluta);
		AudioFileFormat baseFileFormat = null;
		try {
			baseFileFormat = AudioSystem.getAudioFileFormat(mp3Archivo);
		} catch (UnsupportedAudioFileException | IOException ex) {
			Logger.getLogger(Disco.class.getName()).log(Level.SEVERE, null, ex);
		}
		if (baseFileFormat instanceof TAudioFileFormat) {
			Map properties = ((TAudioFileFormat) baseFileFormat).properties();
			tfTitulo.setText((String) properties.get("title"));
			tfArtista.setText((String) properties.get("author"));
			tfAlbum.setText((String) properties.get("album"));
			tfAnno.setText((String) properties.get("date"));
			tfCopyright.setText((String) properties.get("copyright"));
			tfUbicacion.setText(rutaAbsoluta);
			tfComentario.setText((String) properties.get("comment"));
			tfDuracion.setText(new Gestion().formatoReloj((long) properties
					.get("duration") / 1000000));
			tfTamanno.setText(dosDecimales((double) mp3Archivo.length()
					/ (double) 1048576));
		}
	}

	/**
	 * Ajusta la capacidad a 2 decimales
	 * @param time tiempo de la canción
	 * @return capacidad formateada
	 */
	private String dosDecimales(double time) {
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(time);
	}
}
