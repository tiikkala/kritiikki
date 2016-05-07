package Kritiikki.Servletit.Kirjatoiminnot;

import Kritiikki.Mallit.Kirja;
import Kritiikki.Servletit.YleisServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Toteuttaa kirjan tietojen muokkaukseen vaativan toiminnallisuuden.
 */
public class KirjanMuokkausServlet extends YleisServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = luoPrintWriter(response);
        try {
            int id = haeIdSessionilta(request);
            Kirja kirja = new Kirja().haeKirjaJaPisteet(id);
            String nimi = haeStringArvo("nimi", request);
            String kirjailija = haeStringArvo("kirjailija", request);
            String julkaisuvuosi = haeStringArvo("julkaisuvuosi", request);
            String julkaisukieli = haeStringArvo("julkaisukieli", request);
            String suomentaja = haeStringArvo("suomentaja", request);
            kirja.setNimi(nimi);
            kirja.setKirjailja(kirjailija);
            kirja.setJulkaisukieli(nimi);
            kirja.setJulkaisuvuosi(julkaisuvuosi);
            kirja.setSuomentaja(suomentaja);
            int julkaisuvuosiInt = kirja.getJulkaisuvuosi();
            String sivu = "Kirja?id=" + id;
            if (kirja.onkoKelvollinen()) {
                kirja.paivitaKirjanTiedot(id, nimi, kirjailija, julkaisuvuosiInt, julkaisukieli, suomentaja);
                request.getSession().setAttribute("ilmoitus", "Kirjan muokkaus onnistui.");
                ohjaaSivulle(sivu, response);
            } else {
                Collection<String> virheet = kirja.getVirheet();
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
