
package projekat;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AutorFXMLController implements Initializable {

    @FXML
    private TextField ime;
    @FXML
    private TextField sime;
    @FXML
    private TextField prezime;
    @FXML
    private TextArea napomena;
    @FXML
    private TextField id;
    /*Pomocu ove metode skuljaju se svi podaci iz polja i smestaju u konstruktor koji pravi objekat klase Autor
    nakon toga poziva staticku metodu DodajAutora() klase Autor i preko nje unose podaci u bazi*/
    @FXML
    private void DodajAutora(ActionEvent event){
        String imeA = ime.getText();
        String simeA = sime.getText();
        String prezimeA = prezime.getText();
        String napomenaA = napomena.getText();
        if(prezimeA.isEmpty() || imeA.isEmpty()){
            Prozor.prikazNeuspesno("dodali autora.", "Obavezna polja su ime i prezime");
        }
        else{
        Autor a = new Autor(imeA,simeA,prezimeA,napomenaA);
        try {
            int id1 = Autor.DodajAutora(a);
            Prozor.prikazUspesno("uneli","autora i njegov ID je " + id1);
        }
        catch (SQLException ex) {
            Prozor.prikazNeuspesno("se konektovali", "na bazu");
        }
        catch (Exception e) {
            Prozor.prikazNeuspesno("dodali", "autora");
        }
        }
        id.clear();
        ime.clear();
        sime.clear();
        prezime.clear();
        napomena.clear();
    }
    /*Pomocu ove metode se samo uzme podatak iz polja id i aktivira staticka metoda ObrisiAutora() klase Autor
    i preko njega se prosledjuje id iz polja i brise se autor iz baze*/
    @FXML
    private void ObrisiAutora(ActionEvent event){
        try {
            int id1 = Integer.parseInt(id.getText());
            Autor.NadjiAutora(id1);
            Autor.ObrisiAutora(id1);
            Prozor.prikazUspesno("obrisali", "autora");
        } 
        catch(NumberFormatException ne){
        Prozor.prikazNeuspesno("uneli", "broj ID autora, unesite broj");
        }
        catch (Exception e) {
            Prozor.prikazNeuspesno("uneli", "broj ID autora");
        }
        id.clear();
        ime.clear();
        sime.clear();
        prezime.clear();
        napomena.clear();
    }
    /*Pomocu ove metode se samo uzme podatak iz polja id i aktivira staticka metoda NadjiAutora() klase Autor
    i preko njega se prosledjuje id iz polja i nalazi se autor iz baze i popunjavaju se polja koja se dobiju
    iz objekta koji se vraca putem pozivanja gorenavedene staticke metode*/
    @FXML
    private void NadjiAutora(ActionEvent event) throws SQLException{
        try{
        int id1 = Integer.parseInt(id.getText());
        Autor a = Autor.NadjiAutora(id1);
        ime.setText(a.getIme());
        sime.setText(a.getSime());
        prezime.setText(a.getPrezime());
        napomena.setText(a.getNapomena());}
        catch(NumberFormatException ne){
        Prozor.prikazNeuspesno("uneli", "broj ID autora, unesite broj");
        }
        catch(Exception e){
        Prozor.prikazNeuspesno("uneli", "broj ID autora");
        }
    }
    /*Pomocu ove metode skuljaju se svi podaci iz polja i smestaju u konstruktor koji pravi objekat klase Autor
    nakon toga poziva staticku metodu IzmeniAutora() klase Autor i preko nje se menjaju podaci u bazi*/
    @FXML
    private void IzmeniAutora(ActionEvent event){
        try {
        int id1 = Integer.parseInt(id.getText());
        String iime = ime.getText();
        String isime = sime.getText();
        String iprezime = prezime.getText();
        String inapomena = napomena.getText();
        Autor a = new Autor(id1,iime,isime,iprezime,inapomena);
        Autor.IzmeniAutora(a);
        Prozor.prikazUspesno("izmenili", "autora");
        } 
        catch(NumberFormatException ne){
        Prozor.prikazNeuspesno("uneli", "broj ID autora, unesite broj");
        }
        catch (Exception e) {
            Prozor.prikazNeuspesno("uneli", "broj ID autora");
        }
        id.clear();
        ime.clear();
        sime.clear();
        prezime.clear();
        napomena.clear();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
