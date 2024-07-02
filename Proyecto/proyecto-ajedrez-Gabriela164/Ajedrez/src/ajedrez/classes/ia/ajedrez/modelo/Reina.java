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
public class Reina extends Pieza {

    int numMovimientos = 0;

    
    public Reina(Tablero.Posición posición, Color color) {
        super(posición, color, TipoPieza.REINA);
    }

    @Override
    public List<Tablero.Posición> posiblesMovimientos() {
        LinkedList l = new LinkedList();
        Tablero.Posición temp = posición;
        while((temp = temp.arriba()) != null) {
            l.add(temp);
        }
        temp = posición;
        while((temp = temp.abajo()) != null) {
            l.add(temp);
        }
        temp = posición;
        while((temp = temp.izquierda()) != null) {
            l.add(temp);
        }
        temp = posición;
        while((temp = temp.derecha()) != null) {
            l.add(temp);
        }
        temp = posición;
        while((temp = temp.NE()) != null) {
            l.add(temp);
        }
        temp = posición;
        while((temp = temp.SE()) != null) {
            l.add(temp);
        }
        temp = posición;
        while((temp = temp.SO()) != null) {
            l.add(temp);
        }
        temp = posición;
        while((temp = temp.NO()) != null) {
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
        int numMovimientosReina = numMovimientos();
        numMovimientosReina += movimientos;
    }
    
}
