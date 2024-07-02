/*
 * Código auxiliar para el curso de Inteligencia Artificial.
 * Se prohibe publicar o compartir soluciones.
 */
package ia.ajedrez.modelo;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author blackzafiro
 */
public class Rey extends Pieza {

    /*
     * Cantidad de movimientos que ha realizado el rey durante la jugada
     */
    int numMovimientos = 0;

    public Rey(Tablero.Posición posición, Color color) {
        super(posición, color, TipoPieza.REY);
    }

    @Override
    public List<Tablero.Posición> posiblesMovimientos() {
        LinkedList<Tablero.Posición> l = new LinkedList<>();
        Tablero.Posición temp;
        if((temp = posición.arriba()) != null) {
            l.add(temp);
        }
        if((temp = posición.abajo()) != null) {
            l.add(temp);
        }
        if((temp = posición.izquierda()) != null) {
            l.add(temp);
        }
        if((temp = posición.derecha()) != null) {
            l.add(temp);
        }
        return l;
    } 

    @Override
    public int numMovimientos() {
        return numMovimientos;
    }

    @Override
    public void setMovimiento(int movimientos){
        int numMovimientosRey = numMovimientos();
        numMovimientosRey += movimientos;
    }
   
}
