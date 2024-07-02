/*
 * Código auxiliar para el curso de Inteligencia Artificial.
 * Se prohibe publicar o compartir soluciones.
 */
package ia.ajedrez.modelo;

import java.util.LinkedList;
import java.util.List;

/**
 * Caballo
 * @author blackzafiro
 */
public class Caballo extends Pieza {
    /*
     * Cantidad de movimientos que ha hecho el caballo en el tablero durante la jugada. 
     */
    int numMovimientos = 0;

    public Caballo(Tablero.Posición posición, Color color) {
        super(posición, color, TipoPieza.CABALLO);
    }

    @Override
    public List<Tablero.Posición> posiblesMovimientos() {
        LinkedList l = new LinkedList();
        int renglón = posición.renglón();
        char col = (char)(posición.columna() + 1);
		
		Tablero.Posición temp;
        if((temp = posición.relativa(renglón + 2, col)) != null){
            l.add(temp);
        }
        if((temp = posición.relativa(renglón - 2, col)) != null){
            l.add(temp);
        }
        
        col = (char)(posición.columna() - 1);
        if((temp = posición.relativa(renglón + 2, col)) != null){
            l.add(temp);
        }
        if((temp = posición.relativa(renglón - 2, col)) != null){
            l.add(temp);
        }
        
        col = (char)(posición.columna() + 2);
        if((temp = posición.relativa(renglón + 1, col)) != null){
            l.add(temp);
        }
        if((temp = posición.relativa(renglón - 1, col)) != null){
            l.add(temp);
        }
        
        col = (char)(posición.columna() - 2);
        if((temp = posición.relativa(renglón + 1, col)) != null){
            l.add(temp);
        }
        if((temp = posición.relativa(renglón - 1, col)) != null){
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
        int numMovimientosCaballo = numMovimientos();
        numMovimientosCaballo += movimientos;
    }
    
}
