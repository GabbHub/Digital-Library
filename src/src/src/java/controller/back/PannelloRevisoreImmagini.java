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
import model.DAO.UtenteDAOImplements;
import model.transfer_objects.Immagine;
import model.transfer_objects.Notifica;
import model.transfer_objects.Opera;
import model.DAO.Database;
import view.FreeMarker;
import controller.utility.SecurityLayer;

/**
 *Servlet per il pannello del revisore delle immagini
 * 
 * @author SS
 */
public class PannelloRevisoreImmagini extends HttpServlet {

    
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
         * metodo per la validazione di un immagine
         * 
         * @param request   servlet request
         * @see Immagine
         * @see Notifica
         * @see ImmagineDAOImplements
         * @see NotificaDAOImplements
         * @return true se va a buon fine, false altrimenti
         */
    private boolean action_valida(HttpServletRequest request){
            // inizializzo DAO e mappa
           ImmagineDAOImplements i=new ImmagineDAOImplements();
           NotificaDAOImplements n=new NotificaDAOImplements();
          HashMap update=new HashMap();
          update.put("validazione","1");
          //se la cancellazione delle notifiche va a buon fine aggiorno validazione immagine
          if(n.deleteNotifica("notifiche_immagini",Integer.parseInt(request.getParameter("immagine"))))
             return i.updateImmagine(update,"ID="+Integer.parseInt(request.getParameter("immagine"))); 
          else return false;
      }
    
        /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.naming.NamingException
     * @throws java.sql.SQLException
     * @throws IOException if an I/O error occurs
     */
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws NamingException, SQLException, IOException{
        //inizializzo mappa e DAO
        Map data= new HashMap();
        UtenteDAOImplements u=new UtenteDAOImplements();
        OperaDAOImplements o= new OperaDAOImplements();
        ImmagineDAOImplements i= new ImmagineDAOImplements();
        NotificaDAOImplements n=new NotificaDAOImplements();
        //controllo della sessione e connessione al DB
        HttpSession s = SecurityLayer.checkSession(request);
         Database.connect();
        String email=u.getUtenteSingolo((int) s.getAttribute("userid")).getEmail();  
        //controllo che l'utente sia un revisore di immagini
       if (!isNull(s) && ((String)s.getAttribute("username")).equals(email+"staffrevisoreimmagini")){
           //prendo l'opera a cui il revisore è associato e se non è nulla la inserisco
           Opera op=o.getOperaUtente((int) s.getAttribute("userid"));
           if(!isNull(op)){
           data.put("opera_associata", op);
           //se è stata richiesta la validazione
           if(!isNull(request.getParameter("validazione")))
               //se la validazione non va a buon fine richiamo l'action error
               if(!action_valida(request))action_error(request,response);
           //prende le immagini dell'opera associata non validate e le mette in lista
           //poi aggiunge la lista alla mappa
           ArrayList<Immagine> listaimmagini=i.getImmaginiOperaNonValidate(op);
           if(!listaimmagini.isEmpty()){
           data.put("listaimmagini",listaimmagini);}
           } 
           //se è stata richiesta la visualizzazione delle notifiche
          if(!isNull(request.getParameter("immagine")) && !isNull(request.getParameter("numpag"))){
              //controllo che non sia stato inviato un messaggio, altrimenti aggiungo la notifica
               if(!isNull(request.getParameter("messaggio"))){n.addNotifica(
                       "notifiche_immagini",
                       new Notifica((int) s.getAttribute("userid"),
                               request.getParameter("messaggio"),
                               Integer.parseInt(request.getParameter("immagine"))
                               ));
               }
               ArrayList<Notifica> listanotifiche=n.getNotificheImmagine(Integer.parseInt(request.getParameter("immagine")));
               data.put("listanotifiche", listanotifiche);
               data.put("numero_pagina",Integer.parseInt(request.getParameter("numpag")));
               data.put("mittente", (int) s.getAttribute("userid"));
           }
           data.put("active","1");
           data.put("staff","Revisione Acquisizioni");
           Database.close();
           FreeMarker.process("back_revisione_immaggini.html",data, response, getServletContext()); 

           
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
                                    Logger.getLogger(PannelloRevisoreImmagini.class.getName()).log(Level.SEVERE, null, ex);
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
                                    Logger.getLogger(PannelloRevisoreImmagini.class.getName()).log(Level.SEVERE, null, ex);
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
