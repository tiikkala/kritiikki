package Kritiikki.Servletit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OhjaaKirjautumisSivulleServlet extends YleisServlet {
    
    /**
     * Tätä ko. servlettiä kutsutaan eri näkymistä, joiden katselemiseen vaaditaan kirjautumista.
     * Mikäli käyttäjä ei ole kirjautunut, tämän servletin "doGet" metodi käsitellään.
     */
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ohjaaKirjautumisSivulleJosEiKirjautunut(request, response);
    }

    @Override
    public String getServletInfo() {
        return "";
    }  
}