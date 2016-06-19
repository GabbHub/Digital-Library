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
import model.DAO.NotificaDAOImplements;
import model.DAO.OperaDAOImplements;
import model.DAO.TrascrizioneDAOImplements;
import model.DAO.UtenteDAOImplements;
import model.transfer_objects.Notifica;
import model.transfer_objects.Opera;
import model.DAO.Database;
import view.FreeMarker;
import controller.utility.SecurityLayer;

/**
 *Servlet per le trascrizioni in revisione
 * 
 * @author SS
 */
public class TrascrizioniInRevisione extends HttpServlet {

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
        //Inzializzo mappa e DAO
        Map data= new HashMap();
        UtenteDAOImplements u=new UtenteDAOImplements();
        OperaDAOImplements o= new OperaDAOImplements();
        TrascrizioneDAOImplements t=new TrascrizioneDAOImplements();
        ImmagineDAOImplements i= new ImmagineDAOImplements();
        NotificaDAOImplements n=new NotificaDAOImplements();
        //controllo sessione e che l'utente sia trascrittore
        HttpSession s = SecurityLayer.checkSession(request);
         Database.connect();
        String email=u.getUtenteSingolo((int) s.getAttribute("userid")).getEmail();   
       if (!isNull(s) && ((String)s.getAttribute("username")).equals(email+"stafftrascrittore")) {
           //prendo l'opera associata
           Opera opera=o.getOperaUtente((int) s.getAttribute("userid"));
           //se è stata richiesta la visualizzazione delle notifiche
           if(!isNull(request.getParameter("trascrizione")) && !isNull(request.getParameter("numpag"))){
               //controllo che non sia stato inviato un messaggio, altrimenti aggiungo la notifica
               if(!isNull(request.getParameter("messaggio"))){n.addNotifica(
                       "notifiche_trascrizione",
                       new Notifica((int) s.getAttribute("userid"),
                               request.getParameter("messaggio"),
                               Integer.parseInt(request.getParameter("trascrizione"))
                               ));
               }
               ArrayList<Notifica> listanotifiche=n.getNotificheTrascrizione(Integer.parseInt(request.getParameter("trascrizione")));
               data.put("listanotifiche", listanotifiche);
               data.put("numero_pagina",Integer.parseInt(request.getParameter("numpag")));
               data.put("mittente", (int) s.getAttribute("userid"));
           }
           //inizializzo la lista delle trascrizioni in revisione se l'opera non è nulla
           //e inserisco nella mappa insieme all'opera
           ArrayList<model.transfer_objects.Trascrizione> listatrascrizioni = null;
           if(!isNull(opera)){
            listatrascrizioni = t.getTrascrizioniInRevisioneUtente((int) s.getAttribute("userid"));
           }
           Database.close();
           data.put("opera_associata", opera);
           data.put("listatrascrizioni", listatrascrizioni);
           data.put("staff","Trascrizioni");
           data.put("active","2");    
           FreeMarker.process("back_trascrizioni_revisione.html",data, response, getServletContext()); 
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
                            Logger.getLogger(TrascrizioniInRevisione.class.getName()).log(Level.SEVERE, null, ex);
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
                            Logger.getLogger(TrascrizioniInRevisione.class.getName()).log(Level.SEVERE, null, ex);
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
