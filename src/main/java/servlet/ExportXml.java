package servlet;
import controller.UserSessionBean;

import javax.ejb.FinderException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;

public class ExportXml extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("getxml/xml");
        UserSessionBean userSessionBean = (UserSessionBean) req.getSession().getAttribute("userBean");
        Integer clientId = null;
        if(req.getParameter("clientId") != null) {
            clientId = Integer.parseInt(req.getParameter("clientId"));
        }
        ServletOutputStream servletOutputStream = null;
        try {
            servletOutputStream = resp.getOutputStream();
            userSessionBean.exportIntoXml(servletOutputStream, clientId);
            servletOutputStream.flush();
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        } catch (FinderException e) {
            throw new ServletException(e.getMessage());
        } finally {
            if(servletOutputStream != null) servletOutputStream.close();
        }

    }
}
