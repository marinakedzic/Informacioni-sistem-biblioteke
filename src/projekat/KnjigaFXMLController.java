
package projekat;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class KnjigaFXMLController implements Initializable {

    @FXML
    private TextField naziv;
    @FXML
    private TextField ISBN;
    @FXML
    private TextField stanje;
    @FXML
    private TextArea napomena;
    @FXML
    private ComboBox<String> iznar;
    @FXML
    private ComboBox<String> iautor;
    @FXML
    private ComboBox<String> iizdavac;
    //metoda koja cisti sva polja
        @FXML
    public void Ocisti(){
        naziv.clear();
        napomena.clear();
        ISBN.clear();
        stanje.clear();
        iznar.setPromptText(null);
        iznar.getSelectionModel().clearSelection();
        iautor.setPromptText(null);
        iautor.getSelectionModel().clearSelection();
        iizdavac.setPromptText(null);
        iizdavac.getSelectionModel().clearSelection();
    }
    /*Pomocu ove metode skuljaju se svi podaci iz polja i smestaju u konstruktor koji pravi objekat klase Knjiga
    nakon toga poziva staticku metodu DodajKnjigu() klase Knjiga i preko nje unose podaci u bazi*/
    @FXML
    private void DodajKnjigu(ActionEvent event) throws SQLException{
    try{
    String nazivK = naziv.getText();
    int ISBNK = Integer.parseInt(ISBN.getText());
    int stanjeK = Integer.parseInt(stanje.getText());
    String napomenaK = napomena.getText();
    String zanrK = iznar.getSelectionModel().getSelectedItem();
    String autorK = iautor.getSelectionModel().getSelectedItem();
    String izdavacK = iizdavac.getSelectionModel().getSelectedItem();
    Knjiga k = new Knjiga(ISBNK,nazivK,zanrK,stanjeK,napomenaK,autorK,izdavacK);
    Knjiga.DodajKnjigu(k);
        naziv.clear();
        napomena.clear();
        ISBN.clear();
        stanje.clear();
        iznar.setPromptText(null);
        iznar.getSelectionModel().clearSelection();
        iautor.setPromptText(null);
        iautor.getSelectionModel().clearSelection();
        iizdavac.setPromptText(null);
        iizdavac.getSelectionModel().clearSelection();
    Prozor.prikazUspesno("uneli", "knjigu");}
    catch(Exception e){
    Prozor.prikazNeuspesno("uneli", "knjigu. Obavezna polja su naziv, ISBN i stanje");
    }
    
    }
    //metoda koja je bavi otvaranjem novog prozora(stage-a) za manipulisanje sa zanrom
    @FXML
    private void DodajZanr(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ZanrFXML.fxml"));
        stage.getIcons().add(new Image(Projekat.class.getResourceAsStream("knjiga.png")));
        stage.setTitle("Zanr");
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    //metoda koja je bavi otvaranjem novog prozora(stage-a) za manipulisanje sa autorima
    @FXML
    private void DodajAutora(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AutorFXML.fxml"));
        stage.getIcons().add(new Image(Projekat.class.getResourceAsStream("knjiga.png")));
        stage.setTitle("Autor");
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    //metoda koja je bavi otvaranjem novog prozora(stage-a) za manipulisanje sa izdavacima
    @FXML
    private void DodajIzdavaca(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("IzdavacFXML.fxml"));
        stage.getIcons().add(new Image(Projekat.class.getResourceAsStream("knjiga.png")));
        stage.setTitle("Izdavac");
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    /*Pomocu metode IzaberiZanr() koja se nalazi u klasi Zanr uzimamo imena svih zanrova u bazi i vraćamo ih kao ArrayList-u.
    i smešta u novu listu i elemente te liste unosi u odgovarajući Combobox. Ova metoda se poziva u initialize()*/
    private void IzaberiZanr() throws SQLException{
          ArrayList <String> lista = Zanr.IzaberiZanr();
          for (String string : lista) {
            iznar.getItems().add(string);
        }
    }
    /*Pomocu metode IzaberiIzdavaca() koja se nalazi u klasi Izdavac uzimamo imena svih izdavaca u bazi i vraćamo ih kao ArrayList-u.
    i smešta u novu listu i elemente te liste unosi u odgovarajući Combobox. Ova metoda se poziva u initialize()*/
    private void IzaberiIzdavaca() throws SQLException{
            ArrayList <String> lista = Izdavac.IzaberiIzdavaca();
            for (String string : lista) {
            iizdavac.getItems().add(string);
        }
    }
     /*Pomocu metode IzaberiAutora() koja se nalazi u klasi Autor uzimamo imena svih autora u bazi i vraćamo ih kao ArrayList-u.
    i smešta u novu listu i elemente te liste unosi u odgovarajući Combobox. Ova metoda se poziva u initialize()*/
    private void IzaberiAutora() throws SQLException{
            ArrayList <String> lista = Autor.IzaberiAutora();
            for (String string : lista) {
            iautor.getItems().add(string);
        }
    }
    /*Pomocu ove metode se samo uzme podatak iz polja ISBN i aktivira staticka metoda ObrisiKnjigu() klase Knjiga
    i preko njega se prosledjuje ISBN iz polja i brise se knjiga iz baze*/
    @FXML
    private void ObrisiKnjigu(ActionEvent event) throws SQLException{
        try{
        int ISBN1 = Integer.parseInt(ISBN.getText());
        Knjiga.ObrisiKnjigu(ISBN1);
        naziv.clear();
        napomena.clear();
        ISBN.clear();
        stanje.clear();
        iznar.setPromptText(null);
        iznar.getSelectionModel().clearSelection();
        iautor.setPromptText(null);
        iautor.getSelectionModel().clearSelection();
        iizdavac.setPromptText(null);
        iizdavac.getSelectionModel().clearSelection();
        Prozor.prikazUspesno("obrisali", "knjigu");
        }
        catch(NumberFormatException ne){
        Prozor.prikazNeuspesno("uneli", "ISBN knjige, unesite broj");
        }
        catch(Exception e){
        Prozor.prikazNeuspesno("uneli ISBN", "knjige");
        }

    }
    /*Pomocu ove metode se samo uzme podatak iz polja ISBN i aktivira staticka metoda NadjiKnjigu() klase Knjiga
    i preko njega se prosledjuje ISBN iz polja i nalazi se knjiga iz baze i popunjavaju se polja koja se dobiju
    iz objekta koji se vraca putem pozivanja gorenavedene staticke metode*/
    @FXML
    private void NadjiKnjigu(ActionEvent event) throws SQLException{
        try{
        int ISBN1 = Integer.parseInt(ISBN.getText());
        Knjiga k = Knjiga.NadjiKnjigu(ISBN1);
        naziv.setText(k.getNaziv());
        napomena.setText(k.getNapomena());
        iznar.setPromptText(k.getZanr());
        iautor.setPromptText(k.getAutor());
        iizdavac.setPromptText(k.getIzdavac());
        String stanje1 = String.valueOf(k.getStanje());
        stanje.setText(stanje1);}
        catch(NumberFormatException ne){
        Prozor.prikazNeuspesno("uneli", "ISBN knjige, unesite broj");
        }
        catch(Exception e){
            Prozor.prikazNeuspesno("uneli ISBN", "knjige");
                }
    }
    /*Pomocu ove metode skuljaju se svi podaci iz polja i smestaju u konstruktor koji pravi objekat klase Knjiga
    nakon toga poziva staticku metodu IzmeniKnjigu() klase Knjiga i preko nje se menjaju podaci u bazi*/
    @FXML
    private void IzmeniKnjigu(ActionEvent event) throws SQLException{
        try{
        int ISBN1 = Integer.parseInt(ISBN.getText());
        String nazivK = naziv.getText();
        int stanjeK = Integer.parseInt(stanje.getText());
        String napomenaK = napomena.getText();
        String zanrK;
        if(iznar.getSelectionModel().getSelectedItem()==null){
        zanrK = iznar.getPromptText(); }
        else{
        zanrK = iznar.getSelectionModel().getSelectedItem();
        }
        String autorK;
        if(iautor.getSelectionModel().getSelectedItem()==null){
        autorK = iautor.getPromptText();
        }
        else{
        autorK = iautor.getSelectionModel().getSelectedItem();
        }
        String izdavacK;
        if(iizdavac.getSelectionModel().getSelectedItem()==null){
        izdavacK = iizdavac.getPromptText();
        }
        else{
        izdavacK = iizdavac.getSelectionModel().getSelectedItem();
        }
        Knjiga k = new Knjiga(ISBN1,nazivK,zanrK,stanjeK,napomenaK,autorK,izdavacK);
        Knjiga.IzmeniKnjigu(k);
        naziv.clear();
        napomena.clear();
        ISBN.clear();
        stanje.clear();
        iznar.setPromptText(null);
        iznar.getSelectionModel().clearSelection();
        iautor.setPromptText(null);
        iautor.getSelectionModel().clearSelection();
        iizdavac.setPromptText(null);
        iizdavac.getSelectionModel().clearSelection();
        Prozor.prikazUspesno("izmenili", "knjigu");}
        catch(NumberFormatException ne){
        Prozor.prikazNeuspesno("uneli", "ISBN knjige, unesite broj");
        }
        catch(Exception e){
        Prozor.prikazNeuspesno("uneli ISBN", "knjige");
        }
    }
//Aktivira se odmah nakon paljenja ovog kontrolera
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            IzaberiZanr();
        } catch (SQLException ex) {
            Prozor.prikazNeuspesno("se konektovali","na bazu");
        }
        try {
            IzaberiIzdavaca();
        } catch (SQLException ex) {
            Prozor.prikazNeuspesno("se konektovali","na bazu");
        }
        try {
            IzaberiAutora();
        } catch (SQLException ex) {
            Prozor.prikazNeuspesno("se konektovali","na bazu");
        }
    }


    
}
