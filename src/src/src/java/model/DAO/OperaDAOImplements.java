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
import model.transfer_objects.Opera;
import model.transfer_objects.Utente;

/**
 *
 * @author SS
 */
public class OperaDAOImplements implements OperaDAO{
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Opera> getOpere() {
      
            ArrayList<Opera> listaopere;
            listaopere = new ArrayList<>();
        try {            
            ResultSet rs=Database.selectRecord("opere", "1");
            while(rs.next()){
               listaopere.add(new Opera(
                       rs.getInt("ID"),
                       rs.getString("Autore"),
                       rs.getString("Anno"),
                       rs.getString("Lingua"),
                       rs.getString("Titolo"),
                       rs.getInt("numero_pagine"),
                       rs.getBoolean("Pubblicazione_trascrizione"),
                       rs.getBoolean("Pubblicazione_immagini")
                       )); 
               
            }
   
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaopere; 
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Opera getOperaUtente(Utente u) {
        Opera o = null;
        try {            
            ResultSet rs=Database.selectJoin("interventi", "opere","opera=opere.ID","interventi.utente="+u.getId());
            while(rs.next()){
              o=new Opera(
                       rs.getInt(5),
                       rs.getString("Autore"),
                       rs.getString("Anno"),
                       rs.getString("Lingua"),
                       rs.getString("Titolo"),
                      rs.getInt("numero_pagine"),
                       rs.getBoolean("Pubblicazione_trascrizione"),
                       rs.getBoolean("Pubblicazione_immagini")
                       ); 
            }
   
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o; 
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Opera getOperaUtente(int utente) {
        Opera o = null;
        try {            
            ResultSet rs=Database.selectJoin("interventi", "opere","opera=opere.ID","interventi.utente="+utente);
            while(rs.next()){
              o=new Opera(
                       rs.getInt(5),
                       rs.getString("Autore"),
                       rs.getString("Anno"),
                       rs.getString("Lingua"),
                       rs.getString("Titolo"),
                       rs.getInt("numero_pagine"),
                       rs.getBoolean("Pubblicazione_trascrizione"),
                       rs.getBoolean("Pubblicazione_immagini")
                       ); 
            }
   
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o; 
    }
    
        /**
     * {@inheritDoc}
     */
    @Override
    public int contaOperePubblicate() {
        try { 
            return Database.countRecord("opere", "Pubblicazione_immagini=1");
        } catch (SQLException ex) {
            Logger.getLogger(OperaDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    @Override
    public int contaOpereRicerca(String parameter) {
         try { 
            return Database.countRecordricerca("opere",parameter);
        } catch (SQLException ex) {
            Logger.getLogger(OperaDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Opera> getOperePubblicate(int parametro) {
    
           ArrayList<Opera> listaopere;
            listaopere = new ArrayList<>();
        try {            
            ResultSet rs=Database.selectRecordLimitxy("opere", "Pubblicazione_immagini=1","ID",parametro,4);
            while(rs.next()){
               listaopere.add(new Opera(
                       rs.getInt("ID"),
                       rs.getString("Autore"),
                       rs.getString("Anno"),
                       rs.getString("Lingua"),
                       rs.getString("Titolo"),
                       rs.getInt("numero_pagine"),
                       rs.getBoolean("Pubblicazione_trascrizione"),
                       rs.getBoolean("Pubblicazione_immagini")
                       )); 
            }
   
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaopere; 
          
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Opera> getOpereNonPubblicate() {
    
           ArrayList<Opera> listaopere;
            listaopere = new ArrayList<>();
        try {            
            ResultSet rs=Database.selectRecord("opere", "Pubblicazione_immagini=0");
            while(rs.next()){
               listaopere.add(new Opera(
                       rs.getInt("ID"),
                       rs.getString("Autore"),
                       rs.getString("Anno"),
                       rs.getString("Lingua"),
                       rs.getString("Titolo"),
                       rs.getInt("numero_pagine"),
                       rs.getBoolean("Pubblicazione_trascrizione"),
                       rs.getBoolean("Pubblicazione_immagini")
                       )); 
            }
   
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaopere; 
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Opera> getOpereNonTrascritte() {
      
            ArrayList<Opera> listaopere;
            listaopere = new ArrayList<>();
        try {            
            ResultSet rs=Database.selectRecord("opere", "Pubblicazione_trascrizione=0");
            while(rs.next()){
               listaopere.add(new Opera(
                       rs.getInt("ID"),
                       rs.getString("Autore"),
                       rs.getString("Anno"),
                       rs.getString("Lingua"),
                       rs.getString("Titolo"),
                       rs.getInt("numero_pagine"),
                       rs.getBoolean("Pubblicazione_trascrizione"),
                       rs.getBoolean("Pubblicazione_immagini")
                       )); 
            }
   
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaopere; 
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Opera getOperaSingola(String titolo) {
        
            Opera opera = null;
        try {            
            ResultSet rs=Database.selectRecord("opere", "Titolo='"+titolo+"'");
            while(rs.next()){
                opera=new Opera(
                       rs.getInt("ID"),
                       rs.getString("Autore"),
                       rs.getString("Anno"),
                       rs.getString("Lingua"),
                       rs.getString("Titolo"),
                       rs.getInt("numero_pagine"),
                       rs.getBoolean("Pubblicazione_trascrizione"),
                       rs.getBoolean("Pubblicazione_immagini")
                       ); 
            }
   
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return opera; 
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Opera getOperaSingola(int id) {
     
                    Opera opera = null;
        try {            
            ResultSet rs=Database.selectRecord("opere", "ID="+id);
            while(rs.next()){
                opera=new Opera(
                       rs.getInt("ID"),
                       rs.getString("Autore"),
                       rs.getString("Anno"),
                       rs.getString("Lingua"),
                       rs.getString("Titolo"),
                       rs.getInt("numero_pagine"),
                       rs.getBoolean("Pubblicazione_trascrizione"),
                       rs.getBoolean("Pubblicazione_immagini")
                       ); 
            }
   
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return opera; 
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateOpera(Map<String, Object> data, String condizione) {      
               boolean result=false;
        try {
            result=Database.updateRecord("opere", data, condizione);
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;    
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteOpera(String titolo) {
             boolean result=false;
        try {
            result=Database.deleteRecord("opere","Titolo='"+titolo+"'");
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteOpera(int id) {
             boolean result=false;
        try {
            result=Database.deleteRecord("opere","ID="+id);
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteOpera(Opera opera) {
       return deleteOpera(opera.getId());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addOpera(Opera opera) {
       
              boolean result=false;
              Map data= new HashMap();
              data.put("Autore", opera.getAutore());
              data.put("Titolo", opera.getTitolo());
              data.put("Anno", opera.getAnno());
              data.put("Lingua", opera.getLingua());
              data.put("numero_pagine", opera.getNumero_pagine());
                 if(opera.isPubblicazione_trascrizioni())
              data.put("Pubblicazione_trascrizione", 1);
              else data.put("Pubblicazione_trascrizione",0);
                 if(opera.isPubblicazione_immagini())
                     data.put("Pubblicazione_immagini",1);
                             else data.put("Pubblicazione_immagini",0);
        try {
            result=Database.insertRecord("opere", data);
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int contaTrascrizionivalidateOpera(Opera opera) {
            try {
            return Database.countJoin("immagini_digitali", "trascrizione", "immagini_digitali.ID=trascrizione.immagine", "trascrizione.validazione=1 && opera="+opera.getId());
        } catch (SQLException ex) {
            Logger.getLogger(OperaDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int contaTrascrizionivalidateOpera(int opera) {
                 try {
            return Database.countJoin("immagini_digitali", "trascrizione", "immagini_digitali.ID=trascrizione.immagine", "trascrizione.validazione=1 && opera="+opera);
        } catch (SQLException ex) {
            Logger.getLogger(OperaDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public ArrayList<Opera> getOperePubblicate(String parametro_ricerca,int start) {
           
           ArrayList<Opera> listaopere;
            listaopere = new ArrayList<>();
        try {            
            ResultSet rs=Database.selectRicercaLimitxy(parametro_ricerca,start,4);
            while(rs.next()){
               listaopere.add(new Opera(
                       rs.getInt("ID"),
                       rs.getString("Autore"),
                       rs.getString("Anno"),
                       rs.getString("Lingua"),
                       rs.getString("Titolo"),
                       rs.getInt("numero_pagine"),
                       rs.getBoolean("Pubblicazione_trascrizione"),
                       rs.getBoolean("Pubblicazione_immagini")
                       )); 
            }
   
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAOImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaopere; 
    }

}
