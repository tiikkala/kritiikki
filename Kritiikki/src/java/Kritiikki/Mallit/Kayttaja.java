package Kritiikki.Mallit;

import Kritiikki.Tietokanta.Tietokanta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kayttaja extends Kyselytoiminnot {

    private String id;
    private String salasana;
    private String sposti;
    private String rooli;

    public String getId() {
        return this.id;
    }

    public String getSalasana() {
        return this.salasana;
    }

    public String getSposti() {
        return this.sposti;
    }

    public String getRooli() {
        return this.rooli;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }

    public void setSposti(String sposti) {
        this.sposti = sposti;
    }

    public void setRooli(String rooli) {
        this.rooli = rooli;
    }

    /**
     * Luo seuraavana "results":ssa olevan käyttäjän.
     *
     * @return kayttaja
     */
    public Kayttaja palautaKayttaja() {
        Kayttaja kayttaja = new Kayttaja();
        try {
            kayttaja.setId(results.getString("id"));
            kayttaja.setSalasana(results.getString("salasana"));
            kayttaja.setSposti(results.getString("sposti"));
            kayttaja.setRooli(results.getString("rooli"));
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        }
        return kayttaja;
    }

    public Kayttaja etsiKayttaja(String tunnus, String salasana) {
        Kayttaja kayttaja = new Kayttaja();
        try {
            String sql = "SELECT * FROM kayttajat WHERE id = ? AND salasana = ? LIMIT 1";
            alustaKysely(sql);
            statement.setString(1, tunnus);
            statement.setString(2, salasana);
            suoritaKysely();
            if (results.next()) {
                kayttaja = palautaKayttaja();
                return kayttaja;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Kayttaja.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lopeta();
        }
        // Jos käyttäjää ei löydy, palautetaan null, jotta voidaan tarkistaa onko kannassa
        // kirjautumisessa annettuja tietoja vastaava käyttäjä.
        return null;
    }

    public static List<Kayttaja> getKayttajat() throws SQLException {

        String sql = "SELECT id, salasana, sposti FROM kayttajat";
        Connection yhteys = Tietokanta.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Kayttaja> kayttajat = new ArrayList<Kayttaja>();
        while (tulokset.next()) {
            //Luodaan tuloksia vastaava olio ja palautetaan olio:
            Kayttaja k = new Kayttaja();
            k.setId(tulokset.getString("id"));
            k.setSalasana(tulokset.getString("salasana"));
            k.setSposti(tulokset.getString("sposti"));
            kayttajat.add(k);
        }
        //Suljetaan kaikki resutuloksetsid:
        try {
            tulokset.close();
        } catch (Exception e) {
        }
        try {
            kysely.close();
        } catch (Exception e) {
        }
        try {
            yhteys.close();
        } catch (Exception e) {
        }
        return kayttajat;
    }
}
