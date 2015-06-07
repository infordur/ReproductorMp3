package reproductor;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import org.tritonus.share.sampled.file.TAudioFileFormat;
/**
 * Clase Disco en la que se encuentran los datos de las canciones y los métodos para reproducirlas
 * @author Pablo Durán
 *
 */
public class Disco {
	public BasicPlayer player;
	public static float[] eq = new float[32];
	public static int[] eqP = new int[10];
	public static float balance;
	public static int itemEc;
	public double volumen;
	public long duracion;
	public String rutaAbsoluta, titulo, artista, album, ano, comentario,
			copyright;

	/**
	 * Constructor de la clase Disco
	 */
	public Disco() {
		this.volumen = 1;
		player = new BasicPlayer();
	}

	/**
	 * Abre una canción
	 * 
	 * @param Ruta
	 * @return
	 */
	public boolean abrir(String Ruta) {
		File Archivo = new File(Ruta);
		try {
			rutaAbsoluta = Ruta;
			limpiarDatos();
			obternerDatos();
			player.open(Archivo);
			return true;
		} catch (BasicPlayerException ex) {
			return false;
		}
	}

	/**
	 * Reproduce una canción
	 * 
	 * @return true si la puede reproducir, false si no
	 */
	public boolean reproducir() {
		try {
			if (player.getStatus() == 1) {
				player.resume();
			} else {
				player.play();
			}
			cambiarVolumen(volumen);
			return true;
		} catch (BasicPlayerException ex) {
			return false;
		}
	}

	/**
	 * Para la canción
	 * 
	 * @return true si puede detenerla, false si no
	 */
	public boolean parar() {
		try {
			if (player.getStatus() == 1 || player.getStatus() == 0) {
				player.stop();
			}
			return true;
		} catch (BasicPlayerException ex) {
			return false;
		}
	}

	/**
	 * Pausa la canción
	 * 
	 * @return true si puede pausarla, false si no
	 */
	public boolean pausar() {
		try {
			if (player.getStatus() == 0) {
				player.pause();
			}
			return true;
		} catch (BasicPlayerException ex) {
			return false;
		}
	}

	/**
	 * Cambia de canción
	 * 
	 * @param ruta
	 * @param reproducir
	 * @return
	 */
	public boolean cambiarPor(String ruta, boolean reproducir) {
		parar();
		if (abrir(ruta)) {
			if (reproducir) {
				reproducir();
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Devuelve el estado de la canción
	 * @return estado de la canción
	 */
	public int estado() {
		return player.getStatus();
	}

	/**
	 * Cambia el volumen de la canción por el indicado
	 * @param volumen Volumen indicado
	 * @return true si puede cambiarlo, false si no.
	 */
	public boolean cambiarVolumen(double volumen) {
		try {
			this.volumen = volumen;
			player.setGain(volumen);
		} catch (BasicPlayerException ex) {
			return false;
		}
		return true;
	}

	public boolean balance(float bal) {
		try {
			player.setPan(bal);
		} catch (BasicPlayerException ex) {
			return false;
		}
		return true;
	}

	/**
	 * Resetea los datos de la canción
	 */
	public void limpiarDatos() {
		titulo = "";
		artista = "";
		album = "";
		ano = "";
		comentario = "";
		copyright = "";
		duracion = 0;
	}

	/**
	 * Devuelve los datos de la canción
	 */
	public void obternerDatos() {
		File mp3Archivo = new File(rutaAbsoluta);
		AudioFileFormat baseFileFormat = null;
		try {
			baseFileFormat = AudioSystem.getAudioFileFormat(mp3Archivo);
		} catch (UnsupportedAudioFileException | IOException ex) {
			// Logger.getLogger(Disco.class.getName()).log(Level.SEVERE, null,
			// ex);
		}
		if (baseFileFormat instanceof TAudioFileFormat) {
			Map properties = ((TAudioFileFormat) baseFileFormat).properties();
			titulo = (String) properties.get("title");
			artista = (String) properties.get("author");
			album = (String) properties.get("album");
			ano = (String) properties.get("date");
			comentario = (String) properties.get("comment");
			copyright = (String) properties.get("copyright");
			duracion = (long) properties.get("duration") / 1000000;
		}
	}
	

/**
 * Devuelve el nombre del archivo (canción)
 * @return nombre de la canción
 */
public String nombreArchivo(){
        String aux=new File(rutaAbsoluta).getName();
        return aux.substring(0,aux.length()-4);
    }

}
