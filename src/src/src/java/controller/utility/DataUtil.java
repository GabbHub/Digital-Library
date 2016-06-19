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
package controller.utility;

import view.FreeMarker;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList; 
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAO.ImmagineDAOImplements;
import model.transfer_objects.Opera;

/**
 * Classe con due funzioni statiche utili per le password e la presentazione
 * delle opere.
 * @author SS
 */
public class DataUtil {
    
    
    /**
     * 
     * Prende in input una stringa, e ci ritorna la stringa criptata con 
     * algoritmo "SHA-256".
     * 
     * @param string    stringa da criptare       
     * @return String   stringa criptata
     * 
     */
    
    public static String crypt(String string){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = string.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<digested.length;i++){
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FreeMarker.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }          
  }
    
/**
     * 
     * Prende in input una lista di oggettiOpera, e vi aggiunge la prima pagina
     * (Copertina se presente)
     * 
     * @param lista   lista delle opere         
     * @see Opera
     */
    
    public static void setCopertine(ArrayList<Opera> lista){
                ImmagineDAOImplements i= new ImmagineDAOImplements();
                lista.stream().forEach((op) -> {
                    op.setCopertina(i.getImmagineSingola(op.getId(), 0));
        });   
    }
    
}

