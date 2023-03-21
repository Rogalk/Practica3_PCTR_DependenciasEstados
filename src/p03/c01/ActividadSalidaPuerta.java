/*
 * Clase ActividadSalidaPuerta
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

public class ActividadSalidaPuerta implements Runnable{

	private static final int NUMSALIDAS = 10;
	private String puerta;
	private IParque parque;

	public ActividadSalidaPuerta(String puerta, IParque parque) {
		this.puerta = puerta;
		this.parque = parque;
	}

	@Override
	public void run() {
		for (int i = 0; i < NUMSALIDAS; i ++) {
			try {
				parque.salirDelParque(puerta);
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5)*1200);
			} catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Entrada interrumpida");
				Logger.getGlobal().log(Level.INFO, e.toString());
				return;
			}
		}
		Logger.getGlobal().log(Level.INFO,"Finalizada salida por la puerta " + puerta);
	}
}
