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
import java.util.List;
import java.util.Map;
import model.transfer_objects.Immagine;
import model.transfer_objects.Opera;
import model.transfer_objects.Trascrizione;
import model.transfer_objects.Utente;

/**
 *Interfaccia dei metodi per l'oggetto Trascrizione
 * 
 * @author SS
 */
public interface TrascrizioneDAO {
    
     /** ritorna la lista delle trascrizioni in revisione sapendo l'opera
     * @param opera            oggetto Opera con i dati dell'opera
     * @see Trascrizione
     * @return  */
    public ArrayList<Trascrizione> getTrascrizioniInRevisioneOpera(Opera opera);
    /** ritorna la lista delle trascrizioni in revisione sapendo l'opera
     * @param opera            id dell'opera
     * @see Trascrizione
     * @return  */
    public ArrayList<Trascrizione> getTrascrizioniInRevisioneOpera(int opera);
    /** ritorna la lista delle trascrizioni in revisione sapendo l'opera
     * @param opera         stringa del titolo dell'opera
     * @see Trascrizione
     * @return  */
    public ArrayList<Trascrizione> getTrascrizioniInRevisioneOpera(String opera);
    /** ritorna la lista delle trascrizioni in revisione di un utente
     * @param utente        oggetto Utente con i dati dell'utente
     * @see Trascrizione
     * @return  */
    public ArrayList<Trascrizione> getTrascrizioniInRevisioneUtente(Utente utente);
    /** ritorna la lista delle trascrizioni in revisione di un utente
     * @param utente        id dell'utente
     * @see Trascrizione
     * @return  */
    public ArrayList<Trascrizione> getTrascrizioniInRevisioneUtente(int utente);
    /** ritorna la trascrizione associata ad un immagine tramite un oggetto Immagine
     * @param immagine      oggetto Immagine con i dati dell'immagine
     * @see Trascrizione
     * @return  */
    public Trascrizione getTrascrizioneImmagine(Immagine immagine);
     /** associa ad ogni immagine, il trascrittore associato alla sua trascrizione
     * @param listaimmagini     lista delle immagini da cui prendere le trascrizioni
     * @see Trascrizione
     * @see Immagine
     */
    public void setTrascrittoreAssociato(List<Immagine> listaimmagini);
    /** ritorna la trascrizione associata ad un immagine tramite un oggetto opera
     * e il numero della pagina
     * @param opera             oggetto Opera con i dati dell'opera
     * @param numero_pagina     numero della pagina a cui associare la trascrizione
     * @see Trascrizione
     * @return  */
    public Trascrizione getTrascrizioneImmagine(Opera opera, int numero_pagina);
    /** ritorna la trascrizione associata ad un immagine tramite l'id di un opera
     * e il numero della pagina
     * @param opera             id dell'opera
     * @param numero_pagina     numero della pagina a cui associare la trascrizione
     * @see Trascrizione
     * @return  */
    public Trascrizione getTrascrizioneImmagine(int opera, int numero_pagina);
    /** ritorna la trascrizione associata ad un immagine tramite il titolo 
     * di un opera e il numero della pagina
     * @param opera             stringa contenente il titolo dell'opera
     * @param numero_pagina     numero della pagina a cui associare la trascrizione
     * @see Trascrizione
     * @return  */
    public Trascrizione getTrascrizioneImmagine(String opera, int numero_pagina);
    /** ritorna la trascrizione associata ad un immagine tramite l'id di quest'ultima
     * @param immagine       id dell'immagine
     * @see Trascrizione
     * @return  */
    public Trascrizione getTrascrizioneImmagine(int immagine);
    /** effettua l'update della tabella delle trascrizioni nel database tramite
     *  una mappa e una stringa di condizione, se l'update va a buon fine ritorna
     * true, false altrimenti
     * @param data          mappa con i dati
     * @param condizione    stringa con la condizione della query
     * @see Trascrizione
     * @return  */
    public boolean updateTrascrizione(Map<String,Object> data,String condizione);
    /** effettua il delete di una trascrizione dalla tabella delle trascrizioni nel 
     * database tramite l'id della trascrizione,ritorna true se la cancellazione
     *  è andata a buon fine, false altrimenti
     * @param id            id della trascrizione
     * @see Trascrizione
     * @return  */
    public boolean deleteTrascrizione(int id);
    /** effettua il delete di una trascrizione dalla tabella delle trascrizioni nel 
     * database tramite l'oggetto trascrizione,ritorna true se la cancellazione
     *  è andata a buon fine, false altrimenti
     * @param trascrizione      oggetto Trascrizione con i dati della trascrizione
     * @see Trascrizione
     * @return  */
    public boolean deleteTrascrizione(Trascrizione trascrizione);
    /** aggiunge un record alla tabella delle trascrizioni tramite un oggetto di 
     * tipo Trascrizione,  ritorna true se l'inserimento è andato a buon fine,
     * false altrimenti
     * @param trascrizione      oggetto Trascrizione con i dati della trascrizione
     * @see Trascrizione
     * @return  */
    public boolean addTrascrizione(Trascrizione trascrizione);
    
}
