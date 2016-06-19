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
package controller.back;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Objects.isNull;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DAO.UtenteDAOImplements;
import controller.utility.DataUtil;
import model.DAO.Database;
import view.FreeMarker;
import controller.utility.SecurityLayer;

/**
 *Servlet per il profilo dello staff
 * 
 * @author SS
 */

public class BackProfilo extends HttpServlet {

    /**
     * Handles the error if one occurs.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    
      private void action_error(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //assumiamo che l'eccezione sia passata tramite gli attributi della request
        //we assume that the exception has been passed using the request attributes    
           Map data=new HashMap();
          
        Exception exception = (Exception) request.getAttribute("exception");
        String message;
        if (exception != null && exception.getMessage() != null) {
            message = exception.getMessage();
        } else {
            message = "Unknown error";
        }
        data.put("errore",message);
        FreeMarker.process("404error.html",data, response, getServletContext());
    }
             
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws javax.naming.NamingException
     * @throws java.sql.SQLException
     */         
      
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException, SQLException {
                // inizializzo mappa,DAO e connetto al db
                HashMap data=new HashMap();
                Database.connect();
                UtenteDAOImplements u=new UtenteDAOImplements(); 
                //controllo sessioone e che l'utente sia dello staff
                HttpSession s = SecurityLayer.checkSession(request);
                int start=((String)s.getAttribute("username")).indexOf("staff");
       if (!isNull(s) && start!=-1){
                data.put("staff",request.getParameter("staff"));
                data.put("active",request.getParameter("active"));
                //se sono stati inseriti i campi per la modifica della password
                //effettuo update e stampo il risultato
                if(!isNull(request.getParameter("password")) && 
                   !isNull(request.getParameter("nuova_password")) &&
                   !isNull(request.getParameter("conferma_nuova_password"))){
                    HashMap update=new HashMap();
                    if(u.getUtenteSingolo((int)s.getAttribute("userid")).getPassword().equals(DataUtil.crypt(request.getParameter("password")))){
                    update.put("password",DataUtil.crypt(request.getParameter("nuova_password")));
                    if(u.updateUtente(update,"ID="+(int)s.getAttribute("userid")))
                    data.put("risultato_password","eseguito con successo");
                    else   data.put("risultato_password","errore nell'esecuzione");
                } else   data.put("risultato_password","password non corretta");
                }
                //se sono stati inseriti i campi per la modifica dell'indirizzo email
                //effettuo update e stampo il risultato
                 if(!isNull(request.getParameter("nuovo_indirizzo")) && 
                   !isNull(request.getParameter("conferma_nuovo_indirizzo"))){
                    HashMap update=new HashMap();
                    update.put("email",request.getParameter("nuovo_indirizzo"));
                    if(u.updateUtente(update,"ID="+(int)s.getAttribute("userid")))
                    data.put("risultato_indirizzo","eseguito con successo, effettuare logout e rientrare");
                    else data.put("risultato_indirizzo","errore nell'esecuzione");
                 }
                //chiudo il db
                Database.close();
                FreeMarker.process("back_profilo.html", data, response, getServletContext());}
            else{Database.close(); action_error(request,response);}
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
                 } catch (NamingException | SQLException ex) {
                     Logger.getLogger(BackProfilo.class.getName()).log(Level.SEVERE, null, ex);
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
                 } catch (NamingException | SQLException ex) {
                     Logger.getLogger(BackProfilo.class.getName()).log(Level.SEVERE, null, ex);
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
        return "Short description";
    }// </editor-fold>

}
