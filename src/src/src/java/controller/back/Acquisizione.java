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

import java.io.File;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Objects.isNull;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import model.DAO.Database;
import view.FreeMarker;
import controller.utility.SecurityLayer;

//max 10 mb per file e max 50 mb per richiesta di upload
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)

/**
 * Servlet per l'acquisizione di immagini digitali
 *
 * @author SS
 */
public class Acquisizione extends HttpServlet {

    //path per la cartella dei file delle opere
    private final String PATH = "Opere" + File.separatorChar + File.separatorChar;

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
        Map data = new HashMap();

        Exception exception = (Exception) request.getAttribute("exception");
        String message;
        if (exception != null && exception.getMessage() != null) {
            message = exception.getMessage();
        } else {
            message = "Unknown error";
        }
        data.put("errore", message);
        FreeMarker.process("404error.html", data, response, getServletContext());
    }
    
    /**
     * inserisce automaticamente nel db la notifica che l'immagine è stata
     * uploadata
     * 
     * @param id        id dell'acquisitore
     * @param immagine  id dell'immagine
     * @see Notifica
     */
    
    private void action_notifica(int id, int immagine) {
        NotificaDAOImplements n = new NotificaDAOImplements();
        Notifica nt = new Notifica(
                id,
                "Immagine Uploadata",
                immagine);
        n.addNotifica("notifiche_immagini", nt);
    }

    /**
     * metodo per l'upload dei file e delle immagini, i file saranno salvati su 
     * filesystem, e i relativi dati nel database
     * 
     * @param request servlet request
     * @param response servlet response
     * @param data      mappa con i dati
     * @param id id dell'utente che fa l'upload
     * @param opera opera a cui legare l'immagine
     * 
     * @throws IOException if an I/O error occurs
     * @throws Exception if a Java.Lang.Exception  occurs
     * 
     */
    
    private boolean action_upload(HttpServletRequest request, HttpServletResponse response, Map data, int id, Opera opera) throws IOException, Exception{
        //istanzio i DAO necessari
        ImmagineDAOImplements dati = new ImmagineDAOImplements();
        OperaDAOImplements o = new OperaDAOImplements();
        //Se la servlet è multipart
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                FileItemFactory fif = new DiskFileItemFactory();
                ServletFileUpload sfo = new ServletFileUpload(fif);
                List<FileItem> items = sfo.parseRequest(request);
                //lista di immagini
                ArrayList<Immagine> immagini;
                immagini = new ArrayList<>();
                int count = 0;
                //primo ciclo per controllare i dati
                for (FileItem item : items) {
                    //se non è un campo form (quindi un file)
                    if (!item.isFormField()) {
                        String name = item.getName();
                        String contentType = item.getContentType();
                        long size = item.getSize();
                        //controllo che il nome non ia vuoto, che la grandezza sia maggiore di zero e che sia un immagine
                        if (size > 0 && !name.isEmpty() && contentType.startsWith("image/")) {
                            //scelgo il path del file
                            File target = new File(request.getServletContext().getRealPath("") + (PATH + opera.getTitolo() + File.separatorChar + File.separatorChar + name).replace(" ", "_"));
                            //se il file non esiste e non è un cartella
                            if (!target.exists() && !target.isDirectory()) {
                                //inserisco i dati in un oggetto immagine da inserire nella lista
                                immagini.add(new Immagine((PATH + opera.getTitolo() + File.separatorChar + File.separatorChar + name).replace(" ", "_"), false, contentType, size));
                            } else {
                                //altrimenti controllo se l'immagine giò esiste
                                Immagine i = dati.getImmagineSingola((PATH + opera.getTitolo() + File.separatorChar + File.separatorChar + name).replace(" ", "_"));
                                if (!isNull(i)) {
                                    data.put("risultato", "hai provato a inserire un file già esistente:"
                                            + name
                                            + ",pagina associata:"
                                            + i.getNumero_pagina());
                                    return false;
                                }
                            }
                        } else {
                            data.put("risultato", "ERRORE NELL'INSERIMENTO");
                            return false;
                        }
                        //altrimenti se il file è un campo form (quindi non un file)
                    } else if (item.isFormField() && !item.getString().isEmpty()) {
                        //controllo che la pagina inserita non sia già presente
                        if (Database.countRecord("immagini_digitali", "numero_pagina=" + item.getString() + " && opera=" + opera.getId()) == 0) {
                            immagini.get(count++).setNumero_pagina(parseInt(item.getString()));
                        } else {
                            data.put("risultato", "hai provato a inserire una pagina già esistente:" + item.getString());
                            return false;
                        }

                    }
                }
                //se i dati sono corretti esegue secondo ciclo per salvare file e dati nel db
                count = 0;
                for (FileItem item : items) {
                    if (!item.isFormField() && item.getSize() > 0 && item.getContentType().startsWith("image/")) {
                        Immagine i = immagini.get(count++);
                        File target = new File(request.getServletContext().getRealPath("") + (PATH + opera.getTitolo() + File.separatorChar + File.separatorChar + item.getName()).replace(" ", "_"));
                        if (dati.addImmagine(i, opera.getId())) {
                            item.write(target);
                            i = dati.getImmagineSingola(i.getFile());
                            action_notifica(id, i.getId());
                        }
                    }
                }
            } catch (FileUploadException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
            } catch (SQLException | NumberFormatException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
            }
            return true;
        } else {
            data.put("risultato", "ERRORE NELL'INSERIMENTO");
            return false;
        }
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
     * @throws org.apache.commons.fileupload.FileUploadException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException, SQLException, FileUploadException, NullPointerException, Exception {
        
        //istanzio i DAO necessari  e la mappa
        Map data = new HashMap();
        UtenteDAOImplements u = new UtenteDAOImplements();
        OperaDAOImplements o = new OperaDAOImplements();
        //controllo della sessione con connessione al database
        HttpSession s = SecurityLayer.checkSession(request);
        Database.connect();
        //controllo che l'utente ia un acquisitore
        String email = u.getUtenteSingolo((int) s.getAttribute("userid")).getEmail();
        if (!isNull(s) && ((String) s.getAttribute("username")).equals(email + "staffacquisizione")) {

            Opera opera = o.getOperaUtente((int) s.getAttribute("userid"));
            if (request.getMethod().equalsIgnoreCase("post") && action_upload(request, response, data, (int) s.getAttribute("userid"), opera)) {
                data.put("risultato", "eseguito con successo");
            }
            Database.close();
            data.put("opera_associata", opera);
            data.put("staff", "Acquisizioni");
            data.put("active", "1");
            FreeMarker.process("back_acquisizioni.html", data, response, getServletContext());
        } else {
            action_error(request, response);
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
            Logger.getLogger(Acquisizione.class.getName()).log(Level.SEVERE, null, ex);
            action_error(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Acquisizione.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Acquisizione.class.getName()).log(Level.SEVERE, null, ex);
            action_error(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Acquisizione.class.getName()).log(Level.SEVERE, null, ex);
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
