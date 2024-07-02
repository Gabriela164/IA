/*
 * Código auxiliar para el curso de Inteligencia Artificial.
 * Se prohibe publicar o compartir soluciones.
 */
package ia.ajedrez.modelo;

import ia.ajedrez.modelo.Tablero.Posición;
import java.util.List;

/**
 * Código común a todas las piezas del ajedrez.
 * @author blackzafiro
 */
public abstract class Pieza {
    
    /**
     * Posición en el tablero.
     * Cuando es <code>null</code> quiere decir que ya se la comieron.
     */
    protected Tablero.Posición posición;
	
	/**
	 * Blanca o negra.
	 */
    protected Color color;
	
	/**
	 * Enum para caracterizar de qué pieza se trata.
	 */
    protected TipoPieza tipoPieza;
    
    /**
     * Crea una pieza en la posición y con el color indicados.
     * Copia los datos de posición en un objeto nuevo.
     * @param posición
     * @param color 
     */
    public Pieza(Posición posición, Color color, TipoPieza tipo) {
        this.posición = posición.clon();
        this.color = color;
        this.tipoPieza = tipo;
    }
    
    public Posición posición() { return posición; }
    public Color color() { return this.color; }
    public TipoPieza tipoPieza() { return tipoPieza; }
    
    /**
     * Indica si la pieza podría ser colocada en <code>pos</code> en la jugada
     * siguiente, ignorado las posiciones de otras piezas.
     * @param pos posición deseada.
     * @return si está entre los escaques alcanzables.
     */
    public boolean esVálida(Tablero.Posición pos) {
        for(Tablero.Posición p : posiblesMovimientos()) {
            if (pos.equals(p)) return true;
        }
        return false;
    }

    
    
    /**
     * Devuelve la lista de escaques a los que se puede mover la pieza sin
     * salirse de los bordes del tablero y sin tomar en cuenta las posiciones
     * de otras piezas.
     * @return 
     */
    public abstract List<Tablero.Posición> posiblesMovimientos();

    /*
     * Devuelve la cantidad de movimientos que ha realizado la pieza durante la 
     * jugada
     */
    public abstract int numMovimientos();

    /*
     * Incrementa el número de movimientos de la pieza
     */
    public void setMovimiento(int movimientos){
        //TODO: Implementar
    }
    
}
