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
package model.transfer_objects;

/**
 * Classe per l'oggetto Notifica
 * 
 * @author SS
 */
public class Notifica {
    
    //campi dell'oggetto
    private int id;
    private int mittente_destinatario;
    private String notifica;
    private int oggetto;
    private String date;
    
   /**
     * Constructor. costruttore generico per l'oggetto Notifica
     * @param id                       (required) id dell'opera
     * @param mittente_destinatario    (required) id del mittente/destinatario
     * @param notifica                 (required) messaggio della notifica
     * @param oggetto                  (required) id dell'immagine o della trascrizione associata al messaggio
     * @param date                     (required) data e ora della notifica
     */
    
    public Notifica(int id, int mittente_destinatario, String notifica, int oggetto, String date) {
        this.id = id;
        this.mittente_destinatario = mittente_destinatario;
        this.notifica = notifica;
        this.oggetto = oggetto;
        this.date=date;
    }

    /**
     * Constructor. costruttore dell'oggetto Notifica per l'inserimento di una nuova notifica
     * @param mittente_destinatario    (required) id del mittente/destinatario
     * @param notifica                 (required) messaggio della notifica
     * @param oggetto                  (required) id dell'immagine o della trascrizione associata al messaggio
     */
    
    public Notifica(int mittente_destinatario, String notifica, int oggetto) {
        this.mittente_destinatario = mittente_destinatario;
        this.notifica = notifica;
        this.oggetto = oggetto;
    }
    /** imposta il mittente_destinatario della notifica
     * @param mittente_destinatario */
    public void setMittente_destinatario(int mittente_destinatario) {
        this.mittente_destinatario = mittente_destinatario;
    }
    /** imposta la data e l'ora della notifica
     * @param date */
    public void setDate(String date) {
        this.date = date;
    }
    /** Ritorna l'id del mittente/destinatario di una notifica
     * @return  mittente_destinatario */
    public int getMittente_destinatario() {
        return mittente_destinatario;
    }
    /** Ritorna la data e l'ora di una notifica
     * @return  date */
    public String getDate() {
        return date;
    }
    /** imposta l'id della notifica
     * @param id */
    public void setId(int id) {
        this.id = id;
    }
    /** imposta il messaggio della notifica
     * @param notifica */
    public void setNotifica(String notifica) {
        this.notifica = notifica;
    }
    /** imposta l'id dell'oggetto della notifica
     * @param oggetto */
    public void setOggetto(int oggetto) {
        this.oggetto = oggetto;
    }
     /** Ritorna l'id della notifica
     * @return  id */
    public int getId() {
        return id;
    }
     /** Ritorna il messaggio della notifica
     * @return  notifica */
    public String getNotifica() {
        return notifica;
    }
     /** Ritorna l'id dell'oggetto associato alla notifica
     * @return oggetto */
    public int getOggetto() {
        return oggetto;
    }
    
    
    
}
