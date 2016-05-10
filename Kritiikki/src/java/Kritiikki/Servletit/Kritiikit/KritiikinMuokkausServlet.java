package Kritiikki.Servletit.Kritiikit;

import Kritiikki.Mallit.Kritiikki;
import Kritiikki.Servletit.YleisServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Toteuttaa kritiikin muokkauksen toiminnallisuuden.
 */
public class KritiikinMuokkausServlet extends YleisServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = luoPrintWriter(response);
        try {
            int kirjaId = haeIdSessionilta(request);
            int kritiikkiId = haeIntArvo("kritiikkiId", request);
            Kritiikki muokattavaKritiikki = new Kritiikki().haeKritiikki(kritiikkiId);
            String otsikko = haeStringArvo("otsikko", request);
            String teksti = haeStringArvo("teksti", request);
            muokattavaKritiikki.setOtsikko(otsikko);
            muokattavaKritiikki.setTeksti(teksti);
            String sivu = "Kirja?id=" + kirjaId;
            if (muokattavaKritiikki.onkoKelvollinen()) {              
                muokattavaKritiikki.muokkaaKritiikkia(kritiikkiId, teksti, otsikko);
                request.getSession().setAttribute("ilmoitus", "Kritiikin muokkaus onnistui.");
                naytaJSP(sivu, request, response);
            } else {
                Collection<String> virheet = muokattavaKritiikki.getVirheet().values();
                String ilmoitus = virheet.iterator().next();
                request.getSession().setAttribute("ilmoitus", ilmoitus);
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
