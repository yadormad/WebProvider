<%@ page import="entity.impl.Client" %>
<%@ page import="entity.transport.TransportEntity" %>
<%@ page import="java.util.Collection" %>
<%@ page import="entity.impl.ServiceType" %>
<%@ page import="java.io.IOException" %>
<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 30.03.2018
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
    private String typesRadio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<ServiceType> unusedServiceTypes = (Collection<ServiceType>) request.getSession().getAttribute("unusedTypes");
        if(unusedServiceTypes.isEmpty()) {
            request.setAttribute("error", "No service type available");
            request.getRequestDispatcher("/client/all.jsp").include(request, response);
        }
        StringBuilder typesRadioString = new StringBuilder();
        typesRadioString.append("        <label for=serviceType class = inputform>Service type:</label>");
        for (ServiceType serviceType:unusedServiceTypes) {
            typesRadioString.append("        <input type=radio id=serviceType name=serviceType value=").append(serviceType.toString()).append(">").append(serviceType.toString()).append("</br>");
        }
        typesRadioString.insert(typesRadioString.indexOf(">"), "checked");
        return String.valueOf(typesRadioString);
    }

    private void checkId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int clientId = Integer.parseInt(request.getParameter("addServiceButton"));
        request.setAttribute("command", "getclient");
        request.setAttribute("requestObject", clientId);
        request.getRequestDispatcher("/transport/util.jsp").include(request, response);
        client = (Client) ((TransportEntity) request.getAttribute("responseEntity")).getResponseProviderEntities().toArray()[0];
        request.setAttribute("command", "checkId");
        request.setAttribute("message", "addservice");
        request.setAttribute("requestObject", clientId);
        request.getRequestDispatcher("/transport/util.jsp").include(request, response);
        String isAvailableMessage = ((TransportEntity) request.getAttribute("responseEntity")).getMessage();
        if (!isAvailableMessage.equalsIgnoreCase("true")) {
            request.setAttribute("blocked", isAvailableMessage);
        }
    }
%>
