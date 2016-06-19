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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import model.DAO.NotificaDAOImplements;
import model.DAO.OperaDAOImplements;
import model.DAO.TrascrizioneDAOImplements;
import model.DAO.UtenteDAOImplements;
import model.transfer_objects.Notifica;
import model.transfer_objects.Opera;
import model.transfer_objects.Utente;
import model.DAO.Database;
import view.FreeMarker;
import controller.utility.SecurityLayer;

/**
 * Servlet per la gestione dello staff
 *
 * @author SS
 */
public class Utenze extends HttpServlet {

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
     * 
     * associa  un utente dello staff ad un opera
     * 
     * @param opera     id dell'opera
     * @param utente    id dell'utente
     * @param gruppo    id del gruppo dell'utente
     * @throws SQLException 
     */
    private void associa_opera_utente(int opera, int utente, int gruppo) throws SQLException {
        InterventiDAOImplements i = new InterventiDAOImplements();
        HashMap data = new HashMap();
        switch (gruppo) {
            case 5: {
                data.put("operazione", "REVISIONE TRASCRIZIONI");
                break;
            }
            case 4: {
                data.put("operazione", "TRASCRIZIONE");
                break;
            }
            case 3: {
                data.put("operazione", "REVISIONE IMMAGINI");
                break;
            }
            case 2: {
                data.put("operazione", "ACQUISIZIONE");
                break;
            }
        }
        data.put("utente", utente);
        data.put("opera", opera);
        i.addIntervento(data);
    }
    
    /**
     * metodo per la pulizia degli interventi non conclusi di un utente dello staff
     * che è stato degradato
     * 
     * @param utente    id dell'utente
     * @param gruppo    id del gruppo dell'utente
     * @param request   servlet request
     * @throws SQLException 
     */
    private void ripulisci(int utente, int gruppo, HttpServletRequest request) throws SQLException {
        //iizializzo i DAO
        NotificaDAOImplements n = new NotificaDAOImplements();
        ImmagineDAOImplements i = new ImmagineDAOImplements();
        InterventiDAOImplements in = new InterventiDAOImplements();
        TrascrizioneDAOImplements t = new TrascrizioneDAOImplements();
        //controlo il gruppo dell'utente
        switch (gruppo) {
            //se è un revisore trascrizioni elimino tutte le sue notifiche e 
            //l'associazione all'opera
            case 5: {
                if (in.deleteInterventiUtente(utente)) {
                    n.deleteNotificheUtente("notifiche_trascrizione", utente);
                }
                break;
            }
            //se è un trascrittore elimino tutte le sue notifiche e le trascrizioni
            //iniziate e non validate insieme con l'associazione all'opera
            case 4: {
                if (in.deleteInterventiUtente(utente)) {
                    ArrayList<Notifica> lista = n.getNotificheTrascrizioniUtente(utente);
                    if (!lista.isEmpty()) {
                        lista.stream().forEach((no) -> {
                            n.deleteNotifica("notifiche_trascrizione", no.getOggetto());
                            t.deleteTrascrizione(no.getOggetto());
                        });
                    }
                }
                break;
            }
            //se è un revisore di immagini elimino tutte le sue notifiche e
            //l'associazione all'opera
            case 3: {
                if (in.deleteInterventiUtente(utente)) {
                    n.deleteNotificheUtente("notifiche_immagini", utente);
                }
                break;
            }
            //se è un acquisitore elimino l'associazione all'opera, le sue notifiche
            //i file legati alle immagini non ancora validate insieme con queste ultime
            case 2: {
                if (in.deleteInterventiUtente(utente)) {
                    ArrayList<Notifica> lista = n.getNotificheImmaginiUtente(utente);
                    if (!lista.isEmpty()) {
                        lista.stream().forEach((no) -> {
                            try {
                                n.deleteNotifica("notifiche_immagini", no.getOggetto());
                                Path path = Paths.get(request.getServletContext().getRealPath("") + (i.getImmagineSingola(no.getOggetto())).getFile());
                                Files.delete(path);
                                i.deleteImmagine(no.getOggetto());
                            } catch (IOException ex) {
                                Logger.getLogger(Utenze.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                }
            }
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
        Map data = new HashMap();
        InterventiDAOImplements i = new InterventiDAOImplements();
        UtenteDAOImplements u = new UtenteDAOImplements();
        OperaDAOImplements o = new OperaDAOImplements();
        HttpSession s = SecurityLayer.checkSession(request);
        Database.connect();
        String email = u.getUtenteSingolo((int) s.getAttribute("userid")).getEmail();
        if (!isNull(s) && ((String) s.getAttribute("username")).equals(email + "staffamministratore")) {
            data.put("staff", "Amministrazione");
            data.put("active", "2");
            if (!isNull(request.getParameter("opera")) && !isNull(request.getParameter("utente_associato")) && !isNull(request.getParameter("utente_associato_gruppo"))) {
                associa_opera_utente(Integer.parseInt(request.getParameter("opera")),
                        Integer.parseInt(request.getParameter("utente_associato")),
                        Integer.parseInt(request.getParameter("utente_associato_gruppo")));
            } else if (!isNull(request.getParameter("degrada"))) {
                HashMap update = new HashMap();
                update.put("gruppo", "1");
                int gruppo = u.getUtenteSingolo(Integer.parseInt(request.getParameter("degrada"))).getGruppo();
                if (u.updateUtente(update, "ID=" + Integer.parseInt(request.getParameter("degrada")))) {
                    ripulisci(Integer.parseInt(request.getParameter("degrada")), gruppo, request);
                }
            } else if (!isNull(request.getParameter("2"))) {
                HashMap update = new HashMap();
                update.put("gruppo", "2");
                u.updateUtente(update, "ID=" + Integer.parseInt(request.getParameter("2")));
            } else if (!isNull(request.getParameter("3"))) {
                HashMap update = new HashMap();
                update.put("gruppo", "3");
                u.updateUtente(update, "ID=" + Integer.parseInt(request.getParameter("3")));
            } else if (!isNull(request.getParameter("4"))) {
                HashMap update = new HashMap();
                update.put("gruppo", "4");
                u.updateUtente(update, "ID=" + Integer.parseInt(request.getParameter("4")));
            } else if (!isNull(request.getParameter("5"))) {
                HashMap update = new HashMap();
                update.put("gruppo", "5");
                u.updateUtente(update, "ID=" + Integer.parseInt(request.getParameter("5")));
            }

            ArrayList<Utente> listastaff = u.getStaff();
            ArrayList<Utente> listautenza = u.getGruppoUtenza(1);
            for (Utente staff : listastaff) {
                staff.setOpera_associata(i.getTitoloOperaInterventoUtente(staff.getId()));
            }
            if (!isNull(request.getParameter("associa opera"))) {
                ArrayList<Opera> listaopere;
                Utente ut = u.getUtenteSingolo(Integer.parseInt(request.getParameter("associa opera")));
                if (ut.getGruppo() == 2 || ut.getGruppo() == 3) {
                    listaopere = o.getOpereNonPubblicate();
                } else {
                    listaopere = o.getOpereNonTrascritte();
                }
                data.put("listaopere", listaopere);
                data.put("utentedaassociare", ut);
            }
            Database.close();
            data.put("listautenza", listautenza);
            data.put("listastaff", listastaff);
            FreeMarker.process("back_utenze.html", data, response, getServletContext());
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
            Logger.getLogger(Utenze.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Utenze.class.getName()).log(Level.SEVERE, null, ex);
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
