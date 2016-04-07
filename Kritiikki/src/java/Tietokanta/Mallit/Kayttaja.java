package Tietokanta.Mallit;

import Tietokanta.Tietokanta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Kayttaja {

    private String Id;
    private String salasana;
    private String sposti;

    public Kayttaja(String Id, String salasana, String sposti) {
        this.Id = Id;
        this.salasana = salasana;
        this.sposti = sposti;
    }

    public String getId() {
        return this.Id;
    }

    public String getSalasana() {
        return this.salasana;
    }

    public String getSposti() {
        return this.sposti;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }

    public void setSposti(String sposti) {
        this.sposti = sposti;
    }

    public static List<Kayttaja> getKayttajat() throws SQLException {
        String sql = "SELECT id, salasana, sposti FROM kayttajat";
        Connection yhteys = Tietokanta.getYhteys();
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        ResultSet tulokset = kysely.executeQuery();

        ArrayList<Kayttaja> kayttajat = new ArrayList<Kayttaja>();
        while (tulokset.next()) {
            //Luodaan tuloksia vastaava olio ja palautetaan olio:
            Kayttaja k = new Kayttaja(tulokset.getString("id"), tulokset.getString("salasana"),
                    tulokset.getString("sposti"));
            kayttajat.add(k);
        }
        //Suljetaan kaikki resutuloksetsId:
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
