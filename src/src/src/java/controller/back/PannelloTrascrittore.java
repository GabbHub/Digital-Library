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
import java.util.ArrayList;
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
import model.DAO.ImmagineDAOImplements;
import model.DAO.OperaDAOImplements;
import model.DAO.TrascrizioneDAOImplements;
import model.DAO.UtenteDAOImplements;
import model.transfer_objects.Immagine;
import model.transfer_objects.Opera;
import model.DAO.Database;
import view.FreeMarker;
import controller.utility.SecurityLayer;

/**
 *Servlet per il pannello del trascrittore
 * 
 * @author SS
 */
public class PannelloTrascrittore extends HttpServlet {

    
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
        //inizializza mappa e oggetti DAO
        Map data= new HashMap();
        UtenteDAOImplements u=new UtenteDAOImplements();
        OperaDAOImplements o= new OperaDAOImplements();
        ImmagineDAOImplements i= new ImmagineDAOImplements();
        TrascrizioneDAOImplements t=new TrascrizioneDAOImplements();
        //controllo sessione e connessione a DB
        HttpSession s = SecurityLayer.checkSession(request);
         Database.connect();
        String email=u.getUtenteSingolo((int) s.getAttribute("userid")).getEmail(); 
        //controllo che l'utente sia un trascrittore
       if (!isNull(s) && ((String)s.getAttribute("username")).equals(email+"stafftrascrittore")) {
           //prendo l'opera associata all'utentee 
           Opera op=o.getOperaUtente((int) s.getAttribute("userid"));
           if(!isNull(op)){
               //se  è stato richiesto di inviare la trascrizione in revisione
               //effettuo la query al database
              if(!isNull(request.getParameter("revisione"))){
               HashMap update= new HashMap();
               update.put("revisione",true);
               Database.updateRecord("trascrizione", update,"immagine="+Integer.parseInt(request.getParameter("revisione")));
           }
              //inserisco l'opera associata nella mappa
           data.put("opera_associata", op);
           //prendo le immagini non validate dell'opera e gli tolgo le immagini
           //mandate già in revisione se la lista non è vuota
           ArrayList<Immagine> listaimmagini=i.getImmaginiOperaValidate(op);
           ArrayList<Immagine> inrevisione=i.getImmaginiOperaInRevisione(op);
           if(!listaimmagini.isEmpty()){
               listaimmagini.removeAll(inrevisione);
               t.setTrascrittoreAssociato(listaimmagini);              
           data.put("trascrittoreid",(int) s.getAttribute("userid"));
           data.put("listaimmagini",listaimmagini);}
           }          
           data.put("staff","Trascrizioni");
           data.put("active","1");
           Database.close();
           FreeMarker.process("back_trascrizioni.html",data, response, getServletContext()); 

           
       }else{ Database.close(); action_error(request,response);}
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
                            Logger.getLogger(PannelloTrascrittore.class.getName()).log(Level.SEVERE, null, ex);
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
                            Logger.getLogger(PannelloTrascrittore.class.getName()).log(Level.SEVERE, null, ex);
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
