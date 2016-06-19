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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.transfer_objects.Utente;
import static controller.utility.DataUtil.crypt;

/**
 *classe per l'implementazione dell'interfaccia UtenteDAO
 * 
 * @author SS
 */
public class UtenteDAOImplements implements UtenteDAO {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Utente> getGruppoUtenza(int gruppo) {
        ArrayList<Utente> listautenti;
        listautenti = new ArrayList<>();
        try {
            ResultSet rs = Database.selectRecord("utenti", "gruppo='" + gruppo + "'");
            while (rs.next()) {
                listautenti.add(new Utente(
                        rs.getInt("ID"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("giornodinascita"),
                        rs.getString("indirizzo"),
                        rs.getString("citta"))
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listautenti;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Utente getUtenteSingolo(int id) {
        Utente utente = null;
        try {
            ResultSet rs = Database.selectRecord("utenti", "ID=" + id);
            while (rs.next()) {
                utente = new Utente(
                        rs.getInt("ID"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("giornodinascita"),
                        rs.getString("indirizzo"),
                        rs.getString("citta"));
                utente.setGruppo(rs.getInt("gruppo"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return utente;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Utente getUtentesingolo(String email, String password) {

        Utente utente = null;
        try {
            ResultSet rs;
            rs = Database.selectRecord("utenti", "email='" + email + "' && password='" + crypt(password) + "'");
            while (rs.next()) {
                utente = new Utente(
                        rs.getInt("ID"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("giornodinascita"),
                        rs.getString("indirizzo"),
                        rs.getString("citta")
                );
                utente.setGruppo(rs.getInt("gruppo"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return utente;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateUtente(Map<String, Object> data, String condizione) {
        boolean result = false;
        try {
            result = Database.updateRecord("utenti", data, condizione);
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteUtente(Utente utente) {
        return deleteUtente(utente.getId());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteUtente(int id) {
        boolean result = false;
        try {
            result = Database.deleteRecord("utenti", "ID=" + id);
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteUtente(String email) {
        boolean result = false;
        try {
            result = Database.deleteRecord("utenti", "email='" + email + "'");
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addUtente(Utente u) {

        boolean result = false;
        Map data = new HashMap();
        data.put("nome", u.getNome());
        data.put("cognome", u.getCognome());
        data.put("email", u.getEmail());
        data.put("password", crypt(u.getPassword()));
        data.put("indirizzo", u.getIndirizzo());
        data.put("citta", u.getCitta());
        data.put("giornodinascita", u.getGiorno_di_nascita());
        data.put("gruppo", 1);
        try {
            result = Database.insertRecord("utenti", data);
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Utente> getStaff() {
        ArrayList<Utente> listautenti;
        listautenti = new ArrayList<>();
        try {
            ResultSet rs = Database.selectRecord("utenti", "gruppo!=6 && gruppo!=1");
            while (rs.next()) {
                listautenti.add(new Utente(
                        rs.getInt("ID"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getInt("gruppo")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listautenti;
    }

}
