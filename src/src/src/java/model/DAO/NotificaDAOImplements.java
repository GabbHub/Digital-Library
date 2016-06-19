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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.transfer_objects.Immagine;
import model.transfer_objects.Notifica;
import model.transfer_objects.Trascrizione;
import model.transfer_objects.Utente;

/**
 * classe per l'implementazione dell'interfaccia NotificaDAO
 * 
 * @author SS
 */
public class NotificaDAOImplements implements NotificaDAO{

    /** 
     *{@inheritDoc}
     */
        @Override
    public ArrayList<Notifica> getNotificheImmaginiUtente(Utente utente) {
        
              ArrayList<Notifica> listanotifiche;
            listanotifiche = new ArrayList<>();
        try {     
                Timestamp timestamp;
            Date date = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            ResultSet rs=Database.selectRecord("notifiche_immagini", "mittente_destinatario="+utente.getId(), "orario ASC");
            while(rs.next()){
                timestamp=rs.getTimestamp("orario");
                if (timestamp != null)
                 date= new java.util.Date(timestamp.getTime());
                     listanotifiche.add(new Notifica(
                       rs.getInt("ID"),
                       rs.getInt("mittente_destinatario"),
                       rs.getString("notifica"),
                       rs.getInt("immagine"),
                             dateFormat.format(date)
                       ));   
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listanotifiche; 
    }
   
     /** 
     *{@inheritDoc}
     */
            @Override
    public ArrayList<Notifica> getNotificheImmaginiUtente(int utente) {
        
              ArrayList<Notifica> listanotifiche;
            listanotifiche = new ArrayList<>();
        try {     
                Timestamp timestamp;
            Date date = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            ResultSet rs=Database.selectRecord("notifiche_immagini", "mittente_destinatario="+utente, "orario ASC");
            while(rs.next()){
                timestamp=rs.getTimestamp("orario");
                if (timestamp != null)
                 date= new java.util.Date(timestamp.getTime());
                     listanotifiche.add(new Notifica(
                       rs.getInt("ID"),
                       rs.getInt("mittente_destinatario"),
                       rs.getString("notifica"),
                       rs.getInt("immagine"),
                             dateFormat.format(date)
                       ));   
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listanotifiche; 
    }

     /** 
     *{@inheritDoc}
     */
        @Override
    public ArrayList<Notifica> getNotificheTrascrizioniUtente(int utente) {
                  ArrayList<Notifica> listanotifiche;
            listanotifiche = new ArrayList<>();
        try {                Timestamp timestamp;
            Date date = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");    
            ResultSet rs;
            rs=Database.selectRecord("notifiche_trascrizione", "mittente_destinatario="+utente, "orario ASC");
              while(rs.next()){
                timestamp = rs.getTimestamp("orario");
                if (timestamp != null)
                 date= new java.util.Date(timestamp.getTime());
                     listanotifiche.add(new Notifica(
                       rs.getInt("ID"),
                       rs.getInt("mittente_destinatario"),
                       rs.getString("notifica"),
                       rs.getInt("trascrizione"),
                             dateFormat.format(date)
                       ));   
            }
   
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listanotifiche; 
    }
    
     /** 
     *{@inheritDoc}
     */
            @Override
    public ArrayList<Notifica> getNotificheTrascrizioniUtente(Utente utente) {
                  ArrayList<Notifica> listanotifiche;
            listanotifiche = new ArrayList<>();
        try {                Timestamp timestamp;
            Date date = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");    
            ResultSet rs;
            rs=Database.selectRecord("notifiche_trascrizione", "mittente_destinatario="+utente.getId(), "orario ASC");
              while(rs.next()){
                timestamp = rs.getTimestamp("orario");
                if (timestamp != null)
                 date= new java.util.Date(timestamp.getTime());
                     listanotifiche.add(new Notifica(
                       rs.getInt("ID"),
                       rs.getInt("mittente_destinatario"),
                       rs.getString("notifica"),
                       rs.getInt("trascrizione"),
                             dateFormat.format(date)
                       ));   
            }
   
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listanotifiche; 
    }
    
     /** 
     *{@inheritDoc}
     */
    @Override
    public ArrayList<Notifica> getNotificheImmagine(Immagine immagine) {
                                          ArrayList<Notifica> listanotifiche;
            listanotifiche = new ArrayList<>();
        try {       Timestamp timestamp;
            Date date;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");        
            ResultSet rs=Database.selectRecord("notifiche_immagini", "immagine="+immagine.getId(), "orario ASC");
            while(rs.next()){
                timestamp = rs.getTimestamp("orario");
                date = null;
                if (timestamp != null)
                 date= new java.util.Date(timestamp.getTime());
                     listanotifiche.add(new Notifica(
                       rs.getInt("ID"),
                       rs.getInt("mittente_destinatario"),
                       rs.getString("notifica"),
                       rs.getInt("immagine"),
                             dateFormat.format(date)
                       ));   
            }
   
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listanotifiche;
    }
    
     /** 
     *{@inheritDoc}
     */
    @Override
    public ArrayList<Notifica> getNotificheImmagine(int immagine) {
                                          ArrayList<Notifica> listanotifiche;
            listanotifiche = new ArrayList<>();
        try {        Timestamp timestamp;
            Date date;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");           
            ResultSet rs=Database.selectRecord("notifiche_immagini", "immagine="+immagine, "orario ASC");
            while(rs.next()){
                timestamp = rs.getTimestamp("orario");
                date = null;
                if (timestamp != null)
                 date= new java.util.Date(timestamp.getTime());
                     listanotifiche.add(new Notifica(
                       rs.getInt("ID"),
                       rs.getInt("mittente_destinatario"),
                       rs.getString("notifica"),
                       rs.getInt("immagine"),
                             dateFormat.format(date)
                       ));   
            }
   
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listanotifiche;
    }
    
     /** 
     *{@inheritDoc}
     */
    @Override
    public ArrayList<Notifica> getNotificheTrascrizione(Trascrizione trascrizione) {
                                              ArrayList<Notifica> listanotifiche;
            listanotifiche = new ArrayList<>();
        try {       Timestamp timestamp;
            Date date;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");      
            ResultSet rs=Database.selectRecord("notifiche_trascrizione", "trascrizione="+trascrizione, "orario ASC");
            while(rs.next()){
                 timestamp = rs.getTimestamp("orario");
                date = null;
                if (timestamp != null)
                 date= new java.util.Date(timestamp.getTime());
                     listanotifiche.add(new Notifica(
                       rs.getInt("ID"),
                       rs.getInt("mittente_destinatario"),
                       rs.getString("notifica"),
                       rs.getInt("trascrizione"),
                             dateFormat.format(date)
                       ));   
            }
   
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listanotifiche;
    }
    
     /** 
     *{@inheritDoc}
     */
    @Override
    public ArrayList<Notifica> getNotificheTrascrizione(int trascrizione) {
                                              ArrayList<Notifica> listanotifiche;
            listanotifiche = new ArrayList<>();
        try {            Timestamp timestamp;
            Date date;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");       
            ResultSet rs=Database.selectRecord("notifiche_trascrizione", "trascrizione="+trascrizione, "orario ASC");
            while(rs.next()){
                 timestamp = rs.getTimestamp("orario");
                 date = null;
                if (timestamp != null)
                 date= new java.util.Date(timestamp.getTime());
                     listanotifiche.add(new Notifica(
                       rs.getInt("ID"),
                       rs.getInt("mittente_destinatario"),
                       rs.getString("notifica"),
                       rs.getInt("trascrizione"),
                             dateFormat.format(date)
                       ));   
            }
   
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listanotifiche;
    }
    
     /** 
     *{@inheritDoc}
     */
    @Override
    public boolean deleteNotifica(Immagine immagine) {
               boolean result=false;
        try {
            result=Database.deleteRecord("notifiche_immagini","immagine="+immagine.getId());
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
   
     /** 
     *{@inheritDoc}
     */
    @Override
    public boolean deleteNotifica(String table,int oggetto) {
                       boolean result=false;
        try {
            if(table.equals("notifiche_immagini"))
            result=Database.deleteRecord(table,"immagine="+oggetto);
            else if(table.equals("notifiche_trascrizione"))
            result=Database.deleteRecord(table,"trascrizione="+oggetto);
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
     /** 
     *{@inheritDoc}
     */
    @Override
    public boolean deleteNotifica(Trascrizione trascrizione) {
                    boolean result=false;
        try {
            result=Database.deleteRecord("notifiche_trascrizione","trascrizione="+trascrizione.getId());
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
     /** 
     *{@inheritDoc}
     */
    @Override
    public boolean addNotifica(String table,Notifica notifica) {
                      boolean result=false;
                          Map data= new HashMap();
              data.put("mittente_destinatario", notifica.getMittente_destinatario());
              data.put("notifica", notifica.getNotifica());
              try{
               if(table.equals("notifiche_immagini")){
                   data.put("immagine",notifica.getOggetto());
            result=Database.insertRecord(table,data);}
            else if(table.equals("notifiche_trascrizione")){
                    data.put("trascrizione",notifica.getOggetto());
            result=Database.insertRecord(table,data);}
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public int getGruppoUltimaNotificaImmagine(int immagine) {
        int result=-1;
        try {
            ResultSet rs=Database.selectJoinLimit1("utenti", "notifiche_immagini", "utenti.ID=mittente_destinatario", "immagine="+immagine, "orario DESC");
            if(rs.next())result=rs.getInt("gruppo");
        } catch (SQLException ex) {
            Logger.getLogger(NotificaDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
     /** 
     *{@inheritDoc}
     */
    @Override
    public int getGruppoUltimaNotificaImmagine(Immagine immagine) {
             int result=-1;
        try {
            ResultSet rs=Database.selectJoinLimit1("utenti", "notifiche_immagini", "utenti.ID=mittente_destinatario", "immagine="+immagine.getId(), "orario DESC");
            if(rs.next())result=rs.getInt("gruppo");
        } catch (SQLException ex) {
            Logger.getLogger(NotificaDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
     /** 
     *{@inheritDoc}
     */
    @Override
    public int getGruppUltimaNotificaTrascrizione(Trascrizione trascrizione) {
                     int result=-1;
        try {
            ResultSet rs=Database.selectJoinLimit1("utenti", "notifiche_trascrizione", "utenti.ID=mittente_destinatario", "trascrizione="+trascrizione.getId(), "orario DESC");
            if(rs.next())result=rs.getInt("gruppo");
        } catch (SQLException ex) {
            Logger.getLogger(NotificaDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
        
     /** 
     *{@inheritDoc}
     */
       @Override
    public int getGruppUltimaNotificaTrascrizione(int trascrizione) {
                     int result=-1;
        try {
            ResultSet rs=Database.selectJoinLimit1("utenti", "notifiche_trascrizione", "utenti.ID=mittente_destinatario", "trascrizione="+trascrizione, "orario DESC");
            if(rs.next())result=rs.getInt("gruppo");
        } catch (SQLException ex) {
            Logger.getLogger(NotificaDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
     /** 
     *{@inheritDoc}
     */
    @Override
    public boolean deleteNotificheUtente(String table,Utente u) {
                               boolean result=false;
        try {
            if(table.equals("notifiche_immagini"))
            Database.deleteRecord(table, "mittente_destinatario="+u.getId());
            else if(table.equals("notifiche_trascrizione"))
            Database.deleteRecord(table, "mittente_destinatario="+u.getId());
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public boolean deleteNotificheUtente(String table,int u) {
         boolean result=false;
          try {
            if(table.equals("notifiche_immagini"))
            Database.deleteRecord(table, "mittente_destinatario="+u);
            else if(table.equals("notifiche_trascrizione"))
            Database.deleteRecord(table, "mittente_destinatario="+u);
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    }
    

