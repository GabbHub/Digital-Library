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
package view;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
/**
 *Classe per la gestione delle configurazioni e l'utilizzo della libreria
 * FreeMarker
 * @author SS
 */
public class FreeMarker {
    
    /**
     * 
     * @param data              dati da inserire nel template          
     * @param path_template     pathname del template da caricare
     * @param response          servlet response
     * @param servlet_context   contesto della servlet
     * @throws IOException
     */
    
    public static void process(String path_template, Map data, HttpServletResponse response, ServletContext servlet_context) throws IOException{
        
        response.setContentType("text/html;charset=ISO-8859-1");        
        // Configurazione freemarker
        Configuration cfg = new Configuration();
        
        cfg.setDefaultEncoding("ISO-8859-1");
            
        cfg.setServletContextForTemplateLoading(servlet_context, "/");

        Template template = cfg.getTemplate(path_template);
        
        PrintWriter out = response.getWriter();
        
        try{
            template.process(data, out);
            
        } catch (TemplateException ex) {     
            Logger.getLogger(FreeMarker.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            out.flush();
            out.close(); 
        }
        
    }

}