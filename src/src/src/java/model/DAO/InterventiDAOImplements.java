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
import java.util.Map;

/**
 *classe per l'implementazione dell'interfaccia InterventiDAO
 * 
 * @author SS
 */
public class InterventiDAOImplements implements InterventiDAO{

     /** 
     *{@inheritDoc}
     */
    @Override
    public boolean deleteInterventiNonTrascrizione(int opera) throws SQLException {
        return Database.deleteRecordNotIn("interventi",
                       "utenti",
                       "opera="+opera,
                       "interventi.utente", 
                        "ID",
                        "gruppo!=2 && gruppo!=3");
    }
    
     /** 
     *{@inheritDoc}
     */
    @Override
    public boolean deleteInterventiOpera(int opera) throws SQLException {
        return Database.deleteRecord("interventi", "opera="+opera);
    }
    
     /** 
     *{@inheritDoc}
     */
    @Override
    public int contaInterventiOpera(int opera) throws SQLException {
        return Database.countRecord("interventi", "opera="+opera);
    }
    
     /** 
     *{@inheritDoc}
     */
    @Override
    public boolean addIntervento(Map map) throws SQLException {
        return Database.insertRecord("interventi", map);
    }
    
     /** 
     *{@inheritDoc}
     */
    @Override
    public boolean deleteInterventiUtente(int utente) throws SQLException {
       return Database.deleteRecord("interventi", "utente="+utente);
    }
    
    /** 
     *{@inheritDoc}
     */
    @Override
    public String getTitoloOperaInterventoUtente(int utente) throws SQLException {
        String s = null;
     ResultSet rs=Database.selectJoin("interventi", "opere", "interventi.opera=opere.ID","utente="+utente);
               while(rs.next())s=rs.getString("titolo");
               return s;
    }
    
}
