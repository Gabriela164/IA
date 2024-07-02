/*
 * Código utilizado para el curso de Inteligencia Artificial.
 * Se permite consultarlo para fines didácticos en forma personal,
 * pero no esta permitido transferirlo resuelto a estudiantes actuales o potenciales.
 */
package pacman.personajes.navegacion;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import javafx.scene.paint.Color;
import pacman.personajes.Movimiento;

/**
 * Clase donde se define en algoritmo de A* para que se use en el fantasma.
 * @author baruch
 * @author blackzafiro
 */
public class AEstrella extends Algoritmo {
	
	private final static Logger LOGGER = Logger.getLogger("pacman.personajes.navegacion.AEstrella");
	static { LOGGER.setLevel(Level.FINE); }
	
	private PriorityQueue<NodoBusqueda> listaAbierta;   // Cola de prioridad de donde obtendremos los nodos
	                                                    // sobre los que se realizará el algoritmo.

	private HashMap<Estado, Estado> listaCerrada;       // Tabla de dispersión donde se agregan todos los estados
                                                        // que se terminó de revisar.

    private Estado estadoFinal;                         // Casilla donde se encuentra pacman.

    private boolean terminado;                          // Define si nuestro algoritmo ha terminado.

    private NodoBusqueda nodoSolucion;                  // Nodo a partir del cual se define la solución,
	                                                    // porque ya se encontró la mejor rutal al estado meta.
	
    /**
     * Inicializador del algoritmo.
	 * Se debe mandar llamar cada vez que cambien el estado incial y el estado
	 * final.
     * @param estadoInicial Pasillo donde se encuentra el fantasma.
     * @param estadoFinal Pasillo donde se encuentra pacman.
     */
    private void inicializa(Estado estadoInicial, Estado estadoFinal){
		this.estadoFinal = estadoFinal;
        this.terminado = false;
		this.nodoSolucion = null;
        this.listaAbierta = new PriorityQueue<>();
        this.listaCerrada = new HashMap<>();
        estadoInicial.calculaHeuristica(estadoFinal);
        this.listaAbierta.offer(new NodoBusqueda(estadoInicial));
    }
    
    /**
     * Función que realiza un paso en la ejecución del algoritmo.
     */
    private void expandeNodoSiguiente(){
        //Obtenemos el nodo con el menor f(n)
        NodoBusqueda nodoActual = listaAbierta.poll(); 
        //Verificamos si el nodo es el estado final 
        if(nodoActual.estado().equals(estadoFinal)){
            terminado = true;
            nodoSolucion = nodoActual;
            return;
        }
        //En caso contrario, expandimos el nodo actual
        for(NodoBusqueda sucesor : nodoActual.getSucesores()){
            Estado estadoSucesor = sucesor.estado();            
            estadoSucesor.calculaHeuristica(estadoFinal); 

            if(listaCerrada.containsKey(estadoSucesor)){  
                continue; //Se omite y se continua con el siguiente sucesor
            }

            //Verificamos si el sucesor se encuentra en listaAbierta o si su f(n) es menor al primer elemento de la lista de prioridad
            if(!listaAbierta.contains(sucesor) || sucesor.fn() < listaAbierta.peek().fn()){
                listaAbierta.offer(sucesor); // Agregamos el sucesor a la lista abierta
            }
            //Agregamos el estado actual a la lista cerrada y lo marcamos como visitado
            listaCerrada.put(nodoActual.estado(), nodoActual.estado()); 
        }	
    }
	
	/**
	 * Se puede llamar cuando se haya encontrado la solución para obtener el
	 * plan desde el nodo inicial hasta la meta.
	 * @return secuencia de movimientos que llevan del estado inicial a la meta.
	 */
	private LinkedList<Movimiento> generaTrayectoria() {

		LinkedList<Movimiento> trayectoria = new LinkedList<>();
        NodoBusqueda temp = nodoSolucion;

        //Generamos la trayectoria desde el nodo solucion hasta el nodo inicial
        while(temp.padre() != null){
            if(temp.accionPadre() != null){
                //Agregamos la accion del padre al inicio de la lista
                trayectoria.addFirst(temp.accionPadre()); 
            }
            temp = temp.padre(); 
        }
        //Devolvemos la trayectoria en orden desde el nodo inicial hasta el nodo solucion
        return trayectoria;
	}
	
	/**
	 * Pinta las celdas desde el nodo solución hasta el nodo inicial
	 */
	private void pintaTrayectoria(Color color) {
		if (nodoSolucion == null) return;
		NodoBusqueda temp = nodoSolucion.padre();
		while(temp.padre() != null) {
			temp.estado().pintaCelda(color);
			temp = temp.padre();
		}
	}
    
    /**
     * Función que ejecuta A* para determinar la mejor ruta desde el fantasma,
	 * cuya posición se encuetra dentro de <code>estadoInicial</code>, hasta
	 * Pacman, que se encuentra en <code>estadoFinal</code>.
	 * @return Una lista con la secuencia de movimientos que Sombra debe
	 *         ejecutar para llegar hasta PacMan.
     */
	@Override
    public LinkedList<Movimiento> resuelveAlgoritmo(Estado estadoInicial, Estado estadoFinal){
		
		// Inicializa el algoritmo.
        inicializa(estadoInicial, estadoFinal);

        //Ejecuta el algoritmo hasta encontrar la solucion
        while(!terminado && !listaAbierta.isEmpty()){
            expandeNodoSiguiente();
        }

        //Genera la trayectoria desde el nodo solucion hasta el nodo inicial
        LinkedList<Movimiento> trayectoria = generaTrayectoria();
        //Pintamos la trayectoria de color azul 
        pintaTrayectoria(Color.BLUE);

        return trayectoria;
    }
	
}