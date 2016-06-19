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
import java.util.Map;
import model.transfer_objects.Utente;

/**
 *Interfaccia dei metodi per l'oggetto Utente
 * 
 * @author SS
 */
public interface UtenteDAO {
    /** ritorna la lista degli utenti che fanno parte dello staff 
     * @see Utente
     * @return  */
    public ArrayList<Utente> getStaff();
    /** ritorna la lista degli utenti che fanno parte di un determinato gruppo
     * @param gruppo id del gruppo
     * @see Utente
     * @return  */
    public ArrayList<Utente> getGruppoUtenza(int gruppo);
    /** ritorna un utente sapendo il suo id
     * @param id  id dell'utente
     * @see Utente
     * @return  */
    public Utente getUtenteSingolo(int id);
    /** ritorna un utente sapendo la sua email e la sua password
     * @param email      email dell'utente
     * @param password   password dell'utente
     * @see Utente
     * @return  */
    public Utente getUtentesingolo(String email, String password);
    /** effettua l'update della tabella degli utenti nel database tramite
     *  una mappa e una stringa di condizione, se l'update va a buon fine ritorna
     * true, false altrimenti
     * @param data          mappa dei dati
     * @param condizione    stringa di condizione della query
     * @see Utente
     * @return  */
    public boolean updateUtente(Map<String,Object> data,String condizione);
    /** effettua il delete di un Utente dalla tabella degli utenti nel 
     * database tramite un oggetto Utente,ritorna true se la cancellazione
     *  è andata a buon fine, false altrimenti
     * @param utente    oggetto Utente contenente i dati dell'utente
     * @see Utente
     * @return  */
    public boolean deleteUtente(Utente utente);
    /** effettua il delete di un Utente dalla tabella degli utenti nel 
     * database tramite l'id dell'utente,ritorna true se la cancellazione
     *  è andata a buon fine, false altrimenti
     * @param id        id dell'utente
     * @see Utente
     * @return  */
    public boolean deleteUtente(int id);
    /** effettua il delete di un Utente dalla tabella degli utenti nel 
     * database tramite l'email di un utente,ritorna true se la cancellazione
     *  è andata a buon fine, false altrimenti
     * @param email     email dell'utente
     * @see Utente
     * @return  */
    public boolean deleteUtente(String email);
    /** aggiunge un record alla tabella degli utenti tramite un oggetto di 
     * tipo Utente,  ritorna true se l'inserimento è andato a buon fine,
     * false altrimenti
     * @param u       oggetto Utente coi dati dell'utente da inserire
     * @see Utente
     * @return  */
    public boolean addUtente(Utente u);
    
}
