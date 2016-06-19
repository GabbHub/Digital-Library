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
 *Servlet per il login dello staff
 * 
 * @author SS
 */
public class Autenticazione extends HttpServlet {
        
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
            // inizializzo mappa e connette il database
            Map data=new HashMap();
            Database.connect();
            //controllo la sessione
            HttpSession s = SecurityLayer.checkSession(request);
            if (s != null){
                //se è in sessione come staff redirigo alla giusta pagina altrimenti errore
              UtenteDAOImplements dati;
              dati = new UtenteDAOImplements(); 
              Utente u = dati.getUtenteSingolo((Integer)(s.getAttribute("userid")));
              int start=((String)s.getAttribute("username")).indexOf("staff");
              if(u.getGruppo()!=1 && start!=-1){
              switch(((String)s.getAttribute("username")).substring(start)){
                  case "staffamministratore":{Database.close(); response.sendRedirect("pannello_amministratore"); break;}
                  case "staffrevisoretrascrizioni":{ Database.close(); response.sendRedirect("pannello_revisore_trascrizioni"); break;}
                  case "stafftrascrittore":{Database.close(); response.sendRedirect("pannello_trascrittore"); break;}
                  case "staffrevisoreimmagini":{Database.close(); response.sendRedirect("pannello_revisore_immagini"); break;}
                  case "staffacquisizione":{Database.close(); response.sendRedirect("acquisizione"); break;}
              }
            }else{Database.close(); action_error(request, response);}
            }//altrimenti se non sono in sessione controllo se mi sono stati inviati email e password
            else if(isNull(request.getParameter("email"))||isNull(request.getParameter("password"))){
                SecurityLayer.disposeSession(request);
                Database.close(); 
                FreeMarker.process("authentication.html", data, response, getServletContext());
            } else{
                //se mi sono stati inviati email e password controllo che l'utente sia
                //dello staff e redirigo alla giusta pagina
                
                String email = request.getParameter("email");
                String pass = request.getParameter("password");
                UtenteDAOImplements dati;
                dati = new UtenteDAOImplements(); 
                Utente u=dati.getUtentesingolo(email, pass);
            
              
            if (isNull(u)|| u.getGruppo()==1) {
              action_error(request, response);
            } 
              int i=u.getGruppo();
              switch(i){
                  case 6:{ Database.close(); SecurityLayer.createSession(request, email+"staffamministratore" , u.getId()); response.sendRedirect("pannello_amministratore"); break;}
                  case 5:{ Database.close(); SecurityLayer.createSession(request, email+"staffrevisoretrascrizioni" , u.getId());response.sendRedirect("pannello_revisore_trascrizioni"); break;}
                  case 4:{Database.close();  SecurityLayer.createSession(request, email+"stafftrascrittore" , u.getId());response.sendRedirect("pannello_trascrittore"); break;}
                  case 3:{Database.close();  SecurityLayer.createSession(request, email+"staffrevisoreimmagini" , u.getId());response.sendRedirect("pannello_revisore_immagini"); break;}
                  case 2:{Database.close();  SecurityLayer.createSession(request, email+"staffacquisizione" , u.getId());response.sendRedirect("acquisizione"); break;}
              }
             
            }
             Database.close(); 
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
