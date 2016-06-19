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
 * Classe per l'oggetto Utente
 *
 * @author SS
 */
public class Utente {

    //campi dell'oggetto
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String giorno_di_nascita;
    private String indirizzo;
    private String citta;
    private String opera_associata;
    private int gruppo;

    /**
     * Constructor. costruttore generico per l'oggetto Utente
     *
     * @param id (required) nome dell'utente
     * @param nome (required) nome dell'utente
     * @param cognome (required) cognome dell'utente
     * @param email (required) email dell'utente
     * @param password (required) password dell'utente
     * @param giornodinascita (required) giorno di nascita dell'utente
     * @param indirizzo (opzionale) indirizzo dell'utente
     * @param citta (opzionale) città dell'utente
     */
    public Utente(int id, String nome, String cognome, String email, String password, String giornodinascita, String indirizzo, String citta) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.giorno_di_nascita = giornodinascita;
        this.indirizzo = indirizzo;
        this.citta = citta;
    }

    /**
     * Constructor. necessario per la registrazione
     *
     * @param nome (required) nome dell'utente
     * @param cognome (required) cognome dell'utente
     * @param email (required) email dell'utente
     * @param password (required) password dell'utente
     * @param giornodinascita (required) giorno di nascita dell'utente
     * @param indirizzo (opzionale) indirizzo dell'utente
     * @param citta (opzionale) città dell'utente
     */
    public Utente(String nome, String cognome, String email, String password, String giornodinascita, String indirizzo, String citta) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.giorno_di_nascita = giornodinascita;
        this.indirizzo = indirizzo;
        this.citta = citta;
    }

    /**
     * Constructor. necessario per la ricerca dello staff nel Database
     *
     * @param id (required) id dell'utente
     * @param nome (required) nome dell'utente
     * @param cognome (required) cognome dell'utente
     * @param gruppo (required) gruppo dell'utente
     */
    public Utente(int id, String nome, String cognome, int gruppo) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.gruppo = gruppo;
    }

    /** Associa un opera ad un utente
     * @param opera_associata*/
    
    public void setOpera_associata(String opera_associata) {
        this.opera_associata = opera_associata;
    }
    
    /** Ritorna l'opera associata all'utente
     * @return  opera_associata */
    public String getOpera_associata() {
        return opera_associata;
    }
    /** imposta il nome di un utente
     * @param nome */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /** imposta il cognome di un utente
     * @param cognome */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    /** imposta l'email di un utente
     * @param email */
    public void setEmail(String email) {
        this.email = email;
    }
    /** imposta la password di un utente
     * @param password */
    public void setPassword(String password) {
        this.password = password;
    }
    /** imposta l'indirizzo di un utente
     * @param indirizzo */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
      /** imposta la città di un utente
     * @param citta */
    public void setCitta(String citta) {
        this.citta = citta;
    }
     /** Ritorna il nome di un utente
     * @return   nome*/
    public String getNome() {
        return nome;
    }
    /** Ritorna il cognome di un utente
     * @return  cognome */
    public String getCognome() {
        return cognome;
    }
     /** Ritorna l'email di un utente
     * @return  email */
    public String getEmail() {
        return email;
    }
     /** Ritorna la password di un utente
     * @return  password */
    public String getPassword() {
        return password;
    }
     /** Ritorna l'indirizzo di un utente
     * @return  indirizzo */
    public String getIndirizzo() {
        return indirizzo;
    }
    /** Ritorna la città di un utente
     * @return  citta */
    public String getCitta() {
        return citta;
    }
    /** imposta l'id di un utente
     * @param id */
    public void setId(int id) {
        this.id = id;
    }
    /** Ritorna l'id di un utente
     * @return  is */
    public int getId() {
        return id;
    }
    /** imposta il giorno di nascita di un utente
     * @param giorno_di_nascita */
    public void setGiorno_di_nascita(String giorno_di_nascita) {
        this.giorno_di_nascita = giorno_di_nascita;
    }
    /** imposta il gruppo di un utente
     * @param gruppo */
    public void setGruppo(int gruppo) {
        this.gruppo = gruppo;
    }
   /** Ritorna il giorno di nascita di un utente
     * @return  giorno_di_nascita */
    public String getGiorno_di_nascita() {
        return giorno_di_nascita;
    }
   /** Ritorna il gruppo di un utente
     * @return  indirizzo */
    public int getGruppo() {
        return gruppo;
    }

}
