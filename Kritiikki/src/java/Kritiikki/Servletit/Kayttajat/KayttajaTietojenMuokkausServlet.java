package Kritiikki.Servletit.Kayttajat;

import Kritiikki.Mallit.Kayttaja;
import Kritiikki.Servletit.YleisServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Toteuttaa käyttäjätietojen muokkaamisen logiikan.
 */
public class KayttajaTietojenMuokkausServlet extends YleisServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = luoPrintWriter(response);
        try {
            String id = haeStringArvo("id", request);
            String sposti = haeStringArvo("sposti", request);
            String salasana = haeStringArvo("salasana", request);
            Kayttaja kayttaja = new Kayttaja();
            kayttaja.setId(id);
            kayttaja.setSalasana(salasana);
            kayttaja.setSposti(sposti);
            // poistetaan käyttäjätunnukseen liittyvät virheet, koska käyttäjätunnus
            // on käytössä ja tuottaa siten aina virheilmoitukse
            Map<String, String> virheet = kayttaja.getVirheet();
            virheet.remove("tunnus");
            if (virheet.isEmpty()) {
                kayttaja.muutaTietoja(salasana, sposti, id);
                // asetetaan kirjautunukeesi käyttäjäksi nykyinen käyttäjä, jonka tietoja
                // on muokattu
                request.getSession().setAttribute("kirjautunut", kayttaja);
                ohjaaSivulle("Kayttajatiedot", response);
            } else {
                Collection<String> ilmoitukset = virheet.values();
                String ilmoitus = ilmoitukset.iterator().next();
                asetaIlmoitus(ilmoitus, request);
                naytaJSP("kayttajatiedot", request, response);
            }
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
