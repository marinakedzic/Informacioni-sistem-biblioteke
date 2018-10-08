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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;


public class ZamenaLozinkeFXMLController implements Initializable {

    @FXML
    private PasswordField nova;
    @FXML
    private PasswordField stara;
    @FXML
    private PasswordField potvrda;
    /*Metoda koja se bavi zamenom sifre u bazi. Prvo proverava da li stara(trenutna)jednaka onoj u bazi. 
    Ako nije javlja da je pogresno unesena lozinka.
    Ako jeste proverava da li su nova i potvrdjena jednake. Ako jesu menja se lozinka u bazi. 
    Ako nije javlja se da su pogresno unesene lozinke*/
    @FXML
    private void Zamena(ActionEvent event) throws IOException, SQLException{
    Connection conn = MyConnection.getConnection();    
    PreparedStatement ps = conn.prepareStatement("select lozinka from admin where ime=?");
    ps.setString(1, "Admin");
    ResultSet rs = ps.executeQuery();
    rs.next();
    String lozinka1 = rs.getString("lozinka");
    if(stara.getText().equalsIgnoreCase(lozinka1)){
        if(nova.getText().equalsIgnoreCase(potvrda.getText())){
        String novaLozinka = potvrda.getText();
        PreparedStatement ps1 = conn.prepareStatement("update admin set lozinka=? where ime=?");
        ps1.setString(1, novaLozinka);
        ps1.setString(2, "Admin");
        ps1.execute();
            Prozor.prikazUspesno("promenili", "lozinku");
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        }
        else{
        Prozor.prikazNeuspesno("promenili lozinku.", "Nepravilno ste ukucali novu lozinku");
        }
    }
    else{
    Prozor.prikazNeuspesno("promenili lozinku.", "Nepravilno ste ukucali trenutnu lozinku");
    }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
}
