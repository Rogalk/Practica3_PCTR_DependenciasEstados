/**
 * Clase ActividadEntradaPuerta
 * 
 * @Author: Daniel Fernández Barrientos
 * 
 * @version: 1.0
 * 
 * @fecha: 21/03/2023
 * 
 */

package src.p03.c01;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActividadEntradaPuerta implements Runnable{

	// Constante con el numero de entradas
	private static final int NUMENTRADAS = 20;
	
	// Atributos de la clase
	private String puerta;
	private IParque parque;

	// Constructor de clase
	public ActividadEntradaPuerta(String puerta, IParque parque) {
		this.puerta = puerta;
		this.parque = parque;
	}

	/**
	 * Metodo run()
	 * 
	 * Sustituye al heredado de Runnable.
	 * 
	 * Realiza entradas en el parque por la "puerta" que corre en este hilo hasta un total de las definidas en NUMENTRADAS
	 * 
	 * Al final, lanza un log cuando se ha terminado la ejecución del hilo
	 */
	@Override
	public void run() {
		for (int i = 0; i < NUMENTRADAS; i ++) {
			try {
				parque.entrarAlParque(puerta);
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5)*1000);
			} catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Entrada interrumpida");
				Logger.getGlobal().log(Level.INFO, e.toString());
				return;
			}
		}
		Logger.getGlobal().log(Level.INFO,"Finalizada entrada por la puerta " + puerta);
	}

}
