package reproductor;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
/**
 * Clase utilizada para crear un hilo
 * @author Pablo Durán
 *
 */
public class Hilo extends Thread {
	private int contador;
	public int tiempoCancion;
	private JProgressBar barraProgreso;
	private JLabel lbTiempo;

	/**
	 * Constructor de la clase Hilo
	 * @param tiempoCancion tiempo de la canción
	 * @param barraProgreso Barra de progreso de la canción
	 * @param lbTiempo Tiempo total de la canción
	 */
	public Hilo(int tiempoCancion, JProgressBar barraProgreso, JLabel lbTiempo) {
		contador = 0;
		this.barraProgreso = barraProgreso;
		this.tiempoCancion = tiempoCancion;
		this.lbTiempo = lbTiempo;
	}

	/**
	 * Formatea la fecha para que coincida con: 00:00
	 * @param tSegundo Tiempo en segundos
	 * @return fecha formateada
	 */
	public String formatoReloj(long tSegundo) {
		long segundo = tSegundo % 60;
		long minuto = tSegundo / 60;
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
	 * Ejecuta internamente el hilo
	 */
	@Override
	public void run() {
			while (contador < tiempoCancion) {
				++contador;
				lbTiempo.setText(formatoReloj(contador));
				barraProgreso.setValue(contador);
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
	}

}
