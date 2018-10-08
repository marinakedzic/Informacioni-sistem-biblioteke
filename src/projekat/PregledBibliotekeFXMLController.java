
package projekat;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PregledBibliotekeFXMLController implements Initializable {

    @FXML
    private TableView biblioteka;
    @FXML
    private TableColumn ISBN;
    @FXML
    private TableColumn knjiga;
    @FXML
    private TableColumn autor;
    @FXML
    private TableColumn zanr;
    @FXML
    private TableColumn stanje;
    @FXML
    private TableColumn izdavac;
    @FXML
    private TextField nISBN;
    @FXML
    private ComboBox<String> nAutor;
    @FXML
    private ComboBox<String> nIzdavac;
    @FXML
    private ComboBox<String> nZanr;

    //metoda koja je bavi otvaranjem novog prozora(stage-a) za pregled biblioteke
        @FXML
    private void DodajKnjigu(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("KnjigaFXML.fxml"));
        stage.getIcons().add(new Image(Projekat.class.getResourceAsStream("knjiga.png")));
        stage.setTitle("Knjiga");
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    /*Pomocu metode PrikazBiblioteke() koja se nalazi u klasi Knjiga uzimamo imena svih knjiga u bazi i vraćamo ih kao listu.
    i smešta u novu listu i elemente te liste unosi u odgovarajuće kolone tabele. Ova metoda se poziva u initialize()*/
    @FXML
    private void PrikazBiblioteka() throws SQLException{
        ObservableList<Knjiga> lista = Knjiga.PrikazBiblioteke();
        ObservableList<TableColumn> kolone = biblioteka.getColumns();
        kolone.get(0).setCellValueFactory(new PropertyValueFactory("ISBN"));
        kolone.get(1).setCellValueFactory(new PropertyValueFactory("naziv"));
        kolone.get(2).setCellValueFactory(new PropertyValueFactory("autor"));
        kolone.get(3).setCellValueFactory(new PropertyValueFactory("zanr"));
        kolone.get(4).setCellValueFactory(new PropertyValueFactory("stanje"));
        kolone.get(5).setCellValueFactory(new PropertyValueFactory("izdavac"));
        biblioteka.setItems(lista);
    }
    /*Pomocu metode IzaberiZanr() koja se nalazi u klasi Zanr uzimamo imena svih zanrova u bazi i vraćamo ih kao ArrayList-u.
    i smešta u novu listu i elemente te liste unosi u odgovarajući Combobox. Ova metoda se poziva u initialize()*/
    private void IzaberiZanr() throws SQLException{
          ArrayList <String> lista = Zanr.IzaberiZanr();
          for (String string : lista) {
            nZanr.getItems().add(string);
        }
    }
    /*Pomocu metode IzaberiIzdavaca() koja se nalazi u klasi Izdavac uzimamo imena svih izdavaca u bazi i vraćamo ih kao ArrayList-u.
    i smešta u novu listu i elemente te liste unosi u odgovarajući Combobox. Ova metoda se poziva u initialize()*/
    private void IzaberiIzdavaca() throws SQLException{
            ArrayList <String> lista = Izdavac.IzaberiIzdavaca();
            for (String string : lista) {
            nIzdavac.getItems().add(string);
        }
    }
    /*Pomocu metode IzaberiAutora() koja se nalazi u klasi Autor uzimamo imena svih autora u bazi i vraćamo ih kao ArrayList-u.
    i smešta u novu listu i elemente te liste unosi u odgovarajući Combobox. Ova metoda se poziva u initialize()*/
    private void IzaberiAutora() throws SQLException{
            ArrayList <String> lista = Autor.IzaberiAutora();
            for (String string : lista) {
            nAutor.getItems().add(string);
        }
    }
    /*Ova metoda prvo deklarise jednu listu i string koji ce od zavisnosti od zeljenje pretrage dobiti svoju vrednost
    U nizu if-else grananja menja se iskljucivo upit ka bazi  a samim tim i rezultat koji se dobija i smesta u listu
    a nakon toga u tabelu.*/
    @FXML
    private void PretragaBiblioteke() throws SQLException{
    ObservableList <Knjiga> lista = FXCollections.observableArrayList();
    String metoda;
    //ako su izabrani zanr, autor i izdavac
    if (!nZanr.getSelectionModel().isEmpty() && !nAutor.getSelectionModel().isEmpty() && !nIzdavac.getSelectionModel().isEmpty()){
    metoda = "select * from knjige where zanr=? AND autor=? AND izdavac=?";
    String zanrt = nZanr.getSelectionModel().getSelectedItem();
    String autort = nAutor.getSelectionModel().getSelectedItem();
    String izdavact = nIzdavac.getSelectionModel().getSelectedItem();
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement(metoda);
    ps.setString(1, zanrt);
    ps.setString(2, autort);
    ps.setString(3, izdavact);
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
    Knjiga k = new Knjiga();
    k.setISBN(rs.getInt("isbn"));
    k.setNaziv(rs.getString("naziv"));
    k.setStanje(rs.getInt("stanje"));
    k.setZanr(rs.getString("zanr"));
    k.setNapomena(rs.getString("napomena"));
    k.setAutor(rs.getString("autor"));
    k.setIzdavac(rs.getString("izdavac"));
    lista.add(k);
    }
    }
    //ako su izabrani zanr i autor
    else if(!nZanr.getSelectionModel().isEmpty() && !nAutor.getSelectionModel().isEmpty()){
    metoda = "select * from knjige where zanr=? AND autor=?";
    String zanrt = nZanr.getSelectionModel().getSelectedItem();
    String autort = nAutor.getSelectionModel().getSelectedItem();
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement(metoda);
    ps.setString(1, zanrt);
    ps.setString(2, autort);
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
    Knjiga k = new Knjiga();
    k.setISBN(rs.getInt("isbn"));
    k.setNaziv(rs.getString("naziv"));
    k.setStanje(rs.getInt("stanje"));
    k.setZanr(rs.getString("zanr"));
    k.setNapomena(rs.getString("napomena"));
    k.setAutor(rs.getString("autor"));
    k.setIzdavac(rs.getString("izdavac"));
    lista.add(k);
    }
    }
    //ako su izabrani zanr i izdavac
    else if(!nZanr.getSelectionModel().isEmpty() && !nIzdavac.getSelectionModel().isEmpty()){
    metoda = "select * from knjige where zanr=? AND izdavac=?";
    String zanrt = nZanr.getSelectionModel().getSelectedItem();
    String izdavact = nIzdavac.getSelectionModel().getSelectedItem();
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement(metoda);
    ps.setString(1, zanrt);
    ps.setString(2, izdavact);
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
    Knjiga k = new Knjiga();
    k.setISBN(rs.getInt("isbn"));
    k.setNaziv(rs.getString("naziv"));
    k.setStanje(rs.getInt("stanje"));
    k.setZanr(rs.getString("zanr"));
    k.setNapomena(rs.getString("napomena"));
    k.setAutor(rs.getString("autor"));
    k.setIzdavac(rs.getString("izdavac"));
    lista.add(k);
    }
    }
    //ako su izabrani autor i izdavac
    else if(!nAutor.getSelectionModel().isEmpty() && !nIzdavac.getSelectionModel().isEmpty()){
    metoda = "select * from knjige where autor=? AND izdavac=?";
    String autort = nAutor.getSelectionModel().getSelectedItem();
    String izdavact = nIzdavac.getSelectionModel().getSelectedItem();
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement(metoda);
    ps.setString(1, autort);
    ps.setString(2, izdavact);
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
    Knjiga k = new Knjiga();
    k.setISBN(rs.getInt("isbn"));
    k.setNaziv(rs.getString("naziv"));
    k.setStanje(rs.getInt("stanje"));
    k.setZanr(rs.getString("zanr"));
    k.setNapomena(rs.getString("napomena"));
    k.setAutor(rs.getString("autor"));
    k.setIzdavac(rs.getString("izdavac"));
    lista.add(k);
    }
    }
    //ako je izabran samo zanr
    else if(!nZanr.getSelectionModel().isEmpty()){
    metoda = "select * from knjige where zanr=?";
    String zanrt = nZanr.getSelectionModel().getSelectedItem();
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement(metoda);
    ps.setString(1, zanrt);
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
    Knjiga k = new Knjiga();
    k.setISBN(rs.getInt("isbn"));
    k.setNaziv(rs.getString("naziv"));
    k.setStanje(rs.getInt("stanje"));
    k.setZanr(rs.getString("zanr"));
    k.setNapomena(rs.getString("napomena"));
    k.setAutor(rs.getString("autor"));
    k.setIzdavac(rs.getString("izdavac"));
    lista.add(k);
    }
    }
    //ako je izabran samo autor
    else if(!nAutor.getSelectionModel().isEmpty()){
    metoda = "select * from knjige where autor=?";
    String autort = nAutor.getSelectionModel().getSelectedItem();
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement(metoda);
    ps.setString(1, autort);
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
    Knjiga k = new Knjiga();
    k.setISBN(rs.getInt("isbn"));
    k.setNaziv(rs.getString("naziv"));
    k.setStanje(rs.getInt("stanje"));
    k.setZanr(rs.getString("zanr"));
    k.setNapomena(rs.getString("napomena"));
    k.setAutor(rs.getString("autor"));
    k.setIzdavac(rs.getString("izdavac"));
    lista.add(k);
    }
    }
    //ako je izabran samo izdavac
    else if(!nIzdavac.getSelectionModel().isEmpty()){
    metoda = "select * from knjige where izdavac=?";
    String izdavact = nIzdavac.getSelectionModel().getSelectedItem();
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement(metoda);
    ps.setString(1, izdavact);
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
    Knjiga k = new Knjiga();
    k.setISBN(rs.getInt("isbn"));
    k.setNaziv(rs.getString("naziv"));
    k.setStanje(rs.getInt("stanje"));
    k.setZanr(rs.getString("zanr"));
    k.setNapomena(rs.getString("napomena"));
    k.setAutor(rs.getString("autor"));
    k.setIzdavac(rs.getString("izdavac"));
    lista.add(k);
    }
    }
    //ako je izabran samo ISBN
    else if(nISBN!=null){
    metoda = "select * from knjige where ISBN=?";
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement(metoda);
    try{
    int ISBNt = Integer.parseInt(nISBN.getText());
    ps.setInt(1, ISBNt);
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
    Knjiga k = new Knjiga();
    k.setISBN(rs.getInt("isbn"));
    k.setNaziv(rs.getString("naziv"));
    k.setStanje(rs.getInt("stanje"));
    k.setZanr(rs.getString("zanr"));
    k.setNapomena(rs.getString("napomena"));
    k.setAutor(rs.getString("autor"));
    k.setIzdavac(rs.getString("izdavac"));
    lista.add(k);
    }
    }
    catch(NumberFormatException ne){
    Prozor.prikazNeuspesno("pretrazili bazu.", "Molimo vas unesite neki parametar.");
    }
    }
    ObservableList<TableColumn> kolone = biblioteka.getColumns();
    kolone.get(0).setCellValueFactory(new PropertyValueFactory("ISBN"));
    kolone.get(1).setCellValueFactory(new PropertyValueFactory("naziv"));
    kolone.get(2).setCellValueFactory(new PropertyValueFactory("autor"));
    kolone.get(3).setCellValueFactory(new PropertyValueFactory("zanr"));
    kolone.get(4).setCellValueFactory(new PropertyValueFactory("stanje"));
    kolone.get(5).setCellValueFactory(new PropertyValueFactory("izdavac"));
    biblioteka.setItems(lista);
    }
    //metoda koja cisti sva polja i opet aktivira PrikazBiblioteka() metodu da prikaze sve knjige iz baze
    @FXML
    public void Ocisti(){
    nISBN.clear();
    nIzdavac.setValue(null);
    nAutor.setValue(null);
    nZanr.setValue(null);
    try {
        PrikazBiblioteka();
    } 
    catch (SQLException ex) {
        Prozor.prikazNeuspesno("se konektovali","na bazu");}
    }
    //Aktivira se odmah nakon paljenja ovog kontrolera
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            PrikazBiblioteka();
        } catch (SQLException ex) {
            Prozor.prikazNeuspesno("se konektovali","na bazu");}
        finally{
            System.out.println("Uspesna konekcija sa bazom.");
        }
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
