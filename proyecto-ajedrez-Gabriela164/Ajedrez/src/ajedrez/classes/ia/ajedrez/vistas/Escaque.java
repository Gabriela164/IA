/*
 * Código auxiliar para el curso de Inteligencia Artificial.
 * Se prohibe publicar o compartir soluciones.
 */
package ia.ajedrez.vistas;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 * Escaque de un color que puede contener la imagen de una pieza.
 * @author blackzafiro
 */
public class Escaque extends StackPane {
    
    private static final Color enArrastre = Color.BLUE;
    
    private final Rectangle fondo;
    private VistaPieza vistaPieza;
    private final Color color;
    
	/**
	 * Crea un escaque rectangular del color indicado.
	 * @param color objeto de JavaFX para pintar el escaque.
	 */
    public Escaque(Color color) {
        this.color = color;
        
        fondo = new Rectangle(VistaTablero.LADO, VistaTablero.LADO, color);
        this.getChildren().add(fondo);
        vistaPieza = new VistaPieza();
        getChildren().add(vistaPieza);
        
        // Decorativo
        this.setOnDragEntered((DragEvent event) -> {
            resalta(true);
        });
        this.setOnDragExited((DragEvent event) -> {
            resalta(false);
        });
        
        // Necesario para que se active el evento DragDropped
        setOnDragOver((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            if (db.hasImage()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
    }
    
	/**
	 * Asigna la imagen para ser mostrada en este escaque.
	 * @param p imagen a mostrar o null si se quiere borrar la que se estaba
	 * mostrando.
	 */
    public void asigna(Image p) {
        vistaPieza.asignaImagen(p);
    }
    
    /**
     * Indica si cambiar el color del escaque para resaltarlo
     * o devolverlo a su color original.
     * @param resalta si se quiere resaltar.
     */
    public void resalta(boolean resalta) {
        fondo.setFill((resalta) ? enArrastre : color);
    }
}
