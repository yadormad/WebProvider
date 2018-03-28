<%@ page import="entity.transport.TransportEntity" %>
<%@ page import="server.admin.ServerCommunicator" %>
<%@ page import="java.io.Serializable" %><%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 27.03.2018
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    TransportEntity requestEntity = new TransportEntity();
    requestEntity.setCommand(String.valueOf(request.getAttribute("command")));
    requestEntity.setRequsetObject((Serializable) request.getAttribute("requestObject"));
    TransportEntity responseEntity = ServerCommunicator.perform(session.getId(), requestEntity);
    request.getSession(true).setAttribute("responseEntity", responseEntity);
%>
