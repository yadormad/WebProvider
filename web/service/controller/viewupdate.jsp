<%@ page import="entity.impl.Client" %><%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 30.03.2018
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private void checkId(HttpServletRequest request, HttpServletResponse response, int clientId) throws ServletException, IOException {
        request.setAttribute("command", "getclient");
        request.setAttribute("requestObject", clientId);
        request.getRequestDispatcher("/transport/util.jsp").include(request, response);
        request.setAttribute("command", "checkId");
        request.setAttribute("message", "updservice");
        request.setAttribute("requestObject", serviceId);
        request.getRequestDispatcher("/transport/util.jsp").include(request, response);
        String isAvailableMessage = ((TransportEntity) request.getAttribute("responseEntity")).getMessage();
        if (!isAvailableMessage.equalsIgnoreCase("true")) {
            request.setAttribute("blocked", isAvailableMessage);
        }
    }
%>
<%
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    if(request.getParameter("updateServiceButton") != null) {
        serviceId = Integer.parseInt(request.getParameter("updateServiceButton"));
    }
    if(request.getParameter("updButton") != null) {
        serviceId = Integer.parseInt(request.getParameter("updButton"));
        updateService(request, response);
    }
    request.setAttribute("command", "getservice");
    request.setAttribute("requestObject", serviceId);
    request.getRequestDispatcher("/transport/util.jsp").include(request, response);
    Service service = (Service) ((TransportEntity) request.getAttribute("responseEntity")).getResponseProviderEntities().toArray()[0];
    int clientId = service.getClientId();
    checkId(request, response, clientId);
    request.setAttribute("command", "getclient");
    request.setAttribute("requestObject", clientId);
    request.getRequestDispatcher("/transport/util.jsp").include(request, response);
    Client client = (Client) ((TransportEntity) request.getAttribute("responseEntity")).getResponseProviderEntities().toArray()[0];
%>
