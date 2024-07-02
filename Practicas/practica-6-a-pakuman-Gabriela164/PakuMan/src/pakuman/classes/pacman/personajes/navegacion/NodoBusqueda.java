/*
 * Código utilizado para el curso de Inteligencia Artificial.
 * Se permite consultarlo para fines didácticos en forma personal,
 * pero no esta permitido transferirlo resuelto a estudiantes actuales o potenciales.
 */
package pacman.personajes.navegacion;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.LinkedList;
import pacman.personajes.Movimiento;

/**
 * Clase que modela a los nodos de nuestra gráfica de búsqueda.
 * @author blackzafiro
 * @author baruch
 */
public class NodoBusqueda implements Comparable<NodoBusqueda> {
	
	private final static Logger LOGGER = Logger.getLogger("pacman.personajes.navegacion.NodoBusqueda");
	static { LOGGER.setLevel(Level.OFF); }
	
	private NodoBusqueda padre;     // Nodo que generó este nodo.
    private Movimiento accionPadre; // Acción que llevo al agente a este estado.
    private Estado estado;          // Referencia del estado al que se llego.
    private int gn;                 // costo de llegar a este nodo.
    
	/**
	 * Nodo que considera al estado indicado.
	 * @param estado 
	 */
    public NodoBusqueda(Estado estado){
        this.estado = estado;
    }
	
	/**
	 * Devuelve el estado en este nodo.
	 */
	public Estado estado() {
		return estado;
	}
	
	/**
	 * Devuelve la acción que permite llegar a este nodo.
	 * @return 
	 */
	public Movimiento accionPadre() {
		return accionPadre;
	}
	
	/**
	 * Devuelve al nodo búsqueda que generó a este nodo como sucesor.
	 * @return nodo antecesor en la solución.
	 */
	public NodoBusqueda padre() {
		return padre;
	}
	
	/**
	 * Devuelve la distancia de la mejor ruta conocida hasta el momento.
	 * @return Valor de la función g(n).
	 */
	public int gn() {
		return gn;
	}
    
    /**
     * Función para obtener el estado de la función fn.
     * @return Valor de la función f(n).
     */
    public int fn(){
        return gn + estado.hn();
    }
    
    /**
     * Obtenemos todos los sucesores posibles respecto al estado actual del nodo.
     * @return Lista con los sucesores al nodo actual.
     */
    public LinkedList<NodoBusqueda> getSucesores() {
		LinkedList<NodoBusqueda> sucesores = new LinkedList<>();
	
		// Iteramos a través de los posibles movimientos (arriba, abajo, izquierda, derecha)
		for (Movimiento movimiento : Movimiento.values()) {
			// Obtenemos el estado sucesor al aplicar el movimiento desde el estado actual
			Estado sucesor = estado.aplicaAccion(movimiento);
	
			if (sucesor != null) {
				// Calculamos el costo acumulado g(n) para el sucesor sumando el costo del movimiento (10)
				int gnSucesor = gn + movimiento.costo();	
				// Creamos un nuevo NodoBusqueda para el sucesor
				NodoBusqueda nodoSucesor = new NodoBusqueda(sucesor);
				nodoSucesor.padre = this; // Actualizamos. El nodo padre ahora sera el nodo actual
				nodoSucesor.accionPadre = movimiento; //El movimiento actual sera la accion padre del nodo sucesor
				nodoSucesor.gn = gnSucesor;  //Asignamos el costo acumulado al nodo sucesor
				sucesores.add(nodoSucesor); 
			}
		}
		return sucesores;
	}
    
    @Override
    public int compareTo(NodoBusqueda nb){
        return fn() - nb.fn();
    }
    
    @Override
    public boolean equals(Object o){
		if (!(o instanceof NodoBusqueda) || o == null) return false;
        NodoBusqueda otro = (NodoBusqueda)o;
        return estado.equals(otro.estado);
    }
}
