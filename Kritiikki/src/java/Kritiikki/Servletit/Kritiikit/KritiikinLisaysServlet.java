package Kritiikki.Servletit.Kritiikit;

import Kritiikki.Mallit.Kayttaja;
import Kritiikki.Mallit.Kritiikki;
import Kritiikki.Servletit.YleisServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tapio
 */
public class KritiikinLisaysServlet extends YleisServlet {

    /**
     * Toteuttaa kritiikin lisäämiseen liittyvän toiminnallisuuden.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = luoPrintWriter(response);
        try {
            Kritiikki k = new Kritiikki();
            //haetaan kirjautunut käyttäjä
            Kayttaja kirjoittaja = (Kayttaja) request.getSession().getAttribute("kirjautunut");
            int kirjaId = haeIdSessionilta(request);
            k.setKirjoittaja(kirjoittaja.getId());
            k.setOtsikko(haeStringArvo("otsikko", request));
            k.setTeksti(haeStringArvo("teksti", request));
            k.setKirjaId(kirjaId);
            String sivu = "Kirja?id=" + kirjaId;
            if (k.onkoKelvollinen()) {
                k.lisaaKantaan();
                ohjaaSivulle(sivu, response);
            } else {
                Map<String, String> virheet = k.getVirheet();
                String ilmoitus = virheet.values().iterator().next();
                request.setAttribute("ilmoitus", ilmoitus);
                naytaJSP(sivu, request, response);
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
