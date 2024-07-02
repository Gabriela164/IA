/*
 * Código auxiliar para el curso de Inteligencia Artificial.
 * Se prohibe publicar o compartir soluciones.
 */
package ia.ajedrez.vistas;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import static javafx.scene.layout.StackPane.setAlignment;

/**
 * Contiene la imagen de una pieza, con el formato adecuado para ser colocada
 * sobre un escaque.
 * @author blackzafiro
 */
public class VistaPieza extends StackPane {
    protected final ImageView view;
    
	/**
	 * Objeto para poder mostrar la imagen de una pieza, la escala al
	 * tamaño adecuado para colocarse en el escaque.
	 */
    public VistaPieza() {
        view = new ImageView();
        view.setFitWidth(0.7 * VistaTablero.LADO);
        view.setPreserveRatio(true);
        setAlignment(view, Pos.CENTER);
        getChildren().add(view);
    }
    
	/**
	 * Asigna la nueva imagen a mostrar.
	 * @param imagen Imagen a mostrar o null si no se quiere mostrar nada.
	 */
    public void asignaImagen(Image imagen) {
        view.setImage(imagen);
    }

}
