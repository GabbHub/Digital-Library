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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *Classe per la gestione delle query al Database
 * @author SS
 */

public class Database {
     private static Connection db;
    
    /**
     * Connessione al database
     * @throws javax.naming.NamingException
     * @throws java.sql.SQLException
     */
    public static void connect() throws NamingException, SQLException{
        InitialContext ctx = new InitialContext();
          DataSource ds=
                       (DataSource) ctx.lookup("java:comp/env/digital_library");
        Database.db  = ds.getConnection();
    }
    
    /**
     * Chiusura connessione al database
     * @throws java.sql.SQLException
     */
    public static void close() throws SQLException{
        Database.db.close();
    }
    
    /**
     * Select record con condizione
     * @param table         tabella da cui prelevare i dati
     * @param condition     condizione per il filtro dei dati
     * @return              dati prelevati
     * @throws java.sql.SQLException
     */
    public static ResultSet selectRecord(String table, String condition) throws SQLException {
        // Generazione query
          
        String query = "SELECT * FROM " + table + " WHERE " + condition;
        // Esecuzione query
        return Database.executeQuery(query);
    }
    
    /**
     * Select record con condizione e ordinamento
     * @param table         tabella da cui prelevare i dati
     * @param condition     condizione per il filtro dei dati
     * @param order         ordinamento dei dati
     * @return              dati prelevati
     * @throws java.sql.SQLException
     */
    public static ResultSet selectRecord(String table, String condition, String order) throws SQLException{
        // Generazione query
        String query = "SELECT * FROM " + table + " WHERE " + condition + " ORDER BY " + order;
        // Esecuzione query
        return Database.executeQuery(query);
    }
    
    /**
     * Select record con condizione e ordinamento è limite a 1
     * @param table         tabella da cui prelevare i dati
     * @param condition     condizione per il filtro dei dati
     * @param order         ordinamento dei dati
     * @return              dati prelevati
     * @throws java.sql.SQLException
     */
    
     public static ResultSet selectRecordLimit1(String table, String condition, String order) throws SQLException{
        // Generazione query
        String query = "SELECT * FROM " + table + " WHERE " + condition + " ORDER BY " + order + " LIMIT 1;";
        // Esecuzione query
        return Database.executeQuery(query);
    }
        /**
     * select record con condizione,ordinamento, indice di partenza e quantità
     * di record nel risultato
     *
     * @param table
     * @param condition
     * @param order
     * @param start
     * @param quantity
     * @return
     * @throws SQLException
     */
    public static ResultSet selectRecordLimitxy(String table, String condition, String order, int start, int quantity) throws SQLException {
        // Generazione query
        String query = "SELECT * FROM " + table + " WHERE " + condition + " ORDER BY " + order + " LIMIT " + start + "," + quantity;
        // Esecuzione query
        return Database.executeQuery(query);
    }
    
        /**
     * query personalizzata per la ricerca, vede se una stringa è contenuta
     * all'inizio, all'interno, o alla fine dei campi autore titolo anno lingua
     * limitando il risultato a partire dall 'indice
     * start e per la quantità quantity
     *
     * @param parameter
     * @param start
     * @param quantity
     * @return
     * @throws SQLException
     */
    public static ResultSet selectRicercaLimitxy(String parameter, int start, int quantity) throws SQLException {
        // Generazione query
        String query = "SELECT * FROM opere "
                + "WHERE Pubblicazione_immagini=1 && Autore LIKE '%" + parameter + "%' || Titolo LIKE '%" + parameter 
                + "%' || Lingua LIKE '%" + parameter + "%' || Anno LIKE '" + parameter 
                + "' LIMIT " + start + "," + quantity;
        // Esecuzione query
        return Database.executeQuery(query);
    }
    
    
    /**
     * Select record con join tra due tabelle
     * @param table_1           nome della prima tabella
     * @param table_2           nome della seconda tabella
     * @param join_condition    condizione del join tra la tabelle
     * @param where_condition   condizione per il filtro dei dati
     * @return                  dati prelevati
     * @throws java.sql.SQLException
     */
     
    public static ResultSet selectJoin(String table_1, String table_2, String join_condition, String where_condition) throws SQLException{
        // Generazione query
        String query = "SELECT * FROM " + table_1 + " JOIN " + table_2 + " ON " + join_condition + " WHERE " + where_condition;
        // Esecuzione query
        return Database.executeQuery(query);
    }
    
         /**
     * Select record con join tra tre tabelle e ordinamento
     * @param table_1           nome della prima tabella
     * @param table_2           nome della seconda tabella
     * @param table_3           nome della terza tabella
     * @param join_condition_1  condizione del join tra le prime due tabelle
     * @param join_condition_2  condizione del join tra le ultime due tabelle
     * @param where_condition   condizione per il filtro dei dati
     * @return                  dati prelevati
     * @throws java.sql.SQLException
     */
    
    public static ResultSet selectJoin(String table_1, String table_2, String table_3, String join_condition_1, String join_condition_2, String where_condition) throws SQLException{
        // Generazione query
        String query = "SELECT * FROM " + table_1 + " JOIN " + table_2 + " ON " + join_condition_1 +" JOIN " + table_3 +" ON "+join_condition_2+ " WHERE " + where_condition;
        // Esecuzione query
        return Database.executeQuery(query);
    }
    
    /**
     * Select record con join tra due tabelle e ordinamento
     * @param table_1           nome della prima tabella
     * @param table_2           nome della seconda tabella
     * @param join_condition    condizione del join tra la tabelle
     * @param where_condition   condizione per il filtro dei dati
     * @param order             ordinamento dei dati
     * @return                  dati prelevati
     * @throws java.sql.SQLException
     */
    
    public static ResultSet selectJoin(String table_1, String table_2, String join_condition, String where_condition, String order) throws SQLException{
        // Generazione query
        String query = "SELECT * FROM " + table_1 + " JOIN " + table_2 + " ON " + join_condition + " WHERE " + where_condition + " ORDER BY " + order;
        // Esecuzione query
        return Database.executeQuery(query);
    }
    
     /**
     * Select record con join tra due tabelle e ordinamento e limite a 1
     * @param table_1           nome della prima tabella
     * @param table_2           nome della seconda tabella
     * @param join_condition    condizione del join tra le tabelle
     * @param where_condition   condizione per il filtro dei dati
     * @param order             ordinamento dei dati
     * @return                  dati prelevati
     * @throws java.sql.SQLException
     */
    
    public static ResultSet selectJoinLimit1(String table_1, String table_2, String join_condition, String where_condition, String order) throws SQLException{
        // Generazione query
        String query = "SELECT * FROM " + table_1 + " JOIN " + table_2 + " ON " + join_condition + " WHERE " + where_condition + " ORDER BY " + order + " LIMIT 1;";
        // Esecuzione query
        return Database.executeQuery(query);
    }
    
        /**
     * Select record con join tra tre tabelle e ordinamento
     * @param table_1           nome della prima tabella
     * @param table_2           nome della seconda tabella
     * @param table_3           nome della terza tabella
     * @param join_condition_1  condizione del join tra le prime due tabelle
     * @param join_condition_2  condizione del join tra le ultime due tabelle
     * @param where_condition   condizione per il filtro dei dati
     * @param order             ordinamento dei dati
     * @return                  dati prelevati
     * @throws java.sql.SQLException
     */
    
    public static ResultSet selectJoin(String table_1, String table_2, String table_3, String join_condition_1,String join_condition_2, String where_condition, String order) throws SQLException{
        // Generazione query
         String query = "SELECT * FROM " + table_1 + " JOIN " + table_2 + " ON " + join_condition_1 +" JOIN " + table_3 +" ON "+join_condition_2+ " WHERE " + where_condition+ " ORDER BY " + order;
        // Esecuzione query
        return Database.executeQuery(query);
    }
        
           /**
     * Select record con join tra tre tabelle e ordinamento e limite1
     * @param table_1           nome della prima tabella
     * @param table_2           nome della seconda tabella
     * @param table_3           nome della terza tabella
     * @param join_condition_1  condizione del join tra le prime due tabelle
     * @param join_condition_2  condizione del join tra le ultime due tabelle
     * @param where_condition   condizione per il filtro dei dati
     * @param order             ordinamento dei dati
     * @return                  dati prelevati
     * @throws java.sql.SQLException
     */
    
    public static ResultSet selectJoinLimit1(String table_1, String table_2, String table_3, String join_condition_1,String join_condition_2, String where_condition, String order) throws SQLException{
        // Generazione query
         String query = "SELECT * FROM " + table_1 + " JOIN " + table_2 + " ON " + join_condition_1 +" JOIN " + table_3 +" ON "+join_condition_2+ " WHERE " + where_condition+ " ORDER BY " + order + " LIMIT 1;";
        // Esecuzione query
        return Database.executeQuery(query);
    }
         
        /**
     * Select record con join tra due tabelle e raggruppamento
     * @param table_1           nome della prima tabella
     * @param table_2           nome della seconda tabella
     * @param join_condition    condizione del join tra le prime due tabelle
     * @param where_condition   condizione per il filtro dei dati
     * @param groupbycondition  condizione per il raggruppamento
     * @return                  dati prelevati
     * @throws java.sql.SQLException
     */
    
    public static ResultSet selectJoinGroupBy(String table_1, String table_2, String join_condition,String where_condition, String groupbycondition) throws SQLException{
        // Generazione query
         String query = "SELECT * FROM " + table_1 + " JOIN " + table_2 + " ON " + join_condition + " WHERE " + where_condition+ " GROUP BY " + groupbycondition;
        // Esecuzione query
        return Database.executeQuery(query);
    }
       
        /**
     * Select record con join tra tre tabelle e raggruppamento
     * @param table_1           nome della prima tabella
     * @param table_2           nome della seconda tabella
     * @param table_3           nome della terza tabella
     * @param join_condition_1  condizione del join tra le prime due tabelle
     * @param join_condition_2  condizione del join tra le ultime due tabelle
     * @param where_condition   condizione per il filtro dei dati
     * @param groupbycondition  condizione per il raggruppamento
     * @return                  dati prelevati
     * @throws java.sql.SQLException
     */
    
    public static ResultSet selectJoinGroupBy(String table_1, String table_2, String table_3, String join_condition_1,String join_condition_2, String where_condition, String groupbycondition) throws SQLException{
        // Generazione query
         String query = "SELECT * FROM " + table_1 + " JOIN " + table_2 + " ON " + join_condition_1 +" JOIN " + table_3 +" ON "+join_condition_2+ " WHERE " + where_condition+ " GROUP BY " + groupbycondition;
        // Esecuzione query
        return Database.executeQuery(query);
    }

    /**
     * Insert record
     * @param table     tabella in cui inserire i dati
     * @param data      dati da inserire
     * @return dati     prelevati
     * @throws java.sql.SQLException
     */
    
    public static boolean insertRecord(String table, Map<String, Object> data) throws SQLException{
        // Generazione query
        String query = "INSERT INTO " + table + " SET ";
        Object value;
        String attr;
        
        for(Map.Entry<String,Object> e:data.entrySet()){
            attr = e.getKey();
            value = e.getValue();
            if(value instanceof Integer){
                query = query + attr + " = " + value + ", ";
            }else{
                value = value.toString().replace("\'", "\\'");
                query = query + attr + " = '" + value + "', ";
            }
        }
        query = query.substring(0, query.length() - 2);
        // Esecuzione query
        return Database.updateQuery(query);
    }
    
    /**
     * Update record
     * @param table         tabella in cui aggiornare i dati
     * @param data          dati da inserire
     * @param condition     condizione per il filtro dei dati
     * @return              true se l'inserimento è andato a buon fine, false altrimenti
     * @throws java.sql.SQLException
     */
    public static boolean updateRecord(String table, Map<String,Object> data, String condition) throws SQLException{
        // Generazione query
        String query = "UPDATE " + table + " SET ";
        
        Object value;
        String attr;
        
        for(Map.Entry<String,Object> e:data.entrySet()){
            attr = e.getKey();
            value = e.getValue();
            if(value instanceof String){
                value = value.toString().replace("\'", "\\'");
                query = query + attr + " = '" + value + "', ";
            }else{
                query = query + attr + " = " + value + ", ";
            }  
        }
        query = query.substring(0, query.length()-2) + " WHERE " + condition;
        
        // Esecuzione query
        return Database.updateQuery(query);
    }
        /**
     * Rimpiazza il valore di un campo nei record con uno nuovo
     * @param table         tabella in cui eliminare i dati    
     * @param field         campo da filtrare
     * @param old           valore vecchio da sostituire
     * @param brand_new     valore nuovo 
     * @return              true se l'eliminazione è andata a buon fine, false altrimenti
     * @throws java.sql.SQLException
     */
       public static boolean ReplaceRecord(String table,String field, String old, String brand_new)throws SQLException{
          String query = "UPDATE " + 
                  table + 
                  " SET "+field+" = REPLACE("+
                  field+",'"+
                  old.replace("\'", "\\'")+
                  "', '"+brand_new.replace("\'", "\\'")+
                  "')"+" WHERE "+field+" LIKE '%"+old+"%'";
                  return Database.updateQuery(query);
     }
    
    /**
     * Delete record
     * @param table         tabella in cui eliminare i dati
     * @param condition     condizione per il filtro dei dati
     * @return              true se l'eliminazione è andata a buon fine, false altrimenti
     * @throws java.sql.SQLException
     */
       
    public static boolean deleteRecord(String table, String condition) throws SQLException{
        // Generazione query
        String query = "DELETE FROM " + table + " WHERE " + condition;
        // Esecuzione query
        return Database.updateQuery(query);
    }
    
     /**
     * Delete record not in another table
     * @param table_1            tabella in cui eliminare i dati
     * @param table_2            tabella nel quale controllare che il valore del campo non sia presente
     * @param condition_delete   condizione della query di Delete sulla prima tabella
     * @param field              campo i cui valori non devono essere presenti nel della seconda tabella
     * @param field_2            campo in cui controllare che i valori del campo uno non siano presenti
     * @param condition_select   condizione della query di Select sulla seconda tabella
     * @return                   true se l'eliminazione è andata a buon fine, false altrimenti
     * @throws java.sql.SQLException
     */
    
    public static boolean deleteRecordNotIn(String table_1,String table_2, String condition_delete,String field,String field_2, String condition_select) throws SQLException{
        // Generazione query
        String query = "DELETE FROM " + table_1 + " WHERE "+condition_delete+" && "+ field +" NOT IN (SELECT "+field_2+" FROM "+table_2+" WHERE "+condition_select+" )";
        return Database.updateQuery(query);
    }
    
    /**
     * Count record
     * @param table         tabella in cui contare i dati
     * @param condition     condizione per il filtro dei dati
     * @return              numero dei record se la query è stata eseguita on successo, -1 altrimenti
     * @throws java.sql.SQLException
     */
    
    public static int countRecord(String table, String condition) throws SQLException{

        // Generazione query
        String query = "SELECT COUNT(*) FROM " + table + " WHERE " + condition;
        // Esecuzione query
        ResultSet record = Database.executeQuery(query);
        record.next();
        // Restituzione del risultato
        return record.getInt(1);

    }
     /**
     * Count record ricerca
     * @param table         tabella in cui contare i dati
     * @param parameter
     * @return              numero dei record se la query è stata eseguita on successo, -1 altrimenti
     * @throws java.sql.SQLException
     */
        public static int countRecordricerca(String table,String parameter) throws SQLException {
                // Generazione query
        String query = "SELECT COUNT(*) FROM " + table + " WHERE Pubblicazione_immagini=1 && ( Autore LIKE '%" + parameter + "%' || Titolo LIKE '%" + parameter 
                + "%' || Lingua LIKE '%" + parameter + "%' || Anno LIKE '" + parameter+"')";
        // Esecuzione query
        ResultSet record = Database.executeQuery(query);
        record.next();
        // Restituzione del risultato
        return record.getInt(1);
        
    }
    
    /**
     * Count record su Join
     * @param table_1           nome della prima tabella
     * @param table_2           nome della prima tabella
     * @param join_condition    condizione del Join
     * @param condition         condizione per il filtro dei dati
     * @return numero dei record se la query è stata eseguita on successo, -1 altrimenti
     * @throws java.sql.SQLException
     */
    public static int countJoin(String table_1, String table_2, String join_condition, String condition) throws SQLException{

        // Generazione query
        String query = "SELECT COUNT(*) FROM " + table_1 + " JOIN " + table_2 + " ON " + join_condition + " WHERE " + condition;
        // Esecuzione query
        ResultSet record = Database.executeQuery(query);
        record.next();
        // Restituzione del risultato
        return record.getInt(1);

    }
    
    /**
     * Imposta a NULL un attributo di una tabella  
     * @param table         tabella in cui è presente l'attributo
     * @param attribute     attributo da impostare a NULL
     * @param condition     condizione
     * @return
     * @throws java.sql.SQLException
     */
    public static boolean resetAttribute(String table, String attribute, String condition) throws SQLException{
        String query = "UPDATE " + table + " SET " + attribute + " = NULL WHERE " + condition;
        return Database.updateQuery(query);
    }

    // <editor-fold defaultstate="collapsed" desc="Metodi ausiliari.">
    
    /**
     * executeQuery personalizzata
     * @param query query da eseguire
     */
    private static ResultSet executeQuery(String query) throws SQLException{
        
        Statement s1 = Database.db.createStatement();
        ResultSet records = s1.executeQuery(query);

        return records; 
            
    }
    
    /**
     * updateQuery personalizzata
     * @param query query da eseguire
     */
    private static boolean updateQuery(String query) throws SQLException{
        
        Statement s1;
        
        s1 = Database.db.createStatement();
        s1.executeUpdate(query); 
        s1.close();
        return true; 

    }
   // </editor-fold>

    
}