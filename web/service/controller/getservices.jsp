<%@ page import="entity.transport.TransportEntity" %>
<%@ page import="entity.impl.ServiceType" %>
<%@ page import="entity.impl.Service" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="entity.impl.Client" %>
<%@ page import="java.util.*" %>
<%@ page errorPage="../../error_page.jsp" %>
<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 30.03.2018
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!private TransportEntity responseEntity;

    private String viewAsRows(HttpSession session) {
        StringBuilder rowBuilder = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Collection<Service> serviceCollection = (Collection<Service>) responseEntity.getResponseProviderEntities();
        ArrayList<ServiceType> unusedServiceTypes = new ArrayList<>(Arrays.asList(ServiceType.values()));
        if(serviceCollection != null && !serviceCollection.isEmpty()) {
            for (Service nextService : serviceCollection) {
                rowBuilder.append("    <tr>\n")
                        .append("        <td>").append(nextService.getType()).append("</td>\n")
                        .append("        <td>").append(nextService.getName()).append("</td>\n")
                        .append("        <td>").append(dateFormat.format(nextService.getServiceProvisionDate())).append("</td>\n")
                        .append("        <td>").append(dateFormat.format(nextService.getServiceDisablingDate())).append("</td>\n")
                        .append("        <td><form class=tableform action=../service/update.jsp method=post>\n")
                        .append("           <button class=inputform type=submit name=updateServiceButton value=").append(nextService.getId()).append(">Update</button>")
                        .append("         </form>").append("\n")
                        .append("        <form class=tableform action=../client/services.jsp method=post>\n")
                        .append("           <button class=inputform type=submit name=deleteServiceButton value=").append(nextService.getId()).append(">Delete</button>")
                        .append("         </form>").append("</td>\n")
                        .append("    </tr>\n");
                unusedServiceTypes.remove(nextService.getType());
            }
        } else {
            rowBuilder.append(responseEntity.getMessage());
        }
        session.setAttribute("unusedTypes", unusedServiceTypes);
        for(ServiceType serviceType:unusedServiceTypes) {
            rowBuilder.append("    <tr>\n")
                    .append("        <td>").append(serviceType.toString()).append("</td>\n")
                    .append("        <td colspan=\"4\">")
                    .append("    </tr>\n");
        }
        return rowBuilder.toString();
    }
%>

<%@include file="deleteservice.jsp"%>

<%
    Integer clientId = null ;
    if(session.getAttribute("clientButton") == null) {
        clientId = Integer.parseInt(request.getParameter("clientButton"));
        session.setAttribute("clientButton", clientId);
    } else {
        clientId = (Integer) session.getAttribute("clientButton");
    }
    request.setAttribute("command", "getclient");
    request.setAttribute("requestObject", clientId);
    request.getRequestDispatcher("/transport/util.jsp").include(request, response);
    Client client = (Client) ((TransportEntity) request.getAttribute("responseEntity")).getResponseProviderEntities().toArray()[0];
    request.setAttribute("command", "clientservices");
    request.getRequestDispatcher("/transport/util.jsp").include(request, response);
    responseEntity = (TransportEntity) request.getAttribute("responseEntity");
%>
