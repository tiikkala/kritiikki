package Kritiikki.Servletit;

import Kritiikki.Mallit.Kayttaja;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Toteuttaa käyttäjän rekisteröitymiseen liittyvän toiminnallisuuden.
 */
public class RekisteroityminenServlet extends YleisServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = luoPrintWriter(response);
        try {
            String id = haeStringArvo("id", request);
            String sposti = haeStringArvo("sposti", request);
            String salasana = haeStringArvo("salasana", request);
            Kayttaja uusiKayttaja = new Kayttaja();
            uusiKayttaja.setId(id);
            uusiKayttaja.setSalasana(salasana);
            uusiKayttaja.setSposti(sposti);
            if (uusiKayttaja.onkoKelvollinen()) {
                uusiKayttaja.lisaaKantaan();
                tallennaIlmoitus("Rekisteröityminen onnistui.", request);
                ohjaaSivulle("Kirjautuminen", response);
            } else {
                Collection<String> virheet = uusiKayttaja.getVirheet().values();
                String ilmoitus = virheet.iterator().next();
                tallennaIlmoitus(ilmoitus, request);
                request.setAttribute("id", id);
                request.setAttribute("salasana", salasana);
                request.setAttribute("sposti", sposti);
                ohjaaSivulle("Kirjautuminen", response);
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
