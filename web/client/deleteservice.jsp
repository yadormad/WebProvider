<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 30.03.2018
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    if(request.getParameter("deleteServiceButton")!=null) {
        request.setAttribute("command", "delete");
        request.setAttribute("message", "service");
        request.setAttribute("requestObject", Integer.parseInt(request.getParameter("deleteServiceButton")));
        request.getRequestDispatcher("/transport/util.jsp").include(request, response);
    }
%>
