<%@ page import="entity.impl.Client" %>
<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 30.03.2018
  Time: 12:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    if(request.getParameter("addButton") != null && request.getParameter("addButton").equals("submitted")) {
        Client client = new Client(request.getParameter("name"), request.getParameter("info"));
        request.setAttribute("command", "addclient");
        request.setAttribute("requestObject", client);
        request.getRequestDispatcher("/transport/util.jsp").include(request, response);
    }
%>
