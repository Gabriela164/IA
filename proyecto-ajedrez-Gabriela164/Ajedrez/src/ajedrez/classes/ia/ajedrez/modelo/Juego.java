/*
 * Código auxiliar para el curso de Inteligencia Artificial.
 * Se prohibe publicar o compartir soluciones.
 */
package ia.ajedrez.modelo;

import java.util.List;

import ia.ajedrez.modelo.Tablero.Posición;


/**
 * Clase que lleva el control del juego: turnos y jugadas a considerar.
 * @author blackzafiro
 */
public class Juego {
	
	/**
	 * Número de escaques por lado del tablero.
	 */
    public static final int NUM_ESCAQUES = 8;
     
	/**
	 * Estado de las piezas en la jugada actual.
	 */
    private Tablero tableroActual;
	
	/**
	 * Jugador al que le toca mover actualmente.
	 */
    private Color turno;
    
	/**
	 * Crea un tablero con las piezas en el estado inicial.
	 * Juegan primero las blancas.
	 */
    public Juego() {
        tableroActual = new Tablero();
        turno = Color.BLANCA;
    }
    
	/**
	 * Devuelve una referencia al estado actual del tablero.
	 * @return objeto tablero.
	 */
    public Tablero tableroActual() {
        return tableroActual;
    }
    
    /**
     * Revisa si la pieza pertenece al jugador en turno en el tablero actual
     * y si es posible colocarla en la posición solicitada.
     * @param origen posición inicial de la pieza a mover.
     * @param destino posición donde se desea colocar la pieza.
     * @return si esa jugada es válida en el estado actual del juego.
     */
    public boolean jugadaEsVálida(Posición origen, Posición destino) {
        //Obtenemos las piezas dadas sus posiciones del tablero 
        Pieza piezaOrigen = obténPieza(origen);
        Pieza piezaDestino = obténPieza(destino);

        //Primero verificamos si la pieza origen pertenece al jugador en turno. Si no es su turno, no puede moverla.
        //Tambien verificamos que la pieza origen no sea null, ya que si es null, no hay pieza en esa posicion.
        if (piezaOrigen.color() != turno  || piezaOrigen == null) {
            return false;
        }

        // La casilla de origen y destino son la misma.
        if (origen.equals(destino)) {
            return false;
        }
        
        // La casilla de destino contiene una pieza del mismo color.
        if (piezaDestino != null && piezaDestino.color() == turno) {
            return false;
        }
    
        //Movimiento. La posicion destino es un posible movimiento de la pieza origen y esta disponible
        if ( piezaOrigen.esVálida(destino) && piezaDestino == null) {
            return true;
        } 

        //Captura. La posicion destino contiene una pieza del jugador contrario y es un posible movimiento de la pieza origen
        if (piezaOrigen.esVálida(destino) && piezaDestino != null && piezaDestino.color() != turno) {
            return true;
        }

        //Enroque. El rey y la torre intercambian de lugar
        if (esEnroqueCortoValido(origen, destino)) {
            return true;
        }

        if(esEnroqueLargoValido(origen, destino)) {
            return true;
        }

        return false; // La jugada no coincide con ningún tipo válido.
    }

    /*
     * Metodo auxiliar esSuPrimerMovimiento. Verifica si la pieza dada es su primer movimiento
     * @param pieza. Pieza a verificar
     */
    public boolean esSuPrimerMovimiento (Pieza pieza) {
        if(pieza.numMovimientos() == 0){
            return true;    
        }else{
            return false;
        }
    }
    
    /*
     * esEnroqueCorto. Verifica si el rey y la torre pueden intercambiar de posicion
     * @param origen. Posicion del REY
     * @param destino. Posicion en G1 o G8 (dependiendo del color del rey)
     */
    public boolean esEnroqueCortoValido(Posición  origen, Posición destino){
        //Obtenemos las piezas dadas en dichas posiciones
        Pieza piezaOrigen = obténPieza(origen);       //Rey
        Posición E1_E8 = origen;             //Posicion del rey
        //Guardamos en una variable posicion la posicion de la torre 
        Posición F1_F8 = E1_E8.izquierda();  //F1 o F8 (dependiendo del color del rey)
        Posición G1_G8 = destino;           //G1 o G8 (dependiendo del color del rey)
        Posición H1_H8 = F1_F8.izquierda();               //Posicion de la torre
        Pieza esTorre = obténPieza(H1_H8);   
        
        //Verificamos si la pieza origen es un rey
        if(piezaOrigen.tipoPieza() != TipoPieza.REY) return false;
        //Verificamos si la pieza en H1 o H8 (dependiendo del color del rey) es una torre
        if(esTorre.tipoPieza() != TipoPieza.TORRE) return false;

        //Verificamos si la posicion origen es la posicion inicial del rey y es su primer movimiento
        //Posicion de partida del rey 
        if((E1_E8.columna() != 'e' || E1_E8.renglón() != 1) || (E1_E8.columna() != 'e' || E1_E8.renglón() != 8)) return false;

        //Verificamos si la torre esta en su punto de partida  (dependiendo del color del rey) y es su primer movimiento
        if((H1_H8.columna() != 'h' || H1_H8.renglón() != 1) || (H1_H8.columna() != 'h' || H1_H8.renglón() != 8)) return false;

        // Verificamos que sea el primer movimiento de la pieza torre y rey 
        if(!esSuPrimerMovimiento(piezaOrigen)) return false;
        if(!esSuPrimerMovimiento(esTorre)) return false;
       
        //Verificamos que no haya piezas entre el rey y la torre
        if(obténPieza(F1_F8) != null || obténPieza(G1_G8) != null) return false;
        //Verificamos que el rey no este en jaque
        if(tableroActual().estáEnJaque(turno, E1_E8)) return false;
        //Verrificamos que si el rey se mueve a las casillas F1 o F8, H1 o H8, G1 o G8, E1 o E8 (dependiendo del color del rey) no este en jaque
        if(tableroActual().estáEnJaque(turno, F1_F8)) return false;
        if(tableroActual().estáEnJaque(turno, G1_G8)) return false;
        if(tableroActual().estáEnJaque(turno, H1_H8)) return false;
        //Si alguna de estas condiciones se cumple entonces el enroque corto no es valido. En caso contrario, devolvemos true
        return true;
    }
    
    /*
     * esEnroqueLargo. Verifica si el rey y la torre pueden intercambiar de posicion
     * @param origen. Posicion del REY
     * @param destino. Posicion en C1 o C8 (dependiendo del color del rey)
     */
    public boolean esEnroqueLargoValido(Posición  origen, Posición destino){
        //Obtenemos las piezas dadas en dichas posiciones
        Pieza piezaOrigen = obténPieza(origen);

        Posición E1_E8 = origen;        //Posicion del rey E1
        Posición D1_D8 = E1_E8.derecha();
        Posición C1_C8 = destino;
        Posición B1_B8 = destino.derecha();
        Posición A1_A8 = B1_B8.derecha();      //Posicion de la torre

        Pieza esTorre = obténPieza(A1_A8);
        
        //Verificamos si la pieza origen es un rey y si en A1 o A8 esta una torre
        if(piezaOrigen.tipoPieza() != TipoPieza.REY) return false;
        if(esTorre.tipoPieza() != TipoPieza.TORRE) return false;

        //Verificamos si la posicion origen es la posicion inicial del rey y es su primer movimiento
        //Posicion de partida del rey 
        if((E1_E8.columna() != 'e' || E1_E8.renglón() != 1) || (E1_E8.columna() != 'e' || E1_E8.renglón() != 8)) return false;

        //Verificamos si la torre esta en su punto de partida  (dependiendo del color del rey) y es su primer movimiento
        if((A1_A8.columna() != 'a' || A1_A8.renglón() != 1) || (A1_A8.columna() != 'a' || A1_A8.renglón() != 8)) return false;

        //Verificamos que sea el primer movimiento de el rey y la torre 
        if(!esSuPrimerMovimiento(piezaOrigen)) return false;
        if(!esSuPrimerMovimiento(esTorre)) return false;
        //Verificamos que no haya piezas entre el rey y la torre

        if(obténPieza(B1_B8) != null || obténPieza(C1_C8) != null || obténPieza(D1_D8) != null) return false;

        //Verificamos que el rey no este en jaque
        if(tableroActual().estáEnJaque(turno, E1_E8)) return false;
        //Verificamos si las casillas C1 o C8, D1 o D8, E1 o E8 (dependiendo del color del rey) no estan en jaque
        if(tableroActual().estáEnJaque(turno, D1_D8)) return false;
        if(tableroActual().estáEnJaque(turno, C1_C8)) return false;
        if(tableroActual().estáEnJaque(turno, B1_B8)) return false;
        if(tableroActual().estáEnJaque(turno, A1_A8)) return false;
        
        //Si alguna de estas condiciones se cumple entonces el enroque corto no es valido. En caso contrario, devolvemos true
        return true;
        
    }
    

	/**
	 * Actauliza el tablero actual al tablero que resulta de ejecutar la jugada
	 * solicitada, si esta es válida.
	 * Las jugadas posibles son:
	 * - movimiento
	 * - captura
	 * - enroque corto
	 * - enroque largo
	 * @param origen
	 * @param destino
	 * @return si se pudo ejecutar la jugada.
	 */
    public boolean ejecutaJugada(Posición origen, Posición destino) {
        
        //Verificamos si la jugada es posible. La pieza puede realizar un movimiento, una captura o un enroque (corto o largo)
        if(!jugadaEsVálida(origen, destino)) return false;
        
        if(esEnroqueCortoValido(origen, destino)){
            tableroActual().realizarEnroqueC(origen, destino);
        }
        if(esEnroqueLargoValido(origen, destino)){
            tableroActual().realizarEnroqueL(origen, destino);
        }
        //Realizamos el movimiento/captura
        if (destino != null){
            tableroActual().moverPieza(origen, destino);
        }
        //Cambiamos el turno al jugador contrario 
        turno = (turno == Color.BLANCA) ? Color.NEGRA : Color.BLANCA;
        
        return true; //La jugada se realizo con exito
    }
    
	/**
	 * Dadas las coordenadas del escaque, devuelve la pieza que se encuentra ahí
	 * del jugador en turno.
	 * <code>origen</code>
	 * @param origen Coordenadas de la pieza deseada.
	 * @return La pieza en la posición indicada o <code>null</code> si no hay
	 * ahí una pieza del jugador en turno.
	 */
    public Pieza obténPieza(Posición origen) {
        if(turno == Color.BLANCA) {
            return tableroActual.piezasBlancas().get(origen);
        } else {
            return tableroActual.piezasNegras().get(origen);
        }
    }
}
