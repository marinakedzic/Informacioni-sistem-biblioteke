
package projekat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Zanr {
    private String naziv;
    private int id;
    public Zanr(){};
    public Zanr(int id, String naziv){
    this.id = id;
    this.naziv = naziv;
    }
    public Zanr(String naziv){
    this.naziv=naziv;
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
    @Override
    public String toString(){
    return this.naziv;
    }
    //staticka metoda koja prima objekat klase Zanr i ubacuje ga u bazu
    public static int DodajZanr(Zanr z) throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("insert into zanr (id,naziv) values (null,?)");
    ps.setString(1, z.getNaziv());
    ps.execute();
    PreparedStatement ps1 = conn.prepareStatement("select id from zanr where naziv=?");
    ps1.setString(1, z.getNaziv());
    ResultSet rs = ps1.executeQuery();
    rs.next();
    int id1 = rs.getInt("id");
    return id1;
    }
    //staticka metoda koja prima parametar ceo broj tj id zanra i vraca objekat koji je nadjen preko id-a koji mu je prosledjen
    public static String NadjiZanr(int id) throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("select naziv from zanr where id=?");
    ps.setInt(1, id);
    ResultSet rs = ps.executeQuery();
    rs.next();
    String naziv = rs.getString("naziv");
    return naziv;
    }
    //staticka metoda koja prima parametar ceo broj tj id zanra i preko njega nalazi i brise zanr iz baze
    public static void ObrisiZanr(int id) throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("delete from zanr where id=?");
    ps.setInt(1, id);
    ps.execute();
    }
    //staticka metoda koja prima objekat klase Zanr i vrsi date izmene u bazi koje su nastale
    public static void IzmeniZanr(int id,String naziv) throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("update zanr set naziv=? where id=?");
    ps.setString(1, naziv);
    ps.setInt(2, id);
    ps.execute();
    }
    //staticka metoda koja ne prima parametar i vraca ArrayList-u(listu) koja sadrzi samo stringove naziva zanrova
    public static ArrayList<String> IzaberiZanr() throws SQLException{
    Connection conn = MyConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement("select naziv from zanr");
    ResultSet rs = ps.executeQuery();
    ArrayList <String> lista = new ArrayList();
    while(rs.next()){
        String naziv = rs.getString("naziv");
        lista.add(naziv);
                }
    return lista;
    }
}
