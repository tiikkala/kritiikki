package Kritiikki.Servletit;

import Kritiikki.Mallit.Kirja;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EtusivuServlet extends YleisServlet {

    /**
     * Vie käyttäjän etusivulle.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = luoPrintWriter(response);
        try {
            Kirja kirja = new Kirja();
            List<Kirja> kirjat = kirja.haeKirjatJaPisteet();
            request.setAttribute("kirjat", kirjat);
            paivitaIlmoitus(request);
            naytaJSP("etusivu", request, response);
        } finally {
            out.close();
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
