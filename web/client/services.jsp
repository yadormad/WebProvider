
<%@ page import="entity.transport.TransportEntity" %>
<%@ page import="entity.impl.ServiceType" %>
<%@ page import="entity.impl.Service" %>
<%@ page import="entity.ProviderEntity" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="entity.impl.Client" %>
<%@ page import="java.util.*" %><%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 28.03.2018
  Time: 0:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!private TransportEntity responseEntity;
    private Integer clientId;

    private String viewAsRows() {
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
                        .append("         </form>").append("</td>\n")
                        .append("    </tr>\n");
                unusedServiceTypes.remove(nextService.getType());
            }

            for(ServiceType serviceType:unusedServiceTypes) {
                rowBuilder.append("    <tr>\n")
                        .append("        <td>").append(serviceType.toString()).append("</td>\n")
                        .append("        <td colspan=\"4\"><form class=tableform action=../service/new.jsp method=post>\n")
                        .append("           <button class=inputform type=submit name=addServiceButton value=").append(clientId).append(">Add</button>")
                        .append("         </form>").append("</td>\n")
                        .append("    </tr>\n");
            }
        } else {
            rowBuilder.append(responseEntity.getMessage());
        }
        return rowBuilder.toString();
    }
%>

<%
    clientId = Integer.parseInt(request.getParameter("clientButton"));
    request.setAttribute("command", "getclient");
    request.setAttribute("requestObject", clientId);
    request.getRequestDispatcher("/client/util.jsp").include(request, response);
    Client client = (Client) ((TransportEntity) request.getSession().getAttribute("responseEntity")).getResponseProviderEntities().toArray()[0];
    request.setAttribute("command", "clientservices");
    request.getRequestDispatcher("/client/util.jsp").include(request, response);
    responseEntity = (TransportEntity) request.getSession().getAttribute("responseEntity");
%>

<html>
<head>
    <title>Services of <%=client.getName()%></title>
    <link rel="stylesheet" type="text/css" href="../styles/mystyle1.css"/>
</head>
<body class="stpage">
<h1 class="stpage">Services of <%=client.getName()%> table</h1>
<p>client info: <%=client.getInfo()%></p>
<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Provision date</th>
        <th>Disabling date</th>
        <th>Action</th>
    </tr>
    <%
        out.print(viewAsRows());
    %>
</table>
</body>
</html>
