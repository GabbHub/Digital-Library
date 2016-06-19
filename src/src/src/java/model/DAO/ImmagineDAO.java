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
import model.transfer_objects.Immagine;
import model.transfer_objects.Opera;

/**
 * Interfaccia dei metodi per l'oggetto Immagine. 
 * 
 * @author SS
 */
public interface ImmagineDAO {
    /** ritorna la lista delle immagini validate di un opera
     * @param opera         oggetto Opera con i dati dell'opera
     * @see Immagine
     * @see Opera
     * @return  */
    public ArrayList<Immagine> getImmaginiOperaValidate(Opera opera);
     /** ritorna la lista delle immagini validate di un opera
     * @param opera         id dell'opera
     * @see Immagine
     * @return  */
    public ArrayList<Immagine> getImmaginiOperaValidate(int opera);
     /** ritorna la lista delle immagini validate di un opera
     * @param opera         stringa contenente il titolo dell'opera
     * @see Immagine
     * @return  */
    public ArrayList<Immagine> getImmaginiOperaValidate(String opera);
     /** ritorna la lista delle immagini di una determinata opera la cui 
     * trascrizioneassociata è in revisione
     * @param opera         id dell'opera
     * @see Immagine
     * @return  */
    public ArrayList<Immagine> getImmaginiOperaInRevisione(int opera);
    /** ritorna la lista delle immagini di una determinata opera la cui 
     * trascrizioneassociata è in revisione
     * @param opera         oggetto Opera con i dati dell'opera
     * @see Immagine
     * @see Opera
     * @return  */
    public ArrayList<Immagine> getImmaginiOperaInRevisione(Opera opera);
    /** ritorna la lista delle immagini di una determinata opera la cui 
     * trascrizioneassociata è in revisione
     * @param opera         stringa contenente il titolo dell'opera
     * @see Immagine
     * @return  */
    public ArrayList<Immagine> getImmaginiOperaInRevisione(String opera);
    /** ritorna la lista delle immagini di una determinata opera che non sono
     *  ancora state validate
     * @param opera         oggetto Opera con i dati dell'opera
     * @see Immagine
     * @see Opera
     * @return  */
    public ArrayList<Immagine> getImmaginiOperaNonValidate(Opera opera);
    /** ritorna la lista delle immagini di una determinata opera che non sono
     *  ancora state validate
     * @param opera         id dell'opera
     * @see Immagine
     * @return  */
    public ArrayList<Immagine> getImmaginiOperaNonValidate(int opera);
    /** ritorna la lista delle immagini di una determinata opera che non sono
     *  ancora state validate
     * @param opera         stringa contenente il titolo dell'opera
     * @see Immagine
     * @return  */
    public ArrayList<Immagine> getImmaginiOperaNonValidate(String opera);
    /** ritorna la lista delle immagini di una determinata opera che sono state 
     *  caricate da un determinato acquisitore
     * @param opera         oggetto Opera con i dati dell'opera
     * @param acquisitore   id dell'acquisitore associato all'opera
     * @see Immagine
     * @see Opera
     * @return  */
    public ArrayList<Immagine> getImmaginiOperaAcquisitore(Opera opera, int acquisitore);
     /** ritorna la lista delle immagini di una determinata opera che sono state 
     *  caricate da un determinato acquisitore
     * @param opera         id dell'opera
     * @param acquisitore   id dell'acquisitore associato all'opera
     * @see Immagine
     * @return  */
    public ArrayList<Immagine> getImmaginiOperaAcquisitore(int opera, int acquisitore);
    /** ritorna i dati di un'immagine di una determinata opera selezionata 
     *  tramite numero di pagina
     * @param opera             oggetto Opera con i dati dell'opera
     * @param numero_pagina     intero contenente il numero di pagina dell'immagine
     * @see Immagine
     * @see Opera
     * @return  */
    public Immagine getImmagineSingola(Opera opera, int numero_pagina);
    /** ritorna i dati di un'immagine di una determinata opera selezionata 
     *  tramite numero di pagina
     * @param opera             id dell'opera
     * @param numero_pagina     intero contenente il numero di pagina dell'immagine
     * @see Immagine
     * @return  */
    public Immagine getImmagineSingola(int opera, int numero_pagina);
    /** ritorna i dati di un'immagine di una determinata opera selezionata 
     *  tramite numero di pagina
     * @param opera             stringa contenente il titolo dell'opera
     * @param numero_pagina     intero contenente il numero di pagina dell'immagine
     * @see Immagine
     * @return  */
    public Immagine getImmagineSingola(String opera, int numero_pagina);
       /** ritorna i dati di un'immagine di una determinata opera selezionata 
      *  tramite il suo id
     * @param immagine      id dell'immagine
     * @see Immagine
     * @return  */
    public Immagine getImmagineSingola(int immagine);
    /** ritorna i dati di un'immagine di una determinata opera selezionata 
     *  tramite la stringa del File associato
     * @param file      Stringa contente il path del file dell'immagine
     * @see Immagine
     * @return  */
    public Immagine getImmagineSingola(String file);
    /** effettua l'update della tabella delle immagini nel database tramite
     *  una mappa e una stringa di condizione,  ritorna true se l'update
     * va a buon fine, false altrimenti
     * @param data          mappa con i dati
     * @param condizione    stringa contente la condizione per la query
     * @see Immagine
     * @return  */
    public boolean updateImmagine(Map<String,Object> data,String condizione);
    /** effettua il delete di un immagine dalla tabella delle immagini nel 
     * database tramite un oggetto di tipo Immagine,ritorna true se la cancellazione
     * va a buon fine, false altrimenti
     * @param immagine      oggetto Immagine con i dati dell'immagine
     * @see Immagine
     * @return  */
    public boolean deleteImmagine(Immagine immagine);
    /** effettua il delete di un immagine dalla tabella delle immagini nel 
     * database tramite l'id dell'immagine, ritorna true se la cancellazione
     * va a buon fine, false altrimenti
     * @param id        id dell'immagine
     * @see Immagine
     * @return  */
    public boolean deleteImmagine(int id);
    /** aggiunge un record alla tabella delle immagini tramite un oggetto di 
     * tipo Immagine e l'id della relativa opera, ritorna true se l'inserimento
     * va a buon fine, false altrimenti
     * @param immagine      oggetto Immagine con i dati dell'immagine
     * @param opera         id dell'opera in cui inserire l'immagine
     * @see Immagine
     * @return  */
    public boolean addImmagine(Immagine immagine, int opera);
        
}
