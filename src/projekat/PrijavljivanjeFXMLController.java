
package projekat;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PrijavljivanjeFXMLController implements Initializable {

    @FXML
    private TextField ime;
    @FXML
    private PasswordField lozinka;
    
    @FXML
    //metoda koja je bavi otvaranjem novog prozora(stage-a) za pregled biblioteke
    private void PregledBiblioteke() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("GlavniFXML.fxml"));
        stage.getIcons().add(new Image(Projekat.class.getResourceAsStream("knjiga.png")));
        stage.setTitle("Biblioteka");
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    //metoda koja je bavi otvaranjem novog prozora(stage-a) za promenu lozinke
    private void PromenaLozinke() throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ZamenaLozinkeFXML.fxml"));
        stage.getIcons().add(new Image(Projekat.class.getResourceAsStream("change.png")));
        stage.setTitle("Promena lozinke");
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    /*metoda koja proverava da li uneta lozinka odgovara onoj u bazi ako jeste aktivira gorenavedenu metodu 
    PregledBiblioteke() ako ne obavestava da nije uneta tacna lozinka ili naziv*/
    private void Prijava(ActionEvent event) throws IOException, SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("select lozinka from admin where ime=?");
    ps.setString(1, "Admin");
    ResultSet rs = ps.executeQuery();
    rs.next();
    String lozinka1 = rs.getString("lozinka");
    if(lozinka.getText().equalsIgnoreCase(lozinka1) && ime.getText().equalsIgnoreCase("Admin")){
        PregledBiblioteke();
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    else{
    Prozor.prikazNeuspesno("ulogovali.", "Netacna lozinka ili username");
    }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
