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
 *Classe per l'oggetto Opera
 * 
 * @author SS
 */
public class Opera {

     //campi dell'oggetto
    private int id;
    private String autore;
    private String anno;
    private String lingua;
    private String titolo;
    private boolean pubblicazione_trascrizioni;
    private boolean pubblicazione_immagini;
    private Immagine copertina;
    private int numero_pagine;
    private int numero_immagini_validate;
    private int numero_trascrizioni_validate;
    private int persone_associate;
    
        /**
     * Constructor. costruttore generico per l'oggetto Opera
     * @param id                          (required) id dell'opera
     * @param autore                      (required) autore dell'opera
     * @param anno                        (required) anno dell'opera
     * @param lingua                      (required) lingua dell'opera
     * @param titolo                      (required) titolo dell'opera
     * @param numero_pagine               (required) numero pagine dell'opera
     * @param pubblicazione_trascrizioni  (required) serve per sapere se l'opera è trascritta
     * @param pubblicazione_immagini      (required) serve per sapere se l'opera è pubblicata
     */
    
    public Opera(int id, String autore, String anno, String lingua, String titolo,int numero_pagine, boolean pubblicazione_trascrizioni, boolean pubblicazione_immagini) {
        this.id=id;
        this.autore = autore;
        this.anno = anno;
        this.lingua = lingua;
        this.titolo = titolo;
        this.numero_pagine=numero_pagine;
        this.pubblicazione_trascrizioni = pubblicazione_trascrizioni;
        this.pubblicazione_immagini = pubblicazione_immagini;
    }
        
    /**
     * Constructor. costruttore per l'inserimento di una nuova Opera nel DB
     * @param autore                      (required) autore dell'opera
     * @param anno                        (required) anno dell'opera
     * @param lingua                      (required) lingua dell'opera
     * @param titolo                      (required) titolo dell'opera
     * @param numero_pagine               (required) numero pagine dell'opera
     * @param pubblicazione_trascrizioni  (required) serve per sapere se l'opera è trascritta
     * @param pubblicazione_immagini      (required) serve per sapere se l'opera è pubblicata
     */
    
    public Opera(String autore, String anno, String lingua, String titolo, int numero_pagine, boolean pubblicazione_trascrizioni, boolean pubblicazione_immagini) {
        this.autore = autore;
        this.anno = anno;
        this.lingua = lingua;
        this.titolo = titolo;
        this.numero_pagine=numero_pagine;
        this.pubblicazione_trascrizioni = pubblicazione_trascrizioni;
        this.pubblicazione_immagini = pubblicazione_immagini;
    }
    
    /** imposta il numero di immagini validate di un'opera
     * @param numero_immagini_validate */
    public void setNumero_immagini_validate(int numero_immagini_validate) {
        this.numero_immagini_validate = numero_immagini_validate;
    }
    /** imposta il numero di trascrizioni di pagine validate di un'opera
     * @param numero_trascrizioni_validate */
    public void setNumero_trascrizioni_validate(int numero_trascrizioni_validate) {
        this.numero_trascrizioni_validate = numero_trascrizioni_validate;
    }
    /** Ritorna il numero di immagini validate di un'opera
     * @return  numero_immagini_validate */
    public int getNumero_immagini_validate() {
        return numero_immagini_validate;
    }
    /** Ritorna il numero di trascrizioni validate di un'opera
     * @return  numero_trascrizioni_validate */
    public int getNumero_trascrizioni_validate() {
        return numero_trascrizioni_validate;
    }
   /** imposta il numero di pagine  di un'opera
     * @param numero_pagine */
    public void setNumero_pagine(int numero_pagine) {
        this.numero_pagine = numero_pagine;
    }
    /** Ritorna il numero di pagine di un'opera
     * @return  numero_pagine */
    public int getNumero_pagine() {
        return numero_pagine;
    }
    /** Ritorna il numero di persone associate ad un'opera
     * @return  persone_associate */
    public int getPersone_associate() {
        return persone_associate;
    }
    /** imposta il numero di persone dello staff associate ad un'opera
     * @param persone_associate */
    public void setPersone_associate(int persone_associate) {
        this.persone_associate = persone_associate;
    }
    /** Ritorna l'autore di un'opera
     * @return  autore */
    public String getAutore() {
        return autore;
    }
    /** Ritorna l'anno di un'opera
     * @return  anno */
    public String getAnno() {
        return anno;
    }
    /** Ritorna la lingua di un'opera
     * @return  lingua */
    public String getLingua() {
        return lingua;
    }
    /** Ritorna il titolo di un'opera
     * @return  titolo */
    public String getTitolo() {
        return titolo;
    }
    /** Ritorna true se l'opera ha le trascrizioni pubblicate, false altrimenti
     * @return  pubblicazine_trascrizioni */
    public boolean isPubblicazione_trascrizioni() {
        return pubblicazione_trascrizioni;
    }
    /** Ritorna true se l'opera ha le immagini pubblicate, false altrimenti
     * @return  pubblicazione_immagini */
    public boolean isPubblicazione_immagini() {
        return pubblicazione_immagini;
    }
     /** imposta l'autore  di un'opera
     * @param autore */
    public void setAutore(String autore) {
        this.autore = autore;
    }
     /** imposta l'anno  di un'opera
     * @param anno */
    public void setAnno(String anno) {
        this.anno = anno;
    }
     /** imposta la lingua di un'opera
     * @param lingua */
    public void setLingua(String lingua) {
        this.lingua = lingua;
    }
     /** imposta il titolo  di un'opera
     * @param titolo */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
     /** imposta se le trascrizioni di un'opera sono state pubblicate
     * @param pubblicazione_trascrizioni */
    public void setPubblicazione_trascrizioni(boolean pubblicazione_trascrizioni) {
        this.pubblicazione_trascrizioni = pubblicazione_trascrizioni;
    }
      /** imposta se le immagini di un'opera sono state pubblicate
     * @param pubblicazione_immagini */
    public void setPubblicazione_immagini(boolean pubblicazione_immagini) {
        this.pubblicazione_immagini = pubblicazione_immagini;
    }
    /** Ritorna l'id di un'opera
     * @return  id */
       public int getId() {
        return id;
    }
      /** imposta l'id di un'opera 
     * @param id */
    public void setId(int id) {
        this.id = id;
    }
      /** imposta la copertina di un'opera
     * @param copertina */
      public void setCopertina(Immagine copertina) {
        this.copertina = copertina;
    }
    /** Ritorna la copertina di un'opera
     * @return  copertina */
    public Immagine getCopertina() {
        return copertina;
    }
    
}
    
    
  
