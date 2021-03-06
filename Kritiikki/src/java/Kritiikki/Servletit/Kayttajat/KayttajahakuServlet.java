package Kritiikki.Servletit.Kayttajat;

import Kritiikki.Mallit.Kayttaja;
import Kritiikki.Servletit.YleisServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Toteuttaa käyttäjien hakemisen ja näyttämisen hakusanan perusteella.
 */
public class KayttajahakuServlet extends YleisServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = luoPrintWriter(response);
        try {
            String hakusana = haeStringArvo("hakusana", request);
            List<Kayttaja> kayttajat = new Kayttaja().haeHakusanalla(hakusana);
            request.setAttribute("kayttajat", kayttajat);
            if (kayttajat.isEmpty()) {
                asetaIlmoitus("Haku ei tuottanut yhtään osumaa.", request);
                ohjaaSivulle("Kayttajalista", response);
            } else {
                naytaJSP("kayttajalista", request, response);
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
