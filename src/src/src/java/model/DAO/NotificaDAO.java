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
package model.DAO;

import java.util.ArrayList;
import model.transfer_objects.Immagine;
import model.transfer_objects.Notifica;
import model.transfer_objects.Trascrizione;
import model.transfer_objects.Utente;

/**
 *Interfaccia dei metodi per l'oggetto Notifica. 
 * 
 * @author SS
 */
public interface NotificaDAO {
    
    /** ritorna la lista delle notifiche relative alle trascrizioni di un utente
     * @param utente trascrittori e revisori trascrittori
     * @see Notifica
     * @see Utente
     * @return  */
    public ArrayList<Notifica> getNotificheTrascrizioniUtente(Utente utente);
    /** ritorna la lista delle notifiche relative alle trascrizioni di un utente
     * @param utente trascrittori e revisori trascrittori
     * @see Notifica
     * @return  */
    public ArrayList<Notifica> getNotificheTrascrizioniUtente(int utente);
    /** ritorna la lista delle notifiche relative alle immagini di un utente
     * @param utente acquisitore e revisore acquisizioni
     * @see Notifica
     * @see Utente
     * @return  */
    public ArrayList<Notifica> getNotificheImmaginiUtente(Utente utente);
    /** ritorna la lista delle notifiche relative alle immagini di un utente
     * @param utente acquisitore e revisore acquisizioni
     * @see Notifica
     * @return  */
    public ArrayList<Notifica> getNotificheImmaginiUtente(int utente);
    /** ritorna la lista delle notifiche relative ad un immagine
     * @param immagine      oggetto Immagine con i dati dell'immagine
     * @see Notifica
     * @see Immagine
     * @return  */
    public ArrayList<Notifica> getNotificheImmagine(Immagine immagine);
    /** ritorna la lista delle notifiche relative ad un immagine
     * @param immagine      id dell'immagine
     * @see Notifica
     * @return  */
    public ArrayList<Notifica> getNotificheImmagine (int immagine);
    /** ritorna l'id relativo al gruppo dell'utente che per ultimo ha inserito una
     * notifica relativa all'immagine
     * @param immagine      id dell'immagine
     * @see Notifica
     * @return  */
    public int getGruppoUltimaNotificaImmagine (int immagine);
    /** ritorna l'id relativo al gruppo dell'utente che per ultimo ha inserito una
     * notifica relativa all'immagine
     * @param immagine      oggetto Immagine con i dati dell'immagine
     * @see Notifica
     * @see Immagine
     * @return  */
    public int getGruppoUltimaNotificaImmagine (Immagine immagine);
    /** ritorna la lista delle notifiche di una determinata trascrizione
     * @param trascrizione     oggetto Trascrizione con i dati della trascrizione
     * @see Notifica
     * @see Trascrizione
     * @return  */
    public ArrayList<Notifica> getNotificheTrascrizione(Trascrizione trascrizione);
    /** ritorna la lista delle notifiche di una determinata trascrizione
     * @param trascrizione     id della trascrizione
     * @see Notifica
     * @return  */
    public ArrayList<Notifica> getNotificheTrascrizione(int trascrizione);
    /** ritorna l'id relativo al gruppo dell'utente che per ultimo ha inserito una
     * notifica relativa alla trascrizione
     * @param trascrizione      id della trascrizione
     * @see Notifica
     * @return  */
    public int getGruppUltimaNotificaTrascrizione(int trascrizione);
    /** ritorna l'id relativo al gruppo dell'utente che per ultimo ha inserito una
     * notifica relativa alla trascrizione
     * @param trascrizione      oggetto Trascrizione con i dati della trascrizione
     * @see Notifica
     * @see Trascrizione
     * @return  */
    public int getGruppUltimaNotificaTrascrizione(Trascrizione trascrizione);
    /** effettua il delete di una notifica dalla tabella delle notifiche delle
     * immagini nel database tramite l'oggetto immagine, ritorna true se la 
     * cancellazione è andata a buon fine, false altrimenti
     * @param immagine      oggetto Immagine con i dati dell'immagine
     * @see Notifica
     * @see Immagine
     * @return  */
    public boolean deleteNotifica(Immagine immagine);
    /** effettua il delete di una notifica dalla tabella specificata nel database 
     * tramite l'id dell'oggetto,  ritorna true se la cancellazione è andata 
     * a buon fine, false altrimenti
     * @param table         tabella per distinguere notifiche immagini o trascrizioni
     * @param oggetto       id dell'immagine o della trascrizione da cancellare
     * @see Notifica
     * @return  */
    public boolean deleteNotifica(String table,int oggetto);
    /** effettua il delete delle notifiche di un utente dalla tabella specificata
     *  ritorna true se la cancellazione è andata a buon fine, false altrimenti
     * @param table         tabella per distinguere notifiche immagini o trascrizioni
     * @param u             oggetto Utente con i dati dell'utente associato alla trascrizione
     * @see Notifica
     * @see Utente
     * @return  */
    public boolean deleteNotificheUtente(String table, Utente u);
    /** effettua il delete delle notifiche di un utente dalla tabella specificata,
     *  ritorna true se la cancellazione è andata a buon fine, false altrimenti
     * @param table         tabella per distinguere notifiche immagini o trascrizioni
     * @param u             id dell'utente associato alla trascrizione
     * @see Notifica
     * @return  */
    public boolean deleteNotificheUtente(String table, int u);
    /** effettua il delete di una notifica dalla tabella delle notifiche delle
     * trascrizioni nel database tramite l'oggetto trascrizione, ritorna true se 
     * la cancellazione è andata a buon fine, false altrimenti
     * @param trascrizione      oggetto trascrizione con i dati della trascrizione
     * @see Notifica
     * @return  */
    public boolean deleteNotifica(Trascrizione trascrizione);
    /** effettua la insert della notifica nel DB alla tabella specificata, ritorna
     * true se l'inserimento è andata a buon fine, false altrimenti
     * @param table         tabella per distinguere notifiche immagini o trascrizioni
     * @param notifica      oggetto Notifica con i dati della notifica
     * @see Notifica
     * @return  */
    public boolean addNotifica(String table,Notifica notifica);

}
