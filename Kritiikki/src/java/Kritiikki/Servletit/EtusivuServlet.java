package Kritiikki.Servletit;

import Kritiikki.Mallit.Kirja;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EtusivuServlet extends YleisServlet {

    /**
     * Vie käyttäjän etusivulle.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");

        int montakoKirjaaSivulla = 20;
        String sivuParametri = request.getParameter("sivu");
        int sivu = 1;

        // Tarkistetaan, että parametri on olemassa ja että se on numeromuotoinen
//        if (sivuParametri != null && sivuParametri.matches("\\d+")) {
//            sivu = Integer.parseInt(sivuParametri);
//        }
        
        Kirja kirja = new Kirja();
        List<Kirja> kirjat = kirja.haeKirjatJaPisteet();
        request.setAttribute("kirjat", kirjat);
        
        PrintWriter out = luoPrintWriter(response);

        try {
            paivitaIlmoitus(request);
            naytaJSP("etusivu", request, response);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "";
    }
}
