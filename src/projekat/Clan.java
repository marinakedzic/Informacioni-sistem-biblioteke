package projekat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Clan {
    private String ime;
    private String prezime;
    private int clanskaKarta;
    private String email;

    /**
     * @return the ime
     */
    public String getIme() {
        return ime;
    }

    /**
     * @param ime the ime to set
     */
    public void setIme(String ime) {
        this.ime = ime;
    }

    /**
     * @return the prezime
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * @param prezime the prezime to set
     */
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    /**
     * @return the clanskaKarta
     */
    public int getClanskaKarta() {
        return clanskaKarta;
    }

    /**
     * @param clanskaKarta the clanskaKarta to set
     */
    public void setClanskaKarta(int clanskaKarta) {
        this.clanskaKarta = clanskaKarta;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Clan(){}
    public Clan(String ime, String prezime, int clanskaKarta, String email){
    this.ime = ime;
    this.prezime = prezime;
    this.clanskaKarta = clanskaKarta;
    this.email = email;
    }
    //staticka metoda koja prima objekat klase Clan i ubacuje ga u bazu
    public static void DodajClana(Clan c) throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("insert into clanovi (ime, prezime,clanskaKarta,email) values(?,?,?,?)");
    ps.setString(1, c.getIme());
    ps.setString(2, c.getPrezime());
    ps.setInt(3, c.getClanskaKarta());
    ps.setString(4, c.getEmail());
    ps.execute();
    }
    //staticka metoda koja prima parametar ceo broj tj broj clanske karte clana i preko njega nalazi i brise clana iz baze
    public static void ObrisiClana(int clanskaKarta) throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("delete from clanovi where clanskaKarta=?");
    ps.setInt(1, clanskaKarta);
    ps.execute();
    }
    //staticka metoda koja prima objekat klase Clan i vrsi date izmene u bazi koje su nastale
    public static void IzmeniClana(Clan c) throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("update clanovi set ime=?,prezime=?, email=? where clanskaKarta=?");
    ps.setInt(4, c.getClanskaKarta());
    ps.setString(1, c.getIme());
    ps.setString(2, c.getPrezime());
    ps.setString(3, c.getEmail());
    ps.execute();
    }
    //staticka metoda koja prima parametar ceo broj tj clanski broj karte clana i vraca objekat koji je nadjen preko clanskog broja karte koji mu je prosledjen
    public static Clan NadjiClana(int clanskaKarta) throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("select * from clanovi where clanskaKarta=?");   
    ps.setInt(1, clanskaKarta);
    ResultSet rs = ps.executeQuery();
    rs.next();
    Clan c = new Clan();
    c.setIme(rs.getString("ime"));
    c.setPrezime(rs.getString("prezime"));
    c.setEmail(rs.getString("email"));
    c.setClanskaKarta(rs.getInt("clanskaKarta"));
    return c;
    }
    /*staticka metoda koja prima parametar clanski broj i ISBN knjige i vrsi ubacivanje podataka u tabelu vezna,
    nakon toga preko parametra koji predstavlja ISBN knjige nalazi stanje i smanjuje za 1 i upisuje izmenu u bazu knjige*/
    public static void ZaduziKnjigu(int CKB, int IB) throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps2 = conn.prepareStatement("select stanje from knjige where ISBN=?");
    ps2.setInt(1, IB);
    ResultSet rs = ps2.executeQuery();
    rs.next();
    int stanje = rs.getInt("stanje");
    if(stanje>0){
    PreparedStatement ps = conn.prepareStatement("insert into vezna (CKB,IB) values (?,?)");   
    ps.setInt(1, CKB);
    ps.setInt(2, IB);
    ps.execute();
    int pravoStanje = stanje-1;
    PreparedStatement ps3 = conn.prepareStatement("update knjige set stanje=? where ISBN=?");
    ps3.setInt(1, pravoStanje);
    ps3.setInt(2, IB);
    ps3.execute();
    }
    else{
    Prozor.prikazNeuspesno("zaduzili knjigu", "jer je nema na stanju");
    }
    
    }
    /*staticka metoda koja prima parametar clanski broj i ISBN knjige i vrsi brisanje podataka iz tabele vezna,
    nakon toga preko parametra koji predstavlja ISBN knjige nalazi stanje i povecava za 1 i upisuje izmenu u bazu knjige*/
    public static void RazduziKnjigu(int CKB, int IB) throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("delete from vezna where CKB=? AND IB=?");   
    ps.setInt(1, CKB);
    ps.setInt(2, IB);
    ps.execute();
    PreparedStatement ps2 = conn.prepareStatement("select stanje from knjige where ISBN=?");
    ps2.setInt(1, IB);
    ResultSet rs = ps2.executeQuery();
    rs.next();
    int stanje = rs.getInt("stanje");
    int pravoStanje = stanje+1;
    PreparedStatement ps3 = conn.prepareStatement("update knjige set stanje=? where ISBN=?");
    ps3.setInt(1, pravoStanje);
    ps3.setInt(2, IB);
    ps3.execute();
    
    }
    /*staticka metoda koja prima parametar clanski broj i vrsi pretragu podataka u tabeli vezna, uzima listu ISBN-a
    i preko te liste dobija listu naziva knjiga i tu listu vraca*/
    public static ObservableList<String> NadjiKnjige(int clanskaKarta) throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps2 = conn.prepareStatement("select IB from vezna where CKB=?");   
    ps2.setInt(1, clanskaKarta);
    ResultSet rs2 = ps2.executeQuery();
    ObservableList<String> listaNaziva = FXCollections.observableArrayList();
    ObservableList<Integer> listaISBN = FXCollections.observableArrayList();
    while(rs2.next()){
    Integer ISBN = rs2.getInt("IB");
    listaISBN.add(ISBN);
    }
        for (Integer integer : listaISBN) {         
    PreparedStatement ps3 = conn.prepareStatement("select naziv from knjige where ISBN=?");   
    ps3.setInt(1, integer);
    ResultSet rs3 = ps3.executeQuery();
    while(rs3.next()){
    String naziv = rs3.getString("naziv");
    listaNaziva.add(naziv);
    }
    
        }
        return listaNaziva;
}
    /*staticka metoda koja prima parametar naziv knjige i pomocu njega vraca broj ISBN-a knjige */
        public static int NadjiISBN(String naziv) throws SQLException{
    Connection conn = MyConnection.getConnection();         
    PreparedStatement ps3 = conn.prepareStatement("select ISBN from knjige where naziv=?");   
    ps3.setString(1, naziv);
    ResultSet rs3 = ps3.executeQuery();
    rs3.next();
    int ISBN = rs3.getInt("ISBN");
    return ISBN;
    
}
    /*staticka metoda koja parametar prima clanski broj i vrsi pretragu podataka u tabeli vezna, 
    uzima listu vremena zaduzenih knjiga i tu listu vraca*/
    public static ObservableList<Timestamp> NadjiVreme(int clanskaKarta) throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("select vreme from vezna where CKB=?");   
    ps.setInt(1, clanskaKarta);
    ResultSet rs = ps.executeQuery();
    ObservableList<Timestamp> listaVreme = FXCollections.observableArrayList();
    while(rs.next()){
    Timestamp vreme = rs.getTimestamp("vreme");
    listaVreme.add(vreme);
    }
    return listaVreme;}
}