package projekat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Knjiga {


    private int ISBN;
    private String naziv;
    private String zanr;
    private int stanje;
    private String napomena;
    private String izdavac;
    private String autor;
    
     /**
     * @return the ISBN
     */
    public int getISBN() {
        return ISBN;
    }

    /**
     * @param ISBN the ISBN to set
     */
    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * @return the naziv
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * @param naziv the naziv to set
     */
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    /**
     * @return the zanr
     */
    public String getZanr() {
        return zanr;
    }

    /**
     * @param zanr the zanr to set
     */
    public void setZanr(String zanr) {
        this.zanr = zanr;
    }

    /**
     * @return the stanje
     */
    public int getStanje() {
        return stanje;
    }

    /**
     * @param stanje the stanje to set
     */
    public void setStanje(int stanje) {
        this.stanje = stanje;
    }

    /**
     * @return the napomena
     */
    public String getNapomena() {
        return napomena;
    }

    /**
     * @param napomena the napomena to set
     */
    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }
    
        /**
     * @return the izdavac
     */
    public String getIzdavac() {
        return izdavac;
    }

    /**
     * @param izdavac the izdavac to set
     */
    public void setIzdavac(String izdavac) {
        this.izdavac = izdavac;
    }

    /**
     * @return the autor
     */
    public String getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public Knjiga(){}
    public Knjiga(int ISBN,String naziv,String zanr,int stanje,String napomena, String autor, String izdavac){
    this.ISBN = ISBN;
    this.naziv = naziv;
    this.zanr = zanr;
    this.stanje = stanje;
    this.napomena = napomena;
    this.autor = autor;
    this.izdavac = izdavac;
    }
    //staticka metoda koja prima objekat klase Knjiga i ubacuje ga u bazu
    public static void DodajKnjigu(Knjiga k) throws SQLException{
        Connection conn = MyConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("insert into knjige(ISBN,naziv,zanr,stanje,napomena,autor,izdavac)values(?,?,?,?,?,?,?)");
        ps.setInt(1, k.getISBN());
        ps.setString(2, k.getNaziv());
        ps.setString(3, k.getZanr());
        ps.setInt(4, k.getStanje());
        ps.setString(5, k.getNapomena());
        ps.setString(6, k.getAutor());
        ps.setString(7, k.getIzdavac());
        ps.execute();
    }
    //staticka metoda koja prima parametar ceo broj tj ISBN knjige i preko njega nalazi i brise knjigu iz baze
    public static void ObrisiKnjigu(int ISBN) throws SQLException{
        Connection conn = MyConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("delete from knjige where ISBN=?");
        ps.setInt(1, ISBN);
        ps.execute();
    }
    //staticka metoda koja prima parametar ceo broj tj ISBN knjige i vraca objekat koji je nadjen preko ISBN-a koji mu je prosledjen
    public static Knjiga NadjiKnjigu(int ISBN) throws SQLException{
        Connection conn = MyConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from knjige where ISBN=?");
        ps.setInt(1, ISBN);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Knjiga k = new Knjiga();
        k.naziv = rs.getString("naziv");
        k.ISBN = rs.getInt("ISBN");
        k.stanje = rs.getInt("stanje");
        k.napomena = rs.getString("napomena");
        k.zanr = rs.getString("zanr");
        k.autor = rs.getString("autor");
        k.izdavac = rs.getString("izdavac");
        return k;
    }
    //staticka metoda koja prima objekat klase Knjiga i vrsi date izmene u bazi koje su nastale
    public static void IzmeniKnjigu(Knjiga k) throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("update knjige set naziv=?,napomena=?,stanje=?,zanr=?,autor=?,izdavac=? where ISBN=?");
    ps.setInt(7, k.getISBN());
    ps.setString(1, k.getNaziv());
    ps.setString(2, k.getNapomena());
    ps.setInt(3, k.getStanje());
    ps.setString(4,k.getZanr());
    ps.setString(5, k.getAutor());
    ps.setString(6, k.getIzdavac());
    ps.execute();
    }
    //metoda koja iz baze uzima sve informacije iz baze knjige i vraca listu koja sadrzi sve objekte koji su izvuceni u bazi
    public static ObservableList<Knjiga> PrikazBiblioteke()throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("select * from knjige");
    ResultSet rs = ps.executeQuery();
    ObservableList <Knjiga> lista = FXCollections.observableArrayList();
    while(rs.next()){
    Knjiga k = new Knjiga();
    k.ISBN = rs.getInt("isbn");
    k.naziv = rs.getString("naziv");
    k.stanje = rs.getInt("stanje");
    k.zanr = rs.getString("zanr");
    k.napomena = rs.getString("napomena");
    k.setAutor(rs.getString("autor"));
    k.setIzdavac(rs.getString("izdavac"));
    lista.add(k);
    }
    return lista;
            }


}
