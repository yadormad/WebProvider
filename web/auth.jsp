<%@ page import="login.LoginEntity" %>
<%@ page import="server.admin.ServerCommunicator" %>
<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 23.03.2018
  Time: 1:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%
    String loginString = request.getParameter("login");
    String passString = request.getParameter("pass");
    LoginEntity loginEntity = new LoginEntity();
    loginEntity.setLogin(loginString);
    loginEntity.setPass(passString);
    Boolean isAuthorized = ServerCommunicator.authorizeNewUser(session.getId(), loginEntity);
    request.getSession().setAttribute("authorized", isAuthorized);
%>