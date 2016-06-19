/* 
 * Copyright (C) 2016 SS
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package controller.front;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Objects.isNull;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DAO.UtenteDAOImplements;
import model.transfer_objects.Utente;
import model.DAO.Database;
import view.FreeMarker;
import controller.utility.SecurityLayer;

/**
 * Servlet per la Registrazione
 *
 * @author SS
 */
public class Registrazione extends HttpServlet {

    /**
     * Gestisce l'errore se presente
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    private void action_error(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //assumiamo che l'eccezione sia passata tramite gli attributi della request
        //we assume that the exception has been passed using the request attributes    
        Map data = new HashMap();

        Exception exception = (Exception) request.getAttribute("exception");
        String message;
        if (exception != null && exception.getMessage() != null) {
            message = exception.getMessage();
        } else {
            message = "Si è verificato un errore.Ci scusiamo per il disagio";
        }
        data.put("errore", message);
        FreeMarker.process("404error.html", data, response, getServletContext());
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     * @throws javax.naming.NamingException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, NamingException {
        Map data = new HashMap();

        HttpSession s = SecurityLayer.checkSession(request);

        //se l'utente è già loggato lo rimando all'index
        if (s != null) {
            response.sendRedirect("index");
        }

        //Recupera l'email dell'utente
        String email = request.getParameter("u");
        //Recupera la password dell'utente
        String password = request.getParameter("p");
        //Recupera nome utente
        String nome = request.getParameter("n");
        //Recupera cognome utente
        String cognome = request.getParameter("co");
        //Recupera città
        String citta = request.getParameter("ci");
        //recupera indirizzo
        String indirizzo = request.getParameter("ind");
        //Recupera data di nascita
        String nascita = request.getParameter("da");

        //i campi sono già dichiarati required nell'html, eseguo controllo in ogni caso
        if (!isNull(nome) || !isNull(cognome) || !isNull(email)
                || !isNull(password) || !isNull(nascita)) {

            //connetto al DB e inizializzo i DAO
            Database.connect();
            UtenteDAOImplements dati;
            dati = new UtenteDAOImplements();
            //se l'inserimento dell'utente va a buon fine, creo la sessione e lo rimando all'index
            if (dati.addUtente(new Utente(nome, cognome, email, password, nascita, indirizzo, citta))) {
                Utente u = dati.getUtentesingolo(email, password);
                SecurityLayer.createSession(request, email, u.getId());
                Database.close();
                response.sendRedirect("index");
            } else {
                Database.close();
                action_error(request, response);
            }
        }
        FreeMarker.process("registrati.html", data, response, getServletContext());
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
        try {
            processRequest(request, response);
        } catch (SQLException | NamingException ex) {
            action_error(request, response);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException | NamingException ex) {
            action_error(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet per la Registrazione";
    }// </editor-fold>

}
