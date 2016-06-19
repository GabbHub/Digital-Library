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
import model.transfer_objects.Immagine;
import model.transfer_objects.Opera;
import model.DAO.Database;
import view.FreeMarker;
import controller.utility.SecurityLayer;

/**
 *Servlet per le pagine e le trascrizioni dell'opera scelta nel dettaglio
 * 
 * @author SS
 */
public class Details extends HttpServlet {

    
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
            message = "Si è verificato un errore.Ci scusiamo per il disagio";
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
        //inizializzo mappa per html, e controllo la sessione, se non è in sessione rimando alla Login
         Map data=new HashMap();
          HttpSession s = SecurityLayer.checkSession(request);
          //i parametri opera e img sono passati in GET con un href, eseguo controllo
            if (s != null && !isNull(request.getParameter("opera")) && !isNull(request.getParameter("img"))) {
                //inizializzo il DBe i DAO
                Database.connect();
                OperaDAOImplements op=new OperaDAOImplements();
                ImmagineDAOImplements img=new ImmagineDAOImplements();
                //prendo l'opera scelta e la pagina richiesta
                  Opera opera=op.getOperaSingola(request.getParameter("opera"));
                  Immagine immagine=img.getImmagineSingola(opera,Integer.parseInt(request.getParameter("img")));
                  //controllo il numero massimo delle pagine, e inserisco il dato nell'html
                  int maxpage=(opera.getNumero_pagine());
                  data.put("opera",opera);
                  data.put("immagine",immagine);
                  data.put("maxpage",(maxpage));
                  if(immagine.getNumero_pagina()==(maxpage)){data.put("ultimapagina","");}
                 data.put("session",s.getAttribute("username"));
                 Database.close();
        FreeMarker.process("details.html",data, response, getServletContext());
            }
            else{
                response.sendRedirect("login");
            }
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
                         Logger.getLogger(Details.class.getName()).log(Level.SEVERE, null, ex);
                          action_error(request,response);
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
                         Logger.getLogger(Details.class.getName()).log(Level.SEVERE, null, ex);
                          action_error(request,response);
                     }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet per le pagine e le trascrizioni dell'opera scelta nel dettaglio";
    }// </editor-fold>

}
