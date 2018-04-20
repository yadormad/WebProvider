package servlet;

import controller.UserSessionBean;
import model.xml.AllDbXml;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@MultipartConfig
public class ImportXml extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserSessionBean userSessionBean = (UserSessionBean) req.getSession().getAttribute("userBean");
        Part importXmlFilePart = req.getPart("xmlfile");
        String message = "";
        try {
            userSessionBean.importXml(importXmlFilePart);
            message = "xml import succ";
        } catch (SAXException e) {
            message = "xml file isn't valid " + e.getMessage();
        } catch (JAXBException e) {
            e.printStackTrace();
        } finally {
            resp.sendRedirect("/WebProviderWeb/import.jsp?ImportMessage=" + message);
        }
    }
}
