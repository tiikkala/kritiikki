package Kritiikki.Servletit.Kommentit;

import Kritiikki.Mallit.Kommentti;
import Kritiikki.Servletit.YleisServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Toteuttaa kommentin muokkauksen toiminnallisuuden.
 */
public class KommentinMuokkausServlet extends YleisServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = luoPrintWriter(response);
        try {
            int kirjaId = haeIdSessionilta(request);
            int id = haeIntArvo("id", request);
            String teksti = haeStringArvo("teksti", request);
            Kommentti muokattavaKommentti = new Kommentti().haeKommentti(id);
            muokattavaKommentti.setTeksti(teksti);
            String sivu = "Kirja?id=" + kirjaId;
            if (muokattavaKommentti.onkoKelvollinen()) {
                muokattavaKommentti.muokkaaKommenttia(id, teksti);
                asetaIlmoitus("Kommentin muokkaus onnistui.", request);
                ohjaaSivulle(sivu, response);
            } else {
                Collection<String> virheet = muokattavaKommentti.getVirheet().values();
                String ilmoitus = virheet.iterator().next();
                request.getSession().setAttribute("ilmoitus", ilmoitus);
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
