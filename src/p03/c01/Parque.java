// NADA

package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Parque implements IParque{


	// TODO
	private static final int MAXPERSONAS = 50; //Número máximo de personas en el parque
	private int contadorPersonasTotales;
	private int contadorPersonasHanEntrado;
	private Hashtable<String, Integer> contadoresPersonasPuertaEntrada;
	private Hashtable<String, Integer> contadoresPersonasPuertaSalida;
	
	
	public Parque() {
		contadorPersonasTotales = 0;
		contadorPersonasHanEntrado = 0;
		contadoresPersonasPuertaEntrada = new Hashtable<String, Integer>();
		contadoresPersonasPuertaSalida = new Hashtable<String, Integer>();
	}


	@Override
	public synchronized void entrarAlParque(String puerta){		// TODO
		
		// Revisamos que se cumplen las pre-condiciones para poder entrar al parque
		comprobarAntesDeEntrar();
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuertaEntrada.get(puerta) == null){
			contadoresPersonasPuertaEntrada.put(puerta, 0);
		}
		
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;
		contadorPersonasHanEntrado++;
		contadoresPersonasPuertaEntrada.put(puerta, contadoresPersonasPuertaEntrada.get(puerta)+1);
		
		// Imprimimos el estado del parque
		imprimirInfoEntrada(puerta, "Entrada");
		
		// TODO
		
		// Revisamos que se cumplen las post-condiciones tras la entrada en el parque
		checkInvariante();
		
		notifyAll();
		
	}
	
	
	@Override
	public synchronized void salirDelParque (String puerta) throws InterruptedException {
		
		// Revisamos que se cumplen las pre-condiciones para poder salir del parque
		comprobarAntesDeSalir(puerta);
			
		// Aumentamos el contador total y el individual
		contadorPersonasTotales--;		
		contadoresPersonasPuertaSalida.put(puerta, contadoresPersonasPuertaSalida.get(puerta)+1);
		
		//while (contadoresPersonasPuertaSalida.get(puerta) == null || contadoresPersonasPuertaSalida.get(puerta) < 1){
		//	TimeUnit.MILLISECONDS.sleep(1000);
		//}
		
		// Imprimimos el estado del parque
		imprimirInfoSalida(puerta, "Salida");
			
			
		// Revisamos que se cumplen las post-condiciones tras la entrada en el parque
		checkInvariante();
		
		notifyAll();
			
	}
	
	
	private void imprimirInfoEntrada (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); // + " tiempo medio de estancia: "  + tmedio);
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		for(String p: contadoresPersonasPuertaEntrada.keySet()){
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuertaEntrada.get(p));
		}
		System.out.println(" ");
	}
	
	private void imprimirInfoSalida (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); // + " tiempo medio de estancia: "  + tmedio);
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		for(String p: contadoresPersonasPuertaSalida.keySet()){
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuertaSalida.get(p));
		}
		System.out.println(" ");
	}
	
	private int sumarContadoresPuertaEntrada() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuertaEntrada.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		return sumaContadoresPuerta;
	}
	
	private int sumarContadoresPuertaSalida() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuertaSalida.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		return sumaContadoresPuerta;
	}
	
	protected void checkInvariante() {
		assert sumarContadoresPuertaEntrada() == contadorPersonasHanEntrado : "INV: La suma de contadores de las puertas de entrada debe ser igual al valor del contador de personas que han entrado";
		assert sumarContadoresPuertaSalida() <= sumarContadoresPuertaEntrada() : "INV: No puede haber más salidas que entradas";
		assert sumarContadoresPuertaEntrada() - sumarContadoresPuertaSalida() == contadorPersonasTotales : "INV: Las personas que han entrado menos las que han salido tiene que ser al contador del parque";
		// TODO 
		// TODO
		
		
		
	}

	protected synchronized void comprobarAntesDeEntrar(){
		
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

	protected synchronized void comprobarAntesDeSalir(String puerta){
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuertaSalida.get(puerta) == null){
			contadoresPersonasPuertaSalida.put(puerta, 0);
		}
		
		if (contadoresPersonasPuertaEntrada.get(puerta) == null){
			contadoresPersonasPuertaEntrada.put(puerta, 0);
		}
		
		if (contadoresPersonasPuertaEntrada.get(puerta) >= 1 && contadorPersonasTotales >= 1) {
			return;
		}
		
		while (contadoresPersonasPuertaEntrada.get(puerta) < 1 || contadorPersonasTotales < 1) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			}
		}
	}


}
