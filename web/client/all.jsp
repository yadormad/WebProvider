<%@ page import="java.util.Collection" %>
<%@ page import="entity.impl.Client" %>
<%@ page import="server.admin.ServerCommunicator" %>
<%@ page import="entity.transport.TransportEntity" %>
<%@ page errorPage="/error_page.jsp" %><%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 23.03.2018
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private TransportEntity responseEntity;

    private String viewAsRows(){
        StringBuilder rowBuilder = new StringBuilder();
        Collection<Client> clientsCollection = (Collection<Client>) responseEntity.getResponseProviderEntities();
        if(clientsCollection != null && !clientsCollection.isEmpty()) {
            for (Client nextClient : clientsCollection) {
                rowBuilder.append("    <tr>\n        <td>").append(nextClient.getName()).append("</td>\n")
                        .append("        <td><form class=tableform action=../client/services.jsp method=post>\n")
                        .append("           <button class=inputform type=submit name=clientButton value=").append(nextClient.getId()).append("> View services </button>")
                        .append("         </form>").append("\n")
                        .append("        <form class=tableform action=../client/all.jsp method=post>\n")
                        .append("           <button class=inputform type=submit name=deleteButton value=").append(nextClient.getId()).append("> Delete client </button>")
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
    if(request.getParameter("deleteButton") != null) {
        request.setAttribute("command", "delete");
        request.setAttribute("message", "client");
        request.setAttribute("requestObject", Integer.parseInt(request.getParameter("deleteButton")));
        request.getRequestDispatcher("/transport/util.jsp").include(request, response);
    }
    request.setAttribute("command", "allclients");
    request.getRequestDispatcher("/transport/util.jsp").include(request, response);
    responseEntity = (TransportEntity) request.getSession().getAttribute("responseEntity");
%>
<html>
<head>
    <title>Clients table</title>
    <link rel="stylesheet" type="text/css" href="../styles/mystyle1.css"/>
</head>
<body class="stpage">
<h1 class="stpage">Clients table</h1>
<table>
    <tr>
        <th>Name</th>
        <th>Action</th>
    </tr>
    <%
        out.print(viewAsRows());
    %>
</table>
<form class="inputform" action="../client/new.jsp" method=post>
    <button name="addButton" type="submit" class=inputform>Add client</button>
</form>
<button class=inputform onclick='history.back()'>Back</button>
<p class=error>
    <%
        if(request.getAttribute("error") != null) {
            out.print(request.getAttribute("error"));
        }
    %>
</p>
</body>
</html>
