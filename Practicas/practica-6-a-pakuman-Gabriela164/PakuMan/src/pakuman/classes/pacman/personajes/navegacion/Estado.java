/*
 * Código utilizado para el curso de Inteligencia Artificial.
 * Se permite consultarlo para fines didácticos en forma personal,
 * pero no esta permitido transferirlo resuelto a estudiantes actuales o potenciales.
 */
package pacman.personajes.navegacion;

import javafx.scene.paint.Color;
import pacman.escenario.Pasillo;
import pacman.personajes.Movimiento;

/**
 * 
 * @author blackzafiro
 */
public class Estado {
	
	private Algoritmo alg;
	private Pasillo pasillo;	// El estado es una celda del laberinto.
	private int hn;				// Distancia estimada a la meta.
	
	public Estado(Algoritmo alg, Pasillo pasillo) {
		this.alg = alg;
		this.pasillo = pasillo;
	}
	
	public void pintaCelda(Color c) {
		pasillo.pintaFondo(c);
	}
	
	/**
	 * Calcula la distancia Manhattan a la meta.
	 * Para este problema es una heurística admisible pues pacman no tiene
	 * movimientos diagonales.
	 * 
	 * Se debe mandar llamar este método cada vez que se visite el estado por
	 * primera vez.
	 */
	public void calculaHeuristica(Estado meta) {
		/*
        * HINT: calculen la distancia de este mosaico hacia el mosaico meta y luego multipliquenlo por 10
        * para que el valor sea significativo. tampoco deberia haber valores negativos
        */
		int distanciaVertical = Math.abs(pasillo.renglon() - meta.pasillo.renglon());
		int distanciaHorizontal = Math.abs(pasillo.columna() - meta.pasillo.columna());
		//Calcula la distancia de Manhattan y multiplica por 10
		hn = (distanciaVertical + distanciaHorizontal) * 10;
	}
	
	/**
	 * Devuelve un estimado de distancia desde este estado hasta la meta.
	 * @return h(n)
	 */
	public int hn() { return hn; }
	
	/**
	 * Devuelve el estado al que se realizaría el movimiento, de ser posible.
	 * @param mov
	 * @return estado siguiente o <code>null</code> si no es posible moverse
	 *         en esa dirección.
	 */
	public Estado aplicaAccion(Movimiento mov) {
		Pasillo p = pasillo.obtenVecino(mov);
		if (p != null) {
			return alg.leeEstado(p.renglon(), p.columna());
		} else {
			return null;
		}
	}
	
	
	@Override
	public String toString(){
		return "[" + pasillo.renglon() + ", " + pasillo.columna() + "] : hn = " + hn;
	}
}
