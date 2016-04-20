package Kritiikki.Mallit;

import Kritiikki.Tietokanta.Tietokanta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 * Malli, joka sisältää tietoa kirjoista ja tekee niiden hakuun liittyviä
 * SQL-kyselyjä.
 */
public class Kirja extends Kyselytoiminnot {

    private Map<String, String> virheet = new HashMap<String, String>();

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
    
    public boolean onkoKelvollinen() {
        return this.virheet.isEmpty();
    }
    
    public Collection<String> getVirheet() {
        return this.virheet.values();
    }

    public void setNimi(String nimi) {
        this.nimi = nimi.trim();
        if (nimi.trim().length() == 0) {
            virheet.put("nimi", "Nimi ei saa olla tyhjä.");
        } else {
            virheet.remove("nimi");
        }
        if (nimi.trim().length() >= 200) {
            virheet.put("nimi", "Nimi " + enintaanKaksisataa());
        } else {
            virheet.remove("nimi");
        }
    }

    public void setKirjailja(String kirjailija) {
        this.kirjailija = kirjailija.trim();
        if (kirjailija.trim().length() >= 200) {
            virheet.put("kirjailija", "Kirjailijan nimi " + enintaanKaksisataa());
        } else {
            virheet.remove("kirjailija");
        }
    }

    public void setJulkaisuKieli(String kieli) {
        this.julkaisukieli = kieli.trim();
        if (kieli.trim().length() >= 200) {
            virheet.put("kieli", "Julkaisukieli " + enintaanKaksisataa());
        } else {
            virheet.remove("kieli");
        }
    }

    public void setJulkaisuvuosi(String vuosi) {
        try {
        this.julkaisuvuosi = Integer.parseInt(vuosi);
        virheet.remove("vuosi");
        } catch (NumberFormatException e) {
            virheet.put("vuosi", "Julkaisuvuoden on oltava kokonaisluku.");
        }
    }

    public void setSuomentaja(String suomentaja) {
        this.suomentaja = suomentaja.trim();
        if (suomentaja.trim().length() >= 200) {
            virheet.put("suomentaja", "Suomentajan nimi " + enintaanKaksisataa());
        } else {
            virheet.remove("suomentaja");
        }
    }

    public void setPisteet(double pisteet) {
        this.pisteet = pisteet;
    }

    public double getPisteet() {
        if (this.pisteet == null) {
            return 0;
        } else {
            return this.pisteet;
        }
    }

    private String enintaanKaksisataa() {
        return "saa olla enintään 200 merkkiä pitkä.";
    }

    public void lisaaKantaan() {
        try {
            String sql = "INSERT INTO kirjat(nimi, kirjailija, julkaisuvuosi, "
                    + "julkaisukieli, suomentaja) VALUES(?, ?, ?, ?, ?) RETURNING id";
            alustaKysely(sql);
            statement.setString(1, this.getNimi());
            statement.setString(2, this.getKirjailija());
            statement.setInt(3, this.getJulkaisuvuosi());
            statement.setString(4, this.getJulkaisukieli());
            statement.setString(5, this.getSuomentaja());
            suoritaKysely();
            results.next();
            this.id = results.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(Kirja.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lopeta();
        }
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
            k.setJulkaisuvuosi(Integer.toString(results.getInt("julkaisuvuosi")));
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
            while (results.next()) {
                kirja = palautaKirjaJaPisteet();
            }
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
