/*
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

		private static final int NUMENTRADAS = 20;
		private String puerta;
		private IParque parque;

		public ActividadEntradaPuerta(String puerta, IParque parque) {
			this.puerta = puerta;
			this.parque = parque;
		}

		@Override
		public void run() {
			for (int i = 0; i < NUMENTRADAS; i ++) {
				try {
					parque.entrarAlParque(puerta);
					TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5)*500);
				} catch (InterruptedException e) {
					Logger.getGlobal().log(Level.INFO, "Entrada interrumpida");
					Logger.getGlobal().log(Level.INFO, e.toString());
					return;
				}
			}
		}

}
