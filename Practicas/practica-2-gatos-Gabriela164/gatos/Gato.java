/*
 * No redistribuir.
 */
package gatos;

import java.util.LinkedList;

/**
 * Clase para representar un estado del juego del gato. Cada estado sabe cómo
 * generar a sus sucesores.
 *
 * @author Vero
 */
public class Gato {

    public static final int MARCA1 = 1;             // Número usado en el tablero del gato para marcar al primer jugador.
    public static final int MARCA2 = 4;             // Se usan int en lugar de short porque coincide con el tamaÃ±o de la palabra, el código se ejecuta ligeramente más rápido.

    int[][] tablero = new int[3][3];     // Tablero del juego
    Gato padre;                          // Quién generó este estado.
    LinkedList<Gato> sucesores;          // Posibles jugadas desde este estado.
    boolean jugador1 = false;            // Jugador que tiró en este tablero.
    boolean hayGanador = false;          // Indica si la última tirada produjo un ganador.
    int tiradas = 0;                     // Número de casillas ocupadas.

    /**
     * Constructor del estado inicial.
     */
    Gato() {
    }

    /**
     * Constructor que copia el tablero de otro gato y el número de tiradas
     */
    Gato(Gato g) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = g.tablero[i][j];
            }
        }
        tiradas = g.tiradas;
    }

    /**
     * Indica si este estado tiene sucesores expandidos.
     */
    int getNumHijos() {
        if (sucesores != null) {
            return sucesores.size();
        } else {
            return 0;
        }
    }

    /* Función auxiliar.
     * Dada la última posición en la que se tiró y la marca del jugador
     * calcula si esta jugada produjo un ganador y actualiza el atributo correspondiente.
     * 
     * Esta función debe ser lo más eficiente posible para que la generación del árbol no sea demasiado lenta.
     */
    private void hayGanador(int ren, int col, int marca) {
        // Horizontal
        if (tablero[ren][(col + 1) % 3] == marca && tablero[ren][(col + 2) % 3] == marca) {
            hayGanador = true;
            return;
        }
        // Vertical
        if (tablero[(ren + 1) % 3][col] == marca && tablero[(ren + 2) % 3][col] == marca) {
            hayGanador = true;
            return;
        }
        // Diagonal
        if ((ren != 1 && col == 1) || (ren == 1 && col != 1)) {
			hayGanador = false;  // No debiera ser necesaria.
            return; // No pueden hacer diagonal
        }      // Centro y esquinas
        if (col == 1 && ren == 1) {
            // Diagonal \
            if (tablero[0][0] == marca && tablero[2][2] == marca) {
                hayGanador = true;
                return;
            }
            if (tablero[2][0] == marca && tablero[0][2] == marca) {
                hayGanador = true;
                return;
            }
        } else if (ren == col) {
            // Diagonal \
            if (tablero[(ren + 1) % 3][(col + 1) % 3] == marca && tablero[(ren + 2) % 3][(col + 2) % 3] == marca) {
                hayGanador = true;
                return;
            }
        } else {
            // Diagonal /
            if (tablero[(ren + 2) % 3][(col + 1) % 3] == marca && tablero[(ren + 1) % 3][(col + 2) % 3] == marca) {
                hayGanador = true;
                return;
            }
        }
    }


    /* Función auxiliar.
     * Coloca la marca del jugador en turno para este estado en las coordenadas indicadas.
     * Asume que la casilla está libre.
     * Coloca la marca correspondiente, verifica y asigna la variable si hay un ganador.
     */
    private void tiraEn(int ren, int col) {
        tiradas++;
        int marca = (jugador1) ? MARCA1 : MARCA2;
        tablero[ren][col] = marca;
        hayGanador(ren, col, marca);
    }

    
    /**
     * ------- *** ------- *** -------
     * Este es el método que se deja como práctica.
     * ------- *** ------- *** -------
     * Crea la lista sucesores y 
     * agrega a todos los estados que sujen de tiradas válidas. Se consideran
     * tiradas válidas a aquellas en una casilla libre. Además, se optimiza el
     * proceso no agregando estados con jugadas simétricas. Los estados nuevos
     * tendrán una tirada más y el jugador en turno será el jugador
     * contrario.
     */
    public LinkedList<Gato> generaSucesores() {
        // Verifica si hay un ganador o si el tablero está lleno.
        if (hayGanador || tiradas == 9) {
            return null;
        }
    
        // Crea la lista de sucesores.
        sucesores = new LinkedList<Gato>();
    
        // Genera los sucesores.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Verifica si la casilla está libre.
                if (tablero[i][j] == 0) {
                    // Crea un nuevo estado copia del actual.
                    Gato nuevo = new Gato(this);
                    //Cambiar el jugador en turno y tirar en la casilla libre.
                    nuevo.jugador1 = !jugador1;
                    nuevo.tiraEn(i, j);
                    // Asigna el padre del nuevo estado.
                    nuevo.padre = this;
                    // Verifica si el nuevo estado es simétrico a alguno de los estados ya generados.
                    boolean simétrico = false;
                    for (Gato g : sucesores) {
                        if (nuevo.equals(g)) {
                            simétrico = true;
                            break;
                        }
                    }
                    // Si no es simétrico, lo agrega a la lista de sucesores.
                    if (!simétrico) {
                        sucesores.add(nuevo);
                    }
                }
            }
        }
        // Devuelve la lista de sucesores.
        return sucesores;
    }
    
    

    // ------- *** ------- *** -------
    // Serie de funciones que revisan la equivalencia de estados considerando las simetrías de un cuadrado.
    // ------- *** ------- *** -------
    // http://en.wikipedia.org/wiki/Examples_of_groups#The_symmetry_group_of_a_square_-_dihedral_group_of_order_8
    // ba es reflexion sobre / y ba3 reflexion sobre \.
    
    /**
     * Revisa si ambos gatos son exactamente el mismo.
     */
    boolean esIgual(Gato otro) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] != otro.tablero[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Al reflejar el gato sobre la diagonal \ son iguales (ie traspuesta)
     */
    boolean esSimétricoDiagonalInvertida(Gato otro) {
        //Verifica si el tamaño del tablero es el mismo
        if (tablero.length != 3 || tablero[0].length != 3 || otro.tablero.length != 3 || otro.tablero[0].length != 3) {
            return false;
        }
        // Compara las casillas del tablero actual con las casillas del tablero "otro" reflejado respecto a la diagonal invertida.
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j ++){
                if(tablero[i][j] != otro.tablero[j][i]){
                    return false; //Las casillas son diferentes
                }
            }
        }        
        return true;
    }

    /**
     * Al reflejar el gato sobre la diagonal / son iguales (ie traspuesta)
     */
    boolean esSimétricoDiagonal(Gato otro) {
          // Verifica si el tamaño del tablero es 3x3 en ambos estados.
        if (tablero.length != 3 || tablero[0].length != 3 || otro.tablero.length != 3 || otro.tablero[0].length != 3) {
            return false; // Tamaño de tablero no válido.
        }

        // Compara las casillas del tablero actual con las casillas del tablero "otro" reflejado respecto a la diagonal.
         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] != otro.tablero[2-j][2-i]) {
                    return false; // Las casillas son diferentes
                }
            }
        }
        return true; // Todos los elementos son iguales, son simétricos.
    }

    /**
     * Al reflejar el otro gato sobre la vertical son iguales
     */
    boolean esSimétricoVerticalmente(Gato otro) {
        // Verifica si el tamaño del tablero es 3x3 en ambos estados.
        if (tablero.length != 3 || tablero[0].length != 3 || otro.tablero.length != 3 || otro.tablero[0].length != 3) {
            return false; // Tamaño de tablero no válido.
        }

        // Compara las casillas del tablero actual con las casillas del tablero "otro" reflejado verticalmente.
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if (tablero[i][j] != otro.tablero[i][2-j]) {
                    return false; // Las casillas son diferentes, no son simétricos verticalmente.
                }
            }
        }
        return true; //todos los elementos son iguales, son simétricos verticalmente.
    }


    /**
     * Al reflejar el otro gato sobre la horizontal son iguales
     */
    boolean esSimétricoHorizontalmente(Gato otro) {
     // Verifica si el tamaño del tablero es 3x3 en ambos estados.
    if (tablero.length != 3 || tablero[0].length != 3 || otro.tablero.length != 3 || otro.tablero[0].length != 3) {
        return false; // Tamaño de tablero no válido.
    }

    // Compara las casillas del tablero actual con las casillas del tablero "otro" reflejado horizontalmente.
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (tablero[i][j] != otro.tablero[2 - i][j]) {
                return false; // Las casillas son diferentes, no son simétricos horizontalmente.
            }
        }
    }

    return true; // Todos los elementos son iguales, son simétricos horizontalmente.
    }

    /**
     * Rota el otro tablero 90Â° en la dirección de las manecillas del reloj.
     */
    boolean esSimétrico90(Gato otro) {
        
        // Verifica si el tamaño del tablero es 3x3 en ambos estados.
        if (tablero.length != 3 || tablero[0].length != 3 || otro.tablero.length != 3 || otro.tablero[0].length != 3) {
            return false; // Tamaño de tablero no válido.
        }

        //Compara las casillas del tablero actual con las casillas del tablero despues de una rotacion de 90
        //grados en sentido de las manecillas del reloj.
        for(int i = 0; i < 3; i ++){
            for(int j = 0; j < 3; j++){
                if(tablero[i][j] != otro.tablero[2-j][i]){
                    return false; //Las casillas son diferentes, no son simetricos
                }
            }
        }
        return true;
    }

    /**
     * Rota el otro tablero 180Â° en la dirección de las manecillas del reloj.
     */
    boolean esSimétrico180(Gato otro) {
        //Verifica si el tamaño del tablero es 3x3 en ambos estados.
        if (tablero.length != 3 || tablero[0].length != 3 || otro.tablero.length != 3 || otro.tablero[0].length != 3) {
            return false; // Tamaño de tablero no válido.
        }
        //Comparar las casillas del tablero actual con las casillas del tablero otro despues de una rotacion
        //de 180 grados en sentido de las manecillas del reloj.
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(tablero[i][j] != otro.tablero[2-i][2-j]){
                    return false; //Las casillas son diferentes, no son simetricos
                }
            }
        }
        return true;
    }

    /**
     * Rota el otro tablero 270Â° en la dirección de las manecillas del reloj.
     */
    boolean esSimétrico270(Gato otro) {
        //Verifica si el tamaño del tablero es 3x3 en ambos estados.
        if (tablero.length != 3 || tablero[0].length != 3 || otro.tablero.length != 3 || otro.tablero[0].length != 3) {
            return false; // Tamaño de tablero no válido.
        }
         // Compara las casillas del tablero actual con las casillas del tablero "otro" después de una rotación de 270 grados.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] != otro.tablero[j][2 - i]) {
                    return false; // Las casillas son diferentes después de la rotación.
                }
            }
        }
        return true;
    }

    /**
     * Indica si dos estados del juego del gato son iguales, considerando
     * simetrías, de este modo el problema se vuelve manejable.
     */
    @Override
    public boolean equals(Object o) {
        Gato otro = (Gato) o;
        if (esIgual(otro)) {
            return true;
        }

        if (esSimétricoDiagonalInvertida(otro)) {
            return true;
        }
        if (esSimétricoDiagonal(otro)) {
            return true;
        }
        if (esSimétricoVerticalmente(otro)) {
            return true;
        }
        if (esSimétricoHorizontalmente(otro)) {
            return true;
        }
        if (esSimétrico90(otro)) {
            return true;
        }
        if (esSimétrico180(otro)) {
            return true;
        }
        if (esSimétrico270(otro)) {
            return true; // No redujo el diámetro máximo al agregarlo
        }
        return false;
    }

    /**
     * Devuelve una representación con caracteres de este estado. Se puede usar
     * como auxiliar al probar segmentos del código.
     */
    @Override
    public String toString() {
        char simbolo = jugador1 ? 'o' : 'x';
        String gs = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gs += tablero[i][j] + " ";
            }
            gs += '\n';
        }
        return gs;
    }
}
