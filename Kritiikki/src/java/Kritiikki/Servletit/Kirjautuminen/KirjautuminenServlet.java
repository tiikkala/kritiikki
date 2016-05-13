package Kritiikki.Servletit.Kirjautuminen;

import Kritiikki.Mallit.Kayttaja;
import Kritiikki.Servletit.YleisServlet;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Toteuttaa sisäänkirjautumisessa tapahtuvan logiikan.
 */
public class KirjautuminenServlet extends YleisServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        if (onkoKirjautunut(request) != null) {
            ohjaaSivulle("Etusivu", response);
            return;
        }
        PrintWriter out = luoPrintWriter(response);
        try {
            String id = haeStringArvo("id", request);
            String salasana = haeStringArvo("salasana", request);
            if (id == null || id.length() == 0) {
                naytaJSP("kirjautuminen", request, response);
                return;
            }
            request.setAttribute("kayttaja", id);
            if (salasana == null || salasana.length() == 0) {
                asetaIlmoitus("Kirjautuminen epäonnistui! Et antanut salasanaa.", request);
                naytaJSP("kirjautuminen", request, response);
                return;
            }
            Kayttaja kayttaja = new Kayttaja().etsiKayttajaTunnuksenJaSalasananPeruseella(id, salasana);
            if (kayttaja != null) {
                kirjauduSisaan(request, kayttaja);
                ohjaaSivulle("Etusivu", response);
                return;
            } else {
                asetaIlmoitus("Kirjautuminen epäonnistui! Antamasi tunnus tai salasana on väärä.", request);
                naytaJSP("kirjautuminen", request, response);
                return;
            }
        } finally {
                out.close();
            }
        }

    private void kirjauduSisaan(HttpServletRequest request, Kayttaja kayttaja) {
        request.getSession().setAttribute("kirjautunut", kayttaja);
        haeJaTyhjennaIlmoitus(request);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "";
    }
}
