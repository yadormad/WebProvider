<%@ page import="entity.impl.ServiceType" %><%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 02.04.2018
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="controller/viewmenu.jsp"/>
<html>
<head>
    <title>Service types</title>
    <link rel="stylesheet" type="text/css" href="../styles/mystyle1.css"/>
</head>
<body class="inputform">
<c:set var="list" value="${sessionScope.get('ServiceTypes')}" scope="page"/>
<h1>Service types</h1>
<form class="inputform" action="all.jsp" method=post>
    <c:forEach var="type" items="${list}">
        <button name=allServices type=submit class=inputform value=<c:out value="${type.name()}"/>>View <c:out value="${type.name()}"/></button>
    </c:forEach>
</form>
<form class="inputform" action="menu.jsp" method=post>
    <jsp:include page="../back/backbutton.jsp"/>
</form>
</body>
</html>
