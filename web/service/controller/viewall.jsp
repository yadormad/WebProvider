<%@ page import="entity.transport.TransportEntity" %>
<%@ page import="entity.impl.Service" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 02.04.2018
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private TransportEntity responseEntity;

    public String viewAsRows(){
        StringBuilder rowBuilder = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Collection<Service> serviceCollection = (Collection<Service>) responseEntity.getResponseProviderEntities();
        if(serviceCollection != null && !serviceCollection.isEmpty()) {
            for (Service nextService : serviceCollection) {
                rowBuilder.append("    <tr>\n")
                        .append("        <td>").append(nextService.getName()).append("</td>\n")
                        .append("        <td>").append(dateFormat.format(nextService.getServiceProvisionDate())).append("</td>\n")
                        .append("        <td>").append(dateFormat.format(nextService.getServiceDisablingDate())).append("</td>\n")
                        .append("    </tr>\n");
            }
        } else {
            rowBuilder.append(responseEntity.getMessage());
        }
        return rowBuilder.toString();
    }
%>

<%
    request.setAttribute("command", "GETSERVICESBYTYPE");
    request.setAttribute("message", session.getAttribute("serviceType"));
    request.getRequestDispatcher("/transport/util.jsp").include(request, response);
    responseEntity = (TransportEntity) request.getAttribute("responseEntity");
%>
