<%@ page import="javax.naming.InitialContext" %>
<%@ page import="admin.AuthentificationBean" %>
<%@ page import="controller.UserSessionBean" %>
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
    InitialContext initialContext = new InitialContext();
    AuthentificationBean authentificationBean = (AuthentificationBean) initialContext.lookup("java:app/ejb_ejb/AuthentificationEJB!admin.AuthentificationBean");

    String loginString = request.getParameter("login");
    String passString = request.getParameter("pass");
    UserSessionBean userSessionBean = authentificationBean.login(loginString, passString);
    if(userSessionBean != null) {
        request.getSession().setAttribute("authorized", true);
        request.getSession().setAttribute("userBean", userSessionBean);
    }
%>