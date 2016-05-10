package Kritiikki.Mallit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Malli, joka sisältää tietoa kirjoista ja tekee niiden hakuun liittyviä
 * SQL-kyselyjä.
 */
public class Kirja extends Kyselytoiminnot {

    private final Map<String, String> virheet = new HashMap<String, String>();
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
            virheet.put("nimi", "Lisäys epäonnistui. Nimi ei saa olla tyhjä.");
        } else {
            virheet.remove("nimi");
        }
        if (nimi.trim().length() > 200) {
            virheet.put("nimi", "Lisäys epäonnistui. Nimi " + enintaanKaksisataa());
        } else {
            virheet.remove("nimi");
        }
    }

    public void setKirjailja(String kirjailija) {
        this.kirjailija = kirjailija.trim();
        if (kirjailija.trim().length() > 200) {
            virheet.put("kirjailija", "Lisäys epäonnistui. Kirjailijan nimi " + enintaanKaksisataa());
        } else if (kirjailija.trim().length() == 0) {
            virheet.put(kirjailija, "Lisäys epäonnistui. Lisää kirjalle kirjoittaja.");
        } else {
            virheet.remove("kirjailija");
        }
    }

    public void setJulkaisukieli(String kieli) {
        this.julkaisukieli = kieli.trim();
        if (kieli.trim().length() > 200) {
            virheet.put("kieli", "Lisäys epäonnistui. Julkaisukieli " + enintaanKaksisataa());
        } else {
            virheet.remove("kieli");
        }
    }

    public void setJulkaisuvuosi(String vuosi) {
        try {
            if (!vuosi.isEmpty()) {
                this.julkaisuvuosi = Integer.parseInt(vuosi);
            }
            virheet.remove("vuosi");
        } catch (NumberFormatException e) {
            virheet.put("vuosi", "Lisäys epäonnistui. Julkaisuvuoden on oltava kokonaisluku.");
        }
    }

    public void setSuomentaja(String suomentaja) {
        this.suomentaja = suomentaja.trim();
        if (suomentaja.trim().length() > 200) {
            virheet.put("suomentaja", "Lisäys epäonnistui. Suomentajan nimi " + enintaanKaksisataa());
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
     * @return rivien lkm
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
    private Kirja palautaKirjaJaPisteet() {
        Kirja k = new Kirja();
        try {
            k.setId(results.getInt("id"));
            k.setNimi(results.getString("nimi"));
            k.setKirjailja(results.getString("kirjailija"));
            k.setJulkaisuvuosi(Integer.toString(results.getInt("julkaisuvuosi")));
            k.setJulkaisukieli(results.getString("julkaisukieli"));
            k.setSuomentaja(results.getString("suomentaja"));
            k.setPisteet(results.getDouble("pisteet"));
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        }
        return k;
    }

    public List<Kirja> haeKayttajanKritikoimatKirjat(String kayttaja) {
        List<Kirja> kirjat = new ArrayList<Kirja>();
        try {
            String sql = "SELECT * FROM kirjat JOIN kritiikit ON kritiikit.kirjaId = kirjat.id WHERE "
                    + "kritiikit.kirjoittaja = ?";
            alustaKysely(sql);
            statement.setString(1, kayttaja);
            suoritaKysely();
            while (results.next()) {
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

    public List<Kirja> haeKayttajanArvostelematKirjat(String kayttaja) {
        List<Kirja> kirjat = new ArrayList<Kirja>();
        try {
            String sql = "SELECT * FROM pisteet JOIN kirjat ON pisteet.kirjaId = kirjat.id WHERE "
                    + "pisteet.kayttaja = ? ORDER BY pisteet.pisteet DESC";
            alustaKysely(sql);
            statement.setString(1, kayttaja);
            suoritaKysely();
            while (results.next()) {
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

    public void muokkaaKirjanTietoja(int id, String nimi, String kirjailija, int julkaisuvuosi,
            String julkaisukieli, String suomentaja) {
        try {
            String sql = "UPDATE kirjat SET nimi = ?, kirjailija = ?, julkaisuvuosi = ?,"
                    + "julkaisukieli = ?, suomentaja = ? WHERE id = ?";
            alustaKysely(sql);
            statement.setInt(6, id);
            statement.setString(1, nimi);
            statement.setString(2, kirjailija);
            statement.setInt(3, julkaisuvuosi);
            statement.setString(4, julkaisukieli);
            statement.setString(5, suomentaja);
            suoritaKysely();
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        } finally {
            lopeta();
        }
    }

    public void poistaKirja(int id) {
        try {
            String sql = "DELETE FROM kirjat WHERE id = ? RETURNING id";
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

    /**
     * Hakee kannasa kirjan id:n perusteella.
     * @param id = kirjan in
     * @return id:ta vastaava kirja 
     */
    public Kirja haeKirjaJaPisteet(int id) {
        Kirja kirja = new Kirja();
        try {
            String sql = "SELECT id, nimi, kirjailija, julkaisuvuosi, julkaisukieli, "
                    + "suomentaja, pisteet.pisteet FROM kirjat LEFT JOIN (SELECT kirjaId, AVG(pisteet) "
                    + "AS pisteet FROM pisteet GROUP BY kirjaId) AS pisteet ON "
                    + "kirjat.id = pisteet.kirjaId WHERE kirjat.id = ? GROUP BY "
                    + "kirjat.id, kirjat.nimi, kirjat.kirjailija, kirjat.julkaisuvuosi, "
                    + "kirjat.julkaisukieli, kirjat.suomentaja, pisteet.kirjaId, "
                    + "pisteet.pisteet";
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
     * pisteiden keskiarvot. Kysely järjestää kirjat niiden pistemäärän mukaan
     * laskevassa järjestyksessä.
     *
     * @return palauttaa listan tietokannan kirjoista ja niihin liittyvistä
     * pisteistä
     */
    public List<Kirja> haeKirjatJaPisteet() {
        List<Kirja> kirjat = new ArrayList<Kirja>();
        try {
            String sql = "SELECT id, nimi, kirjailija, julkaisuvuosi, julkaisukieli, "
                    + "suomentaja, pisteet.pisteet FROM kirjat LEFT OUTER JOIN (SELECT kirjaId, AVG(pisteet) "
                    + "AS pisteet FROM pisteet GROUP BY kirjaId) AS pisteet ON kirjat.id = pisteet.kirjaId "
                    + "GROUP BY kirjat.id, kirjat.nimi, kirjat.kirjailija, kirjat.julkaisuvuosi, "
                    + "kirjat.julkaisukieli, kirjat.suomentaja, pisteet.kirjaId, "
                    + "pisteet.pisteet ORDER BY pisteet.pisteet DESC NULLS LAST";
            alustaKysely(sql);
            suoritaKysely();
            while (results.next()) {
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

    public List<Kirja> haeKirjatJaJarjestaJulkaisuvuodenPeursteella() {
        List<Kirja> kirjat = new ArrayList<Kirja>();
        try {
            String sql = "SELECT id, nimi, kirjailija, julkaisuvuosi, julkaisukieli, "
                    + "suomentaja, pisteet.pisteet FROM kirjat LEFT OUTER JOIN (SELECT kirjaId, AVG(pisteet) "
                    + "AS pisteet FROM pisteet GROUP BY kirjaId) AS pisteet ON kirjat.id = pisteet.kirjaId "
                    + "GROUP BY kirjat.id, kirjat.nimi, kirjat.kirjailija, kirjat.julkaisuvuosi, "
                    + "kirjat.julkaisukieli, kirjat.suomentaja, pisteet.kirjaId, "
                    + "pisteet.pisteet ORDER BY kirjat.julkaisuvuosi DESC NULLS LAST";
            alustaKysely(sql);
            suoritaKysely();
            while (results.next()) {
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

    public List<Kirja> haeKirjatJaJarjestaParametrinaAnnetunAttribuutinPerusteella(String attribuutti) {
        List<Kirja> kirjat = new ArrayList<Kirja>();
        try {
            String sql = "SELECT id, nimi, kirjailija, julkaisuvuosi, julkaisukieli, "
                    + "suomentaja, pisteet.pisteet FROM kirjat LEFT OUTER JOIN (SELECT kirjaId, AVG(pisteet) "
                    + "AS pisteet FROM pisteet GROUP BY kirjaId) AS pisteet ON kirjat.id = pisteet.kirjaId "
                    + "GROUP BY kirjat.id, kirjat.nimi, kirjat.kirjailija, kirjat.julkaisuvuosi, "
                    + "kirjat.julkaisukieli, kirjat.suomentaja, pisteet.kirjaId, "
                    + "pisteet.pisteet ORDER BY CASE kirjat." + attribuutti
                    + " WHEN '' THEN 1 ELSE 0 END, kirjat." + attribuutti;
            alustaKysely(sql);
            suoritaKysely();
            while (results.next()) {
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

    public List<Kirja> haeKirjatHakusanalla(String hakusana) {
        List<Kirja> kirjat = new ArrayList<Kirja>();
        try {
            String sql = "SELECT id, nimi, kirjailija, julkaisuvuosi, "
                    + "julkaisukieli, suomentaja, pisteet.pisteet FROM kirjat "
                    + "LEFT OUTER JOIN (SELECT kirjaId, AVG(pisteet) AS pisteet FROM "
                    + "pisteet GROUP BY kirjaId) AS pisteet ON kirjat.id = pisteet.kirjaId "
                    + "WHERE kirjat.nimi ~* '" + hakusana + "' OR kirjat.kirjailija ~* '"
                    + hakusana + "' GROUP BY kirjat.id, kirjat.nimi, kirjat.kirjailija, "
                    + "kirjat.julkaisuvuosi, kirjat.julkaisukieli, kirjat.suomentaja, "
                    + "pisteet.kirjaId, pisteet.pisteet ORDER BY pisteet.pisteet DESC NULLS LAST;";
            alustaKysely(sql);
            suoritaKysely();
            while (results.next()) {
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
