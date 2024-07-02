/*
 * Código auxiliar para el curso de Inteligencia Artificial.
 * Se prohibe publicar o compartir soluciones.
 */
package ia.ajedrez.modelo;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumera las piezas del ajedrez.
 * @author blackzafiro
 */
public enum TipoPieza {
    REY('R', Integer.MAX_VALUE),
    REINA('D', 9),
    TORRE('T', 5),
    ALFIL('A', 3),
    CABALLO('C', 3),
    PEÓN('\0', 1);
    
    private static final Map<Character, TipoPieza> tabla = new HashMap<>();
    private final char notación;
	private final int valor;
    
    static {
        for(TipoPieza t : TipoPieza.values()) {
            tabla.put(t.notación(), t);
        }
    }
    
	/**
	 * Cada pieza tiene una letra asociada en la notación oficial del ajedrez.
	 * @param letra 
	 */
    private TipoPieza(char letra, int valor) {
        notación = letra;
		this.valor = valor;
    }
    
	/**
	 * Letra usada para identificar el tipo de pieza.
	 * @return letra de la pieza.
	 */
    public char notación() {
        return notación;
    }
	
	/**
	 * Valor absoluto de la pieza.
	 */
	public int valor() {
		return valor;
	}
    
	/**
	 * Recupera el objeto Pieza a partir de su notación.
	 * @param c letra.
	 * @return la pieza.
	 * @exception IllegalArgumentException si la letra no corresponde a alguna
	 * pieza.
	 */
    public static TipoPieza obtén(char c) {
		TipoPieza tp = tabla.get(c);
        if(tp == null) throw new IllegalArgumentException("No existe la pieza " + c);
		return tp;
    }
}
