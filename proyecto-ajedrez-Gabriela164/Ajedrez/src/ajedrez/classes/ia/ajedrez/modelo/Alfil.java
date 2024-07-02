
package ia.ajedrez.modelo;

import java.util.ArrayList;
import java.util.List;

import ia.ajedrez.modelo.Color;
import ia.ajedrez.modelo.Pieza;
import ia.ajedrez.modelo.Tablero;
import ia.ajedrez.modelo.TipoPieza;



public class Alfil extends Pieza {

    int numMovimientos = 0;

    Alfil(Tablero.Posición posición, Color color) {
        super(posición, color, TipoPieza.ALFIL);
    }

    @Override
    public List<Tablero.Posición> posiblesMovimientos() {
        List<Tablero.Posición> movimientosAlfil = new ArrayList<>();
        Tablero.Posición temp = posición;

        //El alfil se puede mover en diagonal

        while((temp = temp.NE()) != null) {
            movimientosAlfil.add(temp);
        }
        temp = posición;
        while((temp = temp.SE()) != null) {
            movimientosAlfil.add(temp);
        }
        temp = posición;
        while((temp = temp.SO()) != null) {
            movimientosAlfil.add(temp);
        }
        temp = posición;
        while((temp = temp.NO()) != null) {
            movimientosAlfil.add(temp);
        }
        return movimientosAlfil;
    }
    
    @Override
    public int numMovimientos() {
        return numMovimientos;
    }

    @Override
    public void setMovimiento(int movimientos){
        int numMovimientosAlfil = numMovimientos();
        numMovimientosAlfil += movimientos;
    }
}