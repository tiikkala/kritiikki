package Kritiikki.Mallit;


import Kritiikki.Tietokanta.Tietokanta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sisältää yleisesti SQL-kyselyissä tarvittavia toiminnallisuuksia.
 */

public class Kyselytoiminnot {
    protected Connection connection;
    protected PreparedStatement statement;
    protected ResultSet results;
    
    /**
     * Luo tietokantayhteyden ja "valmistelee" kyselyn "statement" käyttäen määriteltyä kyselyä.
     * @param sql = ennalta määritelty kysely
     */
    
    protected void alustaKysely(String sql) {
        connection = null;
        statement = null;
        results = null;
        
        connection = yhteys();
        statement = kysely(sql);
    }
    
    protected Connection yhteys() {
        try {
            return Tietokanta.getYhteys();
        } 
        catch (SQLException e) {
            Logger.getLogger(Kyselytoiminnot.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
    
    protected PreparedStatement kysely(String sql) {
        try {
            return connection.prepareStatement(sql);
        } 
        catch (SQLException e) {
            Logger.getLogger(Kyselytoiminnot.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
    
    /**
     * Suorittaa tietokantakyselyn ja asettaa tulokset "results":iin.
     */   
    protected void suoritaKysely() {
        try {
            results = statement.executeQuery();
        }   
        catch (SQLException ex) {
            Logger.getLogger(Kyselytoiminnot.class.getName()).log(Level.SEVERE, "Kyselyssä on virhe. Palauttaa null-arvon.", ex);
            results = null;
        }
    }
    
    /**
     * Kyselyiden lopetus tapahtuu sulkemalla kaikki tarpeellinen tätä kautta.
     * Virheet käsitellään näin, sillä Exception voi olla myös "NullPointerException", etenekin results:in tapauksessa.
     */   
    protected void lopeta() {
        try { results.close(); } catch (Exception e) {}
        try { statement.close(); } catch (Exception e) {}
        try { connection.close(); } catch (Exception e) {}
    }
}
