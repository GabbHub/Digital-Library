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
import model.DAO.InterventiDAOImplements;
import model.DAO.OperaDAOImplements;
import model.DAO.UtenteDAOImplements;
import model.transfer_objects.Opera;
import model.DAO.Database;
import view.FreeMarker;
import controller.utility.SecurityLayer;

/**
 * servlet per il pannello dell'amministratore
 *
 * @author SS
 */
public class PannelloAmministratore extends HttpServlet {

    ////path per la cartella dei file delle opere
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
            message = "Si è verificato un errore.Ci scusiamo per il disagio";
        }
        data.put("errore", message);
        FreeMarker.process("404error.html", data, response, getServletContext());
    }

    /**
     * metodo per la modifica di un Opera
     *
     * @param request   servlet request
     * @see Opera
     * @see OperaDAOImplements
     */
    private void modificaOpera(HttpServletRequest request) {
        //inizializzo mappa e DAO
        HashMap update = new HashMap();
        OperaDAOImplements o = new OperaDAOImplements();
        //inserisco i nuovi valori nella mappa
        update.put("Autore", request.getParameter("autore"));
        update.put("Titolo", request.getParameter("titolo"));
        update.put("Anno", request.getParameter("anno"));
        update.put("Lingua", request.getParameter("lingua"));
        update.put("numero_pagine", Integer.parseInt(request.getParameter("numero pagine")));
        //prendo l'opera vecchia che mi servirà per modificare il nome della cartella
        //che contiene i file delle immagini dell'opera
        Opera vecchia_opera = o.getOperaSingola(Integer.parseInt(request.getParameter("id")));
        if (o.updateOpera(update, "ID=" + Integer.parseInt(request.getParameter("id")))) {
            //se l'update va a buon fine prendo la vecchia cartella, se questa esiste
            //la rinomno e rimpiazzo il nuovo nome nelle stringhe dei file
            //delle immagini nel database
            File vecchia_cartella = new File(request.getServletContext().getRealPath("").replace("\\", "\\\\") + PATH + vecchia_opera.getTitolo().replace(" ", "_"));
            if (vecchia_cartella.exists()) {
                File nuova_cartella = new File(request.getServletContext().getRealPath("").replace("\\", "\\\\") + PATH + request.getParameter("titolo").replace(" ", "_"));
                if (vecchia_cartella.renameTo(nuova_cartella)) {
                    ImmagineDAOImplements img = new ImmagineDAOImplements();
                    img.ReplaceFieldImmagine("file", vecchia_opera.getTitolo().replace(" ", "_"), request.getParameter("titolo").replace(" ", "_"));
                }
            }
        }
    }

    /**
     * metodo per l'inserimento di un Opera
     *
     * @param request
     * @see Opera
     * @see OperaDAOImplements
     */
    private void inserisciOpera(HttpServletRequest request) {
        //inizializzo DAO e creo l'oggetto opera
        OperaDAOImplements o = new OperaDAOImplements();
        Opera op = new Opera(
                request.getParameter("autore"),
                request.getParameter("anno"),
                request.getParameter("lingua"),
                request.getParameter("titolo"),
                Integer.parseInt(request.getParameter("numero_pagine")),
                false,
                false
        );
        //se l'inserimento va a buon fine creo la cartella col relativo nome
        //che conterrà i file delle immagini dell'opera
        if (o.addOpera(op)) {
            String path = request.getServletContext().getRealPath("").replace("\\", "\\\\") + PATH + op.getTitolo().replace(" ", "_");
            File f = new File(path);
            f.mkdir();
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
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException, SQLException {
        //inizializzo mappa e DAO
        Map data = new HashMap();
        UtenteDAOImplements u = new UtenteDAOImplements();
        OperaDAOImplements o = new OperaDAOImplements();
        InterventiDAOImplements i = new InterventiDAOImplements();
        //controllo sessione e connetto il database
        HttpSession s = SecurityLayer.checkSession(request);
        Database.connect();
        //controllo che l'utente sia l'amministratore
        String email = u.getUtenteSingolo((int) s.getAttribute("userid")).getEmail();
        if (!isNull(s) && ((String) s.getAttribute("username")).equals(email + "staffamministratore")) {
            //se è stata richiesta la pubblicazione delle immagini di un opera
            if (!isNull(request.getParameter("pubblica_immagini"))) {
                data.put("Pubblicazione_immagini", "1");
                //elimino gli interventi che non riguardano la trascrizione 
                i.deleteInterventiNonTrascrizione(Integer.parseInt(request.getParameter("pubblica_immagini")));
                //faccio l'update dell'opera
                o.updateOpera(data, "id=" + Integer.parseInt(request.getParameter("pubblica_immagini")));
            } //se è stata richiesta la pubblicazione della trascrizione di un opera
            else if (!isNull(request.getParameter("pubblica_trascrizione"))) {
                data.put("Pubblicazione_trascrizione", "1");
                //elimino tutti gli interventi sull'opera
                i.deleteInterventiOpera(Integer.parseInt(request.getParameter("pubblica_trascrizione")));
                //faccio l'update dell'opera
                o.updateOpera(data, "id=" + Integer.parseInt(request.getParameter("pubblica_trascrizione")));
            }

            data.put("staff", "Amministrazione");
            data.put("active", "1");

            //se non è stata richiesta la modifica dell'opera prendo la lista delle opere
            //con numero trascrizioni e immagini validate e metto nella mappa
            if (isNull(request.getQueryString()) && isNull(request.getParameter("modificaopera"))) {
                ArrayList<Opera> listaopere = o.getOpere();
                for (Opera op : listaopere) {
                    op.setNumero_immagini_validate(Database.countRecord("immagini_digitali", "opera=" + op.getId() + " && validazione=1"));
                    op.setNumero_trascrizioni_validate(o.contaTrascrizionivalidateOpera(op));
                }
                data.put("listaopere", listaopere);
                Database.close();
                FreeMarker.process("back_index.html", data, response, getServletContext());
            }//se è stata richiesta la modifica dell'opera prendo la lista delle opere
            //con numero trascrizioni e immagini validate insieme ai dat dell'opera
            //da modificare e metto nella mappa
            else if (isNull(request.getQueryString()) && !isNull(request.getParameter("modificaopera"))) {
                Opera op = o.getOperaSingola(request.getParameter("modificaopera"));
                op.setNumero_immagini_validate(Database.countRecord("immagini_digitali", "opera=" + op.getId() + " && validazione=1"));
                ArrayList<Opera> listaopere = o.getOpere();
                for (Opera ope : listaopere) {
                    ope.setNumero_immagini_validate(Database.countRecord("immagini_digitali", "opera=" + ope.getId() + " && validazione=1"));
                    ope.setNumero_trascrizioni_validate(o.contaTrascrizionivalidateOpera(ope));
                }
                data.put("listaopere", listaopere);
                Database.close();
                data.put("modificaopera", op);
                FreeMarker.process("back_index.html", data, response, getServletContext());
            } //altrimenti bisogna effettuare la modifica dell'opera o l'inserimento di una nuova
            else //se l'id non è nullo allora modifichiamo l'opera
            if (!isNull(request.getParameter("id"))) {

                modificaOpera(request);
                //prendo la lista delle opere e vi aggiungo il nomero di immagini
                //e trascrizioni validate per ogni opera
                ArrayList<Opera> listaopere = o.getOpere();
                for (Opera op : listaopere) {
                    op.setNumero_immagini_validate(Database.countRecord("immagini_digitali", "opera=" + op.getId() + " && validazione=1"));
                    op.setNumero_trascrizioni_validate(o.contaTrascrizionivalidateOpera(op));
                }
                //aggiungo il numero di persone associate per ogni opera
                for (Opera op : listaopere) {
                    op.setPersone_associate(i.contaInterventiOpera(op.getId()));
                }

                //chiudo il database inserisco nella mappa e stampo a video
                Database.close();
                data.put("listaopere", listaopere);
                FreeMarker.process("back_index.html", data, response, getServletContext());
            } //altrimenti se l'id  è nullo inseriamo una nuova opera
            else {
                inserisciOpera(request);
                //prendo la lista delle opere e vi aggiungo il nomero di immagini
                //e trascrizioni validate per ogni opera
                ArrayList<Opera> listaopere = o.getOpere();
                for (Opera ope : listaopere) {
                    ope.setNumero_immagini_validate(Database.countRecord("immagini_digitali", "opera=" + ope.getId() + " && validazione=1"));
                    ope.setNumero_trascrizioni_validate(o.contaTrascrizionivalidateOpera(ope));
                }
                //chiudo il database inserisco nella mappa e stampo a video
                Database.close();
                data.put("listaopere", listaopere);
                FreeMarker.process("back_index.html", data, response, getServletContext());

            }
        } else {
            Database.close();
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
            Logger.getLogger(PannelloAmministratore.class
                    .getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PannelloAmministratore.class
                    .getName()).log(Level.SEVERE, null, ex);
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
