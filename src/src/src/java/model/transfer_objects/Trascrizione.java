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
 *  Classe per l'oggetto Trascrizione
 * 
 * @author SS
 */
public class Trascrizione {
    
    //campi dell'oggetto
    private int id;
    private String file;
    private boolean validazione;
    private int immagine;
    private Immagine img;
    private int ultima_notifica; 
    
       /**
     * Constructor. costruttore generico per l'oggetto Trascrizione
     *
     * @param id            (required) id della Trascrizione
     * @param file          (required) file della Trascrizione
     * @param validazione   (required) serve per sapere se la trascrizione è validata
     * @param immagine      (required) immagine a cui è associata la trascrizione
     */
    
    public Trascrizione(int id, String file, boolean validazione,int immagine) {
        this.id = id;
        this.file = file;
        this.validazione = validazione;    
        this.immagine = immagine;
    }
    
       /**
     * Constructor. costruttore per l'oggetto Trascrizione necessario per le Trascrizioni in revisione
     *
     * @param id                (required) id della Trascrizione
     * @param file              (required) file della Trascrizione
     * @param validazione       (required) serve per sapere se la trascrizione è validata
     * @param immagine          (required) immagine a cui è associata la trascrizione
     * @param ultima_notifica   (required) id dell'ultima notifica della trascrizione
     * @param img               (required) oggetto Immagine associato alla Trascrizione
     */
    
    public Trascrizione(int id, String file, boolean validazione,int immagine,int ultima_notifica,Immagine img) {
        this.id = id;
        this.file = file;
        this.validazione = validazione;    
        this.immagine = immagine;
        this.img=img;
        this.ultima_notifica=ultima_notifica;
    }
    
   /**
     * Constructor. costruttore per l'oggetto Trascrizione quando si inizia una nuova trascrizione
     *
     * @param file              (required) file della Trascrizione
     * @param validazione       (required) serve per sapere se la trascrizione è validata
     * @param immagine          (required) immagine a cui è associata la trascrizione
     */
        
    public Trascrizione( String file, boolean validazione, int immagine) {
        this.file = file;
        this.validazione = validazione;    
        this.immagine = immagine;
    }    
    /** Ritorna l'id dell'immagine associata alla trascrizione
     * @return  immagine */
    public int getImmagine() {
        return immagine;
    }
    /** imposta l'id dell'ultima notifica associata alla trascrizione
     * @param ultima_notifica */
    public void setUltima_notifica(int ultima_notifica) {
        this.ultima_notifica = ultima_notifica;
    }
    /** Ritorna l'id dell'ultima notifica associata alla trascrizione
     * @return  ultima_notifica */
    public int getUltima_notifica() {
        return ultima_notifica;
    }
    /** imposta l'id dell'immagine associata alla trascrizione
     * @param immagine */ 
    public void setImmagine(int immagine) {
        this.immagine = immagine;
    }
    /** Ritorna l'id della trascrizione
     * @return  id */
    public int getId() {
        return id;
    }
    /** Ritorna la stringa del file associato alla trascrizione
     * @return  immagine */
    public String getFile() {
        return file;
    }
    /** Ritorna true se la trascrizione è validata, false altrimenti
     * @return  validazione */
    public boolean isValidazione() {
        return validazione;
    }
    /** imposta l'id della trascrizione
     * @param id */ 
    public void setId(int id) {
        this.id = id;
    }
    /** imposta la stringa del file associato alla trascrizione
     * @param file */
    public void setFile(String file) {
        this.file = file;
    }
    /** imposta se la trascrizione è stata validata
     * @param validazione */ 
    public void setValidazione(boolean validazione) {
        this.validazione = validazione;
    }
    /** imposta l'oggetto immagine associato alla trascrizione
     * @param img */ 
    public void setImg(Immagine img) {
        this.img = img;
    }
    /** Ritorna l'oggetto Immagine associato alla trascrizione
     * @return  immagine */
    public Immagine getImg() {
        return img;
    }
    
}
