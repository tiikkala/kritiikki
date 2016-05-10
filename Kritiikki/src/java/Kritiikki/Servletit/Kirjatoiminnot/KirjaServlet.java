package Kritiikki.Servletit.Kirjatoiminnot;

import Kritiikki.Mallit.Kritiikki;
import Kritiikki.Mallit.Kirja;
import Kritiikki.Mallit.Kommentti;
import Kritiikki.Servletit.YleisServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
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
        // haetaan kirjan id
        int id = haeId(request);
        Kirja k = new Kirja().haeKirjaJaPisteet(id);
        request.setAttribute("kirja", k);
        // tallennetaan id sessioniin tulevaa käyttöä varten
        talletaSessionId(request, id);
        // haetaan kirjaan liittyvät kritiikit
        List<Kritiikki> kritiikit = new Kritiikki().haeKritiikitKirjaIdPerusteella(id);   
        // haetaan kritiikkeihin liittyvät kommentit
        Map <Integer, List<Kommentti>> kommentit = haeKritiikkeihinLiittyvatKommentit(kritiikit);
        request.setAttribute("kommentit", kommentit);
        request.setAttribute("kritiikit", kritiikit);
        try {
            paivitaIlmoitus(request);
            naytaJSP("kirja", request, response);
        } finally {
            out.close();
        }
    }
    
    /**
     * Metodi luo hajautustaulun, jossa on avaimena kritiikin id ja arvona lista kritiikkiin 
     * liittyvistä kommenteista. 
     * @param kritiikit = lista kritiikeistä
     * @return kommentit = kritiikkeihin liittyvät kommentit
     */
    private Map<Integer, List<Kommentti>> haeKritiikkeihinLiittyvatKommentit(List<Kritiikki> kritiikit) {
        Map<Integer, List<Kommentti>> kommentit = new HashMap<Integer, List<Kommentti>>();
        for (Kritiikki k : kritiikit) {
            kommentit.put(k.getId(), new Kommentti().haeKritiikkiinLittyvatKommentit(k.getId()));
        }
        return kommentit;
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
