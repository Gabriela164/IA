package recocido;

/**
 * Clase con los métodos necesarios para implementar el algoritmo
 * de recocido simulado junto con la solución a un problema particular.
 * 
 * @author Benjamin Torres
 * @author Verónica E. Arriola
 * @version 0.1
 */
public class RecocidoSimulado{

	/** Es la calificación que otorga la heurística a la solución actual. */
	private float valor;
	/** Parámetros del recocido. */
	private float temperatura;
	private float decaimiento;
	/** Solución actual. */
	private Solucion sol;


	/**
	 * Inicializa los valores necesarios para realizar el
	 * recocido simulado durante un número determinado de iteraciones.
	 * @param inicial Instancia de la clase para el problema particular que
	 * 	  se quiere resolver.  Contine la propuesta de solución inicial.
	 * @param temperatura <code>float</code> con el valor actual .
	 * @param decaimiento <code>float</code> que será usado para hacer decaer el valor de temperatura.
	 */
	public RecocidoSimulado(Solucion inicial,float temperaturaInicial, float decaimiento){ 
		sol = inicial;
		temperatura = temperaturaInicial;
		this.decaimiento = decaimiento;
	}


	/**
	 * Función que calcula una nueva temperatura en base a
	 * la anterior y el decaemiento usado.
	 * @return nueva temperatura
	 */
	public float nuevaTemperatura(){
		temperatura *= decaimiento; 	//Esto reduce gradualmente la temperatura (hasta 0) 
		if(temperatura <0.01){
			temperatura = 0;
		}
		return temperatura;
	}


	/**
	 * Genera y devuelve la solución siguiente a partir de la solución
	 * actual. Dependiendo de su valor,
	 * si es mejor o peor que la que ya se tenía,
	 * y de la probabilidad actual de elegir una solución peor, puede devolver
	 * una solución nueva o quedarse con la que ya se tenía.
	 * @return Solución nueva
	 */
	public Solucion seleccionarSiguienteSolucion(){
		Solucion candidata = sol.siguienteSolucion(); 	//Generamos una nueva solucion candidata
		float evaluacionActual = sol.evaluar();
		float evaluacionCandidata = candidata.evaluar();

		//Calculamos la probabilidad de aceptacion
		float probabilidad = calcularProbabilidad(evaluacionActual,evaluacionCandidata,temperatura);

		//Comparamos la evaluacion de la cantidata con la actual y decide si se acepta o no
		if(evaluacionCandidata < evaluacionActual || Math.random() < probabilidad){
			sol = candidata; //Acepta la nueva solucion candidata si es mejor o segun la probabilidad
		}
		return sol;
	}


	/*
	 * Metodo que calcula la probabilidad de aceptar una solucion candidata. Se calcula en funcion de la 
	 * diferencia entre la evaluacion actual y candidata,  así como la temperatura actual.
	 *
	 */
	private float calcularProbabilidad(float evaluacionActual, float evaluacionCandidata, float temperatura) {
		if (evaluacionCandidata < evaluacionActual) { // Si la solucion candidata es mejor 
			return 1.0f; //La probabilidad es 1. Indica que siempre se acepta soluciones mejores
		}
		return (float) Math.exp((evaluacionActual - evaluacionCandidata) / temperatura); 
		//Entre mayor sea la diferencia entre evaluacionActual y evaluacionCandidata menor será la probabilidad de que se acepte la   
		//solucion candidata. Y a medida que la temperatura disminuye tambien lo hace la probabilidad de aceptar soluciones peores. 
	}
	

	/**
	 * Ejecuta el algoritmo con los parámetros con los que fue inicializado y
	 * devuelve una solución.
	 * @param
	 * @return Solución al problema
	 */
	public Solucion ejecutar(int iteraciones){
		for(int i = 0;i < iteraciones; i++){
			temperatura = nuevaTemperatura(); // ACtualiza la temperatura en cada iteracion
			seleccionarSiguienteSolucion(); // Encuentra la siguiente solucion y la evalua
		}
		return sol;
	}
}
