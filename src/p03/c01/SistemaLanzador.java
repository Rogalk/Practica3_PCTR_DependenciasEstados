/**
 * Clase SistemaLanzador
 * 
 * @Author: Daniel Fernández Barrientos
 * 
 * @version: 1.0
 * 
 * @fecha: 21/03/2023
 * 
 */


package src.p03.c01;

public class SistemaLanzador {
	
	/**
	 * main().
	 * 
	 * Ejecuta la simulación de entradas o salidas.
	 * 
	 * @param args : Se tiene que hacer Run configurations.. y poner 5 en argumentos (5 puertas) y -ea en atributos.
	 */
	public static void main(String[] args) {
		
		IParque parque = new Parque();
		char letra_puerta = 'A';
		
		System.out.println("¡Parque abierto!");
		
		for (int i = 0; i < Integer.parseInt(args[0]); i++) {
			
			String puerta = ""+((char) (letra_puerta++));
			
			// Creación de hilos de entrada
			ActividadEntradaPuerta entradas = new ActividadEntradaPuerta(puerta, parque);
			new Thread (entradas).start();
			
			// Creación de hilos de salida
			ActividadSalidaPuerta salidas = new ActividadSalidaPuerta(puerta, parque);
			new Thread (salidas).start();
			
			
		}
	}	
}
