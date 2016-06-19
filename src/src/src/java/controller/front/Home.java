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
import model.DAO.OperaDAOImplements;
import model.transfer_objects.Opera;
import controller.utility.DataUtil;
import model.DAO.Database;
import view.FreeMarker;
import controller.utility.SecurityLayer;

/**
 *Servlet per l'index dell'applicazione
 * 
 * @author SS
 */
public class Home extends HttpServlet {

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
        //inizializzo mappa per html,connessione al DB e DAO, più dichiaro la lista delle opere
        Map data=new HashMap();
        OperaDAOImplements o=new OperaDAOImplements();
         ArrayList<Opera> listaopere;
         int pagina_home = 0;
         int numeropagine;
         int[] pagine;
          Database.connect();
          //prendo la lista delle opere con almeno le immagini pubblicate
          if(isNull(request.getParameter("parametro_ricerca"))){
          if(isNull(request.getParameter("pagina_home")) && 
                  isNull(request.getParameter("numeropagine"))){
           numeropagine=(int)Math.ceil((float) o.contaOperePubblicate() / (float) 4);
           pagine = new int[numeropagine];
           listaopere=o.getOperePubblicate(0); 
          }else{
              numeropagine=Integer.parseInt(request.getParameter("numeropagine"));
              pagina_home=Integer.parseInt(request.getParameter("pagina_home"));
              pagine = new int[numeropagine];
              listaopere=o.getOperePubblicate(pagina_home*4);
          }
          }else{    
           if(isNull(request.getParameter("pagina_home")) && 
                  isNull(request.getParameter("numeropagine"))){
           numeropagine=(int)Math.ceil((float) o.contaOpereRicerca(request.getParameter("parametro_ricerca")) / (float) 4);
           pagine = new int[numeropagine];
           listaopere=o.getOperePubblicate(request.getParameter("parametro_ricerca"),0); 
          }else{
              numeropagine=Integer.parseInt(request.getParameter("numeropagine"));
              pagina_home=Integer.parseInt(request.getParameter("pagina_home"));
              pagine = new int[numeropagine];
              listaopere=o.getOperePubblicate(request.getParameter("parametro_ricerca"),pagina_home*4);
          }
           data.put("parametro_ricerca",request.getParameter("parametro_ricerca"));
          }
           data.put("pagina_home", pagina_home);
            data.put("numeropagine", numeropagine);
            data.put("pagine", pagine);
           //aggiungo le copertine delle opere da mostrare a video
           DataUtil.setCopertine(listaopere);
           //chiudo il DB e inserisco le opere nella mappa
           Database.close();
         data.put("listaopere", listaopere);
      HttpSession s = SecurityLayer.checkSession(request);
            //se l'utente è in sessione inserisco il benvenuto
            if (s != null) {
                 data.put("session",s.getAttribute("username"));
                FreeMarker.process("index.html", data, response, getServletContext());
            } else {  
            FreeMarker.process("index.html", data , response, getServletContext());
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
                     Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
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
                     Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
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
        return "Homepage";
    }// </editor-fold>

}
