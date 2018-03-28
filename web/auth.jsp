<%@ page import="login.LoginEntity" %>
<%@ page import="server.admin.ServerCommunicator" %><%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 23.03.2018
  Time: 1:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String loginString = request.getParameter("login");
    String passString = request.getParameter("pass");
    LoginEntity loginEntity = new LoginEntity();
    loginEntity.setLogin(loginString);
    loginEntity.setPass(passString);
    Boolean isAuthorized = ServerCommunicator.authorizeNewUser(session.getId(), loginEntity);
    request.getSession().setAttribute("authorized", isAuthorized);
    if(isAuthorized) {
        request.getRequestDispatcher("/menu/menu.jsp").forward(request, response);
        //request.getRequestDispatcher("/index.jsp").forward(request, response);
    } /*else {
        request.getRequestDispatcher("/menu/menu.jsp").forward(request, response);
    }*/;
%>
