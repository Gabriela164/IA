/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ia.ajedrez.vistas;


import ia.ajedrez.ApTablero;
import ia.ajedrez.modelo.Juego;
import ia.ajedrez.modelo.Pieza;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import ia.ajedrez.modelo.Tablero;
import ia.ajedrez.modelo.Tablero.Posición;
import ia.ajedrez.modelo.TipoPieza;
import java.net.URL;
import java.util.HashMap;
import javafx.scene.image.Image;

/**
 *
 * @author blackzafiro
 */
public class VistaTablero extends GridPane {
    
    public static final double LADO = 50;
    private final Escaque[][] escaques;
    private final HashMap<TipoPieza, Image> blancas = new HashMap<>();
    private final HashMap<TipoPieza, Image> negras = new HashMap<>();
    
    public VistaTablero(Tablero t) {
        escaques = new Escaque[t.NUM_ESCAQUES][t.NUM_ESCAQUES];
        creaVistasDePiezas();
        dibujaTablero();
        colocaPiezas(t);
    }
    
    /**
     * Número de escaques por lado.
     * @return número de renglones/columnas.
     */
    public int lado() {
        return escaques.length;
    }
    
    private void creaVistasDePiezas() {
        URL url = ApTablero.class.getResource("/resources/reyBlanco.png");
        blancas.put(TipoPieza.REY, new Image("file:" + url.getFile()));
    
        url = ApTablero.class.getResource("/resources/reyNegro.png");
        negras.put(TipoPieza.REY, new Image("file:" + url.getFile()));

        url = ApTablero.class.getResource("/resources/reinaBlanca.png");
        blancas.put(TipoPieza.REINA, new Image("file:" + url.getFile()));

        url = ApTablero.class.getResource("/resources/reinaNegra.png");
        negras.put(TipoPieza.REINA, new Image("file:" + url.getFile()));
        
        url = ApTablero.class.getResource("/resources/caballoBlanco.png");
        blancas.put(TipoPieza.CABALLO, new Image("file:" + url.getFile()));

        url = ApTablero.class.getResource("/resources/caballoNegro.png");
        negras.put(TipoPieza.CABALLO, new Image("file:" + url.getFile()));

        url = ApTablero.class.getResource("/resources/peonBlanco.png");
        blancas.put(TipoPieza.PEÓN, new Image("file:" + url.getFile()));

        url = ApTablero.class.getResource("/resources/peonNegro.png");
        negras.put(TipoPieza.PEÓN, new Image("file:" + url.getFile()));
    
        url = ApTablero.class.getResource("/resources/torreBlanca.png");
        blancas.put(TipoPieza.TORRE, new Image("file:" + url.getFile()));

        url = ApTablero.class.getResource("/resources/torreNegra.png");
        negras.put(TipoPieza.TORRE, new Image("file:" + url.getFile()));

        url = ApTablero.class.getResource("/resources/alfilBlanco.png");
        blancas.put(TipoPieza.ALFIL, new Image("file:" + url.getFile()));

        url = ApTablero.class.getResource("/resources/alfilNegro.png");
        negras.put(TipoPieza.ALFIL, new Image("file:" + url.getFile()));
    }
    
    private void dibujaTablero() {
        Escaque escaque;
        Color c;
        for(int i = 0; i < Juego.NUM_ESCAQUES; i++) {
            for(int j = 0; j < Juego.NUM_ESCAQUES; j++) {
                //Color cafe claro y cafe fuerte 
                c = ( (i + j) % 2 == 0) ? Color.WHITE : Color.GREEN;
                escaque = new Escaque(c);
                add(escaque, j, i);
                escaques[i][j] = escaque;
            }
        }
    }
    
    private void colocaPiezas(Tablero t) {
        int[] coord;
        
        for(Pieza p : t.piezasBlancas().values()) {
            coord = coordenadas(p.posición());
            escaques[coord[0]][coord[1]].asigna(blancas.get(p.tipoPieza()));
        }
        
        for(Pieza p : t.piezasNegras().values()) {
            coord = coordenadas(p.posición());
            escaques[coord[0]][coord[1]].asigna(negras.get(p.tipoPieza()));
        }
    }
    
    private int[] coordenadas(Posición pos) {
        return new int[] {Juego.NUM_ESCAQUES - pos.renglón(), pos.columna() - 'a'};
    }
    
    /**
     * Cambia el tablero por uno nuevo y muestra las piezas del nuevo tablero.
     * @param t 
     */
    public void asignaTablero(Tablero t) {
        for (Escaque[] renglones : escaques) {
            for (Escaque escaque : renglones) {
                escaque.asigna(null);
            }
        }
        colocaPiezas(t);
    }
}
