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

import java.util.Objects;

/**
 * Classe per l'oggetto Immagine
 * 
 * @author SS
 */
public class Immagine {
    
    //campi dell'oggetto
    private int id;
    private String file;
    private boolean validazione;
    private int numero_pagina;
    private String formato;
    private long dimensioni;
    private int trascrittore_associato;
    private int ultima_notifica;  
    
    /**
     * Constructor. costruttore generico per l'oggetto Immagine
     *
     * @param id            (required) id dell'immagine
     * @param file          (required) file dell'immagine
     * @param validazione   (required) serve per sapere se l'immagine è validata
     * @param numero_pagina (required) numero pagina dell'immagine
     * @param formato       (required) formato dell'immagine
     * @param dimensioni    (required) dimensioni dell'immagine
     */
    
    public Immagine(int id, String file, boolean validazione, int numero_pagina, String formato, long dimensioni) {
        this.id = id;
        this.file = file;
        this.validazione = validazione;
        this.numero_pagina = numero_pagina;
        this.formato = formato;
        this.dimensioni = dimensioni;
    }
    
        /**
     * Constructor. costruttore per l'oggetto Immagine nell'acquisizione dei file
     *
     * @param file          (required) file dell'immagine
     * @param validazione   (required) serve per sapere se l'immagine è validata
     * @param formato       (required) formato dell'immagine
     * @param dimensioni    (required) dimensioni dell'immagine
     */
    
    public Immagine( String file, boolean validazione, String formato, long dimensioni) {
        this.file = file;
        this.validazione = validazione;
        this.formato = formato;
        this.dimensioni = dimensioni;
    }
    
      /**
     * Constructor. costruttore per l'oggetto Immagine necessario per le notifiche
     *
     * @param id                (required) id dell'immagine
     * @param file              (required) file dell'immagine
     * @param validazione       (required) serve per sapere se l'immagine è validata
     * @param numero_pagina     (required) numero pagina dell'immagine
     * @param formato           (required) formato dell'immagine
     * @param dimensioni        (required) dimensioni dell'immagine
     * @param ultima_notifica   (required) ultima notifica legata all'immagine
     */
    
    public Immagine(int id, String file, boolean validazione, int numero_pagina, String formato, long dimensioni,int ultima_notifica) {
        this.id = id;
        this.file = file;
        this.validazione = validazione;
        this.numero_pagina = numero_pagina;
        this.formato = formato;
        this.dimensioni = dimensioni;
        this.ultima_notifica=ultima_notifica;
    }
    
    /**
     * Override del metodo equals, un oggetto è uguale ad un Immagine se è una
     * istanza di immagine ed è associata allo stesso file, stesso numero di 
     * pagina e stesso id.
     * 
     * @param o  oggetto da confrontare
     * @return   true se stesso file, stesso numero di  pagina e stesso id, false altrimenti
    */
    
    @Override
    public boolean equals(Object o) {
        //se è lo stesso oggetto ritorna true
        if (o == this) return true;
        //se non è un istanza di Immagine ritorna false;
        if (!(o instanceof Immagine)) {
            return false;
        }

        Immagine img = (Immagine) o;
        //ritorna vero se associata allo stesso file, stesso numero di pagina e stesso id.
        return (img.getFile().equals(this.getFile()) && 
                img.getNumero_pagina()==this.getNumero_pagina() &&
                img.getId()==this.getId());
    }
    
     /**
     * Override del metodo hashCode
     * 
     * @return 
    */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.id;
        hash = 41 * hash + Objects.hashCode(this.file);
        hash = 41 * hash + (this.validazione ? 1 : 0);
        hash = 41 * hash + this.numero_pagina;
        hash = 41 * hash + Objects.hashCode(this.formato);
        hash = 41 * hash + (int) (this.dimensioni ^ (this.dimensioni >>> 32));
        hash = 41 * hash + this.trascrittore_associato;
        return hash;
    }
    /** imposta l'ultima notifica associata dell'immagine
     * @param ultima_notifica*/
    public void setUltima_notifica(int ultima_notifica) {
        this.ultima_notifica = ultima_notifica;
    }
    /** Ritorna l'ultima notifica dell'immagine
     * @return  ultima_notifica */
    public int getUltima_notifica() {
        return ultima_notifica;
    }
     /** imposta il trascrittore associato dell'immagine
     * @param trascrittore_associato */
    public void setTrascrittore_associato(int trascrittore_associato) {
        this.trascrittore_associato = trascrittore_associato;
    }
    /** Ritorna il trascrittore associato all'immagine
     * @return  trascrittore_associato */
    public int getTrascrittore_associato() {
        return trascrittore_associato;
    }
    /** Ritorna l'id dell'immagine
     * @return  id */
    public int getId() {
        return id;
    }
    /** Ritorna la stringa del file associato all'immagine
     * @return  file */
    public String getFile() {
        return file;
    }
    /** Ritorna true se l'immagine è validata, false altrimenti
     * @return  validazione */
    public boolean isValidazione() {
        return validazione;
    }
    /** Ritorna il numero della pagina dell'immagine
     * @return  numero_pagina */
    public int getNumero_pagina() {
        return numero_pagina;
    }
    /** Ritorna il formato dell'immagine
     * @return  formato */
    public String getFormato() {
        return formato;
    }
    /** Ritorna le dimensioni dell'immagine
     * @return  dimensioni */
    public long getDimensioni() {
        return dimensioni;
    }
     /** imposta l'id dell'immagine
     * @param id */
    public void setId(int id) {
        this.id = id;
    }
    /** imposta la stringa del file associato all'immagine
     * @param file */
    public void setFile(String file) {
        this.file = file;
    }
    /** imposta se l'immagine è stata validata
     * @param validazione */
    public void setValidazione(boolean validazione) {
        this.validazione = validazione;
    }
    /** imposta il numero pagina dell'immagine
     * @param numero_pagina */
    public void setNumero_pagina(int numero_pagina) {
        this.numero_pagina = numero_pagina;
    }
    /** imposta il formato dell'immagine
     * @param formato */
    public void setFormato(String formato) {
        this.formato = formato;
    }
    /** imposta le dimensioni dell'immagine
     * @param dimensioni */
    public void setDimensioni(long dimensioni) {
        this.dimensioni = dimensioni;
    }    
    
}
