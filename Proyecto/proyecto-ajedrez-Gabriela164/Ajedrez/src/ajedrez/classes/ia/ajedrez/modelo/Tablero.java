/*
 * Código auxiliar para el curso de Inteligencia Artificial.
 * Se prohibe publicar o compartir soluciones.
 */
package ia.ajedrez.modelo;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import javax.annotation.processing.ProcessingEnvironment;

import ia.ajedrez.modelo.Alfil;
import ia.ajedrez.modelo.Torre;
import ia.ajedrez.modelo.Pieza;
import ia.ajedrez.modelo.Juego;
/**
 * Representa la posición de las piezas en el tablero.
 * @author blackzafiro
 */
public class Tablero {
    

    private Juego juego;
	/**
	 * Número de escaques por lado.
	 */
    public final int NUM_ESCAQUES = Juego.NUM_ESCAQUES;
	
	/**
	 * Caracter que representa a la última columna del tablero.
	 */
    private final char ÚLTIMA_COLUMNA = 'a' + NUM_ESCAQUES;;
    
	/**
	 * Posición de las piezas en el tablero, identificada según la notación
	 * oficial: renglón con enteros y columna con caracteres.
	 */
    public class Posición {
        private int renglón;
        private char columna;
        
		/**
		 * Constructor
		 * @param col columna
		 * @param ren renglón
		 */
        public Posición(char col, int ren) {
            columna(col);
            renglón(ren);
        }
        
		/**
		 * Constructor copia
		 * @param otra posición a copiar.
		 */
        public Posición(Posición otra) {
            renglón(otra.renglón);
            columna(otra.columna);
        }
        
        /**
         * Recibe la posición en dos caracteres: renglón columna
         * @param notación 
         */
        public Posición(String notación) {
			columna(notación.charAt(0));
            renglón(Integer.parseInt(notación.substring(1, 2)));
        }
        
        /**
         * Crea otro objeto posición con los mismos datos.
         * @return otro objeto representando la misma posición.
         */
        public Posición clon() {
            return new Posición(this);
        }
        
		/**
		 * Número de renglón desde 1 hasta el número de escaques, contando de
		 * abajo hacia arriba.
		 * @return número de renglón.
		 */
        public int renglón() {
            return renglón;
        }
        
		/**
		 * Caracter correspondiente a la columna empezando con 'a', de izquierda
		 * a derecha.
		 * @return caracter que denota la columna.
		 */
        public char columna() {
            return columna;
        }

		/**
		 * Asigna el renglón.
		 * @param ren número de renglón.
		 * @exception IndexOutOfBoundsException si el argumento está fuera del
		 * rango válido.
		 */
        private void renglón(int ren) {
            if (ren < 0 || ren > NUM_ESCAQUES) throw new IndexOutOfBoundsException();
            renglón = ren;
        }
        
		/**
		 * Asigna la columna.
		 * @param col caracter que representa la columna.
		 * @exception IndexOutOfBoundsException si el argumento está fuera del
		 * rango válido.
		 */
        private void columna(char col) {
            if(col < 'a' || col > ÚLTIMA_COLUMNA) throw new IndexOutOfBoundsException();
            columna = col;
        }
        
		/**
		 * Posición que se encuentra abajo de esta.
		 * @return posición o <code>null</code> si se llegó al borde del
		 * tablero.
		 */
        public Posición abajo() {
            if (renglón == 1) return null;
            return new Posición(columna, renglón - 1);
        }
        
		/**
		 * Posición que se encuentra arriba de esta.
		 * @return posición o <code>null</code> si se llegó al borde del
		 * tablero.
		 */
        public Posición arriba() {
            if (renglón == NUM_ESCAQUES) return null;
            return new Posición(columna, renglón + 1);
        }
        
		/**
		 * Posición que se encuentra a la izquierda de esta.
		 * @return posición o <code>null</code> si se llegó al borde del
		 * tablero.
		 */
        public Posición izquierda() {
            if (columna == 'a') return null;
            char ncol = (char) (columna - 1);
            return new Posición(ncol, renglón);
        }
        
		/**
		 * Posición que se encuentra a la derecha de esta.
		 * @return posición o <code>null</code> si se llegó al borde del
		 * tablero.
		 */
        public Posición derecha() {
            if (columna == ÚLTIMA_COLUMNA) return null;
            char ncol = (char) (columna + 1);
            return new Posición(ncol, renglón);
        }
        
		/**
		 * Posición que se encuentra arriba a la derecha de esta.
		 * @return posición o <code>null</code> si se llegó al borde del
		 * tablero.
		 */
        public Posición NE() {
            if (renglón == NUM_ESCAQUES || columna == ÚLTIMA_COLUMNA) return null;
            char ncol = (char) (columna + 1);
            return new Posición(ncol, renglón + 1);
        }
        
		/**
		 * Posición que se encuentra abajo a la derecha de esta.
		 * @return posición o <code>null</code> si se llegó al borde del
		 * tablero.
		 */
        public Posición SE() {
            if (renglón == 1 || columna == ÚLTIMA_COLUMNA) return null;
            char ncol = (char) (columna + 1);
            return new Posición(ncol, renglón - 1);
        }
		
        /**
		 * Posición que se encuentra abajo a la izquierda de esta.
		 * @return posición o <code>null</code> si se llegó al borde del
		 * tablero.
		 */
        public Posición SO() {
            if (renglón == 1 || columna == 'a') return null;
            char ncol = (char) (columna - 1);
            return new Posición(ncol, renglón - 1);
        }
		
        /**
		 * Posición que se encuentra arriba a la izquierda de esta.
		 * @return posición o <code>null</code> si se llegó al borde del
		 * tablero.
		 */
        public Posición NO() {
            if (renglón == NUM_ESCAQUES || columna == 'a') return null;
            char ncol = (char) (columna - 1);
            return new Posición(ncol, renglón + 1);
        }
        
        /**
         * Devuelve la posición en las coordenadas relativas especificadas.
         * Los renglones incrementan hacia arriba, las columnas hacia la
         * derecha.
         * @param deltaRen desplazamiento vertical
         * @param deltaCol desplazamiento horizontal
         * @return La posición absoluta en el tablero.  <code>null</code> si
         * las coordenadas resultantes está fuera del tablero.
         */
        public Posición relativa(int deltaRen, int deltaCol) {
            int ren = renglón + deltaRen;
            if (ren < 1 || ren > NUM_ESCAQUES) return null;
            char col = (char)(columna + deltaCol);
            if(col > ÚLTIMA_COLUMNA) return null;
            return new Posición(col, ren);
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 29 * hash + this.renglón;
            hash = 29 * hash + this.columna;
            return hash;
        }
        
        @Override
        public boolean equals(Object o) {
            if(!(o instanceof Posición)) return false;
            Posición otra = (Posición)o;
            return otra.columna == columna && otra.renglón == renglón;
        }
        
        @Override
        public String toString() {
            return "" + columna + renglón;
        }
    }
    
	/**
	 * Clase para guardar los datos que describen una jugada: de dónde a dónde
	 * se movió la pieza y qué tipo de pieza fue.
	 */
    public class Jugada {

        private final TipoPieza tipoPieza;
        private final Tablero.Posición origen;
        private final Tablero.Posición destino;

        /**
         * Crea un objeto jugada a partir de la notación oficial.
         * @param t tipo de pieza en movimiento
         * @param origen coordenadas de origen según la notación oficial.
         * @param destino coordenadas de destino según la notación oficial.
         */
        public Jugada(char pieza, String origen, String destino) {
            tipoPieza = TipoPieza.obtén(pieza);
            this.origen = decodifica(origen);
            this.destino = decodifica(destino);
        }

        /**
         * @return el tipoPieza
         */
        public TipoPieza tipoPieza() {
            return tipoPieza;
        }

        /**
         * @return el origen
         */
        public Tablero.Posición origen() {
            return origen;
        }

        /**
         * @return el destino
         */
        public Tablero.Posición destino() {
            return destino;
        }
    }
    
	/**
	 * Mapeo de posicón en el tablero a pieza blanca colocada ahí.
	 */
    private HashMap<Posición, Pieza> piezasNegras = new HashMap<>();
	
	/**
	 * Mapeo de posicón en el tablero a pieza negra colocada ahí.
	 */
    private HashMap<Posición, Pieza> piezasBlancas = new HashMap<>();
    
    /**
     * Crea un tablero en el estado inicial del juego de ajedrez.
     */
    public Tablero() {
        
        Posición posBlanca, posNegra;
        Pieza pieza;
       
        //Peones
        posBlanca = new Posición('a', 2);
        posNegra = new Posición('a', 7);
        
        
        for(char col = 'a'; col < ÚLTIMA_COLUMNA; col++) {
            piezasBlancas.put(posBlanca, new Peón(posBlanca, Color.BLANCA));
            piezasNegras.put(posNegra, new Peón(posNegra, Color.NEGRA));
            posBlanca.columna++;
            posNegra.columna++;
        }
        
        // Torres blancas
        posBlanca.columna = 'a';
        posBlanca.renglón = 1;
        pieza = new Torre(posBlanca, Color.BLANCA);
        piezasBlancas.put(pieza.posición(), pieza);

        
        posBlanca.columna = 'h';
        posBlanca.renglón = 1;
        pieza = new Torre(posBlanca, Color.BLANCA);
        piezasBlancas.put(pieza.posición(), pieza);

        // Torres negras
        posNegra.columna = 'a';
        posNegra.renglón = 8;
        pieza = new Torre(posNegra, Color.NEGRA);
        piezasNegras.put(pieza.posición(), pieza);

        posNegra.columna = 'h';
        posNegra.renglón = 8;
        pieza = new Torre(posNegra, Color.NEGRA);
        piezasNegras.put(pieza.posición(), pieza);
        
        
        // Reyes
        posBlanca.columna = 'e';
        posBlanca.renglón = 1;
        pieza = new Rey(posBlanca, Color.BLANCA);
        piezasBlancas.put(pieza.posición(), pieza);
        
        posNegra.columna = 'e';
        posNegra.renglón = 8;
        pieza = new Rey(posNegra, Color.NEGRA);
        piezasNegras.put(pieza.posición(), pieza);

        //Reinas
        posBlanca.columna = 'd';
        posBlanca.renglón = 1;
        pieza = new Reina(posBlanca, Color.BLANCA);
        piezasBlancas.put(pieza.posición(), pieza);

        posNegra.columna = 'd';
        posNegra.renglón = 8;
        pieza = new Reina(posNegra, Color.NEGRA);
        piezasNegras.put(pieza.posición(), pieza);

        
        //Alfiles
        posBlanca.columna = 'c';
        posBlanca.renglón = 1;
        pieza = new Alfil(posBlanca, Color.BLANCA);
        piezasBlancas.put(pieza.posición(), pieza);

        posBlanca.columna = 'f';
        posBlanca.renglón = 1;
        pieza = new Alfil(posBlanca, Color.BLANCA);
        piezasBlancas.put(pieza.posición(), pieza);

        posNegra.columna = 'c';
        posNegra.renglón = 8;
        pieza = new Alfil(posNegra, Color.NEGRA);
        piezasNegras.put(pieza.posición(), pieza);

        posNegra.columna = 'f';
        posNegra.renglón = 8;
        pieza = new Alfil(posNegra, Color.NEGRA);
        piezasNegras.put(pieza.posición(), pieza);
        
        //Caballos 
        posBlanca.columna = 'b';
        posBlanca.renglón = 1;
        pieza = new Caballo(posBlanca, Color.BLANCA);
        piezasBlancas.put(pieza.posición(), pieza);

        posBlanca.columna = 'g';
        posBlanca.renglón = 1;
        pieza = new Caballo(posBlanca, Color.BLANCA);
        piezasBlancas.put(pieza.posición(), pieza);

        posNegra.columna = 'b';
        posNegra.renglón = 8;
        pieza = new Caballo(posNegra, Color.NEGRA);
        piezasNegras.put(pieza.posición(), pieza);

        posNegra.columna = 'g';
        posNegra.renglón = 8;
        pieza = new Caballo(posNegra, Color.NEGRA);
        piezasNegras.put(pieza.posición(), pieza);
    }
    
    /**
     * Crea un tablero con el mismo estado que el tablero que se pasa como
     * parámetro.  También se crean copias de las piezas.
     */
    public Tablero(Tablero otro) {
        // TODO
    }
    
    public Posición decodifica(String notación) {
        return new Posición(notación);
    }
    
    public HashMap<Posición, Pieza> piezasBlancas() {
        return piezasBlancas;
    }
    
    public HashMap<Posición, Pieza> piezasNegras() {
        return piezasNegras;
    }

    /**
     * Imprime las piezas del color indicado que se encuentran en el tablero.
     * @param color color de las piezas a imprimir.
     */
    public void imprimePiezas(Color color) {
        HashMap<Posición, Pieza> mapa = (color == Color.BLANCA) ? piezasBlancas : piezasNegras;
        for(Map.Entry<Posición, Pieza> entry : mapa.entrySet()) {
            System.out.println(" Pieza en tabla: " + entry.getKey() + " " + entry.getValue());
        }
    }
    
    /**
     * Indica la calificación del tablero.
     * Un valor positivo indica un tablero favorable para las piezas blancas,
     * un valor negativo es favorable a las piezas negras, cero indica empate.
     * Devuelve el valor con el valor absoluto más grande cuando hay jaque mate.
     * @return calificación del tablero según Min-max.
     */
    public int calificación() {
        // Asignamos valores a las piezas
        int valorPeón = 1;
        int valorCaballo = 3;
        int valorAlfil = 3;
        int valorTorre = 5;
        int valorReina = 9;
        int valorRey = 1000;
    
        int valorTotal = 0;
    
        // Itera sobre las piezas en el tablero y calcula la calificación
        for (Pieza pieza : piezasBlancas.values()) {
            if (pieza instanceof Peón) {
                valorTotal += valorPeón;
            } else if (pieza instanceof Caballo) {
                valorTotal += valorCaballo;
            } else if (pieza instanceof Alfil) {
                valorTotal += valorAlfil;
            } else if (pieza instanceof Torre) {
                valorTotal += valorTorre;
            } else if (pieza instanceof Reina) {
                valorTotal += valorReina;
            } else if (pieza instanceof Rey) {
                valorTotal += valorRey;
            }
            valorTotal += pieza.posiblesMovimientos().size() * 1; // Valor de dominio del tablero
        }
    
        for (Pieza pieza : piezasNegras.values()) {
            // Realiza lo mismo que en el bucle anterior, pero restando los valores
            if (pieza instanceof Peón) {
                valorTotal -= valorPeón;
            } else if (pieza instanceof Caballo) {
                valorTotal -= valorCaballo;
            } else if (pieza instanceof Alfil) {
                valorTotal -= valorAlfil;
            } else if (pieza instanceof Torre) {
                valorTotal -= valorTorre;
            } else if (pieza instanceof Reina) {
                valorTotal -= valorReina;
            } else if (pieza instanceof Rey) {
                valorTotal -= valorRey;
            }
            valorTotal -= pieza.posiblesMovimientos().size() * 1; // Valor de dominio del tablero
        }

        // Verifica si hay jaque mate
        if (hayJaqueMate(Color.BLANCA)) {
            return Integer.MIN_VALUE; // Ganaron las negras (valor más pequeño)
        } else if (hayJaqueMate(Color.NEGRA)) {
            return Integer.MAX_VALUE; // Ganaron las blancas (valor más grande)
        }
    
        return valorTotal;
    }
    
    

    /*
     * Metodo auxiliar que verifica si el jugador esta en jaque
     * @param jugador jugador en turno para este tablero.
     * @param posiciónRey posicion del rey del jugador
     */
    public boolean estáEnJaque(Color jugador, Posición posiciónRey) {
        // Obtén las piezas del jugador enemigo (oponente) y del jugador actual.
        HashMap<Posición, Pieza> piezasOponente = (jugador == Color.BLANCA) ? piezasNegras : piezasBlancas;
        
        // Recorre las piezas del jugador enemigo.
        for (Map.Entry<Posición, Pieza> entry : piezasOponente.entrySet()) {
            Pieza piezaOponente = entry.getValue();
            // Genera una lista de movimientos posibles para la pieza oponente.
            List<Posición> movimientosPosibles = piezaOponente.posiblesMovimientos();
            // Verifica si la posición del rey está entre los movimientos posibles de la pieza oponente.
            if (movimientosPosibles.contains(posiciónRey)) {
                // El rey está en jaque.
                return true;
            }
        }
        return false; // En otro caso, devuelve false.
    }
    
    

    /*Metodo auxiliar estaEnJaqueMate. Verifica si el jugador esta en jaque mate 
     * @param jugador jugador en turno para este tablero.
     * @param posiciónRey posicion del rey del jugador
     * @return si el jugador esta en jaque mate
     */
    public boolean estáEnJaqueMate(Color jugador, Posición posiciónRey) {
        // Obtén las piezas del jugador enemigo (oponente) y del jugador actual.
        HashMap<Posición, Pieza> piezasOponente = (jugador == Color.BLANCA) ? piezasNegras : piezasBlancas;
    
        //Obtenemos la pieza rey 
        Pieza rey = juego.obténPieza(posiciónRey);
        List<Posición> movimientosRey = rey.posiblesMovimientos();
    
        // Verifica si el rey está en jaque.
        if (!estáEnJaque(jugador, posiciónRey)) {
            return false; // El rey no está en jaque, por lo tanto, no puede estar en jaque mate.
        }
    
        // Comprueba si el rey puede moverse a una casilla segura.
        for (Posición movimientoRey : movimientosRey) {
            if (!estáEnJaque(jugador, movimientoRey)) {
                return false; // El rey puede escapar del jaque, por lo tanto, no es jaque mate.
            }
        }
    
        // Comprueba si alguna pieza del jugador puede bloquear o capturar la pieza amenazante.
        for (Map.Entry<Posición, Pieza> entry : piezasOponente.entrySet()) {
            Pieza piezaOponente = entry.getValue();
            Posición posiciónPieza = entry.getKey();
            List<Posición> movimientosPosibles = piezaOponente.posiblesMovimientos();
            for (Posición movimientoPosible : movimientosPosibles) {
                if (!produciríaJaque(jugador, posiciónRey, posiciónPieza, movimientoPosible)) {
                    return false; // Al menos una pieza puede bloquear o capturar la pieza amenazante, por lo tanto, no es jaque mate.
                }
            }
        }
    
        // Si ninguna de las condiciones anteriores se cumple, entonces es jaque mate.
        return true;
    }
    
    
    
    // Método para verificar si el movimiento de una pieza resultaría en jaque para el jugador.
    private boolean produciríaJaque(Color jugador, Posición posiciónRey, Posición origen, Posición destino) {
        // Realiza una copia temporal del tablero y simula el movimiento.
        Tablero copiaTablero = new Tablero(this); // Debes crear un constructor de copia en la clase Tablero.
        copiaTablero.moverPieza(origen, destino);
    
        // Comprueba si el rey está en jaque en la copia temporal del tablero.
        return copiaTablero.estáEnJaque(jugador, posiciónRey);
    }
    


    /*
     * Metodo auxiliar que encuentra la posicion del rey del jugador
     */
    private Posición encuentraPosicionRey(Color jugador) {
        HashMap<Posición, Pieza> piezasJugador = (jugador == Color.BLANCA) ? piezasBlancas : piezasNegras;
    
        for (Map.Entry<Posición, Pieza> entry : piezasJugador.entrySet()) {
            if (entry.getValue() instanceof Rey) {
                return entry.getKey();
            }
        }
        return null; // Si no se encuentra el rey, retorna null.
    }


    /**
     * Indica si el jugador se encuentra en jaque mate.
     * @param jugador jugador en turno para este tablero.
     * @return si el jugador se encuentra en jaque mate.
     */
    public boolean hayJaqueMate(Color jugador) {
        // Encuentra la posición del rey del jugador actual.
        Posición posiciónRey = encuentraPosicionRey(jugador);
        // Verifica si el rey está en jaque.
        boolean estaEnjaque = estáEnJaqueMate(jugador, posiciónRey);

        return estaEnjaque;
    }
    
    
    
    /*
     * Metodo moverPieza. La pieza origen se mueve a la posicion destino del tablero.
     * puede ser movimiento o captura. Si hay una pieza en la posicion destino, se elimina. 
     * @param origen Posición de la pieza a mover.
     * @param destino Posición de la pieza que se desea capturar
     */ 
    public void moverPieza(Posición origen, Posición destino) {
        Pieza piezaOrigen = juego.obténPieza(origen);
        Pieza piezaDestino = juego.obténPieza(destino);

        //Si existe una pieza en la posicion destino, eliminamos dicha pieza y movemos la pieza origen a destino. 
        // Verificar si la posición de destino contiene una pieza.
        if (piezaDestino != null) {
            // Eliminar la pieza de destino del conjunto de piezas correspondiente.
            if (piezaDestino.color() == Color.BLANCA) {
                piezasBlancas.remove(destino);
            } else {
                piezasNegras.remove(destino);
            }
        }
    
        //Si no existe pieza en la posicion destino, se mueve la pieza origen a la posicion destino.
        if (piezaOrigen.color() == Color.BLANCA) {
            piezasBlancas.put(destino, piezaOrigen);
            piezasBlancas.remove(origen);
        } else {
            piezasNegras.put(destino, piezaOrigen);
            piezasNegras.remove(origen);
        }

        //Incrementamos en uno la cantidad de movimientos de la pieza origen
        piezaOrigen.setMovimiento(1);
    } 

    /*
     * Metodo realizarEnroqueC. El rey y la torre intercambian de lugar. Si el rey es blanco
     * se mueve de E1 a G1 y la torre de H1 a F1. Sino, el rey se mueve de E8 a G8 y la torre de H8 a F8.
     * @param origen Posición de la pieza a mover.
     * @param destino Posición de la pieza que se desea capturar
     */
    public void realizarEnroqueC(Posición origen, Posición destino) {
        // Obtenemos las piezas dadas sus posiciones del tablero 
        Pieza piezaOrigen = juego.obténPieza(origen);
        Pieza torre = null;
        // Movemos el rey a la posicion destino
        moverPieza(origen, destino);
    
        // Movemos la torre a la posicion destino
        if (piezaOrigen.color() == Color.BLANCA) {
            moverPieza(new Posición('h', 1), new Posición('f', 1));
            torre = juego.obténPieza(new Posición('f', 1));
        } else {
            moverPieza(new Posición('h', 8), new Posición('f', 8));
            torre = juego.obténPieza(new Posición('f', 8));
        }
        torre.setMovimiento(1);
        piezaOrigen.setMovimiento(1);
    }
    
    /*
     * Metodo realizarEnroqueL. El rey y la torre intercambian de lugar. 
     * Si el rey es blanco se mueve de E1 a C1 y la torre de A1 a D1. Sino, el rey se mueve de 
     * E8 a C8 y la torre de A8 a D8.
     * @param origen Posición de la pieza a mover.
     * @param destino Posición de la pieza que se desea capturar
     */
    public void realizarEnroqueL(Posición origen, Posición destino) {
        // Obtenemos las piezas dadas sus posiciones del tablero 
        Pieza piezaOrigen = juego.obténPieza(origen);
        Pieza torre = null;
        // Movemos el rey a la posicion destino
        moverPieza(origen, destino);
    
        // Movemos la torre a la posicion destino
        if (piezaOrigen.color() == Color.BLANCA) {
            moverPieza(new Posición('a', 1), new Posición('d', 1));
            torre = juego.obténPieza(new Posición('d', 1));
        } else {
            moverPieza(new Posición('a', 8), new Posición('d', 8));
            torre = juego.obténPieza(new Posición('d', 8));
        }
        torre.setMovimiento(1);
        piezaOrigen.setMovimiento(1);
    }

}
