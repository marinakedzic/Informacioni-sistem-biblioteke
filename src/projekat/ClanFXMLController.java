
package projekat;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ClanFXMLController implements Initializable {

    @FXML
    private ListView<String> zknjige;
    @FXML
    private TextField ime;
    @FXML
    private TextField prezime;
    @FXML
    private TextField cbroj;
    @FXML
    private TextField email;
    @FXML
    private TextField ISBN;
    @FXML
    private ListView<Timestamp> zvreme;
/*Pomocu ove metode skuljaju se svi podaci iz polja i smestaju u konstruktor koji pravi objekat klase Clan
    nakon toga poziva staticku metodu DodajClana() klase Clan i preko nje unose podaci u bazi*/
    @FXML
    private void DodajClana(ActionEvent ev) throws SQLException{
    try{
    String ime1 = ime.getText();
    String prezime1 = prezime.getText();
    String email1 = email.getText();
    int clanskaKarta = Integer.parseInt(cbroj.getText());
    if(prezime1.isEmpty() || ime1.isEmpty()){
    Prozor.prikazNeuspesno("uneli", "clana. Obavezna polja su ime, prezime i clanskaKarta");
    }
    else{
    Clan c = new Clan(ime1,prezime1,clanskaKarta,email1);
    Clan.DodajClana(c);
    Prozor.prikazUspesno("uneli", "clana");
    }
    }
    catch(NumberFormatException ne){
    Prozor.prikazNeuspesno("uneli", "broj clanske karte clana, unesite broj");
    }
    catch(Exception e){
    Prozor.prikazNeuspesno("uneli", "clana. Obavezna polja su ime, prezime i clanskaKarta");
    }
    ime.clear();
    prezime.clear();
    email.clear();
    cbroj.clear();
    }
    /*Pomocu ove metode se samo uzme podatak iz polja cbroj i aktivira staticka metoda ObrisiClana() klase Clan
    i preko njega se prosledjuje broj clanske karte iz polja i brise se clan iz baze*/
    @FXML
    private void ObrisiClana(ActionEvent ev) throws SQLException{
    try{
    int clanskaKarta = Integer.parseInt(cbroj.getText());
    Clan.ObrisiClana(clanskaKarta);
    Prozor.prikazUspesno("obrisali", "clana");}
    catch(NumberFormatException ne){
    Prozor.prikazNeuspesno("uneli", "broj clanske karte clana, unesite broj");
    }
    catch(Exception e){
    Prozor.prikazNeuspesno("uneli", "broj clanske karte");
    }
    ime.clear();
    prezime.clear();
    email.clear();
    cbroj.clear();
    }
    /*Pomocu ove metode skuljaju se svi podaci iz polja i smestaju u konstruktor koji pravi objekat klase Clan
    nakon toga poziva staticku metodu IzmeniClana() klase Clan i preko nje se menjaju podaci u bazi*/
    @FXML
    private void IzmeniClana(ActionEvent ev) throws SQLException{
    String ime1 = ime.getText();
    String prezime1 = prezime.getText();
    String email1 = email.getText();
    try{
    int clanskaKarta = Integer.parseInt(cbroj.getText());
    Clan c = new Clan(ime1,prezime1,clanskaKarta,email1);
    Clan.IzmeniClana(c);
        Prozor.prikazUspesno("izmenili", "clana");
    }
    catch(NumberFormatException ne){
    Prozor.prikazNeuspesno("uneli", "broj clanske karte clana, unesite broj");
    }
    catch(Exception e){
        Prozor.prikazNeuspesno("uneli", "broj clanske katre");
    }
    ime.clear();
    prezime.clear();
    email.clear();
    cbroj.clear();
    }
    /*Pomocu ove metode se samo uzme podatak iz polja cbroj i aktivira staticka metoda NadjiClana() klase Clan
    i preko njega se prosledjuje broj clanske karte iz polja i nalazi se clan iz baze i popunjavaju se polja koja se dobiju
    iz objekta koji se vraca putem pozivanja gorenavedene staticke metode*/
    @FXML
    private void NadjiClana(ActionEvent e) throws SQLException{
    try{
    int clanskaKarta = Integer.parseInt(cbroj.getText());
    Clan c = Clan.NadjiClana(clanskaKarta);
    ime.setText(c.getIme());
    prezime.setText(c.getPrezime());
    email.setText(c.getEmail());
    cbroj.setText(String.valueOf(c.getClanskaKarta()));
    ObservableList listaNaziva = Clan.NadjiKnjige(clanskaKarta);
    zknjige.setItems(listaNaziva);
    ObservableList listaVreme = Clan.NadjiVreme(clanskaKarta);
    zvreme.setItems(listaVreme);}
    catch(NumberFormatException ne){
    Prozor.prikazNeuspesno("uneli", "broj clanske karte clana, unesite broj");
    }
    catch(Exception ex){
        Prozor.prikazNeuspesno("uneli", "broj clanske katre");
    }
    
    }
    /*metoda koja uzima podatke iz polja od kojih clanska karta i ISBN su obavezni 
    aktivira staticku metodu ZaduziKnjigu() klase Clan i tako zaduzuje knjigu i posle se aktiviraju dve staticke metode
    takodje klase Clan koja prikazuje vreme i nazivi knjiga koji su zaduzene*/
    @FXML
    private void ZaduziKnjigu(ActionEvent ev) throws SQLException{
    try{
    int IB = Integer.parseInt(ISBN.getText());
    int CKB = Integer.parseInt(cbroj.getText());
    if(cbroj.equals(null) || ISBN.equals(null)){
    Prozor.prikazNeuspesno("ste zaduzili", "knjigu jer niste izabrali clana ili ste uneli nepostojeci ISBN");
    }
    else{
    Clan.ZaduziKnjigu(CKB, IB);
    }
    ObservableList lista = Clan.NadjiKnjige(CKB);
    zknjige.setItems(lista);
    ObservableList listaVreme = Clan.NadjiVreme(CKB);
    zvreme.setItems(listaVreme);
    ISBN.clear();}
    catch(NumberFormatException ne){
    Prozor.prikazNeuspesno("uneli", "broj clanske karte clana ili ISBN knjige");
    }
    catch(Exception e){
        Prozor.prikazNeuspesno("uneli", "broj clanske katre");
    }
    }
    /*metoda koja uzima podatke iz polja od kojih clanska karta i ISBN su obavezni 
    aktivira staticku metodu RazduziKnjigu() klase Clan i tako razduzuje knjigu i posle se aktiviraju dve staticke metode
    takodje klase Clan koja prikazuje vreme i nazivi knjiga koji su ostale zaduzene*/
    @FXML
    private void RazduziKnjigu(ActionEvent ev) throws SQLException{
    try{
    int IB = Integer.parseInt(ISBN.getText());
    int CKB = Integer.parseInt(cbroj.getText());
    if(cbroj.equals(null) || ISBN.equals(null)){
    Prozor.prikazNeuspesno("ste zaduzili", "knjigu jer niste izabrali clana ili ste uneli nepostojeci ISBN");
    }
    else{
    Clan.RazduziKnjigu(CKB, IB);
    ObservableList lista = Clan.NadjiKnjige(CKB);
    zknjige.setItems(lista);
    ObservableList listaVreme = Clan.NadjiVreme(CKB);
    zvreme.setItems(listaVreme);
    ISBN.clear();
    }}
    catch(NumberFormatException ne){
    Prozor.prikazNeuspesno("uneli", "broj clanske karte clana ili ISBN knjige");
    }
    catch(Exception e){
        Prozor.prikazNeuspesno("uneli", "broj clanske katre");
    }
    }
    /*metoda koja kada se selektuje naziv odredjena knjiga u tabeli aktivira se staticka metoda klase Clan koja 
   prima ime te selektoavne knjige i  vraca ISBN koje se upisuje u poje ISBN
    */
    @FXML
    private void VracaISBN() throws SQLException{
        String naziv = zknjige.getSelectionModel().getSelectedItem();
        int ISBN1 = Clan.NadjiISBN(naziv);
        String broj = String.valueOf(ISBN1);
        ISBN.setText(broj);
    }
     //metoda koja cisti sva polja i opet aktivira PrikazBiblioteka() metodu da prikaze sve knjige iz baze
    @FXML
    private void Ocisti() throws SQLException{
    ime.clear();
    prezime.clear();
    email.clear();
    cbroj.clear();
    ISBN.clear();
    zknjige.getItems().clear();
    zvreme.getItems().clear();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}
