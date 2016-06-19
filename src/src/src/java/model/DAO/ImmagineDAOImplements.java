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
import model.transfer_objects.Immagine;
import model.transfer_objects.Opera;

/**
 * classe per l'implementazione dell'interfaccia ImmagineDAO
 *
 * @author SS
 */
public class ImmagineDAOImplements implements ImmagineDAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Immagine> getImmaginiOperaValidate(Opera opera) {

        ArrayList<Immagine> listaimmagini;
        listaimmagini = new ArrayList<>();
        try {
            ResultSet rs = Database.selectRecord("immagini_digitali", "validazione=1 && opera=" + opera.getId());
            while (rs.next()) {
                listaimmagini.add(new Immagine(
                        rs.getInt("ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("numero_pagina"),
                        rs.getString("formato"),
                        rs.getLong("dimensioni")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaimmagini;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Immagine> getImmaginiOperaValidate(int opera) {

        ArrayList<Immagine> listaimmagini;
        listaimmagini = new ArrayList<>();
        try {
            ResultSet rs = Database.selectRecord("immagini_digitali", "validazione=1 && opera=" + opera);
            while (rs.next()) {
                listaimmagini.add(new Immagine(
                        rs.getInt("ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("numero_pagina"),
                        rs.getString("formato"),
                        rs.getLong("dimensioni")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaimmagini;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Immagine> getImmaginiOperaValidate(String opera) {

        ArrayList<Immagine> listaimmagini;
        listaimmagini = new ArrayList<>();
        OperaDAOImplements o = new OperaDAOImplements();
        try {
            ResultSet rs = Database.selectRecord("immagini_digitali", "validazione=1 && opera=" + (o.getOperaSingola(opera).getId()));
            while (rs.next()) {
                listaimmagini.add(new Immagine(
                        rs.getInt("ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("numero_pagina"),
                        rs.getString("formato"),
                        rs.getLong("dimensioni")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaimmagini;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Immagine> getImmaginiOperaNonValidate(Opera opera) {
        NotificaDAOImplements n = new NotificaDAOImplements();
        ArrayList<Immagine> listaimmagini;
        listaimmagini = new ArrayList<>();
        try {
            ResultSet rs = Database.selectRecord("immagini_digitali", "validazione=0 && opera=" + opera.getId());
            while (rs.next()) {
                listaimmagini.add(new Immagine(
                        rs.getInt("ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("numero_pagina"),
                        rs.getString("formato"),
                        rs.getLong("dimensioni"),
                        n.getGruppoUltimaNotificaImmagine(rs.getInt("ID"))
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaimmagini;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Immagine> getImmaginiOperaNonValidate(int opera) {
        NotificaDAOImplements n = new NotificaDAOImplements();
        ArrayList<Immagine> listaimmagini;
        listaimmagini = new ArrayList<>();
        try {
            ResultSet rs = Database.selectRecord("immagini_digitali", "validazione=0 && opera=" + opera);
            while (rs.next()) {
                listaimmagini.add(new Immagine(
                        rs.getInt("ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("numero_pagina"),
                        rs.getString("formato"),
                        rs.getLong("dimensioni"),
                        n.getGruppoUltimaNotificaImmagine(rs.getInt("ID"))
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaimmagini;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Immagine> getImmaginiOperaNonValidate(String opera) {
        NotificaDAOImplements n = new NotificaDAOImplements();
        ArrayList<Immagine> listaimmagini;
        listaimmagini = new ArrayList<>();
        OperaDAOImplements o = new OperaDAOImplements();
        try {
            ResultSet rs = Database.selectRecord("immagini_digitali", "validazione=0 && opera=" + (o.getOperaSingola(opera).getId()));
            while (rs.next()) {
                listaimmagini.add(new Immagine(
                        rs.getInt("ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("numero_pagina"),
                        rs.getString("formato"),
                        rs.getLong("dimensioni"),
                        n.getGruppoUltimaNotificaImmagine(rs.getInt("ID"))
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaimmagini;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Immagine getImmagineSingola(Opera opera, int numero_pagina) {

        Immagine immagine = null;
        try {
            ResultSet rs = Database.selectRecord("immagini_digitali",
                    "numero_pagina=" + numero_pagina + " && opera=" + (opera.getId()));
            while (rs.next()) {
                immagine = new Immagine(
                        rs.getInt("ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("numero_pagina"),
                        rs.getString("formato"),
                        rs.getLong("dimensioni")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return immagine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Immagine getImmagineSingola(int opera, int numero_pagina) {

        Immagine immagine = null;
        try {
            ResultSet rs = Database.selectRecord("immagini_digitali",
                    "numero_pagina=" + numero_pagina + " && opera=" + opera);
            while (rs.next()) {
                immagine = new Immagine(
                        rs.getInt("ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("numero_pagina"),
                        rs.getString("formato"),
                        rs.getLong("dimensioni")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return immagine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Immagine getImmagineSingola(String opera, int numero_pagina) {

        Immagine immagine = null;
        OperaDAOImplements o = new OperaDAOImplements();
        try {
            ResultSet rs = Database.selectRecord("immagini_digitali",
                    "numero_pagina=" + numero_pagina + " && opera=" + (o.getOperaSingola(opera).getId()));
            while (rs.next()) {
                immagine = new Immagine(
                        rs.getInt("ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("numero_pagina"),
                        rs.getString("formato"),
                        rs.getLong("dimensioni")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return immagine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateImmagine(Map<String, Object> data, String condizione) {
        boolean result = false;
        try {
            result = Database.updateRecord("immagini_digitali", data, condizione);
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public boolean ReplaceFieldImmagine(String field, String vecchio, String nuovo) {
        boolean result = false;
        try {
            result = Database.ReplaceRecord("immagini_digitali", field, vecchio, nuovo);
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteImmagine(Immagine immagine) {

        boolean result = false;
        try {
            result = Database.deleteRecord("immagini_digitali", "ID=" + immagine.getId());
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteImmagine(int id) {

        boolean result = false;
        try {
            result = Database.deleteRecord("immagini_digitali", "ID=" + id);
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addImmagine(Immagine immagine, int opera) {

        boolean result = false;
        Map data = new HashMap();
        data.put("file", immagine.getFile());
        if (immagine.isValidazione()) {
            data.put("validazione", 1);
        } else {
            data.put("validazione", 0);
        }
        data.put("numero_pagina", immagine.getNumero_pagina());
        data.put("formato", immagine.getFormato());
        data.put("dimensioni", immagine.getDimensioni());
        data.put("opera", opera);

        try {
            result = Database.insertRecord("immagini_digitali", data);
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Immagine> getImmaginiOperaInRevisione(int opera) {

        ArrayList<Immagine> listaimmagini;
        listaimmagini = new ArrayList<>();
        try {
            ResultSet rs = Database.selectJoin("immagini_digitali", "trascrizione", "immagini_digitali.ID=trascrizione.immagine", "revisione=1 && immagini_digitali.validazione=1 && opera=" + opera);
            while (rs.next()) {
                listaimmagini.add(new Immagine(
                        rs.getInt("immagini_digitali.ID"),
                        rs.getString("file"),
                        rs.getBoolean("immagini_digitali.validazione"),
                        rs.getInt("numero_pagina"),
                        rs.getString("formato"),
                        rs.getLong("dimensioni")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaimmagini;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Immagine> getImmaginiOperaInRevisione(Opera opera) {

        ArrayList<Immagine> listaimmagini;
        listaimmagini = new ArrayList<>();
        try {
            ResultSet rs = Database.selectJoin("immagini_digitali", "trascrizione", "immagini_digitali.ID=trascrizione.immagine", "revisione=1 && immagini_digitali.validazione=1 && opera=" + (opera.getId()));
            while (rs.next()) {
                listaimmagini.add(new Immagine(
                        rs.getInt("immagini_digitali.ID"),
                        rs.getString("file"),
                        rs.getBoolean("immagini_digitali.validazione"),
                        rs.getInt("numero_pagina"),
                        rs.getString("formato"),
                        rs.getLong("dimensioni")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaimmagini;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Immagine> getImmaginiOperaInRevisione(String opera) {

        ArrayList<Immagine> listaimmagini;
        listaimmagini = new ArrayList<>();
        OperaDAOImplements o = new OperaDAOImplements();
        try {
            ResultSet rs = Database.selectJoin("immagini_digitali", "trascrizione", "immagini_digitali.ID=trascrizione.immagine", "revisione=1 && immagini_digitali.validazione=1 && opera=" + (o.getOperaSingola(opera).getId()));
            while (rs.next()) {
                listaimmagini.add(new Immagine(
                        rs.getInt("immagini_digitali.ID"),
                        rs.getString("file"),
                        rs.getBoolean("immagini_digitali.validazione"),
                        rs.getInt("numero_pagina"),
                        rs.getString("formato"),
                        rs.getLong("dimensioni")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaimmagini;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Immagine getImmagineSingola(int immagine) {
        Immagine img = null;
        try {
            ResultSet rs = Database.selectRecord("immagini_digitali",
                    "ID=" + immagine);
            while (rs.next()) {
                img = new Immagine(
                        rs.getInt("ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("numero_pagina"),
                        rs.getString("formato"),
                        rs.getLong("dimensioni")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Immagine getImmagineSingola(String file) {
        Immagine img = null;
        try {
            ResultSet rs = Database.selectRecord("immagini_digitali",
                    "file='" + file + "'");
            while (rs.next()) {
                img = new Immagine(
                        rs.getInt("ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("numero_pagina"),
                        rs.getString("formato"),
                        rs.getLong("dimensioni")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Immagine> getImmaginiOperaAcquisitore(Opera opera, int acquisitore) {
        NotificaDAOImplements n = new NotificaDAOImplements();
        ArrayList<Immagine> listaimmagini;
        listaimmagini = new ArrayList<>();
        try {
            ResultSet rs = Database.selectJoinGroupBy("immagini_digitali", "notifiche_immagini", "immagini_digitali.ID=notifiche_immagini.immagine", "validazione=0 && opera=" + opera.getId() + " && mittente_destinatario=" + acquisitore, "immagine");
            while (rs.next()) {
                listaimmagini.add(new Immagine(
                        rs.getInt("immagini_digitali.ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("numero_pagina"),
                        rs.getString("formato"),
                        rs.getLong("dimensioni"),
                        n.getGruppoUltimaNotificaImmagine(rs.getInt("immagini_digitali.ID"))
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaimmagini;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Immagine> getImmaginiOperaAcquisitore(int opera, int acquisitore) {
        NotificaDAOImplements n = new NotificaDAOImplements();
        ArrayList<Immagine> listaimmagini;
        listaimmagini = new ArrayList<>();
        try {
            ResultSet rs = Database.selectJoin("immagini_digitali", "notifiche_immagini", "immagini_digitali.ID=notifiche_immagini.immagine", "validazione=0 && opera=" + opera + " && mittente_destinatario=" + acquisitore);
            while (rs.next()) {
                listaimmagini.add(new Immagine(
                        rs.getInt("immagini_digitali.ID"),
                        rs.getString("file"),
                        rs.getBoolean("validazione"),
                        rs.getInt("numero_pagina"),
                        rs.getString("formato"),
                        rs.getLong("dimensioni"),
                        n.getGruppoUltimaNotificaImmagine(rs.getInt("immagini_digitali.ID"))
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaimmagini;
    }

}
