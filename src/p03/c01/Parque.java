/**
 * Clase Parque
 * 
 * @Author: Daniel Fernández Barrientos
 * 
 * @version: 1.0
 * 
 * @fecha: 21/03/2023
 * 
 */

package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;


public class Parque implements IParque{

	
	// Constante con el numero máximo de personas
	private static final int MAXPERSONAS = 50; 
	
	// Atributos de la clase.
	private int contadorPersonasTotales;
	private int contadorPersonasHanEntrado;
	private Hashtable<String, Integer> contadoresPersonasPuertaEntrada;
	private Hashtable<String, Integer> contadoresPersonasPuertaSalida;
	
	
	// Constructor de la clase.
	public Parque() {
		contadorPersonasTotales = 0;
		contadorPersonasHanEntrado = 0;
		contadoresPersonasPuertaEntrada = new Hashtable<String, Integer>();
		contadoresPersonasPuertaSalida = new Hashtable<String, Integer>();
	}

	/**
	 * Metodo entrarAlParque().
	 * 
	 * Tras comprobar las pre-condiciones, aumenta en 1 el número de personas totales y que han entrado, además
	 * de aumentar en 1 el número de personas que han entrado por la puerta recibida como argumento.
	 * 
	 * @param String puerta: Nombre de la puerta por la que se entra
	 */
	@Override
	public synchronized void entrarAlParque(String puerta) {	
		
		// Revisamos que se cumplen las pre-condiciones para poder entrar al parque
		comprobarAntesDeEntrar(puerta);
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;
		contadorPersonasHanEntrado++;
		contadoresPersonasPuertaEntrada.put(puerta, contadoresPersonasPuertaEntrada.get(puerta)+1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
		
		// Revisamos que se cumplen las post-condiciones tras la entrada en el parque
		checkInvariante();
		
		notify();
		
	}
	
	/**
	 * Metodo salirDelParque().
	 * 
	 * Tras comprobar las pre-condiciones, disminuye en 1 el número de personas totales, además
	 * de aumentar en 1 el número de personas que han salido por la puerta recibida como argumento.
	 * 
	 * @param String puerta: Nombre de la puerta por la que se sale
	 */
	@Override
	public synchronized void salirDelParque (String puerta) {
		
		// Revisamos que se cumplen las pre-condiciones para poder salir del parque
		comprobarAntesDeSalir(puerta);
			
		// Aumentamos el contador total y el individual
		contadorPersonasTotales--;		
		contadoresPersonasPuertaSalida.put(puerta, contadoresPersonasPuertaSalida.get(puerta)+1);
				
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Salida");
			
			
		// Revisamos que se cumplen las post-condiciones tras la entrada en el parque
		checkInvariante();
		
		notify();
			
	}
	
	
	/**
	 * Metodo imprimirInfo().
	 * 
	 * Recibida una puerta y un movimiento, muestra la información asociada de entradas/salidas
	 * 
	 * @param String puerta : Nombre de la puerta de la que se quiere sacar información
	 * @param String movimiento : Movimiento del que se quiere sacar información
	 */
	private void imprimirInfo (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		if (movimiento == "Entrada") {
			System.out.println("--> Personas en el parque " + contadorPersonasTotales);
			for(String p: contadoresPersonasPuertaEntrada.keySet()){
				System.out.println("E---> Por puerta " + p + " " + contadoresPersonasPuertaEntrada.get(p));
			} 
			
		// Iteramos por todas las puertas e imprimimos sus salidas
		} else {
			System.out.println("--> Personas en el parque " + contadorPersonasTotales);
			for(String p: contadoresPersonasPuertaSalida.keySet()){
				System.out.println("S---> Por puerta " + p + " " + contadoresPersonasPuertaSalida.get(p));
			}
		}
		
		System.out.println(" ");
	}
	
	/**
	 * Metodo sumarContadoresPuerta()
	 * 
	 * Dado un movimiento, devuelve el número de personas que han entrado o salido por cada puerta
	 * 
	 * @param String movimiento : Parámetro que indica si se quieren calcular los contadores de las puertas de entrada o de salida.
	 * @return sumaContadoresPuerta : Numero de personas que han entrado o salido por las puertas
	 */
	private int sumarContadoresPuerta(String movimiento) {
		int sumaContadoresPuerta = 0;
		
		if (movimiento == "Entrada") {
			Enumeration<Integer> iterPuertas = contadoresPersonasPuertaEntrada.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		} else {
			Enumeration<Integer> iterPuertas = contadoresPersonasPuertaSalida.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		}

		return sumaContadoresPuerta;
	}
	
	
	/**
	 * Metodo checkInvariante().
	 * 
	 * POST-CONDICIONES - Siempre se tienen que dar los assert indicados.
	 * 
	 */
	protected void checkInvariante() {
		assert sumarContadoresPuerta("Entrada") == contadorPersonasHanEntrado : "INV: La suma de contadores de las puertas de entrada debe ser igual al valor del contador de personas que han entrado";
		assert sumarContadoresPuerta("Salida") <= sumarContadoresPuerta("Entrada") : "INV: No puede haber más salidas que entradas";
		assert sumarContadoresPuerta("Entrada") - sumarContadoresPuerta("Salida") == contadorPersonasTotales : "INV: Las personas que han entrado menos las que han salido tiene que ser al contador del parque";
		assert contadorPersonasTotales <= MAXPERSONAS : "INV: Nunca puede haber más de 50 personas en el parque";
	}

	/**
	 * Metodo comprobarAntesDeEntrar().
	 * 
	 * PRE-CONDICIONES : Condiciones que se tienen que dar antes de que el método entradaAlParque() pueda continuar su ejecución
	 * 
	 * @param String puerta : puerta que se va a comprobar
	 */
	protected  void comprobarAntesDeEntrar(String puerta){
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuertaEntrada.get(puerta) == null){
			contadoresPersonasPuertaEntrada.put(puerta, 0);
		}
		
		// Antes de permitir la entrada hay que comprobar que no el parque no esté lleno
		if (contadorPersonasTotales < MAXPERSONAS) {
			return; // se puede pasar
		}
		while (contadorPersonasTotales == MAXPERSONAS) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			}
		}
	}

	
	/**
	 * Metodo comprobarAntesDeSalir().
	 * 
	 * PRE-CONDICIONES : Condiciones que se tienen que dar antes de que el método salidaDelParque() pueda continuar su ejecución
	 * 
	 * @param String puerta : puerta que se va a comprobar
	 */
	protected  void comprobarAntesDeSalir(String puerta){
		
		// Si no hay salidas por esa puerta, inicializamos
		if (contadoresPersonasPuertaSalida.get(puerta) == null){
			contadoresPersonasPuertaSalida.put(puerta, 0);
		}
		
		// Si no hay entradas por esa puerta y se intenta salir antes que entrar, la inicializamos
		//	if (contadoresPersonasPuertaEntrada.get(puerta) == null){
		//		contadoresPersonasPuertaEntrada.put(puerta, 0);
		//	}
		
		if (contadoresPersonasPuertaEntrada.get(puerta) != null && contadoresPersonasPuertaEntrada.get(puerta) >= 1  && contadorPersonasTotales >= 1) {
			return;
		}
		
		while (contadoresPersonasPuertaEntrada.get(puerta) == null || contadorPersonasTotales < 1) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			}
		}
	}


}
