package Kritiikki.Mallit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kayttaja extends Kyselytoiminnot {

    private final Map<String, String> virheet = new HashMap();
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
        this.id = id.trim();
        if (id.trim().length() > 100) {
            virheet.put("tunnus", "Käyttäjätunnuksen maksimipituus on sata merkkiä.");
        } else if (id.isEmpty()) {
            virheet.put("tunnus", "Anna käyttäjätunnus.");
        } else {
            virheet.remove("tunnus");
        }
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana.trim();
        if (salasana.trim().length() > 50) {
            virheet.put("salasana", "Salasana ei saa olla yli 50 merkkiä pitkä.");
        } else if (salasana.trim().length() < 8) {
            virheet.put("salasana", "Salasanan täytyy olla vähintään 8 merkkiä pitkä.");
        } else if (salasana.trim().isEmpty()) {
            virheet.put("salasana", "Anna salasana.");
        } else {
            virheet.remove("salasana");
        }
    }

    public void setSposti(String sposti) {
        this.sposti = sposti.trim();
        if (sposti.trim().length() > 100) {
            virheet.put("sposti", "Sähköpostiosoite ei saa olla yli 100 merkkiä pitkä.");
        } else {
            virheet.remove("sposti");
        }
    }

    public void setRooli(String rooli) {
        this.rooli = rooli;
    }

    public Map<String, String> getVirheet() {
        return this.virheet;
    }

    /**
     * Tarkistaa syötteiden oikeellisuuden ja sen, että kellään muulla ole
     * valittua käyttäjätunnusta käytössä.
     */
    public boolean onkoKelvollinen() {
        if (this.virheet.isEmpty()) {
            if (!onkoTunnusVapaa(this.id)) {
                virheet.put("tunnus", "Tunnus on jo käytössä. Valitse toinen käyttäjätunnus.");
                return false;
            } else {
                virheet.remove("tunnus");
            }
        }
        return this.virheet.isEmpty();
    }

    /**
     * Luo seuraavana "results":ssa olevan käyttäjän.
     *
     * @return kayttaja
     */
    private Kayttaja palautaKayttaja() {
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

    public void lisaaKantaan() {
        try {
            String sql = "INSERT INTO kayttajat (id, salasana, sposti, rooli) VALUES("
                    + "?, ?, ?, 'kayttaja') RETURNING id";
            alustaKysely(sql);
            statement.setString(1, this.getId());
            statement.setString(2, this.getSalasana());
            statement.setString(3, this.getSposti());
            suoritaKysely();
        } catch (SQLException ex) {
            Logger.getLogger(Kirja.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lopeta();
        }
    }

    private boolean onkoTunnusVapaa(String tunnus) {
        if (etsiKayttajaTunnuksenPerusteella(tunnus) != null) {
            return false;
        }
        return true;
    }

    public void poistaKayttaja(String id) {
        try {
            String sql = "DELETE FROM kayttajat WHERE id = ? RETURNING id";
            alustaKysely(sql);
            statement.setString(1, id);
            suoritaKysely();
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        } finally {
            lopeta();
        }
    }

    public void muutaTietoja(String salasana, String sposti, String id) {
        try {
            String sql = "UPDATE kayttajat SET salasana = ?, sposti = ? WHERE id = ? RETURNING id";
            alustaKysely(sql);
            statement.setString(1, salasana);
            statement.setString(2, sposti);
            statement.setString(3, id);
            suoritaKysely();
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        } finally {
            lopeta();
        }
    }

    public Kayttaja etsiKayttajaTunnuksenPerusteella(String tunnus) {
        Kayttaja kayttaja = new Kayttaja();
        try {
            String sql = "SELECT * FROM kayttajat WHERE id = ? LIMIT 1";
            alustaKysely(sql);
            statement.setString(1, tunnus);
            suoritaKysely();
            if (results.next()) {
                return palautaKayttaja();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Kayttaja.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lopeta();
        }
        return null;
    }

    public Kayttaja etsiKayttajaTunnuksenJaSalasananPeruseella(String tunnus, String salasana) {
        try {
            String sql = "SELECT * FROM kayttajat WHERE id = ? AND salasana = ? LIMIT 1";
            alustaKysely(sql);
            statement.setString(1, tunnus);
            statement.setString(2, salasana);
            suoritaKysely();
            if (results.next()) {
                return palautaKayttaja();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Kayttaja.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lopeta();
        }
        return null;
    }

    public List<Kayttaja> getKayttajat() {
        ArrayList<Kayttaja> kayttajat = new ArrayList<Kayttaja>();
        try {
            String sql = "SELECT id, salasana, sposti FROM kayttajat";
            alustaKysely(sql);
            suoritaKysely();
            while (results.next()) {
                kayttajat.add(palautaKayttaja());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Kayttaja.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lopeta();
        }
        return kayttajat;
    }
}
