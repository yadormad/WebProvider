<%@ page import="entity.impl.Service" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="entity.impl.ServiceType" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="entity.transport.TransportEntity" %>
<%@ page import="java.util.Collection" %>
<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 30.03.2018
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private void addService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        Service service = new Service();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        service.setType(ServiceType.valueOf(request.getParameter("serviceType").toUpperCase()));
        service.setName(request.getParameter("name"));
        service.setServiceProvisionDate(dateFormat.parse(request.getParameter("provisionDate")));
        service.setServiceDisablingDate(dateFormat.parse(request.getParameter("disablingDate")));
        service.setClientId(client.getId());
        request.setAttribute("command", "addservice");
        request.setAttribute("requestObject", service);
        request.getRequestDispatcher("/transport/util.jsp").include(request, response);
        String message = ((TransportEntity) request.getAttribute("responseEntity")).getMessage();
        if(message.startsWith("Error")) {
            request.setAttribute("error", message);
        } else {
            Collection<ServiceType> unusedServiceTypes = (Collection<ServiceType>) request.getSession().getAttribute("unusedTypes");
            unusedServiceTypes.remove(service.getType());
        }
    }
%>
