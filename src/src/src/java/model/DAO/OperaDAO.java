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
import model.transfer_objects.Opera;
import model.transfer_objects.Utente;

/**
 *Interfaccia dei metodi per l'oggetto Opera
 * 
 * @author SS
 */
public interface OperaDAO {
    
    /** ritorna la lista opere create
     * @see Opera
     * @return  */
    public ArrayList<Opera> getOpere();
    /** ritorna l'opera sulla quale un utente sta attualmente facendo interventi
     * @param u        oggetto Utente con i dati dell'utente
     * @see Opera
     * @see Utente
     * @return  */
    public Opera getOperaUtente(Utente u);
    /** ritorna l'opera sulla quale un utente sta attualmente facendo interventi
     * @param utente    id dell'utente
     * @see Opera       
     * @return  */
    public Opera getOperaUtente(int utente);
        /** ritorna il numero di opere pubblicate
     * @see Opera
     * @return  */
    public int contaOperePubblicate();
          /** ritorna il numero di opere della ricerca
     * @param parameter
     * @see Opera
     * @return  */
    public int contaOpereRicerca(String parameter);
    /** ritorna un sottoinsieme della lista delle opere pubblicate
     * @param parametro
     * @see Opera
     * @return  */
    public ArrayList<Opera> getOperePubblicate(int parametro);
    /** ritorna un sottoinsieme della lista delle opere pubblicate
     * @param parametro_ricerca
     * @param start
     * @see Opera
     * @return  */
    public ArrayList<Opera> getOperePubblicate(String parametro_ricerca,int start);
    /** ritorna la lista delle opere non pubblicate
     * @see Opera
     * @return  */
    public ArrayList<Opera> getOpereNonPubblicate();
    /** ritorna la lista delle opere non trascritte
     * @see Opera
     * @return  */
    public ArrayList<Opera> getOpereNonTrascritte();
    /** ritorna l'opera sapendo il suo titolo
     * @param titolo    Stringa contente il titolo dell'opera
     * @see Opera
     * @return  */
    public Opera getOperaSingola(String titolo);
    /** ritorna l'opera sapendo il suo id
     * @param id        id dell'opera
     * @see Opera
     * @return  */
    public Opera getOperaSingola(int id);
    /** ritorna il numero delle trascrizioni relative alle pagine dell'opera validate
     * @param opera     oggetto Opera con i dati dell'opera
     * @see Opera
     * @return  */
    public int contaTrascrizionivalidateOpera(Opera opera);
    /** ritorna il numero delle trascrizioni relative alle pagine dell'opera validate
     * @param opera     id dell'opera
     * @see Opera
     * @return  */
    public int contaTrascrizionivalidateOpera(int opera);
    /** effettua l'update della tabella delle opere nel database tramite
     *  una mappa e una stringa di condizione, se l'update va a buon fine ritorna
     * true, false altrimenti
     * @param data          mappa con i dati
     * @param condizione    stringa della condizione della query
     * @see Opera
     * @return  */
    public boolean updateOpera(Map<String,Object> data,String condizione);
    /** effettua il delete di un opera dalla tabella delle opere nel 
     * database tramite il titolo dell'opera,  ritorna true se la cancellazione
     *  è andata a buon fine, false altrimenti
     * @param titolo    stringa contenente il titolo dell'opera
     * @see Opera
     * @return  */
    public boolean deleteOpera(String titolo);
     /** effettua il delete di un opera dalla tabella delle opere nel 
     * database tramite il suo id,  ritorna true se la cancellazione
     *  è andata a buon fine, false altrimenti
     * @param id        id dell'opera
     * @see Opera
     * @return  */
    public boolean deleteOpera(int id);
     /** effettua il delete di un opera dalla tabella delle opere nel 
     * database tramite l'oggetto opera,  ritorna true se la cancellazione
     *  è andata a buon fine, false altrimenti
     * @param opera      oggetto Opera con i dati dell'opera
     * @see Opera
     * @return  */
    public boolean deleteOpera(Opera opera);
    /** aggiunge un record alla tabella delle opere tramite un oggetto di 
     * tipo Opera e l'id,  ritorna true se l'inserimento è andato a buon fine,
     * false altrimenti
     * @param opera      oggetto Opera con i dati dell'opera
     * @see Opera
     * @return  */
    public boolean addOpera(Opera opera);
    
}
