package reproductor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.JLabel;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JScrollPane;

import java.awt.Dimension;

import javax.swing.ListSelectionModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.MouseInfo;

import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class Principal extends JFrame implements BasicPlayerListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable listMusica;
	private static ArrayList<String> canciones = new ArrayList<>();
	private ArrayList<Integer> numerosAleatorios = new ArrayList<>();
	private Disco player = new Disco();
	private Gestion metodos = new Gestion();
	private int itemActual, primeroDeAleatorio, posicionRaton, repetir = 0,
			auxitemActual = 0;
	private boolean aleatorio = false, enSilencio = false;
	public static boolean CambioEnEcualizador = false;
	private float[] ecualizador;
	boolean abierto = true;
	private JLabel lbTiempo;
	private JProgressBar pbProgreso;
	private JLabel lbTiempoCancion;
	private JButton btnMute;
	private JSlider slVolumen;
	Hilo hilo = null;
	private JButton btnAleatorio;
	private JButton btnRepetir;
	private JLabel lbCancion;
	private JPopupMenu popupMenu;
	private VerAyuda verAyuda = new VerAyuda();
	private SobreReproductor acercaDe = new SobreReproductor();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setResizable(false);
		setTitle("Reproductor MP3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 563, 418);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu mnArchivo = new JMenu("Archivo");
		mnArchivo.setMnemonic('a');
		menuBar.add(mnArchivo);

		JMenuItem mnAbrirCancion = new JMenuItem("Abrir Cancion");
		mnAbrirCancion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		mnAbrirCancion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ubicaciones[] = metodos.abrirArchivo("Archivos MP3",
						"mp3");
				if (ubicaciones != null) {
					itemActual = 0;
					auxitemActual = itemActual;
					if (canciones.size() > 0) {
						canciones.clear();
					}
					canciones.addAll(Arrays.asList(ubicaciones));
					player.cambiarPor(ubicaciones[0], false);
					if (canciones.size() > 0) {
						listarTabla();
					}
					// mnAnnadirCanciones.setEnabled(true);
					// jMenuItem4.setEnabled(true);
					if (canciones.size() > 0) {
						llenarNumerosAleatorios();
					}
				}
			}
		});
		mnArchivo.add(mnAbrirCancion);

		JMenuItem mnAbrirCarpeta = new JMenuItem("Abrir Carpeta");
		mnAbrirCarpeta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		mnAbrirCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Ubicaciones[] = metodos.abrirCarpeta();
				if (Ubicaciones != null) {
					itemActual = 0;
					auxitemActual = itemActual;
					if (canciones.size() > 0) {
						canciones.clear();
					}
					for (String Aux : Ubicaciones) {
						if (metodos.esMp3(Aux)) {
							canciones.add(Aux);
						}
					}
					if (!player.cambiarPor(Ubicaciones[0], false)) {
						lbTiempo.setText("00:00/00:00");
						lbTiempoCancion.setVisible(false);
					}
					if (canciones.size() > 0) {
						listarTabla();
					}
					if (canciones.size() > 0) {
						llenarNumerosAleatorios();
					}
				}

			}
		});
		mnArchivo.add(mnAbrirCarpeta);

		JMenuItem mnAnnadirCancion = new JMenuItem("A\u00F1adir Cancion");
		mnAnnadirCancion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnAnnadirCancion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Ubicaciones[] = metodos.abrirArchivo("Archivos MP3",
						"mp3");
				if (Ubicaciones != null) {
					canciones.addAll(Arrays.asList(Ubicaciones));
					if (canciones.size() > 0) {
						listarTabla();
					}
					if (aleatorio) {
						llenarNumerosAleatorios();
					}
				}
			}
		});
		mnArchivo.add(mnAnnadirCancion);

		JMenuItem mntmAadirCarpeta = new JMenuItem("A\u00F1adir Carpeta");
		mntmAadirCarpeta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		mntmAadirCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Ubicaciones[] = metodos.abrirCarpeta();
				if (Ubicaciones != null) {
					for (String Aux : Ubicaciones) {
						if (metodos.esMp3(Aux)) {
							canciones.add(Aux);
						}
					}
					if (canciones.size() > 0) {
						listarTabla();
					}
					if (aleatorio) {
						llenarNumerosAleatorios();
					}
				}
			}
		});
		mnArchivo.add(mntmAadirCarpeta);

		JMenu mnAyuda = new JMenu("Ayuda");
		mnAyuda.setMnemonic('y');
		menuBar.add(mnAyuda);

		JMenuItem mntmVerAyuda = new JMenuItem("Ver Ayuda");
		mntmVerAyuda.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
		mntmVerAyuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verAyuda();
			}
		});
		mnAyuda.add(mntmVerAyuda);

		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de...");
		mntmAcercaDe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		mntmAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				acercaDe();
			}
		});
		mnAyuda.add(mntmAcercaDe);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(listMusica);
		scrollPane.setBounds(285, 11, 252, 264);
		
		contentPane.add(scrollPane);

		listMusica = new JTable();
		listMusica.setSurrendersFocusOnKeystroke(true);
		listMusica.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listMusica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2
						&& e.getButton() == MouseEvent.BUTTON1
						&& canciones.size() > 0) {
					itemActual = listMusica.getSelectedRow();
					seleccionarCancionActual();
					comprobarHilo();
					for (int i = 0; i < numerosAleatorios.size(); i++) {
						if (numerosAleatorios.get(i) == itemActual) {
							auxitemActual = i;
						}
					}
					if (!player.cambiarPor(canciones.get(itemActual), true)) {
						JOptionPane.showMessageDialog(contentPane,
								"Ocurrio un error al abrir esta canción");
					}
				}
				lbCancion.setText(player.nombreArchivo());
				lbTiempoCancion.setText("/ "
						+ metodos.formatoReloj(player.duracion));
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON3) {
					posicionRaton = arg0.getY() / 16;
					popupMenu.show(arg0.getComponent(), arg0.getX(),
							arg0.getY());
				}
			}
		});
		listMusica.setPreferredSize(new Dimension(288, 236));
		listMusica.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "#", "Cancion" }) {

			private static final long serialVersionUID = 1L;
			boolean[] canEdit = new boolean[] { false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
			
		});
		scrollPane.setViewportView(listMusica);
		popupMenu = new JPopupMenu();
		addPopup(listMusica, popupMenu);

		JMenuItem mntmPropiedades = new JMenuItem("Propiedades");
		mntmPropiedades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Propiedades(this, canciones.get(posicionRaton))
						.setVisible(true);
			}
		});

		JMenuItem mntmEliminarCancion = new JMenuItem("Eliminar Cancion");
		mntmEliminarCancion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (canciones.size() > 1) {
					if (itemActual == posicionRaton) {
						if (!pasarCancion(true)) {
							lbTiempo.setText("00:00");
							lbTiempoCancion.setText("/ 00:00");
						}
					}
				} else {
					player.parar();
					lbTiempo.setText("00:00/00:00");
					pbProgreso.setValue(1);
					new File(Gestion.ruta() + "/Canciones.dat").delete();
				}
				DefaultTableModel modelo = (DefaultTableModel) listMusica
						.getModel();
				modelo.removeRow(posicionRaton);
				canciones.remove(posicionRaton);
				if (posicionRaton < itemActual) {
					itemActual--;
					lbCancion.setText(player.nombreArchivo());
				}
				if (canciones.size() > 0) {
					listarTabla();
				}
				if (aleatorio) {
					llenarNumerosAleatorios();
				}
			}
		});

		popupMenu.add(mntmEliminarCancion);
		popupMenu.add(mntmPropiedades);

		listMusica.getColumnModel().getColumn(0).setPreferredWidth(34);

		pbProgreso = new JProgressBar();
		pbProgreso.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (pbProgreso.getMaximum() == pbProgreso.getValue()) {
					pbProgreso.setValue(1); // esto evita que pase dos canciones
											// a la vez
					if (repetir != 2) {
						while (!pasarCancion(true)) {
							// MensajeError();
							// try {
							// Thread.sleep(3000);
							// } catch (InterruptedException ex) {
							// Logger.getLogger(Principal.class.getName()).log(Level.SEVERE,
							// null, ex);
							// }
						}
						if (player.estado() != 0) {
							player.reproducir(); // en caso de q no reprodusca
													// automaticamente
						}
					} else {
						player.parar();
						player.reproducir();
					}
					if (repetir == 0
							&& ((itemActual == 0 && !aleatorio) || (itemActual == primeroDeAleatorio && aleatorio))) {
						player.parar();
					} else {
						comprobarHilo();
					}
				}
			}

		});
		pbProgreso.setBounds(10, 290, 527, 14);
		contentPane.add(pbProgreso);

		JButton btnPlay = new JButton("");
		btnPlay.setOpaque(false);
		btnPlay.setIcon(new ImageIcon("src\\img\\Play.jpg"));
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonPlay();
			}
		});
		btnPlay.setBounds(77, 315, 34, 34);
		contentPane.add(btnPlay);

		JButton btnPause = new JButton("");
		btnPause.setOpaque(false);
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonPausa();

			}
		});
		btnPause.setIcon(new ImageIcon("src\\img\\Pause.jpg"));
		btnPause.setBounds(121, 315, 34, 34);
		contentPane.add(btnPause);

		btnAleatorio = new JButton("");
		btnAleatorio.setBackground(Color.BLACK);
		btnAleatorio.setOpaque(false);
		btnAleatorio.setIcon(new ImageIcon("src\\img\\Aleatorio0.png"));
		btnAleatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aleatorio();
			}

		});
		btnAleatorio.setBounds(215, 251, 47, 33);
		contentPane.add(btnAleatorio);

		lbTiempo = new JLabel("00:00");
		lbTiempo.setForeground(Color.WHITE);
		lbTiempo.setHorizontalAlignment(SwingConstants.RIGHT);
		lbTiempo.setBounds(65, 251, 72, 39);
		contentPane.add(lbTiempo);

		lbTiempoCancion = new JLabel("/ 00:00");
		lbTiempoCancion.setForeground(Color.WHITE);
		lbTiempoCancion.setHorizontalAlignment(SwingConstants.LEFT);
		lbTiempoCancion.setBounds(138, 251, 81, 39);
		contentPane.add(lbTiempoCancion);

		slVolumen = new JSlider();
		slVolumen.setValue(100);
		slVolumen.setOpaque(false);
		slVolumen.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (enSilencio) {
					volumenCambiar();
				} else {
					volumenActual();
				}
				try (ObjectOutputStream escribir = new ObjectOutputStream(
						new FileOutputStream(Gestion.ruta() + "/Volumen.dat"))) {
					escribir.writeDouble(player.volumen);
					escribir.close();
				} catch (FileNotFoundException e) {
				} catch (IOException e) {
				}
			}
		});
		slVolumen.setBounds(285, 315, 208, 39);
		contentPane.add(slVolumen);

		btnMute = new JButton("");
		btnMute.setIcon(new ImageIcon("src\\img\\ConAudio.jpg"));
		btnMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				volumenCambiar();
			}
		});
		btnMute.setBounds(503, 315, 34, 34);
		contentPane.add(btnMute);

		JButton btnSiguiente = new JButton("");
		btnSiguiente.setOpaque(false);
		btnSiguiente.setIcon(new ImageIcon("src\\img\\Adelante.jpg"));
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!pasarCancion(true)) {
					if (canciones.size() > 0) {
						lbTiempo.setText("00:00/00:00");
						lbTiempoCancion.setVisible(false);
					}
				} else {
					seleccionarCancionActual();
					comprobarHilo();
					player.reproducir();
				}
			}

		});
		btnSiguiente.setBounds(207, 315, 34, 34);
		contentPane.add(btnSiguiente);

		JButton btnAnterior = new JButton("");
		btnAnterior.setOpaque(false);
		btnAnterior.setIcon(new ImageIcon("src\\img\\Atras.jpg"));
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!pasarCancion(false)) {
					if (canciones.size() > 0) {
						lbTiempo.setText("00:00/00:00");
						lbTiempoCancion.setVisible(false);
						try {
							player.player.wait(10);
						} catch (InterruptedException e) {
							JOptionPane.showMessageDialog(contentPane,
									"Ups, parece que ha ocurrido un error");
						}
					}
				} else {
					seleccionarCancionActual();
					comprobarHilo();
					player.reproducir();
				}
			}
		});
		btnAnterior.setBounds(32, 315, 34, 34);
		contentPane.add(btnAnterior);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("src\\img\\Music_Animation.gif"));
		lblNewLabel.setBounds(10, 32, 252, 208);
		contentPane.add(lblNewLabel);

		btnRepetir = new JButton("");
		btnRepetir.setBackground(Color.BLACK);
		btnRepetir.setOpaque(false);
		btnRepetir.setIcon(new ImageIcon("src\\img\\Repetir0.png"));
		btnRepetir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repetir();
			}
		});
		btnRepetir.setBounds(20, 251, 47, 33);
		contentPane.add(btnRepetir);

		lbCancion = new JLabel("Reproductor");
		lbCancion.setHorizontalAlignment(SwingConstants.CENTER);
		lbCancion.setForeground(Color.WHITE);
		lbCancion.setBounds(20, 11, 242, 21);
		contentPane.add(lbCancion);

		JButton btnStop = new JButton("");
		btnStop.setOpaque(false);
		btnStop.setIcon(new ImageIcon("src\\img\\Stop.jpg"));
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (canciones.size() > 0) {
					player.parar();
					lbTiempo.setText("00:00");
					pbProgreso.setValue(1);
					seleccionarCancionActual();
					hilo = null;
					barraProgreso();
					hilo = new Hilo((int) player.duracion, pbProgreso, lbTiempo);
					// hilo.stop();
				}
			}
		});
		btnStop.setBounds(165, 315, 34, 34);
		contentPane.add(btnStop);
	}

	/**
	 * Rellena la tabla con las canciones abiertas
	 */
	private void listarTabla() {
		DefaultTableModel modelo = (DefaultTableModel) listMusica.getModel();
		while (modelo.getRowCount() != 0) {
			modelo.removeRow(0);
		}
		if (canciones.size() > 0) {
			File archivo;
			for (int i = 0; i < canciones.size(); i++) {
				archivo = null;
				archivo = new File(canciones.get(i));
				modelo.addRow(new Object[] {
						i + 1,
						archivo.getName().substring(0,
								archivo.getName().length() - 4) });
			}
		}
		seleccionarCancionActual();
		if (abierto) {
			abierto = false;
		}
	}

	/**
	 * Selecciona la canción actual
	 */
	private void seleccionarCancionActual() {
		listMusica.setRowSelectionInterval(itemActual, itemActual);
		lbCancion.setText(player.nombreArchivo());
		if (hilo == null) {
			hilo = new Hilo((int) player.duracion, pbProgreso, lbTiempo);
		} else {
			hilo.stop();
		}
	}

	/**
	 * Llena la columna de numeros con respecto a las canciones
	 */
	private void llenarNumerosAleatorios() {
		ArrayList<Integer> aux = new ArrayList<>();
		for (int i = 0; i < canciones.size(); i++) {
			aux.add(i);
		}
		aux.remove(itemActual);
		if (numerosAleatorios.size() > 0) {
			numerosAleatorios.clear();
		}
		int numeroAleatorio;
		for (int i = 0; i < canciones.size() - 1; i++) {
			numeroAleatorio = (int) (Math.random() * (canciones.size() - 1 - i));
			numerosAleatorios.add(aux.get(numeroAleatorio));
			aux.remove(numeroAleatorio);
		}
		auxitemActual = itemActual;
		primeroDeAleatorio = itemActual;
		numerosAleatorios.add(itemActual, itemActual); // agrega el item actual
														// en su posicion
	}

	/**
	 * Cambia el volumen o silencia el sonido
	 */
	private void volumenCambiar() {
		if (!enSilencio) {
			btnMute.setIcon(new ImageIcon("src\\img\\SinAudio.jpg"));
			enSilencio = true;
			volumenSilenciar();
		} else {
			btnMute.setIcon(new ImageIcon("src\\img\\ConAudio.jpg"));
			enSilencio = false;
			volumenActual();
		}
	}

	/**
	 * Cambia el volumen
	 */
	private void volumenActual() {
		if (player.estado() != -1 && player.estado() != 3) {
			if (!player.cambiarVolumen((double) slVolumen.getValue()
					/ (double) slVolumen.getMaximum())) {
				JOptionPane.showMessageDialog(this, "Ups, algo salio mal",
						"Aviso", 0);
			}
		} else {
			player.volumen = (double) slVolumen.getValue()
					/ (double) slVolumen.getMaximum();
		}
	}

	/**
	 * Pone en silencio el reproductor
	 */
	private void volumenSilenciar() {
		if (player.estado() != -1 && player.estado() != 3) {
			if (!player.cambiarVolumen(0)) {
				JOptionPane.showMessageDialog(this, "Ups, algo salio mal",
						"Aviso", 0);
			}
		} else {
			player.volumen = 0;
		}
	}

	/**
	 * Pasa a la siguiente canción
	 * 
	 * @param adelante
	 *            true si avanza a la siguiente, false si avanza a la anterior
	 * @return true si avanza a la siguiente, false si avanza a la anterior
	 */
	private boolean pasarCancion(boolean adelante) {
		boolean abre = false;
		if (canciones.size() > 0) {
			if (aleatorio) {
				itemActual = auxitemActual;

			}
			if (adelante) {
				itemActual++;
				if (itemActual > canciones.size() - 1) {
					itemActual = 0;
				}
			} else {
				itemActual--;
				if (itemActual < 0) {
					itemActual = canciones.size() - 1;
				}
			}
			auxitemActual = itemActual;
			if (aleatorio) {
				itemActual = numerosAleatorios.get(itemActual);
			}
			if (player.estado() != 0) {
				abre = player.cambiarPor(canciones.get(itemActual), false);
			} else {
				abre = player.cambiarPor(canciones.get(itemActual), true);
			}
			listMusica.setRowSelectionInterval(itemActual, itemActual);
			lbCancion.setText(player.nombreArchivo());
		}
		return abre;
	}

	/**
	 * Asigna el valor máximo de la barra de progreso y el tiempo de la canción
	 */
	private void barraProgreso() {
		pbProgreso.setMaximum((int) player.duracion);
		lbTiempoCancion.setText("/ " + metodos.formatoReloj(player.duracion));
	}

	/**
	 * Reinicia el hilo
	 */
	private void comprobarHilo() {
		hilo = null;
		barraProgreso();
		hilo = new Hilo((int) player.duracion, pbProgreso, lbTiempo);
		hilo.start();
	}

	/**
	 * Modo de reproducción aleatoria
	 */
	private void aleatorio() {
		if (!aleatorio) {
			aleatorio = true;
			btnAleatorio.setIcon(new ImageIcon("src/img/Aleatorio1.png"));
			if (canciones.size() > 0) {
				llenarNumerosAleatorios();
			}
		} else {
			aleatorio = false;
			btnAleatorio.setIcon(new ImageIcon("src/img/Aleatorio0.png"));
		}
	}

	/**
	 * Crea la ventana VerAyuda
	 */
	private void verAyuda() {
		verAyuda.setVisible(true);
		verAyuda.toFront();
	}
	
	private void acercaDe() {
		acercaDe.setVisible(true);
		acercaDe.toFront();
	}

	@Override
	public void opened(Object arg0, Map arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void progress(int arg0, long arg1, byte[] arg2, Map arg3) {
		if (CambioEnEcualizador) {
			ecualizador = (float[]) arg3.get("mp3.equalizer");
			System.arraycopy(player.eq, 0, ecualizador, 0, ecualizador.length);
			player.balance(player.balance);
			CambioEnEcualizador = false;
		}
	}

	@Override
	public void setController(BasicController arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateUpdated(BasicPlayerEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Inicia la canción o en caso de que estuviese pausada la reanuda
	 */
	private void botonPlay() {
		if (canciones.size() > 0) {
			barraProgreso();
			player.reproducir();
			if (!hilo.isAlive()) {
				comprobarHilo();
			} 
			else {
				hilo.resume();
			}
		}
	}

	/**
	 * Pausa la canción
	 */
	private void botonPausa() {
		if (canciones.size() > 0) {
			player.pausar();
			if (hilo.isAlive()) {
				hilo.suspend();
			}
		}
	}

	/**
	 * Cambia el estado de repetir así como su imagen
	 */
	private void repetir() {
		if (repetir == 0) {
			repetir = 1;
			btnRepetir.setIcon(new ImageIcon("src/img/Repetir1.png"));
		} else if (repetir == 1) {
			repetir = 2;
			btnRepetir.setIcon(new ImageIcon("src/img/Repetir2.png"));
		} else if (repetir == 2) {
			repetir = 0;
			btnRepetir.setIcon(new ImageIcon("src/img/Repetir0.png"));
		}
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger() && canciones.size() > 0) {
					showMenu(e);
				}
				if (canciones.size() < 1 && e.getButton() == MouseEvent.BUTTON3) {
					JOptionPane.showMessageDialog(null,
							"No hay canciones disponibles");
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
