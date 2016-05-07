package Kritiikki.Servletit.Kirjautuminen;

import Kritiikki.Servletit.YleisServlet;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KirjauduUlosServlet extends YleisServlet {
    
    /**
     * Toteuttaa uloskirjautumisen ja vie käyttäjän etusivulle.
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        
        kirjauduUlos(request);
        PrintWriter out = luoPrintWriter(response);

        try {
            tallennaIlmoitus("Uloskirjautuminen onnistui.", request);
            ohjaaSivulle("Kirjautuminen", response);
        }
        
        finally {
            if (out != null) {
                out.close();
            }
        }
    }
    
    private void kirjauduUlos(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("kirjautunut");
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