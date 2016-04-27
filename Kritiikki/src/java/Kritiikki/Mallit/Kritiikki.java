package Kritiikki.Mallit;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Malli, joka sisältää tietoa kritiikeistä ja tekee niiden hakuun liittyviä
 * SQL-kyselyjä.
 */
public class Kritiikki extends Kyselytoiminnot {

    private final Map<String, String> virheet = new HashMap<String, String>();

    private int id;
    private int kirjaId;
    private String kirjoittaja;
    private String teksti;
    private String otsikko;
    private Date paivays;

    public int getId() {
        return this.id;
    }

    public int getKirjaId() {
        return this.kirjaId;
    }

    public String getKirjoittaja() {
        return this.kirjoittaja;
    }

    public String getTeksti() {
        return this.teksti;
    }

    public String getOtsikko() {
        return this.otsikko;
    }

    public Date getPaivays() {
        return this.paivays;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKirjaId(int id) {
        this.kirjaId = id;
    }

    public void setPaivays(Date paivays) {
        this.paivays = paivays;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko.trim();
        if (otsikko.trim().length() == 0) {
            virheet.put("otsikko", "Anna kritiikkillesi otsikko.");
        } else {
            virheet.remove("otsikko");
        }
        if (otsikko.trim().length() > 300) {
            virheet.put("otsikko", "Otsikko saa olla enintään 300 merkkiä pitkä.");
        } else {
            virheet.remove("otsikko");
        }
    }

    public void setTeksti(String teksti) {
        this.teksti = teksti;
        if (teksti.trim().length() == 0) {
            virheet.put("teksti", "Et kirjoittanut vielä mitään!");
        } else {
            virheet.remove("teksti");
        }
        if (teksti.trim().length() > 100000) {
            virheet.put("teksti", "Teksti saa olla enintään 100 000 merkkiä pitkä.");
        } else {
            virheet.remove("teksti");
        }
    }
    
    public void setKirjoittaja(String kirjoittaja) {
        this.kirjoittaja = kirjoittaja;
    }

    /**
     * Luo seuraavana "results":ssa olevan Kritiikin.
     */
    private Kritiikki palautaKritiikki() {
        Kritiikki k = new Kritiikki();
        try {
            k.setId(results.getInt("id"));
            k.setKirjaId(results.getInt("kirjaId"));
            k.setOtsikko(results.getString("otsikko"));
            k.setPaivays(results.getDate("paivays"));
            k.setTeksti(results.getString("teksti"));
            k.setKirjoittaja(results.getString("kirjoittaja"));
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        }
        return k;
    }

    public List<Kritiikki> haeKritiikitKirjaIdPerusteella(int kId) {
        List<Kritiikki> kritiikit = new ArrayList<Kritiikki>();
        try {
            String sql = "SELECT * FROM kritiikit WHERE kirjaId = ? ORDER BY paivays";
            alustaKysely(sql);
            statement.setInt(1, kId);
            suoritaKysely();
            while (results.next()) {
                kritiikit.add(palautaKritiikki());
            }
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        } finally {
            lopeta();
        }
        return kritiikit;
    }

    public void lisaaKantaan() {
        try {
            String sql = "INSERT INTO kritiikit(kirjaId, kirjoittaja, teksti, otsikko, paivays)"
                    + " VALUES(?, ?, ?, ?, CURRENT_DATE) RETURNING id";
            alustaKysely(sql);
            statement.setInt(1, this.getKirjaId());
            statement.setString(2, this.getKirjoittaja());
            statement.setString(3, this.getTeksti());
            statement.setString(4, this.getOtsikko());
            suoritaKysely();
            results.next();
            this.id = results.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(Kirja.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lopeta();
        }
    }
}
