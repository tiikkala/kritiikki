package Kritiikki.Mallit;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tomcat.util.buf.TimeStamp;

/**
 * Malli, joka sisältää tietoa kirjoista ja tekee niiden hakuun liittyviä
 * SQL-kyselyjä.
 */
public class Kommentti extends Kyselytoiminnot {

    private final Map<String, String> virheet = new HashMap<String, String>();
    private String teksti;
    private String kirjoittaja;
    private int id;
    private Timestamp paivays;
    private int kritiikkiId;

    public String getTeksti() {
        return this.teksti;
    }

    public String getKirjoittaja() {
        return this.kirjoittaja;
    }

    public int getId() {
        return this.id;
    }

    public int getKritiikkiId() {
        return this.kritiikkiId;
    }

    public Timestamp getPaivays() {
        return this.paivays;
    }

    public void setPaivays(Timestamp paivays) {
        this.paivays = paivays;
    }

    public void setTeksti(String teksti) {
        this.teksti = teksti.trim();
        if (teksti.trim().length() > 10000) {
            virheet.put("teksti", "Kommentin maksimipituus on 10 000 merkkiä.");
        } else {
            virheet.remove("teksti");
        }
    }

    public void setKritiikkiId(int id) {
        this.kritiikkiId = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKirjoittaja(String kirjoittaja) {
        this.kirjoittaja = kirjoittaja;
    }

    public boolean onkoKelvollinen() {
        return this.virheet.isEmpty();
    }

    public Map<String, String> getVirheet() {
        return this.virheet;
    }

    private Kommentti palautaKommentti() {
        Kommentti k = new Kommentti();
        try {
            k.setId(results.getInt("id"));
            k.setKirjoittaja(results.getString("kirjoittaja"));
            k.setKritiikkiId(results.getInt("kritiikkiId"));
            k.setPaivays(results.getTimestamp("paivays"));
            k.setTeksti(results.getString("teksti"));
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        }
        return k;
    }

    public Kommentti haeKommentti(int id) {
        Kommentti k = new Kommentti();
        try {
            String sql = "SELECT * FROM kommentit WHERE id = ?";
            alustaKysely(sql);
            statement.setInt(1, id);
            suoritaKysely();
            if (results.next()) {
                k = palautaKommentti();
            }
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        }
        return k;
    }

    public void muokkaaKommenttia(int id, String teksti) {
        try {
            String sql = "UPDATE kommentit SET teksti = ? WHERE id = ?";
            alustaKysely(sql);
            statement.setString(1, teksti);
            statement.setInt(2, id);
            suoritaKysely();
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        } finally {
            lopeta();
        }
    }

    public void poistaKommentti(int id) {
        try {
            String sql = "DELETE FROM kommentit WHERE id = ? RETURNING id";
            alustaKysely(sql);
            statement.setInt(1, id);
            suoritaKysely();
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        } finally {
            lopeta();
        }
    }

    public List<Kommentti> haeKritiikkiinLittyvatKommentit(int kritiikkiId) {
        List<Kommentti> kommentit = new ArrayList<Kommentti>();
        try {
            String sql = "SELECT * FROM kommentit WHERE kritiikkiId = ? ORDER BY paivays";
            alustaKysely(sql);
            statement.setInt(1, kritiikkiId);
            suoritaKysely();
            while (results.next()) {
                kommentit.add(palautaKommentti());
            }
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);

        } finally {
            lopeta();
        }
        return kommentit;
    }

    public void lisaaKantaan() {
        try {
            String sql = "INSERT INTO kommentit(kritiikkiId, kirjoittaja, teksti, paivays)"
                    + " VALUES(?, ?, ?, CURRENT_TIMESTAMP(2)) RETURNING id";
            alustaKysely(sql);
            statement.setInt(1, this.getKritiikkiId());
            statement.setString(2, this.getKirjoittaja());
            statement.setString(3, this.getTeksti());
            suoritaKysely();
            results.next();
            this.id = results.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(Kirja.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lopeta();
        }
    }

    public List<Kommentti> haeKayttajanKirjoittamatKommentit(String tunnus) {
        List<Kommentti> kommentit = new ArrayList<Kommentti>();
        try {
            String sql = "SELECT * FROM kommentit WHERE kirjoittaja = ? ORDER BY paivays";
            alustaKysely(sql);
            statement.setString(1, tunnus);
            suoritaKysely();
            while (results.next()) {
                kommentit.add(palautaKommentti());
            }
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        } finally {
            lopeta();
        }
        return kommentit;
    }
}
