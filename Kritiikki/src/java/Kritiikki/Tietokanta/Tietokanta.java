package Kritiikki.Tietokanta;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Tietokanta {

    private static InitialContext cxt;
    private static DataSource yhteysVarasto;
    private static Tietokanta tietokantaYhteys;

    public Tietokanta() throws NamingException {

        //Haetaan context-xml-tiedostosta tietokannan yhteystiedot
        //HUOM! Tämä esimerkki ei toimi sellaisenaan ilman Tomcat-palvelinta!
        cxt = new InitialContext();
        yhteysVarasto = (DataSource) cxt.lookup("java:/comp/env/jdbc/tietokanta");
    }

    /**
     * Luo ensimmäisellä kerralla tietokantayhteyden ja muilla kerroilla
     * ainoastaan palauttaa sen.
     */
    public static Connection getYhteys() throws SQLException {
        if (tietokantaYhteys == null) {
            try {
                tietokantaYhteys = new Tietokanta();
            } catch (Exception ex) {
                Logger.getLogger(Tietokanta.class.getName()).log(Level.SEVERE, "Luotiin tietokantaolio", ex);
            }
        }
        return yhteysVarasto.getConnection();
    }
}
