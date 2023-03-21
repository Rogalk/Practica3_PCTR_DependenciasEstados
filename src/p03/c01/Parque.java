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
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	
	
	public Parque() {
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
	}


	@Override
	public  void entrarAlParque(String puerta){		// TODO
		
		// Revisamos que se cumplen las pre-condiciones para poder entrar al parque
		comprobarAntesDeEntrar();
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)+1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
		
		// TODO
		
		// Revisamos que se cumplen las post-condiciones tras la entrada en el parque
		//checkInvariante();
		
	}
	
	
	@Override
	public synchronized void salirDelParque (String puerta) throws InterruptedException {
		
		// Revisamos que se cumplen las pre-condiciones para poder salir del parque
		comprobarAntesDeSalir();
			
		// Aumentamos el contador total y el individual
		contadorPersonasTotales--;		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)-1);
		
		while (contadoresPersonasPuerta.get(puerta) == null || contadoresPersonasPuerta.get(puerta) < 1){
			TimeUnit.MILLISECONDS.sleep(1000);
		}
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Salida");
			
			
		// Revisamos que se cumplen las post-condiciones tras la entrada en el parque
		//checkInvariante();
			
	}
	
	
	private void imprimirInfo (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); // + " tiempo medio de estancia: "  + tmedio);
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		for(String p: contadoresPersonasPuerta.keySet()){
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));
		}
		System.out.println(" ");
	}
	
	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		return sumaContadoresPuerta;
	}
	
	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
		// TODO 
		// TODO
		
		
		
	}

	protected void comprobarAntesDeEntrar(){
		//
		// TODO
		//
	}

	protected void comprobarAntesDeSalir(){
		//
		// TODO
		//
	}


}
