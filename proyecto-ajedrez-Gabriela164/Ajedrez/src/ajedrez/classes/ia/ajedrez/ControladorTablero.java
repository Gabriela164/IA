/*
 * Código auxiliar para el curso de Inteligencia Artificial.
 * Se prohibe publicar o compartir soluciones.
 */
package ia.ajedrez;

import ia.ajedrez.modelo.Juego;
import ia.ajedrez.modelo.Tablero.Posición;
import ia.ajedrez.vistas.VistaTablero;
import ia.ajedrez.vistas.Escaque;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Controlador principal.
 * Establece la comunicación entre las vistas y el modelo.
 * @author blackzafiro
 */
public class ControladorTablero {
    
	/**
	 * Panel para contener el tablero y las piezas en juego.
	 */
    @FXML
    private StackPane pilaBase;
    
	/**
	 * Panel para la numeración de los renglones.
	 */
    @FXML
    private AnchorPane leftPane;
    
	/**
	 * Panel para la notación de las columnas.
	 */
    @FXML
    private AnchorPane bottomPane;
    
	/**
	 * Modelo que lleva el desarrollo del juego.
	 */
    private Juego juego;
	
	/**
	 * Vista que permite visualizar el tablero que se le asigne.
	 */
    private VistaTablero vistaTablero;
    
	/**
	 * Crea el juego y prepara la interfaz en cuanto se despliega la
	 * ventana principal.
	 */
    public void initialize() {
        juego = new Juego();
        vistaTablero = new VistaTablero(juego.tableroActual());
        vistaTablero.setOnDragDetected((MouseEvent event) -> {
            iniciaArrastraPieza(event);
        });
        vistaTablero.setOnDragDropped((DragEvent event) -> {
            terminaArrastraPieza(event);
        });
        pilaBase.getChildren().add(vistaTablero);
        
        ponBordes();
    }
    
    /**
     * Dibuja la notación para columnas y renglones alrededor de la vista
     * del tablero.
     */
    private void ponBordes() {
        int numEscaques = vistaTablero.lado();
        
        TilePane renglones = new TilePane(Orientation.VERTICAL);
        renglones.setPrefRows(numEscaques);
        renglones.setTileAlignment(Pos.CENTER);
        //renglones.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        Label l;
        for(int i = 0; i < numEscaques; i++) {
            l = new Label("" + (numEscaques - i));
            l.setPrefHeight(VistaTablero.LADO);
            l.setAlignment(Pos.CENTER);
            renglones.getChildren().add(l);
        }
        AnchorPane.setTopAnchor(renglones, 0.0);
        AnchorPane.setBottomAnchor(renglones, 0.0);
        leftPane.getChildren().add(renglones);
        
        TilePane columnas = new TilePane(Orientation.HORIZONTAL);
        columnas.setPrefColumns(numEscaques);
        columnas.setTileAlignment(Pos.CENTER);
        //renglones.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        for(int i = 0; i < numEscaques; i++) {
            l = new Label(Character.toString('a' + i));
            l.setPrefWidth(VistaTablero.LADO);
            l.setAlignment(Pos.CENTER);
            columnas.getChildren().add(l);
        }
        AnchorPane.setLeftAnchor(columnas, 0.0);
        AnchorPane.setRightAnchor(columnas, 0.0);
        bottomPane.getChildren().add(columnas);
    }
    
	/**
	 * Se ejecuta cuando el usuario inicia un evento de arrastre.
	 * Detecta si se tomó una pieza y la prepara para ser desplazada al
	 * escaque destino.
	 * @param event Información sobre el evento de ratón.
	 */
    private void iniciaArrastraPieza(MouseEvent event) {
        //System.out.println(event);
        if(event.getTarget() instanceof ImageView objetivo) {
            Escaque origen = (Escaque)(objetivo.getParent().getParent());
            origen.toFront();

            Dragboard db = origen.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putImage(objetivo.getImage());
            db.setDragView(objetivo.getImage());
            db.setContent(content);
        }
    }
    
	/**
	 * Si la pieza fue colocada en un escaque válido actualiza el tablero
	 * acordemente, de otro modo devuelve la pieza a su posición original.
	 * @param event Información sobre el evento de arrastre.
	 */
    public void terminaArrastraPieza(DragEvent event) {
        int colOrigen, renOrigen;
        int colDestino, renDestino;
        int numRenglones = juego.tableroActual().NUM_ESCAQUES;

        Escaque origen = (Escaque)event.getGestureSource();
        Escaque destino = (Escaque)event.getGestureTarget();

        colOrigen = VistaTablero.getColumnIndex(origen);
        renOrigen = VistaTablero.getRowIndex(origen);
        colDestino = VistaTablero.getColumnIndex(destino);
        renDestino = VistaTablero.getRowIndex(destino);
        
        Posición posOrigen = this.juego.tableroActual().
            new Posición((char) ('a' + colOrigen), numRenglones - renOrigen);
        Posición posDestino = this.juego.tableroActual().
            new Posición((char) ('a' + colDestino), numRenglones - renDestino);

        boolean success = false;
        if (juego.ejecutaJugada(posOrigen, posDestino)) {
            Dragboard db = event.getDragboard();
            destino.asigna(db.getImage());
            origen.asigna(null);
        }
        event.setDropCompleted(success);
        event.consume();
    }
    
}
