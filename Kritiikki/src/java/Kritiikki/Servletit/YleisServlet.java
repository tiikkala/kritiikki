package Kritiikki.Servletit;


import Kritiikki.Mallit.Kayttaja;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Luokka sisältää yleismetodeja, joita muut servletit käyttävät.
 * Kaikki muut servletit perivän YleisServletin.
 */

public class YleisServlet extends HttpServlet {
    
    protected boolean ohjaaKirjautumisSivulleJosEiKirjautunut(HttpServletRequest request, HttpServletResponse response) {      
        if (onkoKirjautunut(request) == null) {
            asetaIlmoitus("Sinun pitää ensin kirjautua sisään.", request);
            naytaJSP("kirjautuminen", request, response);
            return true;
        }
        return false;
    }
    
    protected boolean ohjaaSivulleJosArvoaEiValittu(HttpServletRequest request, HttpServletResponse response, int arvoId, int id, String sivu) {       
        if (arvoId == 0) {
            talletaSessionId(request, id);          
            ohjaaSivulle(sivu, response);
            return true;
        }
        return false;
    }
    
    protected boolean ohjaaSivulleJosArvoaEiValittu(HttpServletRequest request, HttpServletResponse response, String arvo, int id, String sivu) {      
        if (arvo == null || arvo.isEmpty()) {
            talletaSessionId(request, id);        
            ohjaaSivulle(sivu, response);
            return true;
        }
        return false;
    }
    
    protected void ohjaaSivulle(String paivitys, HttpServletRequest request, HttpServletResponse response, int id, String sivu) {
        tallennaIlmoitus(paivitys, request);
        talletaSessionId(request, id);           
        ohjaaSivulle(sivu, response);
    }
    
    /**
     * Ilmoitusten muokkaaminen liittyy "virheilmoituksiin", joita näkymissä näytetään, mikäli
     * ilmoitus sisältää jotain tekstiä.
     */
    protected void paivitaIlmoitus(HttpServletRequest request) {
        asetaIlmoitus(haeJaTyhjennaIlmoitus(request), request);
    }
    
    protected void asetaIlmoitus(String ilmoitus, HttpServletRequest request) {
        request.setAttribute("ilmoitus", ilmoitus);
    }
    
    protected void tallennaIlmoitus(String ilmoitus, HttpServletRequest request) {
        request.getSession().setAttribute("ilmoitus", ilmoitus);
    }
    
    protected String haeJaTyhjennaIlmoitus(HttpServletRequest request) {
       String ilmoitus = (String) request.getSession().getAttribute("ilmoitus");
       request.getSession().removeAttribute("ilmoitus");      
       return ilmoitus;
    }
    
    /**
     * Ohjaa määrätyn servletin luo.
     * @param sivu = servletin url web.xml:ssä.
     */    
    protected void ohjaaSivulle(String sivu, HttpServletResponse response) {
        try {
            response.sendRedirect(sivu);
        }       
        catch (IOException ex) {
            Logger.getLogger(YleisServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Näyttää näkymän.
     * @param sivu = näkymän nimi
     */   
    protected void naytaJSP(String sivu, HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher(sivu + ".jsp");      
        try {
            dispatcher.forward(request, response);
        }   
        catch (ServletException e) {
            System.out.println("ServletException");
        } 
        catch (IOException e) {
            System.out.println("IOException");
        }
    }
    
    protected Kayttaja onkoKirjautunut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Kayttaja kayttaja = (Kayttaja) session.getAttribute("kirjautunut");    
        return kayttaja;
    }
    

    protected PrintWriter luoPrintWriter(HttpServletResponse response) {
        PrintWriter out = null;     
        try {
            out = response.getWriter();
        }
        catch (IOException e) {}    
        return out;
    }
    
    /**
     * Tallettaa ko. olevan id arvon tulevaa käyttöä varten.
     * @param id 
     */
    protected void talletaSessionId(HttpServletRequest request, int id) {
        request.getSession().setAttribute("id", id);
    }
    
    protected int haeIdSessionilta(HttpServletRequest request) {
        return (Integer) request.getSession().getAttribute("id");
    }
    
    /**
     * Hakee id-parametria vastaavan arvon.
     */ 
    protected int haeIdParametrina(HttpServletRequest request) {
        return palautaArvo(request.getParameter("id"));
    }
    
    protected int haeId(HttpServletRequest request) {
        int id = haeIdParametrina(request);     
        if (id == 0) {
            id = haeIdSessionilta(request);
        }
        return id;
    }
    
    protected int haeIntArvo(String param, HttpServletRequest request) {
        return palautaArvo(request.getParameter(param));
    }
    
    private int palautaArvo(String param) {
        int arvo;   
        try {
            arvo = Integer.parseInt(param);
        }
        catch(NumberFormatException e) {
            arvo = 0;
        }     
        return arvo;
    }
    
    protected String haeStringArvo(String param, HttpServletRequest request) {
        return request.getParameter(param);
    }
}