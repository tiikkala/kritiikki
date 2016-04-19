package Kritiikki.Mallit;

import Kritiikki.Tietokanta.Tietokanta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 * Malli, joka sisältää tietoa kirjoista ja tekee niiden hakuun liittyviä
 * SQL-kyselyjä.
 */
public class Kirja extends Kyselytoiminnot {

    private int id;
    private String nimi;
    private String kirjailija;
    private int julkaisuvuosi;
    private String julkaisukieli;
    private String suomentaja;
    private Double pisteet;

    public int getId() {
        return this.id;
    }

    public String getNimi() {
        return this.nimi;
    }

    public String getKirjailija() {
        return this.kirjailija;
    }

    public int getJulkaisuvuosi() {
        return this.julkaisuvuosi;
    }

    public String getJulkaisukieli() {
        return this.julkaisukieli;
    }

    public String getSuomentaja() {
        return this.suomentaja;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setKirjailja(String kirjailija) {
        this.kirjailija = kirjailija;
    }

    public void setJulkaisuKieli(String kieli) {
        this.julkaisukieli = kieli;
    }

    public void setJulkaisuvuosi(int vuosi) {
        this.julkaisuvuosi = vuosi;
    }

    public void setSuomentaja(String suomentaja) {
        this.suomentaja = suomentaja;
    }

    public void setPisteet(double pisteet) {
        this.pisteet = pisteet;
    }

    public double getPisteet() {
        return this.pisteet;
    }

    /**
     * Kertoo, kuinka monta riviä taulussa on.
     */
    public int lukumaara() {
        int lkm = 0;
        try {
            String sql = "SELECT count(*) as lkm FROM kirjat";
            alustaKysely(sql);
            suoritaKysely();
            // Tuloksia on aina yksi, mikäli kirjat-taulu on olemassa.
            // Kelataan ensimmäiseen tulokseen:
            results.next();
            lkm = results.getInt("lkm");
        } catch (SQLException ex) {
            Logger.getLogger(Kirja.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lopeta();
        }
        return lkm;
    }

    /**
     * Luo seuraavana "results":ssa olevan Kirjan pisteineen.
     */
    public Kirja palautaKirjaJaPisteet() {
        Kirja k = new Kirja();
        try {
            k.setId(results.getInt("id"));
            k.setNimi(results.getString("nimi"));
            k.setKirjailja(results.getString("kirjailija"));
            k.setJulkaisuvuosi(results.getInt("julkaisuvuosi"));
            k.setJulkaisuKieli(results.getString("julkaisukieli"));
            k.setSuomentaja(results.getString("suomentaja"));
            k.setPisteet(results.getDouble("pisteet"));
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        }
        return k;
    }

    public Kirja haeKirjaJaPisteet(int id) {
        Kirja kirja = new Kirja();
        try {
            String sql = "SELECT id, nimi, kirjailija, julkaisuvuosi, julkaisukieli, "
                    + "suomentaja, pisteet.pisteet FROM kirjat LEFT JOIN (SELECT kirjaId, AVG(pisteet) "
                    + "AS pisteet FROM pisteet GROUP BY kirjaId) AS pisteet ON "
                    + "kirjat.id = pisteet.kirjaId WHERE kirjat.id = ? GROUP BY "
                    + "kirjat.id, kirjat.nimi, kirjat.kirjailija, kirjat.julkaisuvuosi, "
                    + "kirjat.julkaisukieli, kirjat.suomentaja, pisteet.kirjaId, "
                    + "pisteet.pisteet ORDER BY pisteet.pisteet";
            alustaKysely(sql);
            statement.setInt(1, id);
            suoritaKysely();
            kirja = palautaKirjaJaPisteet();
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        } finally {
            lopeta();
        }
        return kirja;
    }

    /**
     * Hakee tietokannasta kirjat ja laskee jokaiselle kirjalle sen saamien
     * pisteiden keskiarvot.
     *
     * @param montako = kuinka monta kirjaa haetaan, sivu = mistä kohtaa kantaa
     * haku aloitetaan
     *
     * @return palauttaa kokoelman tietokannan kirjoista ja niihin liittyvistä
     * pisteistä
     */
    public List<Kirja> haeKirjatJaPisteet() {
        List<Kirja> kirjat = new ArrayList<Kirja>();
        try {
            // TO DO: voisko tätä siistiä?
            String sql = "SELECT id, nimi, kirjailija, julkaisuvuosi, julkaisukieli, "
                    + "suomentaja, pisteet.pisteet FROM kirjat LEFT OUTER JOIN (SELECT kirjaId, AVG(pisteet) "
                    + "AS pisteet FROM pisteet GROUP BY kirjaId) AS pisteet ON kirjat.id = pisteet.kirjaId "
                    + "GROUP BY kirjat.id, kirjat.nimi, kirjat.kirjailija, kirjat.julkaisuvuosi, "
                    + "kirjat.julkaisukieli, kirjat.suomentaja, pisteet.kirjaId, "
                    + "pisteet.pisteet ORDER BY pisteet.pisteet";
            alustaKysely(sql);
//            statement.setInt(1, montako); // nämä sivutusta varten, toteuta aikanaan
//            statement.setInt(2, (sivu - 1) * montako);
            suoritaKysely();
            while (results.next()) {
                // luodaan joka riviä vastaava kirja
                kirjat.add(palautaKirjaJaPisteet());
            }
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        } finally {
            lopeta();
        }
        return kirjat;
    }
}
