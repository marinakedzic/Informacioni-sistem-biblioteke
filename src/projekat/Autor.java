package projekat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Autor {
    private String ime; //polje klase Autor koja je tipa string i predstavlja ime autora
    private String sime;//polje klase Autor koja je tipa string i predstavlja srednje ime autora
    private String prezime;//polje klase Autor koja je tipa string i predstavlja prezime autora
    private String napomena;//polje klase Autor koja je tipa string i predstavlja napomenu vezanu za autora
    private int id;//polje klase Autor koja je tipa string i predstavlja id autora tj jedinstveni broj koji svaki objekat ove klase cini razlicit od ostalih

    
    @Override//anotaciju koja predstavlja obavestenja za kompajler da je ova metoda pregazena metoda roditeljske metode
    public String toString(){//metoda koja vraca string
    return this.ime + " " + this.sime + " " +this.prezime;//vratice string koji se sastoji od imena, srednjeg imena i prezimena datog aktuelnog objekta
    }
    
    /**
     * @return the ime
     */
    public String getIme() {//getter polja ime tj vraca string preko kojeg dobijamo vrednost polja ime datog objekta
        return ime;//vrednost ime koje se vraca kad se pozove metoda
    }

    /**
     * @param ime the ime to set
     */
    public void setIme(String ime) {//seter polja ime koja uzima preko parametra string
        this.ime = ime;//dobijen string kroz parametar postavlja kao naziv nekog objekta
    }

    /**
     * @return the sime
     */
    public String getSime() {
        return sime;
    }

    /**
     * @param sime the sime to set
     */
    public void setSime(String sime) {
        this.sime = sime;
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
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    public Autor(){} //prazan konstruktor
    Autor(String ime,String sime, String prezime,String napomena){ //konstruktor koji sadrzi sva polja osim id-a.
        this.ime = ime;
        this.prezime = prezime;
        this.sime = sime;
        this.napomena = napomena;
    }
    Autor(int id, String ime,String sime, String prezime,String napomena){ //konstruktor koja sadrzi sva polja
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.sime = sime;
        this.napomena = napomena;
    }
    public static int DodajAutora(Autor a) throws SQLException{ //staticka metoda koja prima objekat klase Autor i vraca ceo broj tj u ovom slucaju je to id objekta koji je ubacen u bazu
    Connection conn = MyConnection.getConnection(); // uzimanje konekcije
    PreparedStatement ps = conn.prepareStatement("insert into autor(id,ime,sime,prezime,napomena) values(null,?,?,?,?)"); //Pravljenje upita ka bazi kojim se ubacuju podaci u bazu autor
    ps.setString(1, a.getIme()); // u polje ime u bazi smestiti ime koje se uzima preko getera objekta klase Autor
    ps.setString(2, a.getSime());// u polje sime u bazi smestiti sime koje se uzima preko getera objekta klase Autor
    ps.setString(3, a.getPrezime());// u polje prezime u bazu smestiti prezime koje se uzima preko getera objekta klase Autor
    ps.setString(4, a.getNapomena());// u polje napomena u bazi smestiti napomenu koje se uzima preko getera objekta klase Autor
    ps.execute(); // izvrsavanje upita
    PreparedStatement ps1 = conn.prepareStatement("select id from autor where ime=?"); // Pravljenje upita ka bazi koji na osnovu datog imena izvaci dati id
    ps1.setString(1, a.getIme()); // prosledjujemo preko getera ime objekta koji smo ubacili u bazu
    ResultSet rs = ps1.executeQuery();
    rs.next();
    int id1 = rs.getInt("id"); //smestamo rezultat upita u id1 varijablu koja je ceo broj
    return id1;//vracamo ceo broj iliti id objekta koji smo ubacili u bazu
    }
    public static void IzmeniAutora(Autor a) throws SQLException{//staticka metoda koja prima objekat klase Autor i ne vraca nikakvu vrednost
        Connection conn = MyConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("update autor set ime=?,sime=?,prezime=?,napomena=? where id=?");//Pravljenje upita ka bazi kojim se menjaju podaci u bazi autor
        ps.setInt(5, a.getId());
        ps.setString(1, a.getIme());
        ps.setString(2, a.getSime());
        ps.setString(3, a.getPrezime());
        ps.setString(4, a.getNapomena());
        ps.execute();
    }
    
    public static void ObrisiAutora(int id) throws SQLException{//staticka metoda koja prima parametar ceo broj tj id objekta Autor i ne vraca nikakvu vrednost
        Connection conn= MyConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("delete from autor where id=?");//Pravljenje upita ka bazi kojim se brisu podaci iz baze autor
        ps.setInt(1, id);
        ps.execute();
        
    }
    public static Autor NadjiAutora(int id) throws SQLException{//staticka metoda koja prima parametar ceo broj tj id objekta Autor vraca objekat koji je nadjen preko id-a koji mu je prosledjen
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("select * from autor where id=?");//Pravljenje upita ka bazi kojim se dobijaju svi podaci iz baze autor vezani za prosledjeni id
    ps.setInt(1, id);
    ResultSet rs = ps.executeQuery();
    rs.next();
    Autor a = new Autor();//pravljenje objekta Autor
    a.setIme(rs.getString("ime"));//postavljanje vrednosti za polje ime napravljenog objekta preko rezultata dobijenih iz upita preko setera datog polja
    a.setSime(rs.getString("sime"));//postavljanje vrednosti za polje sime napravljenog objekta preko rezultata dobijenih iz upita preko setera datog polja
    a.setPrezime(rs.getString("prezime"));//postavljanje vrednosti za polje prezime napravljenog objekta preko rezultata dobijenih iz upita preko setera datog polja
    a.setNapomena(rs.getString("napomena"));//postavljanje vrednosti za polje napomenu napravljenog objekta preko rezultata dobijenih iz upita preko setera datog polja
    return a; //vracanje napravljeng objekta
    }
    public static ArrayList<String> IzaberiAutora() throws SQLException{//staticka metoda koja ne prima parametar i vraca ArrayList-u(listu) koja sadrzi samo stringove
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("select * from autor");//Pravljenje upita ka bazi kojim se dobijaju svi podaci iz baze autor
    ResultSet rs = ps.executeQuery();
    ArrayList <String> lista=new ArrayList();//pravljenje prazne liste koja moze sadrzati samo stringove
    while(rs.next()){//prolazak kroz sve rezultate dobijene iz upita
        Autor z = new Autor();//pravljenje objekta za svaki dobijen rezultat
        z.id = rs.getInt("id"); //drugi nacin razlicit od nacina kod nadji autora za postavljanje vrednosti za polje id napravljenog objekta preko rezultata dobijenih iz upita. Bez koriscenja setera datog polja.
        z.ime = rs.getString("ime");//drugi nacin razlicit od nacina kod nadji autora za postavljanje vrednosti za polje ime napravljenog objekta preko rezultata dobijenih iz upita. Bez koriscenja setera datog polja.
        z.sime = rs.getString("sime");//drugi nacin razlicit od nacina kod nadji autora za postavljanje vrednosti za polje sime napravljenog objekta preko rezultata dobijenih iz upita. Bez koriscenja setera datog polja.
        z.prezime = rs.getString("prezime");//drugi nacin razlicit od nacina kod nadji autora za postavljanje vrednosti za polje prezime napravljenog objekta preko rezultata dobijenih iz upita. Bez koriscenja setera datog polja.
        z.napomena = rs.getString("napomena");//drugi nacin razlicit od nacina kod nadji autora za postavljanje vrednosti za polje napomena napravljenog objekta preko rezultata dobijenih iz upita. Bez koriscenja setera datog polja.
        String naziv = z.toString(); //Pravimo novi string koji poziva override-ovan roditeljski metod toString datog objekta 
        lista.add(naziv); // smestamo novi string u napravljenu listu
                }
    return lista;//vracamo napravljenu listu.
    }
}
