package Kritiikki.Servletit;

import Kritiikki.Mallit.Kritiikki;
import Kritiikki.Mallit.Kirja;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Näyttaa kirjan tiedot ja kirjaan liittyvät kritiikit.
 */
public class KirjaServlet extends YleisServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = luoPrintWriter(response);
        int id = haeId(request);
        Kirja k = new Kirja().haeKirjaJaPisteet(id);
        request.setAttribute("kirja", k);
        talletaSessionId(request, id);
        List<Kritiikki> kritiikit = new Kritiikki().haeKritiikitKirjaIdPerusteella(id);
        request.setAttribute("kritiikit", kritiikit);
        try {
            paivitaIlmoitus(request);
            naytaJSP("kirja", request, response);
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
