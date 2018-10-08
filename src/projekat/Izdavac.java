package projekat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Izdavac {
    private int id;
    private String naziv;
    private String napomena;
    public Izdavac(){};
    Izdavac(String naziv,String napomena){
    this.naziv = naziv;
    this.napomena = napomena;
    }
    Izdavac(int id, String naziv,String napomena){
    this.id =id;
    this.naziv = naziv;
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
    @Override
    public String toString(){
    return this.naziv;
    }
    //staticka metoda koja prima objekat klase Izdavac i ubacuje ga u bazu
    public static int DodajIzdavaca(Izdavac i) throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("insert into izdavac(id, naziv,napomena) values (null,?,?)");
    ps.setString(1, i.getNaziv());
    ps.setString(2, i.getNapomena());
    ps.execute();
    PreparedStatement ps1 = conn.prepareStatement("select id from izdavac where naziv=?");
    ps1.setString(1, i.getNaziv());
    ResultSet rs = ps1.executeQuery();
    rs.next();
    int id1 = rs.getInt("id");
    return id1;
    }
    //staticka metoda koja prima objekat klase Izdavac i vrsi date izmene u bazi koje su nastale
    public static void IzmeniIzdavaca(int id,String ime,String napomena) throws SQLException{
      Connection conn = MyConnection.getConnection();
      PreparedStatement ps = conn.prepareStatement("update izdavac set naziv=? where id=?");
      ps.setString(1, ime);
      ps.setInt(2, id);
      ps.execute();
      PreparedStatement nps = conn.prepareStatement("update izdavac set napomena=? where id=?");
      nps.setString(1, napomena);
      nps.setInt(2, id);
      nps.execute();
    
    }
    //staticka metoda koja prima parametar ceo broj tj id izdavaca i preko njega nalazi i brise izdavaca iz baze
    public static void ObrisiIzdavaca(int id) throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("delete from izdavac where id=?");
    ps.setInt(1, id);
    ps.execute();
    
    }
    //staticka metoda koja prima parametar ceo broj tj id izdavaca i vraca objekat koji je nadjen preko id-a koji mu je prosledjen
    public static Izdavac NadjiIzdavaca(int id) throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("select * from izdavac where id=?");
    ps.setInt(1, id);
    ResultSet rs = ps.executeQuery();
    rs.next();
    Izdavac i = new Izdavac();
    i.setNaziv(rs.getString("naziv"));
    i.setNapomena(rs.getString("napomena"));
    return i;
    }
    //staticka metoda koja ne prima parametar i vraca ArrayList-u(listu) koja sadrzi samo stringove naziva izdavaca
    public static ArrayList<String> IzaberiIzdavaca() throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("select naziv from izdavac");
    ResultSet rs = ps.executeQuery();
    ArrayList <String> lista = new ArrayList();
    while(rs.next()){
        String naziv = rs.getString("naziv");
        lista.add(naziv);
                }
    return lista;
    }
}
