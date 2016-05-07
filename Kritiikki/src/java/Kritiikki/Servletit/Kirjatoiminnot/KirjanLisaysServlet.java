package Kritiikki.Servletit.Kirjatoiminnot;

import Kritiikki.Mallit.Kayttaja;
import Kritiikki.Mallit.Kirja;
import Kritiikki.Mallit.Pisteet;
import Kritiikki.Servletit.YleisServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tapio
 */
public class KirjanLisaysServlet extends YleisServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = luoPrintWriter(response);
        try {
            Kirja uusiKirja = new Kirja();
            Pisteet pisteet = new Pisteet();
            String nimi = haeStringArvo("nimi", request);
            String kirjailija = haeStringArvo("kirjailija", request);
            String julkaisuvuosi = haeStringArvo("julkaisuvuosi", request);
            String julkaisukieli = haeStringArvo("julkaisukieli", request);
            String suomentaja = haeStringArvo("suomentaja", request);
            int pisteArvo = haeIntArvo("pisteet", request);
            Kayttaja kirjoittaja = (Kayttaja) request.getSession().getAttribute("kirjautunut");
            String kayttajaTunnus = kirjoittaja.getId();
            uusiKirja.setNimi(nimi);
            uusiKirja.setKirjailja(kirjailija);
            uusiKirja.setJulkaisuvuosi(julkaisuvuosi);
            uusiKirja.setJulkaisukieli(julkaisukieli);
            uusiKirja.setSuomentaja(suomentaja);
            pisteet.setKayttaja(kayttajaTunnus);
            pisteet.setPisteet(pisteArvo);
            if (uusiKirja.onkoKelvollinen()) {
                uusiKirja.lisaaKantaan();
                pisteet.setKirjaId(uusiKirja.getId());
                pisteet.lisaaKantaan();
                ohjaaSivulle("Etusivu", response);
                request.getSession().setAttribute("ilmoitus", "Kirjan lisäys onnistui.");
            } else {
                Collection<String> virheet = uusiKirja.getVirheet();
                String ilmoitus = virheet.iterator().next();
                request.setAttribute("ilmoitus", ilmoitus);
                request.setAttribute("kirja", uusiKirja);
                naytaJSP("etusivu", request, response);
            }
        } finally {
            if (out != null) {
                out.close();
            }
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
