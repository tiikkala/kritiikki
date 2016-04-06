package Tietokanta;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Yhteys {

    private final InitialContext cxt;
    private static DataSource yhteysVarasto;

    public Yhteys() throws NamingException {

        //Haetaan context-xml-tiedostosta tietokannan yhteystiedot
        //HUOM! Tämä esimerkki ei toimi sellaisenaan ilman Tomcat-palvelinta!
        cxt = new InitialContext();
        yhteysVarasto = (DataSource) cxt.lookup("java:/comp/env/jdbc/tietokanta");
    }

    public static Connection getYhteys() throws SQLException {
        return yhteysVarasto.getConnection();
    }
}
