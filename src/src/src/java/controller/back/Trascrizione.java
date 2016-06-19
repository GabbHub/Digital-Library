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
package controller.back;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Objects.isNull;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import model.DAO.ImmagineDAOImplements;
import model.DAO.NotificaDAOImplements;
import model.DAO.OperaDAOImplements;
import model.DAO.TrascrizioneDAOImplements;
import model.DAO.UtenteDAOImplements;
import model.transfer_objects.Immagine;
import model.transfer_objects.Notifica;
import model.transfer_objects.Opera;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import model.DAO.Database;
import view.FreeMarker;
import controller.utility.SecurityLayer;


/**
 *Servlet per la trascrizione
 * 
 * @author SS
 */
public class Trascrizione extends HttpServlet {

    
        //stringa privata che conterrà l'errore del parser da far stampare a video
        private String error=null;
    
     /**
     * Handles the error if one occurs.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */   
     private void action_error(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //assumiamo che l'eccezione sia passata tramite gli attributi della request
        //we assume that the exception has been passed using the request attributes    
           Map data=new HashMap();
          
        Exception exception = (Exception) request.getAttribute("exception");
        String message;
        if (exception != null && exception.getMessage() != null) {
            message = exception.getMessage();
        } else {
            message = "Unknown error";
        }
        data.put("errore",message);
        FreeMarker.process("404error.html",data, response, getServletContext());    
    }
 /**
  * 
  * @param xml  stringa contenente la trascrizione tei/xml
  * @return     true se la validazione va  a buon fine, pce se lancia una parserException, 
  *             false altrimenti
  * @throws ParserConfigurationException
  * @throws IOException
  * @throws org.xml.sax.SAXException 
  */              
 private boolean validateWithIntXSDUsingDOM(String xml) throws ParserConfigurationException, IOException, org.xml.sax.SAXException{
 try {
     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
factory.setValidating(false);
factory.setNamespaceAware(true);

SchemaFactory schemaFactory = 
    SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

factory.setSchema(schemaFactory.newSchema(
    new Source[] {new StreamSource(getServletContext().getRealPath("")+"Trascrizioni/tei.openedition.1.4.xsd")}));

DocumentBuilder builder = factory.newDocumentBuilder();

      builder.setErrorHandler(
          new ErrorHandler() {
            @Override
            public void warning(SAXParseException e) throws SAXException {
              error="WARNING: " + e.getMessage();
            }

            @Override
            public void error(SAXParseException e) throws SAXException {
              error="ERROR: " + e.getMessage();
              throw e;
            }

            @Override
            public void fatalError(SAXParseException e) throws SAXException {
              error="FATAL: " + e.getMessage();
              throw e;
            }
          }
    
      );

//builder.parse(new InputSource(getServletContext().getRealPath("")+"Trascrizioni/teistart.xml"));  per file
builder.parse(new InputSource(new StringReader(xml)));

      return true;
    }    
    catch (ParserConfigurationException | IOException pce) {
      throw pce;
    }
    catch (SAXException se){
      return false;
  } 
     } 

                
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws javax.naming.NamingException
     * @throws java.sql.SQLException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     */          
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException, SQLException, ParserConfigurationException, org.xml.sax.SAXException {
          Map data= new HashMap();
        TrascrizioneDAOImplements t=new TrascrizioneDAOImplements();
        UtenteDAOImplements u=new UtenteDAOImplements();
        NotificaDAOImplements n=new NotificaDAOImplements();
        HttpSession s = SecurityLayer.checkSession(request);
         Database.connect();
        String email=u.getUtenteSingolo((int) s.getAttribute("userid")).getEmail();   
       if (!isNull(s) && ((String)s.getAttribute("username")).equals(email+"stafftrascrittore")) {
             Immagine image = null;
           String opera=request.getParameter("opera");
           int  immagine = (request.getParameter("npg") == null) ? 99999 : Integer.parseInt(request.getParameter("npg"));
           if(!isNull(opera) && (immagine!=99999)){
               OperaDAOImplements o=new OperaDAOImplements();
               ImmagineDAOImplements i=new ImmagineDAOImplements();
               Opera op=o.getOperaSingola(opera);
               image=i.getImmagineSingola(op.getId(), immagine);
               data.put("opera",op);
               data.put("immagine",image);
               data.put("staff","Trascrizioni");
           }
           if(!isNull(image)){model.transfer_objects.Trascrizione tr=t.getTrascrizioneImmagine(image.getId());
                  if(!isNull(tr)){
                      File file=new File(getServletContext().getRealPath("")+"Trascrizioni/teicontainer.xml");
                     FileUtils.writeStringToFile(file,tr.getFile(),"UTF-8");
                     data.put("trascrizione","\"Trascrizioni/teicontainer.xml\"");
                    }else data.put("trascrizione","\"Trascrizioni/teistart.xml\"");
                       data.put("opera","\""+opera+"\"");
           }
                  
         //gestico ricezione ajax
         if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
             String x=request.getParameter("xmlString");
             int npg=Integer.parseInt(request.getParameter("npg"));
             JSONObject obj = null;
             if(validateWithIntXSDUsingDOM(x)){
             model.transfer_objects.Trascrizione tr=t.getTrascrizioneImmagine(npg);
             data.put("file",x);
             t.updateTrascrizione(data,"immagine="+Integer.parseInt(request.getParameter("img")));
                 try {
                     obj = new JSONObject().put("success",true);
                 } catch (JSONException ex) {
                     Logger.getLogger(Trascrizione.class.getName()).log(Level.SEVERE, null, ex);
                 }
                try (PrintWriter out = response.getWriter()) {                     
                     out.print(obj);
                 }
             Database.close();
             return;
             }else{try {
                 obj = new JSONObject().put("success",false).put("alert",error.replace("è", "&egrave;").replace("È","&Egrave;").replace("à","&agrave;"));
                 } catch (JSONException ex) {
                     Logger.getLogger(Trascrizione.class.getName()).log(Level.SEVERE, null, ex);
                 }
             try (PrintWriter out = response.getWriter()) {                     
                     out.print(obj);
                 }
             Database.close();
             return;
                 }
         }
         
         if(!isNull(request.getParameter("trascrittore"))){
             int img=Integer.parseInt(request.getParameter("img"));
             File xml=new File(getServletContext().getRealPath("")+"Trascrizioni/teistart.xml");
             if(t.addTrascrizione(new model.transfer_objects.Trascrizione(FileUtils.readFileToString(xml, "UTF-8"),false,img))){
             Notifica nt=new Notifica(
             Integer.parseInt(request.getParameter("trascrittore")),
              "Trascrizione Iniziata",
                     t.getTrascrizioneImmagine(img).getId());
             n.addNotifica("notifiche_trascrizione", nt);
         }}
         
          Database.close();
         
          FreeMarker.process("back_editor.html", data, response, getServletContext());
       }else{ Database.close(); action_error(request,response);}
      
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                    try {
                        processRequest(request, response);
                    } catch (NamingException | SQLException ex) {
                        Logger.getLogger(Trascrizione.class.getName()).log(Level.SEVERE, null, ex);
                        action_error(request, response);
                    } catch (ParserConfigurationException | SAXException ex) {
                Logger.getLogger(Trascrizione.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                              try {
                        processRequest(request, response);
                    } catch (NamingException | SQLException ex) {
                        Logger.getLogger(Trascrizione.class.getName()).log(Level.SEVERE, null, ex);
                        action_error(request, response);
                    } catch (ParserConfigurationException | SAXException ex) {
                Logger.getLogger(Trascrizione.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
