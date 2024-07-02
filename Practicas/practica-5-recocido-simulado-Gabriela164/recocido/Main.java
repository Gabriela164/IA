package recocido;

import static java.lang.System.out;   
import java.io.File;


/**
 * Clase para ejecutar un proceso de optimización usando recocido simulado.
 * @author Benjamin Torres Saavedra
 * @author Verónica E. Arriola
 * @version 0.1
 */
public class Main{
	/**
	 * Recibe la dirección de un archivo .tsp y utiliza recocido simulado
	 * para encontrar una solución al problema del agente viajero en esa
	 * ciudad.
	 * El programa se podrá ejecutar como:
	 * java recocido.Main <archivo.tsp>
	 * @param args Nombre del archivo tsp.
	 */
	public static void main(String[] args){
		int iteracciones = 100;

		if(args.length < 1){
			out.println("Uso:  java -cp classes recocido.Main ./data/dj38.tsp ");
			return;
		}

		//Guardamos en un String con la ruta del archivo tsp
		String archivoTSPPath = args[0];
		DatosPAV datosCiudades = new DatosPAV(new File(archivoTSPPath));

		//Crear una instancia de la clase hija de Solucion (SolucionAgente)
		Solucion solucionInicial = new SolucionAgente(datosCiudades);

		float temperaturaInicial = 1000.0f;
		float decaimiento = 0.99f;

		//Crear una instancia de la clase RecocidoSimulado con parametros iniciales 
		RecocidoSimulado recocido = new RecocidoSimulado(solucionInicial, temperaturaInicial, decaimiento);
		Solucion mejorSolucion = recocido.ejecutar(iteracciones);

		//Mostramos la mejor solucion encontrada
		System.out.println("Mejor solucion encontrada fue:");
		System.out.println(mejorSolucion);
	}
}
