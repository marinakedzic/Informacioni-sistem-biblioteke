
package projekat;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class ZanrFXMLController implements Initializable {

    @FXML
    private TextField naziv;
    @FXML
    private TextField id;
    /*Pomocu ove metode skuljaju se svi podaci iz polja i smestaju u konstruktor koji pravi objekat klase Zanr
    nakon toga poziva staticku metodu DodajZanr() klase Zanr i preko nje unose podaci u bazi*/
    @FXML
    private void DodajZanr(ActionEvent event){
        String naziv1 = naziv.getText();
        if(naziv1.isEmpty()){
            Prozor.prikazNeuspesno("uneli", "zanr");
        }
        else{
            Zanr z = new Zanr(naziv1);
        try {
            int id1 = Zanr.DodajZanr(z);
            Prozor.prikazUspesno("uneli", "zanr i njegov ID je " + id1);
        } catch (SQLException ex) {
            Prozor.prikazNeuspesno("se konektovali", "na bazu");
        }
        }
        naziv.clear();
    }
    /*Pomocu ove metode se samo uzme podatak iz polja id i aktivira staticka metoda NadjiZanr() klase Zanr
    i preko njega se prosledjuje id iz polja i nalazi se zanr iz baze i popunjavaju se polja koja se dobiju
    iz objekta koji se vraca putem pozivanja gorenavedene staticke metode*/
    @FXML
    private void NadjiZanr(ActionEvent event) throws SQLException{
        try{
        int id1 = Integer.parseInt(id.getText());
        String ime = Zanr.NadjiZanr(id1);
        naziv.setText(ime);}
        catch(NumberFormatException ne){
        Prozor.prikazNeuspesno("uneli", "broj ID zanra, unesite broj");
        }
        catch(Exception e){
        Prozor.prikazNeuspesno("uneli", "broj ID zanra");
        }
    }
    /*Pomocu ove metode se samo uzme podatak iz polja id i aktivira staticka metoda ObrisiZanr() klase Zanr
    i preko njega se prosledjuje id iz polja i brise se zanr iz baze*/
    @FXML
    private void ObrisiZanr(ActionEvent event){
        
        try {
            int id1 = Integer.parseInt(id.getText());
            Zanr.NadjiZanr(id1);
            Zanr.ObrisiZanr(id1);
            Prozor.prikazUspesno("obrisali", "zanr");
        }
        catch(NumberFormatException ne){
        Prozor.prikazNeuspesno("uneli", "broj ID zanra, unesite broj");
        }
        catch (Exception e) {
            Prozor.prikazNeuspesno("uneli", "broj ID zanra");
        }
        id.clear();
        naziv.clear();
    }
    /*Pomocu ove metode skuljaju se svi podaci iz polja i smestaju u konstruktor koji pravi objekat klase Zanr
    nakon toga poziva staticku metodu IzmeniZanr() klase Zanr i preko nje se menjaju podaci u bazi*/
    @FXML
    private void IzmeniZanr(ActionEvent event){
        String inaziv = naziv.getText();
        if(inaziv.isEmpty()){
            Prozor.prikazNeuspesno("izmenili", "zanr");
        }
        else{
        try {
            int id1 = Integer.parseInt(id.getText());
            Zanr.IzmeniZanr(id1, inaziv);
            Prozor.prikazUspesno("izmenili","zanr");
        }
        catch(NumberFormatException ne){
        Prozor.prikazNeuspesno("uneli", "broj ID zanra, unesite broj");
        }
        catch (Exception e) {
            Prozor.prikazNeuspesno("uneli", "broj ID zanra");
        }
        }
        id.clear();
        naziv.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
}
