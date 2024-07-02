/*
 * Código auxiliar para el curso de Inteligencia Artificial.
 * Se prohibe publicar o compartir soluciones.
 */
package ia.ajedrez.modelo;

import java.util.LinkedList;
import java.util.List;

/**
 * Peón.
 * @author blackzafiro
 */
public class Peón extends Pieza {
    
	/**
	 * Los peones pueden moverse dos escaques si es el primer movimiento.
	 */
    private boolean primerMovimiento = true;

    /*
     * Cantidad de movimientos que ha hecho el peón en el tablero durante la jugada. 
     */
    int numMovimientos = 0;


    public Peón(Tablero.Posición posición, Color color) {
        super(posición, color, TipoPieza.PEÓN);
    }

    @Override
    public List<Tablero.Posición> posiblesMovimientos() {
        LinkedList movimientosPeon = new LinkedList();
        Tablero.Posición pos;
        
        // Avanza uno
        if (color == Color.BLANCA) {
            pos = posición.arriba();
        } else {
            pos = posición.abajo();
        }
        if(pos != null) {
            movimientosPeon.add(pos);
        }
        
        // Avanza dos
        if(primerMovimiento) {
            if (color == Color.BLANCA) {
                pos = pos.arriba();
            } else {
                pos = pos.abajo();
            }
            if(pos != null) {
                movimientosPeon.add(pos);
            }
        }

    //Avanza en diagonal si hay una pieza enemiga
     if (color == Color.BLANCA) { //Si el peón es blanco
        pos = posición.NO();  // Avanzar en diagonal arriba a la izquierda
        if (pos != null) {
                movimientosPeon.add(pos);
        }
        pos = posición.NE();  // Avanzar en diagonal arriba a la derecha
        if (pos != null) {
                movimientosPeon.add(pos);
        }
    } else { //El peon es negro 
        pos = posición.SO();  // Avanzar en diagonal abajo a la izquierda
        if (pos != null) {
                movimientosPeon.add(pos);
        }
        pos = posición.SE();  // Avanzar en diagonal abajo a la derecha
        if (pos != null) {
                movimientosPeon.add(pos);
        }
    }
        return movimientosPeon;
    }

    @Override
    public int numMovimientos() {
        return numMovimientos;
    }

    @Override
    public void setMovimiento(int movimientos){
        int numMovimientosPeon = numMovimientos();
        numMovimientosPeon += movimientos;
    }
}
