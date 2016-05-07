package Kritiikki.Servletit.ProfiilinNayttaminen;

import Kritiikki.Mallit.Kayttaja;
import Kritiikki.Mallit.Kirja;
import Kritiikki.Mallit.Kommentti;
import Kritiikki.Mallit.Kritiikki;
import Kritiikki.Servletit.YleisServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Toteuttaa profiilin näyttämiseen liittyvän logiikan. Hakee listan käyttäjän arvostelmista kirjoista
 * sekä käyttäjän kirjoittamista kritiikeistä ja kommenteista.
 */
public class ProfiiliServlet extends YleisServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = luoPrintWriter(response);
        try {
            Kayttaja kayttaja = (Kayttaja) request.getSession().getAttribute("kirjautunut");
            String kayttajaTunnus = kayttaja.getId();
            List<Kirja> kirjat = new Kirja().haeKayttajanArvostelematKirjat(kayttajaTunnus);
            List<Kritiikki> kritiikit = new Kritiikki().haeKayttajanKirjoittamatKritiikit(kayttajaTunnus);
            List<Kirja> kritikoidutKirjat = new Kirja().haeKayttajanKritikoimatKirjat(kayttajaTunnus);
            List<Kommentti> kommentit = new Kommentti().haeKayttajanKirjoittamatKommentit(kayttajaTunnus);
            request.setAttribute("kirjat", kirjat);
            request.setAttribute("kritiikit", kritiikit);
            request.setAttribute("kritikoidutKirjat", kritikoidutKirjat);
            request.setAttribute("kommentit", kommentit);
            naytaJSP("profiili", request, response);
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
