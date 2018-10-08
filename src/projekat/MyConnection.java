
package projekat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    //Singleton patern
    private static Connection conn = null; //objekat klase Connection je na pocetku null
    private MyConnection(){} //prazan konstruktor 
    public static Connection getConnection() throws SQLException{ //srz ovog paterna je metoda koja stvara i vraca objekat tipa Connection
        if(conn==null){//proverava da li konekcija ne postoji, ako ne posotji pravi se nova
            conn= (Connection) DriverManager.getConnection("jdbc:mysql://localhost/biblioteka", "root",""); //konekcioni string
        }
        return conn; //ako postoji vraca posotojecu ili onu koja je upravo napravljena
    }
}
