<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 02.04.2018
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${param.containsKey('allServices')}">
        <c:set var="serviceType" scope="session" value="${param.get('allServices')}"/>
    </c:when>
</c:choose>

<%@include file="controller/viewall.jsp"%>

<html>
<head>
    <title><c:out value="${sessionScope.get('serviceType')}"/> Service</title>
    <link rel="stylesheet" type="text/css" href="../styles/mystyle1.css"/>
</head>
<body>
<table>
    <tr>
        <th>Name</th>
        <th>Provision date</th>
        <th>Disabling date</th>
    </tr>
    <%
        out.print(viewAsRows());
    %>
</table>
</body>
</html>
