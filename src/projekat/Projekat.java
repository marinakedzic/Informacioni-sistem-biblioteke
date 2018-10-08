
package projekat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Projekat extends Application {
    //metoda koja je bavi otvaranjem novog prozora(stage-a) za prijavljivanje
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PrijavljivanjeFXML.fxml"));
        stage.getIcons().add(new Image(Projekat.class.getResourceAsStream("password.png")));
        stage.setTitle("Prijava");
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
