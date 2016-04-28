package Kritiikki.Mallit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Malli, joka sisältää tietoa kirjoille annetuista pisteistä ja tekee niihin
 * liittyviä SQl-kyselyjä.
 */
public class Pisteet extends Kyselytoiminnot {

    private final Map<String, String> virheet = new HashMap<String, String>();

    private int id;
    private int pisteet;
    private int kirjaId;
    private String kayttaja;

    public void setId(int id) {
        this.id = id;
    }

    public void setPisteet(int pisteet) {
        this.pisteet = pisteet;
        if (this.pisteet < 4 || this.pisteet > 10) {
            virheet.put("pisteet", "Pisteiden on oltava luku välillä 4-10.");
        } else {
            virheet.remove("pisteet");
        }
    }

    public void setKirjaId(int id) {
        this.kirjaId = id;
    }

    public void setKayttaja(String tunnus) {
        this.kayttaja = tunnus;
    }

    public int getId() {
        return this.id;
    }

    public int getPisteet() {
        return this.pisteet;
    }

    public int getKirjaId() {
        return this.kirjaId;
    }

    public String getKayttaja() {
        return this.kayttaja;
    }

    public Pisteet palautaPisteet() {
        Pisteet p = new Pisteet();
        try {
            p.setId(results.getInt("id"));
            p.setKayttaja(results.getString("kayttaja"));
            p.setKirjaId(results.getInt("kirjaId"));
            p.setPisteet(results.getInt("pisteet"));
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        }
        return p;
    }
    
    public boolean onkoKelvollinen() {
        return this.virheet.isEmpty();
    }

    public boolean kayttajaOnJoArvostellut(String kayttaja, int kirjaId) {
        List<Pisteet> pisteet = haePisteetRiveittain(kirjaId);
        for (Pisteet p : pisteet) {
            if (p.getKayttaja().equals(kayttaja)) {
                return true;
            }
        }
        return false;
    }
    

    public List<Pisteet> haePisteetRiveittain(int kirjaId) {
        List<Pisteet> pisteet = new ArrayList<Pisteet>();
        try {
            Pisteet p = new Pisteet();
            String sql = "SELECT * FROM pisteet WHERE kirjaId = ?";
            alustaKysely(sql);
            statement.setInt(1, kirjaId);
            suoritaKysely();
            while (results.next()) {
                p = palautaPisteet();
                pisteet.add(p);
            }
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        } finally {
            lopeta();
        }
        return pisteet;
    }

    public Pisteet haePisteidenKeskiarvo(int id) {
        Pisteet pisteet = new Pisteet();
        try {
            String sql = "SELECT AVG(pisteet) FROM pisteet WHERE kirjaId = ?";
            alustaKysely(sql);
            statement.setInt(1, id);
            suoritaKysely();
            results.next();
            pisteet = palautaPisteet();
        } catch (SQLException e) {
            Logger.getLogger(Kayttaja.class
                    .getName()).log(Level.SEVERE, null, e);
        } finally {
            lopeta();
        }
        return pisteet;
    }

    public void lisaaKantaan() {
        try {
            String sql = "INSERT INTO pisteet (pisteet, kirjaId, kayttaja) "
                    + "VALUES(?, ?, ?) RETURNING id";
            alustaKysely(sql);
            statement.setInt(1, this.getPisteet());
            statement.setInt(2, this.getKirjaId());
            statement.setString(3, this.getKayttaja());
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
