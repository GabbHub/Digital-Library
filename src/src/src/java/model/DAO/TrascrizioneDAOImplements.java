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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.transfer_objects.Immagine;
import model.transfer_objects.Opera;
import model.transfer_objects.Trascrizione;
import model.transfer_objects.Utente;

/**
 *classe per l'implementazione dell'interfaccia trascrizioneDAO
 * 
 * @author SS
 */
public class TrascrizioneDAOImplements implements TrascrizioneDAO {
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public Trascrizione getTrascrizioneImmagine(Immagine immagine) {

        Trascrizione trascrizione = null;
        try {
            ResultSet rs = Database.selectRecord("trascrizione", "immagine=" + immagine.getId());
            while (rs.next()) {
                trascrizione = new Trascrizione(
                        rs.getInt("ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("immagine")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trascrizione;
    }
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public Trascrizione getTrascrizioneImmagine(Opera opera, int numero_pagina) {

        Trascrizione trascrizione = null;
        ImmagineDAOImplements img = new ImmagineDAOImplements();

        try {
            ResultSet rs = Database.selectRecord("trascrizione",
                    "immagine=" + (img.getImmagineSingola(opera, numero_pagina).getId()));
            while (rs.next()) {
                trascrizione = new Trascrizione(
                        rs.getInt("ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("immagine")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trascrizione;
    }
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public Trascrizione getTrascrizioneImmagine(int opera, int numero_pagina) {

        Trascrizione trascrizione = null;
        ImmagineDAOImplements img = new ImmagineDAOImplements();

        try {
            ResultSet rs = Database.selectRecord("trascrizione",
                    "immagine=" + (img.getImmagineSingola(opera, numero_pagina).getId()));
            while (rs.next()) {
                trascrizione = new Trascrizione(
                        rs.getInt("ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("immagine")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trascrizione;

    }
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public Trascrizione getTrascrizioneImmagine(String opera, int numero_pagina) {

        Trascrizione trascrizione = null;
        ImmagineDAOImplements img = new ImmagineDAOImplements();

        try {
            ResultSet rs = Database.selectRecord("trascrizione",
                    "immagine=" + (img.getImmagineSingola(opera, numero_pagina).getId()));
            while (rs.next()) {
                trascrizione = new Trascrizione(
                        rs.getInt("ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("immagine")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trascrizione;
    }
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public Trascrizione getTrascrizioneImmagine(int immagine) {

        Trascrizione trascrizione = null;
        try {
            ResultSet rs = Database.selectRecord("trascrizione", "immagine=" + immagine);
            while (rs.next()) {
                trascrizione = new Trascrizione(
                        rs.getInt("ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("immagine")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trascrizione;
    }
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public boolean updateTrascrizione(Map<String, Object> data, String condizione) {

        boolean result = false;
        try {
            result = Database.updateRecord("trascrizione", data, condizione);
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public boolean deleteTrascrizione(int id) {

        boolean result = false;
        try {
            result = Database.deleteRecord("trascrizione", "ID=" + id);
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public boolean deleteTrascrizione(Trascrizione trascrizione) {

        boolean result = false;
        try {
            result = Database.deleteRecord("trascrizione", "ID=" + trascrizione.getId());
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public boolean addTrascrizione(Trascrizione trascrizione) {

        boolean result = false;
        Map data = new HashMap();
        data.put("file", trascrizione.getFile());
        if (trascrizione.isValidazione()) {
            data.put("validazione", 1);
        } else {
            data.put("validazione", 0);
        }
        data.put("immagine", trascrizione.getImmagine());

        try {
            result = Database.insertRecord("trascrizione", data);
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public ArrayList<Trascrizione> getTrascrizioniInRevisioneOpera(Opera opera) {
        NotificaDAOImplements n = new NotificaDAOImplements();
        ArrayList<Trascrizione> listatrascrizioni;
        listatrascrizioni = new ArrayList<>();
        try {
            ResultSet rs = Database.selectJoin("trascrizione",
                    "immagini_digitali", "immagine=immagini_digitali.ID",
                    "opera=" + opera.getId() + " && trascrizione.validazione=0 && trascrizione.revisione=1");
            while (rs.next()) {
                listatrascrizioni.add(new Trascrizione(
                        rs.getInt("trascrizione.ID"),
                        rs.getString("trascrizione.file"),
                        rs.getBoolean("trascrizione.validazione"),
                        rs.getInt("immagine"),
                        n.getGruppUltimaNotificaTrascrizione(rs.getInt("trascrizione.ID")),
                        new Immagine(
                                rs.getInt("immagini_digitali.ID"),
                                rs.getString("immagini_digitali.file"),
                                rs.getBoolean("immagini_digitali.validazione"),
                                rs.getInt("immagini_digitali.numero_pagina"),
                                rs.getString("immagini_digitali.formato"),
                                rs.getLong("immagini_digitali.dimensioni")
                        )));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listatrascrizioni;
    }
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public ArrayList<Trascrizione> getTrascrizioniInRevisioneOpera(int opera) {
        NotificaDAOImplements n = new NotificaDAOImplements();
        ArrayList<Trascrizione> listatrascrizioni;
        listatrascrizioni = new ArrayList<>();
        try {
            ResultSet rs = Database.selectJoin("trascrizione",
                    "immagini_digitali", "immagine=immagini_digitali.ID",
                    "opera=" + opera + " && trascrizione.validazione=0 && trascrizione.revisione=1");
            while (rs.next()) {
                listatrascrizioni.add(new Trascrizione(
                        rs.getInt("trascrizione.ID"),
                        rs.getString("trascrizione.file"),
                        rs.getBoolean("trascrizione.validazione"),
                        rs.getInt("immagine"),
                        n.getGruppUltimaNotificaTrascrizione(rs.getInt("trascrizione.ID")),
                        new Immagine(
                                rs.getInt("immagini_digitali.ID"),
                                rs.getString("immagini_digitali.file"),
                                rs.getBoolean("immagini_digitali.validazione"),
                                rs.getInt("immagini_digitali.numero_pagina"),
                                rs.getString("immagini_digitali.formato"),
                                rs.getLong("immagini_digitali.dimensioni")
                        )));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listatrascrizioni;
    }
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public ArrayList<Trascrizione> getTrascrizioniInRevisioneOpera(String opera) {
        NotificaDAOImplements n = new NotificaDAOImplements();
        ArrayList<Trascrizione> listatrascrizioni;
        listatrascrizioni = new ArrayList<>();
        OperaDAOImplements o = new OperaDAOImplements();
        Opera op = o.getOperaSingola(opera);
        try {
            ResultSet rs = Database.selectJoin("trascrizione",
                    "immagini_digitali", "immagine=immagini_digitali.ID",
                    "opera=" + op.getId() + " && trascrizione.validazione=0 && trascrizione.revisione=1");
            while (rs.next()) {
                listatrascrizioni.add(new Trascrizione(
                        rs.getInt("trascrizione.ID"),
                        rs.getString("trascrizione.file"),
                        rs.getBoolean("trascrizione.validazione"),
                        rs.getInt("immagine"),
                        n.getGruppUltimaNotificaTrascrizione(rs.getInt("trascrizione.ID")),
                        new Immagine(
                                rs.getInt("immagini_digitali.ID"),
                                rs.getString("immagini_digitali.file"),
                                rs.getBoolean("immagini_digitali.validazione"),
                                rs.getInt("immagini_digitali.numero_pagina"),
                                rs.getString("immagini_digitali.formato"),
                                rs.getLong("immagini_digitali.dimensioni")
                        )));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listatrascrizioni;
    }
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public ArrayList<Trascrizione> getTrascrizioniInRevisioneUtente(Utente utente) {
        NotificaDAOImplements n = new NotificaDAOImplements();
        ArrayList<Trascrizione> listatrascrizioni;
        listatrascrizioni = new ArrayList<>();
        try {
            ResultSet rs = Database.selectJoin("trascrizione",
                    "immagini_digitali", "notifiche_trascrizione",
                    "immagine=immagini_digitali.ID", "notifiche_trascrizione.trascrizione=trascrizione.ID",
                    "mittente_destinatario=" + utente.getId() + " && trascrizione.validazione=0 && trascrizione.revisione=1");
            while (rs.next()) {
                listatrascrizioni.add(new Trascrizione(
                        rs.getInt("trascrizione.ID"),
                        rs.getString("trascrizione.file"),
                        rs.getBoolean("trascrizione.validazione"),
                        rs.getInt("immagine"),
                        n.getGruppUltimaNotificaTrascrizione(rs.getInt("trascrizione.ID")),
                        new Immagine(
                                rs.getInt("immagini_digitali.ID"),
                                rs.getString("immagini_digitali.file"),
                                rs.getBoolean("immagini_digitali.validazione"),
                                rs.getInt("immagini_digitali.numero_pagina"),
                                rs.getString("immagini_digitali.formato"),
                                rs.getLong("immagini_digitali.dimensioni")
                        )));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listatrascrizioni;
    }
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public ArrayList<Trascrizione> getTrascrizioniInRevisioneUtente(int utente) {
        NotificaDAOImplements n = new NotificaDAOImplements();
        ArrayList<Trascrizione> listatrascrizioni;
        listatrascrizioni = new ArrayList<>();
        try {
            ResultSet rs = Database.selectJoinGroupBy("trascrizione",
                    "immagini_digitali", "notifiche_trascrizione",
                    "immagine=immagini_digitali.ID", "notifiche_trascrizione.trascrizione=trascrizione.ID",
                    "mittente_destinatario=" + utente + " && trascrizione.validazione=0 && trascrizione.revisione=1",
                    "trascrizione.file,immagini_digitali.file");
            while (rs.next()) {
                listatrascrizioni.add(new Trascrizione(
                        rs.getInt("trascrizione.ID"),
                        rs.getString("trascrizione.file"),
                        rs.getBoolean("trascrizione.validazione"),
                        rs.getInt("immagine"),
                        n.getGruppUltimaNotificaTrascrizione(rs.getInt("trascrizione.ID")),
                        new Immagine(
                                rs.getInt("immagini_digitali.ID"),
                                rs.getString("immagini_digitali.file"),
                                rs.getBoolean("immagini_digitali.validazione"),
                                rs.getInt("immagini_digitali.numero_pagina"),
                                rs.getString("immagini_digitali.formato"),
                                rs.getLong("immagini_digitali.dimensioni")
                        )));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listatrascrizioni;
    }
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public void setTrascrittoreAssociato(List<Immagine> listaimmagini){
        listaimmagini.stream().forEach((img) -> {
            try {
                ResultSet rs=Database.selectJoin("notifiche_trascrizione",
                        "utenti",
                        "trascrizione",
                        "utenti.id=notifiche_trascrizione.mittente_destinatario",
                        "notifiche_trascrizione.trascrizione=trascrizione.ID",
                        "immagine="+img.getId()+" && gruppo=4");
                if(rs.next())img.setTrascrittore_associato(rs.getInt("mittente_destinatario"));
            } catch (SQLException ex) {
                Logger.getLogger(TrascrizioneDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
