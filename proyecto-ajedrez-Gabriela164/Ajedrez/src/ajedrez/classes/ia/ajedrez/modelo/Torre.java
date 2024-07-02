package ia.ajedrez.modelo;

import java.util.ArrayList;
import java.util.List;

import ia.ajedrez.modelo.Color;
import ia.ajedrez.modelo.Pieza;
import ia.ajedrez.modelo.Tablero;
import ia.ajedrez.modelo.TipoPieza;

public class Torre extends Pieza {

    int numMovimientos = 0;

    public Torre(Tablero.Posición posición, Color color) {
        super(posición, color, TipoPieza.TORRE);
    }

    @Override
    public List<Tablero.Posición> posiblesMovimientos() {
        List<Tablero.Posición> movimientosTorre = new ArrayList<>();
        Tablero.Posición temp = posición;

        //La torre se puede mover horizontalmente y verticalmente

        while((temp = temp.arriba()) != null) {
            movimientosTorre.add(temp);
        }

        temp = posición;
        while((temp = temp.abajo()) != null) {
            movimientosTorre.add(temp);
        }

        temp = posición;
        while((temp = temp.izquierda()) != null) {
            movimientosTorre.add(temp);
        }

        temp = posición;
        while((temp = temp.derecha()) != null) {
            movimientosTorre.add(temp);
        }
        return movimientosTorre;
    }

    @Override
    public int numMovimientos() {
        return numMovimientos;
    }

    @Override
    public void setMovimiento(int movimientos){
        int numMovimientosTorre = numMovimientos();
        numMovimientosTorre += movimientos;
    }
}
