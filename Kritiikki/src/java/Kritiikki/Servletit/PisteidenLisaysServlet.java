package Kritiikki.Servletit;

import Kritiikki.Mallit.Kayttaja;
import Kritiikki.Mallit.Pisteet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Toteuttaa pisteiden antamiseen liittyvän logiikan.
 */
public class PisteidenLisaysServlet extends YleisServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = luoPrintWriter(response);
        try {
            int pisteet = haeIntArvo("pisteet", request);
            Kayttaja kayttaja = (Kayttaja) request.getSession().getAttribute("kirjautunut");
            String kayttajaId = kayttaja.getId();
            int kirjaId = haeIdSessionilta(request);
            Pisteet p = new Pisteet();
            p.setKirjaId(kirjaId);
            p.setPisteet(pisteet);
            p.setKayttaja(kayttajaId);
            String sivu = "Kirja?id=" + kirjaId;
            if (!p.kayttajaOnJoArvostellut(kayttaja.getId(), kirjaId) && p.onkoKelvollinen()) {
                p.lisaaKantaan();
                ohjaaSivulle(sivu, response);
            } else {
                request.getSession().setAttribute("ilmoitus", "Voit arvioida kirjan vain kerran. Pisteiden on oltava kokonaisluku"
                        + "välillä 4-10.");
                ohjaaSivulle(sivu, response);
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
