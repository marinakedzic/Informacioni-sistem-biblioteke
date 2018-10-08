
package projekat;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class IzdavacFXMLController implements Initializable {

    @FXML
    private TextField naziv;
    @FXML
    private TextArea napomena;
    @FXML
    private TextField id;
    /*Pomocu ove metode skuljaju se svi podaci iz polja i smestaju u konstruktor koji pravi objekat klase Izdavac
    nakon toga poziva staticku metodu DodajIzdavaca() klase Izdavac i preko nje unose podaci u bazi*/
    @FXML
    private void DodatiIzdavaca(ActionEvent event){
        try{
    String nazivI = naziv.getText();
    String napomenaI = napomena.getText();
        if(nazivI.isEmpty()){
            Prozor.prikazNeuspesno("uneli", "izdavaca");
        }
        else{
            Izdavac i = new Izdavac(nazivI,napomenaI);
            int id1 = Izdavac.DodajIzdavaca(i);
            Prozor.prikazUspesno("uneli","izdavaca i njegov ID je " + id1);
        }}
        catch(SQLException e){
        Prozor.prikazNeuspesno("se konektovali", "na bazu");
        }
    naziv.clear();
    napomena.clear();
    }
    /*Pomocu ove metode se samo uzme podatak iz polja id i aktivira staticka metoda ObrisiIzdavaca() klase Izdavac
    i preko njega se prosledjuje id iz polja i brise se izdavac iz baze*/
    @FXML
    private void ObrisiIzdavaca(ActionEvent event){
        
        try {
            int ido = Integer.parseInt(id.getText());
            Izdavac.ObrisiIzdavaca(ido);
            Prozor.prikazUspesno("obrisali", "izdavaca");
        } 
        catch(NumberFormatException ne){
        Prozor.prikazNeuspesno("uneli", "broj ID izdavaca, unesite broj");
        }
        catch (Exception e) {
            Prozor.prikazNeuspesno("uneli", "broj ID izdavaca");
        }
        id.clear();
        naziv.clear();
        napomena.clear();
    }
    /*Pomocu ove metode se samo uzme podatak iz polja id i aktivira staticka metoda NadjiIzdavaca() klase Izdavac
    i preko njega se prosledjuje id iz polja i nalazi se izdavac iz baze i popunjavaju se polja koja se dobiju
    iz objekta koji se vraca putem pozivanja gorenavedene staticke metode*/
    @FXML
    private void NadjiIzdavaca(ActionEvent event) throws SQLException{
        
        try{
        int id1 = Integer.parseInt(id.getText());
        Izdavac i = Izdavac.NadjiIzdavaca(id1);
        naziv.setText(i.getNaziv());
        napomena.setText(i.getNapomena());}
        catch(NumberFormatException ne){
        Prozor.prikazNeuspesno("uneli", "broj ID izdavaca, unesite broj");
        }
        catch(Exception e){
        Prozor.prikazNeuspesno("uneli", "broj ID izdavaca");
        }
        
    }
    /*Pomocu ove metode skuljaju se svi podaci iz polja i smestaju u konstruktor koji pravi objekat klase Izdavac
    nakon toga poziva staticku metodu IzmeniIzdavaca() klase Izdavac i preko nje se menjaju podaci u bazi*/
    @FXML
    private void IzmeniIzdavaca(ActionEvent event){
        
        String novoIme = naziv.getText();
        String novaNapomena = napomena.getText();
        try {
            int id1 = Integer.parseInt(id.getText());
            Izdavac.IzmeniIzdavaca(id1, novoIme, novaNapomena);
            Prozor.prikazUspesno("izmenili", "izdavaca");
        }
        catch(NumberFormatException ne){
        Prozor.prikazNeuspesno("uneli", "broj ID izdavaca, unesite broj");
        }
        catch (Exception e) {
            Prozor.prikazNeuspesno("uneli", "broj ID izdavaca");
        }
        id.clear();
        naziv.clear();
        napomena.clear();
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
