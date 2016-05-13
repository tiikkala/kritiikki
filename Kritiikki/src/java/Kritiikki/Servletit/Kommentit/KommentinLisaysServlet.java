package Kritiikki.Servletit.Kommentit;

import Kritiikki.Mallit.Kayttaja;
import Kritiikki.Mallit.Kommentti;
import Kritiikki.Mallit.Kritiikki;
import Kritiikki.Servletit.YleisServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * Toteuttaa kommentin lisäämiseksi tarvittavan toiminnallisuuden.
 */
public class KommentinLisaysServlet extends YleisServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = luoPrintWriter(response);
        request.setCharacterEncoding("UTF-8");
        try {
            Kommentti kommentti = new Kommentti();
            int kirjaId = haeIdSessionilta(request);
            Kayttaja kirjoittaja = (Kayttaja) request.getSession().getAttribute("kirjautunut");
            String kayttajaTunnus = kirjoittaja.getId();
            String teksti = request.getParameter("teksti");
            int kritiikkiId = Integer.valueOf(request.getParameter("kritiikkiId"));
            kommentti.setKirjoittaja(kayttajaTunnus);
            kommentti.setKritiikkiId(kritiikkiId);
            kommentti.setTeksti(teksti);
            String sivu = "Kirja?id=" + kirjaId;
            if (kommentti.onkoKelvollinen() && teksti.trim().length() != 0) {
                kommentti.lisaaKantaan();
                ohjaaSivulle(sivu, response);
            } else {
                Map<String, String> virheet = kommentti.getVirheet();
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
