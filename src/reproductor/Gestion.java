package reproductor;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Gestion {
	/**
	 * Abre un archivo
	 * @param texto Descripción de la extensión
	 * @param extension Extensión del archivo
	 * @return array de cadenas relleno en caso de encontrar un archivo o null en caso de que no se haya podido abrir
	 */
	public String[] abrirArchivo(String texto, String extension) {
		JFileChooser dialogo = new JFileChooser();
		dialogo.setMultiSelectionEnabled(true);
		dialogo.setFileFilter(new FileNameExtensionFilter(texto, extension));
		int opcion = dialogo.showOpenDialog(null);
		if (opcion == JFileChooser.APPROVE_OPTION) {
			File Archivos[] = dialogo.getSelectedFiles();
			String Ubicaciones[] = new String[Archivos.length];
			int i = 0;
			for (File Aux : Archivos) {
				Ubicaciones[i] = Aux.getPath();
				i++;
			}
			return Ubicaciones;
		}
		return null;
	}

	/**
	 * Abre una carpeta
	 * @return lista de canciones si habia algo, null en caso contrario
	 */
	public String[] abrirCarpeta() {
		JFileChooser dialogo = new JFileChooser();
		dialogo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int opcion = dialogo.showOpenDialog(null);
		if (opcion == JFileChooser.APPROVE_OPTION) {
			String ruta = dialogo.getSelectedFile().getPath();
			File directorio = new File(dialogo.getSelectedFile().getPath());
			String listaDirectorio[] = directorio.list();
			int tamano = listaDirectorio.length;
			for (int i = 0; i < tamano; i++) {
				listaDirectorio[i] = ruta + "\\" + listaDirectorio[i];
			}
			return listaDirectorio;
		} else {
			return null;
		}
	}

	/**
	 * Comprueba si la extensión del archivo sea mp3
	 * @param ruta Ruta del archivo
	 * @return true si tiene la extension, false si no la tiene
	 */
	public boolean esMp3(String ruta) {
		String ext = "";
		boolean enc = false;
		int tam = ruta.length();
		for (int i = 0; i < tam; i++) {
			if (ruta.charAt(i) == '.') {
				enc = true;
				ext = "";
			} else if (enc) {
				ext += ruta.charAt(i);
			}
		}
		return "mp3".equalsIgnoreCase(ext);
	}

	/**
	 * Define un formato de tiempo y lo ajusta para minutos y segundos
	 * @param tsegundo Cantidad a formatear
	 * @return tiempo formateado
	 */
	public String formatoReloj(long tsegundo) {
		long segundo = tsegundo % 60;
		long minuto = tsegundo / 60;
		String tSeg = "" + segundo;
		String tMin = "" + minuto;
		if (segundo < 10) {
			tSeg = "0" + segundo;
		}
		if (minuto < 10) {
			tMin = "0" + minuto;
		}
		return tMin + ":" + tSeg;
	}

	/**
	 * Devuelve la ruta del archivo en el sistema
	 * @return ruta del archivo en el sistema
	 */
	public static String ruta() {
		return System.getProperty("user.home") + "/Reproductor";
	}
}
