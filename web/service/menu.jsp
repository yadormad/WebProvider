<%@ page import="entity.impl.ServiceType" %><%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 02.04.2018
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="userBean" class="controller.UserSessionBean" scope="session">
    <jsp:setProperty name="userBean" property="*"/>
</jsp:useBean>

<html>
<head>
    <title>Service types</title>
    <link rel="stylesheet" type="text/css" href="../styles/mystyle1.css"/>
</head>
<body class="stpage">
<h1 class="stpage">Service types</h1>
<form class="inputform" action="all.jsp" method=post>
    <c:forEach var="type" items="${userBean.allServiceTypes}">
        <button name=serviceType type=submit class=inputform value="<c:out value="${type.name()}"/>">View <c:out value="${type.name()}"/></button>
    </c:forEach>
</form>
<c:if test="${not empty param.errorMessage && param.errorMessage.equals('notype')}">
    <p class="error">No type was chosen</p>
</c:if>
<a href="../index.jsp" class="stpage">Back</a>
</body>
</html>
