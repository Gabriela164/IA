/*
 * Código auxiliar para el curso de Inteligencia Artificial.
 * Se prohibe publicar o compartir soluciones.
 */
package ia.ajedrez;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Juego de ajedrez.
 * @author blackzafiro
 */
public class ApTablero extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/tablero.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/resources/styles.css").toExternalForm());

        stage.setTitle("Ajedrez");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
