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

import java.sql.SQLException;
import java.util.Map;

/**
 *Interfaccia dei metodi per la gestione degli interventi dello staff sulle opere
 * 
 * @author SS
 */
public interface InterventiDAO {
    /** effettua il delete di interventi che non riguradano la trascrizione ( e
     * dunque acquisizione e revisione acquisizione) ritorna true se la cancellazione
     *  è andata a buon fine, false altrimenti
     * @param opera     id dell'opera
     * @return          true se la cancellazione è andata a buon fine, false altrimenti
     * @throws java.sql.SQLException  */
    public boolean deleteInterventiNonTrascrizione(int opera) throws SQLException;
    /** effettua il delete degli interventi su una determinata opera, ritorna 
     * true se la cancellazione è andata a buon fine, false altrimenti
     * @param opera     id dell'opera
     * @return          true se la cancellazione è andata a buon fine, false altrimenti
     * @throws java.sql.SQLException  */
    public boolean deleteInterventiOpera(int opera) throws SQLException;
    /** effettua il delete degli interventi di un determinato utente, ritorna 
     * true se la cancellazione è andata a buon fine, false altrimenti
     * @param utente        id dell'utente
     * @return              true se la cancellazione è andata a buon fine, false altrimenti
     * @throws java.sql.SQLException  */
    public boolean deleteInterventiUtente(int utente) throws SQLException;
     /** ritorna il titolo dell'opera a cui un Utente sta effettuando interventi
     * @param utente        id dell'utente
     * @return              stringa contente il titolo dell'opera associata all'utente
     * @throws java.sql.SQLException  */
    public String getTitoloOperaInterventoUtente(int utente)throws SQLException;
     /** Ritorna il numero di utenti che stanno eseguendo interventi sull'opera
     * @param opera         id dell'opera
     * @return              il numero di persone che stanno eseguendo interventi sull'opera
     * @throws java.sql.SQLException  */
    public int contaInterventiOpera(int opera) throws SQLException;
   /** aggiunge la insert di un intervento al database attraverso una 
     * mappa coi valori dei campi, ritorna true se va a buon fine, false altrimenti
     * @param map           mappa con i dati
     * @return              rue se va a buon fine, false altrimenti
     * @throws java.sql.SQLException  */
    public boolean addIntervento(Map map) throws SQLException;
    
}
