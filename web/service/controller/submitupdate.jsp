<%@ page import="entity.transport.TransportEntity" %>
<%@ page import="entity.impl.Service" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.io.IOException" %>
<%@ page errorPage="../../error_page.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 30.03.2018
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private void updateService(HttpServletRequest request, HttpServletResponse response) throws ServletException, ParseException, IOException {
        Service service = new Service();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        service.setId(serviceId);
        service.setName(request.getParameter("name"));
        service.setServiceProvisionDate(dateFormat.parse(request.getParameter("provisionDate")));
        service.setServiceDisablingDate(dateFormat.parse(request.getParameter("disablingDate")));
        request.setAttribute("command", "updservice");
        request.setAttribute("requestObject", service);
        request.getRequestDispatcher("/transport/util.jsp").include(request, response);
        String message = ((TransportEntity) request.getAttribute("responseEntity")).getMessage();
        if(message.startsWith("Error")) {
            request.setAttribute("error", message);
        }
    }
%>