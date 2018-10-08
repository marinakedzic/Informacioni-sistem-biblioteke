
package projekat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GlavniFXMLController implements Initializable {
    //metoda koja je bavi otvaranjem novog prozora(stage-a) za pregled biblioteke
    @FXML
    private void PregledBiblioteke(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PregledBibliotekeFXML.fxml"));
        stage.getIcons().add(new Image(Projekat.class.getResourceAsStream("knjiga.png")));
        stage.setTitle("Pregled Biblioteke");
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    //metoda koja je bavi otvaranjem novog prozora(stage-a) za pregled clanova
        @FXML
    private void PregledClanova(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ClanFXML.fxml"));
        stage.getIcons().add(new Image(Projekat.class.getResourceAsStream("clanovi.png")));
        stage.setTitle("Pregled Clanova");
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    } 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
